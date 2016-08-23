package org.freemars.controller.action;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.freemars.controller.FreeMarsController;

/**
 *
 * @author Deniz ARIKAN
 */
public class DisplayHelpContentsAction extends AbstractAction {

    private final FreeMarsController controller;
    private final String helpId;

    public DisplayHelpContentsAction(FreeMarsController controller, String helpId) {
        super("Marsopedia", null);
        this.controller = controller;
        this.helpId = helpId;
    }

    public void actionPerformed(ActionEvent e) {
        controller.getHelpDialog().display(helpId);
    }
}
