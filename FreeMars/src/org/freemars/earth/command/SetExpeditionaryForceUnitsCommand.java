package org.freemars.earth.command;

import java.util.HashMap;
import java.util.Iterator;

import org.freemars.ai.ExpeditionaryForcePlayer;
import org.freemars.controller.FreeMarsController;
import org.freemars.model.FreeMarsModel;
import org.freerealm.command.AbstractCommand;
import org.freerealm.command.AddUnitCommand;
import org.freerealm.executor.CommandResult;
import org.freerealm.unit.FreeRealmUnitType;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class SetExpeditionaryForceUnitsCommand extends AbstractCommand {

    private final FreeMarsController freeMarsController;
    private final ExpeditionaryForcePlayer expeditionaryForcePlayer;
    private final HashMap<FreeRealmUnitType, Integer> units;

    public SetExpeditionaryForceUnitsCommand(FreeMarsController freeMarsController, ExpeditionaryForcePlayer expeditionaryForcePlayer, HashMap<FreeRealmUnitType, Integer> units) {
        this.freeMarsController = freeMarsController;
        this.expeditionaryForcePlayer = expeditionaryForcePlayer;
        this.units = units;
    }

    public CommandResult execute() {
        HashMap<FreeRealmUnitType, Integer> updatedUnits = new HashMap<FreeRealmUnitType, Integer>();
        Iterator<FreeRealmUnitType> iterator = units.keySet().iterator();
        while (iterator.hasNext()) {
            FreeRealmUnitType unitType = iterator.next();
            int count = units.get(unitType);
            int currentCount = expeditionaryForcePlayer.getUnitsOfType(unitType).size();
            if (count > currentCount) {
                for (int i = 0; i < count - currentCount; i++) {
                    Unit unit = new Unit(freeMarsController.getFreeMarsModel().getRealm(), unitType, null, expeditionaryForcePlayer);
                    freeMarsController.execute(new AddUnitCommand(freeMarsController.getFreeMarsModel().getRealm(), expeditionaryForcePlayer, unit, Unit.UNIT_SUSPENDED));
                }
                updatedUnits.put(unitType, count - currentCount);
            } else if (count < currentCount) {
            }
        }
        CommandResult commandResult = null;
        if (updatedUnits.size() > 0) {
            commandResult = new CommandResult(CommandResult.RESULT_OK, "", FreeMarsModel.EXPEDITIONARY_FORCE_CHANGED_UPDATE);
            commandResult.putParameter("target_player", freeMarsController.getFreeMarsModel().getRealm().getPlayerManager().getPlayer(expeditionaryForcePlayer.getTargetPlayerId()));
            commandResult.putParameter("updated_units", updatedUnits);
        } else {
            commandResult = new CommandResult(CommandResult.RESULT_OK, "");
        }
        return commandResult;
    }
}
