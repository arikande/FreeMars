package org.freemars.earth.command;

import org.freemars.earth.Location;
import org.freemars.model.FreeMarsModel;
import org.freerealm.command.AbstractCommand;
import org.freerealm.command.AddUnitCommand;
import org.freerealm.command.WealthRemoveCommand;
import org.freerealm.executor.CommandResult;
import org.freerealm.player.Player;
import org.freerealm.unit.FreeRealmUnitType;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class BuyUnitFromEarthCommand extends AbstractCommand {

    private final FreeMarsModel freeMarsModel;
    private final Player player;
    private final FreeRealmUnitType unitType;

    public BuyUnitFromEarthCommand(FreeMarsModel freeMarsModel, Player player, FreeRealmUnitType unitType) {
        this.freeMarsModel = freeMarsModel;
        this.player = player;
        this.unitType = unitType;
    }

    public CommandResult execute() {
        int price = freeMarsModel.getEarth().getEarthSellsAtPrice(unitType);
        CommandResult commandResult;
        if (player.getWealth() >= price) {
            getExecutor().execute(new WealthRemoveCommand(player, price));
            Unit unit = new Unit(freeMarsModel.getRealm(), unitType, null, player);
            getExecutor().execute(new AddUnitCommand(freeMarsModel.getRealm(), player, unit, Unit.UNIT_SUSPENDED));
            freeMarsModel.getEarth().addUnitLocation(unit, Location.EARTH);
            commandResult = new CommandResult(CommandResult.RESULT_OK, "");
        } else {
            commandResult = new CommandResult(CommandResult.RESULT_ERROR, "Not enough wealth");
        }
        return commandResult;
    }

}
