package org.freerealm.unit;

import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * @author Deniz ARIKAN
 */
public class DefaultUnitGroupDefinition implements UnitGroupDefinition {

    private final HashMap<UnitType, Integer> unitTypeQuantities;

    public DefaultUnitGroupDefinition() {
        unitTypeQuantities = new HashMap<UnitType, Integer>();
    }

    public int getQuantityForUnitType(UnitType unitType) {
        if (unitTypeQuantities.containsKey(unitType)) {
            return unitTypeQuantities.get(unitType);
        } else {
            return 0;
        }
    }

    public void setQuantityForUnitType(UnitType unitType, int quantity) {
        unitTypeQuantities.put(unitType, quantity);
    }

    public void clear() {
        unitTypeQuantities.clear();
    }

    public Iterator<UnitType> getUnitTypesIterator() {
        return unitTypeQuantities.keySet().iterator();
    }
}
