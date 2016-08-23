package org.freerealm.tile;

import java.util.Iterator;
import org.freerealm.modifier.Modifier;

/**
 *
 * @author Deniz ARIKAN
 */
public interface TileModifier extends Modifier {

    public boolean hasTileType(TileType tileType);

    public void addTileType(TileType tileType);

    public void removeTileType(TileType tileType);

    public int getTileTypeCount();

    public Iterator<TileType> getTileTypesIterator();

}
