package org.freemars.earth.action;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.freemars.controller.FreeMarsController;
import org.freemars.earth.ui.IndependenceDeclaredDialog;

/**
 *
 * @author Deniz ARIKAN
 */
public class DisplayIndependenceDeclaredDialogAction extends AbstractAction {

    private final FreeMarsController controller;
    private final int expeditionaryForceTravelTurns;

    public DisplayIndependenceDeclaredDialogAction(FreeMarsController controller, int expeditionaryForceTravelTurns) {
        this.controller = controller;
        this.expeditionaryForceTravelTurns = expeditionaryForceTravelTurns;
    }

    public void actionPerformed(ActionEvent e) {
        new IndependenceDeclaredDialog(controller.getCurrentFrame(), expeditionaryForceTravelTurns).display();
    }
}
