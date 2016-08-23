package org.freemars.message;

import java.util.HashMap;
import org.freerealm.player.DefaultMessage;
import org.freerealm.unit.FreeRealmUnitType;

/**
 *
 * @author Deniz ARIKAN
 */
public class ExpeditionaryForceChangedMessage extends DefaultMessage {

    private HashMap<FreeRealmUnitType, Integer> updatedUnits;

    public HashMap<FreeRealmUnitType, Integer> getUpdatedUnits() {
        return updatedUnits;
    }

    public void setUpdatedUnits(HashMap<FreeRealmUnitType, Integer> updatedUnits) {
        this.updatedUnits = updatedUnits;
    }
}
