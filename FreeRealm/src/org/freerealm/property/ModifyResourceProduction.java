package org.freerealm.property;

import org.freerealm.resource.Resource;

/**
 *
 * @author Deniz ARIKAN
 */
public class ModifyResourceProduction extends SimpleModifier implements Property {

    private Resource resource;
    private boolean modifyingOnlyIfResourceExists;

    public String getName() {
        return "modifyResourceProduction";
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public boolean isModifyingOnlyIfResourceExists() {
        return modifyingOnlyIfResourceExists;
    }

    public void setModifyingOnlyIfResourceExists(boolean modifyingOnlyIfResourceExists) {
        this.modifyingOnlyIfResourceExists = modifyingOnlyIfResourceExists;
    }
}
