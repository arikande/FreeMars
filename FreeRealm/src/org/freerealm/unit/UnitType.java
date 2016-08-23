package org.freerealm.unit;

import org.freerealm.settlement.SettlementBuildable;

/**
 *
 * @author Deniz ARIKAN
 */
public interface UnitType extends SettlementBuildable {

    public int getMovementPoints();

    public int getExplorationRadius();

    public void setExplorationRadius(int explorationRadius);

    public int getWeightForContainer();

    public void setWeightForContainer(int weightForContainer);
}
