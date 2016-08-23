package org.freerealm.command;

import org.freerealm.Realm;
import org.freerealm.map.Coordinate;

/**
 * Command class to remove all improvements of a tile
 * 
 * @author Deniz ARIKAN
 */
public class ClearTileImprovementsCommand extends FreeRealmAbstractCommand {

	private final Coordinate coordinate;

	/**
	 * Constructs a ClearTileImprovementsCommand using coordinate
	 * 
	 * @param coordinate
	 *            Tile coordinate to clear all improvements
	 */
	public ClearTileImprovementsCommand(Realm realm, Coordinate coordinate) {
		super(realm);
		this.coordinate = coordinate;
	}

	/**
	 * Executes command to remove all improvements of a tile
	 * 
	 * @return CommandResult
	 */
	public void execute() {
		getRealm().getMap().getTile(coordinate).clearImprovements();
		setState(SUCCEEDED);
	}
}
