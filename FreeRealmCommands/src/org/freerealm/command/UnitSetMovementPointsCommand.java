package org.freerealm.command;

import org.commandexecutor.Command;
import org.freerealm.unit.Unit;

/**
 * Command class to set a unit's movement points. When executed
 * UnitSuspendCommand will set movement points of the unit to given parameter.
 * 
 * @author Deniz ARIKAN
 */
public class UnitSetMovementPointsCommand extends FreeRealmAbstractCommand {

	private final Unit unit;
	private final int movementPoints;

	/**
	 * Constructs a UnitSetMovementPointsCommand using unit, movementPoints.
	 * 
	 * @param unit
	 *            Unit to suspend, can not be null.
	 * @param movementPoints
	 *            New movementPoints, can not be less than zero.
	 */
	public UnitSetMovementPointsCommand(Unit unit, int movementPoints) {
		this.unit = unit;
		this.movementPoints = movementPoints;
	}

	/**
	 * Executes command to set given unit's movement points.
	 * 
	 * @param realm
	 *            Realm to execute the command
	 * @return CommandResult
	 */
	public void execute() {
		if (unit == null) {
			setText("Unit is null");
			setState(Command.FAILED);
		} else if (movementPoints < 0) {
			setText("movementPoints is less than zero");
			setState(Command.FAILED);
		} else {
			unit.setMovementPoints(movementPoints);
			setState(Command.SUCCEEDED);
		}
	}

}
