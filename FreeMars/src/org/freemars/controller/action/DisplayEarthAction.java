package org.freemars.controller.action;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.freemars.controller.FreeMarsController;
import org.freemars.earth.ui.EarthDialog;

/**
 *
 * @author Deniz ARIKAN
 */
public class DisplayEarthAction extends AbstractAction {

    private final FreeMarsController controller;

    public DisplayEarthAction(FreeMarsController controller) {
        super("Earth");
        this.controller = controller;
    }

    public void actionPerformed(ActionEvent e) {
        EarthDialog earthDialog = new EarthDialog(controller);
        earthDialog.display();
//        controller.refresh();
    }
}
