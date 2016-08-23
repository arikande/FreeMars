package org.freemars.controller.viewcommand;

import java.util.Iterator;

import org.freemars.controller.FreeMarsController;
import org.freemars.ui.map.TilePaintModel;
import org.freerealm.command.AbstractCommand;
import org.freerealm.executor.CommandResult;
import org.freerealm.map.Coordinate;

/**
 *
 * @author Deniz ARIKAN
 */
public class SetMainMapDisplayingGridCommand extends AbstractCommand implements ViewCommand {

    private final FreeMarsController freeMarsController;
    private final boolean displayGrid;

    public SetMainMapDisplayingGridCommand(FreeMarsController freeMarsController, boolean displayGrid) {
        this.freeMarsController = freeMarsController;
        this.displayGrid = displayGrid;
    }

    @Override
    public String toString() {
        return "SetMainMapDisplayingGrid";
    }

    public CommandResult execute() {
        freeMarsController.getFreeMarsModel().getFreeMarsViewModel().setMapPanelDisplayingGrid(displayGrid);
        Iterator<Coordinate> exploredCoordinates = freeMarsController.getFreeMarsModel().getHumanPlayer().getExploredCoordinatesIterator();
        while (exploredCoordinates.hasNext()) {
            Coordinate coordinate = exploredCoordinates.next();
            TilePaintModel tilePaintModel = freeMarsController.getFreeMarsModel().getFreeMarsViewModel().getTilePaintModel(coordinate);
            tilePaintModel.setDisplayingGrid(displayGrid);
        }

        freeMarsController.updateGameFrame();
        return new CommandResult(CommandResult.RESULT_OK, "");
    }

    public boolean isBlockingViewExecutionThread() {
        return false;
    }

}
