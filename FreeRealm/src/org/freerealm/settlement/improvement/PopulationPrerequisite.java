package org.freerealm.settlement.improvement;

import org.freerealm.settlement.Settlement;
import org.freerealm.settlement.SettlementBuildablePrerequisite;

/**
 *
 * @author Deniz ARIKAN
 */
public class PopulationPrerequisite implements SettlementBuildablePrerequisite {

    private Settlement settlement;
    private final int prerequisitePopulation;

    public PopulationPrerequisite(int prerequisitePopulation) {
        this.prerequisitePopulation = prerequisitePopulation;
    }

    public boolean isSatisfied() {
        if (settlement.getPopulation() >= getPrerequisitePopulation()) {
            return true;
        } else {
            return false;
        }
    }

    public void setSettlement(Settlement settlement) {
        this.settlement = settlement;
    }

    public int getPrerequisitePopulation() {
        return prerequisitePopulation;
    }
}
