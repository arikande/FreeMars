package org.freemars.controller;

import java.util.HashMap;
import org.freemars.controller.handler.PostCommandHandler;
import org.freemars.message.ExpeditionaryForceChangedMessage;
import org.freerealm.executor.CommandResult;
import org.freerealm.player.Player;
import org.freerealm.unit.FreeRealmUnitType;

/**
 *
 * @author Deniz ARIKAN
 */
public class ExpeditionaryForceChangedHandler implements PostCommandHandler {

    public void handleUpdate(FreeMarsController controller, CommandResult commandResult) {
        HashMap<FreeRealmUnitType, Integer> updatedUnits = (HashMap<FreeRealmUnitType, Integer>) commandResult.getParameter("updated_units");
        ExpeditionaryForceChangedMessage expeditionaryForceChangedMessage = new ExpeditionaryForceChangedMessage();
        expeditionaryForceChangedMessage.setTurnSent(controller.getFreeMarsModel().getNumberOfTurns());
        expeditionaryForceChangedMessage.setSubject("Expeditionary force changed");
        expeditionaryForceChangedMessage.setUpdatedUnits(updatedUnits);
        StringBuilder message = new StringBuilder();
        message.append("Our Earth government has updated its expeditionary force");
        expeditionaryForceChangedMessage.setText(message.toString());
        Player targetPlayer = (Player) commandResult.getParameter("target_player");
        targetPlayer.addMessage(expeditionaryForceChangedMessage);
    }
}
