package org.freerealm.player;

import org.freerealm.resource.Resource;
import org.freerealm.settlement.improvement.SettlementImprovement;

/**
 * @author Deniz ARIKAN
 */
public class NotEnoughInputResourceForSettlementImprovementMessage extends SettlementRelatedMessage {

    private SettlementImprovement settlementImprovement;
    private Resource resource;

    public SettlementImprovement getSettlementImprovement() {
        return settlementImprovement;
    }

    public void setSettlementImprovement(SettlementImprovement settlementImprovement) {
        this.settlementImprovement = settlementImprovement;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }
}
