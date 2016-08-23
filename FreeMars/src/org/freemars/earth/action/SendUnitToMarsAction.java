package org.freemars.earth.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.freemars.controller.FreeMarsController;
import org.freemars.earth.Earth;
import org.freemars.earth.EarthFlightProperty;
import org.freemars.earth.Location;
import org.freemars.earth.order.RelocateUnitOrder;
import org.freemars.earth.ui.EarthDialog;
import org.freerealm.command.UnitOrderAddCommand;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class SendUnitToMarsAction extends AbstractAction {

    private final FreeMarsController freeMarsController;
    private final EarthDialog earthDialog;
    private final Unit unit;

    public SendUnitToMarsAction(FreeMarsController freeMarsController, EarthDialog earthDialog, Unit unit) {
        this.freeMarsController = freeMarsController;
        this.earthDialog = earthDialog;
        this.unit = unit;
    }

    public void actionPerformed(ActionEvent e) {
        EarthFlightProperty earthFlight = (EarthFlightProperty) unit.getType().getProperty(EarthFlightProperty.NAME);
        if (earthFlight != null) {
            RelocateUnitOrder relocateUnitOrder = new RelocateUnitOrder(freeMarsController.getFreeMarsModel().getRealm());
            relocateUnitOrder.setFreeMarsController(freeMarsController);
            relocateUnitOrder.setUnit(unit);
            relocateUnitOrder.setSource(Location.EARTH);
            relocateUnitOrder.setDestination(Location.MARS_ORBIT);
            freeMarsController.execute(new UnitOrderAddCommand(freeMarsController.getFreeMarsModel().getRealm(), unit, relocateUnitOrder));
            freeMarsController.getFreeMarsModel().getEarth().addUnitLocation(unit, Location.TRAVELING_TO_MARS);
            earthDialog.addUnitInfoText(unit.getName() + " is sent to Mars\n\n");
            earthDialog.update(Earth.UNIT_RELOCATION_UPDATE);
        }
    }
}
