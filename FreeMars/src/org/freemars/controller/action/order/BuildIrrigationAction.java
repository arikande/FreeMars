package org.freemars.controller.action.order;

import java.awt.event.ActionEvent;

import org.freemars.controller.FreeMarsController;
import org.freerealm.command.UnitOrderAddCommand;
import org.freerealm.tile.improvement.TileImprovementType;
import org.freerealm.tile.improvement.TileImprovementTypeManager;
import org.freerealm.unit.Order;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class BuildIrrigationAction extends BuildTileImprovementAction {

    private final FreeMarsController freeMarsController;
    private final Unit unit;

    public BuildIrrigationAction(FreeMarsController freeMarsController, Unit unit) {
        super("Build irrigation", freeMarsController);
        this.freeMarsController = freeMarsController;
        this.unit = unit;
    }

    public void actionPerformed(ActionEvent e) {
        TileImprovementType tileImprovementType = freeMarsController.getFreeMarsModel().getRealm().getTileImprovementTypeManager().getImprovement("Irrigation");
        Unit unitToOrder = unit != null ? unit : freeMarsController.getFreeMarsModel().getActivePlayer().getActiveUnit();
        Order order = prepareImproveTileOrder(tileImprovementType, unitToOrder);
        freeMarsController.execute(new UnitOrderAddCommand(freeMarsController.getFreeMarsModel().getRealm(), unitToOrder, order));
    }

    public boolean checkEnabled() {
        TileImprovementTypeManager tileImprovementTypeManager = freeMarsController.getFreeMarsModel().getRealm().getTileImprovementTypeManager();
        if (tileImprovementTypeManager != null) {
            TileImprovementType tileImprovementType = freeMarsController.getFreeMarsModel().getRealm().getTileImprovementTypeManager().getImprovement("Irrigation");
            return isEnabledForTileImrpovement(tileImprovementType, unit);
        } else {
            return false;
        }
    }

}
