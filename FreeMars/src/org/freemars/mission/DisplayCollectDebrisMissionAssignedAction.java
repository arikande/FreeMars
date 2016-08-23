package org.freemars.mission;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.freemars.controller.FreeMarsController;

/**
 *
 * @author Deniz ARIKAN
 */
public class DisplayCollectDebrisMissionAssignedAction extends AbstractAction {

    private final FreeMarsController controller;
    private final CollectDebrisMission collectDebrisMission;

    public DisplayCollectDebrisMissionAssignedAction(FreeMarsController controller, CollectDebrisMission collectDebrisMission) {
        super("Exploration mission");
        this.controller = controller;
        this.collectDebrisMission = collectDebrisMission;
    }

    public void actionPerformed(ActionEvent e) {
        CollectDebrisMissionAssignedDialog dialog = new CollectDebrisMissionAssignedDialog(controller.getCurrentFrame(), controller.getFreeMarsModel(), collectDebrisMission);
        dialog.display();
    }
}
