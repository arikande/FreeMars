package org.freerealm.player.mission;

/**
 *
 * @author Deniz ARIKAN
 */
public class ClearTileVegetationCountMission extends AbstractMission {

    public static final String NAME = "clear_tile_vegetation_count_mission";
    private int clearTileVegetationCount;

    @Override
    public Mission clone() {
        ClearTileVegetationCountMission clone = new ClearTileVegetationCountMission();
        copyTo(clone);
        clone.setClearTileVegetationCount(clearTileVegetationCount);
        return clone;
    }

    public String getMissionName() {
        return NAME;
    }

    public int getClearTileVegetationCount() {
        return clearTileVegetationCount;
    }

    public void setClearTileVegetationCount(int clearTileVegetationCount) {
        this.clearTileVegetationCount = clearTileVegetationCount;
    }

    public void checkStatus() {
        if (getPlayer().getClearedVegetationCount() >= clearTileVegetationCount) {
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
            return ((getPlayer().getClearedVegetationCount() * 100) / getClearTileVegetationCount());
        }
    }

}
