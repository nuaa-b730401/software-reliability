package org.nuaa.b730401.softwarereliability.core.ui;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/12/15 15:12
 */
public class BPResultGraph extends ApplicationFrame {
    public BPResultGraph(String title, String chartTitle) throws IOException {
        super(title);

        JFreeChart chart = ChartFactory.createXYLineChart(chartTitle,
                "", "", createDataset(),
                PlotOrientation.VERTICAL, true, true, false
        );

        ChartPanel panel = new ChartPanel(chart);
        panel.setPreferredSize(new Dimension(1400, 1200));
        XYPlot plot = chart.getXYPlot();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.GREEN);
        renderer.setSeriesStroke( 0, new BasicStroke(2.0f));
        renderer.setSeriesPaint(1, Color.RED);
        renderer.setSeriesStroke( 1, new BasicStroke(2.0f));
        plot.setRenderer(renderer);
        setContentPane(panel);
    }

    public XYDataset createDataset() throws IOException {
        XYSeriesCollection dataset = new XYSeriesCollection();

        // 添加原始数据
        XYSeries originSeries = new XYSeries("origin data");
        FileInputStream inputStream = new FileInputStream(this.getClass().getResource("/").getPath() + "failure_count_sorted.txt");
        Scanner scanner = new Scanner(inputStream);
        while (scanner.hasNext()) {
            originSeries.add(scanner.nextInt(), scanner.nextInt());
        }
        dataset.addSeries(originSeries);
        inputStream.close();
        scanner.close();

        // 添加预测数据
        XYSeries predictSeries = new XYSeries("predict data");
        inputStream = new FileInputStream(this.getClass().getResource("/").getPath() + "bp.log");
        scanner = new Scanner(inputStream);
        while (scanner.hasNext()) {
            predictSeries.add(scanner.nextDouble(), scanner.nextDouble());
        }
        dataset.addSeries(predictSeries);
        inputStream.close();
        scanner.close();
        return dataset;
    }
}
