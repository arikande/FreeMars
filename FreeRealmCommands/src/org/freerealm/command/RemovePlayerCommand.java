package org.freerealm.command;

import java.util.ArrayList;
import java.util.Iterator;

import org.freerealm.Realm;
import org.freerealm.player.Player;
import org.freerealm.settlement.Settlement;
import org.freerealm.tile.Tile;
import org.freerealm.unit.Unit;

/**
 * Command class to remove a player from the realm.
 * 
 * @author Deniz ARIKAN
 */
public class RemovePlayerCommand extends FreeRealmAbstractCommand {

	private final Player player;

	/**
	 * Constructs an RemovePlayerCommand using player.
	 * 
	 * @param player
	 *            Player to remove
	 */
	public RemovePlayerCommand(Realm realm, Player player) {
		super(realm);
		this.player = player;
	}

	/**
	 * Executes command and removes the given player from realm.
	 * 
	 * @param realm
	 *            Realm to execute the command
	 * @return CommandResult
	 */
	public void execute() {
		Iterator<Settlement> settlementIterator = player.getSettlementsIterator();
		while (settlementIterator.hasNext()) {
			Settlement settlement = settlementIterator.next();
			Tile tile = settlement.getTile();
			tile.setSettlement(null);
		}
		ArrayList<Unit> unitsToRemove = new ArrayList<Unit>();
		Iterator<Unit> unitIterator = player.getUnitsIterator();
		while (unitIterator.hasNext()) {
			unitsToRemove.add(unitIterator.next());
		}
		getExecutor().execute(new RemoveUnitsCommand(getRealm(), player, unitsToRemove));
		player.setStatus(Player.STATUS_REMOVED);
		putParameter("player", player);
		setState(SUCCEEDED);
	}
}
