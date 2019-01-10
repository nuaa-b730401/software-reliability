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
import org.nuaa.b730401.softwarereliability.core.bean.Model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/11/27 20:48
 */
public class EvaluateGraph extends ApplicationFrame{
    public EvaluateGraph(String title, String chartTitle, Model model) {
        super(title);

        JFreeChart chart = ChartFactory.createXYLineChart(chartTitle,
                "", "", createDataset(model),
                PlotOrientation.VERTICAL, true, true, false
                );

        ChartPanel panel = new ChartPanel(chart);
        panel.setPreferredSize(new Dimension(1400, 1200));
        XYPlot plot = chart.getXYPlot();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.GREEN);
        renderer.setSeriesStroke( 0, new BasicStroke(2.0f));
        renderer.setSeriesPaint(1, Color.ORANGE);
        renderer.setSeriesStroke( 1, new BasicStroke(2.0f));
        renderer.setSeriesPaint(2, Color.RED);
        renderer.setSeriesStroke( 2, new BasicStroke(2.0f));
        renderer.setSeriesPaint(3, Color.CYAN);
        renderer.setSeriesStroke( 3, new BasicStroke(2.0f));
        renderer.setSeriesPaint(4, Color.MAGENTA);
        renderer.setSeriesStroke( 4, new BasicStroke(2.0f));
        plot.setRenderer(renderer);
        setContentPane(panel);
    }

    /**
     * 创建数据
     * @param model
     * @return
     */
    public XYDataset createDataset(Model model) {
        XYSeriesCollection dataset = new XYSeriesCollection();
        // U-图数据
        XYSeries uSeries = new XYSeries("U-graph");
        List<Double> yAxisValueList = new ArrayList<>();
        for (int i = 0; i < model.getuValueList().size(); i++) {
            yAxisValueList.add(i / (double)(model.getuValueList().size() + 2));
        }
        for (int i = 0; i < model.getuValueList().size() - 1; i++) {
            uSeries.add(model.getuValueList().get(i),
                    yAxisValueList.get(i));
            uSeries.add(model.getuValueList().get(i),
                    yAxisValueList.get(i + 1));
        }
        uSeries.add(model.getuValueList().get(model.getuValueList().size() - 1),
                yAxisValueList.get(model.getuValueList().size() - 1));

        // y=x
        XYSeries oneSeries = new XYSeries("k=1");
        for (int i = 0; i < model.getuValueList().size(); i++) {
            oneSeries.add(model.getuValueList().get(i),
                    model.getuValueList().get(i));
        }
        yAxisValueList = new ArrayList<>();
        for (int i = 0; i < model.getyValueList().size(); i++) {
            yAxisValueList.add(i / (double)(model.getyValueList().size() + 2));
        }

        // K-S(U-graph)
        XYSeries ksuSeries = new XYSeries("K-S(U-graph) : " + String.format("%.2f", model.getuKS()));
        ksuSeries.add(model.getuValueList().get(model.getUksxIndex()),
                yAxisValueList.get(model.getUksxIndex()));
        ksuSeries.add(model.getuValueList().get(model.getUksxIndex()),
                model.getuValueList().get(model.getUksxIndex()));

        // Y-图
        XYSeries ySeries = new XYSeries("Y-graph");
        for (int i = 0; i < model.getyValueList().size() - 1; i++) {
            ySeries.add(model.getyValueList().get(i),
                    yAxisValueList.get(i));
            ySeries.add(model.getyValueList().get(i),
                    yAxisValueList.get(i + 1));
        }
        ySeries.add(model.getyValueList().get(model.getyValueList().size() - 1),
                yAxisValueList.get(model.getyValueList().size() - 1));

        // K-S(Y-graph)
        XYSeries ksySeries = new XYSeries("K-S(Y-graph) : " + String.format("%.2f", model.getyKS()));
        ksySeries.add(model.getyValueList().get(model.getYksxIndex()),
                yAxisValueList.get(model.getYksxIndex()));
        ksySeries.add(model.getyValueList().get(model.getYksxIndex()),
                model.getyValueList().get(model.getYksxIndex()));

        // add all series to dataset
        dataset.addSeries(uSeries);
        dataset.addSeries(oneSeries);
        dataset.addSeries(ySeries);
        dataset.addSeries(ksuSeries);
        dataset.addSeries(ksySeries);
        return dataset;
    }
}
