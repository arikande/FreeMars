package org.freerealm.history;

import java.util.Iterator;
import java.util.TreeMap;
import org.freerealm.player.Player;

/**
 *
 * @author Deniz ARIKAN
 */
public class FreeRealmPlayerHistory implements PlayerHistory {

    private Player player;
    private final TreeMap<Integer, PlayerTurnHistory> playerTurnHistoryMap;

    public FreeRealmPlayerHistory() {
        playerTurnHistoryMap = new TreeMap<Integer, PlayerTurnHistory>();
    }

    public void addTurnHistory(PlayerTurnHistory playerTurnHistory) {
        playerTurnHistoryMap.put(playerTurnHistory.getTurn(), playerTurnHistory);
    }

    public PlayerTurnHistory getTurnHistory(int turn) {
        return playerTurnHistoryMap.get(turn);
    }

    public Iterator<PlayerTurnHistory> getPlayerTurnHistoryIterator() {
        return playerTurnHistoryMap.values().iterator();
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
