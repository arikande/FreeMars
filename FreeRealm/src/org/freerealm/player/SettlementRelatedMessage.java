package org.freerealm.player;

import org.freerealm.settlement.Settlement;

/**
 * @author Deniz ARIKAN
 */
public class SettlementRelatedMessage extends DefaultMessage {

    private Settlement settlement;

    public Settlement getSettlement() {
        return settlement;
    }

    public void setSettlement(Settlement settlement) {
        this.settlement = settlement;
    }
}
