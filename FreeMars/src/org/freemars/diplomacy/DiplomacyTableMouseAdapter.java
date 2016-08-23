package org.freemars.diplomacy;

import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import org.freemars.ai.ExpeditionaryForcePlayer;
import org.freemars.controller.FreeMarsController;
import org.freemars.player.FreeMarsPlayer;
import org.freemars.ui.image.FreeMarsImageManager;
import org.freemars.ui.util.FreeMarsOptionPane;
import org.freerealm.diplomacy.PlayerRelation;

/**
 *
 * @author arikande
 */
public class DiplomacyTableMouseAdapter extends MouseAdapter {

    private final FreeMarsController freeMarsController;
    private final DiplomacyTableModel diplomacyTableModel;
    private final FreeMarsPlayer negotiatingPlayer;

    public DiplomacyTableMouseAdapter(FreeMarsController freeMarsController, DiplomacyTableModel diplomacyTableModel, FreeMarsPlayer negotiatingPlayer) {
        this.freeMarsController = freeMarsController;
        this.diplomacyTableModel = diplomacyTableModel;
        this.negotiatingPlayer = negotiatingPlayer;
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        DiplomacyTable diplomacyTable = (DiplomacyTable) mouseEvent.getSource();
        int row = diplomacyTable.rowAtPoint(mouseEvent.getPoint());
        FreeMarsPlayer clickedPlayer = diplomacyTableModel.getPlayer(row);
        if (clickedPlayer instanceof ExpeditionaryForcePlayer) {
            FreeMarsOptionPane.showMessageDialog(freeMarsController.getCurrentFrame(), "We cannot negotiate with Expeditionary force", "No diplomacy");
        } else if (negotiatingPlayer.getDiplomacy().getPlayerRelation(clickedPlayer).getStatus() == PlayerRelation.NO_DIPLOMACY_ALLOWED) {
            new NoDiplomacyAllowedDialog(freeMarsController, clickedPlayer).display();
        } else if (negotiatingPlayer.getDiplomacy().getPlayerRelation(clickedPlayer).getStatus() == PlayerRelation.NO_CONTACT) {
            StringBuilder message = new StringBuilder();
            message.append("We have not contacted the ");
            message.append(clickedPlayer.getNation().getAdjective());
            message.append(" yet.");
            FreeMarsOptionPane.showMessageDialog(freeMarsController.getCurrentFrame(), message, "No contact");
        } else {
            NegotiationDialog negotiationDialog = new NegotiationDialog(freeMarsController.getCurrentFrame());
            Image player1FlagImage = FreeMarsImageManager.getImage(negotiatingPlayer.getNation(), false, -1, 60);
            Image player2FlagImage = FreeMarsImageManager.getImage(clickedPlayer.getNation(), false, -1, 60);
            negotiationDialog.setPlayer1FlagImage(player1FlagImage);
            negotiationDialog.setPlayer2FlagImage(player2FlagImage);
            negotiationDialog.setPlayer1ToPlayer2AttitudeLabelText(DiplomacyUtility.getAttitudeString(negotiatingPlayer.getDiplomacy().getPlayerRelation(clickedPlayer).getAttitude()));
            negotiationDialog.setPlayer2ToPlayer1AttitudeLabelText(DiplomacyUtility.getAttitudeString(clickedPlayer.getDiplomacy().getPlayerRelation(negotiatingPlayer).getAttitude()));
            NegotiationDialogHelper.update(freeMarsController, negotiationDialog, negotiatingPlayer, clickedPlayer);
            negotiationDialog.display();
        }
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        DiplomacyTable diplomacyTable = (DiplomacyTable) mouseEvent.getSource();
        int row = diplomacyTable.rowAtPoint(mouseEvent.getPoint());
        diplomacyTable.getSelectionModel().setSelectionInterval(row, row);
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {
        DiplomacyTable diplomacyTable = (DiplomacyTable) mouseEvent.getSource();
        diplomacyTable.getSelectionModel().setSelectionInterval(-1, -1);
        diplomacyTable.clearSelection();
    }
}
