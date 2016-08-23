package org.freemars.earth.support;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.freemars.controller.FreeMarsController;

/**
 *
 * @author Deniz ARIKAN
 */
public class DisplayFreeStarportMessageAction extends AbstractAction {

    private final FreeMarsController controller;

    public DisplayFreeStarportMessageAction(FreeMarsController controller) {
        super("Free starport");
        this.controller = controller;
    }

    public void actionPerformed(ActionEvent e) {
        FreeStarportDialog dialog = new FreeStarportDialog(controller.getCurrentFrame(), controller);
        dialog.display();
    }
}
