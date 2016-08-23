package org.freerealm.property;

import java.util.Iterator;
import java.util.Vector;
import org.freerealm.resource.Resource;

/**
 *
 * @author Deniz ARIKAN
 */
public class ContainerProperty implements Property {

    private static final String NAME = "ContainerProperty";
    private int capacity;
    private final Vector<Resource> resources;
    private final Vector<Integer> unitTypes;
    private boolean accomodatingPopulation;

    public String getName() {
        return NAME;
    }

    public ContainerProperty() {
        resources = new Vector<Resource>();
        unitTypes = new Vector<Integer>();
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void addResource(Resource resource) {
        getResources().add(resource);
    }

    public boolean hasResource(Resource resource) {
        return getResources().contains(resource);
    }

    public Iterator<Resource> getResourcesIterator() {
        return getResources().iterator();
    }

    private Vector<Resource> getResources() {
        return resources;
    }

    public void addUnitType(int unitTypeId) {
        getUnitTypes().add(unitTypeId);
    }

    public boolean hasUnitType(int unitTypeId) {
        return getUnitTypes().contains(unitTypeId);
    }

    public Iterator<Integer> getUnitTypesIterator() {
        return getUnitTypes().iterator();
    }

    private Vector<Integer> getUnitTypes() {
        return unitTypes;
    }

    public boolean isAccomodatingPopulation() {
        return accomodatingPopulation;
    }

    public void setAccomodatingPopulation(boolean accomodatingPopulation) {
        this.accomodatingPopulation = accomodatingPopulation;
    }
}
