package org.freemars.diplomacy.gift;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Deniz ARIKAN
 */
public class ResourceGiftAmountSliderChangeListener implements ChangeListener {

    private final OfferGiftDialog offerGiftDialog;

    public ResourceGiftAmountSliderChangeListener(OfferGiftDialog offerGiftDialog) {
        this.offerGiftDialog = offerGiftDialog;
    }

    public void stateChanged(ChangeEvent e) {
        int resourceGiftAmountSliderValue = offerGiftDialog.getResourceGiftAmountSliderValue();
        offerGiftDialog.setSendGiftButtonEnabled(resourceGiftAmountSliderValue > 0);
        String labelText = String.format("%5s", resourceGiftAmountSliderValue);
        offerGiftDialog.setResourceGiftAmountLabelText(labelText);
    }
}
