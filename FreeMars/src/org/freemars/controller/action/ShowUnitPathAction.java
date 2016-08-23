package org.freemars.controller.action;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JCheckBoxMenuItem;
import org.freemars.controller.FreeMarsController;
import org.freemars.controller.viewcommand.SetMainMapDisplayingUnitPathCommand;

/**
 *
 * @author Deniz ARIKAN
 */
public class ShowUnitPathAction extends AbstractAction {

    private final FreeMarsController freeMarsController;

    public ShowUnitPathAction(FreeMarsController freeMarsController) {
        super("Show unit path");
        this.freeMarsController = freeMarsController;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        boolean showUnitPath = ((JCheckBoxMenuItem) actionEvent.getSource()).isSelected();
        freeMarsController.executeViewCommand(new SetMainMapDisplayingUnitPathCommand(freeMarsController, showUnitPath));
    }
}
