package org.freemars.diplomacy.gift;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Deniz ARIKAN
 */
public class ResourceGiftToggleButtonActionListener implements ActionListener {

    private final OfferGiftDialogModel offerGiftDialogModel;
    private final OfferGiftDialog offerGiftDialog;

    public ResourceGiftToggleButtonActionListener(OfferGiftDialogModel offerGiftDialogModel, OfferGiftDialog offerGiftDialog) {
        this.offerGiftDialogModel = offerGiftDialogModel;
        this.offerGiftDialog = offerGiftDialog;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        offerGiftDialogModel.setGiftType(OfferGiftDialogModel.RESOURCE_GIFT_TYPE);
        offerGiftDialog.setSendGiftButtonEnabled(false);
        offerGiftDialog.clearResourceGiftTypeSelection();
        offerGiftDialog.setResourceGiftAmountSliderEnabled(false);
        offerGiftDialog.setResourceGiftAmountSliderValue(0);
        offerGiftDialog.setResourceGiftAmountSliderMaximum(0);
        offerGiftDialog.displayGiftDetailPanel(OfferGiftDialog.RESOURCE_GIFT_DETAIL_PANEL_NAME);
    }

}
