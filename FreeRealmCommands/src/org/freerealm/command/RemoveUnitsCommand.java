package org.freerealm.command;

import java.util.ArrayList;
import java.util.Iterator;

import org.freerealm.Realm;
import org.freerealm.player.Player;
import org.freerealm.unit.Unit;

/**
 * Command class to remove a group of units from a player.
 * 
 * @author Deniz ARIKAN
 */
public class RemoveUnitsCommand extends FreeRealmAbstractCommand {

	private final Player player;
	private final ArrayList<Unit> units;

	/**
	 * Constructs a RemoveUnitsCommand using player, units.
	 * 
	 * @param player
	 *            Player to remove unit from
	 * @param units
	 *            Vector containing units to remove from player and realm's
	 *            world map
	 */
	public RemoveUnitsCommand(Realm realm, Player player, ArrayList<Unit> units) {
		super(realm);
		this.player = player;
		this.units = units;
	}

	/**
	 * Executes command and removes given group of units.
	 * 
	 * @param realm
	 *            Realm to execute the command
	 * @return CommandResult
	 */
	public void execute() {
		Iterator<Unit> unitsIterator = units.iterator();
		while (unitsIterator.hasNext()) {
			Unit unit = unitsIterator.next();
			getExecutor().execute(new RemoveUnitCommand(getRealm(), player, unit));
		}
		setState(SUCCEEDED);
	}
}
