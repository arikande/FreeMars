package org.freemars.controller;

import org.freemars.ai.ExpeditionaryForcePlayer;
import org.freemars.controller.handler.PostCommandHandler;
import org.freemars.message.ExpeditionaryForceDefeatedMessage;
import org.freemars.model.FreeMarsModel;
import org.freerealm.executor.CommandResult;
import org.freerealm.player.Player;
import org.freerealm.player.PlayerRemovedMessage;

/**
 *
 * @author Deniz ARIKAN
 */
public class PlayerRemovedHandler implements PostCommandHandler {

    public void handleUpdate(FreeMarsController controller, CommandResult commandResult) {
        FreeMarsModel model = controller.getFreeMarsModel();
        Player removedPlayer = (Player) commandResult.getParameter("player");
        if (!removedPlayer.equals(model.getHumanPlayer())) {
            if (isExpeditionaryForceDefeated(model, removedPlayer)) {
                ExpeditionaryForceDefeatedMessage message = new ExpeditionaryForceDefeatedMessage();
                message.setSubject("Player defeated");
                message.setText("We have defeated the " + removedPlayer);
                message.setTurnSent(model.getNumberOfTurns());
                message.setExpeditionaryForcePlayer((ExpeditionaryForcePlayer) removedPlayer);
                model.getHumanPlayer().addMessage(message);
            } else {
                PlayerRemovedMessage message = new PlayerRemovedMessage();
                message.setSubject("Player defeated");
                message.setText(removedPlayer.getNation().getCountryName() + " has withdrawn from colonization of Mars");
                message.setTurnSent(model.getNumberOfTurns());
                message.setPlayer(removedPlayer);
                model.getHumanPlayer().addMessage(message);
            }
        }
    }

    private boolean isExpeditionaryForceDefeated(FreeMarsModel model, Player removedPlayer) {
        if (removedPlayer instanceof ExpeditionaryForcePlayer) {
            ExpeditionaryForcePlayer expeditionaryForcePlayer = (ExpeditionaryForcePlayer) removedPlayer;
            if (model.getHumanPlayer().getId() == expeditionaryForcePlayer.getTargetPlayerId()) {
                return true;
            }
        }
        return false;
    }
}
