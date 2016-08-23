package org.freemars.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.freemars.controller.handler.PostCommandHandler;
import org.freemars.mission.CollectDebrisMission;
import org.freemars.mission.DisplayClearTileVegetationMissionAssignedAction;
import org.freemars.mission.DisplayCollectDebrisMissionAssignedAction;
import org.freemars.mission.DisplayExplorationMissionAssignedAction;
import org.freemars.mission.DisplayExportResourceMissionAssignedAction;
import org.freemars.mission.DisplayPopulationMissionAssignedAction;
import org.freemars.mission.DisplaySettlementCountMissionAssignedAction;
import org.freemars.mission.DisplaySettlementImprovementCountMissionAssignedAction;
import org.freemars.mission.DisplayTileImprovementCountMissionAssignedAction;
import org.freemars.mission.ExportResourceMission;
import org.freerealm.Realm;
import org.freerealm.executor.CommandResult;
import org.freerealm.map.Coordinate;
import org.freerealm.player.Player;
import org.freerealm.player.mission.ClearTileVegetationCountMission;
import org.freerealm.player.mission.ExplorationMission;
import org.freerealm.player.mission.Mission;
import org.freerealm.player.mission.PopulationMission;
import org.freerealm.player.mission.SettlementCountMission;
import org.freerealm.player.mission.SettlementImprovementCountMission;
import org.freerealm.player.mission.TileImprovementCountMission;
import org.freerealm.settlement.Settlement;
import org.freerealm.tile.Tile;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class MissionAssignedHandler implements PostCommandHandler {

    public void handleUpdate(FreeMarsController controller, CommandResult commandResult) {
        Mission mission = (Mission) commandResult.getParameter("mission");
        if (mission.getPlayer().equals(controller.getFreeMarsModel().getHumanPlayer())) {
            if (mission instanceof CollectDebrisMission) {
                ((CollectDebrisMission) mission).addDebrisCoordinates(searchForDebris(controller.getFreeMarsModel().getRealm(), mission.getPlayer()));
                new DisplayCollectDebrisMissionAssignedAction(controller, (CollectDebrisMission) mission).actionPerformed(null);
            }
            if (mission instanceof ExplorationMission) {
                new DisplayExplorationMissionAssignedAction(controller, (ExplorationMission) mission).actionPerformed(null);
            }
            if (mission instanceof ClearTileVegetationCountMission) {
                new DisplayClearTileVegetationMissionAssignedAction(controller, (ClearTileVegetationCountMission) mission).actionPerformed(null);
            }
            if (mission instanceof PopulationMission) {
                new DisplayPopulationMissionAssignedAction(controller, (PopulationMission) mission).actionPerformed(null);
            }
            if (mission instanceof ExportResourceMission) {
                new DisplayExportResourceMissionAssignedAction(controller, (ExportResourceMission) mission).actionPerformed(null);
            }
            if (mission instanceof SettlementCountMission) {
                new DisplaySettlementCountMissionAssignedAction(controller, (SettlementCountMission) mission).actionPerformed(null);
            }
            if (mission instanceof TileImprovementCountMission) {
                new DisplayTileImprovementCountMissionAssignedAction(controller, (TileImprovementCountMission) mission).actionPerformed(null);
            }
            if (mission instanceof SettlementImprovementCountMission) {
                new DisplaySettlementImprovementCountMissionAssignedAction(controller, (SettlementImprovementCountMission) mission).actionPerformed(null);
            }
        }
    }

    private ArrayList<Coordinate> searchForDebris(Realm realm, Player player) {
        ArrayList<Coordinate> possibleCoordinates = new ArrayList<Coordinate>();
        Iterator<Unit> unitsIterator = player.getUnitsIterator();
        while (unitsIterator.hasNext()) {
            Unit unit = unitsIterator.next();
            if (unit.getCoordinate() != null) {
                if (!possibleCoordinates.contains(unit.getCoordinate())) {
                    possibleCoordinates.add(unit.getCoordinate());
                }
                for (int i = 1; i < 7; i++) {
                    List<Coordinate> circleCoordinates = realm.getCircleCoordinates(unit.getCoordinate(), i);
                    for (Coordinate coordinate : circleCoordinates) {
                        if (!possibleCoordinates.contains(coordinate)) {
                            possibleCoordinates.add(coordinate);
                        }
                    }
                }
            }
        }
        Iterator<Settlement> settlementsIterator = player.getSettlementsIterator();
        while (settlementsIterator.hasNext()) {
            Settlement settlement = settlementsIterator.next();
            if (!possibleCoordinates.contains(settlement.getCoordinate())) {
                possibleCoordinates.add(settlement.getCoordinate());
            }
            for (int i = 1; i < 7; i++) {
                List<Coordinate> circleCoordinates = realm.getCircleCoordinates(settlement.getCoordinate(), i);
                for (Coordinate coordinate : circleCoordinates) {
                    if (!possibleCoordinates.contains(coordinate)) {
                        possibleCoordinates.add(coordinate);
                    }
                }
            }
        }
        ArrayList<Coordinate> debrisCoordinates = new ArrayList<Coordinate>();
        for (Coordinate coordinate : possibleCoordinates) {
            Tile tile = realm.getTile(coordinate);
            if (tile != null && tile.getCollectable() != null) {
                debrisCoordinates.add(coordinate);
            }
        }
        return debrisCoordinates;
    }
}
