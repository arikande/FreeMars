package org.freemars.diplomacy.gift;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JToggleButton;
import org.freemars.controller.FreeMarsController;

/**
 *
 * @author Deniz ARIKAN
 */
public class GiftableUnitButtonActionListener implements ActionListener {

    private final FreeMarsController freeMarsController;
    private final OfferGiftDialogModel offerGiftDialogModel;
    private final OfferGiftDialog offerGiftDialog;

    public GiftableUnitButtonActionListener(FreeMarsController freeMarsController, OfferGiftDialogModel offerGiftDialogModel, OfferGiftDialog offerGiftDialog) {
        this.freeMarsController = freeMarsController;
        this.offerGiftDialogModel = offerGiftDialogModel;
        this.offerGiftDialog = offerGiftDialog;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        JToggleButton component = (JToggleButton) actionEvent.getSource();
        int unitIndex = Integer.parseInt(component.getActionCommand());
        offerGiftDialogModel.setSelectedUnitIndex(unitIndex);
        offerGiftDialog.setSendGiftButtonEnabled(true);
    }

}
