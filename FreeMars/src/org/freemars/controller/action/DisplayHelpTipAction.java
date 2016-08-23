package org.freemars.controller.action;

import java.awt.event.ActionEvent;
import java.util.Random;
import javax.swing.AbstractAction;
import org.freemars.controller.FreeMarsController;

/**
 *
 * @author Deniz ARIKAN
 */
public class DisplayHelpTipAction extends AbstractAction {

    private static final int TIP_COUNT = 18;
    private final FreeMarsController controller;

    public DisplayHelpTipAction(FreeMarsController controller) {
        this.controller = controller;
    }

    public void actionPerformed(ActionEvent e) {
        int tipNo = new Random().nextInt(TIP_COUNT) + 1;
        String tipId = "Tip.0";
        if (tipNo < 10) {
            tipId = tipId + "0" + tipNo;
        } else {
            tipId = tipId + tipNo;
        }
        new DisplayHelpContentsAction(controller, tipId).actionPerformed(null);
    }
}
