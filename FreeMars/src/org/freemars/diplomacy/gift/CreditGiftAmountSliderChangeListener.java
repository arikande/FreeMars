package org.freemars.diplomacy.gift;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Deniz ARIKAN
 */
public class CreditGiftAmountSliderChangeListener implements ChangeListener {

    private final OfferGiftDialog offerGiftDialog;

    public CreditGiftAmountSliderChangeListener(OfferGiftDialog offerGiftDialog) {
        this.offerGiftDialog = offerGiftDialog;
    }

    public void stateChanged(ChangeEvent e) {
        int creditGiftAmountSliderValue = offerGiftDialog.getCreditGiftAmountSliderValue();
        offerGiftDialog.setSendGiftButtonEnabled(creditGiftAmountSliderValue > 0);
        offerGiftDialog.setCreditGiftAmountLabelText(String.valueOf(creditGiftAmountSliderValue));
    }
}
