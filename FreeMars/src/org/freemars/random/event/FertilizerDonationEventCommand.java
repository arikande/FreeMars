package org.freemars.random.event;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import org.freemars.model.FreeMarsModel;
import org.freemars.player.FreeMarsPlayer;
import org.freerealm.command.DefaultRandomEventCommand;
import org.freerealm.command.ResourceAddCommand;
import org.freerealm.executor.CommandResult;
import org.freerealm.resource.Resource;
import org.freerealm.settlement.Settlement;

/**
 * @author Deniz ARIKAN
 */
public class FertilizerDonationEventCommand extends DefaultRandomEventCommand {

    private final Random random = new Random();

    public CommandResult execute() {
        CommandResult commandResult = new CommandResult(CommandResult.RESULT_OK, "", CommandResult.NO_UPDATE);
        FreeMarsPlayer freeMarsPlayer = (FreeMarsPlayer) getPlayer();
        if (freeMarsPlayer.getSettlementCount() > 0 && !freeMarsPlayer.hasDeclaredIndependence()) {
            ArrayList<Settlement> settlements = new ArrayList<Settlement>();
            Iterator<Settlement> playerSettlementsIterator = freeMarsPlayer.getSettlementsIterator();
            while (playerSettlementsIterator.hasNext()) {
                Settlement settlement = playerSettlementsIterator.next();
                settlements.add(settlement);
            }
            int randomNumber = random.nextInt(settlements.size());
            Settlement eventSettlement = settlements.get(randomNumber);

            int minimumFertilizerDonationPackages = Integer.parseInt(getRandomEvent().getProperty("minimum_fertilizer_donation_packages").toString());
            int maximumFertilizerDonationPackages = Integer.parseInt(getRandomEvent().getProperty("maximum_fertilizer_donation_packages").toString());
            int fertilizerDonationPackageAmount = Integer.parseInt(getRandomEvent().getProperty("fertilizer_donation_package_amount").toString());
            int fertilizerDonationPackages = random.nextInt(maximumFertilizerDonationPackages - minimumFertilizerDonationPackages + 1) + minimumFertilizerDonationPackages;
            int fertilizerAmountDonated = fertilizerDonationPackageAmount * fertilizerDonationPackages;

            Resource fertilizerResource = getRealm().getResourceManager().getResource("Fertilizer");
            getExecutor().execute(new ResourceAddCommand(eventSettlement, fertilizerResource, fertilizerAmountDonated));
            commandResult = new CommandResult(CommandResult.RESULT_OK, "", FreeMarsModel.RANDOM_EVENT_UPDATE);
            commandResult.putParameter("event_name", getRandomEvent().getName());
            commandResult.putParameter("player_id", freeMarsPlayer.getId());
            commandResult.putParameter("settlement", eventSettlement);
            commandResult.putParameter("fertilizer_amount_donated", fertilizerAmountDonated);
        }
        return commandResult;
    }
}
