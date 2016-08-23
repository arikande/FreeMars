package org.freemars.random.event;

import java.text.DecimalFormat;
import org.freerealm.player.SettlementRelatedMessage;

/**
 * @author Deniz ARIKAN
 */
public class WaterLeakMessage extends SettlementRelatedMessage {

    private int amountLost;

    @Override
    public String getText() {
        StringBuilder messageText = new StringBuilder();
        messageText.append("A leak causes the loss of ");
        messageText.append(new DecimalFormat().format(getAmountLost()));
        messageText.append(" water in ");
        messageText.append(getSettlement().getName());
        return messageText.toString();
    }

    public int getAmountLost() {
        return amountLost;
    }

    public void setAmountLost(int amountLost) {
        this.amountLost = amountLost;
    }
}
