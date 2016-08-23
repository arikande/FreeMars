package org.freemars.controller;

import org.freerealm.player.mission.Mission;

/**
 *
 * @author Deniz ARIKAN
 */
public class MissionAssignment {

    private int turnToAssign;
    private int wealthToAdd;
    private Mission mission;

    public int getTurnToAssign() {
        return turnToAssign;
    }

    public void setTurnToAssign(int turnToAssign) {
        this.turnToAssign = turnToAssign;
    }

    public int getWealthToAdd() {
        return wealthToAdd;
    }

    public void setWealthToAdd(int wealthToAdd) {
        this.wealthToAdd = wealthToAdd;
    }

    public Mission getMission() {
        return mission;
    }

    public void setMission(Mission mission) {
        this.mission = mission;
    }
}
