package org.nuaa.b730401.softwarereliability.core.bp.matrix;

import org.nuaa.b730401.softwarereliability.core.exception.MatrixSizeException;

import java.util.function.Function;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/12/8 10:40
 */
public interface MatrixOps {
    /**
     * 矩阵乘法
     * @param a 矩阵a
     * @param b 矩阵b
     * @return 结果矩阵
     * @throws MatrixSizeException 不满足矩阵运算规则
     */
    Matrix multiple(Matrix a, Matrix b) throws MatrixSizeException;

    /**
     * 矩阵加法
     * @param a 矩阵a
     * @param b 矩阵b
     * @return 结果矩阵
     * @throws MatrixSizeException 不满足矩阵加法运算规则
     */
    Matrix add(Matrix a, Matrix b) throws MatrixSizeException;

    /**
     * 矩阵减法
     * @param a 被减矩阵
     * @param b 减矩阵
     * @return 结果矩阵
     * @throws MatrixSizeException 不满足矩阵减法运算条件
     */
    Matrix minus(Matrix a, Matrix b) throws MatrixSizeException;

    /**
     * 初始化矩阵
     * @param row
     * @param col
     * @return
     */
    Matrix initializeMatrix(int row, int col);

    /**
     * 矩阵逆置
     * @param matrix
     * @return
     */
    Matrix reverse(Matrix matrix);

    /**
     * 映射矩阵
     * @param target
     * @param function
     */
    void mapTo(Matrix target, Function<Double, Double> function);

}
