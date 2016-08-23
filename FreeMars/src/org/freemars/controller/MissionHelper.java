package org.freemars.controller;

import java.util.ArrayList;

import org.freemars.mission.ExportResourceMission;
import org.freemars.player.FreeMarsPlayer;
import org.freerealm.command.AssignMissionToPlayerCommand;
import org.freerealm.command.WealthAddCommand;
import org.freerealm.player.mission.Mission;

/**
 *
 * @author Deniz ARIKAN
 */
public class MissionHelper {

    private final ArrayList<MissionAssignment> missionAssignments;

    public MissionHelper() {
        missionAssignments = new ArrayList<MissionAssignment>();
    }

    public void clearMissions() {
        missionAssignments.clear();
    }

    public void addMissionAssignment(MissionAssignment missionAssignment) {
        missionAssignments.add(missionAssignment);
    }

    public void assignMissions(FreeMarsController freeMarsController, FreeMarsPlayer freeMarsPlayer) {
        if (!freeMarsPlayer.hasDeclaredIndependence()) {
            for (MissionAssignment missionAssignment : missionAssignments) {
                if (missionAssignment.getTurnToAssign() == freeMarsController.getFreeMarsModel().getNumberOfTurns()) {
                    Mission templateMission = missionAssignment.getMission();
                    Mission missionToAssign = templateMission.clone();
                    if (!freeMarsPlayer.isMissionAssigned(missionToAssign.getId())) {
                        missionToAssign.setTurnIssued(freeMarsController.getFreeMarsModel().getNumberOfTurns());
                        missionToAssign.setRealm(freeMarsController.getFreeMarsModel().getRealm());
                        missionToAssign.setStatus(Mission.STATUS_ACTIVE);
                        if (missionToAssign instanceof ExportResourceMission) {
                            ExportResourceMission exportResourceMission = (ExportResourceMission) missionToAssign;
                            exportResourceMission.setStartExportedQuantity(freeMarsPlayer.getResourceTradeData(exportResourceMission.getResourceId()).getQuantityExported());
                        }
                        freeMarsController.execute(new AssignMissionToPlayerCommand(freeMarsPlayer, missionToAssign));
                        freeMarsController.execute(new WealthAddCommand(freeMarsPlayer, missionAssignment.getWealthToAdd()));
                    }
                }
            }
        }
    }
}
