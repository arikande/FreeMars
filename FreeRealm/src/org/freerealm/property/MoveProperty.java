package org.freerealm.property;

import java.util.ArrayList;
import java.util.Iterator;
import org.freerealm.tile.TileType;

/**
 *
 * @author Deniz ARIKAN
 */
public class MoveProperty implements Property {

    public static final String NAME = "move_property";
    private int points;
    private final ArrayList<TileType> tileTypes;

    public MoveProperty() {
        tileTypes = new ArrayList<TileType>();
    }

    public String getName() {
        return NAME;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void addTileType(TileType tileType) {
        getTileTypes().add(tileType);
    }

    public boolean hasTileType(TileType tileType) {
        return getTileTypes().contains(tileType);
    }

    public Iterator<TileType> getTileTypesIterator() {
        return getTileTypes().iterator();
    }

    private ArrayList<TileType> getTileTypes() {
        return tileTypes;
    }
}
