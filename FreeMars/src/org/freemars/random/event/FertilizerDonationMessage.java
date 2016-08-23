package org.freemars.random.event;

import java.text.DecimalFormat;
import org.freerealm.player.SettlementRelatedMessage;

/**
 * @author Deniz ARIKAN
 */
public class FertilizerDonationMessage extends SettlementRelatedMessage {

    private int fertilizerAmountDonated;

    @Override
    public String getText() {
        StringBuilder messageText = new StringBuilder();
        messageText.append("\"Mars is the FUTURE\" organization donates ");
        messageText.append(new DecimalFormat().format(getFertilizerAmountDonated()));
        messageText.append(" tons of fertilizer to ");
        messageText.append(getSettlement().getName());
        return messageText.toString();
    }

    public int getFertilizerAmountDonated() {
        return fertilizerAmountDonated;
    }

    public void setFertilizerAmountDonated(int fertilizerAmountDonated) {
        this.fertilizerAmountDonated = fertilizerAmountDonated;
    }
}
