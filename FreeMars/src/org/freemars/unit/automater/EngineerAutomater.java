package org.freemars.unit.automater;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.freemars.colony.FreeMarsColony;
import org.freemars.controller.FreeMarsController;
import org.freerealm.command.UnitAdvanceToCoordinateCommand;
import org.freerealm.command.UnitOrderAddCommand;
import org.freerealm.executor.CommandResult;
import org.freerealm.executor.order.ClearVegetationOrder;
import org.freerealm.executor.order.ImproveTile;
import org.freerealm.executor.order.TransformTileOrder;
import org.freerealm.map.Coordinate;
import org.freerealm.map.Path;
import org.freerealm.player.Player;
import org.freerealm.settlement.Settlement;
import org.freerealm.tile.Tile;
import org.freerealm.tile.TileType;
import org.freerealm.tile.improvement.TileImprovementType;
import org.freerealm.unit.AbstractUnitAutomater;
import org.freerealm.unit.FreeRealmUnitType;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class EngineerAutomater extends AbstractUnitAutomater {

    private static final Logger logger = Logger.getLogger(EngineerAutomater.class);
    private static final String NAME = "freeMarsEngineerAutomater";
    private FreeMarsController freeMarsController;
    private Coordinate targetCoordinate;
    private final EngineerAutomaterCoordinateHelper engineerAutomaterCoordinateHelper;

    public EngineerAutomater() {
        engineerAutomaterCoordinateHelper = new EngineerAutomaterCoordinateHelper();
    }

    @Override
    public void setUnit(Unit unit) {
        super.setUnit(unit);
        engineerAutomaterCoordinateHelper.setUnit(unit);
    }

    public void setFreeMarsController(FreeMarsController freeMarsController) {
        this.freeMarsController = freeMarsController;
        engineerAutomaterCoordinateHelper.setFreeMarsController(freeMarsController);
    }

    public String getName() {
        return NAME;
    }

    public void automate() {
        logger.trace("Engineer automater running for \"" + getUnit().getName() + "\".");
        if (getUnit().getCurrentOrder() != null) {
            logger.trace("\"" + getUnit().getName() + "\" has a current order. Exiting automater.");
            return;
        }
        ArrayList<Coordinate> possibleCoordinates = new ArrayList<Coordinate>();
        ArrayList<Coordinate> workForceCoordinates = getWorkForceCoordinates();
        for (Coordinate workForceCoordinate : workForceCoordinates) {
            if (!possibleCoordinates.contains(workForceCoordinate)) {
                possibleCoordinates.add(workForceCoordinate);
            }
        }
        ArrayList<Coordinate> colonyPathCoordinates = getColonyPathCoordinates();
        for (Coordinate colonyPathCoordinate : colonyPathCoordinates) {
            if (!possibleCoordinates.contains(colonyPathCoordinate)) {
                possibleCoordinates.add(colonyPathCoordinate);
            }
        }
        ArrayList<CoordinateAssignment> possibleAssignments = new ArrayList<CoordinateAssignment>();
        for (Coordinate coordinate : possibleCoordinates) {
            engineerAutomaterCoordinateHelper.setCoordinate(coordinate);
            if (engineerAutomaterCoordinateHelper.isCoordinateImprovable() && !isCoordinateTargetedByEngineer(coordinate)) {
                CoordinateAssignment coordinateAssignment = new CoordinateAssignment();
                coordinateAssignment.setCoordinate(coordinate);
                int pointsForCoordinate = engineerAutomaterCoordinateHelper.getPointsForCoordinate();
                coordinateAssignment.setPoints(pointsForCoordinate);
                possibleAssignments.add(coordinateAssignment);
            }
        }
        Collections.sort(possibleAssignments);
        Collections.reverse(possibleAssignments);
        if (possibleAssignments.isEmpty()) {
            String log = "Automater for \"" + getUnit().getName() + "\" could not find any assignments in turn " + freeMarsController.getFreeMarsModel().getNumberOfTurns() + ". Removing automater for \"" + getUnit().getName() + "\".";
            logger.trace(log);
            getUnit().setAutomater(null);
            return;
        }
        targetCoordinate = possibleAssignments.get(0).getCoordinate();
        if (logger.isTraceEnabled()) {
            logger.trace("There are " + possibleAssignments.size() + " possible assignments.");
            logger.trace("Next assignment is at " + targetCoordinate + ".");
            logger.trace("Current coordinate of " + getUnit().getName() + " is " + getUnit().getCoordinate() + ".");
        }
        UnitAdvanceToCoordinateCommand unitAdvanceToCoordinateCommand
                = new UnitAdvanceToCoordinateCommand(freeMarsController.getFreeMarsModel().getRealm(), getUnit(), targetCoordinate);
        CommandResult commandResult = freeMarsController.execute(unitAdvanceToCoordinateCommand);
        if (commandResult.getCode() == CommandResult.RESULT_OK) {
            if (getUnit().getCoordinate().equals(targetCoordinate)) {
                Tile tile = freeMarsController.getFreeMarsModel().getTile(targetCoordinate);
                if (tile.getVegetation() != null) {
                    ClearVegetationOrder clearVegetation = new ClearVegetationOrder(freeMarsController.getFreeMarsModel().getRealm());
                    clearVegetation.setTurnGiven(freeMarsController.getFreeMarsModel().getRealm().getNumberOfTurns());
                    clearVegetation.setUnit(getUnit());
                    freeMarsController.execute(new UnitOrderAddCommand(freeMarsController.getFreeMarsModel().getRealm(), getUnit(), clearVegetation));
                } else if (engineerAutomaterCoordinateHelper.isCoordinateTransformable()) {
                    TransformTileOrder transformTileOrder = new TransformTileOrder(freeMarsController.getFreeMarsModel().getRealm());
                    transformTileOrder.setTurnGiven(freeMarsController.getFreeMarsModel().getNumberOfTurns());
                    transformTileOrder.setUnit(getUnit());
                    TileType transformToTileType = null;
                    Iterator<Integer> iterator = tile.getType().getTransformableTileTypeIdsIterator();
                    while (iterator.hasNext()) {
                        Integer integer = iterator.next();
                        transformToTileType = freeMarsController.getFreeMarsModel().getTileType(integer);
                    }
                    transformTileOrder.setTileType(transformToTileType);
                    freeMarsController.execute(new UnitOrderAddCommand(freeMarsController.getFreeMarsModel().getRealm(), getUnit(), transformTileOrder));
                } else {
                    engineerAutomaterCoordinateHelper.setCoordinate(targetCoordinate);
                    TileImprovementType tileImprovementType = engineerAutomaterCoordinateHelper.getNextImprovementForCoordinate();
                    if (tileImprovementType != null) {
                        ImproveTile buildTileImprovement = new ImproveTile(freeMarsController.getFreeMarsModel().getRealm());
                        buildTileImprovement.setTurnGiven(freeMarsController.getFreeMarsModel().getNumberOfTurns());
                        buildTileImprovement.setUnit(getUnit());
                        buildTileImprovement.setTileImprovementType(tileImprovementType);
                        buildTileImprovement.setSymbol(String.valueOf(tileImprovementType.getSymbol()));
                        freeMarsController.execute(new UnitOrderAddCommand(freeMarsController.getFreeMarsModel().getRealm(), getUnit(), buildTileImprovement));
                    }
                }
            }
        } else if (commandResult.getCode() == CommandResult.RESULT_ERROR) {
            ExplorationHelper.manageUnitInExplorationMode(freeMarsController, getUnit());
        }
    }

    private boolean isCoordinateTargetedByEngineer(Coordinate coordinate) {
        FreeRealmUnitType engineerType = freeMarsController.getFreeMarsModel().getRealm().getUnitTypeManager().getUnitType(1);
        Player player = getUnit().getPlayer();
        Iterator<Unit> unitsIterator = player.getUnitsIterator();
        while (unitsIterator.hasNext()) {
            Unit unit = unitsIterator.next();
            if (unit.getType().equals(engineerType) && !unit.equals(getUnit()) && unit.getAutomater() != null && unit.getAutomater() instanceof EngineerAutomater) {
                EngineerAutomater engineerAutomater = (EngineerAutomater) unit.getAutomater();
                if (coordinate.equals(engineerAutomater.getTargetCoordinate())) {
                    return true;
                }
            }
        }
        return false;
    }

    private Coordinate getTargetCoordinate() {
        return targetCoordinate;
    }

    private ArrayList<Coordinate> getWorkForceCoordinates() {
        ArrayList<Coordinate> workForceCoordinates = new ArrayList<Coordinate>();
        Iterator<Settlement> iterator = getUnit().getPlayer().getSettlementsIterator();
        while (iterator.hasNext()) {
            FreeMarsColony freeMarsColony = (FreeMarsColony) iterator.next();
            Iterator<Coordinate> validWorkForceCoordinatesIterator = freeMarsColony.getValidWorkForceCoordinates().iterator();
            while (validWorkForceCoordinatesIterator.hasNext()) {
                Coordinate coordinate = validWorkForceCoordinatesIterator.next();
                if (!workForceCoordinates.contains(coordinate)) {
                    workForceCoordinates.add(coordinate);
                }
            }
        }
        return workForceCoordinates;
    }

    private ArrayList<Coordinate> getColonyPathCoordinates() {
        ArrayList<Coordinate> colonyPathCoordinates = new ArrayList<Coordinate>();
        ArrayList<Settlement> processedSettlements = new ArrayList<Settlement>();
        Player player = getUnit().getPlayer();
        Iterator<Settlement> fromSettlementIterator = player.getSettlementsIterator();
        while (fromSettlementIterator.hasNext()) {
            Settlement fromSettlement = fromSettlementIterator.next();
            Iterator<Settlement> toSettlementIterator = player.getSettlementsIterator();
            while (toSettlementIterator.hasNext()) {
                Settlement toSettlement = toSettlementIterator.next();
                if (!fromSettlement.equals(toSettlement) && !processedSettlements.contains(toSettlement)) {
//                    Path path = freeMarsModel.getRealm().getPathFinder().findPath(getUnit(), fromSettlement.getCoordinate(), toSettlement.getCoordinate(), true);
                    Path path = engineerAutomaterCoordinateHelper.getPath(fromSettlement.getCoordinate(), toSettlement.getCoordinate());
                    if (path != null) {
                        for (int i = 0; i < path.getLength(); i++) {
                            Coordinate pathCoordinate = path.getStep(i);
                            if (!colonyPathCoordinates.contains(pathCoordinate)) {
                                colonyPathCoordinates.add(pathCoordinate);
                            }
                        }
                    }
                    processedSettlements.add(fromSettlement);
                }
            }
        }
        return colonyPathCoordinates;
    }
}
