package org.freerealm.modifier;

import java.util.Iterator;
import org.freerealm.property.FreeRealmPropertyContainer;
import org.freerealm.property.ModifyDefence;
import org.freerealm.property.ModifyEfficiency;
import org.freerealm.property.ModifyMaximumWorkers;
import org.freerealm.property.ModifyProduction;
import org.freerealm.property.ModifyRequiredPopulationResourceAmount;
import org.freerealm.property.ModifyResourceProduction;
import org.freerealm.property.ModifySettlementImprovementCost;
import org.freerealm.property.ModifySettlementImprovementUpkeepCost;
import org.freerealm.property.ModifyTaxIncome;
import org.freerealm.property.ModifyUnitCost;
import org.freerealm.property.ModifyUnitUpkeepCost;
import org.freerealm.property.Property;
import org.freerealm.property.SetMovementCostProperty;
import org.freerealm.property.StoreResourceProperty;
import org.freerealm.resource.Resource;

/**
 *
 * @author Deniz ARIKAN
 */
public class FreeRealmModifier extends FreeRealmPropertyContainer implements Modifier {

    private int id;
    private String name;

    @Override
    public String toString() {
        return getName();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getResourceProductionModifier(Resource resource, boolean resourceExists) {
        int resourceModifier = 0;
        Iterator<Property> propertyIterator = getPropertiesIterator();
        while (propertyIterator.hasNext()) {
            Property property = propertyIterator.next();
            if (property instanceof ModifyResourceProduction) {
                ModifyResourceProduction modifyResourceProduction = (ModifyResourceProduction) property;
                if (resource.equals(modifyResourceProduction.getResource())) {
                    if (!modifyResourceProduction.isModifyingOnlyIfResourceExists() || (modifyResourceProduction.isModifyingOnlyIfResourceExists() && resourceExists)) {
                        resourceModifier = resourceModifier + modifyResourceProduction.getModifier();
                    }
                }
            }
        }
        return resourceModifier;
    }

    public int getDefenceModifier() {
        int defenceModifier = 0;
        Iterator<Property> propertyEditor = getPropertiesIterator();
        while (propertyEditor.hasNext()) {
            Property property = propertyEditor.next();
            if (property instanceof ModifyDefence) {
                ModifyDefence increaseDefence = (ModifyDefence) property;
                defenceModifier = defenceModifier + increaseDefence.getModifier();
            }
        }
        return defenceModifier;
    }

    public int getProductionPointModifier() {
        int productionModifier = 0;
        Iterator propertyIterator = getPropertiesIterator();
        while (propertyIterator.hasNext()) {
            Property property = (Property) propertyIterator.next();
            if (property instanceof ModifyProduction) {
                ModifyProduction increaseProduction = (ModifyProduction) property;
                productionModifier = productionModifier + increaseProduction.getModifier();
            }
        }
        return productionModifier;
    }

    public int getCapacityModifier(Resource resource) {
        int capacityModifier = 0;
        Iterator<Property> propertyEditor = getPropertiesIterator();
        while (propertyEditor.hasNext()) {
            Property property = propertyEditor.next();
            if (property instanceof StoreResourceProperty) {
                StoreResourceProperty storeResource = (StoreResourceProperty) property;
                if (resource.equals(storeResource.getResource())) {
                    capacityModifier = capacityModifier + storeResource.getStorage();
                }
            }
        }
        return capacityModifier;
    }

    public int getSettlementTaxModifier() {
        int taxModifier = 0;
        Iterator propertyEditor = getPropertiesIterator();
        while (propertyEditor.hasNext()) {
            Property property = (Property) propertyEditor.next();
            if (property instanceof ModifyTaxIncome) {
                ModifyTaxIncome increaseTaxIncome = (ModifyTaxIncome) property;
                taxModifier = taxModifier + increaseTaxIncome.getModifier();
            }
        }
        return taxModifier;
    }

    public int getEfficiencyModifier() {
        int efficiencyModifier = 0;
        Iterator propertyEditor = getPropertiesIterator();
        while (propertyEditor.hasNext()) {
            Property property = (Property) propertyEditor.next();
            if (property instanceof ModifyEfficiency) {
                ModifyEfficiency increaseEfficiency = (ModifyEfficiency) property;
                efficiencyModifier = efficiencyModifier + increaseEfficiency.getModifier();
            }
        }
        return efficiencyModifier;
    }

    public int getMaximumTileWorkersModifier() {
        int increasePercent = 0;
        Iterator propertyEditor = getPropertiesIterator();
        while (propertyEditor.hasNext()) {
            Property property = (Property) propertyEditor.next();
            if (property instanceof ModifyMaximumWorkers) {
                ModifyMaximumWorkers modifyMaximumWorkers = (ModifyMaximumWorkers) property;
                increasePercent = increasePercent + modifyMaximumWorkers.getModifier();
            }
        }
        return increasePercent;
    }

    public float getMovementCostModifier() {
        float movementCost = 0;
        Iterator<Property> propertyEditor = getPropertiesIterator();
        while (propertyEditor.hasNext()) {
            Property property = propertyEditor.next();
            if (property instanceof SetMovementCostProperty) {
                SetMovementCostProperty setMovementCost = (SetMovementCostProperty) property;
                if (movementCost == 0 || setMovementCost.getMovementCost() < movementCost) {
                    movementCost = setMovementCost.getMovementCost();
                }
            }
        }
        return movementCost;
    }

    public int getRequiredPopulationResourceAmountModifier(int resourceId) {
        int requiredPopulationResourceAmountModifier = 0;
        Iterator<Property> propertyEditor = getPropertiesIterator();
        while (propertyEditor.hasNext()) {
            Property property = propertyEditor.next();
            if (property instanceof ModifyRequiredPopulationResourceAmount) {
                ModifyRequiredPopulationResourceAmount modifyRequiredPopulationResourceAmount = (ModifyRequiredPopulationResourceAmount) property;
                if (modifyRequiredPopulationResourceAmount.getResourceId() == resourceId) {
                    requiredPopulationResourceAmountModifier = requiredPopulationResourceAmountModifier + modifyRequiredPopulationResourceAmount.getModifier();
                }
            }
        }
        return requiredPopulationResourceAmountModifier;
    }

    public int getUnitUpkeepCostModifier() {
        int modifier = 0;
        Iterator<Property> propertyEditor = getPropertiesIterator();
        while (propertyEditor.hasNext()) {
            Property property = propertyEditor.next();
            if (property instanceof ModifyUnitUpkeepCost) {
                ModifyUnitUpkeepCost modifyUnitUpkeepCost = (ModifyUnitUpkeepCost) property;
                modifier = modifier + modifyUnitUpkeepCost.getModifier();
            }
        }
        return modifier;
    }

    public int getUnitBuildCostModifier() {
        int unitCostModifier = 0;
        Iterator<Property> propertyEditor = getPropertiesIterator();
        while (propertyEditor.hasNext()) {
            Property property = propertyEditor.next();
            if (property instanceof ModifyUnitCost) {
                ModifyUnitCost modifyUnitCost = (ModifyUnitCost) property;
                unitCostModifier = unitCostModifier + modifyUnitCost.getModifier();
            }
        }
        return unitCostModifier;
    }

    public int getSettlementImprovementBuildCostModifier() {
        int settlementImprovementCostModifier = 0;
        Iterator<Property> propertyEditor = getPropertiesIterator();
        while (propertyEditor.hasNext()) {
            Property property = propertyEditor.next();
            if (property instanceof ModifySettlementImprovementCost) {
                ModifySettlementImprovementCost modifySettlementImprovementCost = (ModifySettlementImprovementCost) property;
                settlementImprovementCostModifier = settlementImprovementCostModifier + modifySettlementImprovementCost.getModifier();
            }
        }
        return settlementImprovementCostModifier;
    }

    public int getSettlementImprovementUpkeepCostModifier() {
        int settlementImprovementUpkeepCostModifier = 0;
        Iterator<Property> propertyEditor = getPropertiesIterator();
        while (propertyEditor.hasNext()) {
            Property property = propertyEditor.next();
            if (property instanceof ModifySettlementImprovementUpkeepCost) {
                ModifySettlementImprovementUpkeepCost modifySettlementImprovementUpkeepCost = (ModifySettlementImprovementUpkeepCost) property;
                settlementImprovementUpkeepCostModifier = settlementImprovementUpkeepCostModifier + modifySettlementImprovementUpkeepCost.getModifier();
            }
        }
        return settlementImprovementUpkeepCostModifier;
    }

}
