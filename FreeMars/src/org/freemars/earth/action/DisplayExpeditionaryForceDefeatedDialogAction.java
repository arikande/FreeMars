package org.freemars.earth.action;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.freemars.controller.FreeMarsController;
import org.freemars.earth.ui.ExpeditionaryForceDefeatedDialog;

/**
 *
 * @author Deniz ARIKAN
 */
public class DisplayExpeditionaryForceDefeatedDialogAction extends AbstractAction {

    private final FreeMarsController controller;

    public DisplayExpeditionaryForceDefeatedDialogAction(FreeMarsController controller) {
        this.controller = controller;
    }

    public void actionPerformed(ActionEvent e) {
        new ExpeditionaryForceDefeatedDialog(controller.getCurrentFrame()).display();
    }
}
