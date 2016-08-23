package org.freerealm.resource;

import java.util.Iterator;

/**
 *
 * @author Deniz ARIKAN
 */
public interface ResourceStorer {

    public int getResourceQuantity(Resource resource);

    public void setResourceQuantity(Resource resource, int quantity);

    public int getRemainingCapacity(Resource resource);

    public int getTotalCapacity(Resource resource);

    public Iterator<Resource> getStorableResourcesIterator();

    public Iterator<Resource> getContainedResourcesIterator();

    public boolean isStoringResource(Resource resource);
}
