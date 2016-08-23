package org.freemars.message;

import org.freerealm.player.DefaultMessage;
import org.freerealm.player.Player;
import org.freerealm.settlement.Settlement;

/**
 *
 * @author Deniz ARIKAN
 */
public class SettlementCapturedMessage extends DefaultMessage {

    private Settlement settlement;
    private Player newOwner;
    private Player previousOwner;

    public Settlement getSettlement() {
        return settlement;
    }

    public void setSettlement(Settlement settlement) {
        this.settlement = settlement;
    }

    public Player getNewOwner() {
        return newOwner;
    }

    public void setNewOwner(Player newOwner) {
        this.newOwner = newOwner;
    }

    public Player getPreviousOwner() {
        return previousOwner;
    }

    public void setPreviousOwner(Player previousOwner) {
        this.previousOwner = previousOwner;
    }
}
