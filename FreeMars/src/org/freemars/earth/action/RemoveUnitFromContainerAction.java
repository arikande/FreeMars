package org.freemars.earth.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.freemars.controller.FreeMarsController;
import org.freemars.earth.Earth;
import org.freemars.earth.Location;
import org.freemars.earth.ui.EarthDialog;
import org.freerealm.command.RemoveUnitFromContainerCommand;
import org.freerealm.executor.CommandResult;
import org.freerealm.unit.Unit;
import org.freerealm.unit.UnitContainer;

/**
 *
 * @author Deniz ARIKAN
 */
public class RemoveUnitFromContainerAction extends AbstractAction {

    private final FreeMarsController freeMarsController;
    private final EarthDialog earthDialog;
    private final UnitContainer unitContainer;
    private final Unit unit;

    public RemoveUnitFromContainerAction(FreeMarsController freeMarsController, EarthDialog earthDialog, UnitContainer unitContainer, Unit unit) {
        this.earthDialog = earthDialog;
        this.freeMarsController = freeMarsController;
        this.unitContainer = unitContainer;
        this.unit = unit;
    }

    public void actionPerformed(ActionEvent e) {
        CommandResult commandResult = freeMarsController.execute(new RemoveUnitFromContainerCommand(unitContainer, unit));
        if (commandResult.getCode() == CommandResult.RESULT_OK) {
            freeMarsController.getFreeMarsModel().getEarth().addUnitLocation(unit, Location.EARTH);
            earthDialog.addUnitInfoText("\"" + unitContainer.getName() + "\" has unloaded \"" + unit.getName() + "\"\n\n");
        }
        earthDialog.update(Earth.UNIT_RELOCATION_UPDATE);
    }
}
