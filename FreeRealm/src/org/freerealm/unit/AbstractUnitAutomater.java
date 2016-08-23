package org.freerealm.unit;

/**
 *
 * @author Deniz ARIKAN
 */
public abstract class AbstractUnitAutomater implements UnitAutomater {

    private Unit unit;

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }
}
