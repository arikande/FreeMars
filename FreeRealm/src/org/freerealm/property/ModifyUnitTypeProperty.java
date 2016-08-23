package org.freerealm.property;

/**
 * @author Deniz ARIKAN
 */
public class ModifyUnitTypeProperty extends SimpleModifier implements Property {

    public static final String ALL = "ALL";
    private static final String NAME = "ModifyUnitTypeProperty";
    private boolean activeOnAllUnitTypes;
    private int unitType;
    private String propertyName;
    private String modifierName;

    public String getName() {
        return NAME;
    }

    public boolean isActiveOnAllUnitTypes() {
        return activeOnAllUnitTypes;
    }

    public void setActiveOnAllUnitTypes(boolean activeOnAllUnitTypes) {
        this.activeOnAllUnitTypes = activeOnAllUnitTypes;
    }

    public int getUnitType() {
        return unitType;
    }

    public void setUnitType(int unitType) {
        this.unitType = unitType;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getModifierName() {
        return modifierName;
    }

    public void setModifierName(String modifierName) {
        this.modifierName = modifierName;
    }
}
