package org.freemars.controller.action.order;

import java.awt.event.ActionEvent;
import java.util.Iterator;

import javax.swing.AbstractAction;

import org.freemars.controller.FreeMarsController;
import org.freerealm.command.UnitOrderAddCommand;
import org.freerealm.executor.order.TransformTileOrder;
import org.freerealm.player.Player;
import org.freerealm.tile.Tile;
import org.freerealm.tile.TileType;
import org.freerealm.unit.Order;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class TransformTerrainAction extends AbstractAction {

    private final FreeMarsController freeMarsController;
    private final Unit unit;

    public TransformTerrainAction(FreeMarsController freeMarsController, Unit unit) {
        super("Transform terrain");
        this.freeMarsController = freeMarsController;
        this.unit = unit;
    }

    public void actionPerformed(ActionEvent e) {
        Unit unitToOrder = unit != null ? unit : freeMarsController.getFreeMarsModel().getActivePlayer().getActiveUnit();
        Order order = prepareTransformTerrainOrder(unitToOrder);
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
        if (tile.getType().getTransformableTileTypecount() == 0) {
            return false;
        }
        return unitToOrder.getType().getProperty("ChangeTileTypeProperty") != null;
    }

    private Order prepareTransformTerrainOrder(Unit unit) {
        int turnGiven = freeMarsController.getFreeMarsModel().getNumberOfTurns();
        TransformTileOrder transformTileOrder = new TransformTileOrder(freeMarsController.getFreeMarsModel().getRealm());
        transformTileOrder.setTurnGiven(turnGiven);
        transformTileOrder.setUnit(unit);

        Tile tile = freeMarsController.getFreeMarsModel().getTile(unit.getCoordinate());
        TileType tileType = tile.getType();
        TileType transformToTileType = null;
        if (tileType.getTransformableTileTypecount() > 0) {
            Iterator<Integer> iterator = tileType.getTransformableTileTypeIdsIterator();
            while (iterator.hasNext()) {
                Integer integer = iterator.next();
                transformToTileType = freeMarsController.getFreeMarsModel().getTileType(integer);
            }
        }
        transformTileOrder.setTileType(transformToTileType);
        return transformTileOrder;
    }
}
