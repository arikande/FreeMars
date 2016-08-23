package org.freerealm.vegetation;

import java.util.Iterator;
import org.freerealm.tile.TileModifier;
import org.freerealm.tile.TileType;

/**
 *
 * @author Deniz ARIKAN
 */
public interface VegetationType extends TileModifier {

    public boolean canGrowOnTileType(TileType tileType);

    public void addGrowableTileType(TileType tileType);

    public void removeGrowableTileType(TileType tileType);

    public int getGrowableTileTypeCount();

    public Iterator<TileType> getGrowableTileTypesIterator();

    public int getTurnsNeededToClear();
}
