package org.freerealm;

import org.freerealm.modifier.Modifier;

/**
 *
 * @author Deniz ARIKAN
 */
public class SettlementImprovementUpkeepCostCalculator {

    private int upkeepCost;
    private final Modifier[] modifiers;

    public SettlementImprovementUpkeepCostCalculator(int upkeepCost, Modifier[] generalModifiers) {
        this.upkeepCost = upkeepCost;
        this.modifiers = generalModifiers;
    }

    public int getUpkeepCost() {
        int settlementImprovementUpkeepCostModifier = 0;
        for (Modifier generalModifier : modifiers) {
            settlementImprovementUpkeepCostModifier = settlementImprovementUpkeepCostModifier + generalModifier.getSettlementImprovementUpkeepCostModifier();
        }
        upkeepCost = (int) Utility.modifyByPercent(upkeepCost, settlementImprovementUpkeepCostModifier);
        return upkeepCost;
    }
}
