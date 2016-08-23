package org.freerealm.command;

import org.commandexecutor.Command;
import org.freerealm.player.Player;
import org.freerealm.unit.Unit;

/**
 * Command class to set active unit of a player, value of new unit can be null
 * to indicate none of the units of player are currently active. Passing a null
 * player into the command will result in a runtime exception. If unit does not
 * belong to given player command will return an error.
 * 
 * @author Deniz ARIKAN
 */
public class SetActiveUnitCommand extends FreeRealmAbstractCommand {

	private final Player player;
	private final Unit unit;

	/**
	 * Constructs a SetActiveUnitCommand using player, unit.
	 * 
	 * @param player
	 *            Player to set active unit
	 * @param unit
	 *            Unit to set active, can be null
	 */
	public SetActiveUnitCommand(Player player, Unit unit) {
		this.player = player;
		this.unit = unit;
	}

	/**
	 * Executes command to activate given unit for player.
	 * 
	 * @return CommandResult
	 */
	public void execute() {
		if ((unit != null) && (!player.hasUnit(unit))) {
			setText("Unit does not belong to active user");
			setState(Command.FAILED);
		} else {
			putParameter("previous_active_unit", player.getActiveUnit());
			player.setActiveUnit(unit);
			putParameter("active_unit", unit);
			setState(Command.SUCCEEDED);
		}
	}

}
