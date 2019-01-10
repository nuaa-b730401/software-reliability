package org.nuaa.b730401.softwarereliability.core;


import org.nuaa.b730401.softwarereliability.core.bean.Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/11/27 20:20
 */
public class EvaluateModel {

    /**
     * U-图
     * @param model
     */
    public static void uGraphEvaluate(Model model) {
        List<Double> xAxisValueList = new ArrayList<>();

        for (int i = 0; i < model.getTestDataset().size(); i++) {
            xAxisValueList.add(model.calculateDistribution(model.getTestDataset().get(i)));
        }

        Collections.sort(xAxisValueList);
        for (Double in : xAxisValueList) {
            if (!model.getuValueList().contains(in)) {
                model.getuValueList().add(in);
            }
        }
        model.getuValueList().forEach(System.out::println);
    }

    /**
     * 计算Y-图
     * 此处i取0，n取U-图坐标个数
     * @param model
     */
    public static void yGraphEvaluate(Model model) {
        List<Double> xAxisValueList = new ArrayList<>();
        List<Double> yAxisValueList = new ArrayList<>();
        for (int i = 0; i < model.getuValueList().size(); i++) {
            xAxisValueList.add(-Math.log(1 - model.getuValueList().get(i)));
        }
        for (int i = 0; i < model.getuValueList().size() - 1; i++) {
            double sumTop = 0;
            double sumBase = 0;

            for (int j = 0; j <= i; j++) {
                sumTop += xAxisValueList.get(j);
            }

            for (int j = 0; j < model.getuValueList().size() - 1; j++) {
                sumBase += xAxisValueList.get(j);
            }
            yAxisValueList.add(sumTop / sumBase);
        }

        Collections.sort(yAxisValueList);
        for (Double in : yAxisValueList) {
            if (!model.getyValueList().contains(in)) {
                model.getyValueList().add(in);
            }
        }
    }

    /**
     * 计算K-S距离
     * @param model
     */
    public static void calculateKSDistance(Model model) {
        double ks = 0;
        int base = model.getuValueList().size() + 2;
        // 计算u图ks
        for (int i = 0; i < model.getuValueList().size(); i++) {
            double val = Math.abs((i / (double)base) - model.getuValueList().get(i));
            if (val > model.getuKS()) {
                model.setuKS(val);
                model.setUksxIndex(i);
            }
        }
        // 计算y图ks
        for (int i = 0; i < model.getyValueList().size(); i++) {
            double val = Math.abs((i / (double)base) - model.getyValueList().get(i));
            if (val > model.getyKS()) {
                model.setyKS(val);
                model.setYksxIndex(i);
            }
        }
    }

    public static double plrEvaluate(Model a, Model b) {
        double plra = 1;
        double plrb = 1;

        for (int i = 0; i < a.getTestDataset().size(); i++) {
            plra *= a.calculateProbabilityDensity(a.getTestDataset().get(i));
            plrb *= b.calculateProbabilityDensity(b.getTestDataset().get(i));
            System.out.println(plra + " : " + plrb);
        }
        System.out.println("pl model a : " + plra);
        System.out.println("pl model b : " + plrb);
        return plra / plrb;
    }

}
