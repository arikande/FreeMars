package org.freechart.line;

/**
 *
 * @author Deniz ARIKAN
 */
public class LineChartValue implements Comparable<LineChartValue> {

    private final int xValue;
    private final int yValue;

    public LineChartValue(int xValue, int yValue) {
        this.xValue = xValue;
        this.yValue = yValue;
    }

    public int compareTo(LineChartValue lineChartValue) {
        if (getxValue() < lineChartValue.getxValue()) {
            return -1;
        } else if (getxValue() == lineChartValue.getxValue()) {
            return 0;
        } else {
            return 1;
        }
    }

    public int getxValue() {
        return xValue;
    }

    public int getyValue() {
        return yValue;
    }
}
