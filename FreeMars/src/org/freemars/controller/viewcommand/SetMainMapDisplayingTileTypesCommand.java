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
public class SetMainMapDisplayingTileTypesCommand extends AbstractCommand implements ViewCommand {

    private final FreeMarsController freeMarsController;
    private final boolean displayTileTypes;

    public SetMainMapDisplayingTileTypesCommand(FreeMarsController freeMarsController, boolean displayTileTypes) {
        this.freeMarsController = freeMarsController;
        this.displayTileTypes = displayTileTypes;
    }

    @Override
    public String toString() {
        return "SetMainMapDisplayingTileTypes";
    }

    public CommandResult execute() {
        freeMarsController.getFreeMarsModel().getFreeMarsViewModel().setMapPanelDisplayingTileTypes(displayTileTypes);

        Iterator<Coordinate> exploredCoordinates = freeMarsController.getFreeMarsModel().getHumanPlayer().getExploredCoordinatesIterator();
        while (exploredCoordinates.hasNext()) {
            Coordinate coordinate = exploredCoordinates.next();
            TilePaintModel tilePaintModel = freeMarsController.getFreeMarsModel().getFreeMarsViewModel().getTilePaintModel(coordinate);
            tilePaintModel.setDisplayingTileType(displayTileTypes);
        }

        freeMarsController.updateGameFrame();
        return new CommandResult(CommandResult.RESULT_OK, "");
    }

    public boolean isBlockingViewExecutionThread() {
        return false;
    }

}
