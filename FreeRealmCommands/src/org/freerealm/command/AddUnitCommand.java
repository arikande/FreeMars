package org.freerealm.command;

import org.freerealm.Realm;
import org.freerealm.player.Player;
import org.freerealm.unit.Unit;

/**
 * Command class to add a new unit to given player. Command will also put the
 * given unit to realm's world map and add unit's visible coordinate set to
 * explored coordinates of player. Since these operations assume that unit has a
 * coordinate AddUnitCommand will throw a runtime exception if unit's coordinate
 * is null.
 * 
 * @author Deniz ARIKAN
 */
public class AddUnitCommand extends FreeRealmAbstractCommand {

	private Player player;
	private Unit unit;
	private int status;

	/**
	 * Constructs an AddUnitCommand using player, unit.
	 * 
	 * @param player
	 *            Player to add given unit
	 * @param unit
	 *            New unit
	 */
	public AddUnitCommand(Realm realm, Player player, Unit unit, int status) {
		super(realm);
		this.player = player;
		this.unit = unit;
		this.status = status;
	}

	/**
	 * Constructs an AddUnitCommand using player, unit.
	 * 
	 * @param player
	 *            Player to add given unit
	 * @param unit
	 *            New unit
	 */
	public AddUnitCommand(Realm realm, Player player, Unit unit) {
		this(realm, player, unit, Unit.UNIT_ACTIVE);
	}

	/**
	 * Executes command to add a new unit to given player.
	 * 
	 * @return CommandResult
	 */
	public void execute() {
		player.addUnit(unit);
		if (unit.getName() == null || unit.getName().trim().equals("")) {
			unit.setName(unit.toString());
		}
		if (status == Unit.UNIT_ACTIVE) {
			getExecutor().execute(new UnitActivateCommand(getRealm(), unit, unit.getCoordinate()));
			unit.setMovementPoints(unit.getType().getMovementPoints());
		} else if (status == Unit.UNIT_SUSPENDED) {
			getExecutor().execute(new UnitSuspendCommand(getRealm(), player, unit));
		}
		setState(SUCCEEDED);
	}
}
