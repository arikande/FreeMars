package org.freemars.earth.message;

import java.util.List;
import org.freerealm.player.DefaultMessage;
import org.freerealm.unit.Unit;

/**
 * @author Deniz ARIKAN
 */
public class UnitsSeizedMessage extends DefaultMessage {

    private List<Unit> seizedUnits;

    @Override
    public String getText() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Our units have been seized by Earth government.");
        return stringBuilder.toString();
    }

    public void setSeizedUnits(List<Unit> seizedUnits) {
        this.seizedUnits = seizedUnits;
    }

    public List<Unit> getSeizedUnits() {
        return seizedUnits;
    }
}
