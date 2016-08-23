package org.freemars.controller.action.order;

import java.awt.event.ActionEvent;
import java.util.Iterator;

import org.freemars.controller.FreeMarsController;
import org.freerealm.command.UnitOrderAddCommand;
import org.freerealm.tile.Tile;
import org.freerealm.tile.improvement.TileImprovementType;
import org.freerealm.unit.Order;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class DestroyAllTileImprovementsAction extends DestroyTileImprovementAction {

    private final FreeMarsController freeMarsController;
    private final Unit unit;

    public DestroyAllTileImprovementsAction(FreeMarsController freeMarsController, Unit unit) {
        super("All");
        this.freeMarsController = freeMarsController;
        this.unit = unit;
    }

    public void actionPerformed(ActionEvent e) {
        Unit unitToOrder = unit != null ? unit : freeMarsController.getFreeMarsModel().getActivePlayer().getActiveUnit();
        Tile tile = freeMarsController.getFreeMarsModel().getTile(unitToOrder.getCoordinate());
        Iterator<TileImprovementType> iterator = tile.getImprovementsIterator();
        while (iterator.hasNext()) {
            TileImprovementType tileImprovementType = iterator.next();
            Order order = prepareDestroyTileImprovementOrder(freeMarsController, unitToOrder, tileImprovementType);
            freeMarsController.execute(new UnitOrderAddCommand(freeMarsController.getFreeMarsModel().getRealm(), unitToOrder, order));
        }
    }

    public boolean checkEnabled() {
        return checkEnabled(freeMarsController, unit, null);
    }

}
