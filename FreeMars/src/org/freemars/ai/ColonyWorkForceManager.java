package org.freemars.ai;

import java.util.ArrayList;
import java.util.Iterator;

import org.freemars.colony.AddFertilizerToColonyTilesCommand;
import org.freemars.colony.FreeMarsColony;
import org.freemars.controller.FreeMarsController;
import org.freerealm.Realm;
import org.freerealm.command.SettlementAddAutomanagedResourceCommand;
import org.freerealm.command.SettlementAutomanageResourceCommand;
import org.freerealm.command.SettlementImprovementSetWorkersCommand;
import org.freerealm.command.WorkforceRemoveCommand;
import org.freerealm.resource.Resource;
import org.freerealm.settlement.improvement.SettlementImprovement;
import org.freerealm.settlement.workforce.WorkForce;

/**
 *
 * @author Deniz ARIKAN
 */
public class ColonyWorkForceManager {

    private final FreeMarsController freeMarsController;
    private final ColonyTileWorkForceManagerForExportableResources colonyTileWorkForceManagerForExportableResources;

    public ColonyWorkForceManager(FreeMarsController freeMarsController, AIPlayer freeMarsAIPlayer) {
        this.freeMarsController = freeMarsController;
        colonyTileWorkForceManagerForExportableResources = new ColonyTileWorkForceManagerForExportableResources(freeMarsController, freeMarsAIPlayer);
    }

    public void manage(FreeMarsColony freeMarsColony) {
        removeAllWorkForce(freeMarsColony);
        automanageFoodAndWater(freeMarsColony);
        manageSettlementImprovementWorkforce(freeMarsColony);
        colonyTileWorkForceManagerForExportableResources.manage(freeMarsColony);
    }

    private void removeAllWorkForce(FreeMarsColony freeMarsColony) {
        ArrayList<WorkForce> removableWorkForce = new ArrayList<WorkForce>();
        Iterator<WorkForce> workForceIterator = freeMarsColony.getWorkForceManager().getWorkForceIterator();
        while (workForceIterator.hasNext()) {
            WorkForce workForce = workForceIterator.next();
            removableWorkForce.add(workForce);
        }
        for (WorkForce workForce : removableWorkForce) {
            freeMarsController.execute(new WorkforceRemoveCommand(freeMarsColony, workForce.getCoordinate()));
        }
        Iterator<SettlementImprovement> improvementsIterator = freeMarsColony.getImprovementsIterator();
        while (improvementsIterator.hasNext()) {
            SettlementImprovement settlementImprovement = improvementsIterator.next();
            freeMarsController.execute(new SettlementImprovementSetWorkersCommand(freeMarsColony, settlementImprovement, 0));
        }
    }

    private void automanageFoodAndWater(FreeMarsColony freeMarsColony) {
        Realm realm = freeMarsController.getFreeMarsModel().getRealm();
        Resource foodResource = freeMarsController.getFreeMarsModel().getRealm().getResourceManager().getResource(Resource.FOOD);
        freeMarsController.execute(new SettlementAutomanageResourceCommand(realm, freeMarsColony, foodResource));
        freeMarsController.execute(new SettlementAddAutomanagedResourceCommand(freeMarsColony, foodResource));
        Resource waterResource = freeMarsController.getFreeMarsModel().getRealm().getResourceManager().getResource("Water");
        freeMarsController.execute(new SettlementAutomanageResourceCommand(realm, freeMarsColony, waterResource));
        freeMarsController.execute(new SettlementAddAutomanagedResourceCommand(freeMarsColony, waterResource));
        freeMarsColony.setAutoUsingFertilizer(true);
        freeMarsController.execute(new AddFertilizerToColonyTilesCommand(realm, (FreeMarsColony) freeMarsColony));
        freeMarsController.execute(new SettlementAutomanageResourceCommand(realm, freeMarsColony, foodResource));
    }

    private void manageSettlementImprovementWorkforce(FreeMarsColony freeMarsColony) {
        Resource energyResource = freeMarsController.getFreeMarsModel().getRealm().getResourceManager().getResource("Energy");
        Iterator<SettlementImprovement> improvementsIterator = freeMarsColony.getImprovementsIterator();
        while (improvementsIterator.hasNext()) {
            SettlementImprovement settlementImprovement = improvementsIterator.next();
            if (settlementImprovement.getType().getName().equals("Solar panel")) {
                freeMarsController.execute(new SettlementImprovementSetWorkersCommand(freeMarsColony, settlementImprovement, 5));
            }
            if (settlementImprovement.getType().getName().equals("Solar array")) {
                freeMarsController.execute(new SettlementImprovementSetWorkersCommand(freeMarsColony, settlementImprovement, 10));
            }
            if (settlementImprovement.getType().getName().equals("Steel works")) {
                freeMarsController.execute(new SettlementImprovementSetWorkersCommand(freeMarsColony, settlementImprovement, 40));
            }
            if (settlementImprovement.getType().getName().equals("Steel factory")) {
                freeMarsController.execute(new SettlementImprovementSetWorkersCommand(freeMarsColony, settlementImprovement, settlementImprovement.getType().getMaximumWorkers()));
            }
            if (settlementImprovement.getType().getName().equals("Glass works")) {
                freeMarsController.execute(new SettlementImprovementSetWorkersCommand(freeMarsColony, settlementImprovement, 35));
            }
            if (settlementImprovement.getType().getName().equals("Chemical works")) {
                freeMarsController.execute(new SettlementImprovementSetWorkersCommand(freeMarsColony, settlementImprovement, 30));
            }
            if (settlementImprovement.getType().getName().equals("Hydrogen collector")) {
                freeMarsController.execute(new SettlementImprovementSetWorkersCommand(freeMarsColony, settlementImprovement, 10));
            }

        }
        /*
         private void manageSettlementImprovementWorkforce(FreeMarsColony freeMarsColony) {
         int totalWorkersNeededBySettlementImprovements = 0;
         Iterator<SettlementImprovement> improvementsIterator = freeMarsColony.getImprovementsIterator();
         while (improvementsIterator.hasNext()) {
         SettlementImprovement settlementImprovement = improvementsIterator.next();
         if (settlementImprovement.getNumberOfWorkers() == 0) {
         totalWorkersNeededBySettlementImprovements = totalWorkersNeededBySettlementImprovements + settlementImprovement.getType().getMaximumWorkers();
         }
         }
         int maximumImprovementWorkers = freeMarsColony.getProductionWorkforce() / 4;
         double ratio = (double) maximumImprovementWorkers / (double) totalWorkersNeededBySettlementImprovements;
         improvementsIterator = freeMarsColony.getImprovementsIterator();
         while (improvementsIterator.hasNext()) {
         SettlementImprovement settlementImprovement = improvementsIterator.next();
         if (settlementImprovement.getNumberOfWorkers() == 0) {
         int workers = (int) (settlementImprovement.getType().getMaximumWorkers() * ratio);
         if (workers > settlementImprovement.getType().getMaximumWorkers()) {
         workers = settlementImprovement.getType().getMaximumWorkers();
         }
         freeMarsController.execute(new SettlementImprovementSetWorkersCommand(freeMarsColony, settlementImprovement, workers));
         }
         }*/
    }

}
