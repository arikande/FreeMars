package org.freemars.mission;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import org.freemars.model.FreeMarsModel;
import org.freemars.ui.util.TurnToDateConverter;
import org.freerealm.map.Coordinate;
import org.freerealm.player.Player;
import org.freerealm.player.mission.ClearTileVegetationCountMission;
import org.freerealm.player.mission.ExplorationMission;
import org.freerealm.player.mission.Mission;
import org.freerealm.player.mission.PopulationMission;
import org.freerealm.player.mission.Reward;
import org.freerealm.player.mission.SettlementCountMission;
import org.freerealm.player.mission.SettlementImprovementCountMission;
import org.freerealm.player.mission.TileImprovementCountMission;
import org.freerealm.resource.Resource;
import org.freerealm.tile.Tile;
import org.freerealm.tile.improvement.TileImprovementType;

/**
 *
 * @author Deniz ARIKAN
 */
public class MissionsTableModel extends AbstractTableModel {

    private final FreeMarsModel freeMarsModel;
    private final Player player;
    private final List<Mission> missions;
    private final String[] columnNames = {"Mission", "Status", "Progress", "Date issued", "Duration", "Remaining", "Expires", "Reward"};

    public MissionsTableModel(FreeMarsModel freeMarsModel, Player player) {
        this.freeMarsModel = freeMarsModel;
        this.player = player;
        missions = new ArrayList<Mission>();
        Iterator<Mission> iterator = player.getMissionsIterator();
        while (iterator.hasNext()) {
            Mission mission = iterator.next();
            missions.add(mission);
        }
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    public int getRowCount() {
        return missions.size();
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        Mission mission = missions.get(rowIndex);
        if (columnIndex == 0) {
            return getMissionExplanation(mission);
        } else if (columnIndex == 1) {
            if (mission.getStatus() == Mission.STATUS_ACTIVE) {
                return "Active";
            } else if (mission.getStatus() == Mission.STATUS_COMPLETED) {
                return "Completed";
            } else if (mission.getStatus() == Mission.STATUS_FAILED) {
                return "Failed";
            } else if (mission.getStatus() == Mission.STATUS_CANCELED) {
                return "Canceled";
            } else {
                return "Active";
            }
        } else if (columnIndex == 2) {
            if (mission.getStatus() == Mission.STATUS_ACTIVE) {
                return mission.getProgressPercent() + " %";
            } else {
                return "-";
            }
        } else if (columnIndex == 3) {
            return new TurnToDateConverter(mission.getTurnIssued()).gateDateString();
        } else if (columnIndex == 4) {
            if (mission.getDuration() == -1) {
                return "N/A";
            } else {
                return mission.getDuration() + " months";
            }
        } else if (columnIndex == 5) {
            if (mission.getStatus() == Mission.STATUS_ACTIVE) {
                if (mission.getDuration() == -1) {
                    return "N/A";
                } else {
                    int turnsRemaining = mission.getTurnIssued() + mission.getDuration() - freeMarsModel.getNumberOfTurns();
                    return turnsRemaining + " months";
                }
            } else {
                return "-";
            }
        } else if (columnIndex == 6) {
            if (mission.getDuration() == -1) {
                return "N/A";
            } else {
                int totalTurns = mission.getTurnIssued() + mission.getDuration();
                return new TurnToDateConverter(totalTurns).gateDateString();
            }
        } else if (columnIndex == 7) {
            if (mission.getRewardCount() > 0) {
                Iterator<Reward> iterator = mission.getRewardsIterator();
                while (iterator.hasNext()) {
                    Reward reward = iterator.next();
                    if (reward instanceof WealthReward) {
                        WealthReward wealthReward = (WealthReward) reward;
                        return new DecimalFormat().format(wealthReward.getAmount()) + " credits";
                    }
                }
            } else {
                return "N/A";
            }
            return "";
        } else {
            return "";
        }
    }

    public int getMissionStatus(int index) {
        return missions.get(index).getStatus();
    }

    private String getMissionExplanation(Mission mission) {
        String missionExplanation = "";
        if (mission instanceof ExplorationMission) {
            ExplorationMission explorationMission = (ExplorationMission) mission;
            int mapExplorationPercent = (explorationMission.getExplorationTileCount() * 100) / (freeMarsModel.getRealm().getMapHeight() * freeMarsModel.getRealm().getMapWidth());
            missionExplanation = "Explore " + mapExplorationPercent + "% of Martian surface";
        } else if (mission instanceof ClearTileVegetationCountMission) {
            ClearTileVegetationCountMission clearTileVegetationCountMission = (ClearTileVegetationCountMission) mission;
            missionExplanation = "Clear vegetation of " + clearTileVegetationCountMission.getClearTileVegetationCount() + " tiles";
        } else if (mission instanceof PopulationMission) {
            PopulationMission populationMission = (PopulationMission) mission;
            missionExplanation = "Reach a population of " + populationMission.getPopulation() + " colonists";
        } else if (mission instanceof ExportResourceMission) {
            ExportResourceMission exportResourceMission = (ExportResourceMission) mission;
            Resource resource = freeMarsModel.getRealm().getResourceManager().getResource(exportResourceMission.getResourceId());
            missionExplanation = "Export " + exportResourceMission.getTargetQuantity() + " tons of " + resource.getName() + " to Earth";
        } else if (mission instanceof SettlementCountMission) {
            SettlementCountMission settlementCountMission = (SettlementCountMission) mission;
            missionExplanation = "Have at least " + settlementCountMission.getSettlementCount() + " colonies on Mars";
        } else if (mission instanceof TileImprovementCountMission) {
            TileImprovementCountMission tileImprovementCountMission = (TileImprovementCountMission) mission;
            TileImprovementType tileImprovementType = freeMarsModel.getRealm().getTileImprovementTypeManager().getImprovement(tileImprovementCountMission.getTileImprovementId());
            missionExplanation = "Build at least " + tileImprovementCountMission.getTileImprovementCount() + " " + tileImprovementType.getName().toLowerCase() + "s on Mars";
        } else if (mission instanceof SettlementImprovementCountMission) {
            missionExplanation = "Build ";
            SettlementImprovementCountMission settlementImprovementCountMission = (SettlementImprovementCountMission) mission;
            int i = 0;
            Iterator<Integer> targetImprovementTypesIterator = settlementImprovementCountMission.getTargetImprovementTypesIterator();
            while (targetImprovementTypesIterator.hasNext()) {
                i++;
                int targetImprovementType = targetImprovementTypesIterator.next();
                int count = settlementImprovementCountMission.getTargetCountForImprovementType(targetImprovementType);
                String settlementImprovementTypeName = freeMarsModel.getRealm().getSettlementImprovementManager().getImprovement(targetImprovementType).getName();
                missionExplanation = missionExplanation + count + " x " + settlementImprovementTypeName.toLowerCase();
                if (i < settlementImprovementCountMission.getTargetImprovementTypeCount()) {
                    missionExplanation = missionExplanation + ", ";
                }
            }
        } else if (mission instanceof CollectDebrisMission) {
            CollectDebrisMission collectDebrisMission = (CollectDebrisMission) mission;
            String coordinates = "";
            int debrisTileCount = 0;
            Iterator<Coordinate> iterator = collectDebrisMission.getDebrisCoordinatesIterator();
            while (iterator.hasNext()) {
                Coordinate coordinate = iterator.next();
                Tile tile = freeMarsModel.getTile(coordinate);
                if (tile.getCollectable() != null) {
                    coordinates = coordinates + coordinate + "  ";
                    debrisTileCount = debrisTileCount + 1;
                }
            }
            if (debrisTileCount > 0) {
                missionExplanation = "Collect debris from " + coordinates;
            } else {
                missionExplanation = "All debris collected";
            }
        }
        return missionExplanation;
    }
}
