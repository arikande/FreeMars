package org.freerealm.command;

import org.freerealm.settlement.Settlement;

/**
 * Command class to remove given index from production queue of a settlement.
 * 
 * @author Deniz ARIKAN
 */
public class RemoveFromSettlementProductionQueueCommand extends FreeRealmAbstractCommand {

	private final Settlement settlement;
	private final int index;

	/**
	 * Constructs a AddToSettlementProductionQueueCommand using settlement,
	 * index.
	 * 
	 * @param settlement
	 *            City to assign new production
	 * @param index
	 *            Index of element to remove
	 */
	public RemoveFromSettlementProductionQueueCommand(Settlement settlement, int index) {
		this.settlement = settlement;
		this.index = index;
	}

	/**
	 * Executes command to remove given index.
	 * 
	 * @param realm
	 *            Realm to execute the command
	 * @return CommandResult
	 */
	public void execute() {
		settlement.removeFromProductionQueue(index);
		setState(SUCCEEDED);
	}
}
