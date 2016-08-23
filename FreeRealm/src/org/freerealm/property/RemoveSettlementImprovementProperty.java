package org.freerealm.property;

/**
 *
 * @author Deniz ARIKAN
 */
public class RemoveSettlementImprovementProperty implements Property {

    public static final String NAME = "remove_settlement_improvement_property";
    private int settlementImprovementId;

    public String getName() {
        return NAME;
    }

    public int getSettlementImprovementId() {
        return settlementImprovementId;
    }

    public void setSettlementImprovementId(int settlementImprovementId) {
        this.settlementImprovementId = settlementImprovementId;
    }
}
