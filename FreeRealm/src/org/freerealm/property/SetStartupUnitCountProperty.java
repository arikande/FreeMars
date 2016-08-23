package org.freerealm.property;

import org.freerealm.unit.FreeRealmUnitType;

/**
 *
 * @author Deniz ARIKAN
 */
public class SetStartupUnitCountProperty implements Property {

    public static final String NAME = "set_startup_unit_count_property";
    private FreeRealmUnitType unitType;
    private int count;

    public String getName() {
        return NAME;
    }

    public FreeRealmUnitType getUnitType() {
        return unitType;
    }

    public void setUnitType(FreeRealmUnitType unitType) {
        this.unitType = unitType;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
