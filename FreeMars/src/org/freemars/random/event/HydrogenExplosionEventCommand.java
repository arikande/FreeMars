package org.freemars.random.event;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import org.apache.log4j.Logger;
import org.freemars.model.FreeMarsModel;
import org.freerealm.command.DefaultRandomEventCommand;
import org.freerealm.command.SetSettlementPopulationCommand;
import org.freerealm.executor.CommandResult;
import org.freerealm.resource.Resource;
import org.freerealm.settlement.Settlement;

/**
 * @author Deniz ARIKAN
 */
public class HydrogenExplosionEventCommand extends DefaultRandomEventCommand {

    private final Random random = new Random();

    public CommandResult execute() {
        Logger.getLogger(HydrogenExplosionEventCommand.class).info("HydrogenExplosionEventCommand will be executed for " + getPlayer().getName() + ".");
        CommandResult commandResult = new CommandResult(CommandResult.RESULT_OK, "", CommandResult.NO_UPDATE);
        if (getPlayer().getSettlementCount() > 0) {
            Resource hydrogenResource = getRealm().getResourceManager().getResource("Hydrogen");
            ArrayList<Settlement> settlements = new ArrayList<Settlement>();
            Iterator<Settlement> playerSettlementsIterator = getPlayer().getSettlementsIterator();
            while (playerSettlementsIterator.hasNext()) {
                Settlement settlement = playerSettlementsIterator.next();
                int hydrogenQuantity = settlement.getResourceQuantity(hydrogenResource);
                if (hydrogenQuantity > 0) {
                    settlements.add(settlement);
                }
            }
            if (settlements.size() > 0) {
                int randomNumber = random.nextInt(settlements.size());
                Settlement eventSettlement = settlements.get(randomNumber);
                int minimumColonyPopulation = Integer.parseInt(getRandomEvent().getProperty("minimum_colony_population").toString());
                int minimumColonistsLost = Integer.parseInt(getRandomEvent().getProperty("minimum_colonists_lost").toString());
                int maximumColonistsLost = Integer.parseInt(getRandomEvent().getProperty("maximum_colonists_lost").toString());
                if (eventSettlement.getPopulation() >= minimumColonyPopulation) {
                    int colonistsLost = random.nextInt(maximumColonistsLost - minimumColonistsLost + 1) + minimumColonistsLost;
                    if (colonistsLost > 0) {
                        int newColonyPopulation = eventSettlement.getPopulation() - colonistsLost;
                        getExecutor().execute(new SetSettlementPopulationCommand(eventSettlement, newColonyPopulation));
                        String log = "HydrogenExplosionEventCommand executed successfully. " + colonistsLost + " colonists lost. New population is " + newColonyPopulation + ".";
                        Logger.getLogger(HydrogenExplosionEventCommand.class).info(log);
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
}
