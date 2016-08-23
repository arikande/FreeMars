package org.freemars.command;

import org.freemars.controller.FreeMarsController;
import org.freerealm.command.FreeRealmAbstractCommand;
import org.freerealm.command.SetContainerPopulationCommand;
import org.freerealm.command.SetSettlementPopulationCommand;
import org.freerealm.settlement.Settlement;
import org.freerealm.unit.Unit;

/**
 * 
 * @author Deniz ARIKAN
 */
public class LoadColonistsCommand extends FreeRealmAbstractCommand {

	private final FreeMarsController freeMarsController;
	private final Settlement freeMarsColony;
	private final Unit unit;
	int numberOfColonists;

	public LoadColonistsCommand(FreeMarsController freeMarsController, Settlement freeMarsColony, Unit unit, int numberOfColonists) {
		this.freeMarsController = freeMarsController;
		this.freeMarsColony = freeMarsColony;
		this.unit = unit;
		this.numberOfColonists = numberOfColonists;
	}

	public void execute() {
		int weightPerCitizen = Integer.parseInt(freeMarsController.getFreeMarsModel().getRealm().getProperty("weight_per_citizen"));
		freeMarsController.execute(new SetContainerPopulationCommand(unit, unit.getContainedPopulation() + numberOfColonists, weightPerCitizen));
		freeMarsController.execute(new SetSettlementPopulationCommand(freeMarsColony, freeMarsColony.getPopulation() - numberOfColonists));
		setState(SUCCEEDED);
	}

}
