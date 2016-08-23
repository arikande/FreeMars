/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.freemars.colonydialog;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JList;
import javax.swing.ListModel;

import org.freemars.controller.FreeMarsController;
import org.freerealm.command.RemoveFromSettlementProductionQueueCommand;

/**
 *
 * @author arikande
 */
public class CurrentQueueListMouseAdapter extends MouseAdapter {

    private final FreeMarsController freeMarsController;
    private final ColonyDialogModel colonyDialogModel;
    private final ProductionQueueManagementDialog productionQueueManagementDialog;

    public CurrentQueueListMouseAdapter(FreeMarsController freeMarsController, ColonyDialogModel model, ProductionQueueManagementDialog productionQueueManagementDialog) {
        this.freeMarsController = freeMarsController;
        this.colonyDialogModel = model;
        this.productionQueueManagementDialog = productionQueueManagementDialog;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
            JList list = (JList) e.getSource();
            int index = list.locationToIndex(e.getPoint());
            ListModel dlm = list.getModel();
            Object item = dlm.getElementAt(index);
            list.ensureIndexIsVisible(index);
            productionQueueManagementDialog.removeItemFromCurrentQueue(item);
            freeMarsController.execute(new RemoveFromSettlementProductionQueueCommand(colonyDialogModel.getColony(), index));
            productionQueueManagementDialog.update();
            colonyDialogModel.refresh(ColonyDialogModel.CURRENT_PRODUCTION_UPDATE);
        }
    }
}
