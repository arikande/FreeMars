package org.freemars.colony.manager;

import org.freemars.colony.FreeMarsColony;
import org.freerealm.resource.Resource;

/**
 *
 * @author Deniz ARIKAN
 */
public class SettlementResourceAmountHelper {

    public static int[] getSettlementResourceAmountValues(FreeMarsColony freeMarsColony, Resource resource) {
        int[] settlementResourceAmountValues = {0, 0, 0};
        int resourceQuantity = freeMarsColony.getResourceQuantity(resource);
        int resourceProduction = freeMarsColony.getResourceProductionFromTerrain(resource.getId()) + freeMarsColony.getResourceProductionFromImprovements(resource.getId());
        int resourceStorage = freeMarsColony.getRemainingCapacity(resource);
        settlementResourceAmountValues[0] = resourceQuantity;
        settlementResourceAmountValues[1] = resourceProduction;
        settlementResourceAmountValues[2] = resourceStorage;
        return settlementResourceAmountValues;
    }
}
