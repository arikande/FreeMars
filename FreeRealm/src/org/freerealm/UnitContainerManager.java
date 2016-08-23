package org.freerealm;

import java.util.ArrayList;
import java.util.Iterator;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class UnitContainerManager {

    private final Unit containerUnit;
    private final ArrayList<Integer> containedUnitIds;

    public UnitContainerManager(Unit containerUnit) {
        this.containerUnit = containerUnit;
        containedUnitIds = new ArrayList<Integer>();
    }

    public int getTotalQuantity() {
        int totalQuantity = 0;
        Iterator<Integer> iterator = getUnitsIterator();
        while (iterator.hasNext()) {
            int id = iterator.next();
            totalQuantity = totalQuantity + containerUnit.getPlayer().getUnit(id).getType().getWeightForContainer();
        }
        return totalQuantity;
    }

    public int getContainedUnitsOfTypeCount(int unitTypeId) {
        int count = 0;
        for (Integer containedUnitId : containedUnitIds) {
            if (unitTypeId == containerUnit.getPlayer().getUnit(containedUnitId).getType().getId()) {
                count++;
            }
        }
        return count;
    }

    public void addUnit(int unitId) {
        containedUnitIds.add(unitId);
    }

    public void removeUnit(int unitId) {
        containedUnitIds.remove(new Integer(unitId));
    }

    public boolean containsUnit(Unit unit) {
        return containedUnitIds.contains(unit.getId());
    }

    public Iterator<Integer> getUnitsIterator() {
        return containedUnitIds.iterator();
    }

    public void clear() {
        containedUnitIds.clear();
    }

    public int getNumberOfUnits() {
        return containedUnitIds.size();
    }
}
