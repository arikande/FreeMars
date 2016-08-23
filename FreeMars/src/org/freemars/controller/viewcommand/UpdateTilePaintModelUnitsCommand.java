package org.freemars.controller.viewcommand;

import org.apache.log4j.Logger;
import org.freemars.controller.FreeMarsController;
import org.freemars.ui.map.TilePaintModel;
import org.freemars.ui.map.TilePaintModelBuilder;
import org.freerealm.command.AbstractCommand;
import org.freerealm.executor.CommandResult;
import org.freerealm.map.Coordinate;
import org.freerealm.tile.Tile;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class UpdateTilePaintModelUnitsCommand extends AbstractCommand implements ViewCommand {

    private static final Logger logger = Logger.getLogger(UpdateTilePaintModelUnitsCommand.class);

    private final FreeMarsController freeMarsController;
    private Coordinate coordinate;

    public UpdateTilePaintModelUnitsCommand(FreeMarsController freeMarsController, Coordinate coordinate) {
        this.freeMarsController = freeMarsController;
        this.coordinate = coordinate;
    }

    @Override
    public String toString() {
        return "UpdateTilePaintModelUnits";
    }

    public UpdateTilePaintModelUnitsCommand(FreeMarsController freeMarsController, Unit unit) {
        this.freeMarsController = freeMarsController;
        coordinate = null;
        if (unit != null && unit.getCoordinate() != null) {
            coordinate = unit.getCoordinate();
        }
    }

    public CommandResult execute() {
        long startTime = System.currentTimeMillis();
        if (coordinate != null) {
            TilePaintModel tilePaintModel = freeMarsController.getFreeMarsModel().getFreeMarsViewModel().getTilePaintModel(coordinate);
            if (tilePaintModel != null) {
                Tile tile = freeMarsController.getFreeMarsModel().getTile(coordinate);
                TilePaintModelBuilder.buildModelForTileUnits(freeMarsController.getGameFrame().getMapPanel().getGraphics(), tilePaintModel, freeMarsController.getFreeMarsModel(), tile, null);
                freeMarsController.executeViewCommand(new RepaintMapPanelCommand(freeMarsController));
            }
        }
        long executionTime = System.currentTimeMillis() - startTime;
        if (executionTime > 10) {
            logger.trace("UpdateTilePaintModelUnitsCommand executed in " + executionTime + " miliseconds for coordinate " + coordinate + ".");
        }
        synchronized (ViewCommandExecutionThread.WAIT_ON) {
            ViewCommandExecutionThread.WAIT_ON.notify();
        }
        return new CommandResult(CommandResult.RESULT_OK, "");
    }

    public boolean isBlockingViewExecutionThread() {
        return true;
    }

}
