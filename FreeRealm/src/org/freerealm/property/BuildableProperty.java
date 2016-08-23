package org.freerealm.property;

import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * @author Deniz ARIKAN
 */
public class BuildableProperty implements Property {

    public static final String NAME = "buildable_property";
    private int buildCost;
    private int upkeepCost;
    private int populationCost;
    private final HashMap<Integer, Integer> buildCostResourceIds;

    public BuildableProperty() {
        buildCostResourceIds = new HashMap<Integer, Integer>();
    }

    public String getName() {
        return NAME;
    }

    public int getBuildCost() {
        return buildCost;
    }

    public void setBuildCost(int buildCost) {
        this.buildCost = buildCost;
    }

    public int getBuildCostResourceCount() {
        return buildCostResourceIds.size();
    }

    public Iterator<Integer> getBuildCostResourceIdsIterator() {
        return buildCostResourceIds.keySet().iterator();
    }

    public int getBuildCostResourceQuantity(int resourceId) {
        if (buildCostResourceIds.containsKey(resourceId)) {
            return buildCostResourceIds.get(resourceId);
        } else {
            return 0;
        }
    }

    public void addBuildCostResourceQuantity(int resourceId, int resourceQuantity) {
        if (resourceQuantity > 0) {
            buildCostResourceIds.put(resourceId, resourceQuantity);
        }
    }

    public int getUpkeepCost() {
        return upkeepCost;
    }

    public void setUpkeepCost(int upkeepCost) {
        this.upkeepCost = upkeepCost;
    }

    public int getPopulationCost() {
        return populationCost;
    }

    public void setPopulationCost(int populationCost) {
        this.populationCost = populationCost;
    }
}
