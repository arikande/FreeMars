package org.freemars.colonydialog.controller;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.text.DecimalFormat;
import javax.swing.AbstractAction;
import org.freemars.colony.FreeMarsColony;
import org.freemars.colonydialog.BuyProductionDialog;
import org.freemars.colonydialog.ColonyDialogModel;
import org.freemars.controller.FreeMarsController;
import org.freemars.ui.image.FreeMarsImageManager;
import org.freerealm.modifier.Modifier;
import org.freerealm.settlement.SettlementBuildable;
import org.freerealm.settlement.SettlementBuildableCostCalculator;

/**
 *
 * @author Deniz ARIKAN
 */
public class DisplayBuyProductionDialogAction extends AbstractAction {

    private final FreeMarsController freeMarsController;
    private final ColonyDialogModel colonyDialogModel;

    public DisplayBuyProductionDialogAction(FreeMarsController freeMarsController, ColonyDialogModel colonyDialogModel) {
        super("Buy");
        this.freeMarsController = freeMarsController;
        this.colonyDialogModel = colonyDialogModel;
    }

    public void actionPerformed(ActionEvent e) {
        int productionPointCost = Integer.parseInt(freeMarsController.getFreeMarsModel().getRealm().getProperty("production_point_cost"));
        FreeMarsColony freeMarsColony = colonyDialogModel.getColony();
        SettlementBuildable currentProduction = freeMarsColony.getCurrentProduction();
        int productionCost = new SettlementBuildableCostCalculator(freeMarsColony.getCurrentProduction(), new Modifier[]{freeMarsColony.getPlayer()}).getCost();
        int remainingProductionCost = productionCost - freeMarsColony.getProductionPoints();
        int buyableProductionCost = colonyDialogModel.getColony().getPlayer().getWealth() / productionPointCost;
        int buyableProductionCostInDialog = remainingProductionCost < buyableProductionCost ? remainingProductionCost : buyableProductionCost;
        BuyProductionDialog buyProductionDialog = new BuyProductionDialog(freeMarsController.getCurrentFrame());
        buyProductionDialog.setTitle("Buy " + currentProduction.getName());
        Image image = FreeMarsImageManager.getImage(currentProduction);
        image = FreeMarsImageManager.createResizedCopy(image, 110, -1, false, buyProductionDialog);
        buyProductionDialog.setImage(image);
        buyProductionDialog.setProductionInfoLabelText(currentProduction.getName());
        buyProductionDialog.setProductionPointValueLabelText(new DecimalFormat().format(buyableProductionCostInDialog));
        buyProductionDialog.setCreditValueLabelText(new DecimalFormat().format(buyableProductionCostInDialog * productionPointCost));
        buyProductionDialog.setProductionPointSliderMaximum(buyableProductionCostInDialog);
        buyProductionDialog.setProductionPointSliderValue(buyableProductionCostInDialog);
        buyProductionDialog.setProductionPointSliderMajorTickSpacing(getProductionPointSliderMajorTickSpacing(buyableProductionCostInDialog));
        buyProductionDialog.addBuyProductionSliderChangeListener(new BuyProductionSliderChangeListener(buyProductionDialog, productionPointCost));
        buyProductionDialog.setBuyButtonAction(new BuyColonyProductionAction(freeMarsController, colonyDialogModel, buyProductionDialog));
        buyProductionDialog.setCancelButtonAction(new CloseDialogAction(buyProductionDialog));
        buyProductionDialog.display();
    }

    private int getProductionPointSliderMajorTickSpacing(int remainingProductionCost) {
        if (remainingProductionCost < 1000) {
            return 250;
        } else if (remainingProductionCost < 2000) {
            return 500;
        } else if (remainingProductionCost < 4000) {
            return 1000;
        } else if (remainingProductionCost < 8000) {
            return 2000;
        } else {
            return 4000;
        }
    }
}
