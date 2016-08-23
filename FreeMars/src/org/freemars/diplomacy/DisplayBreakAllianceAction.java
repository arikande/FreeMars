package org.freemars.diplomacy;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import org.freemars.controller.FreeMarsController;
import org.freemars.player.FreeMarsPlayer;
import org.freemars.ui.util.FreeMarsOptionPane;
import org.freerealm.diplomacy.PlayerRelation;
import org.freerealm.executor.command.DecreaseDiplomaticAttitudeCommand;
import org.freerealm.executor.command.SetDiplomaticStatusCommand;

/**
 *
 * @author Deniz ARIKAN
 */
public class DisplayBreakAllianceAction extends AbstractAction {

    private final FreeMarsController freeMarsController;
    private final NegotiationDialog negotiationDialog;
    private final FreeMarsPlayer player1;
    private final FreeMarsPlayer player2;

    public DisplayBreakAllianceAction(FreeMarsController freeMarsController, NegotiationDialog negotiationDialog, FreeMarsPlayer player1, FreeMarsPlayer player2) {
        super("Break alliance");
        this.freeMarsController = freeMarsController;
        this.negotiationDialog = negotiationDialog;
        this.player1 = player1;
        this.player2 = player2;
    }

    public void actionPerformed(ActionEvent ae) {
        Object[] options = {"Yes", "No, thanks"};
        int value = JOptionPane.showOptionDialog(freeMarsController.getCurrentFrame(),
                "Do you really wish to end our alliance with the " + player2.getNation().getAdjective() + "?",
                "Break alliance",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[1]);
        if (value == JOptionPane.YES_OPTION) {
            SetDiplomaticStatusCommand setDiplomaticStatusCommand = new SetDiplomaticStatusCommand(freeMarsController.getFreeMarsModel().getRealm(), player1, player2, PlayerRelation.AT_PEACE);
            freeMarsController.execute(setDiplomaticStatusCommand);
            DecreaseDiplomaticAttitudeCommand decreaseDiplomaticAttitudeCommand = new DecreaseDiplomaticAttitudeCommand(player2, player1, 200);
            freeMarsController.execute(decreaseDiplomaticAttitudeCommand);
            NegotiationDialogHelper.update(freeMarsController, negotiationDialog, player1, player2);
            StringBuilder message = new StringBuilder();
            message.append("We ended our alliance with the ");
            message.append(player2.getNation().getAdjective());
            message.append(".\n");
            message.append(player2.getNation().getAdjective());
            message.append(" attitude towards us has decreased.");
            FreeMarsOptionPane.showMessageDialog(freeMarsController.getCurrentFrame(), message);
        }
    }

}
