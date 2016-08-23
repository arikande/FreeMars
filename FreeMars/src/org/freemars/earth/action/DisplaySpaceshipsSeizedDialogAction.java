package org.freemars.earth.action;

import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.AbstractAction;
import org.freemars.controller.FreeMarsController;
import org.freemars.earth.ui.SpaceshipsSeizedDialog;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class DisplaySpaceshipsSeizedDialogAction extends AbstractAction {

    private final FreeMarsController controller;
    private List<Unit> seizedUnits;

    public DisplaySpaceshipsSeizedDialogAction(FreeMarsController controller) {
        this.controller = controller;
    }

    public void actionPerformed(ActionEvent e) {
        SpaceshipsSeizedDialog spaceShipsSeizedDialog = new SpaceshipsSeizedDialog(controller.getCurrentFrame(), seizedUnits);
        spaceShipsSeizedDialog.display();
    }

    public void setSeizedUnits(List<Unit> seizedUnits) {
        this.seizedUnits = seizedUnits;
    }
}
