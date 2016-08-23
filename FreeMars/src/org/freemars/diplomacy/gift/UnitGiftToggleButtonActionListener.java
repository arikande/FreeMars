package org.freemars.diplomacy.gift;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Deniz ARIKAN
 */
public class UnitGiftToggleButtonActionListener implements ActionListener {

    private final OfferGiftDialogModel offerGiftDialogModel;
    private final OfferGiftDialog offerGiftDialog;

    public UnitGiftToggleButtonActionListener(OfferGiftDialogModel offerGiftDialogModel, OfferGiftDialog offerGiftDialog) {
        this.offerGiftDialogModel = offerGiftDialogModel;
        this.offerGiftDialog = offerGiftDialog;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        offerGiftDialogModel.setGiftType(OfferGiftDialogModel.UNIT_GIFT_TYPE);
        offerGiftDialog.setSendGiftButtonEnabled(false);
        offerGiftDialog.clearGiftableUnitsSelection();
        offerGiftDialog.displayGiftDetailPanel(OfferGiftDialog.UNIT_GIFT_DETAIL_PANEL_NAME);
    }

}
