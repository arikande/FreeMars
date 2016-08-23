package org.freemars.controller;

import org.apache.log4j.Logger;
import org.freemars.controller.handler.PostCommandHandler;
import org.freemars.controller.viewcommand.DisplayUnitMovementCommand;
import org.freemars.controller.viewcommand.UpdateTilePaintModelUnitsCommand;
import org.freemars.model.FreeMarsModel;
import org.freerealm.executor.CommandResult;
import org.freerealm.map.Coordinate;
import org.freerealm.map.Direction;
import org.freerealm.player.Player;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class UnitMovementHandler implements PostCommandHandler {

    private static final Logger logger = Logger.getLogger(UnitMovementHandler.class);

    public void handleUpdate(FreeMarsController freeMarsController, CommandResult commandResult) {
        if (freeMarsController.getFreeMarsModel().getMode() == FreeMarsModel.SIMULATION_MODE) {
            return;
        }
        logger.debug("UnitMovementHandler handling command result with update type " + commandResult.getUpdateType() + ".");
        Unit unit = (Unit) commandResult.getParameter("unit");
        Coordinate previousCoordinate = (Coordinate) commandResult.getParameter("previousCoordinate");
        Direction direction = freeMarsController.getFreeMarsModel().getRealm().getDirection(previousCoordinate, unit.getCoordinate());
        if (direction != null) {
            unit.addCustomProperty("direction", direction.getShortName());
        }
        if (isDisplayingUnitMoveAnimation(freeMarsController, previousCoordinate, unit.getCoordinate())) {
            Coordinate newCoordinate = (Coordinate) commandResult.getParameter("newCoordinate");
            boolean pauseAfterMovement = unit.getMovementPoints() == 0;
            DisplayUnitMovementCommand displayUnitMovementCommand = new DisplayUnitMovementCommand(freeMarsController, unit, previousCoordinate, newCoordinate, pauseAfterMovement);
            freeMarsController.executeViewCommand(displayUnitMovementCommand);
        } else {
            Player humanPlayer = freeMarsController.getFreeMarsModel().getHumanPlayer();
            if (humanPlayer != null) {
                if (humanPlayer.isCoordinateExplored(previousCoordinate)) {
                    freeMarsController.executeViewCommand(new UpdateTilePaintModelUnitsCommand(freeMarsController, previousCoordinate));
                }
                if (humanPlayer.isCoordinateExplored(unit.getCoordinate())) {
                    freeMarsController.executeViewCommand(new UpdateTilePaintModelUnitsCommand(freeMarsController, unit));
                }
            }
        }
    }

    private boolean isDisplayingUnitMoveAnimation(FreeMarsController freeMarsController, Coordinate previousCoordinate, Coordinate newCoordinate) {
        boolean displayUnitMoveAnimation = Boolean.valueOf(freeMarsController.getFreeMarsModel().getFreeMarsPreferences().getProperty("display_unit_move_animation"));
        if (!displayUnitMoveAnimation) {
            return false;
        }
        Player humanPlayer = freeMarsController.getFreeMarsModel().getHumanPlayer();
        if (!humanPlayer.isCoordinateExplored(previousCoordinate) && !humanPlayer.isCoordinateExplored(newCoordinate)) {
            return false;
        }
        if (!freeMarsController.getFreeMarsModel().isMapPanelDisplayingCoordinate(newCoordinate)) {
            return false;
        }
        return true;
    }
}
