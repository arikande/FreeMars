package org.freerealm.resource;

import java.util.Iterator;
import java.util.TreeMap;

/**
 *
 * @author Deniz ARIKAN
 */
public class ResourceManager {

    private final TreeMap<Integer, Resource> resources;

    public ResourceManager() {
        resources = new TreeMap<Integer, Resource>();
    }

    public Resource getResource(int id) {
        return (Resource) getResources().get(id);
    }

    public Resource getResource(String name) {
        Resource returnValue = null;
        Iterator<Resource> resourceIterator = getResourcesIterator();
        while (resourceIterator.hasNext()) {
            Resource resource = resourceIterator.next();
            if (resource.getName().equals(name)) {
                returnValue = resource;
                break;
            }
        }
        return returnValue;
    }

    public void addResource(Resource resource) {
        getResources().put(resource.getId(), resource);
    }

    private TreeMap<Integer, Resource> getResources() {
        return resources;
    }

    public int getResourceCount() {
        return getResources().size();
    }

    public Iterator<Resource> getResourcesIterator() {
        return getResources().values().iterator();
    }
}
