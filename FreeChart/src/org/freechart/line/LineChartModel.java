package org.freechart.line;

import java.awt.Font;
import java.util.Iterator;
import java.util.Vector;

/**
 *
 * @author Deniz ARIKAN
 */
public class LineChartModel {

    private static final int DEFAULT_X_OFFSET = 45;
    private int maximumYValue;
    private int minimumYValue;
    private int yDivisionCount;
    private int maximumXValue;
    private int minimumXValue;
    private int xDivisionCount;
    private int xOffset;
    private String xAxisName;
    private String yAxisName;
    private Font axisNameFont;
    private final Vector<LineChartValue> values;

    public LineChartModel() {
        xOffset = DEFAULT_X_OFFSET;
        values = new Vector<LineChartValue>();
    }

    public void addValue(int x, int y) {
        values.add(new LineChartValue(x, y));
    }

    public Iterator<LineChartValue> getValuesIterator() {
        return values.iterator();
    }

    public int getMaximumYValue() {
        return maximumYValue;
    }

    public void setMaximumYValue(int maximumYValue) {
        this.maximumYValue = maximumYValue;
    }

    public int getMinimumYValue() {
        return minimumYValue;
    }

    public void setMinimumYValue(int minimumYValue) {
        this.minimumYValue = minimumYValue;
    }

    public int getMaximumXValue() {
        return maximumXValue;
    }

    public void setMaximumXValue(int maximumXValue) {
        this.maximumXValue = maximumXValue;
    }

    public int getMinimumXValue() {
        return minimumXValue;
    }

    public void setMinimumXValue(int minimumXValue) {
        this.minimumXValue = minimumXValue;
    }

    public int getYDivisionCount() {
        return yDivisionCount;
    }

    public void setYDivisionCount(int yDivisionCount) {
        this.yDivisionCount = yDivisionCount;
    }

    public int getXDivisionCount() {
        return xDivisionCount;
    }

    public void setXDivisionCount(int xDivisionCount) {
        this.xDivisionCount = xDivisionCount;
    }

    public String getXAxisName() {
        return xAxisName;
    }

    public void setXAxisName(String xAxisName) {
        this.xAxisName = xAxisName;
    }

    public String getYAxisName() {
        return yAxisName;
    }

    public void setYAxisName(String yAxisName) {
        this.yAxisName = yAxisName;
    }

    public Font getAxisNameFont() {
        return axisNameFont;
    }

    public void setAxisNameFont(Font axisNameFont) {
        this.axisNameFont = axisNameFont;
    }

    public int getXOffset() {
        return xOffset;
    }

    public void setXOffset(int xOffset) {
        this.xOffset = xOffset;
    }
}
