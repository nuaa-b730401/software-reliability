package org.nuaa.b730401.softwarereliability.entity;

import lombok.Data;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2019/1/10 23:14
 */
@Data
public class DatasetBean {
    private int x;
    private double y;
    private double predict;

    public DatasetBean(int x, double y) {
        this.x = x;
        this.y = y;
    }
}
