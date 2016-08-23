package org.freemars.unit.automater;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.freemars.controller.FreeMarsController;
import org.freemars.tile.SpaceshipDebrisCollectable;
import org.freerealm.command.UnitAdvanceToCoordinateCommand;
import org.freerealm.executor.CommandResult;
import org.freerealm.map.Coordinate;
import org.freerealm.map.Path;
import org.freerealm.tile.Tile;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class ExplorationHelper {

    private static final Logger logger = Logger.getLogger(ExplorationHelper.class);

    public static boolean manageUnitInExplorationMode(FreeMarsController freeMarsController, Unit unit) {
        logger.trace("Using " + unit.getName() + " as explorer.");
        Coordinate explorationCoordinate = ExplorationHelper.findExplorationCoordinate(freeMarsController, unit);
        if (explorationCoordinate != null) {
            logger.trace("Exploration coordinate is " + explorationCoordinate + ". Trying to advance to coordinate.");
            UnitAdvanceToCoordinateCommand unitAdvanceToCoordinateCommand
                    = new UnitAdvanceToCoordinateCommand(freeMarsController.getFreeMarsModel().getRealm(), unit, explorationCoordinate);
            CommandResult commandResult = freeMarsController.execute(unitAdvanceToCoordinateCommand);
            if (commandResult.getCode() == CommandResult.RESULT_OK) {
                logger.trace("Successfully advanced to coordinate " + explorationCoordinate + ".");
            }
            return commandResult.getCode() == CommandResult.RESULT_OK;
        } else {
            logger.trace("Exploration coordinate is null.");
            return false;
        }
    }

    private static Coordinate findExplorationCoordinate(FreeMarsController freeMarsController, Unit unit) {
        for (int i = 1; i < 10; i++) {
            List<Coordinate> circleCoordinates = freeMarsController.getFreeMarsModel().getRealm().getCircleCoordinates(unit.getCoordinate(), i);
            List<Coordinate> candidateCoordinates = new ArrayList<Coordinate>();
            for (Coordinate coordinate : circleCoordinates) {
                if (isCoordinateOkForScouting(freeMarsController, unit, coordinate)) {
                    candidateCoordinates.add(coordinate);
                }
            }
            Collections.shuffle(candidateCoordinates);
            for (Coordinate candidateCoordinate : candidateCoordinates) {
                Tile tile = freeMarsController.getFreeMarsModel().getTile(candidateCoordinate);
                if (unit.getPlayer().getSettlementCount() > 0 && tile.getCollectable() != null && (tile.getCollectable() instanceof SpaceshipDebrisCollectable)) {
                    return candidateCoordinate;
                }
            }
        }
        for (int i = 1; i < 10; i++) {
            List<Coordinate> circleCoordinates = freeMarsController.getFreeMarsModel().getRealm().getCircleCoordinates(unit.getCoordinate(), i);
            List<Coordinate> candidateCoordinates = new ArrayList<Coordinate>();
            for (Coordinate coordinate : circleCoordinates) {
                if (isCoordinateOkForScouting(freeMarsController, unit, coordinate)) {
                    candidateCoordinates.add(coordinate);
                }
            }
            Collections.shuffle(candidateCoordinates);
            for (int j = 4; j > 0; j--) {
                for (Coordinate candidateCoordinate : candidateCoordinates) {
                    if (getUnexploredNeighborCount(freeMarsController, unit, candidateCoordinate) >= j) {
                        return candidateCoordinate;
                    }
                }
                for (Coordinate candidateCoordinate : candidateCoordinates) {
                    if (getUnexploredNeighborCount(freeMarsController, unit, candidateCoordinate) >= j) {
                        return candidateCoordinate;
                    }
                }
            }
        }
        return null;
    }

    private static boolean isCoordinateOkForScouting(FreeMarsController freeMarsController, Unit unit, Coordinate coordinate) {
        if (coordinate == null) {
            return false;
        }
        if (!unit.getPlayer().isCoordinateExplored(coordinate)) {
            return false;
        }
        Tile tile = freeMarsController.getFreeMarsModel().getTile(coordinate);
        if (tile == null) {
            return false;
        }
        Path path = freeMarsController.getFreeMarsModel().getRealm().getPathFinder().findPath(unit, coordinate);
        if (path == null) {
            return false;
        }
        return true;
    }

    private static int getUnexploredNeighborCount(FreeMarsController freeMarsController, Unit unit, Coordinate coordinate) {
        int unexploredNeighborCount = 0;
        List<Coordinate> neighbors = freeMarsController.getFreeMarsModel().getRealm().getCircleCoordinates(coordinate, 1);
        for (Coordinate neighbor : neighbors) {
            if (!unit.getPlayer().isCoordinateExplored(neighbor)) {
                unexploredNeighborCount = unexploredNeighborCount + 1;
            }
        }
        return unexploredNeighborCount;
    }
}
