package org.freemars.diplomacy.gift;

import java.awt.Image;
import java.util.Iterator;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import org.freemars.colonydialog.controller.CloseDialogAction;
import org.freemars.controller.FreeMarsController;
import org.freemars.player.FreeMarsPlayer;
import org.freemars.ui.image.FreeMarsImageManager;
import org.freerealm.map.Coordinate;
import org.freerealm.resource.Resource;
import org.freerealm.settlement.Settlement;
import org.freerealm.tile.Tile;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class OfferGiftDialogController {
    
    private final OfferGiftDialogModel offerGiftDialogModel;
    private final OfferGiftDialog offerGiftDialog;
    private final FreeMarsController freeMarsController;
    
    private ResourceGiftTypeButtonActionListener resourceGiftTypeButtonActionListener;
    private GiftableUnitButtonActionListener giftableUnitButtonActionListener;
    
    public OfferGiftDialogController(FreeMarsController freeMarsController, FreeMarsPlayer fromPlayer, FreeMarsPlayer toPlayer) {
        this.freeMarsController = freeMarsController;
        offerGiftDialogModel = new OfferGiftDialogModel(fromPlayer, toPlayer);
        offerGiftDialog = new OfferGiftDialog(freeMarsController.getCurrentFrame());
    }
    
    public void displayOfferGiftDialog() {
        offerGiftDialog.addCreditGiftToggleButtonActionListener(new CreditGiftToggleButtonActionListener(offerGiftDialogModel, offerGiftDialog));
        offerGiftDialog.addResourceGiftToggleButtonActionListener(new ResourceGiftToggleButtonActionListener(offerGiftDialogModel, offerGiftDialog));
        offerGiftDialog.addUnitGiftToggleButtonActionListener(new UnitGiftToggleButtonActionListener(offerGiftDialogModel, offerGiftDialog));
        
        offerGiftDialog.setCreditGiftAmountSliderMaximum(offerGiftDialogModel.getFromPlayer().getWealth());
        offerGiftDialog.setCreditGiftAmountSliderMajorTickSpacing(getCreditGiftAmountSliderMajorTickSpacing(offerGiftDialogModel.getFromPlayer().getWealth()));
        offerGiftDialog.addCreditAmountSliderChangeListener(new CreditGiftAmountSliderChangeListener(offerGiftDialog));
        offerGiftDialog.setSendGiftButtonAction(new SendGiftAction(freeMarsController, offerGiftDialog, offerGiftDialogModel));
        offerGiftDialog.setCancelButtonAction(new CloseDialogAction(offerGiftDialog));
        offerGiftDialog.addResourceGiftAmountSliderChangeListener(new ResourceGiftAmountSliderChangeListener(offerGiftDialog));
        Iterator<Resource> resourcesIterator = freeMarsController.getFreeMarsModel().getRealm().getResourceManager().getResourcesIterator();
        while (resourcesIterator.hasNext()) {
            Resource resource = resourcesIterator.next();
            Image resourceImage = FreeMarsImageManager.getImage(resource);
            resourceImage = FreeMarsImageManager.createResizedCopy(resourceImage, -1, 24, false, offerGiftDialog);
            Icon icon = new ImageIcon(resourceImage);
            offerGiftDialog.addResourceGiftType(icon, getResourceGiftTypeButtonActionListener(), String.valueOf(resource.getId()));
        }
        if (offerGiftDialogModel.getFromPlayer().getSettlementCount() > 0) {
            Iterator<Settlement> iterator = offerGiftDialogModel.getFromPlayer().getSettlementsIterator();
            while (iterator.hasNext()) {
                offerGiftDialogModel.addSettlement(iterator.next());
            }
        } else {
            offerGiftDialog.setResourceGiftTypeButtonsEnabled(false);
        }
        ResourceGiftTransferColonySelectorComboBoxItemListener resourceGiftTransferColonySelectorComboBoxItemListener
                = new ResourceGiftTransferColonySelectorComboBoxItemListener(freeMarsController, offerGiftDialog, offerGiftDialogModel);
        offerGiftDialog.addResourceGiftTransferColonySelectorComboBoxItemListener(resourceGiftTransferColonySelectorComboBoxItemListener);
        Iterator<Settlement> settlementsIterator = offerGiftDialogModel.getSettlementsIterator();
        while (settlementsIterator.hasNext()) {
            Settlement settlement = settlementsIterator.next();
            offerGiftDialog.addResourceGiftTransferColonyItem(settlement.getName());
        }
        
        Iterator<Unit> unitsIterator = offerGiftDialogModel.getFromPlayer().getUnitsIterator();
        while (unitsIterator.hasNext()) {
            Unit unit = unitsIterator.next();
            if (isUnitGiftable(unit)) {
                offerGiftDialogModel.addGiftableUnit(unit);
            }
        }
        Iterator<Unit> giftableUnitsIterator = offerGiftDialogModel.getGiftableUnitsIterator();
        int index = 0;
        while (giftableUnitsIterator.hasNext()) {
            Unit giftableUnit = giftableUnitsIterator.next();
            String buttonText = giftableUnit.getName();
            if (giftableUnit.getCoordinate() != null) {
                buttonText = buttonText + " - " + giftableUnit.getCoordinate();
            }
            Image unitImage = FreeMarsImageManager.getImage(giftableUnit);
            unitImage = FreeMarsImageManager.createResizedCopy(unitImage, -1, 48, false, offerGiftDialog);
            Icon icon = new ImageIcon(unitImage);
            offerGiftDialog.addGiftableUnitToggleButton(buttonText, icon, getGiftableUnitButtonActionListener(), String.valueOf(index));
            index = index + 1;
        }
        offerGiftDialog.display();
    }
    
    private int getCreditGiftAmountSliderMajorTickSpacing(int playerWealth) {
        if (playerWealth < 10000) {
            return 2500;
        } else if (playerWealth < 20000) {
            return 5000;
        } else if (playerWealth < 40000) {
            return 10000;
        } else if (playerWealth < 80000) {
            return 20000;
        } else {
            return 40000;
        }
    }
    
    private ResourceGiftTypeButtonActionListener getResourceGiftTypeButtonActionListener() {
        if (resourceGiftTypeButtonActionListener == null) {
            resourceGiftTypeButtonActionListener = new ResourceGiftTypeButtonActionListener(freeMarsController, offerGiftDialogModel, offerGiftDialog);
        }
        return resourceGiftTypeButtonActionListener;
    }
    
    private GiftableUnitButtonActionListener getGiftableUnitButtonActionListener() {
        if (giftableUnitButtonActionListener == null) {
            giftableUnitButtonActionListener = new GiftableUnitButtonActionListener(freeMarsController, offerGiftDialogModel, offerGiftDialog);
        }
        return giftableUnitButtonActionListener;
    }
    
    private boolean isUnitGiftable(Unit unit) {
        if (unit.getContainedUnitCount() > 0 || unit.getContainedPopulation() > 0) {
            return false;
        }
        Coordinate coordinate = unit.getCoordinate();
        if (coordinate != null) {
            Tile tile = freeMarsController.getFreeMarsModel().getTile(coordinate);
            if (tile.getSettlement() != null) {
                return false;
            }
            Iterator<Unit> unitsIterator = tile.getUnitsIterator();
            while (unitsIterator.hasNext()) {
                Unit tileUnit = unitsIterator.next();
                if (!unit.equals(tileUnit) && unit.getPlayer().equals(tileUnit.getPlayer())) {
                    return false;
                }
            }
        }
        return true;
    }
}
