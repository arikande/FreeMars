package org.freerealm.history;

import java.util.Iterator;
import org.freerealm.player.Player;

/**
 *
 * @author Deniz ARIKAN
 */
public interface History {

    public PlayerHistory getPlayerHistory(Player player);

    public PlayerTurnHistory getPlayerTurnHistory(Player player, int turn);

    public void addPlayerHistory(Player player, PlayerHistory playerHistory);

    public void addPlayerTurnHistory(Player player, PlayerTurnHistory playerTurnHistory);

    public Iterator<Player> getPlayersIterator();
}
