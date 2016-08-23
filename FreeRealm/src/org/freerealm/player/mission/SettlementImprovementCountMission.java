package org.freerealm.player.mission;

import java.util.Iterator;
import java.util.TreeMap;

/**
 *
 * @author Deniz ARIKAN
 */
public class SettlementImprovementCountMission extends AbstractMission {

    private static final String NAME = "settlementImprovementCountMission";
    private final TreeMap<Integer, Integer> settlementImprovementCountTargetValues;

    public SettlementImprovementCountMission() {
        settlementImprovementCountTargetValues = new TreeMap<Integer, Integer>();
    }

    @Override
    public Mission clone() {
        SettlementImprovementCountMission clone = new SettlementImprovementCountMission();
        copyTo(clone);
        Iterator<Integer> iterator = getTargetImprovementTypesIterator();
        while (iterator.hasNext()) {
            Integer typeId = iterator.next();
            clone.addSettlementImprovementCountTargetValue(typeId, getTargetCountForImprovementType(typeId));
        }
        return clone;
    }

    public String getMissionName() {
        return NAME;
    }

    public void checkStatus() {
        Iterator<Integer> settlementImprovementIdsIterator = settlementImprovementCountTargetValues.keySet().iterator();
        while (settlementImprovementIdsIterator.hasNext()) {
            int settlementImprovementId = settlementImprovementIdsIterator.next();
            int settlementImprovementCountTargetValue = settlementImprovementCountTargetValues.get(settlementImprovementId);
            if (getPlayer().getBuiltSettlementImprovementCount(settlementImprovementId) < settlementImprovementCountTargetValue) {
                checkIfExpired();
                return;
            }
        }
        setStatus(Mission.STATUS_COMPLETED);
    }

    public int getProgressPercent() {
        if (getStatus() == Mission.STATUS_COMPLETED) {
            return 100;
        } else if (getStatus() == Mission.STATUS_FAILED) {
            return 0;
        } else {
            int settlementImprovementCompletedCount = 0;
            int settlementImprovementTargetCount = 0;
            Iterator<Integer> settlementImprovementIdsIterator = settlementImprovementCountTargetValues.keySet().iterator();
            while (settlementImprovementIdsIterator.hasNext()) {
                int settlementImprovementId = settlementImprovementIdsIterator.next();
                int settlementImprovementCountTargetValue = settlementImprovementCountTargetValues.get(settlementImprovementId);
                settlementImprovementTargetCount = settlementImprovementTargetCount + settlementImprovementCountTargetValue;
                if (getPlayer().getBuiltSettlementImprovementCount(settlementImprovementId) > settlementImprovementCountTargetValue) {
                    settlementImprovementCompletedCount = settlementImprovementCompletedCount + settlementImprovementCountTargetValue;
                } else {
                    settlementImprovementCompletedCount = settlementImprovementCompletedCount + getPlayer().getBuiltSettlementImprovementCount(settlementImprovementId);
                }
            }
            return settlementImprovementCompletedCount * 100 / settlementImprovementTargetCount;
        }
    }

    public Iterator<Integer> getTargetImprovementTypesIterator() {
        return settlementImprovementCountTargetValues.keySet().iterator();
    }

    public int getTargetImprovementTypeCount() {
        return settlementImprovementCountTargetValues.size();
    }

    public int getTargetCountForImprovementType(int typeId) {
        if (settlementImprovementCountTargetValues.containsKey(typeId)) {
            return settlementImprovementCountTargetValues.get(typeId);
        } else {
            return 0;
        }
    }

    public void addSettlementImprovementCountTargetValue(int typeId, int count) {
        settlementImprovementCountTargetValues.put(typeId, count);
    }
}
