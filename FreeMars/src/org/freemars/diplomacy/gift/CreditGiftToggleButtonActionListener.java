package org.freemars.diplomacy.gift;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Deniz ARIKAN
 */
public class CreditGiftToggleButtonActionListener implements ActionListener {

    private final OfferGiftDialogModel offerGiftDialogModel;
    private final OfferGiftDialog offerGiftDialog;

    public CreditGiftToggleButtonActionListener(OfferGiftDialogModel offerGiftDialogModel, OfferGiftDialog offerGiftDialog) {
        this.offerGiftDialogModel = offerGiftDialogModel;
        this.offerGiftDialog = offerGiftDialog;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        offerGiftDialogModel.setGiftType(OfferGiftDialogModel.CREDIT_GIFT_TYPE);
        offerGiftDialog.setCreditGiftAmountSliderValue(0);
        offerGiftDialog.setSendGiftButtonEnabled(false);
        offerGiftDialog.displayGiftDetailPanel(OfferGiftDialog.CREDIT_GIFT_DETAIL_PANEL_NAME);
    }

}
