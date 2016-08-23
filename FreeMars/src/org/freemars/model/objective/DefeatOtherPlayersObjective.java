package org.freemars.model.objective;

import org.freerealm.Realm;
import org.freerealm.player.Player;

/**
 *
 * @author Deniz ARIKAN
 */
public class DefeatOtherPlayersObjective implements Objective {

    public boolean isReached(Realm realm, Player player) {
        return true;
    }
}
