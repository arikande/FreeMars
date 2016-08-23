package org.freemars.diplomacy.gift;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.freemars.controller.FreeMarsController;
import org.freemars.diplomacy.NegotiationDialog;
import org.freemars.diplomacy.NegotiationDialogHelper;
import org.freemars.player.FreeMarsPlayer;

/**
 *
 * @author arikande
 */
public class DisplayOfferGiftAction extends AbstractAction {

    private final FreeMarsController freeMarsController;
    private final NegotiationDialog negotiationDialog;
    private final FreeMarsPlayer fromPlayer;
    private final FreeMarsPlayer toPlayer;

    public DisplayOfferGiftAction(FreeMarsController freeMarsController, NegotiationDialog negotiationDialog, FreeMarsPlayer fromPlayer, FreeMarsPlayer toPlayer) {
        super("Offer gift");
        this.freeMarsController = freeMarsController;
        this.negotiationDialog = negotiationDialog;
        this.fromPlayer = fromPlayer;
        this.toPlayer = toPlayer;
    }

    public void actionPerformed(ActionEvent ae) {
        OfferGiftDialogController offerGiftDialogController = new OfferGiftDialogController(freeMarsController, fromPlayer, toPlayer);
        offerGiftDialogController.displayOfferGiftDialog();
        NegotiationDialogHelper.update(freeMarsController, negotiationDialog, fromPlayer, toPlayer);
    }

}
