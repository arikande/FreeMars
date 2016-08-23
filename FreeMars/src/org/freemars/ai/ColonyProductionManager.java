package org.freemars.ai;

import java.util.Iterator;

import org.apache.log4j.Logger;
import org.freemars.colony.FreeMarsColony;
import org.freemars.controller.FreeMarsController;
import org.freerealm.command.AddToSettlementProductionQueueCommand;
import org.freerealm.command.ClearSettlementProductionQueueCommand;
import org.freerealm.player.mission.Mission;
import org.freerealm.player.mission.SettlementImprovementCountMission;
import org.freerealm.resource.Resource;
import org.freerealm.settlement.SettlementBuildable;
import org.freerealm.settlement.improvement.SettlementImprovementType;
import org.freerealm.unit.FreeRealmUnitType;

/**
 *
 * @author Deniz ARIKAN
 */
public class ColonyProductionManager {

    private static final Logger logger = Logger.getLogger(ColonyProductionManager.class);
    
    private final AIPlayer aiPlayer;
    private final FreeMarsController freeMarsController;
    private final FreeRealmUnitType militiaUnitType;

    public ColonyProductionManager(FreeMarsController freeMarsController, AIPlayer aiPlayer) {
        this.freeMarsController = freeMarsController;
        this.aiPlayer = aiPlayer;
        militiaUnitType = freeMarsController.getFreeMarsModel().getRealm().getUnitTypeManager().getUnitType("Militia");
    }

    protected void manage(FreeMarsColony freeMarsColony) {
        SettlementBuildable settlementBuildable = getFutureProduction(freeMarsColony);
        if (settlementBuildable != null) {
            freeMarsController.execute(new ClearSettlementProductionQueueCommand(freeMarsColony));
            freeMarsController.execute(new AddToSettlementProductionQueueCommand(freeMarsColony, settlementBuildable));
        }
    }

    private SettlementBuildable getFutureProduction(FreeMarsColony freeMarsColony) {
        SettlementImprovementCountMission settlementImprovementCountMission = getCurrentSettlementImprovementCountMission();
        if (settlementImprovementCountMission != null) {
            Iterator<Integer> iterator = settlementImprovementCountMission.getTargetImprovementTypesIterator();
            while (iterator.hasNext()) {
                Integer settlementImprovementTypeId = iterator.next();
                SettlementImprovementType settlementImprovementType = freeMarsController.getFreeMarsModel().getRealm().getSettlementImprovementManager().getImprovement(settlementImprovementTypeId);
                int required = settlementImprovementCountMission.getTargetCountForImprovementType(settlementImprovementTypeId);
                int currentCount = aiPlayer.getSettlementCountHavingImprovementType(settlementImprovementType.getId());
                if (currentCount < required) {
                    if (freeMarsColony.canStartBuild(settlementImprovementType)) {
                        return settlementImprovementType;
                    }
                }
            }
        }

        SettlementImprovementType granaryType = freeMarsController.getFreeMarsModel().getRealm().getSettlementImprovementManager().getImprovement("Granary");
        if (freeMarsColony.canStartBuild(granaryType)) {
            return granaryType;
        }

        SettlementImprovementType waterExtractorType = freeMarsController.getFreeMarsModel().getRealm().getSettlementImprovementManager().getImprovement("Water extractor");
        if (freeMarsColony.canStartBuild(waterExtractorType)) {
            return waterExtractorType;
        }
        if (freeMarsColony.getPopulation() > 200) {
            SettlementImprovementType solarArrayType = freeMarsController.getFreeMarsModel().getRealm().getSettlementImprovementManager().getImprovement("Solar array");
            if (freeMarsColony.canStartBuild(solarArrayType)) {
                return solarArrayType;
            }
        }

        if (freeMarsColony.hasResourcesToComplete(militiaUnitType) && isMilitiaProductionNeeded()) {
            return militiaUnitType;
        }

        SettlementImprovementType hydrogenCollectorType = freeMarsController.getFreeMarsModel().getRealm().getSettlementImprovementManager().getImprovement("Hydrogen collector");
        if (freeMarsColony.canStartBuild(hydrogenCollectorType)) {
            return hydrogenCollectorType;
        }

        if (freeMarsColony.getPopulation() > 400) {
            SettlementImprovementType foodSiloType = freeMarsController.getFreeMarsModel().getRealm().getSettlementImprovementManager().getImprovement("Food silo");
            if (freeMarsColony.canStartBuild(foodSiloType)) {
                return foodSiloType;
            }
        }

        Resource ironResource = freeMarsController.getFreeMarsModel().getRealm().getResourceManager().getResource("Iron");
        if (freeMarsColony.getResourceProductionFromTerrain(ironResource) > 80) {
            SettlementImprovementType steelWorksType = freeMarsController.getFreeMarsModel().getRealm().getSettlementImprovementManager().getImprovement("Steel works");
            if (freeMarsColony.canStartBuild(steelWorksType)) {
                return steelWorksType;
            }
        }
        if (freeMarsColony.getResourceProductionFromTerrain(ironResource) > 120) {
            SettlementImprovementType steelFactoryType = freeMarsController.getFreeMarsModel().getRealm().getSettlementImprovementManager().getImprovement("Steel factory");
            if (freeMarsColony.canStartBuild(steelFactoryType)) {
                return steelFactoryType;
            }
        }

        Resource silicaResource = freeMarsController.getFreeMarsModel().getRealm().getResourceManager().getResource("Silica");
        if (freeMarsColony.getResourceProductionFromTerrain(silicaResource) > 80) {
            SettlementImprovementType glassWorksType = freeMarsController.getFreeMarsModel().getRealm().getSettlementImprovementManager().getImprovement("Glass works");
            if (freeMarsColony.canStartBuild(glassWorksType)) {
                return glassWorksType;
            }
        }

        Resource mineralsResource = freeMarsController.getFreeMarsModel().getRealm().getResourceManager().getResource("Minerals");
        if (freeMarsColony.getResourceQuantity(mineralsResource) > 5000 || freeMarsColony.getResourceProductionFromTerrain(mineralsResource) > 50) {
            SettlementImprovementType chemicalWorksType = freeMarsController.getFreeMarsModel().getRealm().getSettlementImprovementManager().getImprovement("Chemical works");
            if (freeMarsColony.canStartBuild(chemicalWorksType)) {
                return chemicalWorksType;
            }
        }

        FreeRealmUnitType engineerUnitType = freeMarsController.getFreeMarsModel().getRealm().getUnitTypeManager().getUnitType("Engineer");
        int engineerCount = aiPlayer.getUnitManager().getUnitsOfTypeCount(engineerUnitType);
        int engineerProductionCount = aiPlayer.getSettlementManager().getSettlementsProducingBuildableCount(engineerUnitType);
        int engineersRequiredCount = ((int) Math.ceil((double) aiPlayer.getSettlementCount() / 3)) + 1;
        if (engineersRequiredCount > engineerCount + engineerProductionCount) {
            return engineerUnitType;
        }
        SettlementImprovementType workshopType = freeMarsController.getFreeMarsModel().getRealm().getSettlementImprovementManager().getImprovement("Workshop");
        if (freeMarsColony.canStartBuild(workshopType)) {
            return workshopType;
        }
        FreeRealmUnitType transporterUnitType = freeMarsController.getFreeMarsModel().getRealm().getUnitTypeManager().getUnitType("Transporter");
        int transporterCount = aiPlayer.getUnitManager().getUnitsOfTypeCount(transporterUnitType);
        int transporterProductionCount = aiPlayer.getSettlementManager().getSettlementsProducingBuildableCount(transporterUnitType);
        if (transporterCount + transporterProductionCount < 6) {
            return transporterUnitType;
        }

        SettlementImprovementType wallType = freeMarsController.getFreeMarsModel().getRealm().getSettlementImprovementManager().getImprovement("Wall");
        if (freeMarsColony.canStartBuild(wallType)) {
            return wallType;
        }

        SettlementImprovementType marsTransitSystemType = freeMarsController.getFreeMarsModel().getRealm().getSettlementImprovementManager().getImprovement("Mars transit system");
        if (freeMarsColony.canStartBuild(marsTransitSystemType)) {
            return marsTransitSystemType;
        }

        return null;
    }

    private SettlementImprovementCountMission getCurrentSettlementImprovementCountMission() {
        Iterator<Mission> iterator = aiPlayer.getMissionsIterator();
        while (iterator.hasNext()) {
            Mission mission = iterator.next();
            if (mission.getStatus() == Mission.STATUS_ACTIVE && mission instanceof SettlementImprovementCountMission) {
                SettlementImprovementCountMission settlementImprovementCountMission = (SettlementImprovementCountMission) mission;
                return settlementImprovementCountMission;
            }
        }
        return null;
    }

    private boolean isMilitiaProductionNeeded() {
        int totalMilitiaCount = aiPlayer.getUnitsOfType(militiaUnitType).size();
        int requiredMilitiaCount = MilitiaManager.getRequiredMilitiaCountForPlayer(aiPlayer);
        boolean militiaProductionNeeded = requiredMilitiaCount > totalMilitiaCount;
        StringBuilder log = new StringBuilder();
        log.append("Total militia : ");
        log.append(totalMilitiaCount);
        log.append(" Required militia : ");
        log.append(requiredMilitiaCount);
        logger.info(log);
        return militiaProductionNeeded;
    }

}
