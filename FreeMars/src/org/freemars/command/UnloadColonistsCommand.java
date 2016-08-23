package org.freemars.command;

import org.freemars.controller.FreeMarsController;
import org.freerealm.command.FreeRealmAbstractCommand;
import org.freerealm.command.SetContainerPopulationCommand;
import org.freerealm.command.SetSettlementPopulationCommand;
import org.freerealm.map.Coordinate;
import org.freerealm.settlement.Settlement;
import org.freerealm.unit.Unit;

/**
 * 
 * @author Deniz ARIKAN
 */
public class UnloadColonistsCommand extends FreeRealmAbstractCommand {

	private final FreeMarsController freeMarsController;
	private final Settlement freeMarsColony;
	private final Unit unit;
	int numberOfColonists;

	public UnloadColonistsCommand(FreeMarsController freeMarsController, Settlement freeMarsColony, Unit unit, int numberOfColonists) {
		this.freeMarsController = freeMarsController;
		this.freeMarsColony = freeMarsColony;
		this.unit = unit;
		this.numberOfColonists = numberOfColonists;
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
		int containedPopulation = unit.getContainedPopulation();
		if (containedPopulation == 0) {
			setText("Unit does not contain colonists.");
			setState(FAILED);
			return;
		}
		if (numberOfColonists > containedPopulation) {
			numberOfColonists = containedPopulation;
		}
		int weightPerCitizen = Integer.parseInt(freeMarsController.getFreeMarsModel().getRealm().getProperty("weight_per_citizen"));
		freeMarsController.execute(new SetContainerPopulationCommand(unit, unit.getContainedPopulation() - numberOfColonists, weightPerCitizen));
		freeMarsController.execute(new SetSettlementPopulationCommand(freeMarsColony, freeMarsColony.getPopulation() + numberOfColonists));
		setState(SUCCEEDED);
	}

}
