package org.freerealm.property;

/**
 *
 * @author Deniz ARIKAN
 */
public class ModifyRequiredPopulationResourceAmount extends SimpleModifier implements Property {

    private static final String NAME = "ModifyRequiredPopulationResourceAmount";
    private int resourceId;

    public String getName() {
        return NAME;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }
}
