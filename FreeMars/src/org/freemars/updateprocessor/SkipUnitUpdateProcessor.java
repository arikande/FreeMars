package org.freemars.updateprocessor;

import org.commandexecutor.Command;
import org.freemars.controller.FreeMarsController;
import org.freemars.util.Utility;
import org.freerealm.command.SetActiveUnitCommand;
import org.freerealm.player.Player;
import org.freerealm.unit.Unit;

public class SkipUnitUpdateProcessor extends FreeMarsUpdateProcessor {

	public SkipUnitUpdateProcessor(FreeMarsController freeMarsController) {
		super(freeMarsController);
	}

	@Override
	public void processUpdate(Command command) {
		Player activePlayer = getFreeMarsController().getFreeMarsModel().getActivePlayer();
		Unit skippedUnit = (Unit) command.getParameter("skipped_unit");
		Unit nextUnit = Utility.getNextPlayableUnit(activePlayer, skippedUnit);
		getFreeMarsController().addCommandToQueue(new SetActiveUnitCommand(activePlayer, nextUnit));
	}

}