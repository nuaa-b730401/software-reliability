package org.nuaa.b730401.softwarereliability.core.bean;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/11/15 18:15
 */
public abstract class Model implements DataReader{
    /**
     * 错误数
     */
    protected int n;
    protected int trainDataNum;

    /**
     * 失效时间列表，数据集
     */
    protected List<Double> failCountList;
    protected List<Double> trainDataset;
    protected List<Double> testDataset;

    /**
     * U-图数据
     */
    protected List<Double> uValueList;
    protected double uKS;
    // 记录ks坐标（x）
    protected int uksxIndex;

    /**
     * Y-图数据
     */
    protected List<Double> yValueList;
    protected double yKS;
    //记录ks坐标（x）
    protected int yksxIndex;

    public Model() {
        failCountList = new LinkedList<>();
        uValueList = new LinkedList<>();
        yValueList = new LinkedList<>();
        trainDataset = new LinkedList<>();
        testDataset = new LinkedList<>();
        trainDataset.add(0d);
    }

    public Model(List<Double> dataset) {
        this();
        for (Double value : dataset) {
            add(value);
        }
        // 构造训练集和测试集
        trainDataNum = (int) (n * 0.7);
        for (int i = 0; i < n; i++) {
            if (i < trainDataNum) {
                trainDataset.add(failCountList.get(i));
            } else {
                testDataset.add(failCountList.get(i));
            }
        }

    }
    /**
     * 计算概率分布值
     * @param x
     * @return
     */
    public abstract double calculateDistribution(double x);

    /**
     * 计算概率密度值
     * @param x
     * @return
     */
    public abstract double calculateProbabilityDensity(double x);

    public void add(Double data) {
        this.failCountList.add(data);
        n++;
    }

    @Override
    public void readData(String path) throws IOException {
        FileInputStream stream = new FileInputStream(path);
        Scanner scanner = new Scanner(stream);
        while (scanner.hasNext()) {
            scanner.nextInt();
            add(scanner.nextDouble());
        }
        stream.close();
        scanner.close();

        // 构造训练集和测试集
        trainDataNum = (int) (n * 0.7);
        for (int i = 0; i < n; i++) {
            if (i < trainDataNum) {
                trainDataset.add(failCountList.get(i));
            } else {
                testDataset.add(failCountList.get(i));
            }
        }

    }

    public double get(int i) {
        return this.trainDataset.get(i);
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public int getTrainDataNum() {
        return trainDataNum;
    }

    public void setTrainDataNum(int trainDataNum) {
        this.trainDataNum = trainDataNum;
    }

    public List<Double> getFailCountList() {
        return failCountList;
    }

    public void setFailCountList(List<Double> failCountList) {
        this.failCountList = failCountList;
    }

    public List<Double> getuValueList() {
        return uValueList;
    }

    public void setuValueList(List<Double> uValueList) {
        this.uValueList = uValueList;
    }

    public List<Double> getyValueList() {
        return yValueList;
    }

    public void setyValueList(List<Double> yValueList) {
        this.yValueList = yValueList;
    }

    public List<Double> getTrainDataset() {
        return trainDataset;
    }

    public void setTrainDataset(List<Double> trainDataset) {
        this.trainDataset = trainDataset;
    }

    public List<Double> getTestDataset() {
        return testDataset;
    }

    public void setTestDataset(List<Double> testDataset) {
        this.testDataset = testDataset;
    }

    public double getuKS() {
        return uKS;
    }

    public void setuKS(double uKS) {
        this.uKS = uKS;
    }

    public double getyKS() {
        return yKS;
    }

    public void setyKS(double yKS) {
        this.yKS = yKS;
    }

    public int getUksxIndex() {
        return uksxIndex;
    }

    public void setUksxIndex(int uksxIndex) {
        this.uksxIndex = uksxIndex;
    }

    public int getYksxIndex() {
        return yksxIndex;
    }

    public void setYksxIndex(int yksxIndex) {
        this.yksxIndex = yksxIndex;
    }
}
