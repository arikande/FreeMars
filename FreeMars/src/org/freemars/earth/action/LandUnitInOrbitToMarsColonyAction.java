package org.freemars.earth.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.freemars.controller.FreeMarsController;
import org.freemars.earth.EarthFlightProperty;
import org.freemars.earth.Location;
import org.freemars.earth.order.RelocateUnitOrder;
import org.freemars.earth.ui.OrbitDialog;
import org.freerealm.command.UnitOrderAddCommand;
import org.freerealm.settlement.Settlement;
import org.freerealm.settlement.improvement.SettlementImprovementType;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class LandUnitInOrbitToMarsColonyAction extends AbstractAction {

    private final FreeMarsController freeMarsController;
    private final OrbitDialog orbitDialog;
    private final Unit unit;
    private final Settlement settlement;

    public LandUnitInOrbitToMarsColonyAction(FreeMarsController freeMarsController, OrbitDialog orbitDialog, Unit unit, Settlement settlement) {
        this.freeMarsController = freeMarsController;
        this.orbitDialog = orbitDialog;
        this.unit = unit;
        this.settlement = settlement;
    }

    public void actionPerformed(ActionEvent e) {
        EarthFlightProperty earthFlight = (EarthFlightProperty) unit.getType().getProperty(EarthFlightProperty.NAME);
        SettlementImprovementType starPortImprovementType = freeMarsController.getFreeMarsModel().getRealm().getSettlementImprovementManager().getImprovement("Starport");
        if (settlement.hasImprovementType(starPortImprovementType)) {
            if (earthFlight != null) {
                int turnGiven = freeMarsController.getFreeMarsModel().getNumberOfTurns();
                RelocateUnitOrder relocateUnitOrder = new RelocateUnitOrder(freeMarsController.getFreeMarsModel().getRealm());
                relocateUnitOrder.setFreeMarsController(freeMarsController);
                relocateUnitOrder.setUnit(unit);
//                relocateUnitOrder.setTurnGiven(turnGiven);
                relocateUnitOrder.setSource(Location.MARS_ORBIT);
                relocateUnitOrder.setDestination(Location.MARS);
                relocateUnitOrder.setLandOnColony(settlement);
                freeMarsController.execute(new UnitOrderAddCommand(freeMarsController.getFreeMarsModel().getRealm(), unit, relocateUnitOrder));
                orbitDialog.update();
            }
        }
    }
}
