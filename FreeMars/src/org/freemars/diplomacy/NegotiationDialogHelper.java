package org.freemars.diplomacy;

import org.freemars.controller.FreeMarsController;
import org.freemars.diplomacy.gift.DisplayOfferGiftAction;
import org.freemars.player.FreeMarsPlayer;
import org.freerealm.diplomacy.PlayerRelation;

/**
 *
 * @author Deniz ARIKAN
 */
public class NegotiationDialogHelper {

    public static void update(FreeMarsController freeMarsController, NegotiationDialog negotiationDialog, FreeMarsPlayer negotiatingPlayer, FreeMarsPlayer clickedPlayer) {
        PlayerRelation playerRelation = negotiatingPlayer.getDiplomacy().getPlayerRelation(clickedPlayer);
        negotiationDialog.setDiplomaticStatusLabelText(playerRelation.getStatusDefinition());

        negotiationDialog.setPlayer1ToPlayer2AttitudeLabelText(DiplomacyUtility.getAttitudeString(negotiatingPlayer.getDiplomacy().getPlayerRelation(clickedPlayer).getAttitude()));
        negotiationDialog.setPlayer2ToPlayer1AttitudeLabelText(DiplomacyUtility.getAttitudeString(clickedPlayer.getDiplomacy().getPlayerRelation(negotiatingPlayer).getAttitude()));

        if (playerRelation.getStatus() == PlayerRelation.NO_CONTACT || playerRelation.getStatus() == PlayerRelation.AT_PEACE || playerRelation.getStatus() == PlayerRelation.ALLIED) {
            negotiationDialog.setExchangeMapsButtonAction(new DisplayExchangeMapsAction(freeMarsController, negotiatingPlayer, clickedPlayer));
            negotiationDialog.setOfferGiftButtonAction(new DisplayOfferGiftAction(freeMarsController, negotiationDialog, negotiatingPlayer, clickedPlayer));
        } else {
            negotiationDialog.setExchangeMapsButtonAction(null);
            negotiationDialog.setOfferGiftButtonAction(null);
        }
        if (playerRelation.getStatus() == PlayerRelation.NO_CONTACT || playerRelation.getStatus() == PlayerRelation.AT_PEACE) {
            negotiationDialog.setOfferAllianceButtonAction(new DisplayOfferAllianceAction(freeMarsController, negotiationDialog, negotiatingPlayer, clickedPlayer));
            negotiationDialog.setDeclareWarButtonAction(new DisplayDeclareWarAction(freeMarsController, negotiationDialog, negotiatingPlayer, clickedPlayer));
        } else {
            negotiationDialog.setOfferAllianceButtonAction(null);
            negotiationDialog.setDeclareWarButtonAction(null);
        }
        if (playerRelation.getStatus() == PlayerRelation.ALLIED) {
            negotiationDialog.setBreakAllianceButtonAction(new DisplayBreakAllianceAction(freeMarsController, negotiationDialog, negotiatingPlayer, clickedPlayer));
        } else {
            negotiationDialog.setBreakAllianceButtonAction(null);
        }
        if (playerRelation.getStatus() == PlayerRelation.AT_WAR) {
            negotiationDialog.setOfferPeaceButtonAction(new DisplayOfferPeaceAction(freeMarsController, negotiationDialog, negotiatingPlayer, clickedPlayer));
        } else {
            negotiationDialog.setOfferPeaceButtonAction(null);
        }
        negotiationDialog.updateDiplomaticActionButtons();
    }

}
