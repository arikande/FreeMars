package org.freemars.mission;

import org.freemars.player.FreeMarsPlayer;
import org.freerealm.player.mission.AbstractMission;
import org.freerealm.player.mission.Mission;

/**
 *
 * @author Deniz ARIKAN
 */
public class ExportResourceMission extends AbstractMission {

    private static final String NAME = "exportResourceMission";
    private int resourceId;
    private int startExportedQuantity;
    private int targetQuantity;

    @Override
    public Mission clone() {
        ExportResourceMission clone = new ExportResourceMission();
        copyTo(clone);
        clone.setResourceId(resourceId);
        clone.setStartExportedQuantity(startExportedQuantity);
        clone.setTargetQuantity(targetQuantity);
        return clone;
    }

    public String getMissionName() {
        return NAME;
    }

    public void checkStatus() {
        int currentQuantity = ((FreeMarsPlayer) getPlayer()).getResourceTradeData(resourceId).getQuantityExported();
        if ((currentQuantity - startExportedQuantity) >= targetQuantity) {
            setStatus(AbstractMission.STATUS_COMPLETED);
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
            int currentQuantity = ((FreeMarsPlayer) getPlayer()).getResourceTradeData(getResourceId()).getQuantityExported();
            int exportedQuantity = currentQuantity - getStartExportedQuantity();
            return (exportedQuantity * 100) / getTargetQuantity();
        }
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public int getStartExportedQuantity() {
        return startExportedQuantity;
    }

    public void setStartExportedQuantity(int startExportedQuantity) {
        this.startExportedQuantity = startExportedQuantity;
    }

    public int getTargetQuantity() {
        return targetQuantity;
    }

    public void setTargetQuantity(int targetQuantity) {
        this.targetQuantity = targetQuantity;
    }
}
