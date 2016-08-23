package org.freerealm.command;

import org.freerealm.PopulationContainer;

/**
 * 
 * @author Deniz ARIKAN
 */
public class SetContainerPopulationCommand extends FreeRealmAbstractCommand {

	private final PopulationContainer populationContainer;
	private final int population;
	private final int weightPerCitizen;

	public SetContainerPopulationCommand(PopulationContainer populationContainer, int population, int weightPerCitizen) {
		this.populationContainer = populationContainer;
		this.population = population;
		this.weightPerCitizen = weightPerCitizen;
	}

	public void execute() {
		if (populationContainer.canContainPopulation()) {
			if (populationContainer.getRemainingCapacity() >= (population - populationContainer.getContainedPopulation()) * weightPerCitizen) {
				populationContainer.setContainedPopulation(population);
				setState(SUCCEEDED);
			} else {
				setText("Not enough capacity");
				setState(FAILED);
				return;
			}
		} else {
			setText("Container can not carry population");
			setState(FAILED);
			return;
		}
	}

}
