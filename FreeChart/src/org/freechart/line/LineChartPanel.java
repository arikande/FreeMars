package org.freechart.line;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Iterator;
import javax.swing.JPanel;

/**
 *
 * @author Deniz ARIKAN
 */
public class LineChartPanel extends JPanel {

    private final int gutter = 45;
    private final Color lineColor;
    private final LineChartModel model;

    public LineChartPanel(LineChartModel model) {
        this.model = model;
        lineColor = Color.blue;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        Color originalColor = g2d.getColor();
        Font originalFont = g2d.getFont();
        drawAxisLines(g2d);
        drawXAxisValues(g2d);
        drawYAxisValues(g2d);
        drawAxisNames(g2d);
        drawChartValues(g2d);
        g2d.setColor(originalColor);
        g2d.setFont(originalFont);
    }

    

    private void drawAxisLines(Graphics2D g2d) {
        g2d.setColor(Color.black);
        g2d.drawLine(model.getXOffset(), gutter, model.getXOffset(), getHeight() - gutter);
        g2d.drawLine(model.getXOffset(), getHeight() - gutter, getWidth() - model.getXOffset(), getHeight() - gutter);
    }

    private void drawXAxisValues(Graphics2D g2d) {
        int xDivisionCount = (model.getXDivisionCount() > 0 ? model.getXDivisionCount() : 1);
        for (int i = 0; i < model.getXDivisionCount() + 1; i++) {
            int partLength;
            partLength = ((getWidth() - (2 * model.getXOffset())) * i) / xDivisionCount;
            int x = model.getXOffset() + partLength;
            g2d.drawLine(x, getHeight() - gutter - 3, x, getHeight() - gutter + 3);
            String value = String.valueOf((((model.getMaximumXValue() - model.getMinimumXValue()) * i) / xDivisionCount) + model.getMinimumXValue());
            g2d.drawString(value, x - 5, getHeight() - gutter + 20);
        }
    }

    private void drawYAxisValues(Graphics2D g2d) {
        for (int i = 0; i < model.getYDivisionCount() + 1; i++) {
            if (model.getYDivisionCount() > 0) {
                int partLength = ((getHeight() - (2 * gutter)) * (model.getYDivisionCount() - i)) / model.getYDivisionCount();
                int y = gutter + partLength;
                g2d.drawLine(model.getXOffset() - 3, y, model.getXOffset() + 3, y);
                String value = String.valueOf(((model.getMaximumYValue() - model.getMinimumYValue()) * i / model.getYDivisionCount()) + model.getMinimumYValue());
                g2d.drawString(value, gutter - 40, y + 5);
            }
        }
    }

    private void drawChartValues(Graphics2D g2d) {
        g2d.setColor(lineColor);
        int lastX = -1;
        int lastY = -1;
        Iterator<LineChartValue> iterator = model.getValuesIterator();
        while (iterator.hasNext()) {
            LineChartValue lineChartValue = iterator.next();
            int differenceX = model.getMaximumXValue() - model.getMinimumXValue();
            differenceX = (differenceX > 0 ? differenceX : 1);
            int differenceY = model.getMaximumYValue() - model.getMinimumYValue();
            differenceY = (differenceY > 0 ? differenceY : 1);
            int xPaintPoint = model.getXOffset() + ((getWidth() - (2 * model.getXOffset())) * (lineChartValue.getxValue() - model.getMinimumXValue())) / differenceX;
            int yPaintPoint = gutter + ((model.getMaximumYValue() - lineChartValue.getyValue()) * (getHeight() - 2 * gutter)) / differenceY;
            g2d.fillRect(xPaintPoint - 1, yPaintPoint - 1, 2, 2);
            if (lastX != -1 && lastY != -1) {
                g2d.drawLine(lastX, lastY, xPaintPoint, yPaintPoint);
            }
            lastX = xPaintPoint;
            lastY = yPaintPoint;
        }
    }

    private void drawAxisNames(Graphics2D g2d) {
        if (model.getAxisNameFont() != null) {
            g2d.setFont(model.getAxisNameFont());
        }
        if (model.getYAxisName() != null && !model.getYAxisName().trim().equals("")) {
            g2d.drawString(model.getYAxisName(), 10, 25);
        }
        if (model.getXAxisName() != null && !model.getXAxisName().trim().equals("")) {
            g2d.drawString(model.getXAxisName(), getWidth() - 60, getHeight() - 10);
        }
    }
}
