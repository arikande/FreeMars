package org.freerealm.player;

import org.freerealm.settlement.SettlementBuildable;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class UnitCompletedMessage extends SettlementRelatedMessage {

    private Unit unit;
    private SettlementBuildable nextSettlementBuildable;
    private boolean contiuousProduction;

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public SettlementBuildable getNextProduction() {
        return nextSettlementBuildable;
    }

    public void setNextProduction(SettlementBuildable nextProduction) {
        this.nextSettlementBuildable = nextProduction;
    }

    public boolean isContiuousProduction() {
        return contiuousProduction;
    }

    public void setContiuousProduction(boolean contiuousProduction) {
        this.contiuousProduction = contiuousProduction;
    }

}
