package org.freerealm.resource;

import java.util.Iterator;

/**
 *
 * @author Deniz ARIKAN
 */
public interface ResourceProducer {

    public Iterator<Resource> getInputResourcesIterator();

    public Iterator<Resource> getOutputResourcesIterator();

    public int getInputResourceCount();

    public int getInputQuantity(Resource resource);

    public int getOutputResourceCount();

    public int getOutputQuantity(Resource resource);

    public int getMaximumMultiplier();
}
