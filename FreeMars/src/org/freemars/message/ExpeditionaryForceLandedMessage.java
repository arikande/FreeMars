package org.freemars.message;

import java.util.HashMap;
import org.freerealm.player.DefaultMessage;
import org.freerealm.unit.FreeRealmUnitType;

/**
 *
 * @author Deniz ARIKAN
 */
public class ExpeditionaryForceLandedMessage extends DefaultMessage {

    private int attackWave;
    private HashMap<FreeRealmUnitType, Integer> landedUnits;

    public int getAttackWave() {
        return attackWave;
    }

    public void setAttackWave(int attackWave) {
        this.attackWave = attackWave;
    }

    public HashMap<FreeRealmUnitType, Integer> getLandedUnits() {
        return landedUnits;
    }

    public void setLandedUnits(HashMap<FreeRealmUnitType, Integer> landedUnits) {
        this.landedUnits = landedUnits;
    }

}
