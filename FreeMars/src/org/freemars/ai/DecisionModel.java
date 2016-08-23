package org.freemars.ai;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.freemars.controller.AISplashDisplayer;
import org.freemars.controller.FreeMarsController;
import org.freemars.earth.Location;
import org.freemars.earth.order.RelocateUnitOrder;
import org.freemars.player.ResourceTradeData;
import org.freerealm.command.AddSettlementImprovementCommand;
import org.freerealm.command.AddUnitCommand;
import org.freerealm.command.WealthAddCommand;
import org.freerealm.map.Coordinate;
import org.freerealm.player.mission.Mission;
import org.freerealm.resource.Resource;
import org.freerealm.settlement.Settlement;
import org.freerealm.settlement.improvement.SettlementImprovement;
import org.freerealm.settlement.improvement.SettlementImprovementType;
import org.freerealm.settlement.workforce.WorkForce;
import org.freerealm.tile.Tile;
import org.freerealm.unit.FreeRealmUnitType;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class DecisionModel {

    private static final Logger logger = Logger.getLogger(DecisionModel.class);

    private final ColonyManager colonyManager;
    private final UnitManager unitManager;
    private final WealthManager wealthManager;
    private final DiplomacyManager diplomacyManager;
    private final AIPlayer aiPlayer;
    private final FreeMarsController freeMarsController;
    private ArrayList<FreeRealmUnitType> manageableSpaceshipTypes;

    public DecisionModel(FreeMarsController freeMarsController, AIPlayer aiPlayer) {
        this.freeMarsController = freeMarsController;
        this.aiPlayer = aiPlayer;
        colonyManager = new ColonyManager(freeMarsController, aiPlayer);
        unitManager = new UnitManager(freeMarsController, aiPlayer, this);
        wealthManager = new WealthManager(freeMarsController, aiPlayer, this);
        diplomacyManager = new DiplomacyManager(freeMarsController, aiPlayer);
    }

    public void play() throws Exception {
        boolean displayAIPlayerProgressWindow = Boolean.valueOf(freeMarsController.getFreeMarsModel().getFreeMarsPreferences().getProperty("display_ai_player_progress_window"));
        if (displayAIPlayerProgressWindow) {
            AISplashDisplayer.setCurrentProcessLabelText("Checking Earth support...");
        }
        logger.info("Checking Earth support...");
        checkEarthSupport();
        logger.info("Earth support checked.");
        if (displayAIPlayerProgressWindow) {
            AISplashDisplayer.setCurrentProcessLabelText("Managing units...");
        }
        logger.info("Managing units...");
        unitManager.manage();
        logger.info("Units managed.");
        if (displayAIPlayerProgressWindow) {
            AISplashDisplayer.setCurrentProcessLabelText("Managing colonies...");
        }
        logger.info("Managing colonies...");
        colonyManager.manage();
        logger.info("Colonies managed.");
        if (displayAIPlayerProgressWindow) {
            AISplashDisplayer.setCurrentProcessLabelText("Managing wealth...");
        }
        wealthManager.manageWealth();
        if (displayAIPlayerProgressWindow) {
            AISplashDisplayer.setCurrentProcessLabelText("Managing diplomacy...");
        }
        logger.info("Managing diplomacy for the " + aiPlayer.getName() + "...");
        diplomacyManager.manage();
        logger.info("Diplomacy managed.");

        logAIPlayerStatus();
    }

    public ArrayList<FreeRealmUnitType> getManageableSpaceshipTypes() {
        if (manageableSpaceshipTypes == null) {
            manageableSpaceshipTypes = new ArrayList<FreeRealmUnitType>();
            manageableSpaceshipTypes.add(freeMarsController.getFreeMarsModel().getRealm().getUnitTypeManager().getUnitType("Bulk freighter"));
            manageableSpaceshipTypes.add(freeMarsController.getFreeMarsModel().getRealm().getUnitTypeManager().getUnitType("Freighter"));
            manageableSpaceshipTypes.add(freeMarsController.getFreeMarsModel().getRealm().getUnitTypeManager().getUnitType("Shuttle"));
        }
        return manageableSpaceshipTypes;
    }

    private void checkEarthSupport() {
        if (aiPlayer.canReceiveFreeStarport()) {
            logger.debug(aiPlayer.getName() + " receive free starport.");
            Settlement colony = getFreeStarportColony();
            if (colony != null) {
                SettlementImprovementType starportType = freeMarsController.getFreeMarsModel().getRealm().getSettlementImprovementManager().getImprovement("Starport");
                freeMarsController.execute(new AddSettlementImprovementCommand(colony, starportType));
                freeMarsController.execute(new WealthAddCommand(aiPlayer, 1920));
                aiPlayer.setReceivedFreeStarport(true);
            }
        }
        if (aiPlayer.canReceiveFreeColonizer()) {
            logger.debug(aiPlayer.getName() + " receive free colonizer.");
            Settlement colony = getFreeColonizerColony();
            if (colony != null) {
                FreeRealmUnitType colonizerType = freeMarsController.getFreeMarsModel().getRealm().getUnitTypeManager().getUnitType("Colonizer");
                Unit unit = new Unit(freeMarsController.getFreeMarsModel().getRealm(), colonizerType, colony.getCoordinate(), aiPlayer);
                freeMarsController.execute(new AddUnitCommand(freeMarsController.getFreeMarsModel().getRealm(), aiPlayer, unit));
                freeMarsController.execute(new WealthAddCommand(aiPlayer, 900));
                aiPlayer.setReceivedFreeColonizer(true);
            }
        }
        if (aiPlayer.canReceiveFreeTransporter()) {
            logger.debug(aiPlayer.getName() + " receive free transporter.");
            Settlement colony = getFreeTransporterColony();
            if (colony != null) {
                FreeRealmUnitType transporterType = freeMarsController.getFreeMarsModel().getRealm().getUnitTypeManager().getUnitType("Transporter");
                Unit unit = new Unit(freeMarsController.getFreeMarsModel().getRealm(), transporterType, colony.getCoordinate(), aiPlayer);
                freeMarsController.execute(new AddUnitCommand(freeMarsController.getFreeMarsModel().getRealm(), aiPlayer, unit));
                freeMarsController.execute(new WealthAddCommand(aiPlayer, 720));
                aiPlayer.setReceivedFreeTransporter(true);
            }
        }
        if (aiPlayer.canReceiveFreeFinancialAid()) {
            logger.debug(aiPlayer.getName() + " receive free financial aid.");
            freeMarsController.execute(new WealthAddCommand(aiPlayer, 20000));
            aiPlayer.setReceivedFreeFinancialAid(true);
        }
    }

    private Settlement getFreeStarportColony() {
        return getMostPopulousColony();
    }

    private Settlement getFreeColonizerColony() {
        return getMostPopulousColony();
    }

    private Settlement getFreeTransporterColony() {
        return getMostPopulousColony();
    }

    private Settlement getMostPopulousColony() {
        Settlement mostPopulousColony = null;
        Iterator<Settlement> iterator = aiPlayer.getSettlementsIterator();
        while (iterator.hasNext()) {
            Settlement settlement = iterator.next();
            if (mostPopulousColony == null || settlement.getPopulation() > mostPopulousColony.getPopulation()) {
                mostPopulousColony = settlement;
            }
        }
        return mostPopulousColony;
    }

    private void logAIPlayerStatus() {
        StringBuilder log = new StringBuilder();
        log.append(" *** ");
        log.append(aiPlayer.getName());
        log.append(" turn ");
        log.append(freeMarsController.getFreeMarsModel().getNumberOfTurns());
        log.append(" summary");
        log.append(" *** ");
        logger.debug(log);
        log.setLength(0);
        log.append("Population : ").append(aiPlayer.getPopulation()).append(", Colonies : ");
        log.append(aiPlayer.getSettlementCount());
        log.append(", Wealth : ").append(aiPlayer.getWealth()).append(", Earth tax : ");
        log.append(aiPlayer.getEarthTaxRate()).append("%");
        log.append(", Explored Coordinate Count : ").append(aiPlayer.getExploredCoordinateCount());
        log.append(", Total expenses : " + aiPlayer.getTotalExpenses());
        logger.debug(log);
        logger.debug("-----");
        logColonies();
        logger.debug("-----");
        logUnits();
        logger.debug("-----");
        logMissions();
        logger.debug("-----");
        logTrade();
    }

    private void logColonies() {
        logger.debug("* COLONIES *");
        StringBuilder log = new StringBuilder();
        Iterator<Settlement> iterator = aiPlayer.getSettlementsIterator();
        while (iterator.hasNext()) {
            Settlement settlement = iterator.next();
            log.setLength(0);
            log.append(settlement.getName());
            log.append(" ");
            log.append(settlement.getCoordinate());
            log.append(" - Population : ");
            log.append(settlement.getPopulation());
            log.append(" - Current production : ");
            log.append(settlement.getCurrentProduction());
            log.append(" - Production points : ");
            log.append(settlement.getProductionPoints());
            log.append("(+");
            log.append(settlement.getProductionPointsPerTurn());
            log.append(")");
            logger.debug(log);

            log.setLength(0);
            Iterator<SettlementImprovement> settlementImprovementsIterator = settlement.getImprovementsIterator();
            int logItem = 0;
            while (settlementImprovementsIterator.hasNext()) {
                SettlementImprovement settlementImprovement = settlementImprovementsIterator.next();
                if (logItem > 0) {
                    log.append(", ");
                    if (logItem == 4) {
                        logItem = 0;
                        logger.debug(log);
                        log.setLength(0);
                    }
                }
                log.append(settlementImprovement);
                if (settlementImprovement.getType().getMaximumWorkers() > 0) {
                    log.append("(");
                    log.append(settlementImprovement.getNumberOfWorkers());
                    log.append(")");
                }
                logItem++;
            }
            logger.debug(log);

            log.setLength(0);
            Iterator<Resource> resourcesIterator = settlement.getContainedResourcesIterator();
            logItem = 0;
            while (resourcesIterator.hasNext()) {
                Resource resource = resourcesIterator.next();
                int quantity = settlement.getResourceQuantity(resource);
                int production = settlement.getResourceProductionFromImprovements(resource) + settlement.getResourceProductionFromTerrain(resource);
                if (quantity > 0 || production > 0) {
                    if (logItem > 0) {
                        log.append(", ");
                        if (logItem == 4) {
                            logItem = 0;
                            logger.debug(log);
                            log.setLength(0);
                        }
                    }
                    log.append(resource.getName());
                    log.append(" : ");
                    log.append(quantity);
                    if (production > 0) {
                        log.append("(+");
                        log.append(production);
                        log.append(")");
                    }
                    logItem++;
                }
            }
            logger.debug(log);
            log.setLength(0);
            Iterator<WorkForce> workforceIterator = settlement.getWorkForceManager().getWorkForceIterator();
            while (workforceIterator.hasNext()) {
                WorkForce workForce = workforceIterator.next();
                Coordinate workForceCoordinate = workForce.getCoordinate();
                Tile tile = freeMarsController.getFreeMarsModel().getTile(workForceCoordinate);

                log.append(workForceCoordinate);
                log.append(" - ");
                log.append(tile.getType().getName());
                if (tile.getBonusResource() != null) {
                    log.append("(" + tile.getBonusResource().getName() + ")");
                }
                if (tile.getCustomModifier("Fertilizer") != null) {
                    log.append("(F)");
                }
                log.append(" - ");
                log.append(workForce.getResource());
                log.append(" - ");
                log.append(workForce.getNumberOfWorkers());
                log.append("; ");
            }
            logger.debug(log);
            log.setLength(0);
            if (settlement.getTile().getNumberOfUnits() > 0) {
                Iterator<Unit> unitsInSettlementIterator = settlement.getTile().getUnitsIterator();
                while (unitsInSettlementIterator.hasNext()) {
                    Unit unit = unitsInSettlementIterator.next();
                    log.append(unit.getName());
                    if (unitsInSettlementIterator.hasNext()) {
                        log.append(", ");
                    }
                }
                logger.debug(log);
            }
            logger.debug("*");
        }

    }

    private void logUnits() {
        logger.debug("* UNITS *");
        StringBuilder log = new StringBuilder();
        Iterator<Unit> iterator = aiPlayer.getUnitsIterator();
        while (iterator.hasNext()) {
            log.setLength(0);
            Unit unit = iterator.next();
            log.append(unit.getName());
            if (unit.getCoordinate() != null) {
                log.append(" - ");
                log.append(unit.getCoordinate());
                Coordinate coordinate = unit.getCoordinate();
                if (freeMarsController.getFreeMarsModel().getTile(coordinate).getSettlement() != null) {
                    log.append(" - ");
                    log.append("in ");
                    log.append(freeMarsController.getFreeMarsModel().getTile(coordinate).getSettlement().getName());
                }

            }
            if (unit.getStatus() == Unit.UNIT_SUSPENDED) {
                log.append(" - ");
                log.append("SUSPENDED");
            }
            if (unit.getCurrentOrder() != null) {
                log.append(" - ");
                log.append(unit.getCurrentOrder().getName());
            }
            Location location = freeMarsController.getFreeMarsModel().getEarth().getUnitLocation(unit);
            if (location != null) {
                if (location.equals(Location.EARTH)) {
                    log.append(" - EARTH");
                } else if (location.equals(Location.TRAVELING_TO_MARS)) {
                    log.append(" - TRAVELING TO MARS");
                } else if (location.equals(Location.TRAVELING_TO_EARTH)) {
                    log.append(" - TRAVELING TO EARTH");
                } else if (location.equals(Location.MARS_ORBIT)) {
                    log.append(" - MARS ORBIT");
                }
                if (unit.getCurrentOrder() != null && (unit.getCurrentOrder() instanceof RelocateUnitOrder)) {
                    RelocateUnitOrder relocateUnitOrder = (RelocateUnitOrder) unit.getCurrentOrder();
                    if (relocateUnitOrder.getLandOnColony() != null) {
                        log.append(" - Land on colony : ");
                        log.append(relocateUnitOrder.getLandOnColony());
                    }
                }
            }
            if (unit.getTotalContainedWeight() > 0) {
                log.append(" -");
                if (unit.getContainedPopulation() > 0) {
                    log.append(" [");
                    log.append("Colonists : ");
                    log.append(unit.getContainedPopulation());
                    log.append("]");
                }
                if (unit.getContainedUnitCount() > 0) {
                    log.append(" [");
                    Iterator<Integer> containedUnitsIterator = unit.getContainedUnitsIterator();
                    int i = 1;
                    while (containedUnitsIterator.hasNext()) {
                        Integer unitId = containedUnitsIterator.next();
                        Unit containedUnit = unit.getPlayer().getUnit(unitId);
                        log.append(containedUnit.getName());
                        if (i < unit.getContainedUnitCount()) {
                            log.append(",");
                        }
                    }
                    log.append("]");
                }
                if (unit.getTotalResourceWeight() > 0) {
                    log.append(" [");
                    Iterator<Resource> containedResourcesIterator = unit.getContainedResourcesIterator();
                    boolean firstResource = true;
                    while (containedResourcesIterator.hasNext()) {
                        Resource resource = containedResourcesIterator.next();
                        int quantity = unit.getResourceQuantity(resource);
                        if (quantity > 0) {
                            if (!firstResource) {
                                log.append(", ");
                            }
                            firstResource = false;
                            log.append(resource);
                            log.append(":");
                            log.append(quantity);
                        }
                    }
                    log.append("]");
                }
            }
            logger.debug(log);
        }
        logger.debug("Number of units : " + aiPlayer.getUnitCount());
    }

    private void logMissions() {
        logger.debug("* MISSIONS *");
        StringBuilder log = new StringBuilder();
        Iterator<Mission> missionsIterator = aiPlayer.getMissionsIterator();
        int missionCount = 0;
        while (missionsIterator.hasNext()) {
            missionCount++;
            log.setLength(0);
            Mission mission = missionsIterator.next();
            log.append(mission.getMissionName());
            log.append(" - ");
            int remainingTurns = mission.getDuration() - (freeMarsController.getFreeMarsModel().getNumberOfTurns() - mission.getTurnIssued());
            if (remainingTurns >= 0) {
                log.append(remainingTurns);
                log.append(" - ");
            }
            log.append(mission.getProgressPercent());
            log.append("%");
            if (mission.getStatus() == Mission.STATUS_ACTIVE) {
                log.append(" - ACTIVE");
            } else if (mission.getStatus() == Mission.STATUS_COMPLETED) {
                log.append(" - COMPLETED");
            } else if (mission.getStatus() == Mission.STATUS_FAILED) {
                log.append(" - FAILED");
            }
            logger.debug(log);
        }
        if (missionCount > 0) {
            logger.debug("Success : " + aiPlayer.getCompletedMissionPercent() + "%");
        } else {
            logger.debug("No missions");
        }
    }

    private void logTrade() {
        logger.debug("* TRADE *");
        StringBuilder log = new StringBuilder();
        Iterator<Resource> iterator = aiPlayer.getResourceTradeDataIterator();
        while (iterator.hasNext()) {
            Resource resource = iterator.next();
            log.setLength(0);
            ResourceTradeData resourceTradeData = aiPlayer.getResourceTradeData(resource.getId());
            if (resourceTradeData.getQuantityExported() > 0) {
                log.append(resource.getName());
                log.append(" - Exported : ");
                log.append(resourceTradeData.getQuantityExported());
                log.append(", ");
            }
            if (resourceTradeData.getQuantityImported() > 0) {
                log.append(resource.getName());
                log.append(" - Imported : ");
                log.append(resourceTradeData.getQuantityImported());
                log.append(", ");
            }
            log.append("Net income : ");
            log.append(resourceTradeData.getNetIncome());
            logger.debug(log);
        }
        int tradeIncome = aiPlayer.getTradeIncome();
        int tradeProfit = aiPlayer.getTradeProfit();
        logger.debug("Trade income : " + tradeIncome);
        logger.debug("Tax paid : " + (tradeIncome - tradeProfit));
        logger.debug("Net income from trade : " + tradeProfit);
    }

}
