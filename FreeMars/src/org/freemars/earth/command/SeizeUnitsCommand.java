package org.freemars.earth.command;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.freemars.controller.FreeMarsController;
import org.freemars.earth.Location;
import org.freemars.model.FreeMarsModel;
import org.freemars.player.FreeMarsPlayer;
import org.freerealm.command.AbstractCommand;
import org.freerealm.command.RemoveUnitCommand;
import org.freerealm.executor.CommandResult;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class SeizeUnitsCommand extends AbstractCommand {

    private final FreeMarsController freeMarsController;
    private final FreeMarsPlayer freeMarsPlayer;
    private final String[] seizedUnitTypeNames = {"Colonizer", "Transporter", "Mech"};

    public SeizeUnitsCommand(FreeMarsController freeMarsController, FreeMarsPlayer freeMarsPlayer) {
        this.freeMarsController = freeMarsController;
        this.freeMarsPlayer = freeMarsPlayer;
    }

    public CommandResult execute() {
        List<Unit> seizedUnits = new ArrayList<Unit>();
        Iterator<Unit> iterator = freeMarsPlayer.getUnitsIterator();
        while (iterator.hasNext()) {
            Unit unit = iterator.next();
            if (willUnitBeSeized(unit)) {
                seizedUnits.add(unit);
            }
        }
        for (Unit seizedUnit : seizedUnits) {
            freeMarsController.getFreeMarsModel().getEarth().removeUnitLocation(seizedUnit);
            RemoveUnitCommand removeUnitCommand = new RemoveUnitCommand(freeMarsController.getFreeMarsModel().getRealm(), freeMarsPlayer, seizedUnit);
            freeMarsController.execute(removeUnitCommand);
        }
        CommandResult commandResult;
        if (seizedUnits.size() > 0) {
            commandResult = new CommandResult(CommandResult.RESULT_OK, "", FreeMarsModel.UNITS_SEIZED_UPDATE);
            commandResult.putParameter("player", freeMarsPlayer);
            commandResult.putParameter("seized_units", seizedUnits);
        } else {
            commandResult = new CommandResult(CommandResult.RESULT_OK, "", CommandResult.NO_UPDATE);
        }
        return commandResult;
    }

    private boolean willUnitBeSeized(Unit unit) {
        String unitTypeName = unit.getType().getName();
        for (String seizedUnitTypeName : seizedUnitTypeNames) {
            if (unitTypeName.equals(seizedUnitTypeName)) {
                Location unitLocation = freeMarsController.getFreeMarsModel().getEarth().getUnitLocation(unit);
                if (unitLocation != null && (unitLocation.equals(Location.EARTH) || unitLocation.equals(Location.TRAVELING_TO_EARTH))) {
                    return true;
                }
            }
        }
        return false;
    }
}
