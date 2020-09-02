package chart.data;

public class Data {
    protected String dateTime;
    protected long volume ;

    public Data(){}

    public Data(String dateTime, long volume){
        setDateTime(dateTime);
        setVolume(volume);
    }

    /**
     * getter and setter
     * @return
     */
    public String getDateTime() {
        return dateTime;
    }
    public long getVolume() {
        return volume;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
    public void setVolume(long volume) {
        this.volume = volume;
    }
}
