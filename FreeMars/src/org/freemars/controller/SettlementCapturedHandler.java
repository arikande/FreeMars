package org.freemars.controller;

import java.util.ArrayList;
import java.util.List;
import org.freemars.controller.handler.PostCommandHandler;
import org.freemars.controller.viewcommand.UpdateCoordinatePaintModelCommand;
import org.freemars.message.SettlementCapturedMessage;
import org.freemars.player.FreeMarsPlayer;
import org.freerealm.executor.CommandResult;
import org.freerealm.map.Coordinate;
import org.freerealm.player.Player;
import org.freerealm.settlement.Settlement;

/**
 *
 * @author Deniz ARIKAN
 */
public class SettlementCapturedHandler implements PostCommandHandler {

    public void handleUpdate(FreeMarsController freeMarsController, CommandResult commandResult) {
        Settlement settlement = (Settlement) commandResult.getParameter("settlement");

        List<Coordinate> coordinates = new ArrayList<Coordinate>();
        coordinates.add(settlement.getCoordinate());
        coordinates.addAll(freeMarsController.getFreeMarsModel().getRealm().getCircleCoordinates(settlement.getCoordinate(), 1));
        coordinates.addAll(freeMarsController.getFreeMarsModel().getRealm().getCircleCoordinates(settlement.getCoordinate(), 2));
        Player humanPlayer = freeMarsController.getFreeMarsModel().getHumanPlayer();
        if (humanPlayer != null) {
            List<Coordinate> updateCoordinates = new ArrayList<Coordinate>();
            for (Coordinate coordinate : coordinates) {
                if (humanPlayer.isCoordinateExplored(coordinate)) {
                    updateCoordinates.add(coordinate);
                }
            }
            freeMarsController.executeViewCommand(new UpdateCoordinatePaintModelCommand(freeMarsController, updateCoordinates, null));
        }

        Player newOwner = (Player) commandResult.getParameter("newOwner");
        Player previousOwner = (Player) commandResult.getParameter("previousOwner");
        if (previousOwner instanceof FreeMarsPlayer) {
            SettlementCapturedMessage settlementCapturedMessage = new SettlementCapturedMessage();
            settlementCapturedMessage.setTurnSent(freeMarsController.getFreeMarsModel().getNumberOfTurns());
            settlementCapturedMessage.setSubject("Colony captured");
            settlementCapturedMessage.setSettlement(settlement);
            settlementCapturedMessage.setNewOwner(newOwner);
            settlementCapturedMessage.setPreviousOwner(previousOwner);
            StringBuilder message = new StringBuilder();
            message.append("Our colony of ");
            message.append(settlement);
            message.append(" has been captured by ");
            message.append(newOwner);
            settlementCapturedMessage.setText(message.toString());
            previousOwner.addMessage(settlementCapturedMessage);
        }
    }
}
