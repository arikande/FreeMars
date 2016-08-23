package org.freemars.controller.action;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JCheckBoxMenuItem;
import org.freemars.controller.FreeMarsController;
import org.freemars.controller.viewcommand.SetMainMapDisplayingTileCoordinatesCommand;

/**
 *
 * @author Deniz ARIKAN
 */
public class ShowTileCoordinatesAction extends AbstractAction {

    private final FreeMarsController freeMarsController;

    public ShowTileCoordinatesAction(FreeMarsController freeMarsController) {
        super("Show tile coordinates");
        this.freeMarsController = freeMarsController;
    }

    public void actionPerformed(ActionEvent e) {
        boolean displayTileCoordinates = ((JCheckBoxMenuItem) e.getSource()).isSelected();
        freeMarsController.executeViewCommand(new SetMainMapDisplayingTileCoordinatesCommand(freeMarsController, displayTileCoordinates));
    }
}
