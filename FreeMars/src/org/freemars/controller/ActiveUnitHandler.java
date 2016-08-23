package org.freemars.controller;

import org.apache.log4j.Logger;
import org.freemars.controller.handler.PostCommandHandler;
import org.freemars.controller.viewcommand.DisplayUnitCommand;
import org.freemars.controller.viewcommand.UpdateTilePaintModelUnitsCommand;
import org.freemars.ui.map.TilePaintModel;
import org.freerealm.executor.CommandResult;
import org.freerealm.map.Coordinate;
import org.freerealm.unit.Unit;

/**
 * @author Deniz ARIKAN
 */
public class ActiveUnitHandler implements PostCommandHandler {

    private static final Logger logger = Logger.getLogger(ActiveUnitHandler.class);

    public void handleUpdate(FreeMarsController freeMarsController, CommandResult commandResult) {
        logger.debug("ActiveUnitHandler handling command result with update type " + commandResult.getUpdateType() + ".");
        if (freeMarsController.getFreeMarsModel().isHumanPlayerActive()) {
            Unit previousActiveUnit = (Unit) commandResult.getParameter("previous_active_unit");
            Unit activeUnit = (Unit) commandResult.getParameter("active_unit");
            Coordinate previousActiveUnitCoordinate = null;
            Coordinate activeUnitCoordinate = null;
            if (previousActiveUnit != null) {
                previousActiveUnitCoordinate = previousActiveUnit.getCoordinate();
            }
            if (activeUnit != null) {
                activeUnitCoordinate = activeUnit.getCoordinate();
            }
            if (previousActiveUnitCoordinate != null && !previousActiveUnitCoordinate.equals(activeUnitCoordinate)) {
                TilePaintModel tilePaintModel = freeMarsController.getFreeMarsModel().getFreeMarsViewModel().getTilePaintModel(previousActiveUnitCoordinate);
                if (tilePaintModel != null) {
                    tilePaintModel.setPaintingActiveUnitIndicator(false);
                }
            }
            if (activeUnitCoordinate != null) {
                freeMarsController.executeViewCommand(new UpdateTilePaintModelUnitsCommand(freeMarsController, activeUnit));
                freeMarsController.executeViewCommand(new DisplayUnitCommand(freeMarsController, activeUnit));
            }
        }
    }
}
