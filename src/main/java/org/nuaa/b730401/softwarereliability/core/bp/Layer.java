package org.nuaa.b730401.softwarereliability.core.bp;


import org.nuaa.b730401.softwarereliability.core.bp.constant.FunctionConstant;
import org.nuaa.b730401.softwarereliability.core.bp.function.ActivationFunction;
import org.nuaa.b730401.softwarereliability.core.bp.matrix.Matrix;
import org.nuaa.b730401.softwarereliability.core.bp.matrix.MatrixOps;
import org.nuaa.b730401.softwarereliability.core.bp.matrix.MatrixOpsNormal;
import org.nuaa.b730401.softwarereliability.core.exception.MatrixSizeException;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/12/8 11:13
 */
public class Layer {
    /**
     * layer名称
     */
    private String name = "network-layer";

    /**
     * 输入规格
     */
    private int inputSize;
    /**
     * 输出规格
     */
    private int outputSize;

    /**
     * 输入，保留（暂时无用）
     */
    private Matrix input;

    /**
     * 权重矩阵
     */
    private Matrix weightMatrix;

    /**
     * 偏置矩阵
     */
    private Matrix biasMatrix;

    /**
     * 输出
      */
    private Matrix output;

    /**
     * 激活函数，默认为sigmoid函数
     */
    private ActivationFunction activationFunction = FunctionConstant.ACTIVATION_FUNCTION.get("sigmoid");

    /**
     * 默认使用普通计算方法，若需要优化，设置相应的计算器
     */
    private MatrixOps matrixOps = new MatrixOpsNormal();

    /**
     * 矩阵初始化工具，默认用比较粗糙的实现方法
     */
    private MatrixOps initMatrixOps = new MatrixOpsNormal();

    /**
     * 暂存delta值
     */
    private Matrix weightDeltaMatrix;
    private Matrix biasDeltaMatrix;

    /**
     * 缓存当前步骤计算的中间结果
     */
    private Matrix multipleResult;
    private Matrix addResult;

    public Layer(){}

    public Layer(String name) {
        this.name = name;
    }

    public Layer(String name, int inputSize, int outputSize) {
        this.name = name;
        this.inputSize = inputSize;
        this.outputSize = outputSize;
        this.weightMatrix = initMatrixOps.initializeMatrix(outputSize, inputSize);
        this.biasMatrix = initMatrixOps.initializeMatrix(outputSize, 1);
    }

    /**
     * 设置输入向量
     * @param input
     * @return
     */
    public Layer setInput(Matrix input) {
        this.input = input;
        return this;
    }

    /**
     * 设置权重矩阵
     * @param row
     * @param col
     * @param init 初始化方法
     * @return
     */
    public Layer setWeight(int row, int col, MatrixOps init) {
        this.weightMatrix = init.initializeMatrix(row, col);
        return this;
    }

    /**
     * 设置偏置矩阵
     * @param row
     * @param col
     * @param init 初始化方法
     * @return
     */
    public Layer setBias(int row, int col, MatrixOps init) {
        this.biasMatrix = init.initializeMatrix(row, col);
        return this;
    }

    /**
     * 设置激活函数
     * @param activation
     * @return
     */
    public Layer setActivation(ActivationFunction activation) {
        this.activationFunction = activation;
        return this;
    }

    /**
     * 设置矩阵计算工具
     * @param ops
     * @return
     */
    public Layer setMatrixOps(MatrixOps ops) {
        this.matrixOps = ops;
        return this;
    }

    /**
     * 计算该层输出
     * @param input (上一层的输出)
     * @return
     * @throws MatrixSizeException 矩阵计算异常
     */
    public Matrix calculateOutput(Matrix input) throws MatrixSizeException {
        // 缓存输入
        this.input = input;

        // 从输入开始计算
        Matrix out = input;

        // 若需要计算权重，计算
        if (weightMatrix != null) {
            out = matrixOps.multiple(weightMatrix, out);
            this.multipleResult = out;
        }

        // 若需要加偏置，计算
        if (biasMatrix != null) {
            out = matrixOps.add(out, biasMatrix);
            this.addResult = out;
        }

        // 若需要激活，计算
        if (activationFunction != null) {
            matrixOps.mapTo(out, activationFunction::activate);
        }

        // 缓存输出
        this.output = out;
        return out;
    }

    /**
     * 更新临时的delta矩阵
     * @param weightDeltaMatrix
     * @param biasDeltaMatrix
     * @return
     */
    public Layer updateDeltaMatrix(Matrix weightDeltaMatrix, Matrix biasDeltaMatrix) {
        this.weightDeltaMatrix = weightDeltaMatrix;
        this.biasDeltaMatrix = biasDeltaMatrix;
        return this;
    }

    public void updateParamValue() throws MatrixSizeException {
        if (weightMatrix != null) {
            // 更新weight矩阵的权重
            weightMatrix = matrixOps.add(weightMatrix, weightDeltaMatrix);
        }

        if (biasDeltaMatrix != null) {
            // 更新bias矩阵的权重
            biasMatrix = matrixOps.add(biasMatrix, biasDeltaMatrix);
        }
    }

    public String getName() {
        return name;
    }

    public Matrix getInput() {
        return input;
    }

    public Matrix getWeightMatrix() {
        return weightMatrix;
    }


    public Matrix getBiasMatrix() {
        return biasMatrix;
    }


    public Matrix getOutput() {
        return output;
    }

    public ActivationFunction getActivationFunction() {
        return activationFunction;
    }

    public void setActivationFunction(ActivationFunction activationFunction) {
        this.activationFunction = activationFunction;
    }

    public int getInputSize() {
        return inputSize;
    }

    public void setInputSize(int inputSize) {
        this.inputSize = inputSize;
    }

    public int getOutputSize() {
        return outputSize;
    }

    public void setOutputSize(int outputSize) {
        this.outputSize = outputSize;
    }

    public Matrix getMultipleResult() {
        return multipleResult;
    }

    public void setMultipleResult(Matrix multipleResult) {
        this.multipleResult = multipleResult;
    }

    public Matrix getAddResult() {
        return addResult;
    }

    public void setAddResult(Matrix addResult) {
        this.addResult = addResult;
    }

    @Override
    public String toString() {
        String brief = "layer";
        brief += "(" + name + ")\n";
        brief += "input size : " + inputSize + "\n";
        brief += "output size : " + outputSize + "\n";
        if (weightMatrix != null) {
            brief += "weight matrix size : (" + weightMatrix.getRow() + ", " + weightMatrix.getCol() + ")\n";
        }
        if (biasMatrix != null) {
            brief += "bias matrix size : (" + biasMatrix.getRow() + ", " + biasMatrix.getCol() + ")\n";
        }
        if (activationFunction != null) {
            brief += "activation is " + activationFunction.getClass().getSimpleName() + "\n";
        }
        brief += "matrix ops is " + matrixOps.getClass().getSimpleName() + "\n";
        return brief;
    }
}
