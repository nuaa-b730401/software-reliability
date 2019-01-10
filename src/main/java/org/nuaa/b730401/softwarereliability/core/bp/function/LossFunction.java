package org.nuaa.b730401.softwarereliability.core.bp.function;

import org.nuaa.b730401.softwarereliability.core.bp.matrix.Matrix;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/12/8 20:14
 */
public interface LossFunction extends Derivable<Double, Double>{
    /**
     * 计算损失值
     * @param result 训练结果
     * @param target 目标值
     * @return 损失值
     */
    double loss(Matrix result, Matrix target);

    /**
     * 计算导数值
     * @param x
     * @param target
     * @return
     */
    double derivativeValue(double x, double target);
}
