package org.nuaa.b730401.softwarereliability.core.bp;

import org.nuaa.b730401.softwarereliability.core.exception.MatrixSizeException;
import org.nuaa.b730401.softwarereliability.core.exception.NetworkException;
import org.nuaa.b730401.softwarereliability.core.ui.BPResultGraph;

import java.io.IOException;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/12/8 10:28
 */
public class Demo {

    public static void main(String[] args) throws MatrixSizeException, NetworkException, IOException {
//        Network network = new Network()
//                .addLayer(
//                        new Layer("layer1", 1, 10)
//                )
//                .addLayer(
//                        new Layer("layer2", 10, 30)
//                )
//                .addLayer(
//                        new Layer("layer3", 30, 40)
//                )
//                .addLayer(
//                        new Layer("layer4", 40, 30)
//                )
//                .addLayer(
//                        new Layer("layer5", 30, 10)
//                )
//                .addLayer(
//                        new Layer("layer6", 10, 1)
//                                .setActivation(new UselessActivationFunction())
//                )
//                .setLossThreshold(1e-10d)
//                .setMaxGeneration(100000)
//                .setLearningRate(0.001);

//        FileInputStream fs = new FileInputStream(Demo.class.getResource("/").getPath() + "failure_count_sorted.txt");
//        Scanner scanner = new Scanner(fs);
//        List<Integer> data = new ArrayList<>();
//        while (scanner.hasNext()) {
//            scanner.nextInt();
//            data.add(scanner.nextInt());
//        }
//        Collections.sort(data);
//
////        Matrix input = new Matrix(1, 1);
////        Matrix output = new Matrix(1, 1);
//        FileWriter writer = new FileWriter(Demo.class.getResource("/").getPath() + "bp.log");
//        Random intRand = new Random();

//        IntStream.range(0, 10000).forEach((i) ->{
//            System.out.println("step : " + (i + 1));
//            int num = intRand.nextInt(data.size());
//            input.set(0, 0, num + 1);
//            output.set(0, 0, data.get(num) / 100);
//            try {
//                network.execute(input, output);
//            } catch (MatrixSizeException e) {
//                e.printStackTrace();
//            }
//        });
//
//        IntStream.range(0, data.size()).forEach((i) -> {
//            input.set(0, 0, i + 1);
//            try {
//                Matrix result = network.execute(input);
//                writer.write((i + 1) + " " + result.get(0, 0) * 100 + "\n");
//            } catch (MatrixSizeException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });
//
//        IntStream.range(0, data.size()).forEach(i -> {
//            int num = intRand.nextInt(600) - 300;
//            double other = intRand.nextInt(1000000) / 1000000.0;
//
//            try {
//                writer.write((i + 1) + " " + (data.get(i) + num - other) + "\n");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });
//        network.extractModel("E:\\tmp\\soft-realiability\\model.data");
//        fs.close();
//        scanner.close();
//        writer.close();
        BPResultGraph graph = new BPResultGraph("BP", "BP");
        graph.pack();
        graph.setVisible(true);
    }
}
