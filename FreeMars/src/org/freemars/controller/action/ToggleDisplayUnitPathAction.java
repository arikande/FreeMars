package org.freemars.controller.action;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.freemars.controller.FreeMarsController;
import org.freemars.controller.viewcommand.SetMainMapDisplayingUnitPathCommand;

/**
 *
 * @author Deniz ARIKAN
 */
public class ToggleDisplayUnitPathAction extends AbstractAction {

    private final FreeMarsController freeMarsController;

    public ToggleDisplayUnitPathAction(FreeMarsController freeMarsController) {
        super("Toggle grid");
        this.freeMarsController = freeMarsController;
    }

    public void actionPerformed(ActionEvent e) {
        boolean displayUnitPath = freeMarsController.getFreeMarsModel().getFreeMarsViewModel().isMapPanelDisplayingUnitPath();
        freeMarsController.executeViewCommand(new SetMainMapDisplayingUnitPathCommand(freeMarsController, !displayUnitPath));
    }
}
