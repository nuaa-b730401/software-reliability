package org.nuaa.b730401.softwarereliability.core.bp.matrix;

/**
 * @Author: ToMax
 * @Description: 矩阵数据简单封装，后续完善
 * @Date: Created in 2018/12/8 10:28
 */
public class Matrix{
    private double[][] data;
    private int row;
    private int col;

    public Matrix(int row, int col) {
        data = new double[row][col];
        this.row = row;
        this.col = col;
    }

    public Matrix(double[][] data) {
        this.data = data;
        if (data != null) {
            this.row = data.length;
            if (row > 0) {
                this.col = data[0].length;
            }
        }
    }

    public void set(int i, int j, double value) {
        this.data[i][j] = value;
    }

    public double get(int i, int j) {
        return this.data[i][j];
    }

    public void resize(int row, int col) {
        // TODO
    }

    @Override
    public Matrix clone() {
        return new Matrix(data);
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }
    /**
     * 将data转为字符串
     * @return
     */
    public String dataStringBuilder() {
        StringBuilder builder = new StringBuilder();
        builder.append(row);
        builder.append(col);
        builder.append("\n");
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                builder.append(data[i][j]);
                builder.append(" ");
            }
            builder.append("\n");
        }

        return builder.toString();
    }
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("matrix\n");
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                builder.append(data[i][j]);
                builder.append("\t");
            }
            builder.append("\n");
        }
        return builder.toString();
    }
}
