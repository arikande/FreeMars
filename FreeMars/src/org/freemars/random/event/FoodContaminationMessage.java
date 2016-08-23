package org.freemars.random.event;

import java.text.DecimalFormat;
import org.freerealm.player.SettlementRelatedMessage;

/**
 * @author Deniz ARIKAN
 */
public class FoodContaminationMessage extends SettlementRelatedMessage {

    private int amountLost;

    @Override
    public String getText() {
        StringBuilder messageText = new StringBuilder();
        messageText.append("A contamination causes the loss of ");
        messageText.append(new DecimalFormat().format(getAmountLost()));
        messageText.append(" food in ");
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
