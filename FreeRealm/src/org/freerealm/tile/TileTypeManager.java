package org.freerealm.tile;

import java.util.Iterator;
import java.util.TreeMap;
import org.freerealm.resource.Resource;

/**
 *
 * @author Deniz ARIKAN
 */
public class TileTypeManager {

    private TreeMap<Integer, TileType> tileTypes = null;

    public TileTypeManager() {
        tileTypes = new TreeMap<Integer, TileType>();
    }

    public TileType getTileType(int id) {
        return getTileTypes().get(id);
    }

    public TileType getTileType(String name) {
        TileType returnValue = null;
        Iterator<TileType> tileTypesIterator = getTileTypesIterator();
        while (tileTypesIterator.hasNext()) {
            TileType tileType = tileTypesIterator.next();
            if (tileType.getName().equals(name)) {
                returnValue = tileType;
                break;
            }
        }
        return returnValue;
    }

    public void addTileType(TileType tileType) {
        getTileTypes().put(tileType.getId(), tileType);
    }

    private TreeMap<Integer, TileType> getTileTypes() {
        return tileTypes;
    }

    public Iterator<TileType> getTileTypesIterator() {
        return getTileTypes().values().iterator();
    }

    public int getTileTypeCount() {
        return getTileTypes().size();
    }

    public TileType getMaxProducingTileType(Resource resource) {
        TileType resourceTileType = null;
        int resourceProduction = 0;
        for (Iterator<TileType> it = getTileTypesIterator(); it.hasNext();) {
            TileType tileType = it.next();
            if (tileType.getProduction(resource) > resourceProduction) {
                resourceProduction = tileType.getProduction(resource);
                resourceTileType = tileType;
            }
        }
        return resourceTileType;
    }
}
