package org.freemars.message;

import org.freerealm.player.DefaultMessage;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class UnitRelocationCompletedMessage extends DefaultMessage {

    private Unit unit;

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }
}
