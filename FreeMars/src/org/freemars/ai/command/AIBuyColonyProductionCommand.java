package org.freemars.ai.command;

import org.freemars.ai.AIPlayer;
import org.freerealm.Realm;
import org.freerealm.command.AbstractCommand;
import org.freerealm.command.BuySettlementProductionCommand;
import org.freerealm.executor.CommandResult;
import org.freerealm.settlement.Settlement;

/**
 *
 * @author Deniz ARIKAN
 */
public class AIBuyColonyProductionCommand extends AbstractCommand {

    private final Settlement settlement;
    private int amount = 0;

    public AIBuyColonyProductionCommand(Realm realm, Settlement settlement, int amount) {
        super(realm);
        this.settlement = settlement;
        this.amount = amount;
    }

    public CommandResult execute() {
        if (!(settlement.getPlayer() instanceof AIPlayer)) {
            return new CommandResult(CommandResult.RESULT_ERROR, "Colony owner is not an AI player", CommandResult.NO_UPDATE);
        }
        AIPlayer aiPlayer = (AIPlayer) settlement.getPlayer();
        int productionPointCost = Integer.parseInt(getRealm().getProperty("production_point_cost"));
        int productionBuyCost = amount * productionPointCost;
        if (aiPlayer.getWealth() > aiPlayer.getReserveWealth() + productionBuyCost) {
            BuySettlementProductionCommand buySettlementProductionCommand = new BuySettlementProductionCommand(getRealm(), settlement, amount);
            CommandResult commandResult = getExecutor().execute(buySettlementProductionCommand);
            return commandResult;
        }
        return new CommandResult(CommandResult.RESULT_ERROR, "Production not bought. Player reserving wealth.", CommandResult.NO_UPDATE);
    }

}
