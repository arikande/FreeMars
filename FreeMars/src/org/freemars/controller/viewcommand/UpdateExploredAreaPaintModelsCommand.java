package org.freemars.controller.viewcommand;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.freemars.controller.FreeMarsController;
import org.freerealm.command.AbstractCommand;
import org.freerealm.executor.CommandResult;
import org.freerealm.map.Coordinate;

/**
 *
 * @author Deniz ARIKAN
 */
public class UpdateExploredAreaPaintModelsCommand extends AbstractCommand implements ViewCommand {

    private static final Logger logger = Logger.getLogger(UpdateCoordinatePaintModelCommand.class);

    private final FreeMarsController freeMarsController;

    public UpdateExploredAreaPaintModelsCommand(FreeMarsController freeMarsController) {
        this.freeMarsController = freeMarsController;
    }

    @Override
    public String toString() {
        return "UpdateExploredAreaPaintModels";
    }

    public CommandResult execute() {
        long startTime = System.currentTimeMillis();
        List<Coordinate> modelUpdateCoordinates = new ArrayList<Coordinate>();
        modelUpdateCoordinates.addAll(freeMarsController.getFreeMarsModel().getHumanPlayer().getExploredCoordinates());
        for (Coordinate coordinate : freeMarsController.getFreeMarsModel().getHumanPlayer().getExploredCoordinates()) {
            List<Coordinate> circleCoordinates = freeMarsController.getFreeMarsModel().getRealm().getCircleCoordinates(coordinate, 1);
            for (Coordinate circleCoordinate : circleCoordinates) {
                if (!modelUpdateCoordinates.contains(circleCoordinate)) {
                    modelUpdateCoordinates.add(circleCoordinate);
                }
            }
        }
        freeMarsController.executeViewCommand(new UpdateCoordinatePaintModelCommand(freeMarsController, modelUpdateCoordinates, null));
        long endTime = System.currentTimeMillis();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("UpdateExploredAreaPaintModelsCommand executed in ");
        stringBuilder.append(endTime - startTime);
        stringBuilder.append(" miliseconds for ");
        stringBuilder.append(modelUpdateCoordinates.size());
        stringBuilder.append(" tiles in turn ");
        stringBuilder.append(freeMarsController.getFreeMarsModel().getNumberOfTurns());
        stringBuilder.append(".");
        logger.info(stringBuilder.toString());
        synchronized (ViewCommandExecutionThread.WAIT_ON) {
            ViewCommandExecutionThread.WAIT_ON.notify();
        }
        return new CommandResult(CommandResult.RESULT_OK, "");
    }

    public boolean isBlockingViewExecutionThread() {
        return true;
    }

}
