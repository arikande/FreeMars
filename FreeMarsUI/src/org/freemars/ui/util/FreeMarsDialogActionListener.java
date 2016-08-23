package org.freemars.ui.util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

/**
 *
 * @author Deniz ARIKAN
 */
public class FreeMarsDialogActionListener implements ActionListener {

    private final FreeMarsDialog freeMarsDialog;

    public FreeMarsDialogActionListener(FreeMarsDialog freeMarsDialog) {
        this.freeMarsDialog = freeMarsDialog;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        freeMarsDialog.dispatchEvent(new WindowEvent(freeMarsDialog, WindowEvent.WINDOW_CLOSING));
    }
}
