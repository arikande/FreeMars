package org.freemars.model;

import java.util.Iterator;
import java.util.TreeMap;
import org.freemars.ui.map.TilePaintModel;
import org.freerealm.map.Coordinate;
import org.freerealm.map.Path;

/**
 *
 * @author Deniz ARIKAN
 */
public class FreeMarsViewModel {

    public static final Coordinate DEFAULT_CENTERED_COORDINATE = new Coordinate(0, 0);
    public static final int MAIN_MAP_MINIMUM_ZOOM = 0;
    public static final int MAIN_MAP_MAXIMUM_ZOOM = 5;
    public static final int MINI_MAP_MINIMUM_ZOOM = 0;
    public static final int MINI_MAP_MAXIMUM_ZOOM = 5;
    public static final int MAIN_MAP_DEFAULT_ZOOM_LEVEL = 3;
    public static final int MINI_MAP_DEFAULT_ZOOM_LEVEL = 3;
    private final TreeMap<Coordinate, TilePaintModel> paintModels;
    private Coordinate centeredCoordinate;
    private int mapPanelZoomLevel;
    private int miniMapPanelZoomLevel;
    private boolean mapPanelDisplayingTileTypes;
    private boolean mapPanelDisplayingUnitPath;
    private boolean mapPanelDisplayingGrid;
    private boolean mapPanelDisplayingCoordinates;
    private Path mapPanelUnitPath;

    protected FreeMarsViewModel() {
        mapPanelZoomLevel = MAIN_MAP_DEFAULT_ZOOM_LEVEL;
        miniMapPanelZoomLevel = MINI_MAP_DEFAULT_ZOOM_LEVEL;
        mapPanelDisplayingTileTypes = false;
        mapPanelDisplayingUnitPath = false;
        mapPanelDisplayingGrid = false;
        mapPanelDisplayingCoordinates = false;
        centeredCoordinate = DEFAULT_CENTERED_COORDINATE;
        paintModels = new TreeMap<Coordinate, TilePaintModel>();
    }

    public void clearPaintModels() {
        paintModels.clear();
    }

    public int getMapPanelZoomLevel() {
        return mapPanelZoomLevel;
    }

    public void setMapPanelZoomLevel(int mapPanelZoomLevel) {
        this.mapPanelZoomLevel = mapPanelZoomLevel;
    }

    public int getMiniMapPanelZoomLevel() {
        return miniMapPanelZoomLevel;
    }

    public void setMiniMapPanelZoomLevel(int miniMapPanelZoomLevel) {
        this.miniMapPanelZoomLevel = miniMapPanelZoomLevel;
    }

    public boolean isMapPanelDisplayingTileTypes() {
        return mapPanelDisplayingTileTypes;
    }

    public void setMapPanelDisplayingTileTypes(boolean mapPanelDisplayingTileTypes) {
        this.mapPanelDisplayingTileTypes = mapPanelDisplayingTileTypes;
    }

    public boolean isMapPanelDisplayingUnitPath() {
        return mapPanelDisplayingUnitPath;
    }

    public void setMapPanelDisplayingUnitPath(boolean mapPanelDisplayingUnitPath) {
        this.mapPanelDisplayingUnitPath = mapPanelDisplayingUnitPath;
        if (!mapPanelDisplayingUnitPath) {
            mapPanelUnitPath = null;
        }
    }

    public boolean isMapPanelDisplayingGrid() {
        return mapPanelDisplayingGrid;
    }

    public void setMapPanelDisplayingGrid(boolean mapPanelDisplayingGrid) {
        this.mapPanelDisplayingGrid = mapPanelDisplayingGrid;
    }

    public boolean isMapPanelDisplayingCoordinates() {
        return mapPanelDisplayingCoordinates;
    }

    public void setMapPanelDisplayingCoordinates(boolean mapPanelDisplayingCoordinates) {
        this.mapPanelDisplayingCoordinates = mapPanelDisplayingCoordinates;
    }

    public Path getMapPanelUnitPath() {
        return mapPanelUnitPath;
    }

    public void setMapPanelUnitPath(Path mapPanelUnitPath) {
        this.mapPanelUnitPath = mapPanelUnitPath;
    }

    public Coordinate getCenteredCoordinate() {
        return centeredCoordinate;
    }

    public void setCenteredCoordinate(Coordinate centeredCoordinate) {
        this.centeredCoordinate = centeredCoordinate;
    }

    public Iterator<Coordinate> getTilePaintModelCoordinatesIterator() {
        return paintModels.keySet().iterator();
    }

    public TilePaintModel getTilePaintModel(Coordinate coordinate) {
        return paintModels.get(coordinate);
    }

    public void putTilePaintModel(Coordinate coordinate, TilePaintModel tilePaintModel) {
        paintModels.put(coordinate, tilePaintModel);
    }

}
