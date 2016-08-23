package org.freemars.controller;

import org.apache.log4j.Logger;
import org.freemars.controller.handler.PostCommandHandler;
import org.freemars.controller.viewcommand.UpdateTilePaintModelUnitsCommand;
import org.freerealm.executor.CommandResult;
import org.freerealm.map.Coordinate;
import org.freerealm.player.Player;

/**
 *
 * @author Deniz ARIKAN
 */
public class UnitStatusSuspendedHandler implements PostCommandHandler {

    private static final Logger logger = Logger.getLogger(UnitStatusSuspendedHandler.class);

    public void handleUpdate(FreeMarsController freeMarsController, CommandResult commandResult) {
        logger.debug("UnitStatusSuspendedHandler handling command result with update type " + commandResult.getUpdateType() + ".");
        Coordinate coordinate = (Coordinate) commandResult.getParameter("coordinate");
        if (coordinate != null) {
            Player humanPlayer = freeMarsController.getFreeMarsModel().getHumanPlayer();
            if (humanPlayer != null && humanPlayer.isCoordinateExplored(coordinate)) {
                freeMarsController.executeViewCommand(new UpdateTilePaintModelUnitsCommand(freeMarsController, coordinate));
            }
        }
    }

}
