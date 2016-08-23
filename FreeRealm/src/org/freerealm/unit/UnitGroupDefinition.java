package org.freerealm.unit;

import java.util.Iterator;

/**
 *
 * @author Deniz ARIKAN
 */
public interface UnitGroupDefinition {

    public int getQuantityForUnitType(UnitType unitType);

    public void setQuantityForUnitType(UnitType unitType, int quantity);

    public Iterator<UnitType> getUnitTypesIterator();
}
