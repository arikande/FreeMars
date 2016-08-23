package org.freerealm.command;

import org.freerealm.settlement.Settlement;
import org.freerealm.settlement.improvement.SettlementImprovement;

/**
 * 
 * @author Deniz ARIKAN
 */
public class SettlementImprovementSetEnabledCommand extends FreeRealmAbstractCommand {

	private final Settlement settlement;
	private final SettlementImprovement settlementImprovement;
	private final boolean enabled;

	public SettlementImprovementSetEnabledCommand(Settlement settlement, SettlementImprovement settlementImprovement, boolean enabled) {
		this.settlement = settlement;
		this.settlementImprovement = settlementImprovement;
		this.enabled = enabled;
	}

	public void execute() {
		settlementImprovement.setEnabled(enabled);
		if (!enabled) {
			getExecutor().execute(new SettlementImprovementSetWorkersCommand(settlement, settlementImprovement, 0));
		}
		setState(SUCCEEDED);
	}
}
