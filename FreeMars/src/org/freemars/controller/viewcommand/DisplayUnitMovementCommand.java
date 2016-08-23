package org.freemars.controller.viewcommand;

import java.util.ArrayList;
import java.util.List;

import org.freemars.controller.FreeMarsController;
import org.freemars.ui.map.MapPanel;
import org.freemars.ui.map.TilePaintModel;
import org.freemars.ui.map.TilePaintModelBuilder;
import org.freerealm.command.AbstractCommand;
import org.freerealm.executor.CommandResult;
import org.freerealm.map.Coordinate;
import org.freerealm.map.Direction;
import org.freerealm.tile.Tile;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class DisplayUnitMovementCommand extends AbstractCommand implements ViewCommand {

    private final FreeMarsController freeMarsController;
    private final Unit unit;
    private final Coordinate previousCoordinate;
    private final Coordinate newCoordinate;
    private final boolean pauseAfterMovement;
    private boolean updatingPreviousCoordinate = true;

    public DisplayUnitMovementCommand(FreeMarsController freeMarsController, Unit unit, Coordinate previousCoordinate, Coordinate newCoordinate, boolean pauseAfterMovement) {
        this.freeMarsController = freeMarsController;
        this.unit = unit;
        this.previousCoordinate = previousCoordinate;
        this.newCoordinate = newCoordinate;
        this.pauseAfterMovement = pauseAfterMovement;
    }

    @Override
    public String toString() {
        return "DisplayUnitMovement";
    }

    public CommandResult execute() {
        MapPanel mapPanel = freeMarsController.getGameFrame().getMapPanel();
        mapPanel.setMovingUnit(unit);
        mapPanel.setUnitMovementPartCount(0);
        mapPanel.setUnitMoveFromWorldCoordinate(previousCoordinate);
        mapPanel.setUnitMoveToWorldCoordinate(newCoordinate);
        TilePaintModel tilePaintModel = freeMarsController.getFreeMarsModel().getFreeMarsViewModel().getTilePaintModel(previousCoordinate);
        Direction direction = freeMarsController.getFreeMarsModel().getRealm().getDirection(previousCoordinate, newCoordinate);
        if (direction != null) {
            unit.addCustomProperty("direction", direction.getShortName());
        }
        if (tilePaintModel != null && updatingPreviousCoordinate) {
            Tile tile = freeMarsController.getFreeMarsModel().getTile(previousCoordinate);
            TilePaintModelBuilder.buildModelForTileUnits(mapPanel.getGraphics(), tilePaintModel, freeMarsController.getFreeMarsModel(), tile, null);
        }
        UnitMovementPaintTimerAction unitMovementPaintTimerAction = new UnitMovementPaintTimerAction(freeMarsController.getFreeMarsModel(), mapPanel, unit, newCoordinate, pauseAfterMovement);
        List<Coordinate> coordinates = new ArrayList<Coordinate>();
        coordinates.add(previousCoordinate);
        coordinates.add(newCoordinate);
        unitMovementPaintTimerAction.setRepaintRectangle(mapPanel.findPaintRectangle(coordinates));
        new javax.swing.Timer(15, unitMovementPaintTimerAction).start();
        return new CommandResult(CommandResult.RESULT_OK, "");
    }

    public boolean isBlockingViewExecutionThread() {
        return true;
    }

    public void setUpdatingPreviousCoordinate(boolean updatingPreviousCoordinate) {
        this.updatingPreviousCoordinate = updatingPreviousCoordinate;
    }

}
