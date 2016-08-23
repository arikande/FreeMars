package org.freemars.controller.action;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.freemars.controller.FreeMarsController;
import org.freemars.controller.viewcommand.SetMainMapDisplayingGridCommand;

/**
 *
 * @author Deniz ARIKAN
 */
public class ToggleGridAction extends AbstractAction {

    private final FreeMarsController freeMarsController;

    public ToggleGridAction(FreeMarsController freeMarsController) {
        super("Toggle grid");
        this.freeMarsController = freeMarsController;
    }

    public void actionPerformed(ActionEvent e) {
        boolean showGrid = freeMarsController.getFreeMarsModel().getFreeMarsViewModel().isMapPanelDisplayingGrid();
        freeMarsController.executeViewCommand(new SetMainMapDisplayingGridCommand(freeMarsController, !showGrid));
    }
}
