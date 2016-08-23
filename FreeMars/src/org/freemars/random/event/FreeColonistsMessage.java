package org.freemars.random.event;

import org.freerealm.player.SettlementRelatedMessage;

/**
 * @author Deniz ARIKAN
 */
public class FreeColonistsMessage extends SettlementRelatedMessage {

    private int newColonists;

    @Override
    public String getText() {
        StringBuilder messageText = new StringBuilder();
        messageText.append("Colonists funding their own transit have arrived in ");
        messageText.append(getSettlement().getName());
        messageText.append(" increasing population by ");
        messageText.append(newColonists);
        messageText.append(".");
        return messageText.toString();
    }

    public int getNewColonists() {
        return newColonists;
    }

    public void setNewColonists(int newColonists) {
        this.newColonists = newColonists;
    }
}
