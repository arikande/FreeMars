package org.freemars.colonydialog.controller;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JDialog;

/**
 *
 * @author Deniz ARIKAN
 */
public class CloseDialogAction extends AbstractAction {

    private final JDialog dialog;

    public CloseDialogAction(JDialog dialog) {
        super("Close");
        this.dialog = dialog;
    }

    public void actionPerformed(ActionEvent e) {
        dialog.dispose();
    }
}
