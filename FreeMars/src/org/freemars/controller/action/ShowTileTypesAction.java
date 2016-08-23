package org.freemars.controller.action;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JCheckBoxMenuItem;
import org.freemars.controller.FreeMarsController;
import org.freemars.controller.viewcommand.SetMainMapDisplayingTileTypesCommand;

/**
 *
 * @author Deniz ARIKAN
 */
public class ShowTileTypesAction extends AbstractAction {

    private final FreeMarsController freeMarsController;

    public ShowTileTypesAction(FreeMarsController freeMarsController) {
        super("Show tile types");
        this.freeMarsController = freeMarsController;
    }

    public void actionPerformed(ActionEvent e) {
        boolean showTileTypes = ((JCheckBoxMenuItem) e.getSource()).isSelected();
        freeMarsController.executeViewCommand(new SetMainMapDisplayingTileTypesCommand(freeMarsController, showTileTypes));
    }
}
