package org.freemars.controller;

import org.freemars.ai.ExpeditionaryForcePlayer;
import org.freemars.controller.handler.PostCommandHandler;
import org.freemars.earth.action.DisplayIndependenceDeclaredDialogAction;
import org.freerealm.executor.CommandResult;

/**
 *
 * @author Deniz ARIKAN
 */
public class PlayerDeclaredIndependenceHandler implements PostCommandHandler {

    public void handleUpdate(FreeMarsController controller, CommandResult commandResult) {
        ExpeditionaryForcePlayer expeditionaryForcePlayer = (ExpeditionaryForcePlayer) commandResult.getParameter("expeditionary_force_player");
        new DisplayIndependenceDeclaredDialogAction(controller, expeditionaryForcePlayer.getEarthToMarsFlightTurns()).actionPerformed(null);
    }
}
