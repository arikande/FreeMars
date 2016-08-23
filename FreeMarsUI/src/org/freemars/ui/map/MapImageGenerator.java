package org.freemars.ui.map;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import org.freemars.ui.map.tile.TilePaintModel;
import org.freemars.ui.map.tile.TilePaintModelBuilder;
import org.freemars.ui.map.tile.TilePainter;
import org.freerealm.Realm;
import org.freerealm.map.Coordinate;

/**
 *
 * @author Deniz ARIKAN
 */
public class MapImageGenerator {

    public static final int FULL = 0;
    public static final int HALF = 1;
    public static int columnMode = FULL;

    public static BufferedImage generateMapImage(MapImageModel mapImageModel) {
        Realm realm = mapImageModel.getRealm();
        int gridWidth = (int) mapImageModel.getGridDimension().getWidth();
        int gridHeight = (int) mapImageModel.getGridDimension().getHeight();
        int horizontalGrids = mapImageModel.getHorizontalGrids();
        int verticalGrids = mapImageModel.getVerticalGrids();
        Coordinate offsetCoordinate = mapImageModel.getOffsetCoordinate();
        Dimension imageDimension = mapImageModel.getImageDimension();
        if (offsetCoordinate.getOrdinate() % 2 == 1) {
            columnMode = HALF;
        } else {
            columnMode = FULL;
        }
        BufferedImage mapImage = new BufferedImage((int) imageDimension.getWidth(), (int) imageDimension.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Map<Coordinate, TilePaintModel> tilePaintModels = new HashMap<Coordinate, TilePaintModel>();
        for (int ordinate = (offsetCoordinate.getOrdinate() > 0 ? -1 : 0); ordinate < verticalGrids; ordinate++) {
            for (int abscissa = -1; abscissa < horizontalGrids; abscissa++) {
                Coordinate relativeCoordinate = new Coordinate(abscissa, ordinate);
                Coordinate worldCoordinate = getWorldCoordinate(realm, relativeCoordinate, offsetCoordinate);
                if (worldCoordinate != null) {
                    TilePaintModel tilePaintModel = new TilePaintModel();
                    tilePaintModel.setDisplayingGridLines(mapImageModel.isDisplayingGridLines());
                    tilePaintModel.setGridLineColor(mapImageModel.getGridLineColor());
                    tilePaintModel.setDisplayingCoordinate(mapImageModel.isDisplayingGridCoordinates());
                    tilePaintModel.setDisplayingTileType(mapImageModel.isDisplayingGridTypes());
                    tilePaintModel.setGridTextColor(mapImageModel.getGridTextColor());
                    TilePaintModelBuilder.buildModelForTileTerrain(realm, tilePaintModel, worldCoordinate, gridWidth, gridHeight);
                    tilePaintModels.put(worldCoordinate, tilePaintModel);
                }
            }
        }
        paintTerrainLayer(mapImage, mapImageModel, tilePaintModels);
        paintVegetationLayer(mapImage, mapImageModel, tilePaintModels);
        paintBonusResourceLayer(mapImage, mapImageModel, tilePaintModels);
        paintInfoLayer(mapImage, mapImageModel, tilePaintModels);
        return mapImage;
    }

    private static void paintTerrainLayer(Image mapImage, MapImageModel mapImageModel, Map<Coordinate, TilePaintModel> tilePaintModels) {
        for (int ordinate = (mapImageModel.getOffsetCoordinate().getOrdinate() > 0 ? -1 : 0); ordinate < mapImageModel.getVerticalGrids(); ordinate++) {
            for (int abscissa = -1; abscissa < mapImageModel.getHorizontalGrids(); abscissa++) {
                Coordinate relativeCoordinate = new Coordinate(abscissa, ordinate);
                Coordinate worldCoordinate = getWorldCoordinate(mapImageModel.getRealm(), relativeCoordinate, mapImageModel.getOffsetCoordinate());
                if (worldCoordinate != null) {
                    TilePaintModel tilePaintModel = tilePaintModels.get(worldCoordinate);
                    Point paintPoint = getRhombusPoint(relativeCoordinate, mapImageModel.getGridDimension());
                    TilePainter.paintTileTerrain(mapImage.getGraphics(), tilePaintModel, paintPoint);
                }
            }
        }
    }

    private static void paintBonusResourceLayer(Image mapImage, MapImageModel mapImageModel, Map<Coordinate, TilePaintModel> tilePaintModels) {
        for (int ordinate = (mapImageModel.getOffsetCoordinate().getOrdinate() > 0 ? -1 : 0); ordinate < mapImageModel.getVerticalGrids(); ordinate++) {
            for (int abscissa = -1; abscissa < mapImageModel.getHorizontalGrids(); abscissa++) {
                Coordinate relativeCoordinate = new Coordinate(abscissa, ordinate);
                Coordinate worldCoordinate = getWorldCoordinate(mapImageModel.getRealm(), relativeCoordinate, mapImageModel.getOffsetCoordinate());
                if (worldCoordinate != null) {
                    TilePaintModel tilePaintModel = tilePaintModels.get(worldCoordinate);
                    Point paintPoint = getRhombusPoint(relativeCoordinate, mapImageModel.getGridDimension());
                    TilePainter.paintTileBonusResource(mapImage.getGraphics(), tilePaintModel, paintPoint);
                }
            }
        }
    }

    private static void paintVegetationLayer(Image mapImage, MapImageModel mapImageModel, Map<Coordinate, TilePaintModel> tilePaintModels) {
        for (int ordinate = (mapImageModel.getOffsetCoordinate().getOrdinate() > 0 ? -1 : 0); ordinate < mapImageModel.getVerticalGrids(); ordinate++) {
            for (int abscissa = -1; abscissa < mapImageModel.getHorizontalGrids(); abscissa++) {
                Coordinate relativeCoordinate = new Coordinate(abscissa, ordinate);
                Coordinate worldCoordinate = getWorldCoordinate(mapImageModel.getRealm(), relativeCoordinate, mapImageModel.getOffsetCoordinate());
                if (worldCoordinate != null) {
                    TilePaintModel tilePaintModel = tilePaintModels.get(worldCoordinate);
                    Point paintPoint = getRhombusPoint(relativeCoordinate, mapImageModel.getGridDimension());
                    TilePainter.paintTileVegatation(mapImage.getGraphics(), tilePaintModel, paintPoint);
                }
            }
        }
    }

    private static void paintInfoLayer(Image mapImage, MapImageModel mapImageModel, Map<Coordinate, TilePaintModel> tilePaintModels) {
        for (int ordinate = (mapImageModel.getOffsetCoordinate().getOrdinate() > 0 ? -1 : 0); ordinate < mapImageModel.getVerticalGrids(); ordinate++) {
            for (int abscissa = -1; abscissa < mapImageModel.getHorizontalGrids(); abscissa++) {
                Coordinate relativeCoordinate = new Coordinate(abscissa, ordinate);
                Coordinate worldCoordinate = getWorldCoordinate(mapImageModel.getRealm(), relativeCoordinate, mapImageModel.getOffsetCoordinate());
                if (worldCoordinate != null) {
                    TilePaintModel tilePaintModel = tilePaintModels.get(worldCoordinate);
                    Point paintPoint = getRhombusPoint(relativeCoordinate, mapImageModel.getGridDimension());
                    TilePainter.paintTileInfo(mapImage.getGraphics(), tilePaintModel, paintPoint);
                }
            }
        }
    }

    private static Coordinate getWorldCoordinate(Realm realm, Coordinate relativeCoordinate, Coordinate offsetCoordinate) {
        int worldAbscissa = (offsetCoordinate.getAbscissa() + relativeCoordinate.getAbscissa()) % realm.getMapWidth();
        if (worldAbscissa < 0) {
            worldAbscissa = worldAbscissa + realm.getMapWidth();
        }
        int worldOrdinate = offsetCoordinate.getOrdinate() + relativeCoordinate.getOrdinate();
        Coordinate worldCoordinate = new Coordinate(worldAbscissa, worldOrdinate);
        if (realm.isValidCoordinate(worldCoordinate)) {
            return worldCoordinate;
        } else {
            return null;
        }
    }

    private static Point getRhombusPoint(Coordinate coordinate, Dimension gridDimension) {
        int gridWidth = gridDimension.width;
        int gridHeight = gridDimension.height;
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

}
