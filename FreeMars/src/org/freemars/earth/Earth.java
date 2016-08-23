package org.freemars.earth;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import org.freemars.ai.ExpeditionaryForcePlayer;
import org.freemars.earth.order.RelocateUnitOrder;
import org.freerealm.Realm;
import org.freerealm.player.Player;
import org.freerealm.resource.Resource;
import org.freerealm.unit.FreeRealmUnitType;
import org.freerealm.unit.Unit;
import org.freerealm.unit.UnitType;

/**
 *
 * @author Deniz ARIKAN
 */
public class Earth {

    public static final String TAG = "earth";

    public static final int UNIT_RELOCATION_UPDATE = 0;
    public static final int UNIT_SELECTION_UPDATE = 1;
    public static final int BUY_SELL_UPDATE = 2;
    public static final int REALM_TURN_UPDATE = 3;
    public static final int PURCHASE_EARTH_UNIT_UPDATE = 4;
    public static final int SELL_UNIT_TO_EARTH_UPDATE = 5;

    private final Realm realm;
    private final HashMap<Unit, Location> unitLocations;
    private Unit earthDialogSelectedUnit;
    private final HashMap<Resource, Integer> minimumPrices;
    private final HashMap<Resource, Integer> maximumPrices;
    private final HashMap<Resource, Integer> resourceQuantities;
    private final HashMap<Resource, Integer> resourceConsumptionPerPlayer;
    private final HashMap<Resource, Integer> maximumDemandPerPlayer;

    public Earth(Realm realm) {
        this.realm = realm;
        unitLocations = new HashMap<Unit, Location>();
        minimumPrices = new HashMap<Resource, Integer>();
        maximumPrices = new HashMap<Resource, Integer>();
        resourceQuantities = new HashMap<Resource, Integer>();
        resourceConsumptionPerPlayer = new HashMap<Resource, Integer>();
        maximumDemandPerPlayer = new HashMap<Resource, Integer>();
        Iterator<Resource> resourcesIterator = realm.getResourceManager().getResourcesIterator();
        while (resourcesIterator.hasNext()) {
            Resource resource = resourcesIterator.next();
            resourceQuantities.put(resource, 0);
        }
    }

    public Realm getRealm() {
        return realm;
    }

    public void addUnitLocation(Unit unit, Location location) {
        unitLocations.put(unit, location);
    }

    public void removeUnitLocation(Unit unit) {
        unitLocations.remove(unit);
    }

    public Location getUnitLocation(Unit unit) {
        return unitLocations.get(unit);
    }

    public Iterator<Unit> getUnitsIterator() {
        return unitLocations.keySet().iterator();
    }

    public Iterator<Entry<Unit, Location>> getUnitLocationsIterator() {
        return unitLocations.entrySet().iterator();
    }

    /*
     * Earth will sell given unit type at this price.
     */
    public int getEarthSellsAtPrice(UnitType unitType) {
        FreeRealmUnitType shuttleUnitType = realm.getUnitTypeManager().getUnitType("Shuttle");
        if (unitType.equals(shuttleUnitType)) {
            return 40000;
        }
        FreeRealmUnitType freighterUnitType = realm.getUnitTypeManager().getUnitType("Freighter");
        if (unitType.equals(freighterUnitType)) {
            return 80000;
        }
        FreeRealmUnitType bulkFreighterUnitType = realm.getUnitTypeManager().getUnitType("Bulk freighter");
        if (unitType.equals(bulkFreighterUnitType)) {
            return 120000;
        }
        int earthSellsAtPrice = unitType.getProductionCost() * 4;
        if (unitType.getId() == 0) {
            int financeCostPerColonist = Integer.parseInt(realm.getProperty("finance_cost_per_colonist"));
            earthSellsAtPrice = earthSellsAtPrice + financeCostPerColonist * 100;
        }
        return earthSellsAtPrice;
    }

    /*
     * Earth will sell given resource at this price.
     */
    public int getEarthSellsAtPrice(Resource resource) {
        Double earthBuysAtPrice = ((getEarthBuysAtPrice(resource) + 2) * 1.3);
        return earthBuysAtPrice.intValue();
    }

    /*
     * Earth will buy given resource at this price.
     */
    public int getEarthBuysAtPrice(Resource resource) {
        int minimumResourcePrice = minimumPrices.get(resource);
        int maximumResourcePrice = maximumPrices.get(resource);
        int minMaxPriceDifference = maximumResourcePrice - minimumResourcePrice;
        int currentResourceQuantity = resourceQuantities.get(resource);
        int maximumDemand = getMaximumDemandPerPlayer(resource) * getTradingPlayerCount(realm);
        double multiplier = 1 - ((double) currentResourceQuantity / maximumDemand);
        int addedPrice = (int) (multiplier * minMaxPriceDifference);
        return minimumResourcePrice + addedPrice;
    }

    public int getMinimumPrice(Resource resource) {
        return minimumPrices.get(resource);
    }

    public void setMinimumPrice(Resource resource, int minimumPrice) {
        minimumPrices.put(resource, minimumPrice);
    }

    public int getMaximumPrice(Resource resource) {
        return maximumPrices.get(resource);
    }

    public void setMaximumPrice(Resource resource, int maximumPrice) {
        maximumPrices.put(resource, maximumPrice);
    }

    public int getConsumptionPerPlayer(Resource resource) {
        return resourceConsumptionPerPlayer.get(resource);
    }

    public void setConsumptionPerPlayer(Resource resource, int consumption) {
        resourceConsumptionPerPlayer.put(resource, consumption);
    }

    public int getMaximumDemandPerPlayer(Resource resource) {
        return maximumDemandPerPlayer.get(resource);
    }

    public void setMaximumDemand(Resource resource, int maximumDemand) {
        maximumDemandPerPlayer.put(resource, maximumDemand);
    }

    public int getResourceQuantity(Resource resource) {
        return resourceQuantities.get(resource);
    }

    public void setResourceQuantity(Resource resource, int resourceQuantity) {
        resourceQuantities.put(resource, resourceQuantity);
    }

    public void setDefaultResourceQuantities(Realm realm) {
        for (Resource resource : resourceQuantities.keySet()) {
            setResourceQuantity(resource, getTradingPlayerCount(realm) * getMaximumDemandPerPlayer(resource) / 2);
        }
    }
    
    public void manageEarthResourceChange() {
        manageEarthResourceConsumption();
    }

    public void manageEarthResourceConsumption() {
        for (Resource resource : resourceQuantities.keySet()) {
            removeResource(resource, getConsumptionPerPlayer(resource)* getTradingPlayerCount(realm));
        }
    }

    public void addResource(Resource resource, int quantity) {
        resourceQuantities.put(resource, resourceQuantities.get(resource) + quantity);
    }

    public void removeResource(Resource resource, int quantity) {
        int newQuantity = resourceQuantities.get(resource) - quantity;
        if (newQuantity < 0) {
            newQuantity = 0;
        }
        resourceQuantities.put(resource, newQuantity);
    }

    public Unit getEarthDialogSelectedUnit() {
        return earthDialogSelectedUnit;
    }

    public void setEarthDialogSelectedUnit(Unit earthDialogSelectedUnit) {
        this.earthDialogSelectedUnit = earthDialogSelectedUnit;
    }

    public int findETA(Unit unit) {
        if (unit.getCurrentOrder() != null && unit.getCurrentOrder() instanceof RelocateUnitOrder) {
            RelocateUnitOrder relocateUnitOrder = (RelocateUnitOrder) unit.getCurrentOrder();
            return relocateUnitOrder.getRemainingTurns();
        }
        return 0;
    }

    public String findDestination(Unit unit) {
        if (unit.getCurrentOrder() != null && unit.getCurrentOrder() instanceof RelocateUnitOrder) {
            RelocateUnitOrder relocateUnitOrder = (RelocateUnitOrder) unit.getCurrentOrder();
            Location destination = relocateUnitOrder.getDestination();
            if (destination.equals(Location.EARTH)) {
                return "Earth";
            } else if (destination.equals(Location.MARS)) {
                if (relocateUnitOrder.getLandOnColony() != null) {
                    return relocateUnitOrder.getLandOnColony().getName();
                }
            } else if (destination.equals(Location.MARS_ORBIT)) {
                return "Mars orbit";
            }
        }
        return "";
    }

    private int getTradingPlayerCount(Realm realm) {
        int tradingPlayerCount = 0;
        Iterator<Player> playersIterator = realm.getPlayerManager().getPlayersIterator();
        while (playersIterator.hasNext()) {
            Player player = playersIterator.next();
            if (!(player instanceof ExpeditionaryForcePlayer) && player.getStatus() == Player.STATUS_ACTIVE) {
                tradingPlayerCount++;
            }
        }
        return tradingPlayerCount;
    }
}
