package org.nuaa.b730401.softwarereliability.entity;

import lombok.Data;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2019/1/10 23:51
 */
@Data
public class PredictResult {
    private int successCount;
    private int count;

    public PredictResult(int count) {
        this.count = count;
    }

    public void add() {
        successCount++;
    }
}
