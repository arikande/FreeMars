package org.freemars.controller.action;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.freemars.controller.FreeMarsController;

/**
 *
 * @author Deniz ARIKAN
 */
public class DisplayMapEditorAction extends AbstractAction {

    private final FreeMarsController controller;

    public DisplayMapEditorAction(FreeMarsController controller) {
        super("Map editor");
        this.controller = controller;
    }

    public void actionPerformed(ActionEvent e) {
        controller.displayEditorFrame();
    }
}
