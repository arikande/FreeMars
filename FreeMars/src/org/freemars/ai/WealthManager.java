package org.freemars.ai;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;
import org.freemars.ai.command.AIBuyColonyProductionCommand;
import org.freemars.controller.FreeMarsController;
import org.freemars.earth.command.BuyUnitFromEarthCommand;
import org.freemars.player.FreeMarsPlayer;
import org.freerealm.player.mission.Mission;
import org.freerealm.player.mission.SettlementCountMission;
import org.freerealm.player.mission.SettlementImprovementCountMission;
import org.freerealm.settlement.Settlement;
import org.freerealm.settlement.improvement.SettlementImprovementType;
import org.freerealm.unit.FreeRealmUnitType;

/**
 *
 * @author Deniz ARIKAN
 */
public class WealthManager {

    private static final Logger logger = Logger.getLogger(WealthManager.class);
    private final FreeMarsController freeMarsController;
    private final AIPlayer aiPlayer;
    private ArrayList<FreeRealmUnitType> spaceshipTypesToBuy;

    private FreeRealmUnitType shuttleType;
    private FreeRealmUnitType freighterType;
    private FreeRealmUnitType bulkFreighterType;

    public WealthManager(FreeMarsController freeMarsController, AIPlayer freeMarsAIPlayer, DecisionModel decisionModel) {
        this.freeMarsController = freeMarsController;
        this.aiPlayer = freeMarsAIPlayer;
        initSpaceshipTypesToBuy();
    }

    public void manageWealth() {
        long start = System.currentTimeMillis();
        logger.info("Managing wealth...");
        checkSpaceshipPurchase();
        int freighterCount = aiPlayer.getUnitsOfType(freighterType).size();
        if (freeMarsController.getFreeMarsModel().getNumberOfTurns() > 100 && freighterCount < 2) {
            return;
        }
        int bulkFreighterCount = aiPlayer.getUnitsOfType(bulkFreighterType).size();
        if (freeMarsController.getFreeMarsModel().getNumberOfTurns() > 200 && bulkFreighterCount < 1) {
            return;
        }
        checkColonizerPurchase();
        checkTransporterPurchase();
        checkColonyProductionPurchase();
        long duration = System.currentTimeMillis() - start;
        logger.info("Wealth managed in " + duration + " milliseconds.");
    }

    private void initSpaceshipTypesToBuy() {
        shuttleType = freeMarsController.getFreeMarsModel().getRealm().getUnitTypeManager().getUnitType("Shuttle");
        freighterType = freeMarsController.getFreeMarsModel().getRealm().getUnitTypeManager().getUnitType("Freighter");
        bulkFreighterType = freeMarsController.getFreeMarsModel().getRealm().getUnitTypeManager().getUnitType("Bulk freighter");
        spaceshipTypesToBuy = new ArrayList<FreeRealmUnitType>();
        spaceshipTypesToBuy.add(bulkFreighterType);
        spaceshipTypesToBuy.add(freighterType);
        spaceshipTypesToBuy.add(shuttleType);
    }

    private void checkColonizerPurchase() {
        FreeRealmUnitType colonizerUnitType = freeMarsController.getFreeMarsModel().getRealm().getUnitTypeManager().getUnitType("Colonizer");
        if (colonizerPurchase()) {
            freeMarsController.execute(new BuyUnitFromEarthCommand(freeMarsController.getFreeMarsModel(), aiPlayer, colonizerUnitType));
        }
    }

    private boolean colonizerPurchase() {
        FreeRealmUnitType colonizerUnitType = freeMarsController.getFreeMarsModel().getRealm().getUnitTypeManager().getUnitType("Colonizer");
        if (freeMarsController.getFreeMarsModel().getEarth().getEarthSellsAtPrice(colonizerUnitType) <= aiPlayer.getWealth()) {
            int settlementCount = aiPlayer.getSettlementCount();
            int colonizerCount = aiPlayer.getUnitManager().getUnitsOfTypeCount(colonizerUnitType);
            int colonyAndColonizerTotal = settlementCount + colonizerCount;
            Iterator<Mission> iterator = aiPlayer.getMissionsIterator();
            while (iterator.hasNext()) {
                Mission mission = iterator.next();
                if (mission.getStatus() == Mission.STATUS_ACTIVE && mission instanceof SettlementCountMission) {
                    SettlementCountMission settlementCountMission = (SettlementCountMission) mission;
                    if (settlementCountMission.getSettlementCount() > colonyAndColonizerTotal) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void checkTransporterPurchase() {
        if (freeMarsController.getFreeMarsModel().getNumberOfTurns() > FreeMarsPlayer.FREE_TRANSPORTER_MIN_TURN) {
            FreeRealmUnitType transporterUnitType = freeMarsController.getFreeMarsModel().getRealm().getUnitTypeManager().getUnitType("Transporter");
            int transporterCount = aiPlayer.getUnitManager().getUnitsOfTypeCount(transporterUnitType);
            if (transporterCount < (aiPlayer.getPopulation() / 500)) {
                freeMarsController.execute(new BuyUnitFromEarthCommand(freeMarsController.getFreeMarsModel(), aiPlayer, transporterUnitType));
            }
        }
    }

    private void checkSpaceshipPurchase() {
        int shuttleCount = aiPlayer.getUnitsOfType(shuttleType).size();
        if (shuttleCount < 3) {
            int price = freeMarsController.getFreeMarsModel().getEarth().getEarthSellsAtPrice(shuttleType);
            if (aiPlayer.getWealth() >= price) {
                freeMarsController.execute(new BuyUnitFromEarthCommand(freeMarsController.getFreeMarsModel(), aiPlayer, shuttleType));
            }
        }
        int freighterCount = aiPlayer.getUnitsOfType(freighterType).size();
        if (freighterCount < 2) {
            int price = freeMarsController.getFreeMarsModel().getEarth().getEarthSellsAtPrice(freighterType);
            if (aiPlayer.getWealth() >= price) {
                freeMarsController.execute(new BuyUnitFromEarthCommand(freeMarsController.getFreeMarsModel(), aiPlayer, freighterType));
            }
        }
        int bulkFreighterCount = aiPlayer.getUnitsOfType(bulkFreighterType).size();
        if (bulkFreighterCount < 2) {
            int price = freeMarsController.getFreeMarsModel().getEarth().getEarthSellsAtPrice(bulkFreighterType);
            if (aiPlayer.getWealth() >= price) {
                freeMarsController.execute(new BuyUnitFromEarthCommand(freeMarsController.getFreeMarsModel(), aiPlayer, bulkFreighterType));
            }
        }
    }

    private void checkColonyProductionPurchase() {
        logger.info("Checking colony production purchase...");
        SettlementImprovementCountMission settlementImprovementCountMission = getCurrentSettlementImprovementCountMission();
        if (settlementImprovementCountMission != null) {
            logger.info("Player " + aiPlayer.getName() + " has an active \"Settlement improvement count mission\".");
            Iterator<Integer> iterator = settlementImprovementCountMission.getTargetImprovementTypesIterator();
            while (iterator.hasNext()) {
                Integer typeId = iterator.next();
                int targetCount = settlementImprovementCountMission.getTargetCountForImprovementType(typeId);
                int currentCount = aiPlayer.getSettlementCountHavingImprovementType(typeId);
                int required = targetCount - currentCount;
                if (required < 0) {
                    required = 0;
                }
                String settlementImprovementTypeName = freeMarsController.getFreeMarsModel().getRealm().getSettlementImprovementManager().getImprovement(typeId).getName();
                logger.info(settlementImprovementTypeName + " - Required : " + required + " Built : " + currentCount);
                if (required > 0) {
                    List<Settlement> settlements = aiPlayer.getSettlementsBuildingImprovementOfType(typeId);
                    for (Settlement settlement : settlements) {
                        logger.info("Buying settlement improvement count mission production for " + settlementImprovementTypeName + " in " + settlement.getName() + ".");
                        freeMarsController.execute(new AIBuyColonyProductionCommand(freeMarsController.getFreeMarsModel().getRealm(), settlement, 800));
                        required--;
                        if (required == 0) {
                            break;
                        }
                    }
                }
            }
        } else {
            logger.info("Player " + aiPlayer.getName() + " does not have an active \"Settlement improvement count mission\".");
            logger.info("Colony production purchase not needed for mission.");
        }

        SettlementImprovementType starportType = freeMarsController.getFreeMarsModel().getRealm().getSettlementImprovementManager().getImprovement("Starport");
        Iterator<Settlement> iterator = aiPlayer.getSettlementsIterator();
        int productionPointCost = Integer.parseInt(freeMarsController.getFreeMarsModel().getRealm().getProperty("production_point_cost"));
        String currencyUnit = freeMarsController.getFreeMarsModel().getRealm().getProperty("currency_unit");
        while (iterator.hasNext()) {
            Settlement settlement = iterator.next();
            int colonyBuyProductionCredits = (int) ((aiPlayer.getWealth() * aiPlayer.getBuyProductionFor100PopulationRatio() * settlement.getPopulation()) / 100);
            int colonyBuyProductionPoints = colonyBuyProductionCredits / productionPointCost;
            if (settlement.getCurrentProduction() != null) {
                if (settlement.hasImprovementType(starportType)) {
                    StringBuilder stringBuilder = new StringBuilder();
                    colonyBuyProductionPoints = colonyBuyProductionPoints * 5;
                    stringBuilder.append("Buying ");
                    stringBuilder.append(colonyBuyProductionPoints).append(" production points for ");
                    stringBuilder.append(colonyBuyProductionPoints * productionPointCost);
                    stringBuilder.append(" ");
                    stringBuilder.append(currencyUnit);
                    stringBuilder.append(" in the colony of ");
                    stringBuilder.append(settlement.getName()).append(".");
                    logger.info(stringBuilder.toString());
                    freeMarsController.execute(new AIBuyColonyProductionCommand(freeMarsController.getFreeMarsModel().getRealm(), settlement, colonyBuyProductionPoints));
                } else {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Buying ");
                    stringBuilder.append(colonyBuyProductionPoints).append(" production points for ");
                    stringBuilder.append(colonyBuyProductionPoints * productionPointCost);
                    stringBuilder.append(" ");
                    stringBuilder.append(currencyUnit);
                    stringBuilder.append(" in the colony of ");
                    stringBuilder.append(settlement.getName()).append(".");
                    logger.info(stringBuilder.toString());
                    freeMarsController.execute(new AIBuyColonyProductionCommand(freeMarsController.getFreeMarsModel().getRealm(), settlement, colonyBuyProductionPoints));
                }
            }
        }
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

}
