package org.nuaa.b730401.softwarereliability.core.svm;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.IntStream;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/12/15 11:29
 */
@FunctionalInterface
public interface DatasetBuild<T, R> {
    /**
     * 输出
     * @param function
     * @param path
     * @param count
     * @throws IOException
     */
    void write(Function<T, R> function, String path, int count) throws IOException;



    /**
     * 一维函数数据集的构造
     */
    public class NormalFunctionDataBuild implements DatasetBuild<Double, Double>{
        /**
         * 数据上界
         */
        private int maxLimit = 100;
        /**
         * 数据下界
         */
        private int minLimit = -100;

        @Override
        public void write(Function<Double, Double> function, String path, int count) throws IOException {
            FileWriter writer = new FileWriter(path);

            Random random = new Random();
            Set<Integer> cache = new HashSet<>();

            IntStream.range(0, count).forEach(
                    (i)-> {
                        int num = random.nextInt(maxLimit - minLimit) + minLimit;
                        if (!cache.contains(num)) {
                            cache.add(num);
                            try {
                                writer.write(function.apply((double) num) + " 1:" + num + "\n");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
            );
            writer.close();
        }
    }

    public class RealiabilityDataBuild {
        public void write() throws IOException {
            FileInputStream inputStream = new FileInputStream(this.getClass().getResource("/").getPath() + "");
            FileWriter writer = new FileWriter(this.getClass().getResource("/").getPath() + "rea_train.data");
        }
    }
}
