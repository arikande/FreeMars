package org.freemars.diplomacy.gift;

import org.apache.log4j.Logger;
import org.freemars.model.FreeMarsModel;
import org.freemars.player.FreeMarsPlayer;
import org.freerealm.command.AbstractCommand;
import org.freerealm.command.AddUnitToPlayerCommand;
import org.freerealm.command.RemoveUnitFromPlayerCommand;
import org.freerealm.executor.CommandResult;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class SendUnitGiftCommand extends AbstractCommand {

    private static final Logger logger = Logger.getLogger(SendUnitGiftCommand.class);
    private final FreeMarsModel freeMarsModel;
    private final FreeMarsPlayer fromPlayer;
    private final FreeMarsPlayer toPlayer;
    private final Unit unit;

    public SendUnitGiftCommand(FreeMarsModel freeMarsModel, FreeMarsPlayer fromPlayer, FreeMarsPlayer toPlayer, Unit unit) {
        this.freeMarsModel = freeMarsModel;
        this.fromPlayer = fromPlayer;
        this.toPlayer = toPlayer;
        this.unit = unit;
    }

    public CommandResult execute() {
        logger.trace("SendUnitGiftCommand executing...");
        RemoveUnitFromPlayerCommand removeUnitFromPlayerCommand = new RemoveUnitFromPlayerCommand(fromPlayer, unit);
        getExecutor().execute(removeUnitFromPlayerCommand);
        AddUnitToPlayerCommand addUnitToPlayerCommand = new AddUnitToPlayerCommand(freeMarsModel.getRealm(), toPlayer, unit);
        getExecutor().execute(addUnitToPlayerCommand);
        int unitValue = freeMarsModel.getEarth().getEarthSellsAtPrice(unit.getType());
        String wealthNeededForAttitudeChangeProperty = freeMarsModel.getRealm().getProperty("wealth_needed_for_diplomatic_attitude_change");
        int wealthNeededForAttitudeChange = Integer.parseInt(wealthNeededForAttitudeChangeProperty);
        int attitudeDifference = unitValue / wealthNeededForAttitudeChange;
        int attitude = toPlayer.getDiplomacy().getPlayerRelation(fromPlayer).getAttitude();
        toPlayer.getDiplomacy().getPlayerRelation(fromPlayer).setAttitude(attitude + attitudeDifference);

        if (logger.isTraceEnabled()) {
            String currencyUnit = freeMarsModel.getRealm().getProperty("currency_unit");
            StringBuilder log = new StringBuilder();
            log.append("Unit \"");
            log.append(unit.getName());
            log.append("\" having a worth of ");
            log.append(unitValue);
            log.append(" ");
            log.append(currencyUnit);
            log.append(" sent. ");
            log.append(toPlayer.getName());
            log.append(" attitude to ");
            log.append(fromPlayer.getName());
            log.append(" increased from ");
            log.append(attitude);
            log.append(" to ");
            log.append(attitude + attitudeDifference);
            log.append(".");
            logger.trace(log);
        }
        logger.trace("SendUnitGiftCommand executed successfully.");

        return new CommandResult(CommandResult.RESULT_OK, null);
    }

}
