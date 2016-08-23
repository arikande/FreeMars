package org.freemars.mission;

import org.freerealm.player.mission.Reward;

/**
 *
 * @author Deniz ARIKAN
 */
public class WealthReward implements Reward {

    private static final String NAME = "wealthReward";
    private int amount;

    public String getName() {
        return NAME;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
