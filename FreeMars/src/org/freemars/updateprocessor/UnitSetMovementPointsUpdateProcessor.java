package org.freemars.updateprocessor;

import org.commandexecutor.Command;
import org.freemars.controller.FreeMarsController;

public class UnitSetMovementPointsUpdateProcessor extends FreeMarsUpdateProcessor {

	public UnitSetMovementPointsUpdateProcessor(FreeMarsController freeMarsController) {
		super(freeMarsController);
	}

	@Override
	public void processUpdate(Command command) {
		System.out.println("processing update -- " + command.getName());
	}

}
