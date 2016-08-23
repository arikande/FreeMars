package org.freemars.controller.action;

import java.awt.Image;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.freemars.controller.FreeMarsController;
import org.freemars.earth.ui.OrbitDialog;
import org.freemars.ui.image.FreeMarsImageManager;
import org.freemars.ui.unit.RenameUnitDialog;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class DisplayRenameUnitFromOrbitDialogAction extends AbstractAction {

    private final FreeMarsController controller;
    private final OrbitDialog orbitDialog;
    private final Unit unit;

    public DisplayRenameUnitFromOrbitDialogAction(FreeMarsController controller, OrbitDialog orbitDialog, Unit unit) {
        super("Rename unit");
        this.controller = controller;
        this.orbitDialog = orbitDialog;
        this.unit = unit;
    }

    public void actionPerformed(ActionEvent e) {
        Unit unitToRename = unit != null ? unit : controller.getFreeMarsModel().getActivePlayer().getActiveUnit();
        if (unitToRename != null) {
            RenameUnitDialog renameUnitDialog = new RenameUnitDialog(controller.getCurrentFrame());
            renameUnitDialog.setCurrentUnitName(unitToRename.getName());
            Image unitImage = FreeMarsImageManager.getImage(unitToRename);
            unitImage = FreeMarsImageManager.createResizedCopy(unitImage, 70, -1, false, renameUnitDialog);
            renameUnitDialog.setUnitImage(unitImage);
            renameUnitDialog.setConfirmButtonAction(new RenameUnitAction(controller, unitToRename, renameUnitDialog));
            renameUnitDialog.setNameTextFieldSelected();
            renameUnitDialog.display();
            orbitDialog.update();
        }
    }
}
