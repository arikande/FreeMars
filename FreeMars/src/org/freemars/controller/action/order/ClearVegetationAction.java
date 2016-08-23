package org.freemars.controller.action.order;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.freemars.controller.FreeMarsController;
import org.freerealm.command.UnitOrderAddCommand;
import org.freerealm.executor.order.ClearVegetationOrder;
import org.freerealm.player.Player;
import org.freerealm.property.ClearVegetationProperty;
import org.freerealm.tile.Tile;
import org.freerealm.unit.Order;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class ClearVegetationAction extends AbstractAction {

    private final FreeMarsController freeMarsController;
    private final Unit unit;

    public ClearVegetationAction(FreeMarsController controller, Unit unit) {
        super("Clear vegetation");
        this.freeMarsController = controller;
        this.unit = unit;
    }

    public void actionPerformed(ActionEvent e) {
        Unit unitToOrder = unit != null ? unit : freeMarsController.getFreeMarsModel().getActivePlayer().getActiveUnit();
        Order order = prepareClearVegetationOrder(unitToOrder);
        freeMarsController.execute(new UnitOrderAddCommand(freeMarsController.getFreeMarsModel().getRealm(), unitToOrder, order));
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
        if (unitToOrder.getMovementPoints() == 0) {
            return false;
        }
        if (unitToOrder.getCurrentOrder() != null) {
            return false;
        }
        Tile tile = freeMarsController.getFreeMarsModel().getTile(unitToOrder.getCoordinate());
        if (tile == null) {
            return false;
        }
        if (tile.getVegetation() == null) {
            return false;
        }
        return unitToOrder.getType().getProperty(ClearVegetationProperty.NAME) != null;
    }

    private Order prepareClearVegetationOrder(Unit unit) {
        int turnGiven = freeMarsController.getFreeMarsModel().getNumberOfTurns();
        ClearVegetationOrder clearVegetationOrder = new ClearVegetationOrder(freeMarsController.getFreeMarsModel().getRealm());
        clearVegetationOrder.setTurnGiven(turnGiven);
        clearVegetationOrder.setUnit(unit);
        return clearVegetationOrder;
    }
}
