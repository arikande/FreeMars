package org.freerealm.command;

import org.freerealm.Realm;
import org.freerealm.map.Coordinate;
import org.freerealm.resource.bonus.BonusResource;
import org.freerealm.tile.Tile;

/**
 * Command class to set a tile's bonus resource. If bonus resource is null
 * current bonus resource on given tile is removed.
 * 
 * @author Deniz ARIKAN
 */
public class SetTileBonusResourceCommand extends FreeRealmAbstractCommand {

	private Tile tile;
	private Coordinate coordinate;
	private BonusResource bonusResource;

	/**
	 * Constructs a SetTileBonusResourceCommand using tile, bonusResource
	 * 
	 * @param tile
	 *            Tile for setting vegetation
	 * @param bonusResource
	 *            New value for bonusResource
	 */
	public SetTileBonusResourceCommand(Realm realm, Tile tile, BonusResource bonusResource) {
		super(realm);
		this.tile = tile;
		this.bonusResource = bonusResource;
	}

	/**
	 * Constructs a SetTileBonusResourceCommand using coordinate, bonusResource
	 * 
	 * @param coordinate
	 *            Tile coordinate for setting vegetation
	 * @param bonusResource
	 *            New value for bonusResource
	 */
	public SetTileBonusResourceCommand(Realm realm, Coordinate coordinate, BonusResource bonusResource) {
		super(realm);
		this.coordinate = coordinate;
		this.bonusResource = bonusResource;
	}

	/**
	 * Executes command to set new bonus resource to given tile or coordinate.
	 * 
	 * @param realm
	 *            Realm to execute the command
	 * @return CommandResult
	 */
	public void execute() {
		if (tile == null) {
			if (coordinate == null) {
				setText("Coordinate cannot be null.");
				setState(FAILED);
				return;
			}
			tile = getRealm().getMap().getTile(coordinate);
		}
		if (tile == null) {
			setText("Tile cannot be null.");
			setState(FAILED);
			return;
		}
		if (bonusResource != null && !bonusResource.hasTileType(tile.getType())) {
			setText("Given bonus resource cannot exist on this type of tile.");
			setState(FAILED);
			return;
		}
		tile.setBonusResource(bonusResource);
		putParameter("coordinate", coordinate);
		setState(SUCCEEDED);
	}

}
