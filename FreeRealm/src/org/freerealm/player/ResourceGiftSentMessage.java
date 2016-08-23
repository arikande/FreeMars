package org.freerealm.player;

import org.freerealm.resource.Resource;
import org.freerealm.settlement.Settlement;

/**
 *
 * @author Deniz ARIKAN
 */
public class ResourceGiftSentMessage extends DefaultMessage {

    private Settlement fromSettlement;
    private Settlement toSettlement;
    private Resource resource;
    private int amount;

    public Settlement getFromSettlement() {
        return fromSettlement;
    }

    public void setFromSettlement(Settlement fromSettlement) {
        this.fromSettlement = fromSettlement;
    }

    public Settlement getToSettlement() {
        return toSettlement;
    }

    public void setToSettlement(Settlement toSettlement) {
        this.toSettlement = toSettlement;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

}
