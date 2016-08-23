package org.freemars.colonydialog.controller;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.freemars.colonydialog.BuyProductionDialog;
import org.freemars.colonydialog.ColonyDialogModel;
import org.freemars.controller.FreeMarsController;
import org.freerealm.Realm;
import org.freerealm.command.BuySettlementProductionCommand;

/**
 *
 * @author Deniz ARIKAN
 */
public class BuyColonyProductionAction extends AbstractAction {

    private final FreeMarsController freeMarsController;
    private final BuyProductionDialog buyProductionDialog;
    private final ColonyDialogModel model;

    public BuyColonyProductionAction(FreeMarsController freeMarsController, ColonyDialogModel model, BuyProductionDialog buyProductionDialog) {
        super("Buy");
        this.freeMarsController = freeMarsController;
        this.buyProductionDialog = buyProductionDialog;
        this.model = model;
    }

    public void actionPerformed(ActionEvent e) {
        Realm realm = freeMarsController.getFreeMarsModel().getRealm();
        freeMarsController.execute(new BuySettlementProductionCommand(realm, model.getColony(), buyProductionDialog.getProductionPointSliderValue()));
        model.refresh(ColonyDialogModel.CURRENT_PRODUCTION_UPDATE);
        buyProductionDialog.dispose();
    }
}
