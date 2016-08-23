package org.freemars.controller;

import org.apache.log4j.Logger;
import org.freemars.controller.handler.PostCommandHandler;
import org.freerealm.command.SendResourceGiftCommand;
import org.freerealm.executor.CommandResult;
import org.freerealm.player.Player;
import org.freerealm.player.ResourceGiftSentMessage;
import org.freerealm.resource.Resource;
import org.freerealm.settlement.Settlement;

/**
 *
 * @author Deniz ARIKAN
 */
public class ResourceGiftSentHandler implements PostCommandHandler {

    private static final Logger logger = Logger.getLogger(ResourceGiftSentHandler.class);

    public void handleUpdate(FreeMarsController freeMarsController, CommandResult commandResult) {
        logger.debug("ResourceGiftSentHandler handling command result with update type " + commandResult.getUpdateType() + ".");
        Settlement fromSettlement = (Settlement) commandResult.getParameter(SendResourceGiftCommand.PARAMETER_FROM_SETTLEMENT);
        Settlement toSettlement = (Settlement) commandResult.getParameter(SendResourceGiftCommand.PARAMETER_TO_SETTLEMENT);
        Resource resource = (Resource) commandResult.getParameter(SendResourceGiftCommand.PARAMETER_RESOURCE);
        int amount = (Integer) commandResult.getParameter(SendResourceGiftCommand.PARAMETER_AMOUNT);
        if (logger.isTraceEnabled()) {
            StringBuilder log = new StringBuilder();
            log.append("Resource gift of ");
            log.append(amount);
            log.append(" ");
            log.append(resource.getName());
            log.append(" sent from colony ");
            log.append(fromSettlement.getName());
            log.append(" to colony ");
            log.append(toSettlement.getName());
            log.append(".");
            logger.trace(log);
        }
        Player fromPlayer = fromSettlement.getPlayer();
        Player toPlayer = toSettlement.getPlayer();
        if (freeMarsController.getFreeMarsModel().getHumanPlayer().equals(toPlayer)) {
            ResourceGiftSentMessage resourceGiftSentMessage = new ResourceGiftSentMessage();
            resourceGiftSentMessage.setResource(resource);
            resourceGiftSentMessage.setToSettlement(toSettlement);
            StringBuilder messageText = new StringBuilder();
            messageText.append(toSettlement.getName());
            messageText.append(" received a gift of ");
            messageText.append(amount);
            messageText.append(" ");
            messageText.append(resource.getName());
            messageText.append(" from the ");
            messageText.append(fromPlayer.getNation().getAdjective());
            messageText.append(" colony of ");
            messageText.append(fromSettlement.getName());
            messageText.append(".");
            resourceGiftSentMessage.setText(messageText.toString());
            toPlayer.addMessage(resourceGiftSentMessage);
        }
    }

}
