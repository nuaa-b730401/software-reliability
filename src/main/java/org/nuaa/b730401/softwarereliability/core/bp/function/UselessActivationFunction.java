package org.nuaa.b730401.softwarereliability.core.bp.function;

/**
 * @Author: ToMax
 * @Description: 空的激活函数，什么都没做
 * @Date: Created in 2018/12/10 23:14
 */
public class UselessActivationFunction implements ActivationFunction {
    @Override
    public double activate(double x) {
        return x;
    }

    @Override
    public Double derivative(Double x) {
        return 1d;
    }
}
