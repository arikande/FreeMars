package org.freerealm.command;

import org.commandexecutor.Command;
import org.freerealm.unit.Unit;

/**
 * 
 * @author Deniz ARIKAN
 */
public class SkipUnitCommand extends FreeRealmAbstractCommand {

	private final Unit unit;

	public SkipUnitCommand(Unit unit) {
		this.unit = unit;
	}

	/**
	 * Executes command to skip given unit for player.
	 * 
	 * @return CommandResult
	 */
	public void execute() {
		if (unit == null) {
			setState(Command.FAILED);
		} else {
			getExecutor().execute(new UnitSetMovementPointsCommand(unit, 0));
			unit.setSkippedForCurrentTurn(true);
			setState(Command.SUCCEEDED);
			putParameter("skipped_unit", unit);
		}
	}

}
