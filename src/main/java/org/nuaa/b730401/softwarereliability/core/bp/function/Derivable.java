package org.nuaa.b730401.softwarereliability.core.bp.function;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/12/8 20:15
 */
public interface Derivable<T, R> {
    /**
     * 导数
     * @param x input value
     * @return output value
     */
    R derivative(T x);
}
