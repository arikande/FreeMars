package org.freerealm.unit;

import org.freerealm.property.MoveProperty;
import org.freerealm.settlement.FreeRealmSettlementBuildable;

/**
 *
 * @author Deniz ARIKAN
 */
public class FreeRealmUnitType extends FreeRealmSettlementBuildable implements UnitType, Comparable<FreeRealmUnitType> {

    private int explorationRadius;
    private int weightForContainer;

    public int compareTo(FreeRealmUnitType unitType) {
        if (getId() < unitType.getId()) {
            return -1;
        } else if (getId() > unitType.getId()) {
            return 1;
        } else {
            return 0;
        }
    }

    public int getMovementPoints() {
        MoveProperty move = (MoveProperty) getProperty(MoveProperty.NAME);
        if (move != null) {
            return move.getPoints();
        } else {
            return 0;
        }
    }

    public int getExplorationRadius() {
        return explorationRadius;
    }

    public void setExplorationRadius(int explorationRadius) {
        this.explorationRadius = explorationRadius;
    }

    public int getWeightForContainer() {
        return weightForContainer;
    }

    public void setWeightForContainer(int weightForContainer) {
        this.weightForContainer = weightForContainer;
    }
}
