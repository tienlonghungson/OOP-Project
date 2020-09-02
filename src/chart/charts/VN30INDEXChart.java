package chart.charts;

import chart.candlestickchart.CandleStickChart;
import chart.candlestickchart.CandleStickExtraValues;
import chart.data.BarData;
import chart.data.Data;
import chart.inputchart.InformationChart;
import chart.inputchart.SessionCandleChart;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class VN30INDEXChart extends Chart {
    @Override
    protected void buildData(String nameIndex, List<Data> datum) {
        int numberOfRow = Math.min(InformationChart.getTotalRow(nameIndex)-1, 23);
        SessionCandleChart session ;
        StringBuilder temp = new StringBuilder();
        String date  ;
        double start, end, high, low;
        long volume;
        for(int i=numberOfRow; i>=3;i--){
            session = InformationChart.getCandleRow(nameIndex, i);

            date = temp.append(session.getDay()).substring(0,5);
            temp = temp.delete(0, temp.length());

            start = session.getStartPrice();
            end = session.getPrice();
            high = session.getHighPrice();
            low = session.getLowPrice();
            volume= InformationChart.convertStringToLong(session.getMatchingTradeWeight());

            BarData data = new BarData(date, start, high, low, end, volume);
            datum.add(data);
        }
    }

    @Override
    protected void buildVolumeChart(BarChart<String, Number> volumeChart, List<Data> datum) {
        super.buildVolumeChart(volumeChart, datum);
    }

    protected void buildCandlestickChart(CandleStickChart candleStickChart, List<Data> datum){
        candleStickChart.setTitle("VN30-INDEX");
        candleStickChart.getXAxis().setLabel("Time (Days)");
        candleStickChart.getYAxis().setLabel("Price");
        XYChart.Series<String,Number> series = new XYChart.Series<>();
        for(Data data : datum){
            if( data instanceof BarData) {
                series.getData().add(new XYChart.Data<>(data.getDateTime(),((BarData) data).getStart(), new CandleStickExtraValues(((BarData) data).getEnd(), ((BarData) data).getHigh(), ((BarData) data).getLow(), ((BarData) data).getAverage())));
            }
        }
        ObservableList<XYChart.Series<String,Number>> dataSeries = FXCollections.observableArrayList(series);
        candleStickChart.setData(dataSeries);
        candleStickChart.setLegendVisible(false);
    }

    @Override
    public Pane buildChart() {
        VBox vBox = new VBox();
        List<Data> datum = new ArrayList<>();
        CandleStickChart candleStickChart = new CandleStickChart(new CategoryAxis(), new NumberAxis());
        BarChart<String, Number> volumeChart = new BarChart<>(new CategoryAxis(), new NumberAxis());

        buildData("VN30-INDEX", datum);
        buildVolumeChart(volumeChart, datum);
        buildCandlestickChart(candleStickChart, datum);

        vBox.getChildren().addAll(candleStickChart, volumeChart);
        return vBox;
    }
}
