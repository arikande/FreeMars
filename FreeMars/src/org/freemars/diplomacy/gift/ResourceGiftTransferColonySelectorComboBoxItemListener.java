package org.freemars.diplomacy.gift;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Iterator;
import javax.swing.JComboBox;
import org.freemars.controller.FreeMarsController;
import org.freerealm.resource.Resource;
import org.freerealm.settlement.Settlement;

/**
 *
 * @author Deniz ARIKAN
 */
public class ResourceGiftTransferColonySelectorComboBoxItemListener implements ItemListener {

    private final FreeMarsController freeMarsController;
    private final OfferGiftDialog offerGiftDialog;
    private final OfferGiftDialogModel offerGiftDialogModel;

    public ResourceGiftTransferColonySelectorComboBoxItemListener(FreeMarsController freeMarsController, OfferGiftDialog offerGiftDialog, OfferGiftDialogModel offerGiftDialogModel) {
        this.freeMarsController = freeMarsController;
        this.offerGiftDialog = offerGiftDialog;
        this.offerGiftDialogModel = offerGiftDialogModel;
    }

    public void itemStateChanged(ItemEvent itemEvent) {
        if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
            offerGiftDialog.clearResourceGiftTypeSelection();
            offerGiftDialog.setResourceGiftAmountSliderEnabled(false);
            offerGiftDialog.setResourceGiftAmountSliderValue(0);
            offerGiftDialog.setResourceGiftAmountSliderMaximum(0);
            JComboBox comboBox = (JComboBox) itemEvent.getSource();
            offerGiftDialogModel.setSelectedSettlementIndex(comboBox.getSelectedIndex());
            Settlement settlement = offerGiftDialogModel.getSelectedSettlement();
            int resourceIndex = 0;
            Iterator<Resource> resourcesIterator = freeMarsController.getFreeMarsModel().getRealm().getResourceManager().getResourcesIterator();
            while (resourcesIterator.hasNext()) {
                Resource resource = resourcesIterator.next();
                int resourceQuantity = settlement.getResourceQuantity(resource);
                offerGiftDialog.setResourceGiftTypeButtonEnabled(resourceIndex, resourceQuantity > 0);
                String resourceString = resource.getName();
                if (resourceQuantity > 0) {
                    resourceString = resourceString + " - " + resourceQuantity;
                }
                offerGiftDialog.setResourceGiftTypeButtonText(resourceIndex, resourceString);
                resourceIndex = resourceIndex + 1;
            }
        }
    }

}
