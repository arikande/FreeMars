package org.freemars.ai;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.freemars.command.LoadColonistsCommand;
import org.freemars.command.UnloadAllColonistsCommand;
import org.freemars.controller.FreeMarsController;
import org.freemars.unit.automater.ExplorationHelper;
import org.freerealm.command.TransferResourceCommand;
import org.freerealm.command.UnitAdvanceToCoordinateCommand;
import org.freerealm.executor.CommandResult;
import org.freerealm.resource.Resource;
import org.freerealm.resource.ResourceStorer;
import org.freerealm.settlement.Settlement;
import org.freerealm.unit.FreeRealmUnitType;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class TransporterManager {

    private static final Logger logger = Logger.getLogger(TransporterManager.class);

    private final FreeMarsController freeMarsController;
    private final AIPlayer aiPlayer;
    private ArrayList<Resource> exportableResources;
    private final Resource energyResource;
    private final Resource fertilizerResource;
    private final int starportImprovementTypeId;
    private final List<Settlement> transporterDestinations;

    public TransporterManager(FreeMarsController freeMarsController, AIPlayer freeMarsAIPlayer) {
        this.freeMarsController = freeMarsController;
        this.aiPlayer = freeMarsAIPlayer;
        initExportableResources();
        energyResource = freeMarsController.getFreeMarsModel().getRealm().getResourceManager().getResource("Energy");
        fertilizerResource = freeMarsController.getFreeMarsModel().getRealm().getResourceManager().getResource("Fertilizer");
        starportImprovementTypeId = freeMarsController.getFreeMarsModel().getRealm().getSettlementImprovementManager().getImprovement("Starport").getId();
        transporterDestinations = new ArrayList<Settlement>();
    }

    public void manage() {
        transporterDestinations.clear();
        FreeRealmUnitType transporterUnitType = freeMarsController.getFreeMarsModel().getRealm().getUnitTypeManager().getUnitType("Transporter");
        List<Unit> transporters = aiPlayer.getUnitsOfType(transporterUnitType);
        logger.info(transporters.size() + " transporters will be managed.");
        for (Unit transporter : transporters) {
            manageTransporter(transporter);
        }
    }

    private void manageTransporter(Unit transporter) {
        if (transporter.getCoordinate() != null) {
            logger.info("Managing transporter \"" + transporter.getName() + "\"...");
            Settlement nearestColonyWithStarport = findNearestColonyWithStarport(transporter);
            if (nearestColonyWithStarport != null) {
                if (transporter.getRemainingCapacity() > 0) {
                    manageRemainingCapacityTransporter(transporter, nearestColonyWithStarport);
                } else if (transporter.getRemainingCapacity() == 0) {
                    manageFullTransporter(transporter, nearestColonyWithStarport);
                }
            } else {
                logger.info("No colony near transporter \"" + transporter.getName() + "\" has a starport. Switching to exploration mode.");
                ExplorationHelper.manageUnitInExplorationMode(freeMarsController, transporter);
            }
        }
    }

    private void manageRemainingCapacityTransporter(Unit transporter, Settlement nearestColonyWithStarport) {
        logger.info("Transporter named \"" + transporter.getName() + "\" has " + transporter.getRemainingCapacity() + " remaining capacity.");
        Settlement nearestColonyToGetResources = findColonyToGetResources(transporter, nearestColonyWithStarport);
        if (nearestColonyToGetResources != null) {
            logger.info("Nearest colony to get resources is " + nearestColonyToGetResources.getName() + ".");
            UnitAdvanceToCoordinateCommand unitAdvanceToCoordinateCommand
                    = new UnitAdvanceToCoordinateCommand(freeMarsController.getFreeMarsModel().getRealm(), transporter, nearestColonyToGetResources.getCoordinate());
            CommandResult commandResult = freeMarsController.execute(unitAdvanceToCoordinateCommand);
            if (commandResult.getCode() == CommandResult.RESULT_OK) {
                if (transporter.getCoordinate().equals(nearestColonyToGetResources.getCoordinate())) {
                    int fertilizerQuantity = transporter.getResourceQuantity(fertilizerResource);
                    int colonyFertilizerQuantity = nearestColonyToGetResources.getResourceQuantity(fertilizerResource);
                    if (fertilizerQuantity > 0 && colonyFertilizerQuantity < 1200) {
                        StringBuilder log = new StringBuilder();
                        log.append("Unloading ").append(fertilizerQuantity).append(" fertilizer from \"");
                        log.append(transporter.getName()).append("\" to ").append(nearestColonyToGetResources.getName());
                        log.append(".");
                        logger.info(log.toString());
                        freeMarsController.execute(new TransferResourceCommand(transporter, nearestColonyToGetResources, fertilizerResource, fertilizerQuantity));
                    }

                    if (transporter.getContainedPopulation() > 0) {
                        StringBuilder log = new StringBuilder();
                        log.append("Unloading ").append(transporter.getContainedPopulation()).append(" colonists from \"");
                        log.append(transporter.getName()).append("\" to ").append(nearestColonyToGetResources.getName());
                        log.append(".");
                        logger.info(log.toString());
                        freeMarsController.execute(new UnloadAllColonistsCommand(freeMarsController, nearestColonyToGetResources, transporter));
                    }
                    transferAllExportableResources(nearestColonyToGetResources, transporter);
                }
            } else {
                ExplorationHelper.manageUnitInExplorationMode(freeMarsController, transporter);
            }
        } else {
            ExplorationHelper.manageUnitInExplorationMode(freeMarsController, transporter);
        }
    }

    private void manageFullTransporter(Unit transporter, Settlement nearestColonyWithStarport) {
        logger.info("Transporter named \"" + transporter.getName() + "\" is full.");
        logger.info("Nearest colony with starport is " + nearestColonyWithStarport.getName() + ".");
        UnitAdvanceToCoordinateCommand unitAdvanceToCoordinateCommand
                = new UnitAdvanceToCoordinateCommand(freeMarsController.getFreeMarsModel().getRealm(), transporter, nearestColonyWithStarport.getCoordinate());
        CommandResult commandResult = freeMarsController.execute(unitAdvanceToCoordinateCommand);
        if (commandResult.getCode() == CommandResult.RESULT_OK) {
            if (transporter.getCoordinate().equals(nearestColonyWithStarport.getCoordinate())) {
                Iterator<Resource> resourcesIterator = transporter.getContainedResourcesIterator();
                while (resourcesIterator.hasNext()) {
                    Resource resource = resourcesIterator.next();
                    int quantity = transporter.getResourceQuantity(resource);
                    freeMarsController.execute(new TransferResourceCommand(transporter, nearestColonyWithStarport, resource, quantity));
                }
                if (nearestColonyWithStarport.getPopulation() > 800) {
                    freeMarsController.execute(new LoadColonistsCommand(freeMarsController, nearestColonyWithStarport, transporter, 10));
                }
                if (nearestColonyWithStarport.getResourceQuantity(fertilizerResource) > 500) {
                    freeMarsController.execute(new TransferResourceCommand(nearestColonyWithStarport, transporter, fertilizerResource, 300));
                }
            }
        } else {
            ExplorationHelper.manageUnitInExplorationMode(freeMarsController, transporter);
        }
    }

    private void initExportableResources() {
        exportableResources = new ArrayList<Resource>();
        exportableResources.add(freeMarsController.getFreeMarsModel().getRealm().getResourceManager().getResource("Hydrogen"));
        exportableResources.add(freeMarsController.getFreeMarsModel().getRealm().getResourceManager().getResource("Steel"));
        exportableResources.add(freeMarsController.getFreeMarsModel().getRealm().getResourceManager().getResource("Chemicals"));
        exportableResources.add(freeMarsController.getFreeMarsModel().getRealm().getResourceManager().getResource("Glass"));
        exportableResources.add(freeMarsController.getFreeMarsModel().getRealm().getResourceManager().getResource("Lumber"));
        exportableResources.add(freeMarsController.getFreeMarsModel().getRealm().getResourceManager().getResource("Magnesium"));
        exportableResources.add(freeMarsController.getFreeMarsModel().getRealm().getResourceManager().getResource("Iron"));
        exportableResources.add(freeMarsController.getFreeMarsModel().getRealm().getResourceManager().getResource("Silica"));
        exportableResources.add(freeMarsController.getFreeMarsModel().getRealm().getResourceManager().getResource("Minerals"));
    }

    private Settlement findColonyToGetResources(Unit transporter, Settlement starportColony) {
        Settlement colonyToGetResources = null;
        if (starportColony != null) {
            int totalExportableResourcesQuantity = 0;
            logger.info("Finding colony to get resources for " + transporter.getName() + transporter.getCoordinate() + ".");
            List<Settlement> nearSettlements = freeMarsController.getFreeMarsModel().getRealm().getSettlementsNearCoordinate(transporter.getCoordinate(), 0, 7, aiPlayer);
            logger.info("There are " + nearSettlements.size() + " colonies nearby.");
            for (Settlement settlement : nearSettlements) {
                if (!transporterDestinations.contains(settlement) && !settlement.hasImprovementType(starportImprovementTypeId)) {
                    int settlementExportableResourcesQuantity = findTotalExportableResourcesInColony(settlement, starportColony);
                    if (settlementExportableResourcesQuantity > totalExportableResourcesQuantity) {
                        colonyToGetResources = settlement;
                        totalExportableResourcesQuantity = settlementExportableResourcesQuantity;
                    }
                }
            }
        }
        if (!transporterDestinations.contains(colonyToGetResources)) {
            transporterDestinations.add(colonyToGetResources);
        }
        return colonyToGetResources;
    }

    private int findTotalExportableResourcesInColony(Settlement settlement, Settlement starportColony) {
        int totalExportableResourcesInColony = 0;
        for (Resource exportableResource : exportableResources) {
            if (starportColony.getRemainingCapacity(exportableResource) > 2000) {
                totalExportableResourcesInColony = totalExportableResourcesInColony + settlement.getResourceQuantity(exportableResource);
            }
        }
        return totalExportableResourcesInColony;
    }

    private void transferAllExportableResources(ResourceStorer source, ResourceStorer destination) {
        freeMarsController.execute(new TransferResourceCommand(source, destination, energyResource, 300));
        for (Resource exportableResource : exportableResources) {
            int amount = source.getResourceQuantity(exportableResource);
            freeMarsController.execute(new TransferResourceCommand(source, destination, exportableResource, amount));
        }
    }

    private Settlement findNearestColonyWithStarport(Unit transporter) {
        for (int i = 0; i < 12; i++) {
            List<Settlement> nearSettlements = freeMarsController.getFreeMarsModel().getRealm().getSettlementsNearCoordinate(transporter.getCoordinate(), i, i + 1, aiPlayer);
            for (Settlement settlement : nearSettlements) {
                if (settlement.hasImprovementType(starportImprovementTypeId)) {
                    return settlement;
                }
            }
        }
        logger.info("No colony with starport found for coordinate " + transporter.getCoordinate() + ".");
        return null;
    }

}
