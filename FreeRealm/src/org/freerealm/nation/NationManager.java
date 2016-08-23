package org.freerealm.nation;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 *
 * @author Deniz ARIKAN
 */
public class NationManager {

    private TreeMap<Integer, Nation> nations = null;

    public NationManager() {
        nations = new TreeMap<Integer, Nation>();
    }

    public Nation getNation(int id) {
        return getNations().get(id);
    }

    public Nation getNation(String name) {
        Nation returnValue = null;
        Iterator nationsIterator = getNations().entrySet().iterator();
        while (nationsIterator.hasNext()) {
            Entry entry = (Entry) nationsIterator.next();
            Nation nation = (Nation) entry.getValue();
            if (nation.getName().equals(name)) {
                returnValue = nation;
                break;
            }
        }
        return returnValue;
    }

    private TreeMap<Integer, Nation> getNations() {
        return nations;
    }

    public void addNation(Nation nation) {
        getNations().put(nation.getId(), nation);
    }

    public Iterator<Nation> getNationsIterator() {
        return getNations().values().iterator();
    }

    public int getNationCount() {
        return getNations().size();
    }
}
