package chart.inputchart;

import input.Session;

public class SessionCandleChart extends Session {
    private float highPrice;
    private float lowPrice;

    /**
     * getter and setter
     */
    public void setLowPrice(float lowPrice) {
        this.lowPrice = lowPrice;
    }
    public void setHighPrice(float highPrice) {
        this.highPrice = highPrice;
    }

    public float getHighPrice() {
        return highPrice;
    }
    public float getLowPrice() {
        return lowPrice;
    }
}
