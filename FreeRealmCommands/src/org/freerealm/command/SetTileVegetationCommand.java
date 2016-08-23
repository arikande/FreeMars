package org.freerealm.command;

import org.freerealm.Realm;
import org.freerealm.map.Coordinate;
import org.freerealm.tile.Tile;
import org.freerealm.vegetation.Vegetation;

/**
 * Command class to set a tile's vegetation. If vegetation is null current
 * vegetation on given tile is removed.
 * 
 * @author Deniz ARIKAN
 */
public class SetTileVegetationCommand extends FreeRealmAbstractCommand {

	private final Coordinate coordinate;
	private final Vegetation vegetation;

	/**
	 * Constructs a SetTileVegetationCommand using coordinate, vegetation
	 * 
	 * @param coordinate
	 *            Tile coordinate for setting vegetation
	 * @param vegetation
	 *            New value for vegetation
	 */
	public SetTileVegetationCommand(Realm realm, Coordinate coordinate, Vegetation vegetation) {
		super(realm);
		this.coordinate = coordinate;
		this.vegetation = vegetation;
	}

	/**
	 * Executes command to set new vegetation to given tile or coordinate.
	 * 
	 * @param realm
	 *            Realm to execute the command
	 * @return CommandResult
	 */
	public void execute() {
		if (coordinate == null) {
			setText("Coordinate cannot be null.");
			setState(FAILED);
			return;
		}
		Tile tile = getRealm().getMap().getTile(coordinate);
		if (tile == null) {
			setText("Tile cannot be null.");
			setState(FAILED);
			return;
		}
		if (vegetation != null && !vegetation.getType().canGrowOnTileType(tile.getType())) {
			setText("Given vegetation  cannot grow on this type of tile.");
			setState(FAILED);
			return;
		}
		tile.setVegetation(vegetation);
		putParameter("coordinate", coordinate);
		setState(SUCCEEDED);
	}

}
