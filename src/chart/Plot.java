package chart;

import chart.charts.*;
import javafx.scene.layout.Pane;

import java.util.HashMap;
import java.util.Map;

public class Plot {

    private Map<String, Chart> myCharts = new HashMap<>();

    public Plot(){
        myCharts.put("VN-INDEX", new VNINDEXChart());
        myCharts.put("VN30-INDEX", new VN30INDEXChart());
        myCharts.put("UPCOM-INDEX", new UPCOMINDEXChart());
        myCharts.put("HNX-INDEX", new HNXINDEXChart());
        myCharts.put("HNX30-INDEX", new HN30INDEXChart());
    }

    public Pane drawChart(String nameIndex){
        Pane pane = new Pane();
        pane = myCharts.get(nameIndex).buildChart();
        
        return pane;
    }

}
