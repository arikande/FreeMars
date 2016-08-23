package org.freemars.controller;

import java.util.HashMap;
import org.freemars.controller.handler.PostCommandHandler;
import org.freemars.message.ExpeditionaryForceLandedMessage;
import org.freerealm.executor.CommandResult;
import org.freerealm.player.Player;
import org.freerealm.unit.FreeRealmUnitType;

/**
 *
 * @author Deniz ARIKAN
 */
public class ExpeditionaryForceLandedHandler implements PostCommandHandler {

    public void handleUpdate(FreeMarsController controller, CommandResult commandResult) {
        HashMap<FreeRealmUnitType, Integer> landedUnits = (HashMap<FreeRealmUnitType, Integer>) commandResult.getParameter("landedUnits");
        ExpeditionaryForceLandedMessage expeditionaryForceLandedMessage = new ExpeditionaryForceLandedMessage();
        expeditionaryForceLandedMessage.setTurnSent(controller.getFreeMarsModel().getNumberOfTurns());
        expeditionaryForceLandedMessage.setSubject("Expeditionary force landed");
        StringBuilder message = new StringBuilder();
        int attackWave = (Integer) commandResult.getParameter("attackWave");
        switch (attackWave) {
            case 1:
                message.append("First wave of the expeditionary force has landed");
                break;
            case 2:
                message.append("Second wave of the expeditionary force has landed");
                break;
            case 3:
                message.append("Final wave of the expeditionary force has landed");
                break;
        }
        expeditionaryForceLandedMessage.setText(message.toString());
        expeditionaryForceLandedMessage.setAttackWave(attackWave);
        expeditionaryForceLandedMessage.setLandedUnits(landedUnits);
        Player player = (Player) commandResult.getParameter("targetPlayer");
        player.addMessage(expeditionaryForceLandedMessage);
    }
}
