package org.freemars.ui.util;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;

/**
 *
 * @author Deniz ARIKAN
 */
public class FreeMarsDialog extends JDialog {

    public FreeMarsDialog(Frame owner) {
        super(owner);
    }

    public FreeMarsDialog(Dialog owner) {
        super(owner);
    }

    public void display(int width, int height) {
        pack();
        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        if (width > screenSize.width) {
            width = screenSize.width;
        }
        if (height > screenSize.height) {
            height = screenSize.height;
        }
        setBounds((screenSize.width - width) / 2, (screenSize.height - height) / 2, width, height);
        setVisible(true);
        toFront();
    }

    @Override
    protected JRootPane createRootPane() {
        FreeMarsDialogActionListener freeMarsDialogActionListener = new FreeMarsDialogActionListener(this);
        JRootPane modifiedRootPane = super.createRootPane();
        KeyStroke stroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
        modifiedRootPane.registerKeyboardAction(freeMarsDialogActionListener, stroke, JComponent.WHEN_IN_FOCUSED_WINDOW);
        return modifiedRootPane;
    }

}
