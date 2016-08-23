package org.freemars.ui.map;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.freemars.model.FreeMarsModel;
import org.freemars.ui.image.FreeMarsImageManager;
import org.freerealm.RealmConstants;
import org.freerealm.executor.order.Sentry;
import org.freerealm.map.Coordinate;
import org.freerealm.map.Direction;
import org.freerealm.player.Player;
import org.freerealm.tile.Tile;
import org.freerealm.tile.improvement.TileImprovementType;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class TilePaintModelBuilder {

    public static TilePaintModel buildTilePaintModel(Graphics graphics, FreeMarsModel freeMarsModel, Coordinate tileCoordinate) {
        TilePaintModel tilePaintModel = null;
        if (tileCoordinate != null) {
            Tile tile = freeMarsModel.getTile(tileCoordinate);
            if (tile != null) {
                tilePaintModel = new TilePaintModel();
                tilePaintModel.setTileCoordinate(tileCoordinate);
                updateTilePaintModel(graphics, tilePaintModel, freeMarsModel, tile, null);
            }
        }
        return tilePaintModel;
    }

    public static void updateTilePaintModel(Graphics graphics, TilePaintModel tilePaintModel, FreeMarsModel freeMarsModel, Tile tile, List<Unit> excludeUnits) {
        if (tilePaintModel != null) {
            boolean displayUnexploredTiles = Boolean.valueOf(freeMarsModel.getRealm().getProperty("display_unexplored_tiles"));
            if (displayUnexploredTiles || freeMarsModel.getRealm().getPlayerManager().getActivePlayer().isCoordinateExplored(tilePaintModel.getTileCoordinate())) {
                buildModelForExploredTile(graphics, tilePaintModel, freeMarsModel, tilePaintModel.getTileCoordinate(), tile, excludeUnits);
            } else {
                buildModelForUnexploredTile(tilePaintModel, freeMarsModel, tilePaintModel.getTileCoordinate());
            }
        }
    }

    private static void buildModelForExploredTile(Graphics graphics, TilePaintModel tilePaintModel, FreeMarsModel freeMarsModel, Coordinate tileCoordinate, Tile tile, List<Unit> excludeUnits) {
        buildModelForTileTerrain(tilePaintModel, freeMarsModel, tileCoordinate, tile);
        buildModelForTileColony(tilePaintModel, tile);
        buildModelForTileVegetation(tilePaintModel, freeMarsModel, tileCoordinate, tile);
        buildModelForTileBonusResource(tilePaintModel, freeMarsModel, tileCoordinate, tile);
        tilePaintModel.clearTerrainImprovementImages();
        buildModelForRoads(tilePaintModel, freeMarsModel, tileCoordinate, tile);
        buildModelForTileImprovement(tilePaintModel, freeMarsModel, tileCoordinate, tile);
        buildModelForTileInfo(tilePaintModel, freeMarsModel, tile);
        buildModelForTileTerritoryBorder(tilePaintModel, freeMarsModel, tileCoordinate);
        buildModelForTileUnits(graphics, tilePaintModel, freeMarsModel, tile, excludeUnits);
        checkUnitPath(tilePaintModel, freeMarsModel, tileCoordinate, tile);
        if (tile.getCollectable() != null) {
            Image collectableImage = FreeMarsImageManager.getImage("SPACESHIP_DEBRIS");
            tilePaintModel.setCollectableImage(collectableImage);
        }
    }

    private static void buildModelForTileTerrain(TilePaintModel tilePaintModel, FreeMarsModel freeMarsModel, Coordinate tileCoordinate, Tile tile) {
        tilePaintModel.clearTerrainImages();
        Image image = FreeMarsImageManager.getImage(tile);
        tilePaintModel.addTerrainImage(RealmConstants.CENTER, image);
        for (Direction direction : RealmConstants.directions) {
            if (!direction.equals(RealmConstants.CENTER)) {
                Coordinate adjacentCoordinate = freeMarsModel.getRealm().getRelativeCoordinate(tileCoordinate, direction);
                if (adjacentCoordinate != null) {
                    boolean displayUnexploredTiles = Boolean.valueOf(freeMarsModel.getRealm().getProperty("display_unexplored_tiles"));
                    if (displayUnexploredTiles || freeMarsModel.getActivePlayer().isCoordinateExplored(adjacentCoordinate)) {
                        Tile adjacentTile = freeMarsModel.getTile(adjacentCoordinate);
                        if (adjacentTile == null || (adjacentTile != null && adjacentTile.getType().getId() <= tile.getType().getId())) {
                            Image transitionImage = FreeMarsImageManager.getImage("TILE_" + adjacentTile.getType().getName().replaceAll(" ", "") + "_" + direction.getShortName());
                            tilePaintModel.addTerrainImage(direction, transitionImage);
                        }
                    }
                }
            }
        }
    }

    private static void buildModelForTileColony(TilePaintModel tilePaintModel, Tile tile) {
        tilePaintModel.setColonyImage(null);
        tilePaintModel.setColonyName(null);
        tilePaintModel.setColonyNamePrimaryColor(null);
        tilePaintModel.setColonyNameSecondaryColor(null);
        if (tile.getSettlement() != null) {
            Image colonyImage = FreeMarsImageManager.getImage(tile.getSettlement());
            tilePaintModel.setColonyImage(colonyImage);
            tilePaintModel.setColonyName(tile.getSettlement().getName());
            tilePaintModel.setColonyNamePrimaryColor(tile.getSettlement().getPlayer().getPrimaryColor());
            tilePaintModel.setColonyNameSecondaryColor(tile.getSettlement().getPlayer().getSecondaryColor());
        }
    }

    private static void buildModelForTileVegetation(TilePaintModel tilePaintModel, FreeMarsModel viewModel, Coordinate tileCoordinate, Tile tile) {
        tilePaintModel.setVegetationImage(null);
        if (tile.getVegetation() != null) {
            Image vegetationImage = FreeMarsImageManager.getImage(tile.getVegetation());
            tilePaintModel.setVegetationImage(vegetationImage);
        }
    }

    private static void buildModelForTileBonusResource(TilePaintModel tilePaintModel, FreeMarsModel viewModel, Coordinate tileCoordinate, Tile tile) {
        tilePaintModel.setResourceBonusImage(null);
        if (tile.getBonusResource() != null) {
            Image resourceBonusImage = FreeMarsImageManager.getImage(tile.getBonusResource());
            resourceBonusImage = FreeMarsImageManager.createResizedCopy(resourceBonusImage, 34, 34, false, null);
            tilePaintModel.setResourceBonusImage(resourceBonusImage);
        }
    }

    private static void buildModelForTileImprovement(TilePaintModel tilePaintModel, FreeMarsModel freeMarsModel, Coordinate tileCoordinate, Tile tile) {
        TileImprovementType road = freeMarsModel.getRealm().getTileImprovementTypeManager().getImprovement("Road");
        Iterator<TileImprovementType> improvements = tile.getImprovementsIterator();
        while (improvements.hasNext()) {
            TileImprovementType improvement = improvements.next();
            if (!improvement.equals(road)) {
                Image improvementImage = FreeMarsImageManager.getImage(improvement);
                tilePaintModel.addTerrainImprovementImage(improvementImage);
            }
        }
    }

    private static void buildModelForTileInfo(TilePaintModel tilePaintModel, FreeMarsModel freeMarsModel, Tile tile) {
        tilePaintModel.setDisplayingCoordinate(freeMarsModel.getFreeMarsViewModel().isMapPanelDisplayingCoordinates());
        tilePaintModel.setDisplayingGrid(freeMarsModel.getFreeMarsViewModel().isMapPanelDisplayingGrid());
        tilePaintModel.setDisplayingTileType(freeMarsModel.getFreeMarsViewModel().isMapPanelDisplayingTileTypes());
        tilePaintModel.setTileTypeName(tile.getType().getName());
        tilePaintModel.setTileTypeNameColor(Color.WHITE);
    }

    private static void buildModelForTileTerritoryBorder(TilePaintModel tilePaintModel, FreeMarsModel freeMarsModel, Coordinate tileCoordinate) {
        tilePaintModel.clearTerritoryBorderDirections();
        if (freeMarsModel.getRealm().getTileOwner(tileCoordinate) != null) {
            Player tileOwner = freeMarsModel.getRealm().getTileOwner(tileCoordinate);
            tilePaintModel.setTerritoryPrimaryBorderColor(tileOwner.getPrimaryColor());
            tilePaintModel.setTerritorySecondaryBorderColor(tileOwner.getSecondaryColor());
            for (Direction direction : RealmConstants.directions) {
                Coordinate neighborCoordinate = freeMarsModel.getRealm().getRelativeCoordinate(tileCoordinate, direction);
                if (neighborCoordinate == null || freeMarsModel.getRealm().getTileOwner(neighborCoordinate) == null || !freeMarsModel.getRealm().getTileOwner(neighborCoordinate).equals(tileOwner)) {
                    tilePaintModel.addTerritoryBorderDirection(direction);
                }
            }
        }
    }

    public static void buildModelForTileUnits(Graphics graphics, TilePaintModel tilePaintModel, FreeMarsModel freeMarsModel, Tile tile, List<Unit> excludeUnits) {
        tilePaintModel.setUnitImage(null);
        tilePaintModel.setPaintingActiveUnitIndicator(false);
        if (tile.getNumberOfUnits() > 0) {
            Unit unitToPaint = findUnitToPaint(freeMarsModel, tile, excludeUnits);
            if (unitToPaint != null) {
                if (unitToPaint.equals(freeMarsModel.getActivePlayer().getActiveUnit())) {
                    tilePaintModel.setPaintingActiveUnitIndicator(true);
                }
                Image unitImage;
                if ((unitToPaint.getCurrentOrder() != null) && (unitToPaint.getCurrentOrder() instanceof Sentry)) {
                    unitImage = FreeMarsImageManager.getImage(unitToPaint, true);
                } else {
                    unitImage = FreeMarsImageManager.getImage(unitToPaint, false);
                }
                tilePaintModel.setUnitImage(unitImage);
                String rectangleContent = "";
                if (unitToPaint.getCurrentOrder() != null) {
                    rectangleContent = rectangleContent + String.valueOf(unitToPaint.getCurrentOrder().getSymbol());
                    if (unitToPaint.getCurrentOrder().getRemainingTurns() != -1) {
                        rectangleContent = rectangleContent + ":" + unitToPaint.getCurrentOrder().getRemainingTurns();
                    }
                } else {
                    if (unitToPaint.getNextOrder() != null) {
                        rectangleContent = String.valueOf(unitToPaint.getNextOrder().getSymbol());
                    }
                }
                if (unitToPaint.getAutomater() != null) {
                    rectangleContent = rectangleContent + "(A)";
                }
                Font unitRectangleContentFont = new Font("Arial", 0, 12);
                byte unitRectangleWidth = (byte) (graphics.getFontMetrics(unitRectangleContentFont).stringWidth(rectangleContent) + 2);
                unitRectangleWidth = (unitRectangleWidth < 10 ? 10 : unitRectangleWidth);
                tilePaintModel.setUnitRectangleContentFont(unitRectangleContentFont);
                tilePaintModel.setUnitRectangleWidth(unitRectangleWidth);
                tilePaintModel.setUnitRectangleContent(rectangleContent);
                tilePaintModel.setUnitRectangleForegroundColor(unitToPaint.getPlayer().getSecondaryColor());
                tilePaintModel.setUnitRectangleBackgroundColor(unitToPaint.getPlayer().getPrimaryColor());
                Image unitPlayerNationFlagImage = FreeMarsImageManager.getImage(unitToPaint.getPlayer().getNation());
                tilePaintModel.setUnitPlayerNationFlagImage(unitPlayerNationFlagImage);
                tilePaintModel.setUnitCount(tile.getNumberOfUnits());
            }
        }
    }

    private static Unit findUnitToPaint(FreeMarsModel freeMarsModel, Tile tile, List<Unit> excludeUnits) {
        List<Unit> candidateUnits = new ArrayList<Unit>();
        Iterator<Unit> iterator = tile.getUnitsIterator();
        while (iterator.hasNext()) {
            Unit unit = iterator.next();
            if (excludeUnits == null || !excludeUnits.contains(unit)) {
                candidateUnits.add(unit);
            }
        }
        if (candidateUnits.isEmpty()) {
            return null;
        } else {
            iterator = candidateUnits.iterator();
            while (iterator.hasNext()) {
                Unit unit = iterator.next();
                if (unit.equals(freeMarsModel.getActivePlayer().getActiveUnit())) {
                    return unit;
                }
            }
            if (tile.getSettlement() != null) {
                return null;
            }
            return candidateUnits.get(candidateUnits.size() - 1);
        }
    }

    private static void buildModelForRoads(TilePaintModel tilePaintModel, FreeMarsModel freeMarsModel, Coordinate tileCoordinate, Tile tile) {
        TileImprovementType road = freeMarsModel.getRealm().getTileImprovementTypeManager().getImprovement("Road");
        if (tile.hasImprovement(road)) {
            Coordinate north = freeMarsModel.getRealm().getRelativeCoordinate(tileCoordinate, RealmConstants.NORTH);
            if (north != null) {
                Tile northTile = freeMarsModel.getRealm().getTile(north);
                if (northTile.hasImprovement(road)) {
                    Image improvementImage = FreeMarsImageManager.getImage("IMPROVEMENT_road_N");
                    tilePaintModel.addTerrainImprovementImage(improvementImage);
                }
            }
            Coordinate south = freeMarsModel.getRealm().getRelativeCoordinate(tileCoordinate, RealmConstants.SOUTH);
            if (south != null) {
                Tile southTile = freeMarsModel.getRealm().getTile(south);
                if (southTile.hasImprovement(road)) {
                    Image improvementImage = FreeMarsImageManager.getImage("IMPROVEMENT_road_S");
                    tilePaintModel.addTerrainImprovementImage(improvementImage);
                }
            }
            Coordinate east = freeMarsModel.getRealm().getRelativeCoordinate(tileCoordinate, RealmConstants.EAST);
            if (east != null) {
                Tile eastTile = freeMarsModel.getRealm().getTile(east);
                if (eastTile.hasImprovement(road)) {
                    Image improvementImage = FreeMarsImageManager.getImage("IMPROVEMENT_road_E");
                    tilePaintModel.addTerrainImprovementImage(improvementImage);
                }
            }
            Coordinate west = freeMarsModel.getRealm().getRelativeCoordinate(tileCoordinate, RealmConstants.WEST);
            if (west != null) {
                Tile westTile = freeMarsModel.getRealm().getTile(west);
                if (westTile.hasImprovement(road)) {
                    Image improvementImage = FreeMarsImageManager.getImage("IMPROVEMENT_road_W");
                    tilePaintModel.addTerrainImprovementImage(improvementImage);
                }
            }
            Coordinate northWest = freeMarsModel.getRealm().getRelativeCoordinate(tileCoordinate, RealmConstants.NORTHWEST);
            if (northWest != null) {
                Tile northWestTile = freeMarsModel.getRealm().getTile(northWest);
                if (northWestTile.hasImprovement(road)) {
                    Image improvementImage = FreeMarsImageManager.getImage("IMPROVEMENT_road_NW");
                    tilePaintModel.addTerrainImprovementImage(improvementImage);
                }
            }
            Coordinate northEast = freeMarsModel.getRealm().getRelativeCoordinate(tileCoordinate, RealmConstants.NORTHEAST);
            if (northEast != null) {
                Tile northEastTile = freeMarsModel.getRealm().getTile(northEast);
                if (northEastTile.hasImprovement(road)) {
                    Image improvementImage = FreeMarsImageManager.getImage("IMPROVEMENT_road_NE");
                    tilePaintModel.addTerrainImprovementImage(improvementImage);
                }
            }

            Coordinate southWest = freeMarsModel.getRealm().getRelativeCoordinate(tileCoordinate, RealmConstants.SOUTHWEST);
            if (southWest != null) {
                Tile southWestTile = freeMarsModel.getRealm().getTile(southWest);
                if (southWestTile.hasImprovement(road)) {
                    Image improvementImage = FreeMarsImageManager.getImage("IMPROVEMENT_road_SW");
                    tilePaintModel.addTerrainImprovementImage(improvementImage);
                }
            }
            Coordinate southEast = freeMarsModel.getRealm().getRelativeCoordinate(tileCoordinate, RealmConstants.SOUTHEAST);
            if (southEast != null) {
                Tile southEastTile = freeMarsModel.getRealm().getTile(southEast);
                if (southEastTile.hasImprovement(road)) {
                    Image improvementImage = FreeMarsImageManager.getImage("IMPROVEMENT_road_SE");
                    tilePaintModel.addTerrainImprovementImage(improvementImage);
                }
            }

        }
    }

    private static void buildModelForUnexploredTile(TilePaintModel tilePaintModel, FreeMarsModel freeMarsModel, Coordinate tileCoordinate) {
        for (int i = 0; i < RealmConstants.directions.length; i++) {
            Direction direction = RealmConstants.directions[i];
            if (!direction.equals(RealmConstants.CENTER)) {
                Coordinate adjacentCoordinate = freeMarsModel.getRealm().getRelativeCoordinate(tileCoordinate, direction);
                if (adjacentCoordinate != null) {
                    if (freeMarsModel.getRealm().getPlayerManager().getActivePlayer().isCoordinateExplored(adjacentCoordinate)) {
                        Tile adjacentTile = freeMarsModel.getRealm().getTile(adjacentCoordinate);
                        if (adjacentTile != null) {
                            Image transitionImage = FreeMarsImageManager.getImage("TILE_" + adjacentTile.getType().getName().replaceAll(" ", "") + "_" + direction.getShortName());
                            tilePaintModel.addTerrainImage(direction, transitionImage);
                        }
                    }
                }
            }
        }
    }

    private static void checkUnitPath(TilePaintModel tilePaintModel, FreeMarsModel freeMarsModel, Coordinate tileCoordinate, Tile tile) {
        tilePaintModel.setPaintingUnitPath(false);
        if (freeMarsModel.getFreeMarsViewModel().getMapPanelUnitPath() != null) {
            if (freeMarsModel.getFreeMarsViewModel().getMapPanelUnitPath().contains(tileCoordinate)) {
                tilePaintModel.setPaintingUnitPath(true);
                tilePaintModel.setUnitPathColor(Color.RED);
            }
        }
    }
}
