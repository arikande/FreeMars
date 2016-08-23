package org.freemars.random.event;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import org.freemars.model.FreeMarsModel;
import org.freerealm.Realm;
import org.freerealm.command.DefaultRandomEventCommand;
import org.freerealm.command.SetSettlementPopulationCommand;
import org.freerealm.executor.CommandResult;
import org.freerealm.settlement.Settlement;
import org.freerealm.settlement.workforce.WorkForce;
import org.freerealm.tile.Tile;
import org.freerealm.tile.improvement.TileImprovementType;

/**
 * @author Deniz ARIKAN
 */
public class MiningAccidentEventCommand extends DefaultRandomEventCommand {

    private final Random random = new Random();

    public CommandResult execute() {
        CommandResult commandResult = new CommandResult(CommandResult.RESULT_OK, "", CommandResult.NO_UPDATE);
        if (getPlayer().getSettlementCount() > 0) {
            ArrayList<Settlement> settlements = new ArrayList<Settlement>();
            Iterator<Settlement> playerSettlementsIterator = getPlayer().getSettlementsIterator();
            while (playerSettlementsIterator.hasNext()) {
                Settlement settlement = playerSettlementsIterator.next();
                settlements.add(settlement);
            }
            int randomNumber = random.nextInt(settlements.size());
            Settlement eventSettlement = settlements.get(randomNumber);
            WorkForce miningWorkForce = getMiningWorkForce(eventSettlement, getRealm());
            if (miningWorkForce != null) {
                int minimumColonyPopulation = Integer.parseInt(getRandomEvent().getProperty("minimum_colony_population").toString());
                int minimumColonistsLost = Integer.parseInt(getRandomEvent().getProperty("minimum_colonists_lost").toString());
                int maximumColonistsLost = Integer.parseInt(getRandomEvent().getProperty("maximum_colonists_lost").toString());
                minimumColonistsLost = Math.min(minimumColonistsLost, miningWorkForce.getNumberOfWorkers());
                maximumColonistsLost = Math.min(maximumColonistsLost, miningWorkForce.getNumberOfWorkers());
                if (eventSettlement.getPopulation() >= minimumColonyPopulation) {
                    int colonistsLost = random.nextInt(maximumColonistsLost - minimumColonistsLost + 1) + minimumColonistsLost;
                    if (colonistsLost > 0) {
                        int newColonyPopulation = eventSettlement.getPopulation() - colonistsLost;
                        getExecutor().execute(new SetSettlementPopulationCommand(eventSettlement, newColonyPopulation));
                        commandResult = new CommandResult(CommandResult.RESULT_OK, "", FreeMarsModel.RANDOM_EVENT_UPDATE);
                        commandResult.putParameter("event_name", getRandomEvent().getName());
                        commandResult.putParameter("player_id", getPlayer().getId());
                        commandResult.putParameter("settlement", eventSettlement);
                        commandResult.putParameter("colonists_lost", colonistsLost);
                    }
                }
            }
        }
        return commandResult;
    }

    private WorkForce getMiningWorkForce(Settlement settlement, Realm realm) {
        TileImprovementType mineImprovementType = realm.getTileImprovementTypeManager().getImprovement("Mine");
        Iterator<WorkForce> workForceIterator = settlement.getWorkForceManager().getWorkForceIterator();
        while (workForceIterator.hasNext()) {
            WorkForce workForce = workForceIterator.next();
            Tile tile = realm.getTile(workForce.getCoordinate());
            if (tile.hasImprovement(mineImprovementType)) {
                return workForce;
            }
        }
        return null;
    }
}
