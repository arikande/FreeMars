package org.freemars.editor.model;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Observable;
import org.freemars.ui.map.MapImageModel;
import org.freerealm.Realm;
import org.freerealm.map.Coordinate;

/**
 *
 * @author Deniz ARIKAN
 */
public class EditorModel extends Observable {

    public static final int SUGGESTED_PLAYERS_MINIMUM_VALUE = 2;
    public static final int SUGGESTED_PLAYERS_MAXIMUM_VALUE = 12;
    
    public static final int EDITOR_PANEL_MINIMUM_ZOOM_MULTIPLIER = 1;
    public static final int EDITOR_PANEL_MAXIMUM_ZOOM_MULTIPLIER = 20;
    public static final int EDITOR_PANEL_DEFAULT_ZOOM_MULTIPLIER = 10;

    private String fileName;
    private boolean fileDirty;
    private int editorMapZoomLevel;
    private final MapImageModel mapImageModel;
    private final Brush brush;

    public EditorModel(Realm realm) {
        editorMapZoomLevel = EDITOR_PANEL_DEFAULT_ZOOM_MULTIPLIER;
        mapImageModel = new MapImageModel();
        mapImageModel.setRealm(realm);
        mapImageModel.setOffsetCoordinate(new Coordinate());
        brush = new Brush();
    }

    public Realm getRealm() {
        return mapImageModel.getRealm();
    }

    public Brush getBrush() {
        return brush;
    }

    public int getEditorMapZoomLevel() {
        return editorMapZoomLevel;
    }

    public void setEditorMapZoomLevel(int editorMapZoomLevel) {
        this.editorMapZoomLevel = editorMapZoomLevel;
    }

    public boolean isEditorMapDisplayingGridLines() {
        return mapImageModel.isDisplayingGridLines();
    }

    public void setEditorMapDisplayingGridLines(boolean editorMapDisplayingGridLines) {
        mapImageModel.setDisplayingGridLines(editorMapDisplayingGridLines);
    }

    public boolean isEditorMapDisplayingCoordinates() {
        return mapImageModel.isDisplayingGridCoordinates();
    }

    public void setEditorMapDisplayingCoordinates(boolean editorMapDisplayingCoordinates) {
        mapImageModel.setDisplayingGridCoordinates(editorMapDisplayingCoordinates);
    }

    public boolean isEditorMapDisplayingTileTypes() {
        return mapImageModel.isDisplayingGridTypes();
    }

    public void setEditorMapDisplayingTileTypes(boolean editorMapDisplayingTileTypes) {
        mapImageModel.setDisplayingGridTypes(editorMapDisplayingTileTypes);
    }

    public Color getGridLineColor() {
        return mapImageModel.getGridLineColor();
    }

    public void setGridLineColor(Color gridLineColor) {
        mapImageModel.setGridLineColor(gridLineColor);
    }

    public Color getGridTextColor() {
        return mapImageModel.getGridTextColor();
    }

    public void setGridTextColor(Color gridTextColor) {
        mapImageModel.getGridTextColor();
    }

    public Dimension getGridDimension() {
        return mapImageModel.getGridDimension();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDefaultFrameTitle() {
        return "Free Mars Editor";
    }

    public boolean isFileDirty() {
        return fileDirty;
    }

    public void setFileDirty(boolean fileDirty) {
        this.fileDirty = fileDirty;
    }

    public Coordinate getOffsetCoordinate() {
        return mapImageModel.getOffsetCoordinate();
    }

    public void setOffsetCoordinate(Coordinate offsetCoordinate) {
        mapImageModel.setOffsetCoordinate(offsetCoordinate);
    }

    public MapImageModel getMapImageModel() {
        return mapImageModel;
    }

}
