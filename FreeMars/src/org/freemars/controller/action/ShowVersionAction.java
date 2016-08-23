package org.freemars.controller.action;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.freemars.about.AboutDialog;
import org.freemars.controller.FreeMarsController;

/**
 *
 * @author Deniz ARIKAN
 */
public class ShowVersionAction extends AbstractAction {

    private final FreeMarsController controller;

    public ShowVersionAction(FreeMarsController controller) {
        super("About", null);
        this.controller = controller;
    }

    public void actionPerformed(ActionEvent e) {
        new AboutDialog(controller.getCurrentFrame()).display();
    }
}
