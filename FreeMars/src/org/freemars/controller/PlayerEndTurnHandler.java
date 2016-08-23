package org.freemars.controller;

import java.util.Iterator;

import org.apache.log4j.Logger;
import org.freemars.controller.handler.PostCommandHandler;
import org.freemars.model.FreeMarsModel;
import org.freemars.player.FreeMarsPlayer;
import org.freerealm.Realm;
import org.freerealm.command.AddPlayerTurnHistoryCommand;
import org.freerealm.command.EndCurrentTurnCommand;
import org.freerealm.command.SetActivePlayerCommand;
import org.freerealm.executor.CommandResult;
import org.freerealm.history.FreeRealmPlayerTurnHistoryFactory;
import org.freerealm.history.PlayerTurnHistory;
import org.freerealm.player.Player;

/**
 *
 * @author Deniz ARIKAN
 */
public class PlayerEndTurnHandler implements PostCommandHandler {

    private static final Logger logger = Logger.getLogger(PlayerEndTurnHandler.class);

    public void handleUpdate(FreeMarsController freeMarsController, CommandResult commandResult) {
        logger.debug("PlayerEndTurnHandler handling command result with update type " + commandResult.getUpdateType() + ".");
        FreeMarsModel model = freeMarsController.getFreeMarsModel();
        boolean turnEndedForAllPlayers = true;
        Iterator<Player> playerIterator = model.getRealm().getPlayerManager().getPlayersIterator();
        while (playerIterator.hasNext()) {
            Player checkPlayer = playerIterator.next();
            if (!checkPlayer.isTurnEnded()) {
                turnEndedForAllPlayers = false;
                break;
            }
        }
        Realm realm = freeMarsController.getFreeMarsModel().getRealm();
        if (turnEndedForAllPlayers) {
            playerIterator = model.getRealm().getPlayerManager().getPlayersIterator();
            while (playerIterator.hasNext()) {
                FreeMarsPlayer player = (FreeMarsPlayer) playerIterator.next();
                PlayerTurnHistory playerTurnHistory = new FreeRealmPlayerTurnHistoryFactory().preparePlayerHistory(model.getRealm(), player);
                playerTurnHistory.addCustomData("earthTaxRate", String.valueOf(player.getEarthTaxRate()));
                freeMarsController.execute(new AddPlayerTurnHistoryCommand(realm, player, playerTurnHistory));
            }
            logger.info("All players completed their turn. Ending turn " + model.getNumberOfTurns() + ".");
            freeMarsController.addCommandToQueue(new EndCurrentTurnCommand(freeMarsController.getFreeMarsModel().getRealm()));
        } else {
            Player nextPlayer = model.getRealm().getPlayerManager().getNextPlayer(model.getActivePlayer());
            freeMarsController.addCommandToQueue(new SetActivePlayerCommand(realm, nextPlayer));
        }
    }
}
