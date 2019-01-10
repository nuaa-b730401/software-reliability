package org.nuaa.b730401.softwarereliability.core.bean;

import java.util.List;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/11/5 18:59
 */
public class JmModelDataBean extends Model {
    /**
     * p的值对于单个jm而言应是定值
     */
    private Double valueP;

    /**
     * n 的估计值
     */
    private double nPointEvaluate;

    /**
     * fai 的估计值
     */
    private double faiPointEvaluate;

    // 过程变量
    private int right;
    private int left;
    private int root;

    public JmModelDataBean() {

    }

    public JmModelDataBean(List<Double> dataset) {
        super(dataset);
    }

    @Override
    public double calculateDistribution(double x) {
        double value = Math.exp(-faiPointEvaluate * (nPointEvaluate - trainDataNum + 1) * x);
        return  1 - value;
    }

    @Override
    public double calculateProbabilityDensity(double x) {
        double value = faiPointEvaluate * (nPointEvaluate - trainDataNum + 1);
        return Math.exp(-value * x);
    }

    public Double getValueP() {
        return valueP;
    }

    public void setValueP(Double valueP) {
        this.valueP = valueP;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getRoot() {
        return root;
    }

    public void setRoot(int root) {
        this.root = root;
    }

    public double getnPointEvaluate() {
        return nPointEvaluate;
    }

    public void setnPointEvaluate(double nPointEvaluate) {
        this.nPointEvaluate = nPointEvaluate;
    }

    public double getFaiPointEvaluate() {
        return faiPointEvaluate;
    }

    public void setFaiPointEvaluate(double faiPointEvaluate) {
        this.faiPointEvaluate = faiPointEvaluate;
    }
}
