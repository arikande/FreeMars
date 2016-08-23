package org.freerealm;

import java.util.Iterator;
import java.util.TreeMap;
import org.freerealm.resource.Resource;

/**
 *
 * @author Deniz ARIKAN
 */
public class ResourceStorageManager {

    private final TreeMap<Resource, Integer> resources;

    public ResourceStorageManager(Realm realm) {
        resources = new TreeMap<Resource, Integer>();
        Iterator<Resource> resourceIterator = realm.getResourceManager().getResourcesIterator();
        while (resourceIterator.hasNext()) {
            Resource resource = resourceIterator.next();
            setResourceQuantity(resource, 0);
        }
    }

    public int getResourceQuantity(Resource resource) {
        return getResources().get(resource);
    }

    public void setResourceQuantity(Resource resource, int quantity) {
        getResources().put(resource, quantity);
    }

    public TreeMap<Resource, Integer> getResources() {
        return resources;
    }

    public int getTotalQuantity() {
        int totalQuantity = 0;
        Iterator<Integer> iterator = getResources().values().iterator();
        while (iterator.hasNext()) {
            Integer quantity = iterator.next();
            totalQuantity = totalQuantity + quantity;
        }
        return totalQuantity;
    }

    public Iterator<Resource> getResourcesIterator() {
        return resources.keySet().iterator();
    }
}
