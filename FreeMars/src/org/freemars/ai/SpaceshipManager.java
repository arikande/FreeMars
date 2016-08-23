package org.freemars.ai;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.freemars.ai.command.AIBuyResourceFromEarthCommand;
import org.freemars.controller.FreeMarsController;
import org.freemars.earth.Location;
import org.freemars.earth.command.FinanceColonistsCommand;
import org.freemars.earth.command.SellResourceToEarthCommand;
import org.freemars.earth.order.RelocateUnitOrder;
import org.freerealm.command.AddUnitToContainerCommand;
import org.freerealm.command.RemoveUnitFromContainerCommand;
import org.freerealm.command.SetContainerPopulationCommand;
import org.freerealm.command.SetSettlementPopulationCommand;
import org.freerealm.command.TransferResourceCommand;
import org.freerealm.command.UnitActivateCommand;
import org.freerealm.command.UnitOrderAddCommand;
import org.freerealm.executor.CommandResult;
import org.freerealm.resource.Resource;
import org.freerealm.settlement.Settlement;
import org.freerealm.settlement.improvement.SettlementImprovementType;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class SpaceshipManager {

    private static final Logger logger = Logger.getLogger(SpaceshipManager.class);
    private static final int MINIMUM_QUANTITY_TO_TRANSFER = 2000;
    private final FreeMarsController freeMarsController;
    private final AIPlayer aiPlayer;
    private ArrayList<Resource> exportableResources;
    private final DecisionModel decisionModel;

    public SpaceshipManager(FreeMarsController freeMarsController, AIPlayer aiPlayer, DecisionModel decisionModel) {
        this.freeMarsController = freeMarsController;
        this.aiPlayer = aiPlayer;
        this.decisionModel = decisionModel;
        initExportableResources();
    }

    public void manage() {
        for (Unit shuttle : getAvailableSpaceships()) {
            manageSpaceship(shuttle);
        }
    }

    private void manageSpaceship(Unit spaceship) {
        Location location = freeMarsController.getFreeMarsModel().getEarth().getUnitLocation(spaceship);
        if (location == null) {
            unloadColonistsFromSpaceship(spaceship);
            unloadUnitsFromSpaceship(spaceship);
            unloadFertilizerFromSpaceship(spaceship);
            unloadGaussRifleFromSpaceship(spaceship);
            loadResourcesToSpaceship(spaceship);
            if (spaceship.getTotalContainedWeight() > MINIMUM_QUANTITY_TO_TRANSFER) {
                sendSpaceshipToEarth(spaceship);
            }
        } else if (location.equals(Location.MARS_ORBIT)) {
            landSpaceshipToMarsColony(spaceship);
        } else if (location.equals(Location.EARTH)) {
            sellSpaceshipResourcesToEarth(spaceship);
            loadUnitsToSpaceship(spaceship);
            financeColonists(spaceship);
            buyFertilizerFromEarth(spaceship);
            buyGaussRifleFromEarth(spaceship);
            sendSpaceshipToMarsOrbit(spaceship);
        }
    }

    private void initExportableResources() {
        exportableResources = new ArrayList<Resource>();
        exportableResources.add(freeMarsController.getFreeMarsModel().getRealm().getResourceManager().getResource("Steel"));
        exportableResources.add(freeMarsController.getFreeMarsModel().getRealm().getResourceManager().getResource("Glass"));
        exportableResources.add(freeMarsController.getFreeMarsModel().getRealm().getResourceManager().getResource("Chemicals"));
        exportableResources.add(freeMarsController.getFreeMarsModel().getRealm().getResourceManager().getResource("Lumber"));
        exportableResources.add(freeMarsController.getFreeMarsModel().getRealm().getResourceManager().getResource("Magnesium"));
        exportableResources.add(freeMarsController.getFreeMarsModel().getRealm().getResourceManager().getResource("Iron"));
        exportableResources.add(freeMarsController.getFreeMarsModel().getRealm().getResourceManager().getResource("Silica"));
        exportableResources.add(freeMarsController.getFreeMarsModel().getRealm().getResourceManager().getResource("Minerals"));
    }

    private ArrayList<Unit> getAvailableSpaceships() {
        ArrayList<Unit> availableSpaceships = new ArrayList<Unit>();
        Iterator<Unit> iterator = aiPlayer.getUnitManager().getUnitsIterator();
        while (iterator.hasNext()) {
            Unit unit = iterator.next();
            if (decisionModel.getManageableSpaceshipTypes().contains(unit.getType())) {
                if (unit.getCurrentOrder() == null) {
                    availableSpaceships.add(unit);
                }
            }
        }
        return availableSpaceships;
    }

    private void unloadUnitsFromSpaceship(Unit spaceship) {
        Iterator<Integer> iterator = spaceship.getContainedUnitsIterator();
        ArrayList<Integer> unitsToUnload = new ArrayList<Integer>();
        while (iterator.hasNext()) {
            int containedUnitId = iterator.next();
            unitsToUnload.add(containedUnitId);
        }
        for (Integer containedUnitId : unitsToUnload) {
            Unit containedUnit = spaceship.getPlayer().getUnit(containedUnitId);
            freeMarsController.execute(new RemoveUnitFromContainerCommand(spaceship, containedUnit));
            freeMarsController.execute(new UnitActivateCommand(freeMarsController.getFreeMarsModel().getRealm(), containedUnit, spaceship.getCoordinate()));
        }
    }

    private void unloadColonistsFromSpaceship(Unit spaceship) {
        int colonistsOnSpaceship = spaceship.getContainedPopulation();
        if (colonistsOnSpaceship > 0) {
            int weightPerCitizen = Integer.parseInt(freeMarsController.getFreeMarsModel().getRealm().getProperty("weight_per_citizen"));
            Settlement settlement = freeMarsController.getFreeMarsModel().getRealm().getTile(spaceship.getCoordinate()).getSettlement();
            freeMarsController.execute(new SetContainerPopulationCommand(spaceship, 0, weightPerCitizen));
            freeMarsController.execute(new SetSettlementPopulationCommand(settlement, settlement.getPopulation() + colonistsOnSpaceship));
        }
    }

    private void loadResourcesToSpaceship(Unit spaceship) {
        loadExtraHydrogenForExport(spaceship);
        Settlement settlement = freeMarsController.getFreeMarsModel().getRealm().getTile(spaceship.getCoordinate()).getSettlement();
        for (Resource resource : exportableResources) {
            TransferResourceCommand transferResourceCommand = new TransferResourceCommand(settlement, spaceship, resource, settlement.getResourceQuantity(resource));
            freeMarsController.execute(transferResourceCommand);
        }
    }

    private void loadExtraHydrogenForExport(Unit spaceship) {
        Settlement settlement = freeMarsController.getFreeMarsModel().getRealm().getTile(spaceship.getCoordinate()).getSettlement();
        Resource hydrogenResource = freeMarsController.getFreeMarsModel().getRealm().getResourceManager().getResource("Hydrogen");
        int hydrogenQuantity = settlement.getResourceQuantity(hydrogenResource);
        if (hydrogenQuantity >= 2000) {
            TransferResourceCommand transferResourceCommand = new TransferResourceCommand(settlement, spaceship, hydrogenResource, hydrogenQuantity - 2000);
            freeMarsController.execute(transferResourceCommand);
        }
    }

    private void sendSpaceshipToEarth(Unit spaceship) {
        Settlement colony = freeMarsController.getFreeMarsModel().getRealm().getTile(spaceship.getCoordinate()).getSettlement();
        RelocateUnitOrder relocateUnitOrder = new RelocateUnitOrder(freeMarsController.getFreeMarsModel().getRealm());
        relocateUnitOrder.setFreeMarsController(freeMarsController);
        relocateUnitOrder.setUnit(spaceship);
        relocateUnitOrder.setSource(Location.MARS);
        relocateUnitOrder.setDestination(Location.EARTH);
        relocateUnitOrder.setLaunchFromColony(colony);
        freeMarsController.execute(new UnitOrderAddCommand(freeMarsController.getFreeMarsModel().getRealm(), spaceship, relocateUnitOrder));
    }

    private void landSpaceshipToMarsColony(Unit spaceship) {
        Settlement colonyToLand = findColonyToLand();
        if (colonyToLand != null) {
            RelocateUnitOrder relocateUnitOrder = new RelocateUnitOrder(freeMarsController.getFreeMarsModel().getRealm());
            relocateUnitOrder.setFreeMarsController(freeMarsController);
            relocateUnitOrder.setUnit(spaceship);
            relocateUnitOrder.setSource(Location.MARS_ORBIT);
            relocateUnitOrder.setDestination(Location.MARS);
            relocateUnitOrder.setLandOnColony(colonyToLand);
            freeMarsController.execute(new UnitOrderAddCommand(freeMarsController.getFreeMarsModel().getRealm(), spaceship, relocateUnitOrder));
        }
    }

    private Settlement findColonyToLand() {
        Settlement colonyToLand = null;
        int maximumExportableResourceAmount = 0;
        SettlementImprovementType starportType = freeMarsController.getFreeMarsModel().getRealm().getSettlementImprovementManager().getImprovement("Starport");
        Iterator<Settlement> iterator = aiPlayer.getSettlementManager().getSettlementsIterator();
        while (iterator.hasNext()) {
            Settlement settlement = iterator.next();
            if (settlement.hasImprovementType(starportType)) {
                int exportableResourceAmount = 0;
                for (Resource resource : exportableResources) {
                    exportableResourceAmount = exportableResourceAmount + settlement.getResourceQuantity(resource);
                }
                if (exportableResourceAmount > maximumExportableResourceAmount) {
                    maximumExportableResourceAmount = exportableResourceAmount;
                    colonyToLand = settlement;
                }
            }
        }
        return colonyToLand;
    }

    private void sellSpaceshipResourcesToEarth(Unit spaceship) {
        Iterator<Resource> containedResourcesIterator = spaceship.getContainedResourcesIterator();
        while (containedResourcesIterator.hasNext()) {
            Resource resource = containedResourcesIterator.next();
            int quantity = spaceship.getResourceQuantity(resource);
            if (quantity > 0) {
                freeMarsController.execute(new SellResourceToEarthCommand(freeMarsController, spaceship, resource, quantity));
            }
        }
    }

    private void loadUnitsToSpaceship(Unit spaceship) {
        ArrayList<Unit> unitsToLoadToSpaceship = new ArrayList<Unit>();
        Iterator<Entry<Unit, Location>> iterator = freeMarsController.getFreeMarsModel().getEarth().getUnitLocationsIterator();
        while (iterator.hasNext()) {
            Entry<Unit, Location> entry = iterator.next();
            Location location = entry.getValue();
            if (location.equals(Location.EARTH)) {
                Unit locationUnit = entry.getKey();
                if (!spaceship.equals(locationUnit) && locationUnit.getPlayer().equals(spaceship.getPlayer())) {
                    if (spaceship.canContainUnit(locationUnit.getType())) {
                        unitsToLoadToSpaceship.add(locationUnit);
                    }
                }
            }
        }
        for (Unit unit : unitsToLoadToSpaceship) {
            CommandResult commandResult = freeMarsController.execute(new AddUnitToContainerCommand(spaceship, unit));
            if (commandResult.getCode() == CommandResult.RESULT_OK) {
                freeMarsController.getFreeMarsModel().getEarth().removeUnitLocation(unit);
            }
        }
    }

    private void financeColonists(Unit spaceship) {
        if (aiPlayer.getWealth() > aiPlayer.getReserveWealth()) {
            int creditsForColonists = (int) (aiPlayer.getWealth() * aiPlayer.getSpaceshipFinanceColonistsRatio());
            int financeCostPerColonist = Integer.parseInt(freeMarsController.getFreeMarsModel().getRealm().getProperty("finance_cost_per_colonist"));
            int colonistsToFinance = creditsForColonists / financeCostPerColonist;
            int weightPerCitizen = Integer.parseInt(freeMarsController.getFreeMarsModel().getRealm().getProperty("weight_per_citizen"));
            int totalWeight = colonistsToFinance * weightPerCitizen;
            if (spaceship.getRemainingCapacity() >= totalWeight) {
                freeMarsController.execute(new FinanceColonistsCommand(freeMarsController.getFreeMarsModel(), spaceship, colonistsToFinance));
            }
        }
    }

    private void sendSpaceshipToMarsOrbit(Unit spaceship) {
        RelocateUnitOrder relocateUnitOrder = new RelocateUnitOrder(freeMarsController.getFreeMarsModel().getRealm());
        relocateUnitOrder.setFreeMarsController(freeMarsController);
        relocateUnitOrder.setUnit(spaceship);
        relocateUnitOrder.setSource(Location.EARTH);
        relocateUnitOrder.setDestination(Location.MARS_ORBIT);
        freeMarsController.execute(new UnitOrderAddCommand(freeMarsController.getFreeMarsModel().getRealm(), spaceship, relocateUnitOrder));
    }

    private void buyFertilizerFromEarth(Unit spaceship) {
        Resource fertilizerResource = freeMarsController.getFreeMarsModel().getRealm().getResourceManager().getResource("Fertilizer");
        int totalFertilizerInColonies = 0;
        final Iterator<Settlement> settlementsIterator = aiPlayer.getSettlementsIterator();
        while (settlementsIterator.hasNext()) {
            Settlement colony = settlementsIterator.next();
            totalFertilizerInColonies = totalFertilizerInColonies + colony.getResourceQuantity(fertilizerResource);
        }
        if (totalFertilizerInColonies < aiPlayer.getMaximumTotalFertilizerInColonies()) {
            int fertilizerPurchaseQuantity = 400;
            freeMarsController.execute(new AIBuyResourceFromEarthCommand(freeMarsController.getFreeMarsModel().getEarth(), spaceship, fertilizerResource, fertilizerPurchaseQuantity));
        } else {
            StringBuilder log = new StringBuilder();
            log.append("Colonies have a total of ").append(totalFertilizerInColonies);
            log.append(" fertilizer in stock, more than the maximum value of ");
            log.append(aiPlayer.getMaximumTotalFertilizerInColonies());
            log.append(" fertilizer. Not buying fertilizer for \"").append(spaceship.getName());
            log.append("\".");
            logger.info(log.toString());
        }
    }

    private void buyGaussRifleFromEarth(Unit spaceship) {
        Resource gaussRifleResource = freeMarsController.getFreeMarsModel().getRealm().getResourceManager().getResource("Gauss rifle");
        int gaussRifleBuyCredits = (int) (aiPlayer.getWealth() * aiPlayer.getGaussRifleBuyRatio());
        int gaussRiflePrice = freeMarsController.getFreeMarsModel().getEarth().getEarthSellsAtPrice(gaussRifleResource);
        int quantity = gaussRifleBuyCredits / gaussRiflePrice;
        if (quantity > 0) {
            freeMarsController.execute(new AIBuyResourceFromEarthCommand(freeMarsController.getFreeMarsModel().getEarth(), spaceship, gaussRifleResource, quantity));
        }
    }

    private void unloadFertilizerFromSpaceship(Unit spaceship) {
        Resource fertilizerResource = freeMarsController.getFreeMarsModel().getRealm().getResourceManager().getResource("Fertilizer");
        int fertilizerQuantity = spaceship.getResourceQuantity(fertilizerResource);
        if (fertilizerQuantity > 0) {
            Settlement settlement = freeMarsController.getFreeMarsModel().getRealm().getTile(spaceship.getCoordinate()).getSettlement();
            TransferResourceCommand transferResourceCommand = new TransferResourceCommand(spaceship, settlement, fertilizerResource, fertilizerQuantity);
            freeMarsController.execute(transferResourceCommand);
        }
    }

    private void unloadGaussRifleFromSpaceship(Unit spaceship) {
        Resource gaussRifleResource = freeMarsController.getFreeMarsModel().getRealm().getResourceManager().getResource("Gauss rifle");
        int gaussRifleQuantity = spaceship.getResourceQuantity(gaussRifleResource);
        if (gaussRifleQuantity > 0) {
            Settlement settlement = freeMarsController.getFreeMarsModel().getRealm().getTile(spaceship.getCoordinate()).getSettlement();
            TransferResourceCommand transferResourceCommand = new TransferResourceCommand(spaceship, settlement, gaussRifleResource, gaussRifleQuantity);
            freeMarsController.execute(transferResourceCommand);
        }
    }

}
