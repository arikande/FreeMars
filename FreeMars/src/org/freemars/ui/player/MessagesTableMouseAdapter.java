package org.freemars.ui.player;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import org.freemars.colony.FreeMarsColony;
import org.freemars.controller.FreeMarsController;
import org.freemars.controller.action.DisplayColonyDialogAction;
import org.freemars.controller.action.DisplayEarthAction;
import org.freemars.controller.action.DisplayEarthTaxRateStatisticsDialogAction;
import org.freemars.controller.action.DisplayExpeditionaryForceUpdateAction;
import org.freemars.controller.action.DisplayTreasuryStatisticsDialogAction;
import org.freemars.controller.viewcommand.SetCenteredCoordinateCommand;
import org.freemars.diplomacy.DisplayDiplomacyDialogAction;
import org.freemars.earth.action.DisplayExpeditionaryForceDefeatedDialogAction;
import org.freemars.earth.action.DisplayExpeditionaryForceLandedDialogAction;
import org.freemars.earth.action.DisplaySpaceshipsSeizedDialogAction;
import org.freemars.earth.action.DisplayUnitsSeizedDialogAction;
import org.freemars.earth.message.SpaceshipsSeizedMessage;
import org.freemars.earth.message.UnitsSeizedMessage;
import org.freemars.message.EarthTaxRateChangedMessage;
import org.freemars.message.ExpeditionaryForceChangedMessage;
import org.freemars.message.ExpeditionaryForceDefeatedMessage;
import org.freemars.message.ExpeditionaryForceLandedMessage;
import org.freemars.message.NotEnoughFertilizerMessage;
import org.freemars.message.SettlementCapturedMessage;
import org.freemars.message.UnitAttackedMessage;
import org.freemars.message.UnitRelocationCompletedMessage;
import org.freerealm.player.DiplomaticRelationUpdatedMessage;
import org.freerealm.player.Message;
import org.freerealm.player.NotEnoughPopulationForProductionMessage;
import org.freerealm.player.NotEnoughResourceForProductionMessage;
import org.freerealm.player.ResourceGiftSentMessage;
import org.freerealm.player.ResourceWasteMessage;
import org.freerealm.player.SettlementImprovementCompletedMessage;
import org.freerealm.player.SettlementRelatedMessage;
import org.freerealm.player.UnitCompletedMessage;
import org.freerealm.player.WealthGiftSentMessage;

/**
 *
 * @author Deniz ARIKAN
 */
public class MessagesTableMouseAdapter extends MouseAdapter {

    private final FreeMarsController freeMarsController;
    private final MessagesDialog messagesDialog;

    public MessagesTableMouseAdapter(FreeMarsController freeMarsController, MessagesDialog messagesDialog) {
        this.freeMarsController = freeMarsController;
        this.messagesDialog = messagesDialog;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        MessagesTable messagesTable = (MessagesTable) e.getSource();
        int row = messagesTable.rowAtPoint(new Point(e.getX(), e.getY()));
        Message message = messagesTable.getMessageAt(row);
        if (messagesTable.getRowCount() == 1) {
            messagesDialog.dispose();
        }
        if (message instanceof UnitRelocationCompletedMessage) {
            new DisplayEarthAction(freeMarsController).actionPerformed(null);
        } else if (message instanceof ResourceWasteMessage) {
            ResourceWasteMessage resourceWasteMessage = (ResourceWasteMessage) message;
            new DisplayColonyDialogAction(freeMarsController, (FreeMarsColony) resourceWasteMessage.getSettlement()).actionPerformed(null);
        } else if (message instanceof SettlementImprovementCompletedMessage) {
            SettlementImprovementCompletedMessage settlementImprovementCompletedMessage = (SettlementImprovementCompletedMessage) message;
            new DisplayColonyDialogAction(freeMarsController, (FreeMarsColony) settlementImprovementCompletedMessage.getSettlement()).actionPerformed(null);
        } else if (message instanceof UnitCompletedMessage) {
            UnitCompletedMessage unitCompletedMessage = (UnitCompletedMessage) message;
            new DisplayColonyDialogAction(freeMarsController, (FreeMarsColony) unitCompletedMessage.getSettlement()).actionPerformed(null);
        } else if (message instanceof NotEnoughPopulationForProductionMessage) {
            NotEnoughPopulationForProductionMessage notEnoughPopulationForProductionMessage = (NotEnoughPopulationForProductionMessage) message;
            new DisplayColonyDialogAction(freeMarsController, (FreeMarsColony) notEnoughPopulationForProductionMessage.getSettlement()).actionPerformed(null);
        } else if (message instanceof NotEnoughResourceForProductionMessage) {
            NotEnoughResourceForProductionMessage notEnoughResourceForProductionMessage = (NotEnoughResourceForProductionMessage) message;
            new DisplayColonyDialogAction(freeMarsController, (FreeMarsColony) notEnoughResourceForProductionMessage.getSettlement()).actionPerformed(null);
        } else if (message instanceof UnitAttackedMessage) {
            UnitAttackedMessage unitAttackedMessage = (UnitAttackedMessage) message;
            freeMarsController.executeViewCommand(new SetCenteredCoordinateCommand(freeMarsController, unitAttackedMessage.getCoordinate()));
        } else if (message instanceof SettlementCapturedMessage) {
            SettlementCapturedMessage settlementCapturedMessage = (SettlementCapturedMessage) message;
            freeMarsController.executeViewCommand(new SetCenteredCoordinateCommand(freeMarsController, settlementCapturedMessage.getSettlement().getCoordinate()));
        } else if (message instanceof ExpeditionaryForceChangedMessage) {
            ExpeditionaryForceChangedMessage expeditionaryForceChangedMessage = (ExpeditionaryForceChangedMessage) message;
            new DisplayExpeditionaryForceUpdateAction(freeMarsController, expeditionaryForceChangedMessage.getUpdatedUnits()).actionPerformed(null);
        } else if (message instanceof ExpeditionaryForceDefeatedMessage) {
            new DisplayExpeditionaryForceDefeatedDialogAction(freeMarsController).actionPerformed(null);
        } else if (message instanceof EarthTaxRateChangedMessage) {
            new DisplayEarthTaxRateStatisticsDialogAction(freeMarsController).actionPerformed(null);
        } else if (message instanceof NotEnoughFertilizerMessage) {
            NotEnoughFertilizerMessage notEnoughFertilizerMessage = (NotEnoughFertilizerMessage) message;
            new DisplayColonyDialogAction(freeMarsController, notEnoughFertilizerMessage.getFreeMarsColony()).actionPerformed(null);
        } else if (message instanceof SettlementRelatedMessage) {
            SettlementRelatedMessage settlementRelatedMessage = (SettlementRelatedMessage) message;
            new DisplayColonyDialogAction(freeMarsController, (FreeMarsColony) settlementRelatedMessage.getSettlement()).actionPerformed(null);
        } else if (message instanceof SpaceshipsSeizedMessage) {
            SpaceshipsSeizedMessage spaceshipsSeizedMessage = (SpaceshipsSeizedMessage) message;
            DisplaySpaceshipsSeizedDialogAction displaySpaceshipsSeizedDialogAction = new DisplaySpaceshipsSeizedDialogAction(freeMarsController);
            displaySpaceshipsSeizedDialogAction.setSeizedUnits(spaceshipsSeizedMessage.getSeizedUnits());
            displaySpaceshipsSeizedDialogAction.actionPerformed(null);
        } else if (message instanceof UnitsSeizedMessage) {
            UnitsSeizedMessage unitsSeizedMessage = (UnitsSeizedMessage) message;
            DisplayUnitsSeizedDialogAction displayUnitsSeizedDialogAction = new DisplayUnitsSeizedDialogAction(freeMarsController);
            displayUnitsSeizedDialogAction.setSeizedUnits(unitsSeizedMessage.getSeizedUnits());
            displayUnitsSeizedDialogAction.actionPerformed(null);
        } else if (message instanceof ExpeditionaryForceLandedMessage) {
            ExpeditionaryForceLandedMessage expeditionaryForceLandedMessage = (ExpeditionaryForceLandedMessage) message;
            DisplayExpeditionaryForceLandedDialogAction displayExpeditionaryForceLandedDialogAction = new DisplayExpeditionaryForceLandedDialogAction(freeMarsController);
            displayExpeditionaryForceLandedDialogAction.setAttackWave(expeditionaryForceLandedMessage.getAttackWave());
            displayExpeditionaryForceLandedDialogAction.setLandedUnits(expeditionaryForceLandedMessage.getLandedUnits());
            displayExpeditionaryForceLandedDialogAction.actionPerformed(null);
        } else if (message instanceof DiplomaticRelationUpdatedMessage) {
            new DisplayDiplomacyDialogAction(freeMarsController).actionPerformed(null);
        } else if (message instanceof WealthGiftSentMessage) {
            new DisplayTreasuryStatisticsDialogAction(freeMarsController).actionPerformed(null);
        } else if (message instanceof ResourceGiftSentMessage) {
            ResourceGiftSentMessage resourceGiftSentMessage = (ResourceGiftSentMessage) message;
            new DisplayColonyDialogAction(freeMarsController, (FreeMarsColony) resourceGiftSentMessage.getToSettlement()).actionPerformed(null);
        }
    }
}
