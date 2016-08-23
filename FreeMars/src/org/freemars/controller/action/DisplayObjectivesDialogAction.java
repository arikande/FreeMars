package org.freemars.controller.action;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.freemars.controller.FreeMarsController;
import org.freemars.ui.player.ObjectivesDialog;

/**
 *
 * @author Deniz ARIKAN
 */
public class DisplayObjectivesDialogAction extends AbstractAction {

    private final FreeMarsController controller;

    public DisplayObjectivesDialogAction(FreeMarsController controller) {
        super("Objectives");
        this.controller = controller;
    }

    public void actionPerformed(ActionEvent e) {
        new ObjectivesDialog(controller.getCurrentFrame(), controller.getFreeMarsModel()).display();
    }
}
