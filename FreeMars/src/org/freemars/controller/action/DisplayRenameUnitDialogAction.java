package org.freemars.controller.action;

import java.awt.Image;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.freemars.controller.FreeMarsController;
import org.freemars.ui.image.FreeMarsImageManager;
import org.freemars.ui.unit.RenameUnitDialog;
import org.freerealm.player.Player;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class DisplayRenameUnitDialogAction extends AbstractAction {

    private final FreeMarsController freeMarsController;
    private final Unit unit;

    public DisplayRenameUnitDialogAction(FreeMarsController freeMarsController, Unit unit) {
        super("Rename unit");
        this.freeMarsController = freeMarsController;
        this.unit = unit;
    }

    public void actionPerformed(ActionEvent e) {
        Unit unitToRename = unit != null ? unit : freeMarsController.getFreeMarsModel().getActivePlayer().getActiveUnit();
        if (unitToRename != null) {
            RenameUnitDialog renameUnitDialog = new RenameUnitDialog(freeMarsController.getCurrentFrame());
            renameUnitDialog.setCurrentUnitName(unitToRename.getName());
            Image unitImage = FreeMarsImageManager.getImage(unitToRename);
            unitImage = FreeMarsImageManager.createResizedCopy(unitImage, 70, -1, false, renameUnitDialog);
            renameUnitDialog.setUnitImage(unitImage);
            renameUnitDialog.setConfirmButtonAction(new RenameUnitAction(freeMarsController, unitToRename, renameUnitDialog));
            renameUnitDialog.setNameTextFieldSelected();
            renameUnitDialog.display();
        }
    }

    public boolean checkEnabled() {
        Player activePlayer = freeMarsController.getFreeMarsModel().getActivePlayer();
        if (activePlayer == null) {
            return false;
        }
        Unit unitToOrder = unit != null ? unit : freeMarsController.getFreeMarsModel().getActivePlayer().getActiveUnit();
        return unitToOrder != null;
    }
}
