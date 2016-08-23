package org.freemars.message;

import org.freerealm.player.DefaultMessage;

/**
 *
 * @author Deniz ARIKAN
 */
public class EarthTaxRateChangedMessage extends DefaultMessage {

    private byte previousTaxRate;
    private byte newTaxRate;

    public byte getPreviousTaxRate() {
        return previousTaxRate;
    }

    public void setPreviousTaxRate(byte previousTaxRate) {
        this.previousTaxRate = previousTaxRate;
    }

    public byte getNewTaxRate() {
        return newTaxRate;
    }

    public void setNewTaxRate(byte newTaxRate) {
        this.newTaxRate = newTaxRate;
    }
}
