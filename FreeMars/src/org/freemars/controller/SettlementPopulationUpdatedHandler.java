package org.freemars.controller;

import org.apache.log4j.Logger;
import org.freemars.colony.AddFertilizerToColonyTilesCommand;
import org.freemars.colony.FreeMarsColony;
import org.freemars.controller.handler.PostCommandHandler;
import org.freerealm.Realm;
import org.freerealm.command.SettlementAutomanageResourceCommand;
import org.freerealm.executor.CommandResult;
import org.freerealm.resource.Resource;

/**
 *
 * @author Deniz ARIKAN
 */
public class SettlementPopulationUpdatedHandler implements PostCommandHandler {

    private static final Logger logger = Logger.getLogger(SettlementPopulationUpdatedHandler.class);

    public void handleUpdate(FreeMarsController freeMarsController, CommandResult commandResult) {
        logger.debug("SettlementPopulationUpdatedHandler handling command result with update type " + commandResult.getUpdateType() + ".");
        FreeMarsColony freeMarsColony = (FreeMarsColony) commandResult.getParameter("settlement");
        Resource foodResource = freeMarsController.getFreeMarsModel().getRealm().getResourceManager().getResource(Resource.FOOD);
        Realm realm = freeMarsController.getFreeMarsModel().getRealm();
        if (freeMarsColony.isAutomanagingResource(foodResource)) {
            freeMarsController.execute(new SettlementAutomanageResourceCommand(realm, freeMarsColony, foodResource));
            if (freeMarsColony.isAutoUsingFertilizer()) {
                freeMarsController.execute(new AddFertilizerToColonyTilesCommand(freeMarsController.getFreeMarsModel().getRealm(), freeMarsColony));
            }
        }
        Resource waterResource = freeMarsController.getFreeMarsModel().getRealm().getResourceManager().getResource("Water");
        if (freeMarsColony.isAutomanagingResource(waterResource)) {
            freeMarsController.execute(new SettlementAutomanageResourceCommand(realm, freeMarsColony, waterResource));
        }

    }
}
