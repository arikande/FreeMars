package org.freemars.controller;

import java.util.Iterator;

import org.apache.log4j.Logger;
import org.freemars.ai.AIPlayer;
import org.freemars.controller.handler.PostCommandHandler;
import org.freemars.earth.EarthFlightProperty;
import org.freemars.player.FreeMarsPlayer;
import org.freerealm.command.SignalPlayerEndTurnCommand;
import org.freerealm.executor.CommandResult;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class AutoEndTurnHandler implements PostCommandHandler {

    public void handleUpdate(FreeMarsController controller, CommandResult commandResult) {
        FreeMarsPlayer activePlayer = (FreeMarsPlayer) controller.getFreeMarsModel().getActivePlayer();
        if (activePlayer != null && !(activePlayer instanceof AIPlayer)) {
            if (isAutoEndingTurn(controller, activePlayer)) {
                activePlayer.setAutoEndTurnPossible(false);
                Logger.getLogger(AutoEndTurnHandler.class).info("Auto ending turn " + controller.getFreeMarsModel().getNumberOfTurns() + " for player " + activePlayer + ".");
                controller.execute(new SignalPlayerEndTurnCommand(activePlayer));
            }
        }
    }

    private boolean isAutoEndingTurn(FreeMarsController controller, FreeMarsPlayer freeMarsPlayer) {
        boolean autoEndTurn = Boolean.valueOf(controller.getFreeMarsModel().getFreeMarsPreferences().getProperty("auto_end_turn"));
        if (!autoEndTurn || !freeMarsPlayer.isAutoEndTurnPossible()) {
            return false;
        }
        Iterator<Unit> unitsIterator = freeMarsPlayer.getUnitsIterator();
        while (unitsIterator.hasNext()) {
            Unit playerUnit = unitsIterator.next();
            if (playerUnit.getStatus() == Unit.UNIT_ACTIVE && playerUnit.getMovementPoints() != 0 && playerUnit.getCurrentOrder() == null) {
                return false;
            }
        }
        if (isThereAManageableSpaceshipInAColony(freeMarsPlayer)) {
            return false;
        }
        return true;
    }

    private boolean isThereAManageableSpaceshipInAColony(FreeMarsPlayer freeMarsPlayer) {
        Iterator<Unit> unitsIterator = freeMarsPlayer.getUnitsIterator();
        while (unitsIterator.hasNext()) {
            Unit playerUnit = unitsIterator.next();
            if (playerUnit.getStatus() == Unit.UNIT_ACTIVE && playerUnit.getType().getProperty(EarthFlightProperty.NAME) != null && !playerUnit.isSkippedForCurrentTurn()) {
                return true;
            }
        }
        return false;
    }
}
