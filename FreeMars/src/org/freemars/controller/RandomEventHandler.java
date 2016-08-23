package org.freemars.controller;

import org.freemars.controller.handler.PostCommandHandler;
import org.freemars.random.event.ColonistsReturningToEarthMessage;
import org.freemars.random.event.CreditDonationMessage;
import org.freemars.random.event.EnergyLossMessage;
import org.freemars.random.event.FertilizerDonationMessage;
import org.freemars.random.event.FireMessage;
import org.freemars.random.event.FoodContaminationMessage;
import org.freemars.random.event.FreeColonistsMessage;
import org.freemars.random.event.HydrogenExplosionMessage;
import org.freemars.random.event.MeteorStrikeMessage;
import org.freemars.random.event.MiningAccidentMessage;
import org.freemars.random.event.OxygenLeakMessage;
import org.freemars.random.event.ShuttleAccidentMessage;
import org.freemars.random.event.TransporterAccidentMessage;
import org.freemars.random.event.WaterLeakMessage;
import org.freerealm.executor.CommandResult;
import org.freerealm.player.Player;
import org.freerealm.settlement.Settlement;

/**
 *
 * @author Deniz ARIKAN
 */
public class RandomEventHandler implements PostCommandHandler {

    public void handleUpdate(FreeMarsController freeMarsController, CommandResult commandResult) {
        Player humanPlayer = freeMarsController.getFreeMarsModel().getHumanPlayer();
        int playerId = Integer.parseInt(commandResult.getParameter("player_id").toString());
        if (humanPlayer != null && humanPlayer.getId() == playerId) {
            String eventName = commandResult.getParameter("event_name").toString();
            if (eventName.equals("METEOR_STRIKE")) {
                Settlement settlement = (Settlement) commandResult.getParameter("settlement");
                int colonistsLost = Integer.parseInt(commandResult.getParameter("colonists_lost").toString());
                MeteorStrikeMessage eventMessage = new MeteorStrikeMessage();
                eventMessage.setTurnSent(freeMarsController.getFreeMarsModel().getNumberOfTurns());
                eventMessage.setSettlement(settlement);
                eventMessage.setColonistsLost(colonistsLost);
                humanPlayer.addMessage(eventMessage);
            } else if (eventName.equals("OXYGEN_LEAK")) {
                Settlement settlement = (Settlement) commandResult.getParameter("settlement");
                int colonistsLost = Integer.parseInt(commandResult.getParameter("colonists_lost").toString());
                OxygenLeakMessage eventMessage = new OxygenLeakMessage();
                eventMessage.setTurnSent(freeMarsController.getFreeMarsModel().getNumberOfTurns());
                eventMessage.setSettlement(settlement);
                eventMessage.setColonistsLost(colonistsLost);
                humanPlayer.addMessage(eventMessage);
            } else if (eventName.equals("HYDROGEN_EXPLOSION")) {
                Settlement settlement = (Settlement) commandResult.getParameter("settlement");
                int colonistsLost = Integer.parseInt(commandResult.getParameter("colonists_lost").toString());
                HydrogenExplosionMessage eventMessage = new HydrogenExplosionMessage();
                eventMessage.setTurnSent(freeMarsController.getFreeMarsModel().getNumberOfTurns());
                eventMessage.setSettlement(settlement);
                eventMessage.setColonistsLost(colonistsLost);
                humanPlayer.addMessage(eventMessage);
            } else if (eventName.equals("FIRE")) {
                Settlement settlement = (Settlement) commandResult.getParameter("settlement");
                int colonistsLost = Integer.parseInt(commandResult.getParameter("colonists_lost").toString());
                FireMessage eventMessage = new FireMessage();
                eventMessage.setTurnSent(freeMarsController.getFreeMarsModel().getNumberOfTurns());
                eventMessage.setSettlement(settlement);
                eventMessage.setColonistsLost(colonistsLost);
                humanPlayer.addMessage(eventMessage);
            } else if (eventName.equals("MINING_ACCIDENT")) {
                Settlement settlement = (Settlement) commandResult.getParameter("settlement");
                int colonistsLost = Integer.parseInt(commandResult.getParameter("colonists_lost").toString());
                MiningAccidentMessage eventMessage = new MiningAccidentMessage();
                eventMessage.setTurnSent(freeMarsController.getFreeMarsModel().getNumberOfTurns());
                eventMessage.setSettlement(settlement);
                eventMessage.setColonistsLost(colonistsLost);
                humanPlayer.addMessage(eventMessage);
            } else if (eventName.equals("TRANSPORTER_ACCIDENT")) {
                Settlement settlement = (Settlement) commandResult.getParameter("settlement");
                int colonistsLost = Integer.parseInt(commandResult.getParameter("colonists_lost").toString());
                TransporterAccidentMessage eventMessage = new TransporterAccidentMessage();
                eventMessage.setTurnSent(freeMarsController.getFreeMarsModel().getNumberOfTurns());
                eventMessage.setSettlement(settlement);
                eventMessage.setColonistsLost(colonistsLost);
                humanPlayer.addMessage(eventMessage);
            } else if (eventName.equals("SHUTTLE_ACCIDENT")) {
                Settlement settlement = (Settlement) commandResult.getParameter("settlement");
                int colonistsLost = Integer.parseInt(commandResult.getParameter("colonists_lost").toString());
                ShuttleAccidentMessage eventMessage = new ShuttleAccidentMessage();
                eventMessage.setTurnSent(freeMarsController.getFreeMarsModel().getNumberOfTurns());
                eventMessage.setSettlement(settlement);
                eventMessage.setColonistsLost(colonistsLost);
                humanPlayer.addMessage(eventMessage);
            } else if (eventName.equals("COLONISTS_RETURNING_TO_EARTH")) {
                Settlement settlement = (Settlement) commandResult.getParameter("settlement");
                int leavingColonists = Integer.parseInt(commandResult.getParameter("leaving_colonists").toString());
                ColonistsReturningToEarthMessage eventMessage = new ColonistsReturningToEarthMessage();
                eventMessage.setTurnSent(freeMarsController.getFreeMarsModel().getNumberOfTurns());
                eventMessage.setSettlement(settlement);
                eventMessage.setLeavingColonists(leavingColonists);
                humanPlayer.addMessage(eventMessage);
            } else if (eventName.equals("FREE_COLONISTS")) {
                Settlement settlement = (Settlement) commandResult.getParameter("settlement");
                int newColonists = Integer.parseInt(commandResult.getParameter("new_colonists").toString());
                FreeColonistsMessage eventMessage = new FreeColonistsMessage();
                eventMessage.setTurnSent(freeMarsController.getFreeMarsModel().getNumberOfTurns());
                eventMessage.setSettlement(settlement);
                eventMessage.setNewColonists(newColonists);
                humanPlayer.addMessage(eventMessage);
            } else if (eventName.equals("CREDIT_DONATION")) {
                int creditsDonated = Integer.parseInt(commandResult.getParameter("credits_donated").toString());
                CreditDonationMessage eventMessage = new CreditDonationMessage();
                eventMessage.setTurnSent(freeMarsController.getFreeMarsModel().getNumberOfTurns());
                eventMessage.setCreditsDonated(creditsDonated);
                humanPlayer.addMessage(eventMessage);
            } else if (eventName.equals("FERTILIZER_DONATION")) {
                Settlement settlement = (Settlement) commandResult.getParameter("settlement");
                int fertilizerAmountDonated = Integer.parseInt(commandResult.getParameter("fertilizer_amount_donated").toString());
                FertilizerDonationMessage eventMessage = new FertilizerDonationMessage();
                eventMessage.setTurnSent(freeMarsController.getFreeMarsModel().getNumberOfTurns());
                eventMessage.setFertilizerAmountDonated(fertilizerAmountDonated);
                eventMessage.setSettlement(settlement);
                humanPlayer.addMessage(eventMessage);
            } else if (eventName.equals("WATER_LEAK")) {
                Settlement settlement = (Settlement) commandResult.getParameter("settlement");
                int amountLost = Integer.parseInt(commandResult.getParameter("amount_lost").toString());
                WaterLeakMessage eventMessage = new WaterLeakMessage();
                eventMessage.setTurnSent(freeMarsController.getFreeMarsModel().getNumberOfTurns());
                eventMessage.setAmountLost(amountLost);
                eventMessage.setSettlement(settlement);
                humanPlayer.addMessage(eventMessage);
            } else if (eventName.equals("FOOD_CONTAMINATION")) {
                Settlement settlement = (Settlement) commandResult.getParameter("settlement");
                int amountLost = Integer.parseInt(commandResult.getParameter("amount_lost").toString());
                FoodContaminationMessage eventMessage = new FoodContaminationMessage();
                eventMessage.setTurnSent(freeMarsController.getFreeMarsModel().getNumberOfTurns());
                eventMessage.setAmountLost(amountLost);
                eventMessage.setSettlement(settlement);
                humanPlayer.addMessage(eventMessage);
            } else if (eventName.equals("ENERGY_LOSS")) {
                Settlement settlement = (Settlement) commandResult.getParameter("settlement");
                int amountLost = Integer.parseInt(commandResult.getParameter("amount_lost").toString());
                EnergyLossMessage eventMessage = new EnergyLossMessage();
                eventMessage.setTurnSent(freeMarsController.getFreeMarsModel().getNumberOfTurns());
                eventMessage.setAmountLost(amountLost);
                eventMessage.setSettlement(settlement);
                humanPlayer.addMessage(eventMessage);
            }
        }
    }
}
