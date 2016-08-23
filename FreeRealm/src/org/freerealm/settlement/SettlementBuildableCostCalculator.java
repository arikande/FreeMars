package org.freerealm.settlement;

import org.freerealm.Utility;
import org.freerealm.modifier.Modifier;
import org.freerealm.settlement.improvement.SettlementImprovementType;
import org.freerealm.unit.FreeRealmUnitType;

/**
 *
 * @author Deniz ARIKAN
 */
public class SettlementBuildableCostCalculator {

    private final SettlementBuildable settlementBuildable;
    private final Modifier[] modifiers;

    public SettlementBuildableCostCalculator(SettlementBuildable settlementBuildable, Modifier[] settlementModifiers) {
        this.settlementBuildable = settlementBuildable;
        this.modifiers = settlementModifiers;
    }

    public int getCost() {
        int productionCost = settlementBuildable.getProductionCost();
        int settlementImprovementBuildCostModifier = 0;
        int unitBuildCostModifier = 0;
        for (Modifier settlementModifier : modifiers) {
            settlementImprovementBuildCostModifier = settlementImprovementBuildCostModifier + settlementModifier.getSettlementImprovementBuildCostModifier();
            unitBuildCostModifier = unitBuildCostModifier + settlementModifier.getUnitBuildCostModifier();
        }
        if (settlementBuildable instanceof SettlementImprovementType) {
            productionCost = (int) Utility.modifyByPercent(productionCost, settlementImprovementBuildCostModifier);
        } else if (settlementBuildable instanceof FreeRealmUnitType) {
            productionCost = (int) Utility.modifyByPercent(productionCost, unitBuildCostModifier);
        }
        return productionCost;
    }
}
