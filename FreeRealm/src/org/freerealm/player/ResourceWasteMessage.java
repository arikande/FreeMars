package org.freerealm.player;

import org.freerealm.resource.Resource;

/**
 * @author Deniz ARIKAN
 */
public class ResourceWasteMessage extends SettlementRelatedMessage {

    private Resource resource;

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }
}
