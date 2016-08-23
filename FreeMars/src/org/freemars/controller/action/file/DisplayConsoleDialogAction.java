package org.freemars.controller.action.file;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import org.freemars.about.ConsoleDialog;
import org.freemars.about.ConsoleDialogKeyActionListener;
import org.freemars.controller.FreeMarsController;

/**
 *
 * @author Deniz ARIKAN
 */
public class DisplayConsoleDialogAction extends AbstractAction {

    private final FreeMarsController freeMarsController;

    public DisplayConsoleDialogAction(FreeMarsController freeMarsController1) {
        super("Display console");
        this.freeMarsController = freeMarsController1;
    }

    public void actionPerformed(ActionEvent e) {

        ConsoleDialog consoleDialog = new ConsoleDialog(freeMarsController.getCurrentFrame());
        KeyStroke stroke = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
        ConsoleDialogKeyActionListener consoleDialogKeyActionListener = new ConsoleDialogKeyActionListener(freeMarsController, consoleDialog);
        consoleDialog.getRootPane().registerKeyboardAction(consoleDialogKeyActionListener, stroke, JComponent.WHEN_IN_FOCUSED_WINDOW);
        consoleDialog.display();
    }

}
