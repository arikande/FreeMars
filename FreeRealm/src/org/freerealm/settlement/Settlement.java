package org.freerealm.settlement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;
import org.freerealm.ResourceStorageManager;
import org.freerealm.map.Coordinate;
import org.freerealm.map.PlayerMapItem;
import org.freerealm.resource.Resource;
import org.freerealm.resource.ResourceStorer;
import org.freerealm.settlement.improvement.SettlementImprovement;
import org.freerealm.settlement.improvement.SettlementImprovementType;
import org.freerealm.tile.Tile;

/**
 *
 * @author Deniz ARIKAN
 */
public interface Settlement extends PlayerMapItem, ResourceStorer {

    public boolean hasImprovementType(SettlementImprovementType improvementType);

    public boolean hasImprovementType(int improvementTypeId);

    public SettlementImprovement getImprovementOfType(SettlementImprovementType improvementType);

    public void addImprovement(SettlementImprovement improvement);

    public void removeImprovement(int improvementTypeId);

    public void clearImprovements();

    public Iterator<SettlementImprovement> getImprovementsIterator();

    public int getImprovementUpkeep();

    public int getImprovementCount();

    public Iterator<SettlementImprovementType> getBuildableImprovements();

    public int getTotalResourceProduction(int resourceId);

    public int getTotalResourceProduction(Resource resource);

    public int getResourceProductionFromTerrain(int resourceId);

    public int getResourceProductionFromTerrain(Resource resource);

    public int getResourceProductionFromImprovements(int resourceId);

    public int getResourceProductionFromImprovements(Resource resource);

    public boolean canStartBuild(SettlementBuildable buildable);

    public void clearAutomanagedResources();

    public Iterator<Resource> getAutomanagedResourcesIterator();

    public boolean isAutomanagingResource(Resource resource);

    public void addAutomanagedResource(Resource resource);

    public void removeAutomanagedResource(Resource resource);

    public int getWealthCollectedByTax();

    public int getWealthCollectedByTaxIf(int tax);

    public SettlementBuildable getCurrentProduction();

    public void addToProductionQueue(SettlementBuildable buildable);

    public void removeFromProductionQueue(int index);

    public void clearProductionQueue();

    public Iterator<SettlementBuildable> getProductionQueueIterator();

    public int getProductionWorkforce();

    public int getProductionPointsPerTurn();

    public int getMaxWorkersPerTile();

    public ArrayList<Coordinate> getValidWorkForceCoordinates();

    public double getPopulationIncreasePercent();

    public double getPopulationDecreasePercent();

    public int getTotalCapacity(Resource resource);

    public String getName();

    public void setName(String name);

    public int getPopulation();

    public void setPopulation(int population);

    public int getProductionPoints();

    public void setProductionPoints(int productionPoints);

    public ResourceStorageManager getStorageManager();

    public void setStorageManager(ResourceStorageManager resourceStorageManager);

    public WorkForceManager getWorkForceManager();

    public boolean isContiuousProduction();

    public void setContiuousProduction(boolean contiuousProduction);

    public int getResourceQuantity(Resource resource);

    public void setResourceQuantity(Resource resource, int quantity);

    public int getRemainingCapacity(Resource resource);

    public TreeMap<Resource, Integer> getResources();

    public Iterator<Resource> getStorableResourcesIterator();

    public Iterator<Resource> getContainedResourcesIterator();

    public boolean isStoringResource(Resource resource);

    public int getEfficiency();

    public int getResourceModifier(Resource resource);

    public int getDefenceModifier();

    public Tile getTile();
}
