package org.freemars.controller.action;

import java.awt.KeyboardFocusManager;
import java.awt.Window;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import org.freemars.controller.FreeMarsController;
import org.freemars.earth.ui.OrbitDialog;

/**
 *
 * @author Deniz ARIKAN
 */
public class DisplayOrbitAction extends AbstractAction {

    private final FreeMarsController controller;

    public DisplayOrbitAction(FreeMarsController controller) {
        super("Orbit");
        this.controller = controller;
    }

    public void actionPerformed(ActionEvent e) {
        Window windowFocusOwner = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusedWindow();
        JFrame parent = (JFrame) SwingUtilities.getRoot(windowFocusOwner);
        (new OrbitDialog(parent, controller)).display();
    }
}
