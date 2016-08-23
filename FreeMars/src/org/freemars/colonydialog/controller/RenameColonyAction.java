package org.freemars.colonydialog.controller;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.freemars.colonydialog.ColonyDialogModel;
import org.freemars.colonydialog.RenameColonyDialog;
import org.freemars.controller.FreeMarsController;
import org.freerealm.command.RenameSettlementCommand;

/**
 *
 * @author Deniz ARIKAN
 */
public class RenameColonyAction extends AbstractAction {

    private final FreeMarsController freeMarsController;
    private final RenameColonyDialog dialog;
    private final ColonyDialogModel model;

    public RenameColonyAction(FreeMarsController freeMarsController, ColonyDialogModel model, RenameColonyDialog dialog) {
        super("Rename");
        this.freeMarsController = freeMarsController;
        this.dialog = dialog;
        this.model = model;
    }

    public void actionPerformed(ActionEvent e) {
        freeMarsController.execute(new RenameSettlementCommand(model.getColony(), dialog.getNameTextFieldValue()));
        dialog.dispose();
        model.refresh(ColonyDialogModel.COLONY_RENAME_UPDATE);
    }
}
