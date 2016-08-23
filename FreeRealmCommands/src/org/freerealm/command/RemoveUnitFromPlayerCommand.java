package org.freerealm.command;

import org.freerealm.player.Player;
import org.freerealm.unit.Unit;

/**
 * Command class to remove a player's unit. If removed unit was the player's
 * active unit this command will not find and activate player's next unit, it
 * will just set the active unit of player to null. If needed, the command
 * caller must to make next available unit active.
 * <p>
 * Passing a null player into the command will result in a runtime exception. If
 * unit does not belong to given player command will return an error.
 * 
 * @author Deniz ARIKAN
 */
public class RemoveUnitFromPlayerCommand extends FreeRealmAbstractCommand {

	private final Player player;
	private final Unit unit;

	/**
	 * Constructs a RemoveUnitCommand using player, unit.
	 * 
	 * @param player
	 *            Player to remove unit from
	 * @param unit
	 *            Unit to remove from player and realm's world map
	 */
	public RemoveUnitFromPlayerCommand(Player player, Unit unit) {
		this.player = player;
		this.unit = unit;
	}

	/**
	 * Executes command and removes given unit.
	 * 
	 * @return CommandResult
	 */
	public void execute() {
		if ((unit != null) && (!player.hasUnit(unit))) {
			setText("Unit does not belong to player");
			setState(FAILED);
		}
		if (player.getActiveUnit() != null && player.getActiveUnit().equals(unit)) {
			getExecutor().execute(new SetActiveUnitCommand(player, null));
		}
		getExecutor().execute(new UnitSetAutomaterCommand(unit, null));
		getExecutor().execute(new UnitOrdersClearCommand(unit));
		unit.setPlayer(null);
		player.removeUnit(unit);
		setState(SUCCEEDED);
	}

}
