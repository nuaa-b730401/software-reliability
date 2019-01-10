package org.nuaa.b730401.softwarereliability.core.svm;

import org.nuaa.b730401.softwarereliability.core.ui.SVRResultGraph;

import java.io.IOException;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/12/15 12:39
 */
public class App {
    public static void main(String[] args) throws IOException {
        // generate train data
//        DatasetBuild datasetBuild = new DatasetBuild.NormalFunctionDataBuild();
//        Function<Double, Double> function = x -> x * x;
//        datasetBuild.write(function, App.class.getClass().getResource("/").getPath() + "train.data", 210);
//         generate test data
//        datasetBuild.write(function, App.class.getClass().getResource("/").getPath() + "test.data", 90);
//        new SVRModel().generateDataset();
//        new SVRModel().train();
//        new SVRModel().test();
        SVRResultGraph graph = new SVRResultGraph("SVR", "SVR");
        graph.pack();
        graph.setVisible(true);
//        FileWriter writer = new FileWriter(App.class.getResource("/").getPath() + "input.data");
//        for (int i = -500; i <= 500; i++) {
//            writer.write( (i*i*i + i*i + i + 1)+ " 1:" + i + "\n");
//        }
//        writer.close();
//        new SVRModel().execute(App.class.getResource("/").getPath() + "input.data");
    }
}
