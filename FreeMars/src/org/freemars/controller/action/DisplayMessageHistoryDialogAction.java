package org.freemars.controller.action;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.freemars.controller.FreeMarsController;
import org.freemars.ui.player.MessageHistoryDialog;

/**
 *
 * @author Deniz ARIKAN
 */
public class DisplayMessageHistoryDialogAction extends AbstractAction {

    private final FreeMarsController controller;

    public DisplayMessageHistoryDialogAction(FreeMarsController controller) {
        super("Message history");
        this.controller = controller;
    }

    public void actionPerformed(ActionEvent e) {
        new MessageHistoryDialog(controller.getCurrentFrame(), controller.getFreeMarsModel().getActivePlayer()).display();
    }
}
