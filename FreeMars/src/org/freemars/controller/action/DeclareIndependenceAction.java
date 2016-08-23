package org.freemars.controller.action;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import org.freemars.controller.FreeMarsController;
import org.freemars.earth.command.DeclareIndependenceCommand;
import org.freemars.player.FreeMarsPlayer;
import org.freemars.ui.util.FreeMarsOptionPane;

/**
 *
 * @author Deniz ARIKAN
 */
public class DeclareIndependenceAction extends AbstractAction {

    private final FreeMarsController freeMarsController;

    public DeclareIndependenceAction(FreeMarsController freeMarsController) {
        super("Declare Independence");
        this.freeMarsController = freeMarsController;
    }

    public void actionPerformed(ActionEvent e) {
        FreeMarsPlayer activePlayer = (FreeMarsPlayer) freeMarsController.getFreeMarsModel().getActivePlayer();
        if (activePlayer.getSettlementCount() == 0) {
            FreeMarsOptionPane.showMessageDialog(freeMarsController.getCurrentFrame(), "You can not declare independence if you do not have any colonies", "No colonies");

        } else {
            Object[] options = {"Yes, liberty or death", "No, not yet"};
            int value = JOptionPane.showOptionDialog(freeMarsController.getCurrentFrame(),
                    "Do you really want to declare your independence from Earth government?",
                    "Declare independence",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[1]);
            if (value == JOptionPane.YES_OPTION) {
                freeMarsController.execute(new DeclareIndependenceCommand(freeMarsController, activePlayer));
            }
        }
    }

    public boolean checkEnabled() {
        FreeMarsPlayer activePlayer = freeMarsController.getFreeMarsModel().getActivePlayer();
        if (activePlayer == null) {
            return false;
        }
        return !activePlayer.hasDeclaredIndependence();
    }
}
