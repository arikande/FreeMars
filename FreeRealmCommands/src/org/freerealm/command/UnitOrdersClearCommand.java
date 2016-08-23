package org.freerealm.command;

import org.freerealm.unit.Unit;

/**
 * Command class to clear an order from a given unit. When called this command
 * will clear all orders of the given unit.
 * 
 * @author Deniz ARIKAN
 */
public class UnitOrdersClearCommand extends FreeRealmAbstractCommand {

	private final Unit unit;

	/**
	 * Constructs a UnitOrdersClearCommand using unit.
	 * 
	 * @param unit
	 *            This unit's orders will be cleared.
	 */
	public UnitOrdersClearCommand(Unit unit) {
		this.unit = unit;
	}

	/**
	 * Executes command and clears orders of given unit.
	 * 
	 * @param realm
	 *            Realm to execute the command
	 * @return CommandResult
	 */
	public void execute() {
		if (unit != null) {
			unit.setCurrentOrder(null);
			unit.clearQueuedOrders();
			putParameter("unit", unit);
			setState(SUCCEEDED);
		} else {
			setText("Unit can not be null");
			setState(FAILED);
		}
	}

}
