package org.freerealm.settlement.improvement;

import java.util.Iterator;
import java.util.Vector;
import org.freerealm.settlement.Settlement;
import org.freerealm.settlement.SettlementBuildablePrerequisite;

/**
 *
 * @author Deniz ARIKAN
 */
public class SettlementImprovementPrerequisite implements SettlementBuildablePrerequisite {

    private Settlement settlement;
    private final Vector<SettlementImprovementType> prerequisiteImprovements;

    public SettlementImprovementPrerequisite(Vector<SettlementImprovementType> prerequisiteImprovements) {
        this.prerequisiteImprovements = prerequisiteImprovements;
    }

    public boolean isSatisfied() {
        for (SettlementImprovementType improvementType : prerequisiteImprovements) {
            if (!settlement.hasImprovementType(improvementType)) {
                return false;
            }
        }
        return true;
    }

    public void setSettlement(Settlement settlement) {
        this.settlement = settlement;
    }

    public Iterator<SettlementImprovementType> getPrerequisiteImprovementsIterator() {
        return prerequisiteImprovements.iterator();
    }
}
