package org.freerealm.property;

import org.freerealm.resource.Resource;

/**
 * @author Deniz ARIKAN
 */
public class StoreResourceProperty implements Property {

    public static final String NAME = "store_resource_property";
    private Resource resource;
    private int storage = 0;

    public String getName() {
        return NAME;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public int getStorage() {
        return storage;
    }

    public void setStorage(int storage) {
        this.storage = storage;
    }
}
