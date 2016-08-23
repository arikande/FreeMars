package org.freemars.earth.command;

import org.freemars.earth.Earth;
import org.freemars.earth.Location;
import org.freerealm.command.AbstractCommand;
import org.freerealm.executor.CommandResult;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class RelocateUnitCommand extends AbstractCommand {

    private final Earth earthFlightModel;
    private final Unit unit;
    private final Location location;

    public RelocateUnitCommand(Earth earthFlightModel, Unit unit, Location location) {
        this.earthFlightModel = earthFlightModel;
        this.unit = unit;
        this.location = location;
    }

    public CommandResult execute() {
        earthFlightModel.addUnitLocation(unit, location);
        CommandResult commandResult = new CommandResult(CommandResult.RESULT_OK, "");
        commandResult.putParameter("unit", unit);
        commandResult.putParameter("location", location);
        return commandResult;
    }
}
