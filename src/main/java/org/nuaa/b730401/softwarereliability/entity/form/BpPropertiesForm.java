package org.nuaa.b730401.softwarereliability.entity.form;

import lombok.Data;

import java.util.List;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2019/1/10 22:12
 */
@Data
public class BpPropertiesForm {
    private int batch;
    private int generation;
    private int layerNum;
    private List<LayerForm> layers;
    /**
     * 迭代次数
     */
    private int count;
    /**
     * 阈值
     */
    private double limit;
    /**
     * 学习率
     */
    private double learningRate;

    /**
     * 损失函数
     */
    private String loss;
}

