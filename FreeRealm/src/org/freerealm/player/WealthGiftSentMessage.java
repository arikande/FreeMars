package org.freerealm.player;

/**
 *
 * @author Deniz ARIKAN
 */
public class WealthGiftSentMessage extends DefaultMessage {

    private Player fromPlayer;
    private Player toPlayer;
    private int amount;

    public Player getFromPlayer() {
        return fromPlayer;
    }

    public void setFromPlayer(Player fromPlayer) {
        this.fromPlayer = fromPlayer;
    }

    public Player getToPlayer() {
        return toPlayer;
    }

    public void setToPlayer(Player toPlayer) {
        this.toPlayer = toPlayer;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

}
