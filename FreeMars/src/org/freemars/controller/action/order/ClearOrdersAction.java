package org.freemars.controller.action.order;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.freemars.controller.FreeMarsController;
import org.freerealm.command.UnitOrdersClearCommand;
import org.freerealm.player.Player;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class ClearOrdersAction extends AbstractAction {

    private final FreeMarsController freeMarsController;
    private final Unit unit;

    public ClearOrdersAction(FreeMarsController freeMarsController, Unit unit) {
        super("Clear orders");
        this.freeMarsController = freeMarsController;
        this.unit = unit;
    }

    public void actionPerformed(ActionEvent e) {
        Unit clearOrdersUnit = unit;
        if (clearOrdersUnit == null) {
            clearOrdersUnit = freeMarsController.getFreeMarsModel().getActivePlayer().getActiveUnit();
            if (clearOrdersUnit == null) {
                return;
            }
        }
        freeMarsController.execute(new UnitOrdersClearCommand(clearOrdersUnit));
    }

    public boolean checkEnabled() {
        Player activePlayer = freeMarsController.getFreeMarsModel().getActivePlayer();
        if (activePlayer == null) {
            return false;
        }
        Unit unitToOrder = unit != null ? unit : freeMarsController.getFreeMarsModel().getActivePlayer().getActiveUnit();
        if (unitToOrder == null) {
            return false;
        }
        return unitToOrder.getCurrentOrder() != null;
    }
}
