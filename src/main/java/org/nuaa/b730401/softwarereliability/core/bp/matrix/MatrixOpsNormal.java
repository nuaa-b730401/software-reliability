package org.nuaa.b730401.softwarereliability.core.bp.matrix;

import org.nuaa.b730401.softwarereliability.core.exception.MatrixSizeException;

import java.util.Random;
import java.util.function.Function;

/**
 * @Author: ToMax
 * @Description: 最粗糙的方式实现矩阵运算
 * @Date: Created in 2018/12/8 10:44
 */
public class MatrixOpsNormal implements MatrixOps {
    /**
     * 用于初始化矩阵的随机数
     */
    private static final Random NORMAL_RANDOM = new Random();
    @Override
    public Matrix multiple(Matrix a, Matrix b) throws MatrixSizeException {
        if (a == null || b == null) {
            return null;
        }
        if (a.getCol() != b.getRow()) {
            throw new MatrixSizeException(a + ".col(" + a.getCol() + ") != " + b + ".row(" + b.getRow() + ")");
        }

        Matrix matrix = new Matrix(a.getRow(), b.getCol());

        // 开始运算，没有任何优化
        for (int row = 0; row < matrix.getRow(); row++) {
            for (int col = 0; col < matrix.getCol(); col++) {
                double value = 0;
                for (int i = 0; i < a.getCol(); i++) {
                    value += a.get(row, i) * b.get(i, col);
                }
                matrix.set(row, col, value);
            }
        }
        return matrix;
    }

    @Override
    public Matrix add(Matrix a, Matrix b) throws MatrixSizeException {
        if (a == null || b == null) {
            return null;
        }

        if (a.getRow() != b.getRow() || a.getCol() != b.getCol()) {
            throw new MatrixSizeException("matrix a is not the same size as matrix b");
        }

        Matrix matrix = new Matrix(a.getRow(), a.getCol());

        // 直接相加
        for (int i = 0; i < matrix.getRow(); i++) {
            for (int j = 0; j < matrix.getCol(); j++) {
                matrix.set(i, j,
                        a.get(i, j) + b.get(i, j));
            }
        }
        return matrix;
    }

    @Override
    public Matrix minus(Matrix a, Matrix b) throws MatrixSizeException {
        if (a == null || b == null) {
            return null;
        }

        if (a.getRow() != b.getRow() || a.getCol() != b.getCol()) {
            throw new MatrixSizeException("matrix a is not the same size as matrix b");
        }

        Matrix matrix = new Matrix(a.getRow(), a.getCol());

        // 直接相减
        for (int i = 0; i < matrix.getRow(); i++) {
            for (int j = 0; j < matrix.getCol(); j++) {
                matrix.set(i, j,
                        a.get(i, j) - b.get(i, j));
            }
        }
        return matrix;
    }

    @Override
    public Matrix reverse(Matrix matrix) {
        Matrix rev = new Matrix(matrix.getCol(), matrix.getRow());
        for (int i = 0; i < matrix.getRow(); i++) {
            for (int j = 0; j < matrix.getCol(); j++) {
                rev.set(j, i, matrix.get(i, j));
            }
        }
        return rev;
    }

    @Override
    public Matrix initializeMatrix(int row, int col) {
        Matrix matrix = new Matrix(row, col);

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                matrix.set(i, j, NORMAL_RANDOM.nextGaussian());
            }
        }

        return matrix;
    }

    @Override
    public void mapTo(Matrix target, Function<Double, Double> function) {
        if (target != null) {
            for (int i = 0; i < target.getRow(); i++) {
                for (int j = 0; j < target.getCol(); j++) {
                    target.set(i, j, function.apply(target.get(i, j)));
                }
            }
        }
    }
}
