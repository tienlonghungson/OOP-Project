package chart.charts;

import chart.charts.Chart;
import chart.data.Data;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class HN30INDEXChart extends Chart {
    @Override
    protected void buildVolumeChart(BarChart<String, Number> volumeChart, List<Data> datum) {
        super.buildVolumeChart(volumeChart, datum);
        volumeChart.setTitle("HN30-INDEX");
        volumeChart.setPrefSize(500, 400);
    }

    @Override
    public Pane buildChart() {
        VBox vBox = new VBox();
        List<Data> datum = new ArrayList<>();
        BarChart<String, Number> volumeChart = new BarChart<>(new CategoryAxis(), new NumberAxis());

        buildData("HNX30-INDEX", datum);
        buildVolumeChart(volumeChart, datum);

        vBox.getChildren().add(volumeChart);
        return vBox;
    }
}
