package org.freemars.ui.map;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import javax.swing.JPanel;
import org.freerealm.map.Coordinate;

/**
 *
 * @author Deniz ARIKAN
 */
public class RhombusGridPanel extends JPanel {

    public static final int FULL = 0;
    public static final int HALF = 1;
    public int columnMode = FULL;
    private int gridWidth;
    private int gridHeight;
    private int horizontalGrids;
    private int verticalGrids;

    public RhombusGridPanel(int gridWidth, int gridHeight) {
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        setGridCounts();
    }

    public void setGridCounts() {
        horizontalGrids = (getWidth() / gridWidth) + 1;
        verticalGrids = (2 * getHeight() / (gridHeight)) + 1;
    }

    public Point getRhombusPoint(Coordinate coordinate) {
        int x = coordinate.getAbscissa() * gridWidth;
        int y = coordinate.getOrdinate() * (gridHeight / 2);
        if (Math.abs(coordinate.getOrdinate()) % 2 == 1) {
            if (columnMode == FULL) {
                x = x + gridWidth / 2;
            } else {
                x = x - gridWidth / 2;
            }
        }
        if (columnMode == HALF) {
            x = x + gridWidth / 2;
        }
        return new Point(x, y);
    }

    public void paintRhombus(Graphics2D g2d, Point point) {
        int x1 = (int) point.getX();
        int x2 = x1 + gridWidth / 2;
        int x3 = x1 + gridWidth;
        int y1 = (int) point.getY();
        int y2 = y1 + gridHeight / 2;
        int y3 = y1 + gridHeight;
        g2d.drawLine(x1, y2, x2, y1);
        g2d.drawLine(x2, y1, x3, y2);
        g2d.drawLine(x3, y2, x2, y3);
        g2d.drawLine(x1, y2, x2, y3);
    }

    public void paintText(Graphics2D g2d, Point paintPoint, Font font, Color color, String text) {
        g2d.setFont(font);
        g2d.setColor(color);
        int textAbscissa = (int) paintPoint.getX() + getGridWidth() * 3 / 8;
        int textOrdinate = (int) paintPoint.getY() + getGridHeight() / 2;
        g2d.drawString(text, textAbscissa, textOrdinate);
    }

    public int getGridWidth() {
        return gridWidth;
    }

    public void setGridWidth(int gridWidth) {
        this.gridWidth = gridWidth;
        repaint();
    }

    public int getGridHeight() {
        return gridHeight;
    }

    public void setGridHeight(int gridHeight) {
        this.gridHeight = gridHeight;
        repaint();
    }

    public int getHorizontalGrids() {
        return horizontalGrids;
    }

    public int getVerticalGrids() {
        return verticalGrids;
    }

    public void setColumnMode(int mode) {
        this.columnMode = mode;
        repaint();
    }
}
