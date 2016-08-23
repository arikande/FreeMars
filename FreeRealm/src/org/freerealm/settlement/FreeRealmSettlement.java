package org.freerealm.settlement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;
import org.freerealm.Realm;
import org.freerealm.ResourceStorageManager;
import org.freerealm.SettlementImprovementUpkeepCostCalculator;
import org.freerealm.Utility;
import org.freerealm.map.Coordinate;
import org.freerealm.map.FreeRealmPlayerMapItem;
import org.freerealm.modifier.Modifier;
import org.freerealm.property.BuildableProperty;
import org.freerealm.property.ModifyMaximumWorkers;
import org.freerealm.resource.Resource;
import org.freerealm.settlement.improvement.SettlementImprovement;
import org.freerealm.settlement.improvement.SettlementImprovementType;
import org.freerealm.settlement.workforce.WorkForce;
import org.freerealm.tile.Tile;

/**
 *
 * @author Deniz ARIKAN
 */
public class FreeRealmSettlement extends FreeRealmPlayerMapItem implements Settlement {

    private Realm realm;
    private String name;
    private int population;
    private final ArrayList<SettlementBuildable> productionQueue;
    private int productionPoints;
    private ResourceStorageManager storageManager;
    private final WorkForceManager workForceManager;
    private boolean contiuousProduction;
    private final HashMap<Integer, SettlementImprovement> improvements;
    private final ArrayList<Resource> automanagedResources;

    public FreeRealmSettlement(Realm realm) {
        super(realm);
        setRealm(realm);
        productionPoints = 0;
        contiuousProduction = false;
        productionQueue = new ArrayList<SettlementBuildable>();
        workForceManager = new WorkForceManager();
        storageManager = new ResourceStorageManager(realm);
        improvements = new HashMap<Integer, SettlementImprovement>();
        automanagedResources = new ArrayList<Resource>();
    }

    @Override
    public String toString() {
        return getName();
    }

    public boolean hasImprovementType(int improvementTypeId) {
        Iterator<SettlementImprovement> improvementsIterator = getImprovementsIterator();
        while (improvementsIterator.hasNext()) {
            SettlementImprovementType checkImprovementType = improvementsIterator.next().getType();
            if (checkImprovementType.getId() == improvementTypeId) {
                return true;
            }
        }
        return false;
    }

    public boolean hasImprovementType(SettlementImprovementType improvementType) {
        return hasImprovementType(improvementType.getId());
    }

    public SettlementImprovement getImprovementOfType(SettlementImprovementType improvementType) {
        Iterator<SettlementImprovement> improvementsIterator = getImprovementsIterator();
        while (improvementsIterator.hasNext()) {
            SettlementImprovement settlementImprovement = improvementsIterator.next();
            SettlementImprovementType checkImprovementType = settlementImprovement.getType();
            if (checkImprovementType.equals(improvementType)) {
                return settlementImprovement;
            }
        }
        return null;
    }

    public void addImprovement(SettlementImprovement improvement) {
        improvements.put(improvement.getType().getId(), improvement);
    }

    public void removeImprovement(int improvementTypeId) {
        improvements.remove(improvementTypeId);
    }

    public void clearImprovements() {
        improvements.clear();
    }

    public Iterator<SettlementImprovement> getImprovementsIterator() {
        return improvements.values().iterator();
    }

    public int getImprovementCount() {
        return improvements.size();
    }

    public int getImprovementUpkeep() {
        int upkeep = 0;
        Iterator<SettlementImprovement> improvementsIterator = getImprovementsIterator();
        while (improvementsIterator.hasNext()) {
            SettlementImprovement settlementImprovement = improvementsIterator.next();
            if (settlementImprovement.isEnabled()) {
                SettlementImprovementType settlementImprovementType = settlementImprovement.getType();
                upkeep = upkeep + settlementImprovementType.getUpkeepCost();
            }
        }
        upkeep = new SettlementImprovementUpkeepCostCalculator(upkeep, new Modifier[]{getPlayer()}).getUpkeepCost();
        return upkeep;
    }

    public Iterator<SettlementImprovementType> getBuildableImprovements() {
        List<SettlementImprovementType> buildableImprovements = new ArrayList<SettlementImprovementType>();
        Iterator<SettlementImprovementType> iterator = realm.getSettlementImprovementManager().getImprovementsIterator();
        while (iterator.hasNext()) {
            SettlementImprovementType cityImprovement = iterator.next();
            if (canStartBuild(cityImprovement)) {
                buildableImprovements.add(cityImprovement);
            }
        }
        return buildableImprovements.iterator();
    }

    public int getTotalResourceProduction(int resourceId) {
        return getResourceProductionFromTerrain(resourceId) + getResourceProductionFromImprovements(resourceId);
    }

    public int getTotalResourceProduction(Resource resource) {
        return getResourceProductionFromTerrain(resource) + getResourceProductionFromImprovements(resource);
    }

    public int getResourceProductionFromTerrain(int resourceId) {
        return getResourceProductionFromTerrain(realm.getResourceManager().getResource(resourceId));
    }

    public int getResourceProductionFromTerrain(Resource resource) {
        int totalProduction = getRawResourceProductionFromTerrain(resource);
        int actualProduction = (int) Utility.modifyByPercent(totalProduction, getResourceModifier(resource));
        return applyEfficiency(actualProduction);
    }

    public int getResourceProductionFromImprovements(int resourceId) {
        return getResourceProductionFromImprovements(realm.getResourceManager().getResource(resourceId));
    }

    public int getResourceProductionFromImprovements(Resource resource) {
        int resourceProductionFromImprovements = 0;
        Iterator<SettlementImprovement> iterator = getImprovementsIterator();
        while (iterator.hasNext()) {
            SettlementImprovement settlementImprovement = iterator.next();
            if (settlementImprovement.isEnabled() && settlementImprovement.getType().isResourceProducer() && settlementImprovement.getType().getMaximumWorkers() > 0) {
                int improvementMaximumResourceOutput = settlementImprovement.getType().getOutputQuantity(resource) * settlementImprovement.getType().getMaximumMultiplier();
                int improvementResourceOutput = (improvementMaximumResourceOutput * settlementImprovement.getNumberOfWorkers()) / settlementImprovement.getType().getMaximumWorkers();
                resourceProductionFromImprovements = resourceProductionFromImprovements + improvementResourceOutput;
            }
        }
        return resourceProductionFromImprovements;
    }

    public boolean canStartBuild(SettlementBuildable settlementBuildable) {
        if (settlementBuildable == null) {
            return false;
        }
        if (!arePrerequisitesSatisfied(settlementBuildable)) {
            return false;
        }
        BuildableProperty buildableProperty = (BuildableProperty) settlementBuildable.getProperty(BuildableProperty.NAME);
        if (buildableProperty == null) {
            return false;
        }
        if ((settlementBuildable instanceof SettlementImprovementType) && hasImprovementType((SettlementImprovementType) settlementBuildable)) {
            return false;
        }
        return true;
    }

    public boolean hasResourcesToComplete(SettlementBuildable settlementBuildable) {
        BuildableProperty buildableProperty = (BuildableProperty) settlementBuildable.getProperty(BuildableProperty.NAME);
        if (buildableProperty == null) {
            return false;
        }
        Iterator<Integer> resourceIdsIterator = buildableProperty.getBuildCostResourceIdsIterator();
        while (resourceIdsIterator.hasNext()) {
            Integer resourceId = resourceIdsIterator.next();
            Resource resource = getRealm().getResourceManager().getResource(resourceId);
            if (getResourceQuantity(resource) < buildableProperty.getBuildCostResourceQuantity(resourceId)) {
                return false;
            }
        }
        return true;
    }

    public void clearAutomanagedResources() {
        automanagedResources.clear();
    }

    public Iterator<Resource> getAutomanagedResourcesIterator() {
        return automanagedResources.iterator();
    }

    public boolean isAutomanagingResource(Resource resource) {
        return automanagedResources.contains(resource);
    }

    public void addAutomanagedResource(Resource resource) {
        if (!isAutomanagingResource(resource)) {
            automanagedResources.add(resource);
        }
    }

    public void removeAutomanagedResource(Resource resource) {
        if (isAutomanagingResource(resource)) {
            automanagedResources.remove(resource);
        }
    }

    public int getWealthCollectedByTax() {
        int wealthCollectedByTax = (getProductionWorkforce() * getPlayer().getTaxRate() * 3) / 100;
        wealthCollectedByTax = wealthCollectedByTax * (100 + getTaxIncomeModifier()) / 100;
        return wealthCollectedByTax;
    }

    public int getWealthCollectedByTaxIf(int tax) {
        int wealthCollectedByTax = (getProductionWorkforce() * tax * 3) / 100;
        wealthCollectedByTax = wealthCollectedByTax * (100 + getTaxIncomeModifier()) / 100;
        return wealthCollectedByTax;
    }

    public SettlementBuildable getCurrentProduction() {
        if (productionQueue.size() > 0) {
            return productionQueue.get(0);
        } else {
            return null;
        }
    }

    public void addToProductionQueue(SettlementBuildable buildable) {
        productionQueue.add(buildable);
    }

    public void removeFromProductionQueue(int index) {
        productionQueue.remove(index);
    }

    public void clearProductionQueue() {
        productionQueue.clear();
    }

    public Iterator<SettlementBuildable> getProductionQueueIterator() {
        return productionQueue.iterator();
    }

    public int getProductionWorkforce() {
        return getPopulation() - getWorkForceManager().getTotalWorkers() - getSettlementImprovementWorkerCount();
    }

    public int getProductionPointsPerTurn() {
        int productionPointsPerTurn = (getProductionWorkforce() * (100 - getPlayer().getTaxRate())) / 100;
        productionPointsPerTurn = (int) Utility.modifyByPercent(productionPointsPerTurn, getProductionModifier());
        return applyEfficiency(productionPointsPerTurn);
    }

    public int getMaxWorkersPerTile() {
        int maxWorkersPerTile = Integer.parseInt(getRealm().getProperty("max_workers_per_tile"));
        maxWorkersPerTile = maxWorkersPerTile + getMaximumTileWorkersModifier();
        return maxWorkersPerTile;
    }

    /**
     * This method will return all valid coordinates that can be assigned a
     * workforce by the settlement. Resulting Vector will not only contain empty
     * coordinates around the settlement, but also the coordinates that are
     * already assigned a workforce and those that are being used by a nearby
     * settlement.
     *
     * @return ArrayList
     */
    public ArrayList<Coordinate> getValidWorkForceCoordinates() {
        ArrayList<Coordinate> availableCoordinatesForWorkForce = new ArrayList<Coordinate>();
        availableCoordinatesForWorkForce.add(getCoordinate());
        availableCoordinatesForWorkForce.addAll(realm.getCircleCoordinates(getCoordinate(), 1));
        return availableCoordinatesForWorkForce;
    }

    public double getPopulationIncreasePercent() {
        double defaultPopulationIncrease = realm.getPopulationChangeManager().getPopulationIncreaseBasePercent(population);
        return defaultPopulationIncrease;
    }

    public double getPopulationDecreasePercent() {
        double defaultPopulationDecrease = realm.getPopulationChangeManager().getPopulationDecreaseBasePercent(population);
        return defaultPopulationDecrease;
    }

    public int getTotalCapacity(Resource resource) {
        int capacity = Integer.parseInt(realm.getProperty("default_storage"));
        capacity = capacity + getCapacityModifier(resource);
        return capacity;
    }

    // <editor-fold defaultstate="collapsed" desc="Getters & setters">
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
        manageWorkforce();
    }

    public int getProductionPoints() {
        return productionPoints;
    }

    public void setProductionPoints(int productionPoints) {
        this.productionPoints = productionPoints;
    }

    public ResourceStorageManager getStorageManager() {
        return storageManager;
    }

    public void setStorageManager(ResourceStorageManager resourceStorageManager) {
        this.storageManager = resourceStorageManager;
    }

    public WorkForceManager getWorkForceManager() {
        return workForceManager;
    }

    public boolean isContiuousProduction() {
        return contiuousProduction;
    }

    public void setContiuousProduction(boolean contiuousProduction) {
        this.contiuousProduction = contiuousProduction;
    }

    public int getResourceQuantity(Resource resource) {
        return getStorageManager().getResourceQuantity(resource);
    }

    public void setResourceQuantity(Resource resource, int quantity) {
        getStorageManager().setResourceQuantity(resource, quantity);
    }

    public int getRemainingCapacity(Resource resource) {
        return getTotalCapacity(resource) - getResourceQuantity(resource);
    }

    public TreeMap<Resource, Integer> getResources() {
        return getStorageManager().getResources();
    }

    public Iterator<Resource> getStorableResourcesIterator() {
        return realm.getResourceManager().getResourcesIterator();
    }

    public Iterator<Resource> getContainedResourcesIterator() {
        return realm.getResourceManager().getResourcesIterator();
    }

    public boolean isStoringResource(Resource resource) {
        return true;
    }

    private Realm getRealm() {
        return realm;
    }

    private void setRealm(Realm realm) {
        this.realm = realm;
    }

    private int getBaseEfficiency() {
        if (getPopulation() < 1000) {
            return 100;
        } else if (getPopulation() < 1500) {
            return 95;
        } else if (getPopulation() < 3500) {
            return 90;
        }
        return 80;
    }

    public int getEfficiency() {
        return getBaseEfficiency() + getEfficiencyModifier();
    }

    private int applyEfficiency(int value) {
        return Utility.calculatePercent(value, getEfficiency());
    }
    // </editor-fold>

    private int getSettlementImprovementWorkerCount() {
        int settlementImprovementWorkerCount = 0;
        Iterator<SettlementImprovement> iterator = getImprovementsIterator();
        while (iterator.hasNext()) {
            SettlementImprovement cityImprovement = iterator.next();
            settlementImprovementWorkerCount = settlementImprovementWorkerCount + cityImprovement.getNumberOfWorkers();
        }
        return settlementImprovementWorkerCount;
    }

    private int getRawResourceProductionFromTerrain(Resource resource) {
        int totalProduction = 0;
        Iterator workforceIterator = getWorkForceManager().getWorkforce().entrySet().iterator();
        while (workforceIterator.hasNext()) {
            Entry entry = (Entry) workforceIterator.next();
            WorkForce workForce = (WorkForce) entry.getValue();
            Resource workForceResource = workForce.getResource();
            if (resource.equals(workForceResource)) {
                Coordinate coordinate = (Coordinate) entry.getKey();
                Tile tile = getRealm().getTile(coordinate);
                int producedQuantity = tile.getProduction(resource) * workForce.getNumberOfWorkers();
                totalProduction = totalProduction + producedQuantity;
            }
        }
        return totalProduction;
    }

    private void manageWorkforce() {
        if (getProductionWorkforce() < 0) {
            int populationDecrease = -1 * getProductionWorkforce();
            Iterator<WorkForce> workforceIterator = getWorkForceManager().getWorkForceIterator();
            while (workforceIterator.hasNext()) {
                WorkForce workForce = (WorkForce) workforceIterator.next();
                if (workForce.getNumberOfWorkers() > populationDecrease) {
                    workForce.setNumberOfWorkers(workForce.getNumberOfWorkers() - populationDecrease);
                    return;
                } else {
                    populationDecrease = populationDecrease - workForce.getNumberOfWorkers();
                    workForce.setNumberOfWorkers(0);
                }
            }
        }
    }

    private boolean arePrerequisitesSatisfied(SettlementBuildable settlementBuildable) {
        Iterator<SettlementBuildablePrerequisite> prerequisitesIterator = settlementBuildable.getPrerequisitesIterator();
        while (prerequisitesIterator.hasNext()) {
            SettlementBuildablePrerequisite prerequisite = prerequisitesIterator.next();
            prerequisite.setSettlement(this);
            if (!prerequisite.isSatisfied()) {
                return false;
            }
        }
        return true;
    }
    // <editor-fold defaultstate="collapsed" desc="Modifiers">

    public int getResourceModifier(Resource resource) {
        int modifier = 0;
        Iterator<SettlementImprovement> iterator = getImprovementsIterator();
        while (iterator.hasNext()) {
            SettlementImprovement settlementImprovement = iterator.next();
            if (settlementImprovement.isEnabled()) {
                modifier = modifier + settlementImprovement.getType().getResourceProductionModifier(resource, true);
            }
        }
        return modifier;
    }

    public int getDefenceModifier() {
        int modifier = 0;
        Iterator<SettlementImprovement> iterator = getImprovementsIterator();
        while (iterator.hasNext()) {
            SettlementImprovement settlementImprovement = iterator.next();
            if (settlementImprovement.isEnabled()) {
                modifier = modifier + settlementImprovement.getType().getDefenceModifier();
            }
        }
        return modifier;
    }

    private int getTaxIncomeModifier() {
        int modifier = 0;
        Iterator<SettlementImprovement> iterator = getImprovementsIterator();
        while (iterator.hasNext()) {
            SettlementImprovement settlementImprovement = iterator.next();
            if (settlementImprovement.isEnabled()) {
                modifier = modifier + settlementImprovement.getType().getSettlementTaxModifier();
            }
        }
        return modifier;
    }

    private int getProductionModifier() {
        int modifier = 0;
        Iterator<SettlementImprovement> iterator = getImprovementsIterator();
        while (iterator.hasNext()) {
            SettlementImprovement settlementImprovement = iterator.next();
            if (settlementImprovement.isEnabled()) {
                modifier = modifier + settlementImprovement.getType().getProductionPointModifier();
            }
        }
        modifier = modifier + getPlayer().getProductionPointModifier();
        return modifier;
    }

    private int getMaximumTileWorkersModifier() {
        int modifier = 0;
        Iterator<SettlementImprovement> iterator = getImprovementsIterator();
        while (iterator.hasNext()) {
            SettlementImprovement settlementImprovement = iterator.next();
            if (settlementImprovement.isEnabled()) {

                modifier = modifier + settlementImprovement.getType().getMaximumTileWorkersModifier();
            }
        }
        ModifyMaximumWorkers modifyMaximumWorkers = (ModifyMaximumWorkers) getPlayer().getProperty("ModifyMaximumWorkers");
        if (modifyMaximumWorkers != null) {
            modifier = modifier + modifyMaximumWorkers.getModifier();
        }
        return modifier;
    }

    private int getEfficiencyModifier() {
        int modifier = 0;
        Iterator<SettlementImprovement> iterator = getImprovementsIterator();
        while (iterator.hasNext()) {
            SettlementImprovement settlementImprovement = iterator.next();
            if (settlementImprovement.isEnabled()) {
                modifier = modifier + settlementImprovement.getType().getEfficiencyModifier();
            }
        }
        return modifier;
    }

    private int getCapacityModifier(Resource resource) {
        int modifier = 0;
        Iterator<SettlementImprovement> iterator = getImprovementsIterator();
        while (iterator.hasNext()) {
            SettlementImprovement settlementImprovement = iterator.next();
            if (settlementImprovement.isEnabled()) {
                modifier = modifier + settlementImprovement.getType().getCapacityModifier(resource);
            }
        }
        return modifier;

    }

    public Tile getTile() {
        return realm.getTile(getCoordinate());
    }
    // </editor-fold>
}
