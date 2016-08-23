package org.freemars.command;

import org.freemars.controller.FreeMarsController;
import org.freerealm.command.FreeRealmAbstractCommand;
import org.freerealm.map.Coordinate;
import org.freerealm.settlement.Settlement;
import org.freerealm.unit.Unit;

/**
 * 
 * @author Deniz ARIKAN
 */
public class UnloadAllColonistsCommand extends FreeRealmAbstractCommand {

	private final FreeMarsController freeMarsController;
	private final Settlement freeMarsColony;
	private final Unit unit;

	public UnloadAllColonistsCommand(FreeMarsController freeMarsController, Settlement freeMarsColony, Unit unit) {
		this.freeMarsController = freeMarsController;
		this.freeMarsColony = freeMarsColony;
		this.unit = unit;
	}

	public void execute() {
		Coordinate unitCoordinate = unit.getCoordinate();
		if (unitCoordinate == null) {
			setState(FAILED);
			return;
		}
		if (!unitCoordinate.equals(freeMarsColony.getCoordinate())) {
			setText("Unit is not in colony tile.");
			setState(FAILED);
			return;
		}
		int numberOfColonists = unit.getContainedPopulation();
		if (numberOfColonists == 0) {
			setText("Unit does not contain colonists.");
			setState(FAILED);
			return;
		}
		getExecutor().execute(new UnloadColonistsCommand(freeMarsController, freeMarsColony, unit, numberOfColonists));
		setState(SUCCEEDED);
	}

}
