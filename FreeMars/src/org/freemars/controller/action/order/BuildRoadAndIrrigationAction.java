package org.freemars.controller.action.order;

import java.awt.event.ActionEvent;

import org.freemars.controller.FreeMarsController;
import org.freerealm.command.UnitOrderAddCommand;
import org.freerealm.executor.order.ImproveTile;
import org.freerealm.player.Player;
import org.freerealm.tile.improvement.TileImprovementType;
import org.freerealm.unit.Order;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class BuildRoadAndIrrigationAction extends BuildTileImprovementAction {

    private final FreeMarsController freeMarsController;
    private final Unit unit;

    public BuildRoadAndIrrigationAction(FreeMarsController freeMarsController, Unit unit) {
        super("Build road & irrigation", freeMarsController);
        this.freeMarsController = freeMarsController;
        this.unit = unit;
    }

    public void actionPerformed(ActionEvent e) {
        Unit unitToOrder = unit != null ? unit : freeMarsController.getFreeMarsModel().getActivePlayer().getActiveUnit();
        Order buildRoadOrder = prepareBuildRoadOrder(unitToOrder);
        Order buildIrrigationOrder = prepareBuildIrrigationOrder(unitToOrder);
        freeMarsController.execute(new UnitOrderAddCommand(freeMarsController.getFreeMarsModel().getRealm(), unitToOrder, buildRoadOrder));
        freeMarsController.execute(new UnitOrderAddCommand(freeMarsController.getFreeMarsModel().getRealm(), unitToOrder, buildIrrigationOrder));
    }

    @Override
    public boolean isEnabled() {
        Player activePlayer = freeMarsController.getFreeMarsModel().getActivePlayer();
        if (activePlayer == null) {
            return false;
        }
        Unit unitToOrder = unit != null ? unit : freeMarsController.getFreeMarsModel().getActivePlayer().getActiveUnit();
        if (unitToOrder == null) {
            return false;
        }
        if (unitToOrder.getCurrentOrder() == null) {
            return false;
        }
        if (unitToOrder.getMovementPoints() == 0) {
            return false;
        }
        TileImprovementType roadImprovement = (TileImprovementType) freeMarsController.getFreeMarsModel().getRealm().getTileImprovementTypeManager().getImprovement("Road");
        TileImprovementType irrigationImprovement = (TileImprovementType) freeMarsController.getFreeMarsModel().getRealm().getTileImprovementTypeManager().getImprovement("Irrigation");
        return isEnabledForTileImrpovement(roadImprovement, unitToOrder) && isEnabledForTileImrpovement(irrigationImprovement, unitToOrder);
    }

    private Order prepareBuildRoadOrder(Unit unit) {
        int turnGiven = freeMarsController.getFreeMarsModel().getNumberOfTurns();
        TileImprovementType tileImprovement = (TileImprovementType) freeMarsController.getFreeMarsModel().getRealm().getTileImprovementTypeManager().getImprovement("Road");
        ImproveTile buildTileImprovement = new ImproveTile(freeMarsController.getFreeMarsModel().getRealm());
        buildTileImprovement.setTurnGiven(turnGiven);
        buildTileImprovement.setUnit(unit);
        buildTileImprovement.setTileImprovementType(tileImprovement);
        return buildTileImprovement;
    }

    private Order prepareBuildIrrigationOrder(Unit unit) {
        int turnGiven = freeMarsController.getFreeMarsModel().getNumberOfTurns();
        TileImprovementType tileImprovement = (TileImprovementType) freeMarsController.getFreeMarsModel().getRealm().getTileImprovementTypeManager().getImprovement("Irrigation");
        ImproveTile buildTileImprovement = new ImproveTile(freeMarsController.getFreeMarsModel().getRealm());
        buildTileImprovement.setTurnGiven(turnGiven);
        buildTileImprovement.setUnit(unit);
        buildTileImprovement.setTileImprovementType(tileImprovement);
        return buildTileImprovement;
    }
}
