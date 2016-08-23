package org.freemars.random.event;

import java.text.DecimalFormat;
import org.freerealm.player.DefaultMessage;

/**
 * @author Deniz ARIKAN
 */
public class CreditDonationMessage extends DefaultMessage {

    private int creditsDonated;

    @Override
    public String getText() {
        StringBuilder messageText = new StringBuilder();
        messageText.append("\"Mars is the FUTURE\" organization donates ");
        messageText.append(new DecimalFormat().format(getCreditsDonated()));
        messageText.append(" credits to our colonial government");
        return messageText.toString();
    }

    public int getCreditsDonated() {
        return creditsDonated;
    }

    public void setCreditsDonated(int creditsDonated) {
        this.creditsDonated = creditsDonated;
    }
}
