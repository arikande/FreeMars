package org.freerealm.command;

import org.freerealm.settlement.Settlement;
import org.freerealm.settlement.SettlementBuildable;

/**
 * Command class to assign a new production to a settlement. If new production
 * is a settlement improvement that the settlement already has, an error will be
 * returned. New value for production can be null. Continuous production can
 * also be set/unset with this command.
 * 
 * @author Deniz ARIKAN
 */
public class AddToSettlementProductionQueueCommand extends FreeRealmAbstractCommand {

	private final Settlement settlement;
	private final SettlementBuildable buildable;

	/**
	 * Constructs a AddToSettlementProductionQueueCommand using settlement,
	 * buildable, contiuousProduction.
	 * 
	 * @param settlement
	 *            Settlement to assign new production
	 * @param buildable
	 *            New production assignment
	 */
	public AddToSettlementProductionQueueCommand(Settlement settlement, SettlementBuildable buildable) {
		this.settlement = settlement;
		this.buildable = buildable;
	}

	/**
	 * Executes command to assign new production and mode to given settlement.
	 * 
	 * @return CommandResult
	 */
	public void execute() {
		if (!settlement.canStartBuild(buildable)) {
			setText("Settlement cannot build " + buildable);
			setState(FAILED);
		} else {
			settlement.addToProductionQueue(buildable);
			setState(FAILED);
		}
	}
}
