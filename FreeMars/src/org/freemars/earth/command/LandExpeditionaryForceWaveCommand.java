package org.freemars.earth.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;
import org.freemars.ai.ExpeditionaryForcePlayer;
import org.freemars.controller.FreeMarsController;
import org.freemars.model.FreeMarsModel;
import org.freerealm.command.AbstractCommand;
import org.freerealm.command.AddExploredCoordinatesToPlayerCommand;
import org.freerealm.command.UnitActivateCommand;
import org.freerealm.executor.CommandResult;
import org.freerealm.map.Coordinate;
import org.freerealm.player.Player;
import org.freerealm.settlement.Settlement;
import org.freerealm.tile.Tile;
import org.freerealm.unit.Unit;
import org.freerealm.unit.UnitType;

/**
 *
 * @author Deniz ARIKAN
 */
public class LandExpeditionaryForceWaveCommand extends AbstractCommand {

    private static final Logger logger = Logger.getLogger(LandExpeditionaryForceWaveCommand.class);
    private final FreeMarsController freeMarsController;
    private final ExpeditionaryForcePlayer expeditionaryForcePlayer;
    private List<Coordinate> landableRegionCoordinates;
    private final Random randomGenerator = new Random();

    public LandExpeditionaryForceWaveCommand(FreeMarsController freeMarsController, ExpeditionaryForcePlayer expeditionaryForcePlayer) {
        this.freeMarsController = freeMarsController;
        this.expeditionaryForcePlayer = expeditionaryForcePlayer;
    }

    public CommandResult execute() {
        Player targetPlayer = freeMarsController.getFreeMarsModel().getRealm().getPlayerManager().getPlayer(expeditionaryForcePlayer.getTargetPlayerId());
        StringBuilder log = new StringBuilder();
        log.append("Units of \"");
        log.append(expeditionaryForcePlayer.getName());
        log.append("\" are landing to attack \"");
        log.append(targetPlayer.getName());
        log.append("\".");
        logger.trace(log);
        addExploredTilesToExpeditionaryForcePlayer(expeditionaryForcePlayer, targetPlayer);
        landableRegionCoordinates = getLandableRegionCoordinates(freeMarsController.getFreeMarsModel(), expeditionaryForcePlayer, targetPlayer);
        List<Unit> unlandedUnits = new ArrayList<Unit>();
        Iterator<Unit> iterator = expeditionaryForcePlayer.getUnitsIterator();
        while (iterator.hasNext()) {
            Unit unit = iterator.next();
            if (unit.getStatus() == Unit.UNIT_SUSPENDED) {
                unlandedUnits.add(unit);
            }
        }
        Map<UnitType, Integer> landedUnits = new HashMap<UnitType, Integer>();
        int i = 0;
        for (Unit unlandedUnit : unlandedUnits) {
            if (i % expeditionaryForcePlayer.getRemainingAttackWaves() == 0) {
                Coordinate landingCoordinate = findLandingCoordinate(targetPlayer);
                landUnit(unlandedUnit, landingCoordinate);
                if (landedUnits.containsKey(unlandedUnit.getType())) {
                    landedUnits.put(unlandedUnit.getType(), landedUnits.get(unlandedUnit.getType()) + 1);
                } else {
                    landedUnits.put(unlandedUnit.getType(), 1);
                }
            }
            i++;
        }
        expeditionaryForcePlayer.setRemainingAttackWaves(expeditionaryForcePlayer.getRemainingAttackWaves() - 1);
        int attackWave = expeditionaryForcePlayer.getMaximumAttackWaves() - expeditionaryForcePlayer.getRemainingAttackWaves();
        CommandResult commandResult = new CommandResult(CommandResult.RESULT_OK, "", FreeMarsModel.EXPEDITIONARY_FORCE_LANDED_UPDATE);
        commandResult.putParameter("targetPlayer", targetPlayer);
        commandResult.putParameter("attackWave", attackWave);
        commandResult.putParameter("landedUnits", landedUnits);
        return commandResult;
    }

    private void addExploredTilesToExpeditionaryForcePlayer(Player expeditionaryForcePlayer, Player targetPlayer) {
        Iterator<Settlement> iterator = targetPlayer.getSettlementsIterator();
        while (iterator.hasNext()) {
            Settlement settlement = iterator.next();
            expeditionaryForcePlayer.addExploredCoordinate(settlement.getCoordinate());
            for (int i = 1; i < 6; i++) {
                List<Coordinate> circleCoordinates = freeMarsController.getFreeMarsModel().getRealm().getCircleCoordinates(settlement.getCoordinate(), i);
                getExecutor().equals(new AddExploredCoordinatesToPlayerCommand(expeditionaryForcePlayer, circleCoordinates));
            }
        }
    }

    private List<Coordinate> getLandableRegionCoordinates(FreeMarsModel freeMarsModel, Player landingPlayer, Player targetPlayer) {
        List<Coordinate> coordinates = new ArrayList<Coordinate>();
        Iterator<Settlement> iterator = targetPlayer.getSettlementsIterator();
        while (iterator.hasNext()) {
            Settlement settlement = iterator.next();
            for (int i = 2; i < 6; i++) {
                List<Coordinate> circleCoordinates = freeMarsController.getFreeMarsModel().getRealm().getCircleCoordinates(settlement.getCoordinate(), i);
                for (Coordinate coordinate : circleCoordinates) {
                    if (!coordinates.contains(coordinate)) {
                        coordinates.add(coordinate);
                    }
                }
            }
        }
        iterator = targetPlayer.getSettlementsIterator();
        while (iterator.hasNext()) {
            Settlement settlement = iterator.next();
            for (int i = 0; i < 3; i++) {
                List<Coordinate> circleCoordinates = freeMarsController.getFreeMarsModel().getRealm().getCircleCoordinates(settlement.getCoordinate(), i);
                coordinates.removeAll(circleCoordinates);
            }
        }
        List<Coordinate> landableCoordinates = new ArrayList<Coordinate>();
        for (int i = 0; i < coordinates.size(); i++) {
            Coordinate coordinate = coordinates.get(i);
            if (isValidLandingCoordinateForPlayer(freeMarsModel, landingPlayer, coordinate) && !landableCoordinates.contains(coordinate)) {
                landableCoordinates.add(coordinate);
            }
        }
        return landableCoordinates;
    }

    private void landUnit(Unit unit, Coordinate landingCoordinate) {
        freeMarsController.execute(new UnitActivateCommand(freeMarsController.getFreeMarsModel().getRealm(), unit, landingCoordinate));
    }

    private boolean isValidLandingCoordinateForPlayer(FreeMarsModel freeMarsModel, Player player, Coordinate coordinate) {
        if (!freeMarsModel.getRealm().isValidCoordinate(coordinate)) {
            return false;
        }
        Tile tile = freeMarsModel.getRealm().getTile(coordinate);
        if (tile == null) {
            return false;
        }
        if (tile.getSettlement() != null) {
            return false;
        }
        if (tile.getOccupiedByPlayer() != null && !tile.getOccupiedByPlayer().equals(player)) {
            return false;
        }
        if (tile.getUnits().size() >= 4) {
            return false;
        }
        if (!tile.getType().getName().equals("Plains") && !tile.getType().getName().equals("Wasteland") && !tile.getType().getName().equals("Desert")) {
            return false;
        }
        return true;
    }

    private Coordinate findLandingCoordinate(Player targetPlayer) {
        int landableCoordinatesSize = landableRegionCoordinates.size();
        int randomIndex = randomGenerator.nextInt(landableCoordinatesSize);
        return landableRegionCoordinates.get(randomIndex);
    }
}
