package org.freemars.earth;

import org.freerealm.property.Property;
import org.freerealm.property.SimpleModifier;

/**
 * @author Deniz ARIKAN
 */
public class ModifyResourceEarthSellPrice extends SimpleModifier implements Property {

    public static final String ALL = "ALL";
    private static final String NAME = "ModifyResourceEarthSellPrice";
    private String resource;

    public String getName() {
        return NAME;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }
}
