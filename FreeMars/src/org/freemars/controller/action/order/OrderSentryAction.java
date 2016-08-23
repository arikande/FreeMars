package org.freemars.controller.action.order;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.freemars.controller.FreeMarsController;
import org.freerealm.command.UnitOrderAddCommand;
import org.freerealm.executor.order.Sentry;
import org.freerealm.player.Player;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class OrderSentryAction extends AbstractAction {

    private final FreeMarsController freeMarsController;
    private final Unit unit;

    public OrderSentryAction(FreeMarsController freeMarsController, Unit unit) {
        super("Sentry / Sleep", null);
        this.freeMarsController = freeMarsController;
        this.unit = unit;
    }

    public void actionPerformed(ActionEvent e) {
        Unit unitToOrder = unit != null ? unit : freeMarsController.getFreeMarsModel().getActivePlayer().getActiveUnit();
        Sentry sentry = new Sentry(freeMarsController.getFreeMarsModel().getRealm());
        sentry.setUnit(unitToOrder);
        freeMarsController.execute(new UnitOrderAddCommand(freeMarsController.getFreeMarsModel().getRealm(), unitToOrder, sentry));
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
        return unitToOrder.getCurrentOrder() == null;
    }
}
