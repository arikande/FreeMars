package org.freemars.controller.action;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JCheckBoxMenuItem;
import org.freemars.controller.FreeMarsController;
import org.freemars.controller.viewcommand.SetMainMapDisplayingGridCommand;

/**
 *
 * @author Deniz ARIKAN
 */
public class ShowGridAction extends AbstractAction {

    private final FreeMarsController freeMarsController;

    public ShowGridAction(FreeMarsController controller) {
        super("Show grid");
        this.freeMarsController = controller;
    }

    public void actionPerformed(ActionEvent e) {
        boolean showGrid = ((JCheckBoxMenuItem) e.getSource()).isSelected();
        freeMarsController.executeViewCommand(new SetMainMapDisplayingGridCommand(freeMarsController, showGrid));
    }
}
