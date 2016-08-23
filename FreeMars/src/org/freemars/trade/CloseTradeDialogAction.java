package org.freemars.trade;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JDialog;

/**
 *
 * @author Deniz ARIKAN
 */
public class CloseTradeDialogAction extends AbstractAction {

    private final JDialog dialog;

    public CloseTradeDialogAction(JDialog dialog) {
        super("Close", null);
        this.dialog = dialog;
    }

    public void actionPerformed(ActionEvent e) {
        dialog.dispose();
    }
}
