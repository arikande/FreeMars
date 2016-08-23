package org.freerealm.command;

import org.freerealm.Realm;
import org.freerealm.player.Player;
import org.freerealm.unit.Unit;

/**
 * Command class to suspend a player's unit. When executed UnitSuspendCommand
 * will set movement points of the unit to 0, remove it from the world map, set
 * its coordinate to null and update its status as UNIT_SUSPENDED.
 * <p>
 * Suspending a unit is removing it from realm but not deleting it. Once
 * suspended, a unit can not move, attack or be attacked. If removed unit was
 * the player's active unit this command will not find and activate player's
 * next unit, it will just set the active unit of player to null. If needed, the
 * command caller must to make next available unit active.
 * <p>
 * Passing a null player into the command will result in a runtime exception. If
 * unit does not belong to given player command will return an error.
 * 
 * @author Deniz ARIKAN
 */
public class UnitSuspendCommand extends FreeRealmAbstractCommand {

	private final Player player;
	private final Unit unit;

	/**
	 * Constructs a UnitSuspendCommand using unit.
	 * 
	 * @param unit
	 *            Unit to suspend, can not be null
	 */
	public UnitSuspendCommand(Realm realm, Player player, Unit unit) {
		super(realm);
		this.player = player;
		this.unit = unit;
	}

	/**
	 * Executes command to suspend given unit.
	 * 
	 * @return CommandResult
	 */
	public void execute() {
		if ((unit != null) && (!player.hasUnit(unit))) {
			setText("Unit does not belong to active user");
			setState(FAILED);
			return;
		}
		if (player.getActiveUnit() != null && player.getActiveUnit().equals(unit)) {
			player.setActiveUnit(null);
		}
		putParameter("unit", unit);
		putParameter("coordinate", unit.getCoordinate());
		getExecutor().execute(new UnitSetMovementPointsCommand(unit, 0));
		unit.setStatus(Unit.UNIT_SUSPENDED);
		getRealm().getMap().removeUnit(unit);
		unit.setCoordinate(null);
		setState(SUCCEEDED);
	}
}
