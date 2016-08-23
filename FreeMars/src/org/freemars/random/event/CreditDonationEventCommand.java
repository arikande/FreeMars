package org.freemars.random.event;

import java.util.Random;

import org.freemars.model.FreeMarsModel;
import org.freemars.player.FreeMarsPlayer;
import org.freerealm.command.DefaultRandomEventCommand;
import org.freerealm.command.WealthAddCommand;
import org.freerealm.executor.CommandResult;

/**
 * @author Deniz ARIKAN
 */
public class CreditDonationEventCommand extends DefaultRandomEventCommand {

    private final Random random = new Random();

    public CommandResult execute() {
        CommandResult commandResult = new CommandResult(CommandResult.RESULT_OK, "", CommandResult.NO_UPDATE);
        FreeMarsPlayer freeMarsPlayer = (FreeMarsPlayer) getPlayer();
        if (!freeMarsPlayer.hasDeclaredIndependence()) {
            commandResult = new CommandResult(CommandResult.RESULT_OK, "", FreeMarsModel.RANDOM_EVENT_UPDATE);
            int minimumCreditDonationPackages = Integer.parseInt(getRandomEvent().getProperty("minimum_credit_donation_packages").toString());
            int maximumCreditDonationPackages = Integer.parseInt(getRandomEvent().getProperty("maximum_credit_donation_packages").toString());
            int creditDonationPackageValue = Integer.parseInt(getRandomEvent().getProperty("credit_donation_package_value").toString());
            int creditDonationPackages = random.nextInt(maximumCreditDonationPackages - minimumCreditDonationPackages + 1) + minimumCreditDonationPackages;
            int creditsDonated = creditDonationPackageValue * creditDonationPackages;
            getExecutor().execute(new WealthAddCommand(freeMarsPlayer, creditsDonated));
            commandResult.putParameter("event_name", getRandomEvent().getName());
            commandResult.putParameter("player_id", freeMarsPlayer.getId());
            commandResult.putParameter("credits_donated", creditsDonated);
        }
        return commandResult;
    }
}
