package org.freemars.ui.map;

import java.awt.Color;
import java.awt.Dimension;
import org.freerealm.Realm;
import org.freerealm.map.Coordinate;

/**
 *
 * @author Deniz ARIKAN
 */
public class MapImageModel {

    private Realm realm;
    private Dimension imageDimension;
    private Dimension gridDimension;
    private int horizontalGrids;
    private int verticalGrids;
    private Coordinate offsetCoordinate;
    private boolean displayingGridLines;
    private Color gridLineColor;
    private boolean displayingGridCoordinates;
    private boolean displayingGridTypes;
    private Color gridTextColor;

    public Realm getRealm() {
        return realm;
    }

    public void setRealm(Realm realm) {
        this.realm = realm;
    }

    public Dimension getImageDimension() {
        return imageDimension;
    }

    public void setImageDimension(Dimension imageDimension) {
        this.imageDimension = imageDimension;
    }

    public Dimension getGridDimension() {
        return gridDimension;
    }

    public void setGridDimension(Dimension gridDimension) {
        this.gridDimension = gridDimension;
    }

    public int getHorizontalGrids() {
        return horizontalGrids;
    }

    public void setHorizontalGrids(int horizontalGrids) {
        this.horizontalGrids = horizontalGrids;
    }

    public int getVerticalGrids() {
        return verticalGrids;
    }

    public void setVerticalGrids(int verticalGrids) {
        this.verticalGrids = verticalGrids;
    }

    public Coordinate getOffsetCoordinate() {
        return offsetCoordinate;
    }

    public void setOffsetCoordinate(Coordinate offsetCoordinate) {
        this.offsetCoordinate = offsetCoordinate;
    }

    public boolean isDisplayingGridLines() {
        return displayingGridLines;
    }

    public void setDisplayingGridLines(boolean displayingGridLines) {
        this.displayingGridLines = displayingGridLines;
    }

    public Color getGridLineColor() {
        return gridLineColor;
    }

    public void setGridLineColor(Color gridLineColor) {
        this.gridLineColor = gridLineColor;
    }

    public boolean isDisplayingGridCoordinates() {
        return displayingGridCoordinates;
    }

    public void setDisplayingGridCoordinates(boolean displayingGridCoordinates) {
        this.displayingGridCoordinates = displayingGridCoordinates;
    }

    public boolean isDisplayingGridTypes() {
        return displayingGridTypes;
    }

    public void setDisplayingGridTypes(boolean displayingGridTypes) {
        this.displayingGridTypes = displayingGridTypes;
    }

    public Color getGridTextColor() {
        return gridTextColor;
    }

    public void setGridTextColor(Color gridTextColor) {
        this.gridTextColor = gridTextColor;
    }

}
