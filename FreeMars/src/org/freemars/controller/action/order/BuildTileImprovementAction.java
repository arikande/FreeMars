package org.freemars.controller.action.order;

import java.util.Iterator;
import javax.swing.AbstractAction;
import org.freemars.controller.FreeMarsController;
import org.freerealm.executor.order.ImproveTile;
import org.freerealm.player.Player;
import org.freerealm.property.BuildTileImprovementProperty;
import org.freerealm.tile.Tile;
import org.freerealm.tile.improvement.BuildTileImprovementChecker;
import org.freerealm.tile.improvement.TileImprovementType;
import org.freerealm.unit.Order;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public abstract class BuildTileImprovementAction extends AbstractAction {

    private final FreeMarsController freeMarsController;

    public BuildTileImprovementAction(String name, FreeMarsController freeMarsController) {
        super(name);
        this.freeMarsController = freeMarsController;
    }

    protected boolean isEnabledForTileImrpovement(TileImprovementType tileImprovementType, Unit unit) {
        Player activePlayer = freeMarsController.getFreeMarsModel().getActivePlayer();
        if (activePlayer == null) {
            return false;
        }
        Unit unitToOrder = unit != null ? unit : freeMarsController.getFreeMarsModel().getActivePlayer().getActiveUnit();
        if (unitToOrder == null) {
            return false;
        }
        if (unitToOrder.getCurrentOrder() != null) {
            return false;
        }
        if (unitToOrder.getMovementPoints() == 0) {
            return false;
        }
        BuildTileImprovementProperty buildTileImprovement = (BuildTileImprovementProperty) unitToOrder.getType().getProperty(BuildTileImprovementProperty.NAME);
        if (buildTileImprovement != null) {
            Tile tile = freeMarsController.getFreeMarsModel().getTile(unitToOrder.getCoordinate());
            if (!isTileImprovementInProgress(tile, tileImprovementType) && new BuildTileImprovementChecker().canBuildTileImprovement(buildTileImprovement, tileImprovementType, tile)) {
                return true;
            }
        }
        return false;
    }

    protected Order prepareImproveTileOrder(TileImprovementType tileImprovementType, Unit unit) {
        int turnGiven = freeMarsController.getFreeMarsModel().getNumberOfTurns();
        ImproveTile buildTileImprovement = new ImproveTile(freeMarsController.getFreeMarsModel().getRealm());
        buildTileImprovement.setSymbol(String.valueOf(tileImprovementType.getSymbol()));
        buildTileImprovement.setTurnGiven(turnGiven);
        buildTileImprovement.setUnit(unit);
        buildTileImprovement.setTileImprovementType(tileImprovementType);
        return buildTileImprovement;
    }

    private boolean isTileImprovementInProgress(Tile tile, TileImprovementType tileImprovement) {
        Iterator<Unit> iterator = tile.getUnitsIterator();
        while (iterator.hasNext()) {
            Unit unit = iterator.next();
            if (unit.getCurrentOrder() != null && unit.getCurrentOrder() instanceof ImproveTile) {
                ImproveTile improveTile = (ImproveTile) unit.getCurrentOrder();
                if (improveTile.getTileImprovementType().equals(tileImprovement)) {
                    return true;
                }
            }
        }
        return false;
    }
}
