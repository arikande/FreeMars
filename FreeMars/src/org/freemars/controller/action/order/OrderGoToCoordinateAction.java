package org.freemars.controller.action.order;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.freemars.controller.FreeMarsController;
import org.freerealm.command.UnitOrderAddCommand;
import org.freerealm.executor.order.GoToCoordinate;
import org.freerealm.map.Coordinate;
import org.freerealm.map.Path;
import org.freerealm.property.MoveProperty;
import org.freerealm.tile.Tile;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class OrderGoToCoordinateAction extends AbstractAction {

    private final Coordinate coordinate;
    private final FreeMarsController freeMarsController;

    public OrderGoToCoordinateAction(FreeMarsController controller, Coordinate coordinate) {
        this.freeMarsController = controller;
        this.coordinate = coordinate;
    }

    public void actionPerformed(ActionEvent e) {
        Unit activeUnit = freeMarsController.getFreeMarsModel().getActivePlayer().getActiveUnit();
        GoToCoordinate goToCoordinate = new GoToCoordinate(freeMarsController.getFreeMarsModel().getRealm());
        goToCoordinate.setUnit(activeUnit);
        goToCoordinate.setCoordinate(coordinate);
        freeMarsController.execute(new UnitOrderAddCommand(freeMarsController.getFreeMarsModel().getRealm(), activeUnit, goToCoordinate));
    }

    @Override
    public boolean isEnabled() {
        Unit unit = freeMarsController.getFreeMarsModel().getActivePlayer().getActiveUnit();
        if (unit == null) {
            return false;
        }
        if (unit.getCurrentOrder() != null) {
            return false;
        }
        if (unit.getCoordinate().equals(coordinate)) {
            return false;
        }
        MoveProperty move = (MoveProperty) unit.getType().getProperty(MoveProperty.NAME);
        if (move == null) {
            return false;
        } else {
            Tile tile = freeMarsController.getFreeMarsModel().getTile(coordinate);
            if (tile == null) {
                return false;
            } else {
                Path path = freeMarsController.getFreeMarsModel().getRealm().getPathFinder().findPath(unit, coordinate);
                if (path != null) {
                    return true;
                } else {
                    return false;
                }
            }
        }
    }
}
