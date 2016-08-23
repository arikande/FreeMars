package org.freemars.controller;

import org.apache.log4j.Logger;
import org.freemars.controller.handler.PostCommandHandler;
import org.freemars.controller.viewcommand.UpdateTilePaintModelUnitsCommand;
import org.freerealm.executor.CommandResult;
import org.freerealm.player.Player;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class UnitOrdersClearedHandler implements PostCommandHandler {

    private static final Logger logger = Logger.getLogger(UnitOrdersClearedHandler.class);

    public void handleUpdate(FreeMarsController freeMarsController, CommandResult commandResult) {
        logger.debug("UnitOrdersClearedHandler handling command result with update type " + commandResult.getUpdateType() + ".");
        Unit unit = (Unit) commandResult.getParameter("unit");
        Player humanPlayer = freeMarsController.getFreeMarsModel().getHumanPlayer();
        if (unit != null && humanPlayer != null && humanPlayer.isCoordinateExplored(unit.getCoordinate())) {
            freeMarsController.executeViewCommand(new UpdateTilePaintModelUnitsCommand(freeMarsController, unit));
        }
    }
}
