package org.freemars.editor.controller.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import org.freerealm.Realm;
import org.freerealm.command.FreeRealmAbstractCommand;
import org.freerealm.command.SetTileVegetationCommand;
import org.freerealm.map.Coordinate;
import org.freerealm.tile.Tile;
import org.freerealm.tile.TileType;
import org.freerealm.vegetation.FreeRealmVegetation;
import org.freerealm.vegetation.Vegetation;
import org.freerealm.vegetation.VegetationType;

/**
 * 
 * @author Deniz ARIKAN
 */
public class ConvertTilesCommand extends FreeRealmAbstractCommand {

	public static final String NAME = "ConvertTilesCommand";
	private final Realm realm;
	private final TileType fromTileType;
	private final VegetationType fromVegetationType;
	private final TileType toTileType;
	private final VegetationType toVegetationType;
	private final int numberOfTilesToConvert;

	public ConvertTilesCommand(Realm realm, TileType fromTileType, VegetationType fromVegetationType, TileType toTileType, VegetationType toVegetationType, int numberOfTilesToConvert) {
		this.realm = realm;
		this.fromTileType = fromTileType;
		this.fromVegetationType = fromVegetationType;
		this.toTileType = toTileType;
		this.toVegetationType = toVegetationType;
		this.numberOfTilesToConvert = numberOfTilesToConvert;
	}

	public void execute() {
		int numberOfTilesConverted = 0;
		List<Coordinate> candidateCoordinates = new ArrayList<Coordinate>();
		for (int i = 0; i < realm.getMapWidth(); i++) {
			for (int j = 0; j < realm.getMapHeight(); j++) {
				Coordinate coordinate = new Coordinate(i, j);
				Tile tile = realm.getTile(coordinate);
				if (tile.getType().equals(fromTileType) && isVegetationCandidate(tile)) {
					candidateCoordinates.add(coordinate);
				}
			}
		}
		if (candidateCoordinates.size() > numberOfTilesToConvert) {
			for (int i = 0; i < numberOfTilesToConvert; i++) {
				int index = new Random().nextInt(candidateCoordinates.size());
				convertCoordinate(candidateCoordinates.get(index));
				numberOfTilesConverted = numberOfTilesConverted + 1;
				candidateCoordinates.remove(index);
			}
		} else {
			for (Coordinate candidateCoordinate : candidateCoordinates) {
				convertCoordinate(candidateCoordinate);
				numberOfTilesConverted = numberOfTilesConverted + 1;
			}
		}
		setText(numberOfTilesConverted + " tiles converted");
		putParameter("number_of_tiles_converted", numberOfTilesConverted);
		setState(SUCCEEDED);
	}

	private void convertCoordinate(Coordinate coordinate) {
		Tile convertTile = realm.getTile(coordinate);
		convertTile.setType(toTileType);
		Properties tileProperties = new Properties();
		tileProperties.setProperty("imageType", "0" + new Random().nextInt(4));
		convertTile.setCustomProperties(tileProperties);
		Vegetation vegetation = null;
		if (toVegetationType != null) {
			vegetation = new FreeRealmVegetation();
			vegetation.setType(toVegetationType);
			Properties vegetationProperties = new Properties();
			vegetationProperties.setProperty("imageType", "0" + new Random().nextInt(4));
			vegetation.setCustomProperties(vegetationProperties);
		}
		SetTileVegetationCommand setTileVegetationCommand = new SetTileVegetationCommand(realm, coordinate, vegetation);
		getExecutor().execute(setTileVegetationCommand);
	}

	@Override
	public String getName() {
		return NAME;
	}

	private boolean isVegetationCandidate(Tile tile) {
		if (fromVegetationType == null && tile.getVegetation() == null) {
			return true;
		} else if (tile.getVegetation() != null && tile.getVegetation().getType().equals(fromVegetationType)) {
			return true;
		}
		return false;
	}

}
