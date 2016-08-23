package org.freemars.earth.support;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.freemars.controller.FreeMarsController;

/**
 *
 * @author Deniz ARIKAN
 */
public class DisplayFreeColonizerMessageAction extends AbstractAction {

    private final FreeMarsController controller;

    public DisplayFreeColonizerMessageAction(FreeMarsController controller) {
        super("Free colonizer");
        this.controller = controller;
    }

    public void actionPerformed(ActionEvent e) {
        FreeColonizerDialog dialog = new FreeColonizerDialog(controller.getCurrentFrame(), controller);
        dialog.display();
    }
}
