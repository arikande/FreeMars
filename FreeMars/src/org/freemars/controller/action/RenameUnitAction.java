package org.freemars.controller.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.freemars.controller.FreeMarsController;
import org.freemars.ui.unit.RenameUnitDialog;
import org.freemars.ui.util.FreeMarsOptionPane;
import org.freerealm.command.SetUnitNameCommand;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class RenameUnitAction extends AbstractAction {

    private final FreeMarsController controller;
    private final RenameUnitDialog renameUnitDialog;
    private final Unit unit;

    public RenameUnitAction(FreeMarsController controller, Unit unit, RenameUnitDialog renameUnitDialog) {
        this.controller = controller;
        this.unit = unit;
        this.renameUnitDialog = renameUnitDialog;
    }

    public void actionPerformed(ActionEvent e) {
        String name = renameUnitDialog.getNameTextFieldValue();
        if (!name.trim().equals("")) {
            controller.execute(new SetUnitNameCommand(unit, name));
            renameUnitDialog.dispose();
        } else {
            FreeMarsOptionPane.showMessageDialog(renameUnitDialog, "Unit name cannot be empty", "Error");
            renameUnitDialog.setNameTextFieldValue(unit.getName());
            renameUnitDialog.setNameTextFieldFocused();
            renameUnitDialog.setNameTextFieldSelected();
        }
    }
}
