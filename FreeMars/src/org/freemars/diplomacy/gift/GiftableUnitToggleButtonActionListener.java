package org.freemars.diplomacy.gift;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Deniz ARIKAN
 */
public class GiftableUnitToggleButtonActionListener implements ActionListener {

    private final OfferGiftDialogModel offerGiftDialogModel;
    private final OfferGiftDialog offerGiftDialog;

    public GiftableUnitToggleButtonActionListener(OfferGiftDialogModel offerGiftDialogModel, OfferGiftDialog offerGiftDialog) {
        this.offerGiftDialogModel = offerGiftDialogModel;
        this.offerGiftDialog = offerGiftDialog;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        offerGiftDialog.setSendGiftButtonEnabled(true);
    }

}
