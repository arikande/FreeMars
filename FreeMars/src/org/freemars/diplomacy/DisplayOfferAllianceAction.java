package org.freemars.diplomacy;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.freemars.controller.FreeMarsController;
import org.freemars.player.FreeMarsPlayer;
import org.freemars.ui.util.FreeMarsOptionPane;
import org.freerealm.diplomacy.PlayerRelation;
import org.freerealm.executor.command.SetDiplomaticStatusCommand;

/**
 *
 * @author arikande
 */
public class DisplayOfferAllianceAction extends AbstractAction {

    private final FreeMarsController freeMarsController;
    private final NegotiationDialog negotiationDialog;
    private final FreeMarsPlayer player1;
    private final FreeMarsPlayer player2;

    public DisplayOfferAllianceAction(FreeMarsController freeMarsController, NegotiationDialog negotiationDialog, FreeMarsPlayer player1, FreeMarsPlayer player2) {
        super("Offer alliance");
        this.freeMarsController = freeMarsController;
        this.negotiationDialog = negotiationDialog;
        this.player1 = player1;
        this.player2 = player2;
    }

    public void actionPerformed(ActionEvent ae) {
        int attitude = player2.getDiplomacy().getPlayerRelation(player1).getAttitude();
        if (attitude < 800) {
            FreeMarsOptionPane.showMessageDialog(freeMarsController.getCurrentFrame(), "We do not wish to be allied at this time");
        } else {
            FreeMarsOptionPane.showMessageDialog(freeMarsController.getCurrentFrame(), "We accept your alliance offer");
            SetDiplomaticStatusCommand setDiplomaticStatusCommand = new SetDiplomaticStatusCommand(freeMarsController.getFreeMarsModel().getRealm(), player1, player2, PlayerRelation.ALLIED);
            freeMarsController.execute(setDiplomaticStatusCommand);
            NegotiationDialogHelper.update(freeMarsController, negotiationDialog, player1, player2);
        }
    }

}
