package org.freerealm.history;

import java.util.HashMap;
import java.util.Iterator;
import org.freerealm.player.Player;

/**
 *
 * @author Deniz ARIKAN
 */
public class FreeRealmHistory implements History {

    private final HashMap<Player, PlayerHistory> playerHistoryMap;

    public FreeRealmHistory() {
        playerHistoryMap = new HashMap<Player, PlayerHistory>();
    }

    public PlayerHistory getPlayerHistory(Player player) {
        return playerHistoryMap.get(player);
    }

    public void addPlayerTurnHistory(Player player, PlayerTurnHistory playerTurnHistory) {
        getPlayerHistory(player).addTurnHistory(playerTurnHistory);
    }

    public PlayerTurnHistory getPlayerTurnHistory(Player player, int turn) {
        return getPlayerHistory(player).getTurnHistory(turn);
    }

    public void addPlayerHistory(Player player, PlayerHistory playerHistory) {
        playerHistoryMap.put(player, playerHistory);
    }

    public Iterator<Player> getPlayersIterator() {
        return playerHistoryMap.keySet().iterator();
    }
}
