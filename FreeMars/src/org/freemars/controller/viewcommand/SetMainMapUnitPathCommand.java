package org.freemars.controller.viewcommand;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.freemars.controller.FreeMarsController;
import org.freemars.ui.map.TilePaintModel;
import org.freerealm.command.AbstractCommand;
import org.freerealm.executor.CommandResult;
import org.freerealm.map.Coordinate;
import org.freerealm.map.Path;

/**
 *
 * @author Deniz ARIKAN
 */
public class SetMainMapUnitPathCommand extends AbstractCommand implements ViewCommand {

    private static final Logger logger = Logger.getLogger(SetMainMapUnitPathCommand.class);
    private final FreeMarsController freeMarsController;
    private final Path path;

    public SetMainMapUnitPathCommand(FreeMarsController freeMarsController, Path path) {
        this.freeMarsController = freeMarsController;
        this.path = path;
    }

    @Override
    public String toString() {
        return "SetMainMapUnitPath";
    }

    public CommandResult execute() {
        List<Coordinate> updateCoordinates = new ArrayList<Coordinate>();
        Iterator<Coordinate> iterator = freeMarsController.getFreeMarsModel().getFreeMarsViewModel().getTilePaintModelCoordinatesIterator();
        while (iterator.hasNext()) {
            Coordinate coordinate = iterator.next();
            TilePaintModel tilePaintModel = freeMarsController.getFreeMarsModel().getFreeMarsViewModel().getTilePaintModel(coordinate);
            if (tilePaintModel.isPaintingUnitPath()) {
                tilePaintModel.setPaintingUnitPath(false);
                updateCoordinates.add(coordinate);
            }
        }
        freeMarsController.getFreeMarsModel().getFreeMarsViewModel().setMapPanelUnitPath(path);
        if (path != null) {
            freeMarsController.executeViewCommand(new UpdateCoordinatePaintModelCommand(freeMarsController, path.getCoordinates(), null));
            Iterator<Coordinate> pathCoordinates = path.getCoordinatesIterator();
            while (pathCoordinates.hasNext()) {
                Coordinate pathCoordinate = pathCoordinates.next();
                if (!updateCoordinates.contains(pathCoordinate)) {
                    updateCoordinates.add(pathCoordinate);
                }
            }
        }
        freeMarsController.executeViewCommand(new UpdateCoordinatePaintModelCommand(freeMarsController, updateCoordinates, null));
        return new CommandResult(CommandResult.RESULT_OK, "");
    }

    public boolean isBlockingViewExecutionThread() {
        return false;
    }

}
