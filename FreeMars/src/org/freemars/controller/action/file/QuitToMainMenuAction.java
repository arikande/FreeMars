package org.freemars.controller.action.file;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import org.freemars.command.ResetFreeMarsModelCommand;
import org.freemars.controller.FreeMarsController;

/**
 *
 * @author Deniz ARIKAN
 */
public class QuitToMainMenuAction extends AbstractAction {

    private final FreeMarsController freeMarsController;

    public QuitToMainMenuAction(FreeMarsController freeMarsController1) {
        super("Quit to main menu");
        this.freeMarsController = freeMarsController1;
    }

    public void actionPerformed(ActionEvent e) {
        Object[] options = {"Yes", "No, thanks"};
        int value = JOptionPane.showOptionDialog(freeMarsController.getCurrentFrame(),
                "Quit current game and return to main menu?",
                "Quit game",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[1]);
        if (value == JOptionPane.YES_OPTION) {
            freeMarsController.execute(new ResetFreeMarsModelCommand(freeMarsController.getFreeMarsModel()));
            freeMarsController.displayMainMenuFrame();
            freeMarsController.displayMainMenuWindow();
        }
    }
}
