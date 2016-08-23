package org.freemars.message;

import org.freemars.ai.ExpeditionaryForcePlayer;
import org.freerealm.player.DefaultMessage;

/**
 *
 * @author Deniz ARIKAN
 */
public class ExpeditionaryForceDefeatedMessage extends DefaultMessage {

    private ExpeditionaryForcePlayer expeditionaryForcePlayer;

    public ExpeditionaryForcePlayer getExpeditionaryForcePlayer() {
        return expeditionaryForcePlayer;
    }

    public void setExpeditionaryForcePlayer(ExpeditionaryForcePlayer expeditionaryForcePlayer) {
        this.expeditionaryForcePlayer = expeditionaryForcePlayer;
    }
}
