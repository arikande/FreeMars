package org.freemars.model.objective;

import org.freerealm.Realm;
import org.freerealm.player.Player;

/**
 *
 * @author Deniz ARIKAN
 */
public class ReachTreasuryObjective implements Objective {

    private final int targetTreasury;

    public ReachTreasuryObjective(int targetTreasury) {
        this.targetTreasury = targetTreasury;
    }

    public boolean isReached(Realm realm, Player player) {
        if (player.getWealth() >= targetTreasury) {
            return true;
        } else {
            return false;
        }
    }

    public int getTargetTreasury() {
        return targetTreasury;
    }
}
