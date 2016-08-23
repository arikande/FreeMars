package org.freerealm.command;

import java.util.ArrayList;
import java.util.Iterator;

import org.freerealm.Realm;
import org.freerealm.modifier.Modifier;
import org.freerealm.player.Message;
import org.freerealm.player.NotEnoughInputResourceForSettlementImprovementMessage;
import org.freerealm.player.NotEnoughPopulationForProductionMessage;
import org.freerealm.player.NotEnoughResourceForProductionMessage;
import org.freerealm.player.Player;
import org.freerealm.player.ResourceWasteMessage;
import org.freerealm.player.SettlementImprovementCompletedMessage;
import org.freerealm.player.UnitCompletedMessage;
import org.freerealm.property.BuildableProperty;
import org.freerealm.property.RemoveSettlementImprovementProperty;
import org.freerealm.resource.Resource;
import org.freerealm.settlement.RequiredPopulationResourceAmountCalculator;
import org.freerealm.settlement.Settlement;
import org.freerealm.settlement.SettlementBuildable;
import org.freerealm.settlement.SettlementBuildableCostCalculator;
import org.freerealm.settlement.improvement.SettlementImprovement;
import org.freerealm.settlement.improvement.SettlementImprovementResourceProductionModel;
import org.freerealm.settlement.improvement.SettlementImprovementType;
import org.freerealm.unit.FreeRealmUnitType;
import org.freerealm.unit.Unit;

/**
 * 
 * @author Deniz ARIKAN
 */
public class EndPlayerTurnCommand extends FreeRealmAbstractCommand {

	private final Player player;

	public EndPlayerTurnCommand(Realm realm, Player player) {
		super(realm);
		this.player = player;
	}

	public void execute() {
		manageWealth();
		manageSettlements();
		manageUnits();
		setState(SUCCEEDED);
	}

	private void manageWealth() {
		player.setWealth(player.getWealth() + player.getTotalIncome() - player.getTotalExpenses());
	}

	private void manageSettlements() {
		Iterator<Settlement> settlementIterator = player.getSettlementsIterator();
		while (settlementIterator.hasNext()) {
			endTurnSettlement(settlementIterator.next());
		}
	}

	private void manageUnits() {
		ArrayList<Unit> temporaryUnitList = new ArrayList<Unit>();
		Iterator<Unit> unitIterator = player.getUnitsIterator();
		while (unitIterator.hasNext()) {
			temporaryUnitList.add(unitIterator.next());
		}
		for (Unit unit : temporaryUnitList) {
			endTurnUnit(unit);
		}
	}

	private void endTurnUnit(Unit unit) {
		if (unit.getCurrentOrder() != null || unit.getNextOrder() != null) {
			getExecutor().execute(new UnitOrderExecuteCommand(getRealm(), unit));
		}
	}

	private void endTurnSettlement(Settlement settlement) {
		manageSettlementTerrainResourceProduction(settlement);
		manageSettlementProductionPoints(settlement);
		manageResourceOutputImprovements(settlement);
		manageSettlementPopulation(settlement);
		manageResourceInputOutputImprovements(settlement);
		manageSettlementProduction(settlement);
		manageSettlementStorage(settlement);
		manageSettlementAutomanagedResources(settlement);
	}

	private void manageSettlementTerrainResourceProduction(Settlement settlement) {
		Iterator<Resource> resourceIterator = getRealm().getResourceManager().getResourcesIterator();
		while (resourceIterator.hasNext()) {
			Resource resource = (Resource) resourceIterator.next();
			settlement.setResourceQuantity(resource, settlement.getResourceQuantity(resource) + settlement.getResourceProductionFromTerrain(resource));
		}
	}

	private void manageResourceOutputImprovements(Settlement settlement) {
		Iterator<SettlementImprovement> iterator = settlement.getImprovementsIterator();
		while (iterator.hasNext()) {
			SettlementImprovement settlementImprovement = iterator.next();
			if (settlementImprovement.isEnabled()) {
				manageOutputImprovement(settlement, settlementImprovement);
			}
		}
	}

	private void manageOutputImprovement(Settlement settlement, SettlementImprovement settlementImprovement) {
		if (settlementImprovement.getType().getInputResourceCount() == 0) {
			SettlementImprovementResourceProductionModel settlementImprovementResourceProductionModel = new SettlementImprovementResourceProductionModel(settlement, settlementImprovement);
			Iterator<Resource> outputResourcesIterator = settlementImprovement.getType().getOutputResourcesIterator();
			if (outputResourcesIterator != null) {
				while (outputResourcesIterator.hasNext()) {
					Resource resource = outputResourcesIterator.next();
					getExecutor().execute(new ResourceAddCommand(settlement, resource, settlementImprovementResourceProductionModel.getOutputAmount(resource)));
				}
			}
		}
	}

	private void manageResourceInputOutputImprovements(Settlement settlement) {
		Iterator<SettlementImprovement> iterator = settlement.getImprovementsIterator();
		while (iterator.hasNext()) {
			SettlementImprovement settlementImprovement = iterator.next();
			if (settlementImprovement.isEnabled()) {
				manageResourceInputOutputImprovement(settlement, settlementImprovement);
			}
		}
	}

	private void manageResourceInputOutputImprovement(Settlement settlement, SettlementImprovement settlementImprovement) {
		if (settlementImprovement.getType().getInputResourceCount() > 0) {
			SettlementImprovementResourceProductionModel settlementImprovementResourceProductionModel = new SettlementImprovementResourceProductionModel(settlement, settlementImprovement);
			Iterator<Resource> inputResourcesIterator = settlementImprovement.getType().getInputResourcesIterator();
			if (inputResourcesIterator != null) {
				while (inputResourcesIterator.hasNext()) {
					Resource resource = inputResourcesIterator.next();
					if (settlement.getResourceQuantity(resource) < settlementImprovement.getType().getInputQuantity(resource) && settlementImprovement.getNumberOfWorkers() > 0) {
						NotEnoughInputResourceForSettlementImprovementMessage notEnoughInputResourceForSettlementImprovementMessage = new NotEnoughInputResourceForSettlementImprovementMessage();
						notEnoughInputResourceForSettlementImprovementMessage.setSubject("Not enough input resource");
						StringBuilder messageText = new StringBuilder();
						messageText.append("Settlement of ");
						messageText.append(settlement.getName());
						messageText.append(" does not have enough ");
						messageText.append(resource.getName().toLowerCase());
						messageText.append(" to use in ");
						messageText.append(settlementImprovement.getType().getName());
						messageText.append(".");
						notEnoughInputResourceForSettlementImprovementMessage.setText(messageText.toString());
						notEnoughInputResourceForSettlementImprovementMessage.setTurnSent(getRealm().getNumberOfTurns());
						notEnoughInputResourceForSettlementImprovementMessage.setSettlement(settlement);
						notEnoughInputResourceForSettlementImprovementMessage.setSettlementImprovement(settlementImprovement);
						notEnoughInputResourceForSettlementImprovementMessage.setResource(resource);
						settlement.getPlayer().addMessage(notEnoughInputResourceForSettlementImprovementMessage);
					} else {
						getExecutor().execute(new ResourceRemoveCommand(settlement, resource, settlementImprovementResourceProductionModel.getInputAmount(resource)));
					}
				}
			}
			Iterator<Resource> outputResourcesIterator = settlementImprovement.getType().getOutputResourcesIterator();
			if (outputResourcesIterator != null) {
				while (outputResourcesIterator.hasNext()) {
					Resource resource = outputResourcesIterator.next();
					getExecutor().execute(new ResourceAddCommand(settlement, resource, settlementImprovementResourceProductionModel.getOutputAmount(resource)));
				}
			}
		}
	}

	private void manageSettlementProductionPoints(Settlement settlement) {
		settlement.setProductionPoints(settlement.getProductionPoints() + settlement.getProductionPointsPerTurn());
	}

	private void manageSettlementPopulation(Settlement settlement) {
		boolean isSettlementPopulationIncreasing = true;
		Iterator<Integer> iterator = getRealm().getRequiredPopulationResourcesIterator();
		while (iterator.hasNext()) {
			Integer resourceId = iterator.next();
			Resource resource = getRealm().getResourceManager().getResource(resourceId);
			Modifier[] modifiers = new Modifier[] { player };
			RequiredPopulationResourceAmountCalculator requiredPopulationResourceAmountCalculator = new RequiredPopulationResourceAmountCalculator(getRealm(), resource, modifiers);
			int requiredPopulationResourceAmount = requiredPopulationResourceAmountCalculator.getRequiredPopulationResourceAmount();
			int requiredAmount = requiredPopulationResourceAmount * settlement.getPopulation();
			if (requiredAmount > settlement.getResourceQuantity(resource)) {
				isSettlementPopulationIncreasing = false;
				settlement.setResourceQuantity(resource, 0);
			} else {
				settlement.setResourceQuantity(resource, settlement.getResourceQuantity(resource) - requiredAmount);
			}
		}
		if (isSettlementPopulationIncreasing) {
			double populationChange = (settlement.getPopulation() * settlement.getPopulationIncreasePercent()) / 100;
			if (populationChange > 0 && populationChange < 1) {
				populationChange = 1;
			} else {
				populationChange = Math.floor(populationChange);
			}
			settlement.setPopulation(settlement.getPopulation() + (int) populationChange);
		} else {
			int populationChange = (int) Math.ceil(settlement.getPopulation() * settlement.getPopulationDecreasePercent() / 100);
			settlement.setPopulation(settlement.getPopulation() - populationChange);
		}
	}

	private void manageSettlementProduction(Settlement settlement) {
		SettlementBuildable currentProduction = settlement.getCurrentProduction();
		if (currentProduction == null) {
			return;
		}
		BuildableProperty buildableProperty = (BuildableProperty) currentProduction.getProperty(BuildableProperty.NAME);
		if (buildableProperty == null) {
			return;
		}
		int currentProductionCost = new SettlementBuildableCostCalculator(currentProduction, new Modifier[] { settlement.getPlayer() }).getCost();
		if (settlement.getProductionPoints() < currentProductionCost) {
			return;
		}
		int populationCost = buildableProperty.getPopulationCost();
		if (settlement.getPopulation() <= populationCost) {
			StringBuilder messageText = new StringBuilder("Not enough population for " + currentProduction.getName().toLowerCase() + " production in " + settlement.getName());
			NotEnoughPopulationForProductionMessage notEnoughPopulationForProductionMessage = new NotEnoughPopulationForProductionMessage();
			notEnoughPopulationForProductionMessage.setSettlement(settlement);
			notEnoughPopulationForProductionMessage.setText(messageText.toString());
			notEnoughPopulationForProductionMessage.setTurnSent(getRealm().getNumberOfTurns());
			settlement.getPlayer().addMessage(notEnoughPopulationForProductionMessage);
			return;
		}

		Iterator<Integer> resourceIdsIterator = buildableProperty.getBuildCostResourceIdsIterator();
		while (resourceIdsIterator.hasNext()) {
			Integer resourceId = resourceIdsIterator.next();
			Resource resource = getRealm().getResourceManager().getResource(resourceId);
			if (settlement.getResourceQuantity(resource) < buildableProperty.getBuildCostResourceQuantity(resourceId)) {
				StringBuilder messageText = new StringBuilder("Not enough " + resource.getName().toLowerCase() + " for " + currentProduction.getName().toLowerCase() + " production in "
						+ settlement.getName());
				NotEnoughResourceForProductionMessage notEnoughResourceForProductionMessage = new NotEnoughResourceForProductionMessage();
				notEnoughResourceForProductionMessage.setText(messageText.toString());
				notEnoughResourceForProductionMessage.setTurnSent(getRealm().getNumberOfTurns());
				notEnoughResourceForProductionMessage.setSettlement(settlement);
				notEnoughResourceForProductionMessage.setResource(resource);
				settlement.getPlayer().addMessage(notEnoughResourceForProductionMessage);
				return;
			}
		}

		settlement.setProductionPoints(settlement.getProductionPoints() - currentProductionCost);

		SetSettlementPopulationCommand setSettlementPopulationCommand = new SetSettlementPopulationCommand(settlement, settlement.getPopulation() - populationCost);
		getExecutor().execute(setSettlementPopulationCommand);

		resourceIdsIterator = buildableProperty.getBuildCostResourceIdsIterator();
		while (resourceIdsIterator.hasNext()) {
			Integer resourceId = resourceIdsIterator.next();
			Resource resource = getRealm().getResourceManager().getResource(resourceId);
			settlement.setResourceQuantity(resource, settlement.getResourceQuantity(resource) - buildableProperty.getBuildCostResourceQuantity(resourceId));
		}

		if (currentProduction instanceof SettlementImprovementType) {
			SettlementImprovementType settlementImprovementType = (SettlementImprovementType) currentProduction;
			getExecutor().execute(new AddSettlementImprovementCommand(settlement, settlementImprovementType));
			SettlementImprovementType completedImprovement = settlementImprovementType;
			settlement.removeFromProductionQueue(0);
			RemoveSettlementImprovementProperty removeSettlementImprovement = (RemoveSettlementImprovementProperty) settlementImprovementType.getProperty(RemoveSettlementImprovementProperty.NAME);
			if (removeSettlementImprovement != null) {
				getExecutor().execute(new RemoveSettlementImprovementCommand(settlement, removeSettlementImprovement.getSettlementImprovementId()));
			}
			settlement.getPlayer().addMessage(prepareSettlementImprovementCompletedMessage(settlement, completedImprovement));
		} else if (currentProduction instanceof FreeRealmUnitType) {
			FreeRealmUnitType unitType = (FreeRealmUnitType) currentProduction;
			Unit newUnit = new Unit(getRealm());
			newUnit.setType(unitType);
			newUnit.setCoordinate(settlement.getCoordinate());
			newUnit.setPlayer(settlement.getPlayer());
			getExecutor().execute(new AddUnitCommand(getRealm(), settlement.getPlayer(), newUnit));
			if ((settlement.getPlayer().getActiveUnit() == null)) {
				getExecutor().execute(new SetActiveUnitCommand(settlement.getPlayer(), newUnit));
			}
			if (!settlement.isContiuousProduction()) {
				settlement.removeFromProductionQueue(0);
			}
			settlement.getPlayer().addMessage(prepareUnitCompletedMessage(settlement, newUnit));
		}
	}

	private void manageSettlementStorage(Settlement settlement) {
		Iterator<Resource> resourceIterator = getRealm().getResourceManager().getResourcesIterator();
		while (resourceIterator.hasNext()) {
			Resource resource = (Resource) resourceIterator.next();
			int storage = settlement.getTotalCapacity(resource);
			if (settlement.getResourceQuantity(resource) > storage) {
				int wastedAmount = settlement.getResourceQuantity(resource) - storage;
				StringBuilder messageText = new StringBuilder(wastedAmount + " units of " + resource + " is wasted in " + settlement.getName());
				ResourceWasteMessage message = new ResourceWasteMessage();
				message.setText(messageText.toString());
				message.setTurnSent(getRealm().getNumberOfTurns());
				message.setSettlement(settlement);
				message.setResource(resource);
				player.addMessage(message);
				settlement.setResourceQuantity(resource, storage);
			} else {
				int resourceProduction = settlement.getResourceProductionFromTerrain(resource);
				int nextTurnResourceAmount = settlement.getResourceQuantity(resource) + resourceProduction;
				if (nextTurnResourceAmount > storage) {
					int willBeWastedAmount = settlement.getResourceQuantity(resource) + resourceProduction - storage;
					StringBuilder messageText = new StringBuilder("With current production " + willBeWastedAmount + " units of " + resource + " will be wasted in " + settlement.getName()
							+ " next turn");
					ResourceWasteMessage message = new ResourceWasteMessage();
					message.setText(messageText.toString());
					message.setTurnSent(getRealm().getNumberOfTurns());
					message.setSettlement(settlement);
					message.setResource(resource);
					player.addMessage(message);

				}
			}
		}
	}

	private void manageSettlementAutomanagedResources(Settlement settlement) {
		Iterator<Resource> iterator = settlement.getAutomanagedResourcesIterator();
		while (iterator.hasNext()) {
			Resource resource = iterator.next();
			getExecutor().execute(new SettlementAutomanageResourceCommand(getRealm(), settlement, resource));
		}
	}

	private Message prepareSettlementImprovementCompletedMessage(Settlement settlement, SettlementImprovementType completedImprovement) {
		StringBuilder messageText = new StringBuilder("Settlement of " + settlement.getName() + " has completed " + completedImprovement);
		SettlementImprovementCompletedMessage message = new SettlementImprovementCompletedMessage();
		message.setSubject("Building complete");
		message.setText(messageText.toString());
		message.setTurnSent(getRealm().getNumberOfTurns());
		message.setSettlement(settlement);
		message.setSettlementImprovementType(completedImprovement);
		message.setNextProduction(settlement.getCurrentProduction());
		return message;
	}

	private Message prepareUnitCompletedMessage(Settlement settlement, Unit newUnit) {
		UnitCompletedMessage unitCompletedMessage = new UnitCompletedMessage();
		unitCompletedMessage.setSubject("Unit complete");
		StringBuilder messageText = new StringBuilder("Settlement of " + settlement.getName() + " has completed " + settlement.getCurrentProduction());
		unitCompletedMessage.setText(messageText.toString());
		unitCompletedMessage.setTurnSent(getRealm().getNumberOfTurns());
		unitCompletedMessage.setSettlement(settlement);
		unitCompletedMessage.setUnit(newUnit);
		unitCompletedMessage.setNextProduction(settlement.getCurrentProduction());
		unitCompletedMessage.setContiuousProduction(settlement.isContiuousProduction());
		return unitCompletedMessage;
	}

}
