package org.freemars.model.objective;

import org.freerealm.Realm;
import org.freerealm.player.Player;

/**
 *
 * @author Deniz ARIKAN
 */
public interface Objective {

    public boolean isReached(Realm realm, Player player);
}
