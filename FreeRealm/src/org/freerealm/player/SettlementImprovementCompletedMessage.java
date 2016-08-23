package org.freerealm.player;

import org.freerealm.settlement.SettlementBuildable;
import org.freerealm.settlement.improvement.SettlementImprovementType;

/**
 *
 * @author Deniz ARIKAN
 */
public class SettlementImprovementCompletedMessage extends SettlementRelatedMessage {

    private SettlementImprovementType settlementImprovementType;
    private SettlementBuildable nextSettlementBuildable;

    public SettlementImprovementType getSettlementImprovementType() {
        return settlementImprovementType;
    }

    public void setSettlementImprovementType(SettlementImprovementType settlementImprovementType) {
        this.settlementImprovementType = settlementImprovementType;
    }

    public SettlementBuildable getNextProduction() {
        return nextSettlementBuildable;
    }

    public void setNextProduction(SettlementBuildable nextProduction) {
        this.nextSettlementBuildable = nextProduction;
    }
}
