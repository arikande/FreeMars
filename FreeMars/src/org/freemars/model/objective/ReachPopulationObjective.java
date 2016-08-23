package org.freemars.model.objective;

import org.freerealm.Realm;
import org.freerealm.player.Player;

/**
 *
 * @author Deniz ARIKAN
 */
public class ReachPopulationObjective implements Objective {

    private final int targetPopulation;

    public ReachPopulationObjective(int targetPopulation) {
        this.targetPopulation = targetPopulation;
    }

    public boolean isReached(Realm realm, Player player) {
        if (player.getPopulation() >= targetPopulation) {
            return true;
        } else {
            return false;
        }
    }

    public int getTargetPopulation() {
        return targetPopulation;
    }
}
