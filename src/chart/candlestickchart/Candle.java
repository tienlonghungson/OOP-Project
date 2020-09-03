package chart.candlestickchart;

import javafx.scene.Group;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Region;
import javafx.scene.shape.Line;

/**
 * Candle node used for drawing a candle
 */
class Candle extends Group {

    /**
     * highLowLine displays the line of low and high value
     * bar displays the rectangle of open and close value
     * tooltip displays the information ( data ) of one candle when mouse enter candle zone
     */
    private Line highLowLine = new Line();
    private Region bar = new Region();
    private String seriesStyleClass;
    private String dataStyleClass;
    private boolean openAboveClose = true;
    private Tooltip tooltip = new Tooltip();

    Candle(String seriesStyleClass, String dataStyleClass) {
        setAutoSizeChildren(false);
        getChildren().addAll(highLowLine, bar);

        setSeriesAndDataStyleClasses(seriesStyleClass, dataStyleClass);

        tooltip.setGraphic(new TooltipContent());
        Tooltip.install(bar, tooltip);
    }

    public void setSeriesAndDataStyleClasses(String seriesStyleClass, String dataStyleClass) {
        this.seriesStyleClass = seriesStyleClass;
        this.dataStyleClass = dataStyleClass;
        updateStyleClasses();
    }

    public void update(double closeOffset, double highOffset, double lowOffset, double candleWidth) {
        openAboveClose = closeOffset > 0;
        updateStyleClasses();
        highLowLine.setStartY(highOffset);
        highLowLine.setEndY(lowOffset);

        if (openAboveClose) {
            bar.resizeRelocate(-candleWidth / 2, 0, candleWidth, closeOffset);
        } else {
            bar.resizeRelocate(-candleWidth / 2, closeOffset, candleWidth, closeOffset * -1);
        }
    }

    public void updateTooltip(double open, double close, double high, double low) {
        TooltipContent tooltipContent = (TooltipContent) tooltip.getGraphic();
        tooltipContent.update(open, close, high, low);
//                    tooltip.setText("Open: "+open+"\nClose: "+close+"\nHigh: "+high+"\nLow: "+low);
    }

    private void updateStyleClasses() {
        getStyleClass().setAll("candlestick-candle", seriesStyleClass, dataStyleClass);
        highLowLine.getStyleClass().setAll("candlestick-line", seriesStyleClass, dataStyleClass,
                openAboveClose ? "open-above-close" : "close-above-open");
        bar.getStyleClass().setAll("candlestick-bar", seriesStyleClass, dataStyleClass,
                openAboveClose ? "open-above-close" : "close-above-open");
    }

}
