package org.freemars.random.event;

import org.freerealm.player.SettlementRelatedMessage;

/**
 * @author Deniz ARIKAN
 */
public class ColonistsReturningToEarthMessage extends SettlementRelatedMessage {

    private int leavingColonists;

    @Override
    public String getText() {
        StringBuilder messageText = new StringBuilder();
        messageText.append("Colonists losing hope in Mars colonization have left ");
        messageText.append(getSettlement().getName());
        messageText.append(" decreasing population by ");
        messageText.append(leavingColonists);
        messageText.append(".");
        return messageText.toString();
    }

    public int getLeavingColonists() {
        return leavingColonists;
    }

    public void setLeavingColonists(int leavingColonists) {
        this.leavingColonists = leavingColonists;
    }
}
