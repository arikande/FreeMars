package org.freemars.controller;

import org.apache.log4j.Logger;
import org.freemars.controller.handler.PostCommandHandler;
import org.freemars.controller.viewcommand.UpdateTilePaintModelUnitsCommand;
import org.freemars.util.Utility;
import org.freerealm.command.SetActiveUnitCommand;
import org.freerealm.executor.CommandResult;
import org.freerealm.map.Coordinate;
import org.freerealm.player.Player;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class UnitOrderAssignedHandler implements PostCommandHandler {

    private static final Logger logger = Logger.getLogger(UnitOrderAssignedHandler.class);

    public void handleUpdate(FreeMarsController freeMarsController, CommandResult commandResult) {
        logger.debug("UnitOrderAssignedHandler handling command result with update type " + commandResult.getUpdateType() + ".");
        Unit unit = (Unit) commandResult.getParameter("unit");
        if (unit != null) {
            Coordinate unitCoordinate = unit.getCoordinate();
            Player humanPlayer = freeMarsController.getFreeMarsModel().getHumanPlayer();
            if (unitCoordinate != null && humanPlayer != null && humanPlayer.isCoordinateExplored(unitCoordinate)) {
                freeMarsController.executeViewCommand(new UpdateTilePaintModelUnitsCommand(freeMarsController, unit));
            }
            if (unit.getMovementPoints() == 0) {
                Unit nextUnit = Utility.getNextPlayableUnit(unit.getPlayer(), unit);
                freeMarsController.execute(new SetActiveUnitCommand(unit.getPlayer(), nextUnit));
            }
        }
    }
}
