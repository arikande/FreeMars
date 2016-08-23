package org.freemars.controller.action.order;

import javax.swing.AbstractAction;
import org.freemars.controller.FreeMarsController;
import org.freerealm.executor.order.DestroyTileImprovement;
import org.freerealm.player.Player;
import org.freerealm.tile.Tile;
import org.freerealm.tile.improvement.TileImprovementType;
import org.freerealm.unit.Order;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public abstract class DestroyTileImprovementAction extends AbstractAction {

    public DestroyTileImprovementAction(String name) {
        super(name);
    }

    protected Order prepareDestroyTileImprovementOrder(FreeMarsController freeMarsController, Unit unit, TileImprovementType tileImprovementType) {
        int turnGiven = freeMarsController.getFreeMarsModel().getNumberOfTurns();
        DestroyTileImprovement destroyTileImprovement = new DestroyTileImprovement(freeMarsController.getFreeMarsModel().getRealm());
        destroyTileImprovement.setTurnGiven(turnGiven);
        destroyTileImprovement.setUnit(unit);
        destroyTileImprovement.setTileImprovementType(tileImprovementType);
        return destroyTileImprovement;
    }

    public boolean checkEnabled(FreeMarsController freeMarsController, Unit unit, TileImprovementType tileImprovementType) {
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
        if (tileImprovementType != null) {
            return tile.hasImprovement(tileImprovementType);
        } else {
            return tile.getImprovementCount() > 0;
        }
    }
}
