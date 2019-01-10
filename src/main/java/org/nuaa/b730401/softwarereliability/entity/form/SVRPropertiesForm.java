package org.nuaa.b730401.softwarereliability.entity.form;

import lombok.Data;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2019/1/10 23:07
 */
@Data
public class SVRPropertiesForm {
    /**
     * svm type
     */
    private int svm = 0;
    /**
     * kernel type
     */
    private int kernel = 3;

    /**
     * degree in kernel function
     */
    private int degree = 3;

    /**
     * the parameter C of C-SVC, epsilon-SVR, and nu-SVR
     */
    private int cost = 1;

    /**
     * the epsilon in loss function of epsilon-SVR
     */
    private double epsilon = 0.1;

    /**
     * cache memory size in MB
     */
    private int cacheSize = 100;

}
