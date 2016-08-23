package org.freerealm.command;

import org.freerealm.settlement.Settlement;

/**
 * 
 * @author Deniz ARIKAN
 */
public class ClearSettlementProductionQueueCommand extends FreeRealmAbstractCommand {

	private final Settlement settlement;

	public ClearSettlementProductionQueueCommand(Settlement settlement) {
		this.settlement = settlement;
	}

	/**
	 * Executes command to assign new production and mode to given settlement.
	 * 
	 * @return CommandResult
	 */
	public void execute() {
		settlement.clearProductionQueue();
		setState(SUCCEEDED);
	}
}
