package org.freerealm.command;

import org.freerealm.settlement.Settlement;

/**
 * 
 * @author Deniz ARIKAN
 */
public class SetSettlementPopulationCommand extends FreeRealmAbstractCommand {

	private final Settlement settlement;
	private final int population;

	public SetSettlementPopulationCommand(Settlement settlement, int population) {
		this.settlement = settlement;
		this.population = population;
	}

	public void execute() {
		int currentPopulation = settlement.getPopulation();
		if (population != currentPopulation) {
			settlement.setPopulation(population);
			putParameter("settlement", settlement);
			putParameter("population", population);
			setState(SUCCEEDED);
			return;
		} else {
			setState(FAILED);
			return;
		}
	}

}
