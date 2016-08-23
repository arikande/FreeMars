package org.freemars.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.freemars.controller.handler.PostCommandHandler;
import org.freemars.controller.viewcommand.UpdateCoordinatePaintModelCommand;
import org.freemars.model.FreeMarsModel;
import org.freerealm.command.ResourceAddCommand;
import org.freerealm.executor.CommandResult;
import org.freerealm.map.Coordinate;
import org.freerealm.player.Player;
import org.freerealm.resource.Resource;
import org.freerealm.settlement.Settlement;
import org.freerealm.tile.Tile;

/**
 *
 * @author Deniz ARIKAN
 */
public class VegetationChangedHandler implements PostCommandHandler {

    private static final Logger logger = Logger.getLogger(VegetationChangedHandler.class);

    public void handleUpdate(FreeMarsController freeMarsController, CommandResult commandResult) {
        logger.debug("VegetationChangedHandler handling command result with update type " + commandResult.getUpdateType() + ".");
        Coordinate coordinate = (Coordinate) commandResult.getParameter("coordinate");
        Player humanPlayer = freeMarsController.getFreeMarsModel().getHumanPlayer();
        if (humanPlayer != null && humanPlayer.isCoordinateExplored(coordinate)) {
            List<Coordinate> coordinates = new ArrayList<Coordinate>();
            coordinates.add(coordinate);
            freeMarsController.executeViewCommand(new UpdateCoordinatePaintModelCommand(freeMarsController, coordinates, null));
        }
        FreeMarsModel model = freeMarsController.getFreeMarsModel();
        Tile tile = model.getTile(coordinate);
        if (tile != null && tile.getVegetation() == null) {
            Settlement settlementToAddLumber = null;
            if (tile.getNumberOfUnits() > 0) {
                Player player = tile.getFirstUnit().getPlayer();
                ArrayList<Coordinate> checkCoordinates = new ArrayList<Coordinate>();
                checkCoordinates.add(coordinate);
                checkCoordinates.addAll(model.getRealm().getCircleCoordinates(coordinate, 1));
                for (Coordinate checkCoordinate : checkCoordinates) {
                    Tile checkTile = model.getTile(checkCoordinate);
                    if (checkTile.getSettlement() != null && checkTile.getSettlement().getPlayer().equals(player)) {
                        settlementToAddLumber = checkTile.getSettlement();
                    }
                }
            }
            if (settlementToAddLumber != null) {
                int lumberToAddOnVegetationClear = Integer.parseInt(model.getRealm().getProperty("lumber_to_add_on_vegetation_clear"));
                Resource resource = model.getRealm().getResourceManager().getResource("Lumber");
                freeMarsController.execute(new ResourceAddCommand(settlementToAddLumber, resource, lumberToAddOnVegetationClear));
            }
        }
    }
}
