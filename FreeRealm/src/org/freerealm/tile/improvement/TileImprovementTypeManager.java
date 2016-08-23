package org.freerealm.tile.improvement;

import java.util.Iterator;
import java.util.TreeMap;

/**
 *
 * @author Deniz ARIKAN
 */
public class TileImprovementTypeManager {

    private TreeMap<Integer, TileImprovementType> improvements = null;

    public TileImprovementTypeManager() {
        improvements = new TreeMap<Integer, TileImprovementType>();
    }

    public TileImprovementType getImprovement(int id) {
        return getImprovements().get(id);
    }

    public TileImprovementType getImprovement(String name) {
        TileImprovementType returnValue = null;
        Iterator<TileImprovementType> improvementsIterator = getImprovementsIterator();
        while (improvementsIterator.hasNext()) {
            TileImprovementType improvement = improvementsIterator.next();
            if (improvement.getName().equals(name)) {
                returnValue = improvement;
                break;
            }
        }
        return returnValue;
    }

    public void addImprovement(TileImprovementType improvement) {
        getImprovements().put(improvement.getId(), improvement);
    }

    private TreeMap<Integer, TileImprovementType> getImprovements() {
        return improvements;
    }

    public Iterator<TileImprovementType> getImprovementsIterator() {
        return getImprovements().values().iterator();
    }

    public int getImprovementCount() {
        return getImprovements().size();
    }
}
