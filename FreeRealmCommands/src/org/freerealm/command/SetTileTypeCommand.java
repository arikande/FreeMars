package org.freerealm.command;

import org.freerealm.Realm;
import org.freerealm.map.Coordinate;
import org.freerealm.tile.TileType;

/**
 * Command class to change type of a tile to anew value. If coordinate or new
 * value of tile type is null, command will throw an exception.
 * 
 * @author Deniz ARIKAN
 */
public class SetTileTypeCommand extends FreeRealmAbstractCommand {

	private final Coordinate coordinate;
	private final TileType tileType;

	/**
	 * Constructs a SetTileTypeCommand using coordinate, tileType
	 * 
	 * @param coordinate
	 *            Tile coordinate for setting new tile type
	 * @param tileType
	 *            New value for tile type
	 */
	public SetTileTypeCommand(Realm realm, Coordinate coordinate, TileType tileType) {
		super(realm);
		this.coordinate = coordinate;
		this.tileType = tileType;

	}

	/**
	 * Executes command to set new tile type to given coordinate.
	 * 
	 * @param realm
	 *            Realm to execute the command
	 * @return CommandResult
	 */
	public void execute() {
		getRealm().getMap().getTile(coordinate).setType(tileType);
		setState(SUCCEEDED);
	}

}
