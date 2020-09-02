package chart.charts;

import chart.data.Data;
import chart.inputchart.InformationChart;
import input.Session;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Pane;

import java.util.List;

public abstract class Chart {
    public Chart(){}

    protected void buildData(String nameIndex, List<Data> datum){
        int numberOfRow = Math.min(InformationChart.getTotalRow(nameIndex)-1, 23);
        Session session ;
        StringBuilder temp = new StringBuilder();
        String date  ;
        long volume;
        for(int i=numberOfRow; i>=3;i--){
        	try {
            session = InformationChart.getRow(nameIndex, i);

            date = temp.append(session.getDay()).substring(0,5);
            temp = temp.delete(0, temp.length());

            volume= InformationChart.convertStringToLong(session.getMatchingTradeWeight());

            Data bar = new Data(date, volume);
            datum.add(bar);
        	} catch(Exception e){
        		e.printStackTrace();
        	}
        }
    }



    protected void buildVolumeChart(BarChart<String, Number> volumeChart, List<Data> datum){
        volumeChart.setPrefSize(500,250);
    	volumeChart.getXAxis().setLabel("Time (Days)");
        volumeChart.getYAxis().setLabel("Volume (Million Share)");
        volumeChart.setLegendVisible(false);
        volumeChart.setBarGap(0.5);
        volumeChart.getStylesheets().add("/chart/charts/BarChart.css");
        volumeChart.setVerticalGridLinesVisible(false);

        volumeChart.getYAxis().setAutoRanging(true);
        if(volumeChart.getYAxis() instanceof NumberAxis){
            ((NumberAxis) volumeChart.getYAxis()).forceZeroInRangeProperty().setValue(Boolean.FALSE);
        }

        XYChart.Series<String,Number> series = new XYChart.Series<>();
        for(Data data : datum){
            series.getData().add(new XYChart.Data<>(data.getDateTime(), data.getVolume()/1000000));
        }
        ObservableList<XYChart.Series<String,Number>> dataSeries = FXCollections.observableArrayList(series);
        volumeChart.setData(dataSeries);
    }

    public abstract Pane buildChart();
}
