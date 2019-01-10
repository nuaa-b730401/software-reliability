package org.nuaa.b730401.softwarereliability.core.bp;


import org.nuaa.b730401.softwarereliability.core.bp.constant.FunctionConstant;
import org.nuaa.b730401.softwarereliability.core.bp.function.LossFunction;
import org.nuaa.b730401.softwarereliability.core.bp.matrix.Matrix;
import org.nuaa.b730401.softwarereliability.core.exception.MatrixSizeException;
import org.nuaa.b730401.softwarereliability.core.exception.NetworkException;

import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/12/8 14:19
 */
public class Network {
    /**
     * 最大网络层数
     */
    private static final int MAX_NETWORK_LAYERS = 100;
    /**
     * 单层最多节点
     */
    private static final int MAX_NODE_NUM = 500;
    /**
     * 网络层级
     */
    private List<Layer> layers;

    /**
     * 学习率
     */
    private double learningRate = 1;

    private double lossThreshold;

    /**
     * 最大迭代次数
     */
    private int maxGeneration;

    /**
     * 设置损失计算函数，默认为NormalLossFunction，是一个比较粗糙的实现
     */
    private LossFunction lossFunction = FunctionConstant.LOSS_FUNCTION_MAP.get("normal");

    /**
     * 存储delta计算过程中间值，维度为2，第一个维度是层数，第二个维度是index
     * 即链式偏导到第i层的第j个输出的值
     */
    private double[][] deltaCalculateCache = new double[MAX_NETWORK_LAYERS][MAX_NODE_NUM];

    public Network() {
        layers = new LinkedList<>();
    }

    /**
     * 运行网络
     * @param input
     * @param target
     * @return 最后计算的结果
     */
    public Matrix execute(Matrix input, Matrix target) throws MatrixSizeException {
        if (layers.size() <= 0) {
            return input;
        }
        Matrix result = forward(input);
        int generationCount = 0;
        // 开始迭代循环
        while (lossValue(result, target) > lossThreshold && generationCount <= maxGeneration) {
            // 代数增加
            generationCount++;
            // 初始化deltaCalculateCache，即初始化loss求偏导的值
            for (int i = 0; i < layers.get(layers.size() - 1).getOutputSize(); i++) {
                deltaCalculateCache[layers.size() - 1][i] = learningRate * lossFunction.derivativeValue(layers.get(layers.size() - 1)
                        .getOutput().get(i, 0), target.get(i, 0));
            }
            // 更新系统
            backward();
            // 重新计算输出
            result = forward(input);
        }

        System.out.println("this loss : " + lossValue(result, target));
        return result;
    }

    /**
     * 依据输入获取输出
     * @param input
     * @return
     * @throws MatrixSizeException
     */
    public Matrix execute(Matrix input) throws MatrixSizeException {
        return forward(input);
    }

    /**
     * 前向传播
     * @param input
     * @return
     * @throws MatrixSizeException
     */
    public Matrix forward(Matrix input) throws MatrixSizeException {
        Matrix matrix = input.clone();
        for (Layer layer : layers) {
            matrix = layer.calculateOutput(matrix);
        }
        return matrix;
    }

    /**
     * 误差反向传播
     */
    public void backward() throws MatrixSizeException {
        for (int i = layers.size(); i >= 1; i--) {
            calculateDelta(i);
        }
    }


    /**
     * 增加网络层
     * @param layer
     * @return
     * @throws NetworkException
     */
    public Network addLayer(Layer layer) throws NetworkException {
        if (layers.size() > MAX_NETWORK_LAYERS) {
            throw new NetworkException("layer out of bound");
        }
        layers.add(layer);
        return this;
    }

    /**
     * 设置损失函数
     * @param loss
     * @return
     */
    public Network setLoss(LossFunction loss) {
        this.lossFunction = loss;
        return this;
    }

    /**
     * 设置最大迭代次数
     * @param maxGeneration
     * @return
     */
    public Network setMaxGeneration(int maxGeneration) {
        this.maxGeneration = maxGeneration;
        return this;
    }

    /**
     * 设置损失的阈值
     * @param lossThreshold
     * @return
     */
    public Network setLossThreshold(double lossThreshold) {
        this.lossThreshold = lossThreshold;
        return this;
    }

    /**
     * 计算单轮的损失值
     * @param result
     * @param target
     * @return
     */
    private double lossValue(Matrix result, Matrix target) {
        return lossFunction.loss(result, target);
    }

    /**
     * 计算并且更新参数值
     * @param layerNum
     * @throws MatrixSizeException
     */
    private void calculateDelta(int layerNum) throws MatrixSizeException {
        Layer layer = layers.get(layerNum - 1);

        // 计算bias的误差
        Matrix biasDelta = new Matrix(layer.getBiasMatrix().getRow(), layer.getBiasMatrix().getCol());
        for (int i = 0; i < biasDelta.getRow(); i++) {
            biasDelta.set(i, 0, deltaCalculateCache[layerNum - 1][i]
                    * layer.getActivationFunction().derivative(layer.getAddResult().get(i, 0)));
        }

        // 计算weight的误差
        Matrix weightDelta = new Matrix(layer.getWeightMatrix().getRow(), layer.getWeightMatrix().getCol());
        for (int i = 0; i < weightDelta.getRow(); i++) {
            for (int j = 0; j < weightDelta.getCol(); j++) {
                // 假设输入层，输出层恒为一维向量
                weightDelta.set(i, j,
                        layer.getInput().get(j, 0) * biasDelta.get(i, 0)
                );
            }
        }

        // 更新到layer中
        layer.updateDeltaMatrix(weightDelta, biasDelta);
        // 更新layer层的参数
        layer.updateParamValue();

        // 更新delta cache
        if (layerNum == 1) {
            return;
        }
        layerNum --;
        // TODO : 验证
        for (int i = 0; i < layer.getInputSize(); i++) {
            double sum = 0;
            for (int j = 0; j < layer.getOutputSize(); j++) {
                sum += layer.getWeightMatrix().get(j, i) * biasDelta.get(j, 0);
            }
            deltaCalculateCache[layerNum - 1][i] = sum;
        }
    }

    /**
     * 简单导出模型，直接以文本形式输出
     * TODO : 后续考虑存入数据库或者使用json存储
     * @param path
     * @throws IOException
     */
    public void extractModel(String path) throws IOException {
        FileWriter writer = new FileWriter(path);
        writer.write(String.valueOf(learningRate));
        writer.write("\n");
        writer.write(String.valueOf(lossThreshold));
        writer.write("\n");
        writer.write(String.valueOf(maxGeneration));
        writer.write("\n" + layers.size());
//        writer.write(lossFunction.toString());
//        writer.write("\n");
        writer.write("\n");
        for (Layer layer : layers) {
            writer.write(layer.getName());
            writer.write("\n" + layer.getInputSize());
            writer.write("\n" + layer.getOutputSize());
            writer.write("\n");
            writer.write(layer.getWeightMatrix().dataStringBuilder());
            writer.write(layer.getBiasMatrix().dataStringBuilder());
            writer.write(layer.getInput().dataStringBuilder());
            writer.write(layer.getOutput().dataStringBuilder());
            writer.write("\n");
//            writer.write(layer.getActivationFunction().toString());
//            writer.write("\n");
        }
        writer.close();
    }

    public List<Layer> getLayers() {
        return layers;
    }

    public void setLayers(List<Layer> layers) {
        this.layers = layers;
    }

    public double getLearningRate() {
        return learningRate;
    }

    public Network setLearningRate(double learningRate) {
        this.learningRate = learningRate;
        return this;
    }

    public double getLossThreshold() {
        return lossThreshold;
    }

    public int getMaxGeneration() {
        return maxGeneration;
    }

    public LossFunction getLossFunction() {
        return lossFunction;
    }

    public double[][] getDeltaCalculateCache() {
        return deltaCalculateCache;
    }
}
