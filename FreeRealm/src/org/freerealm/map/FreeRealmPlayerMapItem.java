package org.freerealm.map;

import org.freerealm.Realm;
import org.freerealm.player.Player;

/**
 * 
 * @author Deniz ARIKAN
 */
public class FreeRealmPlayerMapItem extends FreeRealmMapItem implements PlayerMapItem {

    private Player player;

    public FreeRealmPlayerMapItem(Realm realm) {
        super(realm, null);
    }

    protected FreeRealmPlayerMapItem(Realm realm, Coordinate coordinate, Player player) {
        super(realm, coordinate);
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
