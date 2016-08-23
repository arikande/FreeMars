package org.freerealm.tile.improvement;

import java.util.Iterator;
import java.util.Vector;
import org.freerealm.tile.Tile;
import org.freerealm.tile.TileBuildablePrerequisite;

/**
 *
 * @author arikande
 */
public class NoTileImprovementPrerequisite implements TileBuildablePrerequisite {

    private Tile tile;
    private final Vector<Integer> exclusiveImprovementIds;

    public NoTileImprovementPrerequisite() {
        exclusiveImprovementIds = new Vector<Integer>();
    }

    public boolean isSatisfied() {
        Iterator<TileImprovementType> iterator = tile.getImprovementsIterator();
        while (iterator.hasNext()) {
            TileImprovementType tileImprovement = iterator.next();
            if (exclusiveImprovementIds.contains(tileImprovement.getId())) {
                return false;
            }
        }
        return true;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }

    public void addExclusiveImprovementId(int id) {
        exclusiveImprovementIds.add(id);
    }

    public Iterator<Integer> getExclusiveImprovementsIterator() {
        return exclusiveImprovementIds.iterator();
    }
}
