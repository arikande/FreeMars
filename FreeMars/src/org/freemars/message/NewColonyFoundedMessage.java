package org.freemars.message;

import org.freerealm.player.SettlementRelatedMessage;

/**
 *
 * @author Deniz ARIKAN
 */
public class NewColonyFoundedMessage extends SettlementRelatedMessage {

    @Override
    public String getText() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Colony of " + getSettlement().getName() + " founded.");
        return stringBuilder.toString();
    }
}
