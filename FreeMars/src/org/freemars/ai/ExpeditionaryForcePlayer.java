package org.freemars.ai;

import org.apache.log4j.Logger;
import org.freemars.player.FreeMarsPlayer;
import org.freerealm.Realm;
import org.freerealm.player.Player;

/**
 *
 * @author Deniz ARIKAN
 */
public class ExpeditionaryForcePlayer extends FreeMarsPlayer {

    public static final int BASE_INFANTRY_COUNT = 8;
    public static final int BASE_MECH_COUNT = 10;
    public static final int BASE_LGT_COUNT = 6;

    public static final int MAX_INFANTRY_COUNT = 30;
    public static final int MAX_MECH_COUNT = 28;
    public static final int MAX_LGT_COUNT = 22;

    private int targetPlayerId;
    private ExpeditionaryForceDecisionModel expeditionaryForceDecisionModel;
    private int earthToMarsFlightTurns;
    private int maximumAttackWaves = 3;
    private int remainingAttackWaves = getMaximumAttackWaves();

    public ExpeditionaryForcePlayer(Realm realm) {
        super(realm);
    }

    public void play() {
        try {
            if (getStatus() == Player.STATUS_ACTIVE) {
                getDecisionModel().play();
                String logInfo = "Expeditionary force player with id " + getId() + " and name \"" + getName() + "\" has played turn " + getRealm().getNumberOfTurns() + ".";
                Logger.getLogger(ExpeditionaryForcePlayer.class).info(logInfo);
            }
        } catch (Exception e) {
        }
    }

    public boolean hasLanded() {
        return getRemainingAttackWaves() < getMaximumAttackWaves();
    }

    public int getTargetPlayerId() {
        return targetPlayerId;
    }

    public void setTargetPlayerId(int targetPlayerId) {
        this.targetPlayerId = targetPlayerId;
    }

    public ExpeditionaryForceDecisionModel getDecisionModel() {
        return expeditionaryForceDecisionModel;
    }

    public void setDecisionModel(ExpeditionaryForceDecisionModel expeditionaryForceDecisionModel) {
        this.expeditionaryForceDecisionModel = expeditionaryForceDecisionModel;
    }

    public int getEarthToMarsFlightTurns() {
        return earthToMarsFlightTurns;
    }

    public void setEarthToMarsFlightTurns(int earthToMarsFlightTurns) {
        this.earthToMarsFlightTurns = earthToMarsFlightTurns;
    }

    public int getMaximumAttackWaves() {
        return maximumAttackWaves;
    }

    public void setMaximumAttackWaves(int maximumAttackWaves) {
        this.maximumAttackWaves = maximumAttackWaves;
    }

    public int getRemainingAttackWaves() {
        return remainingAttackWaves;
    }

    public void setRemainingAttackWaves(int remainingAttackWaves) {
        this.remainingAttackWaves = remainingAttackWaves;
    }

}
