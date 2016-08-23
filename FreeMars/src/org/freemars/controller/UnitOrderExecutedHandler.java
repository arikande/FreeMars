package org.freemars.controller;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.freemars.controller.handler.PostCommandHandler;
import org.freemars.controller.viewcommand.UpdateCoordinatePaintModelCommand;
import org.freemars.controller.viewcommand.UpdateTilePaintModelUnitsCommand;
import org.freerealm.executor.CommandResult;
import org.freerealm.executor.order.ClearVegetationOrder;
import org.freerealm.executor.order.ImproveTile;
import org.freerealm.executor.order.TransformTileOrder;
import org.freerealm.map.Coordinate;
import org.freerealm.player.Player;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class UnitOrderExecutedHandler implements PostCommandHandler {

    private static final Logger logger = Logger.getLogger(UnitOrderExecutedHandler.class);

    public void handleUpdate(FreeMarsController freeMarsController, CommandResult commandResult) {
        logger.debug("UnitOrderExecutedHandler handling command result with update type " + commandResult.getUpdateType() + ".");
        Unit unit = (Unit) commandResult.getParameter("unit");
//        Order order = (Order) commandResult.getParameter("order");
//        logger.debug("Unit " + unit + " order " + order);
        if (updateNeeded(freeMarsController, unit)) {
            List<Coordinate> updateCoordinates = new ArrayList<Coordinate>();
            updateCoordinates.add(unit.getCoordinate());
            updateCoordinates.addAll(freeMarsController.getFreeMarsModel().getRealm().getCircleCoordinates(unit.getCoordinate(), 1));
            freeMarsController.executeViewCommand(new UpdateCoordinatePaintModelCommand(freeMarsController, updateCoordinates, null));
            freeMarsController.executeViewCommand(new UpdateTilePaintModelUnitsCommand(freeMarsController, unit));
        }
    }

    private boolean updateNeeded(FreeMarsController freeMarsController, Unit unit) {
        Player humanPlayer = freeMarsController.getFreeMarsModel().getHumanPlayer();
        if (humanPlayer == null) {
            return false;
        }
        if (!humanPlayer.isCoordinateExplored(unit.getCoordinate())) {
            return false;
        }
        return (unit.getCurrentOrder() instanceof ImproveTile) || (unit.getCurrentOrder() instanceof ClearVegetationOrder) || (unit.getCurrentOrder() instanceof TransformTileOrder);
    }
}
