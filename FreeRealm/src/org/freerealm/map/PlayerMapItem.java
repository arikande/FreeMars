package org.freerealm.map;

import org.freerealm.player.Player;

/**
 * 
 * @author Deniz ARIKAN
 */
public interface PlayerMapItem extends MapItem {

    public Player getPlayer();

    public void setPlayer(Player player);
}
