package org.freemars.unit.automater;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.freemars.controller.FreeMarsController;
import org.freerealm.executor.order.ClearVegetationOrder;
import org.freerealm.executor.order.ImproveTile;
import org.freerealm.executor.order.TransformTileOrder;
import org.freerealm.map.Coordinate;
import org.freerealm.map.Path;
import org.freerealm.player.Player;
import org.freerealm.player.mission.ClearTileVegetationCountMission;
import org.freerealm.player.mission.Mission;
import org.freerealm.player.mission.TileImprovementCountMission;
import org.freerealm.settlement.Settlement;
import org.freerealm.tile.Tile;
import org.freerealm.tile.improvement.TileImprovementType;
import org.freerealm.unit.FreeRealmUnitType;
import org.freerealm.unit.Order;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class EngineerAutomaterCoordinateHelper {

    private FreeMarsController freeMarsController;
    private Coordinate coordinate;
    private Unit unit;
    private TileImprovementType roadImprovementType;
    protected EngineerAutomaterPathCache pathCache;

    protected EngineerAutomaterCoordinateHelper() {
        pathCache = new EngineerAutomaterPathCache();
    }

    protected void setFreeMarsController(FreeMarsController freeMarsController) {
        this.freeMarsController = freeMarsController;
        roadImprovementType = freeMarsController.getFreeMarsModel().getRealm().getTileImprovementTypeManager().getImprovement(0);
    }

    protected void setUnit(Unit unit) {
        this.unit = unit;
    }

    protected void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    protected Path getPath(Coordinate coordinateA, Coordinate coordinateB) {
        Path path = pathCache.getPath(coordinateA, coordinateB);
        if (path == null) {
            path = freeMarsController.getFreeMarsModel().getRealm().getPathFinder().findPath(unit, coordinateA, coordinateB);
            if (path != null) {
                pathCache.addPath(coordinateA, coordinateB, path);
            }
        }
        return path;
    }

    protected boolean isCoordinateImprovable() {
        Tile tile = freeMarsController.getFreeMarsModel().getTile(coordinate);
        if (!isVegetationClearingInProgress(tile) && (tile.getVegetation() != null || isCoordinateTransformable() || getNextImprovementForCoordinate() != null)) {
            return true;
        }
        return false;
    }

    protected boolean isCoordinateTransformable() {
        Tile tile = freeMarsController.getFreeMarsModel().getTile(coordinate);
        if (tile.getType().getId() == 5 && !isTransformationInProgress(tile)) {
            return true;
        }
        return false;
    }

    protected TileImprovementType getNextImprovementForCoordinate() {
        ArrayList<Settlement> settlementsNearCoordinate = freeMarsController.getFreeMarsModel().getRealm().getSettlementsNearCoordinate(coordinate, 1, 1, unit.getPlayer());
        Tile tile = freeMarsController.getFreeMarsModel().getTile(coordinate);
        Iterator<TileImprovementType> iterator = freeMarsController.getFreeMarsModel().getRealm().getTileImprovementTypeManager().getImprovementsIterator();
        while (iterator.hasNext()) {
            TileImprovementType tileImprovementType = iterator.next();
            if (tileImprovementType.equals(roadImprovementType) || !settlementsNearCoordinate.isEmpty() || tile.getSettlement() != null) {
                if (tileImprovementType.canBeBuiltOnTileType(tile.getType()) && !tile.hasImprovement(tileImprovementType)) {
                    if (!improvementInProgress(tile, tileImprovementType)) {
                        return tileImprovementType;
                    }
                }
            }
        }
        return null;
    }

    protected int getPointsForCoordinate() {
        return getPositivePoints() - getNegativePoints();
    }

    private int getPositivePoints() {
        int positivePoints = 0;
        Tile tile = freeMarsController.getFreeMarsModel().getTile(coordinate);
        if (tile.getSettlement() != null) {
            positivePoints = positivePoints + 20;
        }
        Iterator<Settlement> iterator = unit.getPlayer().getSettlementsIterator();
        while (iterator.hasNext()) {
            Settlement settlement = iterator.next();
            if (settlement.getWorkForceManager().getAssignedWorkforceForTile(coordinate) != null) {
                int populationPoints = settlement.getPopulation() / 50;
                positivePoints = positivePoints + 15 + populationPoints;
            }
        }
        ArrayList<Settlement> settlementsNearCoordinate = freeMarsController.getFreeMarsModel().getRealm().getSettlementsNearCoordinate(coordinate, 1, 1, unit.getPlayer());
        if (!settlementsNearCoordinate.isEmpty()) {
            positivePoints = positivePoints + 10;
        }
        if (tile.getBonusResource() != null) {
            positivePoints = positivePoints + 8;
        }
        if (tile.getType().getId() == 2) {
            positivePoints = positivePoints + 5;
        }
        Player player = unit.getPlayer();
        if (tile.getVegetation() != null) {
            positivePoints = positivePoints + 3;
            Mission mission = getActiveMissionOfType(player, ClearTileVegetationCountMission.class);
           if (mission != null) {
               positivePoints = positivePoints + 15;
           }
        }
        if (!tile.hasImprovement(roadImprovementType)) {
            positivePoints = positivePoints + 3;
            
            
           Mission mission = getActiveMissionOfType(player, TileImprovementCountMission.class);
           if (mission != null) {
               TileImprovementCountMission tileImprovementCountMission = (TileImprovementCountMission) mission;
                    if (tileImprovementCountMission.getTileImprovementId() == roadImprovementType.getId()) {
                        positivePoints = positivePoints + 15;
                    }
           }
            
            /*
            Iterator<Mission> missionsIterator = player.getMissionsIterator();
            while (missionsIterator.hasNext()) {
                Mission mission = missionsIterator.next();
                if (mission.getStatus() == Mission.STATUS_ACTIVE && mission instanceof TileImprovementCountMission) {
                    TileImprovementCountMission tileImprovementCountMission = (TileImprovementCountMission) mission;
                    if (tileImprovementCountMission.getTileImprovementId() == roadImprovementType.getId()) {
                        positivePoints = positivePoints + 15;
                    }
                }
            }
            */
        }
        return positivePoints;
    }

    private Mission getActiveMissionOfType(Player player, Class<?> clazz) {
        Iterator<Mission> missionsIterator = player.getMissionsIterator();
        while (missionsIterator.hasNext()) {
            Mission mission = missionsIterator.next();
            if (mission.getStatus() == Mission.STATUS_ACTIVE && clazz.isInstance(mission) ) {
                return mission;
            }
        }
        return null;
    }

    private int getNegativePoints() {
        int negativePoints = 0;
        Tile tile = freeMarsController.getFreeMarsModel().getTile(coordinate);
        if (!unit.getCoordinate().equals(coordinate)) {
            Path path = getPath(unit.getCoordinate(), coordinate);
            if (path != null) {
                negativePoints = negativePoints + 3 * path.getLength();
            } else {
                negativePoints = negativePoints + 20;
            }
        }
        negativePoints = negativePoints + 2 * getImprovementCountInNeighbors();
        if (engineerNearCoordinate(unit.getPlayer())) {
            negativePoints = negativePoints + 5;
        }
        if (tile.getType().getId() == 5) {
            negativePoints = negativePoints + 5;
        }
        return negativePoints;
    }

    private int getImprovementCountInNeighbors() {
        int improvementCountInNeighbors = 0;
        List<Coordinate> circleCoordinates = freeMarsController.getFreeMarsModel().getRealm().getCircleCoordinates(coordinate, 1);
        for (Coordinate checkCoordinate : circleCoordinates) {
            Tile tile = freeMarsController.getFreeMarsModel().getTile(checkCoordinate);
            improvementCountInNeighbors = improvementCountInNeighbors + tile.getImprovementCount();
        }
        return improvementCountInNeighbors;
    }

    private boolean engineerNearCoordinate(Player player) {
        FreeRealmUnitType engineerUnitType = freeMarsController.getFreeMarsModel().getRealm().getUnitTypeManager().getUnitType(1);
        ArrayList<Coordinate> nearCoordinates = new ArrayList<Coordinate>();
        nearCoordinates.addAll(freeMarsController.getFreeMarsModel().getRealm().getCircleCoordinates(coordinate, 1));
        nearCoordinates.addAll(freeMarsController.getFreeMarsModel().getRealm().getCircleCoordinates(coordinate, 2));
        for (Coordinate checkCoordinate : nearCoordinates) {
            Tile tile = freeMarsController.getFreeMarsModel().getTile(checkCoordinate);
            Iterator<Unit> tileUnits = tile.getUnitsIterator();
            while (tileUnits.hasNext()) {
                Unit tileUnit = tileUnits.next();
                if (tileUnit.getType().equals(engineerUnitType) && tileUnit.getPlayer().equals(player) && tileUnit.getAutomater() != null) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean improvementInProgress(Tile tile, TileImprovementType improvement) {
        Iterator<Unit> unitsInTile = tile.getUnitsIterator();
        while (unitsInTile.hasNext()) {
            Unit tileUnit = unitsInTile.next();
            if (tileUnit.getCurrentOrder() != null && tileUnit.getCurrentOrder() instanceof ImproveTile) {
                ImproveTile improveTile = (ImproveTile) tileUnit.getCurrentOrder();
                if (improveTile.getTileImprovementType().equals(improvement)) {
                    return true;
                }
            }
            Iterator<Order> ordersIterator = tileUnit.getOrdersIterator();
            while (ordersIterator.hasNext()) {
                Order order = ordersIterator.next();
                if (order instanceof ImproveTile) {
                    ImproveTile improveTile = (ImproveTile) order;
                    if (improveTile.getTileImprovementType().equals(improvement)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean isVegetationClearingInProgress(Tile tile) {
        Iterator<Unit> unitsInTile = tile.getUnitsIterator();
        while (unitsInTile.hasNext()) {
            Unit tileUnit = unitsInTile.next();
            if (tileUnit.getCurrentOrder() != null && tileUnit.getCurrentOrder() instanceof ClearVegetationOrder) {
                return true;
            }
        }
        return false;
    }

    private boolean isTransformationInProgress(Tile tile) {
        Iterator<Unit> unitsInTile = tile.getUnitsIterator();
        while (unitsInTile.hasNext()) {
            Unit tileUnit = unitsInTile.next();
            if (tileUnit.getCurrentOrder() != null && tileUnit.getCurrentOrder() instanceof TransformTileOrder) {
                return true;
            }
        }
        return false;
    }
}
