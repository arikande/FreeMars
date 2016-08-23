package org.freerealm.player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import org.freerealm.UnitUpkeepCostCalculator;
import org.freerealm.modifier.Modifier;
import org.freerealm.unit.FreeRealmUnitType;
import org.freerealm.unit.Unit;
import org.freerealm.unit.UnitType;

/**
 *
 * @author Deniz ARIKAN
 */
public class UnitManager {

    private final Player player;
    private final TreeMap<Integer, Unit> units;
    private Unit activeUnit;

    public UnitManager(Player player) {
        this.player = player;
        units = new TreeMap<Integer, Unit>();
    }

    public Unit getNextUnit(Unit unit) {
        if (unit == null) {
            return getFirstUnit();
        }
        Iterator<Unit> iterator = getUnitsIterator();
        while (iterator.hasNext()) {
            Unit tryUnit = iterator.next();
            if (tryUnit.equals(unit)) {
                if (iterator.hasNext()) {
                    return iterator.next();
                } else {
                    return getFirstUnit();
                }
            }
        }
        return getFirstUnit();
    }

    private Unit getFirstUnit() {
        if (units.size() > 0) {
            return units.get(units.firstKey());
        } else {
            return null;
        }
    }

    public void addUnit(Unit unit) {
        units.put(unit.getId(), unit);
    }

    public void removeUnit(Unit unit) {
        if ((getActiveUnit() != null) && (getActiveUnit().equals(unit))) {
            setActiveUnit(null);
        }
        units.remove(unit.getId());
    }

    public Unit getUnit(int id) {
        return (Unit) units.get(id);
    }

    public TreeMap<Integer, Unit> getUnits() {
        return units;
    }

    public Iterator<Unit> getUnitsIterator() {
        return units.values().iterator();
    }

    public int getUnitCount() {
        return units.size();
    }

    public Unit getActiveUnit() {
        return activeUnit;
    }

    public void setActiveUnit(Unit activeUnit) {
        this.activeUnit = activeUnit;
    }

    public Player getPlayer() {
        return player;
    }

    public int getUnitUpkeep() {
        int upkeep = 0;
        Iterator<Unit> unitsIterator = getUnits().values().iterator();
        while (unitsIterator.hasNext()) {
            UnitType unitType = unitsIterator.next().getType();
            upkeep = upkeep + unitType.getUpkeepCost();
        }
        upkeep = new UnitUpkeepCostCalculator(upkeep, new Modifier[]{getPlayer()}).getUpkeepCost();
        return upkeep;
    }

    public List<Unit> getUnitsOfType(FreeRealmUnitType unitType) {
        ArrayList<Unit> unitsOfType = new ArrayList<Unit>();
        Iterator<Unit> iterator = getUnitsIterator();
        while (iterator.hasNext()) {
            Unit unit = iterator.next();
            if (unit.getType().equals(unitType)) {
                unitsOfType.add(unit);
            }
        }
        return unitsOfType;
    }

    public int getUnitsOfTypeCount(FreeRealmUnitType unitType) {
        return getUnitsOfType(unitType).size();
    }
}
