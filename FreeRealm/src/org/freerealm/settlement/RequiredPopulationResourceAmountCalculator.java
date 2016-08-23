package org.freerealm.settlement;

import org.freerealm.Realm;
import org.freerealm.modifier.Modifier;
import org.freerealm.resource.Resource;

/**
 *
 * @author Deniz ARIKAN
 */
public class RequiredPopulationResourceAmountCalculator {

    private Realm realm;
    private int resourceId;
    private Modifier[] modifiers;

    public RequiredPopulationResourceAmountCalculator(Realm realm, int resourceId, Modifier[] modifiers) {
        this.realm = realm;
        this.resourceId = resourceId;
        this.modifiers = modifiers;
    }

    public RequiredPopulationResourceAmountCalculator(Realm realm, Resource resource, Modifier[] modifiers) {
        this(realm, resource.getId(), modifiers);
    }

    public int getRequiredPopulationResourceAmount() {
        int requiredPopulationResourceAmount = realm.getRequiredPopulationResourceAmount(resourceId) + getModifier(resourceId);
        if (requiredPopulationResourceAmount < 0) {
            requiredPopulationResourceAmount = 0;
        }
        return requiredPopulationResourceAmount;
    }

    private int getModifier(int resourceId) {
        int modifierTotal = 0;
        for (Modifier modifier : modifiers) {
            modifierTotal = modifierTotal + modifier.getRequiredPopulationResourceAmountModifier(resourceId);
        }
        return modifierTotal;
    }
}
