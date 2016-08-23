package org.freemars.controller;

import java.util.List;
import org.freemars.controller.handler.PostCommandHandler;
import org.freemars.earth.message.UnitsSeizedMessage;
import org.freemars.player.FreeMarsPlayer;
import org.freerealm.executor.CommandResult;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class UnitsSeizedHandler implements PostCommandHandler {

    public void handleUpdate(FreeMarsController controller, CommandResult commandResult) {
        FreeMarsPlayer freeMarsPlayer = (FreeMarsPlayer) commandResult.getParameter("player");
        if (freeMarsPlayer.equals(controller.getFreeMarsModel().getHumanPlayer())) {
            List<Unit> seizedUnits = (List<Unit>) commandResult.getParameter("seized_units");
            UnitsSeizedMessage unitsSeizedMessage = new UnitsSeizedMessage();
            unitsSeizedMessage.setTurnSent(controller.getFreeMarsModel().getNumberOfTurns());
            unitsSeizedMessage.setSeizedUnits(seizedUnits);
            freeMarsPlayer.addMessage(unitsSeizedMessage);
        }
    }
}
