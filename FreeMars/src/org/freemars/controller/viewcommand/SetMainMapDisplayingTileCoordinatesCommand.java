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
public class SetMainMapDisplayingTileCoordinatesCommand extends AbstractCommand implements ViewCommand {

    private final FreeMarsController freeMarsController;
    private final boolean displayTileCoordinates;

    public SetMainMapDisplayingTileCoordinatesCommand(FreeMarsController freeMarsController, boolean displayTileCoordinates) {
        this.freeMarsController = freeMarsController;
        this.displayTileCoordinates = displayTileCoordinates;
    }

    @Override
    public String toString() {
        return "SetMainMapDisplayingTileCoordinates";
    }

    public CommandResult execute() {
        freeMarsController.getFreeMarsModel().getFreeMarsViewModel().setMapPanelDisplayingCoordinates(displayTileCoordinates);

        Iterator<Coordinate> exploredCoordinates = freeMarsController.getFreeMarsModel().getHumanPlayer().getExploredCoordinatesIterator();
        while (exploredCoordinates.hasNext()) {
            Coordinate coordinate = exploredCoordinates.next();
            TilePaintModel tilePaintModel = freeMarsController.getFreeMarsModel().getFreeMarsViewModel().getTilePaintModel(coordinate);
            tilePaintModel.setDisplayingCoordinate(displayTileCoordinates);
        }

        freeMarsController.updateGameFrame();
        return new CommandResult(CommandResult.RESULT_OK, "");
    }

    public boolean isBlockingViewExecutionThread() {
        return false;
    }

}
