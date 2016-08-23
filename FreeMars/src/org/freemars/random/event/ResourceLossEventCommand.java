package org.freemars.random.event;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import org.freemars.model.FreeMarsModel;
import org.freemars.player.FreeMarsPlayer;
import org.freerealm.command.DefaultRandomEventCommand;
import org.freerealm.command.ResourceRemoveCommand;
import org.freerealm.executor.CommandResult;
import org.freerealm.resource.Resource;
import org.freerealm.settlement.Settlement;

/**
 * @author Deniz ARIKAN
 */
public class ResourceLossEventCommand extends DefaultRandomEventCommand {

    private Resource resource;
    private final Random random = new Random();

    public CommandResult execute() {
        CommandResult commandResult = new CommandResult(CommandResult.RESULT_OK, "", CommandResult.NO_UPDATE);
        FreeMarsPlayer freeMarsPlayer = (FreeMarsPlayer) getPlayer();
        if (freeMarsPlayer.getSettlementCount() > 0) {
            ArrayList<Settlement> settlements = new ArrayList<Settlement>();
            Iterator<Settlement> playerSettlementsIterator = freeMarsPlayer.getSettlementsIterator();
            while (playerSettlementsIterator.hasNext()) {
                Settlement settlement = playerSettlementsIterator.next();
                settlements.add(settlement);
            }
            int randomNumber = random.nextInt(settlements.size());
            Settlement eventSettlement = settlements.get(randomNumber);
            int minimumPackages = Integer.parseInt(getRandomEvent().getProperty("minimum_packages").toString());
            int packageAmount = Integer.parseInt(getRandomEvent().getProperty("package_amount").toString());
            int minimumAmountNeeded = minimumPackages * packageAmount;
            if (eventSettlement.getResourceQuantity(resource) >= minimumAmountNeeded) {
                int maximumPackages = Integer.parseInt(getRandomEvent().getProperty("maximum_packages").toString());
                int totalPackages = random.nextInt(maximumPackages - minimumPackages + 1) + minimumPackages;
                int resourceAmountLost = packageAmount * totalPackages;
                boolean found = false;
                while (!found && resourceAmountLost > 0) {
                    if (eventSettlement.getResourceQuantity(resource) >= resourceAmountLost) {
                        found = true;
                    } else {
                        resourceAmountLost = resourceAmountLost - packageAmount;
                    }
                }
                getExecutor().execute(new ResourceRemoveCommand(eventSettlement, resource, resourceAmountLost));
                commandResult = new CommandResult(CommandResult.RESULT_OK, "", FreeMarsModel.RANDOM_EVENT_UPDATE);
                commandResult.putParameter("event_name", getRandomEvent().getName());
                commandResult.putParameter("player_id", freeMarsPlayer.getId());
                commandResult.putParameter("settlement", eventSettlement);
                commandResult.putParameter("amount_lost", resourceAmountLost);
            }
        }
        return commandResult;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }
}
