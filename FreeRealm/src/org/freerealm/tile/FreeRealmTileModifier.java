package org.freerealm.tile;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.freerealm.modifier.FreeRealmModifier;

/**
 *
 * @author Deniz ARIKAN
 */
public class FreeRealmTileModifier extends FreeRealmModifier implements TileModifier {

    private final Map<Integer, TileType> tileTypes;

    public FreeRealmTileModifier() {
        tileTypes = new HashMap<Integer, TileType>();
    }

    public boolean hasTileType(TileType tileType) {
        return getTileTypes().containsKey(tileType.getId());
    }

    public void addTileType(TileType tileType) {
        getTileTypes().put(tileType.getId(), tileType);
    }

    public void removeTileType(TileType tileType) {
        getTileTypes().remove(tileType.getId());
    }

    public int getTileTypeCount() {
        return getTileTypes().size();
    }

    public Iterator<TileType> getTileTypesIterator() {
        return getTileTypes().values().iterator();
    }

    private Map<Integer, TileType> getTileTypes() {
        return tileTypes;
    }
}
