package org.freemars.controller;

import org.apache.log4j.Logger;
import org.freemars.controller.handler.PostCommandHandler;
import org.freerealm.command.SendWealthGiftCommand;
import org.freerealm.executor.CommandResult;
import org.freerealm.player.Player;
import org.freerealm.player.WealthGiftSentMessage;

/**
 *
 * @author Deniz ARIKAN
 */
public class WealthGiftSentHandler implements PostCommandHandler {

    private static final Logger logger = Logger.getLogger(WealthGiftSentHandler.class);

    public void handleUpdate(FreeMarsController freeMarsController, CommandResult commandResult) {
        logger.debug("WealthGiftSentHandler handling command result with update type " + commandResult.getUpdateType() + ".");
        Player fromPlayer = (Player) commandResult.getParameter(SendWealthGiftCommand.PARAMETER_FROM_PLAYER);
        Player toPlayer = (Player) commandResult.getParameter(SendWealthGiftCommand.PARAMETER_TO_PLAYER);
        int amount = (Integer) commandResult.getParameter(SendWealthGiftCommand.PARAMETER_AMOUNT);
        if (logger.isTraceEnabled()) {
            StringBuilder log = new StringBuilder();
            log.append("Wealth gift sent from player ");
            log.append(fromPlayer.getName());
            log.append(" to player ");
            log.append(toPlayer.getName());
            logger.trace(log);
            log.append(".");
        }

        if (freeMarsController.getFreeMarsModel().getHumanPlayer().equals(toPlayer)) {
            String currencyUnit = freeMarsController.getFreeMarsModel().getRealm().getProperty("currency_unit");
            WealthGiftSentMessage wealthGiftSentMessage = new WealthGiftSentMessage();
            StringBuilder messageText = new StringBuilder();
            messageText.append("We have received a gift of ");
            messageText.append(amount);
            messageText.append(" ");
            messageText.append(currencyUnit);
            messageText.append(" from the ");
            messageText.append(fromPlayer.getName());
            messageText.append(".");
            wealthGiftSentMessage.setText(messageText.toString());
            toPlayer.addMessage(wealthGiftSentMessage);
        }
    }

}
