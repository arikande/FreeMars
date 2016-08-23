package org.freemars.controller;

import org.freemars.controller.handler.PostCommandHandler;
import org.freerealm.command.SetActiveUnitCommand;
import org.freerealm.executor.CommandResult;
import org.freerealm.player.Player;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class UnitSkippedHandler implements PostCommandHandler {

    public void handleUpdate(FreeMarsController controller, CommandResult commandResult) {
        Player activePlayer = controller.getFreeMarsModel().getActivePlayer();
        Unit skippedUnit = (Unit) commandResult.getParameter("skippedUnit");
        Unit nextUnit = org.freemars.util.Utility.getNextPlayableUnit(activePlayer, skippedUnit);
        controller.execute(new SetActiveUnitCommand(activePlayer, nextUnit));
    }
}
