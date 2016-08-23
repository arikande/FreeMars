package org.freerealm.diplomacy;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import org.freerealm.player.Player;

/**
 *
 * @author Deniz ARIKAN
 */
public class Diplomacy {

    private final Set<PlayerRelation> playerRelations;

    public Diplomacy() {
        playerRelations = new TreeSet<PlayerRelation>();
    }

    public void addPlayerRelation(PlayerRelation playerRelation) {
        playerRelations.add(playerRelation);
    }

    public PlayerRelation getPlayerRelation(Player targetPlayer) {
        Iterator<PlayerRelation> iterator = playerRelations.iterator();
        while (iterator.hasNext()) {
            PlayerRelation playerRelation = iterator.next();
            if (playerRelation.getTargetPlayerId() == targetPlayer.getId()) {
                return playerRelation;
            }
        }
        return null;
    }

    public Iterator<PlayerRelation> getPlayerRelationsIterator() {
        return playerRelations.iterator();
    }
}
