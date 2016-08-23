package org.freerealm.unit;

import java.util.Iterator;
import org.freerealm.Realm;
import org.freerealm.ResourceStorageManager;
import org.freerealm.UnitContainerManager;
import org.freerealm.resource.Resource;

/**
 *
 * @author Deniz ARIKAN
 */
public class ContainerManager {

    private final Realm realm;
    private ResourceStorageManager resourceStorageManager;
    private UnitContainerManager unitContainerManager;
    private int containedPopulation;

    public ContainerManager(Realm realm, Unit containerUnit) {
        this.realm = realm;
        resourceStorageManager = new ResourceStorageManager(realm);
        unitContainerManager = new UnitContainerManager(containerUnit);
    }

    public ResourceStorageManager getResourceStorageManager() {
        return resourceStorageManager;
    }

    public void setResourceStorageManager(ResourceStorageManager resourceStorageManager) {
        this.resourceStorageManager = resourceStorageManager;
    }

    public UnitContainerManager getUnitContainerManager() {
        return unitContainerManager;
    }

    public void setUnitContainerManager(UnitContainerManager unitContainerManager) {
        this.unitContainerManager = unitContainerManager;
    }

    public int getTotalCargoWeight() {
        int weightPerCitizen = Integer.parseInt(realm.getProperty("weight_per_citizen"));
        return getTotalResourceWeight() + unitContainerManager.getTotalQuantity() + weightPerCitizen * getContainedPopulation();
    }

    public int getTotalResourceWeight() {
        return resourceStorageManager.getTotalQuantity();
    }

    public void addUnit(int unitId) {
        unitContainerManager.addUnit(unitId);
    }

    public void removeUnit(int unitId) {
        unitContainerManager.removeUnit(unitId);
    }

    public boolean containsUnit(Unit unit) {
        return unitContainerManager.containsUnit(unit);
    }

    public int getContainedUnitCount() {
        return unitContainerManager.getNumberOfUnits();
    }

    public Iterator<Integer> getContainedUnitsIterator() {
        return unitContainerManager.getUnitsIterator();
    }

    public int getContainedUnitsOfTypeCount(int unitTypeId) {
        return unitContainerManager.getContainedUnitsOfTypeCount(unitTypeId);
    }
    
    public int getResourceQuantity(Resource resource) {
        return resourceStorageManager.getResourceQuantity(resource);
    }

    public void setResourceQuantity(Resource resource, int quantity) {
        resourceStorageManager.setResourceQuantity(resource, quantity);
    }

    public Iterator<Resource> getContainedResourcesIterator() {
        return resourceStorageManager.getResourcesIterator();
    }

    public int getContainedPopulation() {
        return containedPopulation;
    }

    public void setContainedPopulation(int population) {
        this.containedPopulation = population;
    }
}
