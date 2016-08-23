package org.freemars.controller.action.file;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import org.apache.log4j.Logger;
import org.freemars.controller.FreeMarsController;

/**
 *
 * @author Deniz ARIKAN
 */
public class ExitGameAction extends AbstractAction {

    private final FreeMarsController controller;

    public ExitGameAction(FreeMarsController controller) {
        super("Exit game");
        this.controller = controller;
    }

    public void actionPerformed(ActionEvent e) {
        Object[] options = {"Yes, exit", "No, thanks"};
        int value = JOptionPane.showOptionDialog(controller.getCurrentFrame(),
                "Really quit game?",
                "Quit game",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[1]);
        if (value == JOptionPane.YES_OPTION) {
            Logger.getLogger(ExitGameAction.class).info("Exiting Free Mars.");
            System.exit(0);
        }
    }
}
