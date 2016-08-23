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
public class DisplayOfferPeaceAction extends AbstractAction {

    private final FreeMarsController freeMarsController;
    private final NegotiationDialog negotiationDialog;
    private final FreeMarsPlayer player1;
    private final FreeMarsPlayer player2;

    public DisplayOfferPeaceAction(FreeMarsController freeMarsController, NegotiationDialog negotiationDialog, FreeMarsPlayer player1, FreeMarsPlayer player2) {
        super("Offer peace");
        this.freeMarsController = freeMarsController;
        this.negotiationDialog = negotiationDialog;
        this.player1 = player1;
        this.player2 = player2;
    }

    public void actionPerformed(ActionEvent ae) {
        SetDiplomaticStatusCommand setDiplomaticStatusCommand = new SetDiplomaticStatusCommand(freeMarsController.getFreeMarsModel().getRealm(), player1, player2, PlayerRelation.AT_PEACE);
        freeMarsController.execute(setDiplomaticStatusCommand);
        NegotiationDialogHelper.update(freeMarsController, negotiationDialog, player1, player2);
        FreeMarsOptionPane.showMessageDialog(freeMarsController.getCurrentFrame(), "Offer peace");
    }

}
