package org.freerealm.player.mission;

/**
 *
 * @author Deniz ARIKAN
 */
public class SettlementCountMission extends AbstractMission {

    private static final String NAME = "settlementCountMission";
    private int settlementCount;

    @Override
    public Mission clone() {
        SettlementCountMission clone = new SettlementCountMission();
        copyTo(clone);
        clone.setSettlementCount(settlementCount);
        return clone;
    }

    public String getMissionName() {
        return NAME;
    }

    public void checkStatus() {
        if (getPlayer().getSettlementCount() >= getSettlementCount()) {
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
            return (getPlayer().getSettlementCount() * 100) / getSettlementCount();
        }
    }

    public int getSettlementCount() {
        return settlementCount;
    }

    public void setSettlementCount(int settlementCount) {
        this.settlementCount = settlementCount;
    }
}
