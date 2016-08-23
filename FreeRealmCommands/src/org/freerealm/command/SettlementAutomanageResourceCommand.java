package org.freerealm.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.freerealm.Realm;
import org.freerealm.map.Coordinate;
import org.freerealm.modifier.Modifier;
import org.freerealm.resource.Resource;
import org.freerealm.settlement.RequiredPopulationResourceAmountCalculator;
import org.freerealm.settlement.Settlement;
import org.freerealm.settlement.improvement.SettlementImprovement;
import org.freerealm.settlement.improvement.SettlementImprovementType;
import org.freerealm.settlement.workforce.WorkForce;
import org.freerealm.tile.Tile;

/**
 * 
 * @author Deniz ARIKAN
 */
public class SettlementAutomanageResourceCommand extends FreeRealmAbstractCommand {

	private final Settlement settlement;
	private final Resource resource;
	private final List<ResourceProducer> resourceProducers;

	public SettlementAutomanageResourceCommand(Realm realm, Settlement settlement, Resource resource) {
		super(realm);
		this.settlement = settlement;
		this.resource = resource;
		resourceProducers = new ArrayList<ResourceProducer>();
	}

	public void execute() {
		removeCurrentWorkForceForResource();
		removeCurrentWorkForceForImprovements();
		findResourceProducingCoordinates();
		findResourceProducingImprovements();
		Collections.sort(resourceProducers);
		Collections.reverse(resourceProducers);
		Iterator<ResourceProducer> iterator = resourceProducers.iterator();
		while (iterator.hasNext()) {
			Modifier[] modifiers = new Modifier[] { settlement.getPlayer() };
			RequiredPopulationResourceAmountCalculator requiredPopulationResourceAmountCalculator = new RequiredPopulationResourceAmountCalculator(getRealm(), resource, modifiers);
			int requiredPopulationResourceAmount = requiredPopulationResourceAmountCalculator.getRequiredPopulationResourceAmount();
			int requiredAmount = settlement.getPopulation() * requiredPopulationResourceAmount - settlement.getTotalResourceProduction(resource);
			ResourceProducer resourceProducer = iterator.next();
			if (requiredAmount > 0) {
				int workersToAssign = ((int) Math.ceil((requiredAmount) / (resourceProducer.getProduction())));
				if (resourceProducer instanceof CoordinateResourceProducer) {
					CoordinateResourceProducer coordinateResourceProducer = (CoordinateResourceProducer) resourceProducer;
					if (workersToAssign > settlement.getMaxWorkersPerTile()) {
						workersToAssign = settlement.getMaxWorkersPerTile();
					}
					if (workersToAssign > settlement.getProductionWorkforce()) {
						workersToAssign = settlement.getProductionWorkforce();
					}
					if (workersToAssign > 0) {
						WorkForce workForce = prepareWorkForce(workersToAssign, coordinateResourceProducer.getCoordinate());
						getExecutor().execute(new WorkforceAssignCommand(getRealm(), settlement, workForce, coordinateResourceProducer.getCoordinate()));
					}
				} else if (resourceProducer instanceof ImprovementResourceProducer) {
					ImprovementResourceProducer improvementResourceProducer = (ImprovementResourceProducer) resourceProducer;
					SettlementImprovement settlementImprovement = improvementResourceProducer.getSettlementImprovement();
					if (workersToAssign > settlementImprovement.getType().getMaximumWorkers()) {
						workersToAssign = settlementImprovement.getType().getMaximumWorkers();
					}
					if (workersToAssign > settlement.getProductionWorkforce()) {
						workersToAssign = settlement.getProductionWorkforce();
					}
					if (workersToAssign > 0) {
						getExecutor().execute(new SettlementImprovementSetWorkersCommand(settlement, settlementImprovement, workersToAssign));
					}
				}
			}
		}
		setState(SUCCEEDED);
	}

	private void removeCurrentWorkForceForResource() {
		for (Coordinate coordinate : settlement.getValidWorkForceCoordinates()) {
			WorkForce workForce = settlement.getWorkForceManager().getAssignedWorkforceForTile(coordinate);
			if (workForce != null && workForce.getResource().equals(resource)) {
				getExecutor().execute(new WorkforceRemoveCommand(settlement, coordinate));
			}
		}
	}

	private void removeCurrentWorkForceForImprovements() {
		Iterator<SettlementImprovement> iterator = settlement.getImprovementsIterator();
		while (iterator.hasNext()) {
			SettlementImprovement settlementImprovement = iterator.next();
			SettlementImprovementType settlementImprovementType = settlementImprovement.getType();
			if (settlementImprovementType.getOutputQuantity(resource) > 0 && settlementImprovementType.getInputResourceCount() == 0 && settlementImprovementType.getMaximumWorkers() > 0) {
				getExecutor().execute(new SettlementImprovementSetWorkersCommand(settlement, settlementImprovement, 0));
			}
		}
	}

	private void findResourceProducingCoordinates() {
		for (Coordinate coordinate : settlement.getValidWorkForceCoordinates()) {
			Tile tile = getRealm().getTile(coordinate);
			if (tile.getProduction(resource) > 0) {
				float tileResourceProduction = org.freerealm.Utility.modifyByPercent(tile.getProduction(resource), settlement.getResourceModifier(resource));
				tileResourceProduction = org.freerealm.Utility.modifyByPercent(tileResourceProduction, settlement.getEfficiency() - 100);
				resourceProducers.add(new CoordinateResourceProducer(tileResourceProduction, coordinate));
			}
		}
	}

	private void findResourceProducingImprovements() {
		Iterator<SettlementImprovement> iterator = settlement.getImprovementsIterator();
		while (iterator.hasNext()) {
			SettlementImprovement settlementImprovement = iterator.next();
			SettlementImprovementType settlementImprovementType = settlementImprovement.getType();
			if (settlementImprovementType.getOutputQuantity(resource) > 0 && settlementImprovementType.getInputResourceCount() == 0 && settlementImprovementType.getMaximumWorkers() > 0) {
				int production = settlementImprovementType.getOutputQuantity(resource) * settlementImprovementType.getMaximumMultiplier() / settlementImprovementType.getMaximumWorkers();
				resourceProducers.add(new ImprovementResourceProducer(production, settlementImprovement));
			}
		}
	}

	private WorkForce prepareWorkForce(int workers, Coordinate coordinate) {
		WorkForce workForce = new WorkForce();
		workForce.setNumberOfWorkers(workers);
		workForce.setResource(resource);
		workForce.setCoordinate(coordinate);
		return workForce;
	}

	private class ResourceProducer implements Comparable<ResourceProducer> {

		private float production;

		public ResourceProducer(float production) {
			this.production = production;
		}

		public int compareTo(ResourceProducer other) {
			if (getProduction() > other.getProduction()) {
				return 1;
			} else if (getProduction() == other.getProduction()) {
				return 0;
			} else {
				return -1;
			}
		}

		public float getProduction() {
			return production;
		}

	}

	private class ImprovementResourceProducer extends ResourceProducer {

		private final SettlementImprovement settlementImprovement;

		public ImprovementResourceProducer(float production, SettlementImprovement settlementImprovement) {
			super(production);
			this.settlementImprovement = settlementImprovement;
		}

		@Override
		public String toString() {
			return settlementImprovement + " -> " + getProduction();
		}

		public SettlementImprovement getSettlementImprovement() {
			return settlementImprovement;
		}
	}

	private class CoordinateResourceProducer extends ResourceProducer {

		private final Coordinate coordinate;

		public CoordinateResourceProducer(float production, Coordinate coordinate) {
			super(production);
			this.coordinate = coordinate;
		}

		@Override
		public String toString() {
			return coordinate + " -> " + getProduction();
		}

		public Coordinate getCoordinate() {
			return coordinate;
		}
	}
}
