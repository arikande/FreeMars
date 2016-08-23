package org.freerealm.unit;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;
import org.freerealm.Container;
import org.freerealm.Customizable;
import org.freerealm.Realm;
import org.freerealm.Utility;
import org.freerealm.map.Coordinate;
import org.freerealm.map.FreeRealmPlayerMapItem;
import org.freerealm.player.Player;
import org.freerealm.property.ContainerProperty;
import org.freerealm.property.Fight;
import org.freerealm.property.ModifyUnitTypeProperty;
import org.freerealm.resource.Resource;

/**
 *
 * @author Deniz ARIKAN
 */
public class Unit extends FreeRealmPlayerMapItem implements Container, Fighter, Customizable {

    public static final int UNIT_ACTIVE = 0;
    public static final int UNIT_SUSPENDED = 1;
    private UnitType type;
    private String name;
    private float movementPoints;
    private boolean skippedForCurrentTurn;
    private Order currentOrder;
    private ContainerManager containerManager;
    private int status;
    private UnitAutomater automater;
    private Properties customProperties;
    private final ArrayList<Order> queuedOrders;

    public Unit(Realm realm, UnitType type, Coordinate coordinate, Player player) {
        super(realm, coordinate, player);
        status = UNIT_ACTIVE;
        this.type = type;
        queuedOrders = new ArrayList<Order>();
        containerManager = new ContainerManager(realm, this);
        movementPoints = type.getMovementPoints();
        customProperties = new Properties();
    }

    public Unit(Realm realm) {
        super(realm);
        status = UNIT_ACTIVE;
        queuedOrders = new ArrayList<Order>();
        containerManager = new ContainerManager(realm, this);
        customProperties = new Properties();
    }

    @Override
    public String toString() {
        return getType().toString() + " " + getId();
    }

    public Object getCustomProperty(Object key) {
        return customProperties.get(key);
    }

    public void addCustomProperty(Object key, Object value) {
        customProperties.put(key, value);
    }

    public Properties getCustomProperties() {
        return customProperties;
    }

    public void setCustomProperties(Properties properties) {
        this.customProperties = properties;
    }

    public UnitType getType() {
        return type;
    }

    public void setType(FreeRealmUnitType type) {
        this.type = type;
        setMovementPoints(type.getMovementPoints());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getMovementPoints() {
        return movementPoints;
    }

    public void setMovementPoints(float movementPoints) {
        this.movementPoints = movementPoints;
    }

    public boolean isSkippedForCurrentTurn() {
        return skippedForCurrentTurn;
    }

    public void setSkippedForCurrentTurn(boolean skippedForCurrentTurn) {
        this.skippedForCurrentTurn = skippedForCurrentTurn;
    }

    public Order getCurrentOrder() {
        return currentOrder;
    }

    public void setCurrentOrder(Order order) {
        this.currentOrder = order;
    }

    public Order getNextOrder() {
        if (queuedOrders.size() > 0) {
            return queuedOrders.get(0);
        } else {
            return null;
        }
    }

    public Iterator<Order> getOrdersIterator() {
        return queuedOrders.iterator();
    }

    public void addOrder(Order order) {
        queuedOrders.add(order);
    }

    public void removeOrder(Order order) {
        queuedOrders.remove(order);
    }

    public void clearQueuedOrders() {
        queuedOrders.clear();
    }

    public int getTotalContainedWeight() {
        return containerManager.getTotalCargoWeight();
    }

    public int getResourceQuantity(Resource resource) {
        return containerManager.getResourceQuantity(resource);
    }

    public void setResourceQuantity(Resource resource, int quantity) {
        containerManager.setResourceQuantity(resource, quantity);
    }

    public int getRemainingCapacity(Resource resource) {
        ContainerProperty containerProperty = (ContainerProperty) getType().getProperty("ContainerProperty");
        if (containerProperty == null) {
            return 0;
        }
        if (!containerProperty.hasResource(resource)) {
            return 0;
        }
        return getTotalCapacity(resource) - getTotalContainedWeight();
    }

    public int getTotalCapacity() {
        ContainerProperty containerProperty = (ContainerProperty) getType().getProperty("ContainerProperty");
        if (containerProperty == null) {
            return 0;
        }
        int totalCapacity = containerProperty.getCapacity();
        ModifyUnitTypeProperty modifyUnitTypeProperty = (ModifyUnitTypeProperty) getPlayer().getProperty("ModifyUnitTypeProperty");
        if (modifyUnitTypeProperty != null) {
            if (modifyUnitTypeProperty.isActiveOnAllUnitTypes() || modifyUnitTypeProperty.getUnitType() == getType().getId()) {
                if (modifyUnitTypeProperty.getPropertyName().equals("ContainerProperty")) {
                    if (modifyUnitTypeProperty.getModifierName().equals("capacity")) {
                        int capacityModifier = modifyUnitTypeProperty.getModifier();
                        totalCapacity = (int) Utility.modifyByPercent(totalCapacity, capacityModifier);
                    }
                }
            }
        }
        return totalCapacity;
    }

    public int getTotalCapacity(Resource resource) {
        return getTotalCapacity();
    }

    public int getRemainingCapacity() {
        return getTotalCapacity() - getTotalContainedWeight();
    }

    public ContainerManager getContainerManager() {
        return containerManager;
    }

    public void setContainerManager(ContainerManager containerManager) {
        this.containerManager = containerManager;
    }

    public int getTotalResourceWeight() {
        return getContainerManager().getTotalResourceWeight();
    }

    public Iterator<Resource> getStorableResourcesIterator() {
        ContainerProperty containerProperty = (ContainerProperty) getType().getProperty("ContainerProperty");
        if (containerProperty == null) {
            return new ArrayList<Resource>().iterator();
        }
        return containerProperty.getResourcesIterator();
    }

    public Iterator<Resource> getContainedResourcesIterator() {
        return containerManager.getContainedResourcesIterator();
    }

    public boolean isStoringResource(Resource resource) {
        ContainerProperty containerProperty = (ContainerProperty) getType().getProperty("ContainerProperty");
        if (containerProperty == null) {
            return false;
        }
        return containerProperty.hasResource(resource);
    }

    public int getContainedUnitCount() {
        return containerManager.getContainedUnitCount();
    }

    public Iterator<Integer> getContainedUnitsIterator() {
        return containerManager.getContainedUnitsIterator();
    }

    public int getContainedUnitsOfTypeCount(int unitTypeId) {
        return getContainerManager().getContainedUnitsOfTypeCount(unitTypeId);
    }

    public boolean canContainUnit(UnitType unitType) {
        ContainerProperty containerProperty = (ContainerProperty) getType().getProperty("ContainerProperty");
        if (containerProperty == null) {
            return false;
        }
        return containerProperty.hasUnitType(unitType.getId());
    }

    public int getContainedPopulation() {
        return containerManager.getContainedPopulation();
    }

    public void setContainedPopulation(int population) {
        containerManager.setContainedPopulation(population);
    }

    public boolean canContainPopulation() {
        ContainerProperty containerProperty = (ContainerProperty) getType().getProperty("ContainerProperty");
        if (containerProperty == null) {
            return false;
        }
        return containerProperty.isAccomodatingPopulation();
    }

    public void addUnit(int unitId) {
        containerManager.addUnit(unitId);
    }

    public void removeUnit(int unitId) {
        containerManager.removeUnit(unitId);
    }

    public boolean containsUnit(Unit unit) {
        return containerManager.containsUnit(unit);
    }

    public boolean canAttack() {
        Fight fight = (Fight) getType().getProperty("Fight");
        if (fight == null) {
            return false;
        }
        if (getMovementPoints() == 0) {
            return false;
        }
        return true;
    }

    public int getAttackPoints() {
        Fight fight = (Fight) getType().getProperty("Fight");
        if (fight == null) {
            return 0;
        }
        return fight.getAttackPoints();
    }

    public int getDefencePoints() {
        Fight fight = (Fight) getType().getProperty("Fight");
        if (fight == null) {
            return 0;
        }
        return fight.getDefencePoints();
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public UnitAutomater getAutomater() {
        return automater;
    }

    public void setAutomater(UnitAutomater automater) {
        this.automater = automater;
    }
}
