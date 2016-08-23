package org.freerealm;

import org.freerealm.modifier.Modifier;

/**
 *
 * @author Deniz ARIKAN
 */
public class UnitUpkeepCostCalculator {

    private int upkeepCost;
    private final Modifier[] modifiers;

    public UnitUpkeepCostCalculator(int upkeepCost, Modifier[] generalModifiers) {
        this.upkeepCost = upkeepCost;
        this.modifiers = generalModifiers;
    }

    public int getUpkeepCost() {
        int modifier = 0;
        for (Modifier generalModifier : modifiers) {
            modifier = modifier + generalModifier.getUnitUpkeepCostModifier();
        }
        upkeepCost = (int) Utility.modifyByPercent(upkeepCost, modifier);
        return upkeepCost;
    }
}
