package chart.data;

import java.math.BigDecimal;

/**
 *
 * represent the data in one period ( a day, a time,... )
 */
public class BarData extends Data /*implements Serializable */{

    protected double start;
    protected double end;
    protected double high;
    protected double low;
    protected double average ;


    public BarData() {
        super();
    }

    public BarData( String dateTime, double open, double high, double low, double close, long volume) {
        super(dateTime, volume);
        setStart(open);
        setEnd(close);
        setLow(low);
        setHigh(high);
        setAverage((open+close+high+low)/4);
    }

    /**
     * getter and setter
     */
    public double getStart() {
        return start;
    }
    public double getHigh() {
        return high;
    }
    public double getLow() {
        return low;
    }
    public double getEnd() {
        return end;
    }
    public double getAverage(){return average;}

    public void setStart(double start) {
        this.start = start;
    }
    public void setHigh(double high) {
        this.high = high;
    }
    public void setLow(double low) {
        this.low = low;
    }
    public void setEnd(double end) {
        this.end = end;
    }
    public void setAverage(double average) {
        this.average = average;
    }


    /**
     * Updates the last price, adjusting the high and low
     * @param close The last price
     */
    public void update( double close ) {
        if( close > high ) {
            high = close;
        }
        
        if( close < low ) {
            low = close;
        }
        this.end = close;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(" Date: ").append(dateTime);
        sb.append(" Open: ").append(start);
        sb.append(" High: ").append(high);
        sb.append(" Low: ").append(low);
        sb.append(" Close: ").append(end);
        sb.append(" Volume: ").append(volume);

        return sb.toString();
    }
}
