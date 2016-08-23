package org.freerealm.command;

import org.freerealm.Realm;
import org.freerealm.unit.Order;
import org.freerealm.unit.Unit;

/**
 * Command class to assign an order to a given unit. When called, this command
 * will immediately execute the given order and if the order is completed with
 * this execution, unit's order will be set to null. If order is not completed
 * it will be set as unit's order. Passing a null order to
 * UnitOrderAssignCommand will cause a runtime exception.
 * <p>
 * If ordered unit was the player's active unit this command will not find and
 * activate player's next unit. If needed, the command caller must make next
 * unit active.
 * 
 * @author Deniz ARIKAN
 */
public class UnitOrderAddCommand extends FreeRealmAbstractCommand {

	private final Unit unit;
	private final Order order;

	/**
	 * Constructs a UnitOrderAddCommand using unit and order.
	 * 
	 * @param unit
	 *            New order will be assigned to this unit
	 * @param order
	 *            Order to assign
	 */
	public UnitOrderAddCommand(Realm realm, Unit unit, Order order) {
		super(realm);
		this.unit = unit;
		this.order = order;
	}

	/**
	 * Executes command and assigns new order to given unit.
	 * 
	 * @param realm
	 *            Realm to execute the command
	 * @return CommandResult
	 */
	public void execute() {
		if (unit != null) {
			unit.addOrder(order);
			getExecutor().execute(new UnitOrderExecuteCommand(getRealm(), unit));
			putParameter("unit", unit);
			putParameter("order", order);
			setState(SUCCEEDED);
		} else {
			setText("Unit can not be null");
			setState(FAILED);
		}
	}
}
