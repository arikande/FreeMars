package org.freemars.unit.manager;

import javax.swing.Icon;

/**
 *
 * @author Deniz ARIKAN
 */
public class UnitsTableRow {

    private int unitId;
    private Icon unitIcon;
    private String unitName;
    private String unitType;
    private String unitLocation;
    private String unitOrder;
    private String unitCoordinate;

    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }

    public Icon getUnitIcon() {
        return unitIcon;
    }

    public void setUnitIcon(Icon unitIcon) {
        this.unitIcon = unitIcon;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public String getUnitLocation() {
        return unitLocation;
    }

    public void setUnitLocation(String unitLocation) {
        this.unitLocation = unitLocation;
    }

    public String getUnitOrder() {
        return unitOrder;
    }

    public void setUnitOrder(String unitOrder) {
        this.unitOrder = unitOrder;
    }

    public String getUnitCoordinate() {
        return unitCoordinate;
    }

    public void setUnitCoordinate(String unitCoordinate) {
        this.unitCoordinate = unitCoordinate;
    }
}
