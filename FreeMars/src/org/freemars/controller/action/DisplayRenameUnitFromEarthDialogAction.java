package org.freemars.controller.action;

import java.awt.Image;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.freemars.controller.FreeMarsController;
import org.freemars.earth.Earth;
import org.freemars.earth.ui.EarthDialog;
import org.freemars.ui.image.FreeMarsImageManager;
import org.freemars.ui.unit.RenameUnitDialog;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class DisplayRenameUnitFromEarthDialogAction extends AbstractAction {

    private final FreeMarsController controller;
    private final EarthDialog earthDialog;
    private final Unit unit;

    public DisplayRenameUnitFromEarthDialogAction(FreeMarsController controller,EarthDialog earthDialog, Unit unit) {
        super("Rename unit");
        this.controller = controller;
        this.earthDialog = earthDialog;
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
            earthDialog.update(Earth.UNIT_RELOCATION_UPDATE);
        }
    }
}
