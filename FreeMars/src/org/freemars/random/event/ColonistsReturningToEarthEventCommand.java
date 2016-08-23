package org.freemars.random.event;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import org.freemars.model.FreeMarsModel;
import org.freerealm.command.DefaultRandomEventCommand;
import org.freerealm.command.SetSettlementPopulationCommand;
import org.freerealm.executor.CommandResult;
import org.freerealm.settlement.Settlement;

/**
 * @author Deniz ARIKAN
 */
public class ColonistsReturningToEarthEventCommand extends DefaultRandomEventCommand {

    private final Random random = new Random();

    public CommandResult execute() {
        CommandResult commandResult = new CommandResult(CommandResult.RESULT_OK, "", CommandResult.NO_UPDATE);
        if (getPlayer().getSettlementCount() > 0 && getRealm().getNumberOfTurns() > 48) {
            ArrayList<Settlement> settlements = new ArrayList<Settlement>();
            Iterator<Settlement> playerSettlementsIterator = getPlayer().getSettlementsIterator();
            while (playerSettlementsIterator.hasNext()) {
                Settlement settlement = playerSettlementsIterator.next();
                settlements.add(settlement);
            }
            int randomNumber = random.nextInt(settlements.size());
            Settlement eventSettlement = settlements.get(randomNumber);
            int minimumColonyPopulation = Integer.parseInt(getRandomEvent().getProperty("minimum_colony_population").toString());
            int minimumLeavingColonists = Integer.parseInt(getRandomEvent().getProperty("minimum_leaving_colonists").toString());
            int maximumLeavingColonists = Integer.parseInt(getRandomEvent().getProperty("maximum_leaving_colonists").toString());
            if (eventSettlement.getPopulation() >= minimumColonyPopulation) {
                int leavingColonists = random.nextInt(maximumLeavingColonists - minimumLeavingColonists + 1) + minimumLeavingColonists;
                if (leavingColonists > 0) {
                    int newColonyPopulation = eventSettlement.getPopulation() - leavingColonists;
                    getExecutor().execute(new SetSettlementPopulationCommand(eventSettlement, newColonyPopulation));
                    commandResult = new CommandResult(CommandResult.RESULT_OK, "", FreeMarsModel.RANDOM_EVENT_UPDATE);
                    commandResult.putParameter("event_name", getRandomEvent().getName());
                    commandResult.putParameter("player_id", getPlayer().getId());
                    commandResult.putParameter("settlement", eventSettlement);
                    commandResult.putParameter("leaving_colonists", leavingColonists);
                }
            }
        }
        return commandResult;
    }
}
