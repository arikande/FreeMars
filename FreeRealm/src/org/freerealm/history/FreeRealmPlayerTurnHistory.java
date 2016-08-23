package org.freerealm.history;

import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * @author Deniz ARIKAN
 */
public class FreeRealmPlayerTurnHistory implements PlayerTurnHistory {

    private int turn;
    private int population;
    private int wealth;
    private int settlementCount;
    private int unitCount;
    private int mapExplorationPercent;
    private final HashMap<String, String> customData;

    public FreeRealmPlayerTurnHistory() {
        customData = new HashMap<String, String>();
    }

    public int getTurn() {
        return turn;
    }

    public int getPopulation() {
        return population;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public int getWealth() {
        return wealth;
    }

    public void setWealth(int wealth) {
        this.wealth = wealth;
    }

    public int getSettlementCount() {
        return settlementCount;
    }

    public void setSettlementCount(int settlementCount) {
        this.settlementCount = settlementCount;
    }

    public int getUnitCount() {
        return unitCount;
    }

    public void setUnitCount(int unitCount) {
        this.unitCount = unitCount;
    }

    public int getMapExplorationPercent() {
        return mapExplorationPercent;
    }

    public void setMapExplorationPercent(int mapExplorationPercent) {
        this.mapExplorationPercent = mapExplorationPercent;
    }

    public void addCustomData(String name, String data) {
        customData.put(name, data);
    }

    public String getCustomData(String name) {
        return customData.get(name);
    }

    public Iterator<String> getCustomDataIterator() {
        return customData.keySet().iterator();
    }
}
