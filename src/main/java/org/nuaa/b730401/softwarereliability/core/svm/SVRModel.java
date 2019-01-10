package org.nuaa.b730401.softwarereliability.core.svm;

import java.io.IOException;
import java.util.function.Function;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/12/15 11:09
 */
public class SVRModel {
    /**
     * 训练集路径
     */
    private String trainFilePath = this.getClass().getResource("/").getPath() + "train_data";
    /**
     * 测试集路径
     */
    private String testFilePath = this.getClass().getResource("/").getPath() + "test_data";
    /**
     * 模型名称
     */
    private String modelFilePath = this.getClass().getResource("/").getPath() + "model";

    /**
     * 测试结果输出
     */
    private String testResultPath = this.getClass().getResource("/").getPath() + "output";
    /**
     * 训练参数集合，用于调参
     */
    private String[] trainParamStringArray = new String[]{
        "-s", "3",
        "-t", "1",
        "-d", "3",
        "-e", "0.001",
        trainFilePath,
        modelFilePath
    };

    /**
     * 测试参数集合
     */
    private String[] testParamStringArray = new String[] {
        testFilePath,
        modelFilePath,
        testResultPath
    };

    public void train() throws IOException {
        svm_train.main(trainParamStringArray);
    }

    public void test() throws IOException {
        svm_predict.main(testParamStringArray);
    }

    public double execute(String path) throws IOException {
        svm_predict.main(
                new String[]{
                        path,
                        modelFilePath,
                        getClass().getResource("/").getPath() + "predict.data"
                }
        );
        return 0;
    }

    public void generateDataset() throws IOException {
        Function<Double, Double> function = x -> x*x*x + x*x + x + 1;
        new DatasetBuild.NormalFunctionDataBuild().write(function, trainFilePath, 210);
        new DatasetBuild.NormalFunctionDataBuild().write(function, testFilePath, 90);
    }
}
