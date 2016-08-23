package org.freerealm.unit;

import java.util.Iterator;

/**
 *
 * @author Deniz ARIKAN
 */
public interface UnitContainer {

    public String getName();

    public int getTotalCapacity();

    public int getTotalContainedWeight();

    public int getRemainingCapacity();

    public void addUnit(int unitId);

    public void removeUnit(int unitId);

    public Iterator<Integer> getContainedUnitsIterator();

    public boolean canContainUnit(UnitType unitType);
}
