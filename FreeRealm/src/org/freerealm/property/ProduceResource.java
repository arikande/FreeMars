package org.freerealm.property;

import java.util.HashMap;
import java.util.Iterator;
import org.freerealm.resource.Resource;

/**
 *
 * @author Deniz ARIKAN
 */
public class ProduceResource implements Property {

    private final HashMap<Resource, Integer> input;
    private final HashMap<Resource, Integer> output;
    private int maximumMultiplier;

    public ProduceResource() {
        input = new HashMap<Resource, Integer>();
        output = new HashMap<Resource, Integer>();
    }

    public String getName() {
        return "ProduceResource";
    }

    public void addInput(Resource resource, int quantity) {
        input.put(resource, quantity);
    }

    public void addOutput(Resource resource, int quantity) {
        output.put(resource, quantity);
    }

    public int getInputCount() {
        return input.size();
    }

    public int getOutputCount() {
        return output.size();
    }

    public Iterator<Resource> getInputResourcesIterator() {
        return input.keySet().iterator();
    }

    public Iterator<Resource> getOutputResourcesIterator() {
        return output.keySet().iterator();
    }

    public int getInputQuantity(Resource resource) {
        if (input.containsKey(resource)) {
            return input.get(resource);
        } else {
            return 0;
        }
    }

    public int getOutputQuantity(Resource resource) {
        if (output.containsKey(resource)) {
            return output.get(resource);
        } else {
            return 0;
        }
    }

    public int getMaximumMultiplier() {
        return maximumMultiplier;
    }

    public void setMaximumMultiplier(int maximumMultiplier) {
        this.maximumMultiplier = maximumMultiplier;
    }
}
