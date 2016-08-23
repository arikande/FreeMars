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
import org.freerealm.settlement.Settlement;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class SendUnitToMarsColonyAction extends AbstractAction {

    private final FreeMarsController freeMarsController;
    private final EarthDialog earthDialog;
    private final Settlement settlement;

    public SendUnitToMarsColonyAction(FreeMarsController freeMarsController, EarthDialog earthDialog, Settlement settlement) {
        this.freeMarsController = freeMarsController;
        this.earthDialog = earthDialog;
        this.settlement = settlement;
    }

    public void actionPerformed(ActionEvent e) {
        Unit unit = freeMarsController.getFreeMarsModel().getEarth().getEarthDialogSelectedUnit();
        EarthFlightProperty earthFlight = (EarthFlightProperty) unit.getType().getProperty(EarthFlightProperty.NAME);
        if (earthFlight != null) {
            int turnGiven = 0;
            RelocateUnitOrder relocateUnitOrder = new RelocateUnitOrder(freeMarsController.getFreeMarsModel().getRealm());
            relocateUnitOrder.setFreeMarsController(freeMarsController);
            relocateUnitOrder.setUnit(unit);
            relocateUnitOrder.setTurnGiven(turnGiven);
            relocateUnitOrder.setSource(Location.EARTH);
            relocateUnitOrder.setDestination(Location.MARS);
            relocateUnitOrder.setLandOnColony(settlement);
            freeMarsController.execute(new UnitOrderAddCommand(freeMarsController.getFreeMarsModel().getRealm(), unit, relocateUnitOrder));
            freeMarsController.getFreeMarsModel().getEarth().addUnitLocation(unit, Location.TRAVELING_TO_MARS);
            earthDialog.addUnitInfoText(unit.getName() + " is sent to Martian colony of " + settlement.getName() + "\n\n");
            earthDialog.update(Earth.UNIT_RELOCATION_UPDATE);
        }
    }
}
