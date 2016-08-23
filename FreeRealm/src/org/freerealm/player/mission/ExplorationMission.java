package org.freerealm.player.mission;

/**
 *
 * @author Deniz ARIKAN
 */
public class ExplorationMission extends AbstractMission {

    public static final String NAME = "explorationMission";
    private int explorationTileCount;

    @Override
    public Mission clone() {
        ExplorationMission clone = new ExplorationMission();
        copyTo(clone);
        clone.setExplorationTileCount(explorationTileCount);
        return clone;
    }

    public String getMissionName() {
        return NAME;
    }

    public int getExplorationTileCount() {
        return explorationTileCount;
    }

    public void setExplorationTileCount(int explorationTileCount) {
        this.explorationTileCount = explorationTileCount;
    }

    public void checkStatus() {
        if (getPlayer().getExploredCoordinateCount() >= explorationTileCount) {
            setStatus(Mission.STATUS_COMPLETED);
        } else {
            checkIfExpired();
        }
    }

    public int getProgressPercent() {
        if (getStatus() == Mission.STATUS_COMPLETED) {
            return 100;
        } else if (getStatus() == Mission.STATUS_FAILED) {
            return 0;
        } else {
            return ((getPlayer().getExploredCoordinateCount() * 100) / getExplorationTileCount());
        }
    }

}
