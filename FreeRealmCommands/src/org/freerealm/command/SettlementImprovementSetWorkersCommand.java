package org.freerealm.command;

import org.freerealm.settlement.Settlement;
import org.freerealm.settlement.improvement.SettlementImprovement;

/**
 * 
 * @author Deniz ARIKAN
 */
public class SettlementImprovementSetWorkersCommand extends FreeRealmAbstractCommand {

	private final Settlement settlement;
	private final SettlementImprovement settlementImprovement;
	private final int workers;

	public SettlementImprovementSetWorkersCommand(Settlement settlement, SettlementImprovement settlementImprovement, int workers) {
		this.settlement = settlement;
		this.settlementImprovement = settlementImprovement;
		this.workers = workers;
	}

	public void execute() {
		int availableWorkers = settlement.getProductionWorkforce() + settlementImprovement.getNumberOfWorkers();
		int workersToAssign = (availableWorkers > workers ? workers : availableWorkers);
		workersToAssign = (settlementImprovement.getType().getMaximumWorkers() > workersToAssign ? workersToAssign : settlementImprovement.getType().getMaximumWorkers());
		settlementImprovement.setNumberOfWorkers(workersToAssign);
		if (workersToAssign > 0) {
			getExecutor().execute(new SettlementImprovementSetEnabledCommand(settlement, settlementImprovement, true));
		}
		setState(SUCCEEDED);
	}
}
