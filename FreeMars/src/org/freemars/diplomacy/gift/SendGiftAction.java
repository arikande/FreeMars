package org.freemars.diplomacy.gift;

import org.freerealm.command.SendResourceGiftCommand;
import org.freerealm.command.SendWealthGiftCommand;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.freemars.controller.FreeMarsController;
import org.freemars.ui.util.FreeMarsOptionPane;
import org.freerealm.executor.CommandResult;
import org.freerealm.player.Player;
import org.freerealm.resource.Resource;
import org.freerealm.settlement.Settlement;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class SendGiftAction extends AbstractAction {

    private final FreeMarsController freeMarsController;
    private final OfferGiftDialogModel offerGiftDialogModel;
    private final OfferGiftDialog offerGiftDialog;

    public SendGiftAction(FreeMarsController freeMarsController, OfferGiftDialog offerGiftDialog, OfferGiftDialogModel offerGiftDialogModel) {
        super("Send");
        this.freeMarsController = freeMarsController;
        this.offerGiftDialogModel = offerGiftDialogModel;
        this.offerGiftDialog = offerGiftDialog;
    }

    public void actionPerformed(ActionEvent e) {
        int giftType = offerGiftDialogModel.getGiftType();
        switch (giftType) {
            case OfferGiftDialogModel.CREDIT_GIFT_TYPE:
                sendCreditGift();
                break;
            case OfferGiftDialogModel.RESOURCE_GIFT_TYPE:
                sendResourceGift();
                break;
            case OfferGiftDialogModel.UNIT_GIFT_TYPE:
                sendUnitGift();
                break;
        }
        offerGiftDialog.dispose();
    }

    private void sendCreditGift() {
        int creditAmount = offerGiftDialog.getCreditGiftAmountSliderValue();
        CommandResult commandResult = freeMarsController.execute(
                new SendWealthGiftCommand(freeMarsController.getFreeMarsModel().getRealm(), offerGiftDialogModel.getFromPlayer(), offerGiftDialogModel.getToPlayer(), creditAmount)
        );
        if (commandResult.getCode() == CommandResult.RESULT_OK) {
            FreeMarsOptionPane.showMessageDialog(offerGiftDialog, getCreditSentMessage(creditAmount, offerGiftDialogModel.getToPlayer()), "Success");
        }
    }

    private void sendResourceGift() {
        Resource resource = freeMarsController.getFreeMarsModel().getRealm().getResourceManager().getResource(offerGiftDialogModel.getResourceId());
        int resourceAmount = offerGiftDialog.getResourceGiftAmountSliderValue();
        Settlement fromSettlement = offerGiftDialogModel.getSelectedSettlement();
        Settlement toSettlement = getToSettlement(offerGiftDialogModel.getToPlayer());
        if (toSettlement == null) {
            String errorMessage = offerGiftDialogModel.getToPlayer().getName() + " has no colony to receive resources";
            FreeMarsOptionPane.showMessageDialog(offerGiftDialog, errorMessage, "Failed");
        } else if (resource != null && resourceAmount > 0 && fromSettlement != null) {
            int resourceUnitValue = (int) 1.5 * freeMarsController.getFreeMarsModel().getEarth().getEarthBuysAtPrice(resource);
            CommandResult commandResult = freeMarsController.execute(
                    new SendResourceGiftCommand(freeMarsController.getFreeMarsModel().getRealm(), fromSettlement, toSettlement, resource, resourceAmount, resourceUnitValue)
            );
            if (commandResult.getCode() == CommandResult.RESULT_OK) {
                FreeMarsOptionPane.showMessageDialog(offerGiftDialog, getResourceSentMessage(resource, resourceAmount, toSettlement), "Success");
            }
        }
    }

    private void sendUnitGift() {
        Unit unit = offerGiftDialogModel.getSelectedUnit();
        if (unit != null) {
            SendUnitGiftCommand sendUnitGiftCommand
                    = new SendUnitGiftCommand(freeMarsController.getFreeMarsModel(), offerGiftDialogModel.getFromPlayer(), offerGiftDialogModel.getToPlayer(), unit);
            CommandResult commandResult = freeMarsController.execute(sendUnitGiftCommand);
            if (commandResult.getCode() == CommandResult.RESULT_OK) {
                FreeMarsOptionPane.showMessageDialog(offerGiftDialog, getUnitSentMessage(unit, offerGiftDialogModel.getToPlayer()), "Success");
            }

        }
    }

    private String getCreditSentMessage(int creditAmount, Player toPlayer) {
        String currencyUnit = freeMarsController.getFreeMarsModel().getRealm().getProperty("currency_unit");
        StringBuilder message = new StringBuilder();
        message.append("A gift of ");
        message.append(creditAmount);
        message.append(" ");
        message.append(currencyUnit);
        message.append(" sent successfully to ");
        message.append(toPlayer.getName());
        message.append(".\n");
        message.append("They thanked us for our support.");
        return message.toString();
    }

    private String getResourceSentMessage(Resource resource, int resourceAmount, Settlement toSettlement) {
        StringBuilder message = new StringBuilder();
        message.append("A gift of ");
        message.append(resourceAmount);
        message.append(" ");
        message.append(resource.getName().toLowerCase());
        message.append(" sent successfully to ");
        message.append(toSettlement.getPlayer().getNation().getAdjective());
        message.append(" colony of ");
        message.append(toSettlement.getName());
        message.append(".\n");
        message.append("They thanked us for our support.");
        return message.toString();
    }

    private String getUnitSentMessage(Unit unit, Player toPlayer) {
        StringBuilder message = new StringBuilder();
        message.append("Unit \"");
        message.append(unit.getName());
        message.append("\" delivered successfully to ");
        message.append(toPlayer.getName());
        message.append(".\n");
        message.append("They thanked us for our support.");
        return message.toString();
    }

    private Settlement getToSettlement(Player toPlayer) {
        if (toPlayer.getSettlementCount() > 0) {
            return toPlayer.getSettlementsIterator().next();
        } else {
            return null;
        }
    }

}
