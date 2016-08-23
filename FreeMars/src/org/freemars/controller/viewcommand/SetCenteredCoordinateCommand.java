package org.freemars.controller.viewcommand;

import org.freemars.controller.FreeMarsController;
import org.freerealm.command.AbstractCommand;
import org.freerealm.executor.CommandResult;
import org.freerealm.map.Coordinate;

/**
 *
 * @author Deniz ARIKAN
 */
public class SetCenteredCoordinateCommand extends AbstractCommand implements ViewCommand {

    private final FreeMarsController freeMarsController;
    private final Coordinate coordinate;

    public SetCenteredCoordinateCommand(FreeMarsController freeMarsController, Coordinate coordinate) {
        this.freeMarsController = freeMarsController;
        this.coordinate = coordinate;
    }

    @Override
    public String toString() {
        return "SetCenteredCoordinate - Coordinate : " + coordinate;
    }

    public CommandResult execute() {
        if (!coordinate.equals(freeMarsController.getFreeMarsModel().getFreeMarsViewModel().getCenteredCoordinate())) {
            freeMarsController.getFreeMarsModel().getFreeMarsViewModel().setCenteredCoordinate(coordinate);
            freeMarsController.getGameFrame().getMapPanel().update();
        }
        return new CommandResult(CommandResult.RESULT_OK, "");
    }

    public boolean isBlockingViewExecutionThread() {
        return false;
    }

}
