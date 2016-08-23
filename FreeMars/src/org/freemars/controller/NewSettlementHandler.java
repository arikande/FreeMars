package org.freemars.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.freemars.ai.AIPlayer;
import org.freemars.colony.AddFertilizerToColonyTilesCommand;
import org.freemars.colony.FreeMarsColony;
import org.freemars.controller.handler.PostCommandHandler;
import org.freemars.controller.viewcommand.UpdateCoordinatePaintModelCommand;
import org.freemars.message.NewColonyFoundedMessage;
import org.freerealm.Realm;
import org.freerealm.command.AddTileImprovementCommand;
import org.freerealm.command.SettlementAddAutomanagedResourceCommand;
import org.freerealm.command.SettlementAutomanageResourceCommand;
import org.freerealm.executor.CommandResult;
import org.freerealm.map.Coordinate;
import org.freerealm.player.Player;
import org.freerealm.resource.Resource;
import org.freerealm.settlement.Settlement;
import org.freerealm.settlement.improvement.SettlementImprovement;
import org.freerealm.settlement.improvement.SettlementImprovementType;
import org.freerealm.tile.Tile;
import org.freerealm.tile.improvement.TileImprovementType;

/**
 *
 * @author Deniz ARIKAN
 */
public class NewSettlementHandler implements PostCommandHandler {

    public void handleUpdate(FreeMarsController freeMarsController, CommandResult commandResult) {
        Settlement settlement = (Settlement) commandResult.getParameter("settlement");
        List<Coordinate> coordinates = new ArrayList<Coordinate>();
        coordinates.add(settlement.getCoordinate());
        coordinates.addAll(freeMarsController.getFreeMarsModel().getRealm().getCircleCoordinates(settlement.getCoordinate(), 1));
        coordinates.addAll(freeMarsController.getFreeMarsModel().getRealm().getCircleCoordinates(settlement.getCoordinate(), 2));
        Player humanPlayer = freeMarsController.getFreeMarsModel().getHumanPlayer();
        if (humanPlayer != null) {
            List<Coordinate> updateCoordinates = new ArrayList<Coordinate>();
            for (Coordinate coordinate : coordinates) {
                if (humanPlayer.isCoordinateExplored(coordinate)) {
                    updateCoordinates.add(coordinate);
                }
            }
            freeMarsController.executeViewCommand(new UpdateCoordinatePaintModelCommand(freeMarsController, updateCoordinates, null));
        }
        addRoadsToColonyTile(freeMarsController, settlement.getTile());
        addResourcesToColony(freeMarsController, settlement);
        addWaterPumpToColony(freeMarsController, settlement);
        addSolarPanelToColony(freeMarsController, settlement);
        addH2CollectorToColony(freeMarsController, settlement);
        Resource foodResource = freeMarsController.getFreeMarsModel().getRealm().getResourceManager().getResource(Resource.FOOD);
        Realm realm = freeMarsController.getFreeMarsModel().getRealm();
        freeMarsController.execute(new SettlementAutomanageResourceCommand(realm, settlement, foodResource));
        freeMarsController.execute(new SettlementAddAutomanagedResourceCommand(settlement, foodResource));
        Resource waterResource = freeMarsController.getFreeMarsModel().getRealm().getResourceManager().getResource("Water");
        freeMarsController.execute(new SettlementAutomanageResourceCommand(realm, settlement, waterResource));
        freeMarsController.execute(new SettlementAddAutomanagedResourceCommand(settlement, waterResource));
        ((FreeMarsColony) settlement).setAutoUsingFertilizer(true);
        freeMarsController.execute(new AddFertilizerToColonyTilesCommand(realm, (FreeMarsColony) settlement));
        freeMarsController.execute(new SettlementAutomanageResourceCommand(realm, settlement, foodResource));
        if (!(settlement.getPlayer() instanceof AIPlayer)) {
            NewColonyFoundedMessage newColonyFoundedMessage = new NewColonyFoundedMessage();
            newColonyFoundedMessage.setSettlement(settlement);
            newColonyFoundedMessage.setTurnSent(freeMarsController.getFreeMarsModel().getRealm().getNumberOfTurns());
            if (((Properties) commandResult.getParameter("properties")).get("set_message_read") != null) {????
                boolean messageRead = ((Boolean) ((Properties) commandResult.getParameter("properties")).get("set_message_read"));
                newColonyFoundedMessage.setRead(messageRead);
            }
            settlement.getPlayer().addMessage(newColonyFoundedMessage);
        }
    }

    private void addRoadsToColonyTile(FreeMarsController controller, Tile tile) {
        TileImprovementType road = controller.getFreeMarsModel().getRealm().getTileImprovementTypeManager().getImprovement("Road");
        controller.execute(new AddTileImprovementCommand(tile, road));
    }

    private void addResourcesToColony(FreeMarsController freeMarsController, Settlement settlement) {
        settlement.setResourceQuantity(freeMarsController.getFreeMarsModel().getRealm().getResourceManager().getResource(0), 1000);
        settlement.setResourceQuantity(freeMarsController.getFreeMarsModel().getRealm().getResourceManager().getResource("Food"), 1000);
        settlement.setResourceQuantity(freeMarsController.getFreeMarsModel().getRealm().getResourceManager().getResource("Energy"), 1000);
        settlement.setResourceQuantity(freeMarsController.getFreeMarsModel().getRealm().getResourceManager().getResource("Fertilizer"), 1000);
    }

    private void addWaterPumpToColony(FreeMarsController freeMarsController, Settlement settlement) {
        SettlementImprovementType waterPumpType = freeMarsController.getFreeMarsModel().getRealm().getSettlementImprovementManager().getImprovement("Water pump");
        SettlementImprovement waterPump = new SettlementImprovement();
        waterPump.setType(waterPumpType);
        settlement.addImprovement(waterPump);
    }

    private void addSolarPanelToColony(FreeMarsController freeMarsController, Settlement settlement) {
        SettlementImprovementType solarPanelType = freeMarsController.getFreeMarsModel().getRealm().getSettlementImprovementManager().getImprovement("Solar panel");
        SettlementImprovement solarPanel = new SettlementImprovement();
        solarPanel.setType(solarPanelType);
        int colonistsToAssignToSolarPanel;
        if (solarPanelType.getMaximumWorkers() < settlement.getProductionWorkforce()) {
            colonistsToAssignToSolarPanel = solarPanelType.getMaximumWorkers();
        } else {
            colonistsToAssignToSolarPanel = settlement.getProductionWorkforce();
        }
        solarPanel.setNumberOfWorkers(colonistsToAssignToSolarPanel);
        settlement.addImprovement(solarPanel);
    }

    private void addH2CollectorToColony(FreeMarsController freeMarsController, Settlement settlement) {
        int freeH2CollectorColonyCount = Integer.parseInt(freeMarsController.getFreeMarsModel().getRealm().getProperty("free_h2_collector_colony_count"));
        if (freeH2CollectorColonyCount >= settlement.getPlayer().getSettlementCount()) {
            SettlementImprovementType hydrogenCollectorType = freeMarsController.getFreeMarsModel().getRealm().getSettlementImprovementManager().getImprovement("Hydrogen collector");
            SettlementImprovement hydrogenCollector = new SettlementImprovement();
            hydrogenCollector.setType(hydrogenCollectorType);
            int colonistsToAssignToHydrogenCollector;
            if (hydrogenCollectorType.getMaximumWorkers() < settlement.getProductionWorkforce()) {
                colonistsToAssignToHydrogenCollector = hydrogenCollectorType.getMaximumWorkers();
            } else {
                colonistsToAssignToHydrogenCollector = settlement.getProductionWorkforce();
            }
            hydrogenCollector.setNumberOfWorkers(colonistsToAssignToHydrogenCollector);
            settlement.addImprovement(hydrogenCollector);
        }
    }
}
