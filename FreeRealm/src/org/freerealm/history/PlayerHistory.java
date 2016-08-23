package org.freerealm.history;

import java.util.Iterator;
import org.freerealm.player.Player;

/**
 *
 * @author Deniz ARIKAN
 */
public interface PlayerHistory {

    public void setPlayer(Player player);

    public Player getPlayer();

    public PlayerTurnHistory getTurnHistory(int turn);

    public void addTurnHistory(PlayerTurnHistory playerTurnHistory);

    public Iterator<PlayerTurnHistory> getPlayerTurnHistoryIterator();
}
