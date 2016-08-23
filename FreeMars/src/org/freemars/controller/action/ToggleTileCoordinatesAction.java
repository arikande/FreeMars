package org.freemars.controller.action;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.freemars.controller.FreeMarsController;
import org.freemars.controller.viewcommand.SetMainMapDisplayingTileCoordinatesCommand;

/**
 *
 * @author Deniz ARIKAN
 */
public class ToggleTileCoordinatesAction extends AbstractAction {

    private final FreeMarsController freeMarsController;

    public ToggleTileCoordinatesAction(FreeMarsController freeMarsController) {
        super("Toggle tile coordinates", null);
        this.freeMarsController = freeMarsController;
    }

    public void actionPerformed(ActionEvent e) {
        boolean displayCoordinates = freeMarsController.getFreeMarsModel().getFreeMarsViewModel().isMapPanelDisplayingCoordinates();
        freeMarsController.executeViewCommand(new SetMainMapDisplayingTileCoordinatesCommand(freeMarsController, !displayCoordinates));
    }
}
