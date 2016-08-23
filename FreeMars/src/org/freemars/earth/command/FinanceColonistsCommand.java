package org.freemars.earth.command;

import org.freemars.model.FreeMarsModel;
import org.freemars.player.FreeMarsPlayer;
import org.freerealm.Utility;
import org.freerealm.command.AbstractCommand;
import org.freerealm.command.SetContainerPopulationCommand;
import org.freerealm.command.WealthRemoveCommand;
import org.freerealm.executor.CommandResult;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class FinanceColonistsCommand extends AbstractCommand {

    private final FreeMarsModel freeMarsModel;
    private final Unit unit;
    private final int colonistCount;

    public FinanceColonistsCommand(FreeMarsModel freeMarsModel, Unit unit, int colonistCount) {
        this.freeMarsModel = freeMarsModel;
        this.unit = unit;
        this.colonistCount = colonistCount;
    }

    public CommandResult execute() {
        int financeCostPerColonist = Integer.parseInt(freeMarsModel.getRealm().getProperty("finance_cost_per_colonist"));
        FreeMarsPlayer freeMarsPlayer = (FreeMarsPlayer) freeMarsModel.getActivePlayer();
        int totalCost = colonistCount * financeCostPerColonist;
        totalCost = (int) Utility.modifyByPercent(totalCost, freeMarsPlayer.getFinanceColonizerCostModifier());
        if (freeMarsPlayer.getWealth() >= totalCost) {
            freeMarsPlayer.setFinancedColonistTotal(freeMarsPlayer.getFinancedColonistTotal() + colonistCount);
            getExecutor().execute(new WealthRemoveCommand(freeMarsPlayer, totalCost));
            int currentPopulation = unit.getContainedPopulation();
            int weightPerCitizen = Integer.parseInt(freeMarsModel.getRealm().getProperty("weight_per_citizen"));
            getExecutor().execute(new SetContainerPopulationCommand(unit, currentPopulation + colonistCount, weightPerCitizen));
        }
        return new CommandResult(CommandResult.RESULT_OK, "");
    }
}
