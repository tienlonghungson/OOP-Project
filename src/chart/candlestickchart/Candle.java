package chart.candlestickchart;

import javafx.scene.Group;
import javafx.scene.layout.Region;
import javafx.scene.shape.Line;

/**
 * Candle node used for drawing a candle
 */
class Candle extends Group {

    private Line highLowLine = new Line();
    private Region bar = new Region();
    private String seriesStyleClass;
    private String dataStyleClass;
    private boolean openAboveClose = true;

    Candle(String seriesStyleClass, String dataStyleClass) {
        setAutoSizeChildren(false);
        getChildren().addAll(highLowLine, bar);

        setSeriesAndDataStyleClasses(seriesStyleClass, dataStyleClass);
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

    private void updateStyleClasses() {
        getStyleClass().setAll("candlestick-candle", seriesStyleClass, dataStyleClass);
        highLowLine.getStyleClass().setAll("candlestick-line", seriesStyleClass, dataStyleClass,
                openAboveClose ? "open-above-close" : "close-above-open");
        bar.getStyleClass().setAll("candlestick-bar", seriesStyleClass, dataStyleClass,
                openAboveClose ? "open-above-close" : "close-above-open");
    }
}
