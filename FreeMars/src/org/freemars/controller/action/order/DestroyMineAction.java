package org.freemars.controller.action.order;

import java.awt.event.ActionEvent;

import org.freemars.controller.FreeMarsController;
import org.freerealm.command.UnitOrderAddCommand;
import org.freerealm.tile.improvement.TileImprovementType;
import org.freerealm.unit.Order;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class DestroyMineAction extends DestroyTileImprovementAction {

    private final FreeMarsController freeMarsController;
    private final Unit unit;

    public DestroyMineAction(FreeMarsController freeMarsController, Unit unit) {
        super("Mine");
        this.freeMarsController = freeMarsController;
        this.unit = unit;
    }

    public void actionPerformed(ActionEvent e) {
        Unit unitToOrder = unit != null ? unit : freeMarsController.getFreeMarsModel().getActivePlayer().getActiveUnit();
        TileImprovementType tileImprovementType = freeMarsController.getFreeMarsModel().getRealm().getTileImprovementTypeManager().getImprovement("Mine");
        Order order = prepareDestroyTileImprovementOrder(freeMarsController, unitToOrder, tileImprovementType);
        freeMarsController.execute(new UnitOrderAddCommand(freeMarsController.getFreeMarsModel().getRealm(), unitToOrder, order));
    }

    public boolean checkEnabled() {
        TileImprovementType tileImprovementType = freeMarsController.getFreeMarsModel().getRealm().getTileImprovementTypeManager().getImprovement("Mine");
        return checkEnabled(freeMarsController, unit, tileImprovementType);
    }

}
