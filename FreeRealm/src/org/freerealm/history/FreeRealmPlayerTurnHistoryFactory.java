package org.freerealm.history;

import org.freerealm.Realm;
import org.freerealm.player.Player;

/**
 *
 * @author Deniz ARIKAN
 */
public class FreeRealmPlayerTurnHistoryFactory {

    public FreeRealmPlayerTurnHistory preparePlayerHistory(Realm realm, Player player) {
        FreeRealmPlayerTurnHistory playerTurnHistory = new FreeRealmPlayerTurnHistory();
        playerTurnHistory.setTurn(realm.getNumberOfTurns());
        playerTurnHistory.setPopulation(player.getPopulation());
        playerTurnHistory.setWealth(player.getWealth());
        playerTurnHistory.setSettlementCount(player.getSettlementCount());
        playerTurnHistory.setUnitCount(player.getUnitCount());
        int mapExplorationPercent = (player.getExploredCoordinateCount() * 100) / (realm.getMapHeight() * realm.getMapWidth());
        playerTurnHistory.setMapExplorationPercent(mapExplorationPercent);
        return playerTurnHistory;
    }
}
