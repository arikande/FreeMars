package org.freerealm.unit;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 *
 * @author Deniz ARIKAN
 */
public class UnitTypeManager {

    private TreeMap<Integer, FreeRealmUnitType> unitTypes = null;

    public UnitTypeManager() {
        unitTypes = new TreeMap<Integer, FreeRealmUnitType>();
    }

    public FreeRealmUnitType getUnitType(int id) {
        return (FreeRealmUnitType) getUnitTypes().get(id);
    }

    public FreeRealmUnitType getUnitType(String name) {
        FreeRealmUnitType returnValue = null;
        Iterator unitTypeIterator = unitTypes.entrySet().iterator();
        while (unitTypeIterator.hasNext()) {
            Entry entry = (Entry) unitTypeIterator.next();
            FreeRealmUnitType unitType = (FreeRealmUnitType) entry.getValue();
            if (unitType.getName().equals(name)) {
                returnValue = unitType;
                break;
            }
        }
        return returnValue;
    }

    public int getUnitTypeCount() {
        return unitTypes.size();
    }

    public TreeMap<Integer, FreeRealmUnitType> getUnitTypes() {
        return unitTypes;
    }

    public Iterator<FreeRealmUnitType> getUnitTypesIterator() {
        return getUnitTypes().values().iterator();
    }
}
