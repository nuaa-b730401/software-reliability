package org.nuaa.b730401.softwarereliability.core.util;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/11/28 23:36
 */
public class Sort {
    public static void main(String[] args) throws IOException {
        FileInputStream input = new FileInputStream(Sort.class.getResource("/").getPath() + "/failure_count.txt");
        FileWriter writer = new FileWriter(Sort.class.getResource("/").getPath() + "/failure_count_sorted.txt");
        Scanner scanner = new Scanner(input);
        List<Double> list = new ArrayList<>();
        while (scanner.hasNext()) {
            scanner.nextInt();
            list.add(scanner.nextDouble());
        }
        Collections.sort(list);
        int i = 1;
        for (Double in : list) {
            writer.write(i + " " + in);
            writer.write("\n");
            i++;
        }
        scanner.close();
        input.close();
        writer.close();
    }
}
