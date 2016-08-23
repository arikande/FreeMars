package org.freemars.model.objective;

import java.util.Iterator;
import org.freemars.ai.ExpeditionaryForcePlayer;
import org.freemars.model.FreeMarsModel;
import org.freemars.player.FreeMarsPlayer;
import org.freerealm.Realm;
import org.freerealm.player.Player;

/**
 *
 * @author Deniz ARIKAN
 */
public class DeclareAndDefendIndependenceObjective implements Objective {

    private FreeMarsModel freeMarsModel;

    public DeclareAndDefendIndependenceObjective() {
    }

    public DeclareAndDefendIndependenceObjective(FreeMarsModel freeMarsModel) {
        this.freeMarsModel = freeMarsModel;
    }

    public boolean isReached(Realm realm, Player player) {
        if (((FreeMarsPlayer) player).hasDeclaredIndependence()) {
            Iterator<Player> iterator = realm.getPlayerManager().getPlayersIterator();
            while (iterator.hasNext()) {
                Player otherPlayer = iterator.next();
                if (otherPlayer instanceof ExpeditionaryForcePlayer) {
                    if (otherPlayer.getUnitCount() == 0 && otherPlayer.getSettlementCount() == 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void setFreeMarsModel(FreeMarsModel freeMarsModel) {
        this.freeMarsModel = freeMarsModel;
    }
}
