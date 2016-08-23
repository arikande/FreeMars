package org.freerealm.player;

import java.util.Iterator;
import java.util.TreeMap;
import org.freerealm.settlement.Settlement;
import org.freerealm.settlement.SettlementBuildable;

/**
 *
 * @author Deniz ARIKAN
 */
public class SettlementManager {

    private final TreeMap<Integer, Settlement> settlements;
    private final Player player;

    public SettlementManager(Player player) {
        this.player = player;
        settlements = new TreeMap<Integer, Settlement>();
    }

    public void addSettlement(Settlement settlement) {
        settlements.put(settlement.getId(), settlement);
    }

    public void removeSettlement(Settlement settlement) {
        settlements.remove(settlement.getId());
    }

    public Settlement getSettlement(int settlementId) {
        return settlements.get(settlementId);
    }

    public Iterator<Settlement> getSettlementsIterator() {
        return settlements.values().iterator();
    }

    public int getSettlementCount() {
        return settlements.size();
    }

    public Player getPlayer() {
        return player;
    }

    public int getSettlementUpkeep() {
        int upkeep = 0;
        Iterator<Settlement> settlementIterator = getSettlementsIterator();
        while (settlementIterator.hasNext()) {
            upkeep = upkeep + settlementIterator.next().getImprovementUpkeep();
        }
        return upkeep;
    }

    public int getSettlementsProducingBuildableCount(SettlementBuildable buildable) {
        int result = 0;
        Iterator<Settlement> iterator = getSettlementsIterator();
        while (iterator.hasNext()) {
            Settlement settlement = iterator.next();
            if (settlement.getCurrentProduction() != null && settlement.getCurrentProduction().equals(buildable)) {
                result = result + 1;
            }
        }
        return result;
    }
}
