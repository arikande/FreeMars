package org.freerealm.settlement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.freerealm.modifier.FreeRealmModifier;
import org.freerealm.property.BuildableProperty;

/**
 *
 * @author Deniz ARIKAN
 */
public class FreeRealmSettlementBuildable extends FreeRealmModifier implements SettlementBuildable {

    private final List<SettlementBuildablePrerequisite> prerequisites;

    public FreeRealmSettlementBuildable() {
        prerequisites = new ArrayList<SettlementBuildablePrerequisite>();
    }

    public int getProductionCost() {
        BuildableProperty buildable = (BuildableProperty) getProperty(BuildableProperty.NAME);
        if (buildable != null) {
            return buildable.getBuildCost();
        } else {
            return 0;
        }
    }

    public int getUpkeepCost() {
        BuildableProperty buildable = (BuildableProperty) getProperty(BuildableProperty.NAME);
        if (buildable != null) {
            return buildable.getUpkeepCost();
        } else {
            return 0;
        }
    }

    public int getPopulationCost() {
        BuildableProperty buildable = (BuildableProperty) getProperty(BuildableProperty.NAME);
        if (buildable != null) {
            return buildable.getPopulationCost();
        } else {
            return 0;
        }
    }

    public int getBuildCostResourceCount() {
        BuildableProperty buildable = (BuildableProperty) getProperty(BuildableProperty.NAME);
        if (buildable != null) {
            return buildable.getBuildCostResourceCount();
        } else {
            return 0;
        }
    }

    public Iterator<Integer> getBuildCostResourceIdsIterator() {
        BuildableProperty buildable = (BuildableProperty) getProperty(BuildableProperty.NAME);
        if (buildable != null) {
            return buildable.getBuildCostResourceIdsIterator();
        } else {
            return null;
        }
    }

    public int getBuildCostResourceQuantity(int resourceId) {
        BuildableProperty buildable = (BuildableProperty) getProperty(BuildableProperty.NAME);
        if (buildable != null) {
            return buildable.getBuildCostResourceQuantity(resourceId);
        } else {
            return 0;
        }
    }

    public Iterator<SettlementBuildablePrerequisite> getPrerequisitesIterator() {
        return prerequisites.iterator();
    }

    public void addPrerequisite(SettlementBuildablePrerequisite prerequisite) {
        prerequisites.add(prerequisite);
    }

    public int getPrerequisiteCount() {
        return prerequisites.size();
    }
}
