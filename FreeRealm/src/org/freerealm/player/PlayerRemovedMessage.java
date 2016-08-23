package org.freerealm.player;

/**
 *
 * @author Deniz ARIKAN
 */
public class PlayerRemovedMessage extends DefaultMessage {

    private Player player;

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
