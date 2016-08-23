package org.freerealm.command;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import org.freerealm.Realm;
import org.freerealm.Utility;
import org.freerealm.map.AStarPathFinder;
import org.freerealm.map.Coordinate;
import org.freerealm.map.FreeRealmMap;
import org.freerealm.resource.bonus.BonusResource;
import org.freerealm.tile.FreeRealmTile;
import org.freerealm.tile.Tile;
import org.freerealm.tile.TileType;
import org.freerealm.vegetation.FreeRealmVegetation;
import org.freerealm.vegetation.Vegetation;
import org.freerealm.vegetation.VegetationType;

/**
 * Command class to create a new map for the realm. Newly created map will
 * replace current map of the realm and all items (units, cities etc.) will be
 * reset. Map width and height must be defined for new map, if any of these
 * values are negative command will return an error.
 * <p>
 * In addition to width and height, tile type can be also be specified. In this
 * case all of the new map's tiles have the given type. If tile type is not
 * given probabilities defined in tile type configuration will be used to
 * randomly generate tile types. If tile type is defined but is null a runtime
 * exception will occur.
 * 
 * @author Deniz ARIKAN
 */
public class CreateMapCommand extends FreeRealmAbstractCommand {

	private int width = 0;
	private int height = 0;
	private TileType useTileType;
	private Random rand = new Random(System.currentTimeMillis());

	/**
	 * Constructs a CreateMapCommand using width, height.
	 * 
	 * @param width
	 *            Width of new map
	 * @param height
	 *            Height of new map
	 */
	public CreateMapCommand(Realm realm, int width, int height) {
		super(realm);
		this.width = width;
		this.height = height;
	}

	/**
	 * Constructs a CreateMapCommand using tileType, width, height.
	 * 
	 * @param tileType
	 *            All tiles in new map will be of this type
	 * @param width
	 *            Width of new map
	 * @param height
	 *            Height of new map
	 */
	public CreateMapCommand(Realm realm, TileType tileType, int width, int height) {
		this(realm, width, height);
		this.useTileType = tileType;
	}

	/**
	 * Executes command to create a new map for realm.
	 * 
	 * @return CommandResult
	 */
	public void execute() {
		Tile[][] mapItems = new Tile[width][height];
		FreeRealmMap freeRealmMap = new FreeRealmMap();
		freeRealmMap.setMapItems(mapItems);
		getRealm().setMap(freeRealmMap);
		if (useTileType == null) {
			int probabilityTotal = 0;
			Iterator<TileType> tileIterator = getRealm().getTileTypeManager().getTileTypesIterator();
			while (tileIterator.hasNext()) {
				TileType tileType = tileIterator.next();
				probabilityTotal = probabilityTotal + tileType.getProbability();
			}
			for (int i = 0; i < width; i++) {
				for (int j = 0; j < height; j++) {
					TileType tileType = generateTileType(probabilityTotal);
					mapItems[i][j] = new FreeRealmTile(tileType);
					addRandomVegetation(new Coordinate(i, j));
					addRandomBonusResource(mapItems[i][j]);
				}
			}
		} else {
			for (int i = 0; i < width; i++) {
				for (int j = 0; j < height; j++) {
					mapItems[i][j] = new FreeRealmTile(useTileType);
				}
			}
		}

		getRealm().setPathFinder(new AStarPathFinder(getRealm(), 100));
		setState(SUCCEEDED);
	}

	private TileType generateTileType(int probabilityTotal) {
		int randomNumber = rand.nextInt(probabilityTotal);
		Iterator<TileType> tileIterator = getRealm().getTileTypeManager().getTileTypesIterator();
		TileType tileType = null;
		while (tileIterator.hasNext()) {
			tileType = (TileType) tileIterator.next();
			randomNumber = randomNumber - tileType.getProbability();
			if (randomNumber < 0) {
				return tileType;
			}
		}
		return tileType;
	}

	private void addRandomVegetation(Coordinate coordinate) {
		Tile tile = getRealm().getTile(coordinate);
		int vegetationExistsRandomNumber = rand.nextInt(2);
		if (vegetationExistsRandomNumber == 0) {
			ArrayList<VegetationType> vegetationTypes = Utility.getVegetationTypesAvailableForTileType(getRealm(), tile.getType());
			if (vegetationTypes.size() > 0) {
				int vegetationTypeRandomNumber = rand.nextInt(vegetationTypes.size());
				Vegetation vegetation = new FreeRealmVegetation();
				vegetation.setType(vegetationTypes.get(vegetationTypeRandomNumber));
				getExecutor().execute(new SetTileVegetationCommand(getRealm(), coordinate, vegetation));
			}
		}
	}

	private void addRandomBonusResource(Tile tile) {
		int bonusResourceExistsRandomNumber = rand.nextInt(5);
		if (bonusResourceExistsRandomNumber == 0) {
			ArrayList<BonusResource> bonusResourceTypes = Utility.getBonusResourceTypesAvailableForTileType(getRealm(), tile.getType());
			if (bonusResourceTypes.size() > 0) {
				int bonusResourceTypeRandomNumber = rand.nextInt(bonusResourceTypes.size());
				BonusResource bonusResource = bonusResourceTypes.get(bonusResourceTypeRandomNumber);
				getExecutor().execute(new SetTileBonusResourceCommand(getRealm(), tile, bonusResource));
			}
		}
	}
}
