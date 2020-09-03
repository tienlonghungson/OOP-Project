# Candlestick Chart Code

Class CandlestickChart is a customed chart which was inheriated from XYChart.
Recall that XYChart is an abstract class, so to fullfill the CandlestickChart class, the code must explicitly implement the abastract methods from XYChart, which are: 
+ protected void layoutPlotChildren(): determine the coordinate of each data (candle ) in each series, send value to the tooltip content, determine seriesPath ( average line)
+ protected void dataItemChanged(Data<String, Number> item), protected void dataItemAdded(Series<String, Number> series, int itemIndex, Data<String, Number> item), protected void dataItemRemoved(Data<String, Number> item, Series<String, Number> series): tell the PlotChildren that a specified series has changed data, or add, or move data, so the code might be doing : getPlotChildren().add(candle), getPlotChildren().remove(candle);
+ protected void seriesAdded(Series<String, Number> series, int seriesIndex), protected void seriesRemoved(Series<String, Number> series) : like 3 above method, but this time is the whole series is added or remove, so the code have to iterate all over the series

Beside, we need some auxiliary class to support :
+ CandleStickExtraValues: object from this class stores value of a candle
+ TooltipContent : display the value of each candle in the chart.
+ Candle : tell how to layout a candle ( including TooltipContent) with datum: preprocessed coordinate from method layoutPlotChildren(), value CandleStickExtraValue( send to TooltipContent)
