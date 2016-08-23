package org.freerealm.command;

import org.freerealm.Realm;
import org.freerealm.unit.Unit;

/**
 * Command class to remove a player's unit from realm's world map.
 * 
 * @author Deniz ARIKAN
 */
public class RemoveUnitFromMapCommand extends FreeRealmAbstractCommand {

	private final Unit unit;

	/**
	 * Constructs a RemoveUnitFromWorldMapCommand using player, unit.
	 * 
	 * @param player
	 *            Player to remove unit from
	 * @param unit
	 *            Unit to remove from realm's world map
	 */
	public RemoveUnitFromMapCommand(Realm realm, Unit unit) {
		super(realm);
		this.unit = unit;
	}

	/**
	 * Executes command and removes given unit.
	 * 
	 * @param realm
	 *            Realm to execute the command
	 * @return CommandResult
	 */
	public void execute() {
		getRealm().getMap().removeUnit(unit);
		setState(SUCCEEDED);
	}

}
