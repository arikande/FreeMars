package org.freerealm.settlement.improvement;

import java.util.Iterator;
import java.util.TreeMap;

/**
 *
 * @author Deniz ARIKAN
 */
public class SettlementImprovementManager {

    private final TreeMap<Integer, SettlementImprovementType> improvements;

    public SettlementImprovementManager() {
        improvements = new TreeMap<Integer, SettlementImprovementType>();
    }

    public SettlementImprovementType getImprovement(int id) {
        return getImprovements().get(id);
    }

    public SettlementImprovementType getImprovement(String name) {
        SettlementImprovementType returnValue = null;
        Iterator<SettlementImprovementType> improvementsIterator = getImprovementsIterator();
        while (improvementsIterator.hasNext()) {
            SettlementImprovementType improvement = improvementsIterator.next();
            if (improvement.getName().equals(name)) {
                returnValue = improvement;
                break;
            }
        }
        return returnValue;
    }

    public void addImprovement(SettlementImprovementType improvement) {
        getImprovements().put(improvement.getId(), improvement);
    }

    private TreeMap<Integer, SettlementImprovementType> getImprovements() {
        return improvements;
    }

    public Iterator<SettlementImprovementType> getImprovementsIterator() {
        return getImprovements().values().iterator();
    }

    public int getImprovementCount() {
        return getImprovements().size();
    }
}
