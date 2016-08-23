package org.freemars.diplomacy.gift;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.freemars.player.FreeMarsPlayer;
import org.freerealm.settlement.Settlement;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class OfferGiftDialogModel {

    public static final int CREDIT_GIFT_TYPE = 0;
    public static final int RESOURCE_GIFT_TYPE = 1;
    public static final int UNIT_GIFT_TYPE = 2;

    private final FreeMarsPlayer fromPlayer;
    private final FreeMarsPlayer toPlayer;
    private int giftType;
    private final List<Settlement> resourceGiftSettlements;
    private int selectedSettlementIndex;
    private int resourceId;
    private final List<Unit> giftableUnits;
    private int selectedUnitIndex;

    public OfferGiftDialogModel(FreeMarsPlayer fromPlayer, FreeMarsPlayer toPlayer) {
        this.fromPlayer = fromPlayer;
        this.toPlayer = toPlayer;
        resourceGiftSettlements = new ArrayList<Settlement>();
        giftableUnits = new ArrayList<Unit>();
    }

    public int getGiftType() {
        return giftType;
    }

    public void setGiftType(int giftType) {
        this.giftType = giftType;
    }

    public FreeMarsPlayer getFromPlayer() {
        return fromPlayer;
    }

    public FreeMarsPlayer getToPlayer() {
        return toPlayer;
    }

    public void addSettlement(Settlement settlement) {
        resourceGiftSettlements.add(settlement);
    }

    public Settlement getSelectedSettlement() {
        return resourceGiftSettlements.get(selectedSettlementIndex);
    }

    public Iterator<Settlement> getSettlementsIterator() {
        return resourceGiftSettlements.iterator();
    }

    public int getSelectedSettlementIndex() {
        return selectedSettlementIndex;
    }

    public void setSelectedSettlementIndex(int selectedSettlementIndex) {
        this.selectedSettlementIndex = selectedSettlementIndex;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public void addGiftableUnit(Unit unit) {
        giftableUnits.add(unit);
    }

    public Iterator<Unit> getGiftableUnitsIterator() {
        return giftableUnits.iterator();
    }

    public int getSelectedUnitIndex() {
        return selectedUnitIndex;
    }

    public void setSelectedUnitIndex(int selectedUnitIndex) {
        this.selectedUnitIndex = selectedUnitIndex;
    }

    public Unit getSelectedUnit() {
        return giftableUnits.get(selectedUnitIndex);
    }

}
