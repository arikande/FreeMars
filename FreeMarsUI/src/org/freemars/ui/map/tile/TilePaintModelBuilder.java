package org.freemars.ui.map.tile;

import java.awt.Image;
import org.freemars.ui.image.FreeMarsImageManager;
import org.freerealm.Realm;
import org.freerealm.RealmConstants;
import org.freerealm.map.Coordinate;
import org.freerealm.map.Direction;
import org.freerealm.tile.Tile;

/**
 *
 * @author Deniz ARIKAN
 */
public class TilePaintModelBuilder {

    public static void buildModelForTileTerrain(Realm realm, TilePaintModel tilePaintModel, Coordinate tileCoordinate, int gridWidth, int gridHeight) {
        Tile tile = realm.getTile(tileCoordinate);
        tilePaintModel.setWidth(gridWidth);
        tilePaintModel.setHeight(gridHeight);
        tilePaintModel.setTileTypeName(tile.getType().getName());
        tilePaintModel.setTileCoordinate(tileCoordinate);
        tilePaintModel.clearTerrainImages();
        Image image = FreeMarsImageManager.getImage(tile);
        image = FreeMarsImageManager.createResizedCopy(image, gridWidth + 1, gridHeight + 1, false, null);
        tilePaintModel.addTerrainImage(RealmConstants.CENTER, image);
        for (Direction direction : RealmConstants.directions) {
            if (!direction.equals(RealmConstants.CENTER)) {
                Coordinate adjacentCoordinate = realm.getRelativeCoordinate(tileCoordinate, direction);
                if (adjacentCoordinate != null) {
                    Tile adjacentTile = realm.getTile(adjacentCoordinate);
                    if (adjacentTile != null && adjacentTile.getType().getId() <= tile.getType().getId()) {
                        Image transitionImage = FreeMarsImageManager.getImage("TILE_" + adjacentTile.getType().getName().replaceAll(" ", "") + "_" + direction.getShortName());
                        transitionImage = FreeMarsImageManager.createResizedCopy(transitionImage, gridWidth + 1, gridHeight + 1, false, null);
                        tilePaintModel.addTerrainImage(direction, transitionImage);
                    }
                }
            }
        }
        tilePaintModel.setVegetationImage(null);
        if (tile.getVegetation() != null) {
            Image vegetationImage = FreeMarsImageManager.getImage(tile.getVegetation());
            vegetationImage = FreeMarsImageManager.createResizedCopy(vegetationImage, gridWidth, gridHeight, false, null);
            tilePaintModel.setVegetationImage(vegetationImage);
        }
        tilePaintModel.setResourceBonusImage(null);
        if (tile.getBonusResource() != null) {
            Image resourceBonusImage = FreeMarsImageManager.getImage(tile.getBonusResource());
            resourceBonusImage = FreeMarsImageManager.createResizedCopy(resourceBonusImage, gridWidth / 5, gridWidth / 5, false, null);
            tilePaintModel.setResourceBonusImage(resourceBonusImage);
        }
    }

}
