package org.freerealm.player;

/**
 *
 * @author Deniz ARIKAN
 */
public class DiplomaticRelationUpdatedMessage extends DefaultMessage {

    private Player updatedWithPlayer;

    public Player getUpdatedWithPlayer() {
        return updatedWithPlayer;
    }

    public void setUpdatedWithPlayer(Player updatedWithPlayer) {
        this.updatedWithPlayer = updatedWithPlayer;
    }

}
