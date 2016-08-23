package org.freemars.controller.action;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.freemars.ui.util.FreeMarsDialog;

/**
 *
 * @author Deniz ARIKAN
 */
public class CloseFreeMarsDialogAction extends AbstractAction {

    private final FreeMarsDialog freeMarsDialog;

    public CloseFreeMarsDialogAction(FreeMarsDialog freeMarsDialog) {
        super("Close", null);
        this.freeMarsDialog = freeMarsDialog;
    }

    public void actionPerformed(ActionEvent e) {
        freeMarsDialog.dispose();
    }
}
