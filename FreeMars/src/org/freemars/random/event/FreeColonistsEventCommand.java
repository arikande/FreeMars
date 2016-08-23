package org.freemars.random.event;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import org.apache.log4j.Logger;
import org.freemars.model.FreeMarsModel;
import org.freemars.player.FreeMarsPlayer;
import org.freerealm.command.DefaultRandomEventCommand;
import org.freerealm.command.SetSettlementPopulationCommand;
import org.freerealm.executor.CommandResult;
import org.freerealm.settlement.Settlement;

/**
 * @author Deniz ARIKAN
 */
public class FreeColonistsEventCommand extends DefaultRandomEventCommand {

    private final Random random = new Random();

    public CommandResult execute() {

        CommandResult commandResult = new CommandResult(CommandResult.RESULT_OK, "", CommandResult.NO_UPDATE);
        FreeMarsPlayer freeMarsPlayer = (FreeMarsPlayer) getPlayer();
        Logger.getLogger(FreeColonistsEventCommand.class).info("FreeColonistsEventCommand will be executed for " + freeMarsPlayer.getName() + ".");
        if (freeMarsPlayer.getSettlementCount() > 0 && !freeMarsPlayer.hasDeclaredIndependence()) {
            ArrayList<Settlement> settlements = new ArrayList<Settlement>();
            Iterator<Settlement> playerSettlementsIterator = freeMarsPlayer.getSettlementsIterator();
            while (playerSettlementsIterator.hasNext()) {
                Settlement settlement = playerSettlementsIterator.next();
                settlements.add(settlement);
            }
            int randomNumber = random.nextInt(settlements.size());
            Settlement eventSettlement = settlements.get(randomNumber);
            int minimumColonyPopulation = Integer.parseInt(getRandomEvent().getProperty("minimum_colony_population").toString());
            int minimumNewColonists = Integer.parseInt(getRandomEvent().getProperty("minimum_new_colonists").toString());
            int maximumNewColonists = Integer.parseInt(getRandomEvent().getProperty("maximum_new_colonists").toString());
            if (eventSettlement.getPopulation() >= minimumColonyPopulation) {
                int newColonists = random.nextInt(maximumNewColonists - minimumNewColonists + 1) + minimumNewColonists;
                if (newColonists > 0) {
                    Logger.getLogger(FreeColonistsEventCommand.class).info(newColonists + " colonists will be added to " + eventSettlement.getName() + ".");
                    int oldColonyPopulation = eventSettlement.getPopulation();
                    int newColonyPopulation = oldColonyPopulation + newColonists;
                    getExecutor().execute(new SetSettlementPopulationCommand(eventSettlement, newColonyPopulation));
                    String logMessage = "Population of " + eventSettlement.getName() + " is increased from " + oldColonyPopulation + " to " + newColonyPopulation + ".";
                    Logger.getLogger(FreeColonistsEventCommand.class).info(logMessage);
/*
                    FreeMarsColony freeMarsColony = (FreeMarsColony) eventSettlement;
                    Resource foodResource = getRealm().getResourceManager().getResource(Resource.FOOD);
                    if (freeMarsColony.isAutomanagingResource(foodResource)) {
                        getExecutor().execute(new SettlementAutomanageResourceCommand(getRealm(), freeMarsColony, foodResource));
                        Logger.getLogger(FreeColonistsEventCommand.class).info(eventSettlement.getName() + " is automanaging food. Food production updated for new colonists.");
                        if (freeMarsColony.isAutoUsingFertilizer()) {
                            getExecutor().execute(new AddFertilizerToColonyTilesCommand(getRealm(), (FreeMarsColony) freeMarsColony));
                            Logger.getLogger(FreeColonistsEventCommand.class).info("Fertilizer added to food producing tiles.");
                        }
                    }
                    Resource waterResource = getRealm().getResourceManager().getResource("Water");
                    if (freeMarsColony.isAutomanagingResource(waterResource)) {
                        getExecutor().execute(new SettlementAutomanageResourceCommand(getRealm(), freeMarsColony, waterResource));
                        Logger.getLogger(FreeColonistsEventCommand.class).info(eventSettlement.getName() + " is automanaging water. Water production updated for new colonists.");
                    }
                    */
                    commandResult = new CommandResult(CommandResult.RESULT_OK, "", FreeMarsModel.RANDOM_EVENT_UPDATE);
                    commandResult.putParameter("event_name", getRandomEvent().getName());
                    commandResult.putParameter("player_id", freeMarsPlayer.getId());
                    commandResult.putParameter("settlement", eventSettlement);
                    commandResult.putParameter("new_colonists", newColonists);
                }
            }
        }
        return commandResult;
    }
}
