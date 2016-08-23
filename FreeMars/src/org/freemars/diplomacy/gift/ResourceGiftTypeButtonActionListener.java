package org.freemars.diplomacy.gift;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JToggleButton;
import org.freemars.controller.FreeMarsController;
import org.freerealm.resource.Resource;

/**
 *
 * @author Deniz ARIKAN
 */
public class ResourceGiftTypeButtonActionListener implements ActionListener {

    private final FreeMarsController freeMarsController;
    private final OfferGiftDialogModel offerGiftDialogModel;
    private final OfferGiftDialog offerGiftDialog;

    public ResourceGiftTypeButtonActionListener(FreeMarsController freeMarsController, OfferGiftDialogModel offerGiftDialogModel, OfferGiftDialog offerGiftDialog) {
        this.freeMarsController = freeMarsController;
        this.offerGiftDialogModel = offerGiftDialogModel;
        this.offerGiftDialog = offerGiftDialog;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        JToggleButton component = (JToggleButton) actionEvent.getSource();
        offerGiftDialogModel.setResourceId(Integer.parseInt(component.getActionCommand()));
        Resource resource = freeMarsController.getFreeMarsModel().getRealm().getResourceManager().getResource(offerGiftDialogModel.getResourceId());
        int resourceQuantity = offerGiftDialogModel.getSelectedSettlement().getResourceQuantity(resource);
        offerGiftDialog.setResourceGiftAmountSliderEnabled(true);
        offerGiftDialog.setResourceGiftAmountSliderMaximum(resourceQuantity);
        offerGiftDialog.setResourceGiftAmountSliderValue(0);
        offerGiftDialog.setResourceGiftAmountSliderTickSpacing(getResourceGiftAmountSliderMajorTickSpacing(resourceQuantity));
    }

    private int getResourceGiftAmountSliderMajorTickSpacing(int resourceQuantity) {
        if (resourceQuantity < 100) {
            return 25;
        } else if (resourceQuantity < 500) {
            return 100;
        } else if (resourceQuantity < 1000) {
            return 250;
        } else if (resourceQuantity < 2000) {
            return 500;
        } else if (resourceQuantity < 4000) {
            return 1000;
        } else if (resourceQuantity < 8000) {
            return 2000;
        } else {
            return 4000;
        }
    }

}
