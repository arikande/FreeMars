package org.freemars.controller;

import org.freemars.controller.handler.PostCommandHandler;
import org.freemars.message.EarthTaxRateChangedMessage;
import org.freerealm.executor.CommandResult;

/**
 *
 * @author Deniz ARIKAN
 */
public class EarthTaxRateChangedHandler implements PostCommandHandler {

    public void handleUpdate(FreeMarsController controller, CommandResult commandResult) {
        EarthTaxRateChangedMessage earthTaxRateChangedMessage = new EarthTaxRateChangedMessage();
        earthTaxRateChangedMessage.setTurnSent(controller.getFreeMarsModel().getNumberOfTurns());
        earthTaxRateChangedMessage.setSubject("Earth tax rate changed");
        byte previousTaxRate = (Byte) commandResult.getParameter("previousTaxRate");
        byte newTaxRate = (Byte) commandResult.getParameter("newTaxRate");
        StringBuilder message = new StringBuilder();
        if (newTaxRate > previousTaxRate) {
            message.append("Earth tax rate increased to ");
        } else {
            message.append("Earth tax rate decreased to ");
        }
        message.append(newTaxRate);
        message.append("%");
        earthTaxRateChangedMessage.setText(message.toString());
        controller.getFreeMarsModel().getActivePlayer().addMessage(earthTaxRateChangedMessage);
    }
}
