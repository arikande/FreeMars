package org.freerealm.player.mission;

/**
 *
 * @author Deniz ARIKAN
 */
public class TileImprovementCountMission extends AbstractMission {

    public static final String NAME = "tileImprovementCountMission";
    private int tileImprovementId;
    private int tileImprovementCount;

    @Override
    public Mission clone() {
        TileImprovementCountMission clone = new TileImprovementCountMission();
        copyTo(clone);
        clone.setTileImprovementId(tileImprovementId);
        clone.setTileImprovementCount(tileImprovementCount);
        return clone;
    }

    public String getMissionName() {
        return NAME;
    }

    public void checkStatus() {
        if (getPlayer().getBuiltTileImprovementCount(getTileImprovementId()) >= getTileImprovementCount()) {
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
            return (getPlayer().getBuiltTileImprovementCount(getTileImprovementId()) * 100) / getTileImprovementCount();
        }
    }

    public int getTileImprovementId() {
        return tileImprovementId;
    }

    public void setTileImprovementId(int tileImprovementId) {
        this.tileImprovementId = tileImprovementId;
    }

    public int getTileImprovementCount() {
        return tileImprovementCount;
    }

    public void setTileImprovementCount(int tileImprovementCount) {
        this.tileImprovementCount = tileImprovementCount;
    }
}
