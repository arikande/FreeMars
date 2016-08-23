package org.freerealm.vegetation;

import java.util.Iterator;
import org.freerealm.tile.FreeRealmTileModifier;
import org.freerealm.tile.TileType;

/**
 *
 * @author Deniz ARIKAN
 */
public class FreeRealmVegetationType extends FreeRealmTileModifier implements VegetationType {

    private int turnsNeededToClear;

    public boolean canGrowOnTileType(TileType tileType) {
        return hasTileType(tileType);
    }

    public void addGrowableTileType(TileType tileType) {
        addTileType(tileType);
    }

    public void removeGrowableTileType(TileType tileType) {
        removeTileType(tileType);
    }

    public int getGrowableTileTypeCount() {
        return getTileTypeCount();
    }

    public Iterator<TileType> getGrowableTileTypesIterator() {
        return getTileTypesIterator();
    }

    public int getTurnsNeededToClear() {
        return turnsNeededToClear;
    }

    public void setTurnsNeededToClear(int turnsNeededToClear) {
        this.turnsNeededToClear = turnsNeededToClear;
    }
}
