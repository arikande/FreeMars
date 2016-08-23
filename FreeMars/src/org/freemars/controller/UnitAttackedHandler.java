package org.freemars.controller;

import org.freemars.controller.handler.PostCommandHandler;
import org.freemars.message.UnitAttackedMessage;
import org.freerealm.executor.CommandResult;
import org.freerealm.map.Coordinate;
import org.freerealm.player.Player;
import org.freerealm.settlement.Settlement;
import org.freerealm.tile.Tile;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class UnitAttackedHandler implements PostCommandHandler {

    public void handleUpdate(FreeMarsController freeMarsController, CommandResult commandResult) {
        Unit attacker = (Unit) commandResult.getParameter("attacker");
        Unit defender = (Unit) commandResult.getParameter("defender");
        Unit winner = (Unit) commandResult.getParameter("winner");
        Coordinate attackCoordinate = (Coordinate) commandResult.getParameter("coordinate");
        Player player = defender.getPlayer();
        if (freeMarsController.getFreeMarsModel().getHumanPlayer().equals(player)) {
            UnitAttackedMessage unitAttackedMessage = new UnitAttackedMessage();
            unitAttackedMessage.setTurnSent(freeMarsController.getFreeMarsModel().getNumberOfTurns());
            unitAttackedMessage.setSubject("Battle");
            unitAttackedMessage.setAttacker(attacker);
            unitAttackedMessage.setDefender(defender);
            unitAttackedMessage.setWinner(winner);
            unitAttackedMessage.setCoordinate(attackCoordinate);
            StringBuilder message = new StringBuilder();
            if (defender.equals(winner)) {
                message.append("Our ");
                message.append(defender);
                message.append(" has successfully defended itself from ");
                message.append(attacker.getPlayer().getNation().getAdjective());
                message.append(" ");
                message.append(attacker);
                message.append(" at ");
                message.append(attackCoordinate);
                unitAttackedMessage.setText(message.toString());
            } else {
                message.append("Our ");
                message.append(defender);
                message.append(" was destroyed by ");
                message.append(attacker.getPlayer().getNation().getAdjective());
                message.append(" ");
                message.append(attacker);
                message.append(" ");
                Tile tile = freeMarsController.getFreeMarsModel().getTile(attackCoordinate);
                if (tile.getSettlement() != null) {
                    Settlement settlement = tile.getSettlement();
                    message.append("while defending ");
                    message.append(settlement.getName());
                } else {
                    message.append(" at ");
                    message.append(attackCoordinate);
                }
            }
            unitAttackedMessage.setText(message.toString());
            player.addMessage(unitAttackedMessage);
        }
    }
}
