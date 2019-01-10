package org.nuaa.b730401.softwarereliability.entity.form;

import lombok.Data;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2019/1/10 23:02
 */
@Data
public class LayerForm {
    /**
     * 输出维度
     */
    private int row;
    /**
     * 输入维度
     */
    private int col;
    /**
     * 激活函数类型
     */
    private String activation;
}
