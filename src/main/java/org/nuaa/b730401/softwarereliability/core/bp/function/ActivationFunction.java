package org.nuaa.b730401.softwarereliability.core.bp.function;

/**
 * @Author: ToMax
 * @Description: 激活函数
 * @Date: Created in 2018/12/8 14:40
 */
public interface ActivationFunction extends Derivable<Double, Double>{
    /**
     * 激活
     * @param x input value
     * @return output value
     */
    double activate(double x);
}
