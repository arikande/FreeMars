package org.freemars.colonydialog.controller;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JDialog;
import org.freemars.colonydialog.BuildableImprovementsListMouseAdapter;
import org.freemars.colonydialog.BuildableUnitsListMouseAdapter;
import org.freemars.colonydialog.ColonyDialogModel;
import org.freemars.colonydialog.CurrentQueueListMouseAdapter;
import org.freemars.colonydialog.ProductionQueueManagementDialog;
import org.freemars.controller.FreeMarsController;

/**
 *
 * @author Deniz ARIKAN
 */
public class DisplayQueueManagementDialogAction extends AbstractAction {

    private final FreeMarsController freeMarsController;
    private final ColonyDialogModel colonyDialogModel;
    private final JDialog parent;

    public DisplayQueueManagementDialogAction(FreeMarsController freeMarsController, ColonyDialogModel model, JDialog parent) {
        super("Production queue");
        this.freeMarsController = freeMarsController;
        this.colonyDialogModel = model;
        this.parent = parent;
    }

    public void actionPerformed(ActionEvent e) {
        ProductionQueueManagementDialog productionQueueManagementDialog = new ProductionQueueManagementDialog(parent);
        productionQueueManagementDialog.setModel(colonyDialogModel);
        productionQueueManagementDialog.addBuildableUnitsListMouseListener(new BuildableUnitsListMouseAdapter(freeMarsController, colonyDialogModel, productionQueueManagementDialog));
        productionQueueManagementDialog.addBuildableImprovementsListMouseListener(new BuildableImprovementsListMouseAdapter(freeMarsController, colonyDialogModel, productionQueueManagementDialog));
        productionQueueManagementDialog.addCurrentQueueListMouseListener(new CurrentQueueListMouseAdapter(freeMarsController, colonyDialogModel, productionQueueManagementDialog));
        productionQueueManagementDialog.update();
        productionQueueManagementDialog.display();
    }
}
