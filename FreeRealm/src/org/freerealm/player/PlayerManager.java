package org.freerealm.player;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 *
 * @author Deniz ARIKAN
 */
public class PlayerManager {

    private Player activePlayer;
    private final TreeMap<Integer, Player> players;

    public PlayerManager() {
        players = new TreeMap<Integer, Player>();
    }

    private TreeMap<Integer, Player> getPlayers() {
        return players;
    }

    public void addPlayer(Player player) {
        getPlayers().put(player.getId(), player);
    }

    public void removePlayer(Player player) {
        getPlayers().remove(player.getId());
    }

    public Player getActivePlayer() {
        return activePlayer;
    }

    public void setActivePlayer(Player activePlayer) {
        this.activePlayer = activePlayer;
    }

    public Player getPlayer(int id) {
        return getPlayers().get(id);
    }

    public Player getPlayer(String name) {
        Player returnValue = null;
        Iterator<Player> playerIterator = getPlayersIterator();
        while (playerIterator.hasNext()) {
            Player player = (Player) playerIterator.next();
            if (player.getName().equals(name)) {
                returnValue = player;
                break;
            }
        }
        return returnValue;
    }

    public Player getFirstPlayer() {
        return getPlayers().get(getPlayers().firstKey());
    }

    public Player getNextPlayer(Player player) {
        boolean found = false;
        Iterator playerIterator = getPlayers().entrySet().iterator();
        Player returnValue = getPlayers().get(getPlayers().firstKey());
        while (playerIterator.hasNext()) {
            Entry entry = (Entry) playerIterator.next();
            Player checkPlayer = (Player) entry.getValue();
            if (found) {
                returnValue = checkPlayer;
                break;
            }
            if (checkPlayer.equals(player)) {
                found = true;
            }
        }
        return returnValue;
    }

    public int getPlayerCount() {
        return getPlayers().size();
    }

    public Iterator<Player> getPlayersIterator() {
        return getPlayers().values().iterator();
    }

    public int getNextAvailablePlayerId() {
        int nextAvailablePlayerId = 0;
        Iterator<Integer> iterator = players.keySet().iterator();
        while (iterator.hasNext()) {
            Integer playerId = iterator.next();
            if (playerId > nextAvailablePlayerId) {
                nextAvailablePlayerId = playerId;
            }
        }
        return nextAvailablePlayerId + 1;
    }
}
