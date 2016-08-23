package org.freemars.earth.command;

import org.freemars.earth.Earth;
import org.freemars.player.FreeMarsPlayer;
import org.freemars.player.ResourceTradeData;
import org.freerealm.command.AbstractCommand;
import org.freerealm.command.ResourceAddCommand;
import org.freerealm.command.ResourceRemoveCommand;
import org.freerealm.command.WealthRemoveCommand;
import org.freerealm.executor.CommandResult;
import org.freerealm.resource.Resource;
import org.freerealm.unit.Unit;

/**
 * Command class to buy resources from Earth.
 *
 * @author Deniz ARIKAN
 */
public class BuyResourceFromEarthCommand extends AbstractCommand {

    private final Earth earthFlightModel;
    private final Unit spaceShip;
    private final Resource resource;
    private int amount = 0;

    /**
     * Constructs a BuyResourceFromEarthCommand using player, amount
     *
     * @param amount Amount to remove
     */
    public BuyResourceFromEarthCommand(Earth earthFlightModel, Unit spaceShip, Resource resource, int amount) {
        this.earthFlightModel = earthFlightModel;
        this.spaceShip = spaceShip;
        this.resource = resource;
        this.amount = amount;
    }

    /**
     * Executes command to ...
     *
     * @return CommandResult
     */
    public CommandResult execute() {
        CommandResult result = getExecutor().execute(new ResourceAddCommand(spaceShip, resource, amount));
        if (result.getCode() == CommandResult.RESULT_OK) {
            FreeMarsPlayer player = (FreeMarsPlayer) spaceShip.getPlayer();
            int totalPrice = earthFlightModel.getEarthSellsAtPrice(resource) * amount;
            result = getExecutor().execute(new WealthRemoveCommand(player, totalPrice));
            if (result.getCode() == CommandResult.RESULT_OK) {
                earthFlightModel.removeResource(resource, amount);
                ResourceTradeData resourceTradeData = player.getResourceTradeData(resource.getId());
                resourceTradeData.setQuantityImported(resourceTradeData.getQuantityImported() + amount);
                resourceTradeData.setExpenditure(resourceTradeData.getExpenditure() + totalPrice);
                return new CommandResult(CommandResult.RESULT_OK, "");
            } else {
                getExecutor().execute(new ResourceRemoveCommand(spaceShip, resource, amount));
                return new CommandResult(CommandResult.RESULT_ERROR, "");
            }
        }
        return new CommandResult(CommandResult.RESULT_ERROR, "");
    }
}
