package org.freemars.ai;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.freemars.controller.FreeMarsController;
import org.freemars.unit.automater.ExplorationHelper;
import org.freerealm.command.UnitAdvanceToCoordinateCommand;
import org.freerealm.command.UnitOrderAddCommand;
import org.freerealm.executor.CommandResult;
import org.freerealm.executor.order.Fortify;
import org.freerealm.map.Coordinate;
import org.freerealm.settlement.Settlement;
import org.freerealm.tile.Tile;
import org.freerealm.unit.FreeRealmUnitType;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class MilitiaManager {

    private static final Logger logger = Logger.getLogger(MilitiaManager.class);
    private final FreeMarsController freeMarsController;
    private final AIPlayer aiPlayer;
    private final DecisionModel decisionModel;
    private final FreeRealmUnitType militiaUnitType;

    public MilitiaManager(FreeMarsController freeMarsController, AIPlayer aiPlayer, DecisionModel decisionModel) {
        this.freeMarsController = freeMarsController;
        this.aiPlayer = aiPlayer;
        this.decisionModel = decisionModel;
        militiaUnitType = freeMarsController.getFreeMarsModel().getRealm().getUnitTypeManager().getUnitType("Militia");
    }

    public void manage() {
        long start = System.currentTimeMillis();
        List<Unit> availableMilitia = getAvailableMilitia();
        if (availableMilitia.size() > 0) {
            logger.info(availableMilitia.size() + " militia will be managed.");
            for (Unit militia : availableMilitia) {
                manageMilitia(militia);
            }
            long duration = System.currentTimeMillis() - start;
            logger.info("Militia units managed in " + duration + " milliseconds");
        } else {
            logger.info("No militia to manage.");
        }
    }

    private void manageMilitia(Unit militia) {
        Tile tile = freeMarsController.getFreeMarsModel().getTile(militia.getCoordinate());
        if (tile.getSettlement() != null && isMilitiaNeededForSettlement(tile.getSettlement())) {
            Fortify fortify = new Fortify(freeMarsController.getFreeMarsModel().getRealm());
            UnitOrderAddCommand unitOrderAddCommand = new UnitOrderAddCommand(freeMarsController.getFreeMarsModel().getRealm(), militia, fortify);
            CommandResult commandResult = freeMarsController.execute(unitOrderAddCommand);
            if (CommandResult.RESULT_OK == commandResult.getCode()) {
                logger.info(militia.getName() + " fortified in " + tile.getSettlement().getName() + ".");
            }
        } else {
            Settlement settlement = getNearestSettlementRequiringMilitia(militia.getCoordinate());
            if (settlement != null) {
                logger.info(militia.getName() + " advancing to " + settlement.getName() + ".");
                UnitAdvanceToCoordinateCommand unitAdvanceToCoordinateCommand = new UnitAdvanceToCoordinateCommand(freeMarsController.getFreeMarsModel().getRealm(), militia, settlement.getCoordinate());
                CommandResult commandResult = freeMarsController.execute(unitAdvanceToCoordinateCommand);
                if (commandResult.getCode() == CommandResult.RESULT_ERROR) {
                    logger.trace(militia.getName() + " could not advance to " + settlement.getName() + ". Switching to exploration mode.");
                    ExplorationHelper.manageUnitInExplorationMode(freeMarsController, militia);
                }
            }
        }
    }

    private List<Unit> getAvailableMilitia() {
        List<Unit> availableMilitia = new ArrayList<Unit>();
        Iterator<Unit> iterator = aiPlayer.getUnitManager().getUnitsIterator();
        while (iterator.hasNext()) {
            Unit unit = iterator.next();
            if (unit.getCoordinate() != null && unit.getType().equals(militiaUnitType)) {
                if (unit.getCurrentOrder() == null) {
                    availableMilitia.add(unit);
                }
            }
        }
        return availableMilitia;
    }

    private Settlement getNearestSettlementRequiringMilitia(Coordinate centerCoordinate) {
        for (int i = 1; i < 10; i++) {
            List<Coordinate> circleCoordinates = freeMarsController.getFreeMarsModel().getRealm().getCircleCoordinates(centerCoordinate, i);
            for (Coordinate coordinate : circleCoordinates) {
                Tile tile = freeMarsController.getFreeMarsModel().getTile(coordinate);
                if (tile.getSettlement() != null && isMilitiaNeededForSettlement(tile.getSettlement())) {
                    return tile.getSettlement();
                }
            }
        }
        return null;
    }

    private boolean isMilitiaNeededForSettlement(Settlement settlement) {
        return getFortifiedMilitiaCountInSettlement(settlement) < getRequiredMilitiaCountForSettlement(aiPlayer, settlement);
    }

    public static int getRequiredMilitiaCountForPlayer(AIPlayer aiPlayer) {
        int requiredMilitiaCountForPlayer = 0;
        Iterator<Settlement> iterator = aiPlayer.getSettlementsIterator();
        while (iterator.hasNext()) {
            Settlement settlement = iterator.next();
            requiredMilitiaCountForPlayer = requiredMilitiaCountForPlayer + getRequiredMilitiaCountForSettlement(aiPlayer, settlement);
        }
        return requiredMilitiaCountForPlayer;
    }

    private static int getRequiredMilitiaCountForSettlement(AIPlayer aiPlayer, Settlement settlement) {
        int requiredMilitiaCountForSettlement = settlement.getPopulation() / aiPlayer.getColonyPopulationForMilitiaGarrison();
        if (requiredMilitiaCountForSettlement == 0) {
            return 1;
        } else {
            return requiredMilitiaCountForSettlement;
        }
    }

    private int getFortifiedMilitiaCountInSettlement(Settlement settlement) {
        int fortifiedMilitiaCountInTile = 0;
        Iterator<Unit> units = settlement.getTile().getUnitsIterator();
        while (units.hasNext()) {
            Unit unit = units.next();
            if (unit.getType().equals(militiaUnitType)) {
                if (unit.getCurrentOrder() instanceof Fortify) {
                    fortifiedMilitiaCountInTile = fortifiedMilitiaCountInTile + 1;
                }
            }
        }
        return fortifiedMilitiaCountInTile;
    }
}
