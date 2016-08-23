package org.freemars.controller;

import org.apache.log4j.Logger;
import org.freemars.controller.handler.PostCommandHandler;
import org.freemars.ui.util.FreeMarsOptionPane;
import org.freerealm.diplomacy.PlayerRelation;
import org.freerealm.executor.CommandResult;
import org.freerealm.player.DiplomaticRelationUpdatedMessage;
import org.freerealm.player.Player;

/**
 *
 * @author Deniz ARIKAN
 */
public class DiplomaticStatusUpdatedHandler implements PostCommandHandler {

    private static final Logger logger = Logger.getLogger(DiplomaticStatusUpdatedHandler.class);

    public void handleUpdate(FreeMarsController freeMarsController, CommandResult commandResult) {
        Player player1 = (Player) commandResult.getParameter("player1");
        Player player2 = (Player) commandResult.getParameter("player2");
        int oldStatus = (Integer) commandResult.getParameter("old_status");
        String oldStatusName = (String) commandResult.getParameter("old_status_name");
        int newStatus = (Integer) commandResult.getParameter("new_status");
        String newStatusName = (String) commandResult.getParameter("new_status_name");
        if (logger.isTraceEnabled()) {
            StringBuilder log = new StringBuilder();
            log.append("DiplomaticStatusUpdatedHandler handling command result with update type ");
            log.append(commandResult.getUpdateType());
            log.append(".");
            logger.trace(log);
            log.setLength(0);
            log.append("Player : ");
            log.append(player1.getName());
            log.append(", Target player : ");
            log.append(player2.getName());
            log.append(", Old status : ");
            log.append(oldStatus);
            log.append(" - ");
            log.append(oldStatusName);
            log.append(", New status : ");
            log.append(newStatus);
            log.append(" - ");
            log.append(newStatusName);
            logger.trace(log);
        }
        if (isHumanPlayerDiplomaticStatusUpdated(freeMarsController, player1, player2)) {
            Player otherPlayer = (player1.equals(freeMarsController.getFreeMarsModel().getHumanPlayer()) ? player2 : player1);
            if (oldStatus == PlayerRelation.NO_CONTACT && newStatus == PlayerRelation.AT_PEACE) {
                if (freeMarsController.getFreeMarsModel().isHumanPlayerActive()) {
                    StringBuilder message = new StringBuilder();
                    message.append("We have contacted the ");
                    message.append(otherPlayer.getName());
                    FreeMarsOptionPane.showMessageDialog(freeMarsController.getCurrentFrame(), message, "Contact");
                } else {
                    DiplomaticRelationUpdatedMessage diplomaticRelationUpdatedMessage = new DiplomaticRelationUpdatedMessage();
                    diplomaticRelationUpdatedMessage.setUpdatedWithPlayer(otherPlayer);
                    diplomaticRelationUpdatedMessage.setText("We have been contacted by " + otherPlayer.getName());
                    freeMarsController.getFreeMarsModel().getHumanPlayer().addMessage(diplomaticRelationUpdatedMessage);
                }
            } else if (newStatus == PlayerRelation.AT_WAR) {
                StringBuilder message = new StringBuilder();
                message.append("We have declared war on the ");
                message.append(otherPlayer.getName());
                message.append(".");
                FreeMarsOptionPane.showMessageDialog(freeMarsController.getCurrentFrame(), message);
            }
        }

    }

    private boolean isHumanPlayerDiplomaticStatusUpdated(FreeMarsController freeMarsController, Player player1, Player player2) {
        Player humanPlayer = freeMarsController.getFreeMarsModel().getHumanPlayer();
        return humanPlayer.equals(player1) || humanPlayer.equals(player2);
    }
}
