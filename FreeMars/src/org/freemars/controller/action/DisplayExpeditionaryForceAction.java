package org.freemars.controller.action;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.freemars.ai.ExpeditionaryForcePlayer;
import org.freemars.controller.FreeMarsController;
import org.freemars.earth.action.DisplayExpeditionaryForceDefeatedDialogAction;
import org.freemars.earth.ui.ExpeditionaryForceDialog;
import org.freemars.player.FreeMarsPlayer;
import org.freerealm.player.Player;

/**
 *
 * @author Deniz ARIKAN
 */
public class DisplayExpeditionaryForceAction extends AbstractAction {

    private final FreeMarsController freeMarsController;

    public DisplayExpeditionaryForceAction(FreeMarsController freeMarsController) {
        super("Expeditionary force");
        this.freeMarsController = freeMarsController;
    }

    public void actionPerformed(ActionEvent e) {
        FreeMarsPlayer activePlayer = (FreeMarsPlayer) freeMarsController.getFreeMarsModel().getActivePlayer();
        ExpeditionaryForcePlayer expeditionaryForcePlayer = freeMarsController.getFreeMarsModel().getRelatedExpeditionaryForcePlayer(activePlayer);
        if (expeditionaryForcePlayer.getStatus() == Player.STATUS_REMOVED) {
            new DisplayExpeditionaryForceDefeatedDialogAction(freeMarsController).actionPerformed(e);
        } else {
            int turnsSinceIndependenceDeclaration = freeMarsController.getFreeMarsModel().getNumberOfTurns() - activePlayer.getIndependenceTurn();
            int remainingTurnsToLand = expeditionaryForcePlayer.getEarthToMarsFlightTurns() - turnsSinceIndependenceDeclaration;
            ExpeditionaryForceDialog expeditionaryForceDialog = new ExpeditionaryForceDialog(freeMarsController.getCurrentFrame(), activePlayer, expeditionaryForcePlayer, remainingTurnsToLand);
            expeditionaryForceDialog.setCloseAction(new CloseFreeMarsDialogAction(expeditionaryForceDialog));
            expeditionaryForceDialog.display();
        }
    }
}
