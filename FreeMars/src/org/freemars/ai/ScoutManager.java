package org.freemars.ai;

import java.util.List;

import org.freemars.controller.FreeMarsController;
import org.freemars.unit.automater.ScoutAutomater;
import org.freerealm.command.UnitSetAutomaterCommand;
import org.freerealm.unit.FreeRealmUnitType;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class ScoutManager {

    private final FreeMarsController freeMarsController;
    private final AIPlayer aiPlayer;

    public ScoutManager(FreeMarsController freeMarsController, AIPlayer aiPlayer) {
        this.freeMarsController = freeMarsController;
        this.aiPlayer = aiPlayer;
    }

    public void manage() {
        FreeRealmUnitType scoutUnitType = freeMarsController.getFreeMarsModel().getRealm().getUnitTypeManager().getUnitType("Scout");
        List<Unit> scouts = aiPlayer.getUnitsOfType(scoutUnitType);
        for (Unit scout : scouts) {
            if (scout.getAutomater() == null) {
                ScoutAutomater scoutAutomater = new ScoutAutomater();
                scoutAutomater.setFreeMarsController(freeMarsController);
                UnitSetAutomaterCommand unitSetAutomaterCommand = new UnitSetAutomaterCommand(scout, scoutAutomater);
                freeMarsController.execute(unitSetAutomaterCommand);
            }
        }
    }
}
