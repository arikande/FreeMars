package org.freemars.controller;

import java.util.Iterator;

import org.apache.log4j.Logger;
import org.freemars.ai.AIPlayer;
import org.freemars.ai.ExpeditionaryForcePlayer;
import org.freemars.controller.handler.PostCommandHandler;
import org.freemars.player.FreeMarsPlayer;
import org.freerealm.command.SignalPlayerEndTurnCommand;
import org.freerealm.executor.CommandResult;
import org.freerealm.player.Player;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class ActivePlayerHandler implements PostCommandHandler {

    public void handleUpdate(FreeMarsController freeMarsController, CommandResult commandResult) {
        FreeMarsPlayer activePlayer = freeMarsController.getFreeMarsModel().getActivePlayer();
        if (activePlayer.getStatus() != Player.STATUS_REMOVED) {
            if (activePlayer instanceof AIPlayer) {
                new FreeMarsAIPlayerActivatedHandler((AIPlayer) activePlayer).handleUpdate(freeMarsController, commandResult);
            } else if (activePlayer instanceof ExpeditionaryForcePlayer) {
                new ExpeditionaryForcePlayerActivatedHandler((ExpeditionaryForcePlayer) activePlayer).handleUpdate(freeMarsController, commandResult);
            } else {
                new HumanPlayerActivatedHandler(activePlayer).handleUpdate(freeMarsController, commandResult);
            }
        } else {
            String logInfo = "Status of player with id ";
            logInfo = logInfo + activePlayer.getId() + " and name \"" + activePlayer.getName() + "\" is \"removed\". Auto skipping turn.";
            Logger.getLogger(ActivePlayerHandler.class).info(logInfo);
            freeMarsController.execute(new SignalPlayerEndTurnCommand(activePlayer));
        }
    }

    protected void manageAutomatedUnits(Player player) {
        Iterator<Unit> unitsIterator = player.getUnitsIterator();
        while (unitsIterator.hasNext()) {
            Unit playerUnit = unitsIterator.next();
            if (playerUnit.getAutomater() != null) {
                playerUnit.getAutomater().automate();
            }
        }
    }
}
