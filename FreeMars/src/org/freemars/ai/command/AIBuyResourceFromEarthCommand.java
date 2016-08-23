package org.freemars.ai.command;

import org.apache.log4j.Logger;
import org.freemars.ai.AIPlayer;
import org.freemars.earth.Earth;
import org.freemars.earth.command.BuyResourceFromEarthCommand;
import org.freerealm.command.AbstractCommand;
import org.freerealm.executor.CommandResult;
import org.freerealm.resource.Resource;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class AIBuyResourceFromEarthCommand extends AbstractCommand {

    private static final Logger logger = Logger.getLogger(AIBuyResourceFromEarthCommand.class);

    private final Earth earthFlightModel;
    private final Unit spaceship;
    private final Resource resource;
    private int amount = 0;

    public AIBuyResourceFromEarthCommand(Earth earthFlightModel, Unit spaceship, Resource resource, int amount) {
        this.earthFlightModel = earthFlightModel;
        this.spaceship = spaceship;
        this.resource = resource;
        this.amount = amount;
    }

    public CommandResult execute() {
        if (!(spaceship.getPlayer() instanceof AIPlayer)) {
            return new CommandResult(CommandResult.RESULT_ERROR, "Spaceship owner is not an AI player", CommandResult.NO_UPDATE);
        }
        if (amount <= 0) {
            return new CommandResult(CommandResult.RESULT_ERROR, "Resource not bought. Amount is less than or equal to zero.", CommandResult.NO_UPDATE);
        }
        AIPlayer aiPlayer = (AIPlayer) spaceship.getPlayer();
        int resourceUnitPrice = earthFlightModel.getEarthSellsAtPrice(resource);
        int resourceTotalPrice = resourceUnitPrice * amount;
        if (aiPlayer.getWealth() > aiPlayer.getReserveWealth() + resourceTotalPrice) {
            BuyResourceFromEarthCommand buyResourceFromEarthCommand = new BuyResourceFromEarthCommand(earthFlightModel, spaceship, resource, amount);
            CommandResult commandResult = getExecutor().execute(buyResourceFromEarthCommand);
            if (commandResult.getCode() == CommandResult.RESULT_OK) {
                StringBuilder log = new StringBuilder();
                log.append("\"").append(spaceship.getName()).append("\" purchased ").append(amount);
                log.append(" ").append(resource.getName()).append(" paying ").append(resourceUnitPrice);
                log.append(" ").append("credits per unit");
                log.append(" for a total of ");
                log.append(resourceTotalPrice).append(" credits.");
                logger.info(log.toString());
            } else if (commandResult.getCode() == CommandResult.RESULT_ERROR) {
                logger.error("Error while executing BuyResourceFromEarthCommand in AIBuyResourceFromEarthCommand.");
                logger.error("Error message : " + commandResult.getText());
            }
            return commandResult;
        }
        return new CommandResult(CommandResult.RESULT_ERROR, "Resource not bought. Player reserving wealth.", CommandResult.NO_UPDATE);
    }

}
