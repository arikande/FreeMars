package org.freemars.earth;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.freerealm.property.Property;
import org.freerealm.resource.Resource;

/**
 *
 * @author Deniz ARIKAN
 */
public class EarthFlightProperty implements Property {

    public static final String NAME = "earth_flight_property";
    private int earthMarsTravelTime;
    private final Map<Resource, Integer> consumedResources = new HashMap<Resource, Integer>();

    public EarthFlightProperty() {
        earthMarsTravelTime = 0;
    }

    public String getName() {
        return NAME;
    }

    public int getEarthMarsTravelTime() {
        return earthMarsTravelTime;
    }

    public void setEarthMarsTravelTime(int earthMarsTravelTime) {
        this.earthMarsTravelTime = earthMarsTravelTime;
    }

    public Iterator<Resource> getConsumedResourcesIterator() {
        return consumedResources.keySet().iterator();
    }

    public int getResourceConsumption(Resource resource) {
        if (consumedResources.containsKey(resource)) {
            return consumedResources.get(resource);
        } else {
            return 0;
        }
    }

    public void setResourceConsumption(Resource resource, int consumption) {
        consumedResources.put(resource, consumption);
    }
}
