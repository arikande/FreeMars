package org.freerealm.history;

import java.util.Iterator;

/**
 *
 * @author Deniz ARIKAN
 */
public interface PlayerTurnHistory {

    public int getTurn();

    public void setTurn(int turn);

    public int getPopulation();

    public void setPopulation(int population);

    public int getWealth();

    public void setWealth(int wealth);

    public int getUnitCount();

    public void setUnitCount(int unitCount);

    public int getSettlementCount();

    public void setSettlementCount(int settlementCount);

    public int getMapExplorationPercent();

    public void setMapExplorationPercent(int explorationPercent);

    public void addCustomData(String name, String data);

    public String getCustomData(String name);

    public Iterator<String> getCustomDataIterator();
}
