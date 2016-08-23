package org.freemars.diplomacy;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import org.freemars.controller.FreeMarsController;
import org.freemars.player.FreeMarsPlayer;
import org.freerealm.diplomacy.PlayerRelation;
import org.freerealm.executor.command.DecreaseDiplomaticAttitudeCommand;
import org.freerealm.executor.command.SetDiplomaticStatusCommand;

/**
 *
 * @author Deniz ARIKAN
 */
public class DisplayDeclareWarAction extends AbstractAction {

    private final FreeMarsController freeMarsController;
    private final NegotiationDialog negotiationDialog;
    private final FreeMarsPlayer player1;
    private final FreeMarsPlayer player2;

    public DisplayDeclareWarAction(FreeMarsController freeMarsController, NegotiationDialog negotiationDialog, FreeMarsPlayer player1, FreeMarsPlayer player2) {
        super("Declare war");
        this.freeMarsController = freeMarsController;
        this.negotiationDialog = negotiationDialog;
        this.player1 = player1;
        this.player2 = player2;
    }

    public void actionPerformed(ActionEvent ae) {
        Object[] options = {"Yes", "No"};
        int value = JOptionPane.showOptionDialog(freeMarsController.getCurrentFrame(),
                "Do you really wish declare war on " + player2.getNation().getAdjective() + "?",
                "Declare war",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[1]);
        if (value == JOptionPane.YES_OPTION) {
            SetDiplomaticStatusCommand setDiplomaticStatusCommand = new SetDiplomaticStatusCommand(freeMarsController.getFreeMarsModel().getRealm(), player1, player2, PlayerRelation.AT_WAR);
            freeMarsController.execute(setDiplomaticStatusCommand);
            DecreaseDiplomaticAttitudeCommand decreaseDiplomaticAttitudeCommand = new DecreaseDiplomaticAttitudeCommand(player2, player1, 200);
            freeMarsController.execute(decreaseDiplomaticAttitudeCommand);
            NegotiationDialogHelper.update(freeMarsController, negotiationDialog, player1, player2);
        }
    }

}
