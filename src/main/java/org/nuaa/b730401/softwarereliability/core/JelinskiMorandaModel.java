package org.nuaa.b730401.softwarereliability.core;

import org.nuaa.b730401.softwarereliability.core.bean.JmModelDataBean;

import java.io.IOException;

/**
 * @Author: ToMax
 * @Description: 用于实现J-M模型
 * @Date: Created in 2018/11/5 18:47
 */
public class JelinskiMorandaModel {
    /**
     * 针对自变量x而给定的误差精度控制值
     */
    private static final int EX = 1;
    /**
     * 针对自变量y而给定的误差精度控制值
     */
    private static final int EY = 1;

    public static JmModelDataBean execute() throws IOException {
        JmModelDataBean jm = new JmModelDataBean();
        // 读取文件
        jm.readData(JelinskiMorandaModel.class.getResource("/").getPath() + "failure_count.txt");
        // 初始化
        calculateValueP(jm);
        // 从步骤一开始执行
        calculateStepFirst(jm);

        return jm;
    }

    /**
     * 计算失效时间间隔
     * @param jm
     * @param i
     * @return
     */
    private static double calculateMtbf(JmModelDataBean jm, int i) {
        return 1 / (jm.getFaiPointEvaluate() * (jm.getnPointEvaluate() - i + 1));
    }

    /**
     * 计算步骤一
     * @param jm
     */
    private static void calculateStepFirst(JmModelDataBean jm) {
        if (jm.getValueP() > (jm.getTrainDataNum() - 1) / 2.0) {
            jm.setLeft(jm.getTrainDataNum() - 1);
            jm.setRight(jm.getTrainDataNum());
            // to step 2
            calculateStepSecond(jm);
        }
    }

    /**
     * 步骤二
     * @param jm
     */
    private static void calculateStepSecond(JmModelDataBean jm) {
        while (calculateFunctionF(jm, jm.getRight()) > EY) {
            jm.setLeft(jm.getRight());
            jm.setRight(jm.getRight() + 1);
        }

        double compareValue = calculateFunctionF(jm, jm.getRight());
        if (compareValue >= -EX && compareValue <= EY) {
            jm.setRoot(jm.getRight());
            // to step 5
            calculateStepFifth(jm);

        } else if (compareValue <= -EX){
            // to step 3
            calculateStepThird(jm);
        }
    }

    /**
     * 步骤三
     * @param jm
     */
    private static void calculateStepThird(JmModelDataBean jm) {
        if (Math.abs(jm.getRight() - jm.getLeft()) <= EX) {
            jm.setRoot((jm.getRight() + jm.getLeft()) / 2);
            // to step 5
            calculateStepFifth(jm);
        } else if (Math.abs(jm.getRight() - jm.getLeft()) > EX) {
            jm.setRoot((jm.getRight() + jm.getLeft()) / 2);
            // to step 4
            calculateStepFourth(jm);
        }
    }

    /**
     * 步骤四
     * @param jm
     */
    private static void calculateStepFourth(JmModelDataBean jm) {
        double tempRootFValue = calculateFunctionF(jm, jm.getRoot());
        if (tempRootFValue > EY) {
            jm.setLeft(jm.getRoot());
            // to step 3
            calculateStepThird(jm);
        } else if (tempRootFValue >= -EX && tempRootFValue <= EY) {
            // to step 5
            calculateStepFifth(jm);
        } else {
            jm.setRight(jm.getRoot());
            // to step 3
            calculateStepThird(jm);
        }
    }

    /**
     * 步骤五
     * @param jm
     */
    private static void calculateStepFifth(JmModelDataBean jm) {
        jm.setnPointEvaluate(jm.getRoot());
        double tempFaiValue = 0;
        double baseValue = 0;
        for (int i = 1; i <= jm.getTrainDataNum(); i++) {
            baseValue += (i - 1) * (jm.get(i) - jm.get(i - 1));
        }
        baseValue = jm.getnPointEvaluate() * jm.get(jm.getTrainDataNum()) - baseValue;
        tempFaiValue = jm.getTrainDataNum() / baseValue;
        jm.setFaiPointEvaluate(tempFaiValue);
    }



    /**
     * 计算函数f
     * @param jm
     * @param n
     * @return
     */
    private static double calculateFunctionF(JmModelDataBean jm, int n) {
        double f = 0;
        for (int i = 1; i <= jm.getTrainDataNum(); i++) {
            f += 1 / (n - i + 1.0);
        }
        f -= (jm.getTrainDataNum() / (n - calculateValueP(jm)));
        return f;
    }

    /**
     * 求p，因p对于jm是定值，所以不去进行重复计算，而是缓存在相应的jm对象中
     * @param jm
     * @return
     */
    private static double calculateValueP(JmModelDataBean jm) {
        if (jm.getValueP() != null) {
            return jm.getValueP();
        }
        double p = 0;
        for (int i = 1; i <= jm.getTrainDataNum(); i++) {
            p += (i - 1) * (jm.get(i) - jm.get(i - 1));
        }
        p /= jm.get(jm.getTrainDataNum());
        jm.setValueP(p);
        return p;
    }
}

