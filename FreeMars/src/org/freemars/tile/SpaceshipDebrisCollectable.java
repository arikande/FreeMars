package org.freemars.tile;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.commandexecutor.Executor;
import org.freerealm.Realm;
import org.freerealm.command.ResourceAddCommand;
import org.freerealm.resource.Resource;
import org.freerealm.settlement.Settlement;
import org.freerealm.tile.Collectable;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class SpaceshipDebrisCollectable implements Collectable {

    private static final String NAME = "spaceshipDebrisCollectable";
    private Executor executor;
    private int resourceId;
    private int amount;
    private Unit collectingUnit;
    private Settlement deliveredSettlement;

    public String getName() {
        return NAME;
    }

    public void collected(Realm realm, Unit unit) {
        String log;
        deliveredSettlement = findSettlementToDeliverDebris(realm, unit);
        if (deliveredSettlement != null) {
            realm.getTile(unit.getCoordinate()).setCollectable(null);
            Resource resource = realm.getResourceManager().getResource(getResourceId());
            executor.execute(new ResourceAddCommand(deliveredSettlement, resource, getAmount()));
            collectingUnit = unit;
            log = "Spaceship debris collected by " + unit.getName() + ". " + getAmount() + " " + resource.getName() + " delivered to " + deliveredSettlement.getName() + ".";
        } else {
            log = "Spaceship debris cannot be delivered. There is no colony near " + unit.getName() + ".";
        }
        Logger.getLogger(SpaceshipDebrisCollectable.class).info(log);
    }

	public void setExecutor(Executor executor) {
        this.executor = executor;
    }

    public Settlement getDeliveredSettlement() {
        return deliveredSettlement;
    }

    public Unit getCollectingUnit() {
        return collectingUnit;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    private Settlement findSettlementToDeliverDebris(Realm realm, Unit unit) {
        Settlement settlementToDeliverDebris = null;
        ArrayList<Settlement> settlementsNearCoordinate = realm.getSettlementsNearCoordinate(unit.getCoordinate(), 0, 10, unit.getPlayer());
        if (!settlementsNearCoordinate.isEmpty()) {
            settlementToDeliverDebris = settlementsNearCoordinate.get(0);
        } else if (unit.getPlayer().getSettlementCount() > 0) {
            Iterator<Settlement> iterator = unit.getPlayer().getSettlementsIterator();
            while (iterator.hasNext()) {
                return iterator.next();
            }
        }
        return settlementToDeliverDebris;
    }

}
