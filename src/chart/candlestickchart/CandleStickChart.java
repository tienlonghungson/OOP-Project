package chart.candlestickchart;

import javafx.scene.chart.XYChart;
import javafx.scene.chart.Axis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.Node;

import javafx.collections.ObservableList;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

import javafx.util.Duration;
import javafx.event.ActionEvent;
import javafx.animation.FadeTransition;

public class CandleStickChart extends XYChart<String, Number> {

    // -------------- CONSTRUCTORS ----------------------------------------------
    /**
     * Construct a new CandleStickChart with the given axis.
     *
     * @param xAxis The x axis to use
     * @param yAxis The y axis to use
     */
    public CandleStickChart(Axis<String> xAxis, Axis<Number> yAxis) {
        super(xAxis, yAxis);
        yAxis.autoRangingProperty().set(true);
        if(yAxis instanceof NumberAxis){
            ((NumberAxis) yAxis).forceZeroInRangeProperty().setValue(Boolean.FALSE);
        }
        setAnimated(false);
        xAxis.setAnimated(false);
        yAxis.setAnimated(false);
        verticalGridLinesVisibleProperty().set(false);
//        horizontalGridLinesVisibleProperty().set(false);
        getStylesheets().add(getClass().getResource("CandleStickChartStyles.css").toExternalForm());
        setLegendVisible(true);
    }

    /**
     * Construct a new CandleStickChart with the given axis and data.
     *
     * @param xAxis The x axis to use
     * @param yAxis The y axis to use
     * @param data The data to use, this is the actual list used so any
     * changes to it will be reflected in the chart
     */
    public CandleStickChart(Axis<String> xAxis, Axis<Number> yAxis, ObservableList<Series<String, Number>> data) {
        this(xAxis, yAxis);
        setData(data);
    }

    // -------------- METHODS ------------------------------------------------------------------------------------------
    /**
     * Called to update and layout the content for the plot
     */
    @Override
    protected void layoutPlotChildren() {
        // we have nothing to layout if no data is present
        if (getData() == null) {
            return;
        }
        // update candle positions
        for (int seriesIndex = 0; seriesIndex < getData().size(); seriesIndex++) {
            Series<String, Number> series = getData().get(seriesIndex);
            Iterator<Data<String, Number>> iTer = getDisplayedDataIterator(series);
            Path seriesPath = null;
            if (series.getNode() instanceof Path) {
                seriesPath = (Path) series.getNode();
                seriesPath.getElements().clear();
            }
            while (iTer.hasNext()) {
                Data<String, Number> item = iTer.next();
                double x = getXAxis().getDisplayPosition(getCurrentDisplayedXValue(item));
                double y = getYAxis().getDisplayPosition(getCurrentDisplayedYValue(item));
                Node itemNode = item.getNode();
                CandleStickExtraValues extra = (CandleStickExtraValues) item.getExtraValue();
                if (itemNode instanceof Candle && extra != null) {
                    Candle candle = (Candle) itemNode;

                    double close = getYAxis().getDisplayPosition(extra.getClose());
                    double high = getYAxis().getDisplayPosition(extra.getHigh());
                    double low = getYAxis().getDisplayPosition(extra.getLow());
                    // calculate candle width
                    double candleWidth = 10;

                    // update candle
                    candle.update(close - y, high - y, low - y, candleWidth);
                    // position the candle
                    candle.setLayoutX(x);
                    candle.setLayoutY(y);
                }
                if (seriesPath != null) {
                    if (seriesPath.getElements().isEmpty()) {
                        seriesPath.getElements().add(new MoveTo(x, getYAxis().getDisplayPosition(extra.getAverage())));
                    } else {
                        seriesPath.getElements().add(new LineTo(x, getYAxis().getDisplayPosition(extra.getAverage())));
                    }
                }
            }
        }
    }

    @Override
    protected void dataItemChanged(Data<String, Number> item) {
    }

    @Override
    protected void dataItemAdded(Series<String, Number> series, int itemIndex, Data<String, Number> item) {
        Node candle = createCandle(getData().indexOf(series), item, itemIndex);
        getPlotChildren().add(candle);
      
        // always draw average line on top
        if (series.getNode() != null) {
            series.getNode().toFront();
        }
    }

    @Override
    protected void dataItemRemoved(Data<String, Number> item, Series<String, Number> series) {
        final Node candle = item.getNode();
            getPlotChildren().remove(candle);
        
    }

    @Override
    protected void seriesAdded(Series<String, Number> series, int seriesIndex) {
        // handle any data already in series
        for (int j = 0; j < series.getData().size(); j++) {
            Data item = series.getData().get(j);
            Node candle = createCandle(seriesIndex, item, j);      
            getPlotChildren().add(candle);
         
        }
        // create series path
        Path seriesPath = new Path();
        seriesPath.getStyleClass().setAll("candlestick-average-line", "series" + seriesIndex);
        series.setNode(seriesPath);
        getPlotChildren().add(seriesPath);
    }

    @Override
    protected void seriesRemoved(Series<String, Number> series) {
        // remove all candle nodes
        for (XYChart.Data<String, Number> d : series.getData()) {
            final Node candle = d.getNode();
                getPlotChildren().remove(candle);
        }
    }

    /**
     * Create a new Candle node to represent a single data item
     *
     * @param seriesIndex The index of the series the data item is in
     * @param item The data item to create node for
     * @param itemIndex The index of the data item in the series
     * @return New candle node to represent the give data item
     */
    private Node createCandle(int seriesIndex, final Data item, int itemIndex) {
        Node candle = item.getNode();
        // check if candle has already been created
        if (candle instanceof Candle) {
            ((Candle) candle).setSeriesAndDataStyleClasses("series" + seriesIndex, "data" + itemIndex);
        } else {
            candle = new Candle("series" + seriesIndex, "data" + itemIndex);
            item.setNode(candle);
        }
        return candle;
    }

    /**
     * This is called when the range has been invalidated and we need to
     * update it. If the axis are auto ranging then we compile a list of all
     * data that the given axis has to plot and call invalidateRange() on
     * the axis passing it that data.
     */
    @Override
    protected void updateAxisRange() {
        // For candle stick chart we need to override this method as we need to let the axis know that they need to be able
        // to cover the whole area occupied by the high to low range not just its center data value
        final Axis<String> xa = getXAxis();
        final Axis<Number> ya = getYAxis();
        List<String> xData = null;
        List<Number> yData = null;
        if (xa.isAutoRanging()) {
            xData = new ArrayList<>();
        }
        if (ya.isAutoRanging()) {
            yData = new ArrayList<>();
        }
        if (xData != null || yData != null) {
            for (Series<String, Number> series : getData()) {
                for (Data<String, Number> data : series.getData()) {
                    if (xData != null) {
                        xData.add(data.getXValue());
                    }
                    if (yData != null) {
                        CandleStickExtraValues extras = (CandleStickExtraValues) data.getExtraValue();
                        if (extras != null) {
                            yData.add(extras.getHigh());
                            yData.add(extras.getLow());
                        } else {
                            yData.add(data.getYValue());
                        }
                    }
                }
            }
            if (xData != null) {
                xa.invalidateRange(xData);
            }
            if (yData != null) {
                ya.invalidateRange(yData);
            }
        }
    }
}