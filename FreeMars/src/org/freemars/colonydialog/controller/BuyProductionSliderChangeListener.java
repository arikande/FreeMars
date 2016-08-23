package org.freemars.colonydialog.controller;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.freemars.colonydialog.BuyProductionDialog;

/**
 *
 * @author Deniz ARIKAN
 */
public class BuyProductionSliderChangeListener implements ChangeListener {

    private final BuyProductionDialog buyProductionDialog;
    private final int productionPointCost;

    public BuyProductionSliderChangeListener(BuyProductionDialog buyProductionDialog, int productionPointCost) {
        this.buyProductionDialog = buyProductionDialog;
        this.productionPointCost = productionPointCost;
    }

    public void stateChanged(ChangeEvent e) {
        buyProductionDialog.setProductionPointValueLabelText(String.valueOf(buyProductionDialog.getProductionPointSliderValue()));
        buyProductionDialog.setCreditValueLabelText(String.valueOf(productionPointCost * buyProductionDialog.getProductionPointSliderValue()));
    }
}
