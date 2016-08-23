package org.freemars.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.freemars.ai.ExpeditionaryForcePlayer;
import org.freemars.model.FreeMarsModel;
import org.freerealm.player.Player;
import org.freerealm.unit.FreeRealmUnitType;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class FreeMarsPlayerUtilities {

    public static List<Player> getDefeatedPlayers(FreeMarsModel freeMarsModel) {
        ArrayList<Player> defeatedPlayers = new ArrayList<Player>();
        Iterator<Player> playersIterator = freeMarsModel.getPlayersIterator();
        while (playersIterator.hasNext()) {
            Player player = playersIterator.next();
            if (player instanceof ExpeditionaryForcePlayer) {
                if (isExpeditionaryForcePlayerDefeated(freeMarsModel, (ExpeditionaryForcePlayer) player)) {
                    defeatedPlayers.add(player);
                }
            } else {
                if (isPlayerDefeated(freeMarsModel, player)) {
                    defeatedPlayers.add(player);
                }
            }
        }
        return defeatedPlayers;
    }

    private static boolean isExpeditionaryForcePlayerDefeated(FreeMarsModel freeMarsModel, ExpeditionaryForcePlayer expeditionaryForcePlayer) {
        if (!isPlayerDefeated(freeMarsModel, expeditionaryForcePlayer)) {
            return false;
        }
        return expeditionaryForcePlayer.getUnitCount() <= 0;
    }

    private static boolean isPlayerDefeated(FreeMarsModel freeMarsModel, Player player) {
        if (player.getStatus() != Player.STATUS_ACTIVE) {
            return false;
        }
        if (player.getSettlementCount() > 0) {
            return false;
        }
        return getActiveColonizerCount(freeMarsModel, player) <= 0;
    }

    private static int getActiveColonizerCount(FreeMarsModel freeMarsModel, Player player) {
        int colonizerCount = 0;
        FreeRealmUnitType colonizerUnitType = freeMarsModel.getRealm().getUnitTypeManager().getUnitType("Colonizer");
        Iterator<Unit> iterator = player.getUnitsIterator();
        while (iterator.hasNext()) {
            Unit unit = iterator.next();
            if (unit.getStatus() == Unit.UNIT_ACTIVE && unit.getCoordinate() != null && unit.getType().equals(colonizerUnitType)) {
                colonizerCount = colonizerCount + 1;
            }
        }
        return colonizerCount;
    }
}
