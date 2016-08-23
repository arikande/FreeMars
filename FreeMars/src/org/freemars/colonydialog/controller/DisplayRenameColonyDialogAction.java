package org.freemars.colonydialog.controller;

import java.awt.Image;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.freemars.colonydialog.ColonyDialogModel;
import org.freemars.colonydialog.RenameColonyDialog;
import org.freemars.controller.FreeMarsController;
import org.freemars.ui.image.FreeMarsImageManager;

/**
 * @author Deniz ARIKAN
 */
public class DisplayRenameColonyDialogAction extends AbstractAction {

    private final FreeMarsController freeMarsController;
    private final ColonyDialogModel model;

    public DisplayRenameColonyDialogAction(FreeMarsController freeMarsController, ColonyDialogModel colonyDialogModel) {
        super("Rename");
        this.freeMarsController = freeMarsController;
        this.model = colonyDialogModel;
    }

    public void actionPerformed(ActionEvent e) {
        RenameColonyDialog renameColonyDialog = new RenameColonyDialog(null);
        renameColonyDialog.setCurrentColonyName(model.getColony().getName());
        Image colonyImage = FreeMarsImageManager.getImage(model.getColony());
        colonyImage = FreeMarsImageManager.createResizedCopy(colonyImage, 90, -1, false, renameColonyDialog);
        renameColonyDialog.setCurrentColonyImage(colonyImage);
        renameColonyDialog.setConfirmButtonAction(new RenameColonyAction(freeMarsController, model, renameColonyDialog));
        renameColonyDialog.display();
    }
}
