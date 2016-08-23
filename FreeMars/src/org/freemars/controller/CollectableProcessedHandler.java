package org.freemars.controller;

import org.freemars.controller.handler.PostCommandHandler;
import org.freemars.controller.viewcommand.DisplayMessageCommand;
import org.freemars.tile.SpaceshipDebrisCollectable;
import org.freemars.ui.map.TilePaintModel;
import org.freerealm.executor.CommandResult;
import org.freerealm.map.Coordinate;
import org.freerealm.settlement.Settlement;
import org.freerealm.tile.Collectable;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class CollectableProcessedHandler implements PostCommandHandler {

    public void handleUpdate(FreeMarsController freeMarsController, CommandResult commandResult) {
        Collectable collectable = (Collectable) commandResult.getParameter("collectable");
        if (collectable instanceof SpaceshipDebrisCollectable) {
            SpaceshipDebrisCollectable spaceshipDebrisCollectable = (SpaceshipDebrisCollectable) collectable;
            Settlement settlement = spaceshipDebrisCollectable.getDeliveredSettlement();
            String resourceName = freeMarsController.getFreeMarsModel().getRealm().getResourceManager().getResource(spaceshipDebrisCollectable.getResourceId()).getName();
            if (settlement != null) {
                Coordinate coordinate = (Coordinate) commandResult.getParameter("collected_coordinate");
                TilePaintModel tilePaintModel = freeMarsController.getFreeMarsModel().getFreeMarsViewModel().getTilePaintModel(coordinate);
                if (tilePaintModel != null) {
                    tilePaintModel.setCollectableImage(null);
                }
                Unit collectingUnit = spaceshipDebrisCollectable.getCollectingUnit();
                if (collectingUnit.getPlayer().equals(freeMarsController.getFreeMarsModel().getHumanPlayer())) {
                    String message = spaceshipDebrisCollectable.getAmount() + " " + resourceName + " found in debris. Resources delivered to " + settlement + ".";
                    freeMarsController.executeViewCommand(new DisplayMessageCommand(freeMarsController, message));
                }
            }
        }
    }
}
