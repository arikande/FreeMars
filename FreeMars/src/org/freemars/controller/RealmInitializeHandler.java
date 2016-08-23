package org.freemars.controller;

import org.freemars.controller.handler.PostCommandHandler;
import org.freemars.controller.viewcommand.SetCenteredCoordinateCommand;
import org.freemars.model.FreeMarsModel;
import org.freerealm.executor.CommandResult;
import org.freerealm.player.Player;
import org.freerealm.unit.Unit;

/**
 * @author Deniz ARIKAN
 */
public class RealmInitializeHandler implements PostCommandHandler {

    public void handleUpdate(FreeMarsController freeMarsController, CommandResult commandResult) {
        FreeMarsModel freeMarsModel = freeMarsController.getFreeMarsModel();
        Player activePlayer = freeMarsModel.getActivePlayer();
        Unit activeUnit = activePlayer.getActiveUnit();
        if (activeUnit != null) {
            freeMarsController.executeViewCommand(new SetCenteredCoordinateCommand(freeMarsController, activeUnit.getCoordinate()));
        }
    }
}
