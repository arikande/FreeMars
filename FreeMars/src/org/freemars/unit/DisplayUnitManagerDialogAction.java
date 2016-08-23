package org.freemars.unit;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.freemars.colonydialog.controller.CloseDialogAction;
import org.freemars.controller.FreeMarsController;
import org.freemars.unit.manager.UnitManagerDialog;
import org.freemars.unit.manager.UnitsTableMouseInputAdapter;
import org.freerealm.player.Player;

/**
 *
 * @author Deniz ARIKAN
 */
public class DisplayUnitManagerDialogAction extends AbstractAction {

    private final FreeMarsController controller;

    public DisplayUnitManagerDialogAction(FreeMarsController controller) {
        super("Unit manager");
        this.controller = controller;
    }

    public void actionPerformed(ActionEvent e) {
        Player player = controller.getFreeMarsModel().getActivePlayer();
        UnitManagerDialog unitManagerDialog = new UnitManagerDialog(controller.getCurrentFrame());
        unitManagerDialog.setCloseButtonAction(new CloseDialogAction(unitManagerDialog));
        unitManagerDialog.setUnitsTableMouseInputAdapter(new UnitsTableMouseInputAdapter(controller, unitManagerDialog, player));
        new UnitsTableHelper(controller, unitManagerDialog).updateUnitsTable();
        new UnitsTableHelper(controller, unitManagerDialog).updateUnitTypeCounts();
        unitManagerDialog.display();
    }
}
