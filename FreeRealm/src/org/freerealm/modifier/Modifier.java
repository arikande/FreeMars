package org.freerealm.modifier;

import org.freerealm.property.PropertyContainer;
import org.freerealm.resource.Resource;

/**
 *
 * @author Deniz ARIKAN
 */
public interface Modifier extends PropertyContainer {

    public int getId();

    public void setId(int id);

    public String getName();

    public void setName(String name);

    public int getResourceProductionModifier(Resource resource, boolean resourceExists);

    public int getDefenceModifier();

    public int getProductionPointModifier();

    public int getSettlementTaxModifier();

    public int getMaximumTileWorkersModifier();

    public int getEfficiencyModifier();

    public int getCapacityModifier(Resource resource);

    public float getMovementCostModifier();

    public int getSettlementImprovementBuildCostModifier();

    public int getSettlementImprovementUpkeepCostModifier();

    public int getUnitBuildCostModifier();

    public int getUnitUpkeepCostModifier();

    public int getRequiredPopulationResourceAmountModifier(int resourceId);

}
