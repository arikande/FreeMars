package org.freemars.random.event;

import org.freerealm.player.SettlementRelatedMessage;

/**
 * @author Deniz ARIKAN
 */
public class HydrogenExplosionMessage extends SettlementRelatedMessage {

    private int colonistsLost;

    @Override
    public String getText() {
        StringBuilder messageText = new StringBuilder();
        messageText.append("A hydrogen explosion in ");
        messageText.append(getSettlement().getName());
        messageText.append(" causes the loss of ");
        messageText.append(colonistsLost);
        messageText.append(" colonists");
        return messageText.toString();
    }

    public int getColonistsLost() {
        return colonistsLost;
    }

    public void setColonistsLost(int colonistsLost) {
        this.colonistsLost = colonistsLost;
    }
}
