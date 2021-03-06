package org.freemars.unit.manager;

import java.awt.event.ActionEvent;
import org.freemars.controller.FreeMarsController;
import org.freemars.controller.action.order.OrderFortifyAction;
import org.freemars.unit.UnitsTableHelper;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class FortifyUnitFromManagerAction extends OrderFortifyAction {

    private final FreeMarsController freeMarsController;
    private final UnitManagerDialog unitManagerDialog;

    public FortifyUnitFromManagerAction(FreeMarsController freeMarsController, UnitManagerDialog unitManagerDialog, Unit unit) {
        super(freeMarsController, unit);
        this.freeMarsController = freeMarsController;
        this.unitManagerDialog = unitManagerDialog;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
        new UnitsTableHelper(freeMarsController, unitManagerDialog).updateUnitsTable();
    }
}
