package org.freemars.ai;

import java.util.List;

import org.freemars.controller.FreeMarsController;
import org.freemars.unit.automater.EngineerAutomater;
import org.freerealm.command.UnitSetAutomaterCommand;
import org.freerealm.unit.FreeRealmUnitType;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class EngineerManager {

    private final FreeMarsController freeMarsController;
    private final AIPlayer aiPlayer;

    public EngineerManager(FreeMarsController freeMarsController, AIPlayer aiPlayer) {
        this.freeMarsController = freeMarsController;
        this.aiPlayer = aiPlayer;
    }

    public void manage() {
        FreeRealmUnitType engineerUnitType = freeMarsController.getFreeMarsModel().getRealm().getUnitTypeManager().getUnitType("Engineer");
        List<Unit> engineers = aiPlayer.getUnitsOfType(engineerUnitType);
        for (Unit engineer : engineers) {
            if (engineer.getAutomater() == null) {
                EngineerAutomater engineerAutomater = new EngineerAutomater();
                engineerAutomater.setFreeMarsController(freeMarsController);
                UnitSetAutomaterCommand unitSetAutomaterCommand = new UnitSetAutomaterCommand(engineer, engineerAutomater);
                freeMarsController.execute(unitSetAutomaterCommand);
            }
        }
    }

}
