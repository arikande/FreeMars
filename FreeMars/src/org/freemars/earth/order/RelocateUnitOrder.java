package org.freemars.earth.order;

import java.util.Iterator;

import org.freemars.controller.FreeMarsController;
import org.freemars.earth.EarthFlightProperty;
import org.freemars.earth.Location;
import org.freemars.earth.ModifyHydrogenConsumption;
import org.freemars.earth.command.RelocateUnitCommand;
import org.freemars.message.UnitRelocationCompletedMessage;
import org.freemars.player.FreeMarsPlayer;
import org.freerealm.Realm;
import org.freerealm.Utility;
import org.freerealm.command.UnitActivateCommand;
import org.freerealm.command.UnitSuspendCommand;
import org.freerealm.executor.order.AbstractOrder;
import org.freerealm.resource.Resource;
import org.freerealm.settlement.Settlement;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class RelocateUnitOrder extends AbstractOrder {

    private static final String NAME = "RelocateUnitOrder";
    private FreeMarsController freeMarsController;
    private Location source;
    private Location destination;
    private Settlement launchFromColony;
    private Settlement landOnColony;

    public RelocateUnitOrder(Realm realm) {
        super(realm);
    }

    public void setFreeMarsController(FreeMarsController freeMarsController) {
        this.freeMarsController = freeMarsController;
    }

    public Location getSource() {
        return source;
    }

    public void setSource(Location source) {
        this.source = source;
    }

    public Location getDestination() {
        return destination;
    }

    public void setDestination(Location destination) {
        this.destination = destination;
    }

    public int getTimeSpent() {
        return getRealm().getNumberOfTurns() - getTurnGiven();
    }

    public int getRemainingTurns() {
        return getTimeNeeded(source, destination) - getTimeSpent();
    }

    @Override
    public boolean isExecutable() {
        return true;
    }

    @Override
    public void execute() {
        EarthFlightProperty earthFlight = (EarthFlightProperty) getUnit().getType().getProperty(EarthFlightProperty.NAME);
        if (earthFlight == null) {
            setComplete(true);
            return;
        }
        if (getLaunchFromColony() != null) {
            Iterator<Resource> consumedResourcesIterator = earthFlight.getConsumedResourcesIterator();
            while (consumedResourcesIterator.hasNext()) {
                Resource resource = consumedResourcesIterator.next();
                int requiredResourceQuantity = earthFlight.getResourceConsumption(resource);
                ModifyHydrogenConsumption modifyHydrogenConsumption = (ModifyHydrogenConsumption) getUnit().getPlayer().getProperty("ModifyHydrogenConsumption");
                if (modifyHydrogenConsumption != null) {
                    requiredResourceQuantity = (int) Utility.modifyByPercent(requiredResourceQuantity, modifyHydrogenConsumption.getModifier());
                }
                if (getLaunchFromColony().getResourceQuantity(resource) < requiredResourceQuantity) {
                    setComplete(true);
                    return;
                }
            }
            consumedResourcesIterator = earthFlight.getConsumedResourcesIterator();
            while (consumedResourcesIterator.hasNext()) {
                Resource resource = consumedResourcesIterator.next();
                int requiredResourceQuantity = earthFlight.getResourceConsumption(resource);
                ModifyHydrogenConsumption modifyHydrogenConsumption = (ModifyHydrogenConsumption) getUnit().getPlayer().getProperty("ModifyHydrogenConsumption");
                if (modifyHydrogenConsumption != null) {
                    requiredResourceQuantity = (int) Utility.modifyByPercent(requiredResourceQuantity, modifyHydrogenConsumption.getModifier());
                }
                getLaunchFromColony().setResourceQuantity(resource, getLaunchFromColony().getResourceQuantity(resource) - requiredResourceQuantity);
            }
            setLaunchFromColony(null);
        }
        if (getUnit().getStatus() != Unit.UNIT_SUSPENDED) {
            getExecutor().execute(new UnitSuspendCommand(getRealm(), getUnit().getPlayer(), getUnit()));
        }
        freeMarsController.getFreeMarsModel().getEarth().addUnitLocation(getUnit(), getIntermediateLocation(source, destination));
        if (getTimeSpent() >= getTimeNeeded(source, destination)) {
            setComplete(true);
            if (landOnColony != null) {
                freeMarsController.getFreeMarsModel().getEarth().removeUnitLocation(getUnit());
                getExecutor().execute(new UnitActivateCommand(freeMarsController.getFreeMarsModel().getRealm(), getUnit(), landOnColony.getCoordinate()));
            } else {
                if (destination.equals(Location.EARTH)) {
                    UnitRelocationCompletedMessage message = new UnitRelocationCompletedMessage();
                    message.setSubject("Shuttle");
                    message.setText(getUnit().getName() + " has arrived at Earth");
                    message.setTurnSent(getRealm().getNumberOfTurns());
                    message.setUnit(getUnit());
                    getUnit().getPlayer().addMessage(message);
                }
                getExecutor().execute(new RelocateUnitCommand(freeMarsController.getFreeMarsModel().getEarth(), getUnit(), destination));
            }
        }
    }

    @Override
    public String getName() {
        return NAME;
    }

    public Settlement getLandOnColony() {
        return landOnColony;
    }

    public void setLandOnColony(Settlement landOnColony) {
        this.landOnColony = landOnColony;
    }

    public Settlement getLaunchFromColony() {
        return launchFromColony;
    }

    public void setLaunchFromColony(Settlement launchFromColony) {
        this.launchFromColony = launchFromColony;
    }

    private Location getIntermediateLocation(Location source, Location destination) {
        if (source.equals(Location.EARTH) && (destination.equals(Location.MARS_ORBIT) || destination.equals(Location.MARS))) {
            return Location.TRAVELING_TO_MARS;
        }
        if ((source.equals(Location.MARS) || source.equals(Location.MARS_ORBIT)) && destination.equals(Location.EARTH)) {
            return Location.TRAVELING_TO_EARTH;
        }
        if (source.equals(Location.MARS) && destination.equals(Location.MARS_ORBIT)) {
            return Location.MARS;
        }
        if (source.equals(Location.MARS_ORBIT) && destination.equals(Location.MARS)) {
            return Location.MARS;
        }
        return Location.TRAVELING_TO_EARTH;
    }

    private int getTimeNeeded(Location source, Location destination) {
        int timeNeeded = 0;
        EarthFlightProperty earthFlight = (EarthFlightProperty) getUnit().getType().getProperty(EarthFlightProperty.NAME);
        FreeMarsPlayer freeMarsPlayer = (FreeMarsPlayer) getUnit().getPlayer();
        if (source.equals(Location.MARS)) {
            if (destination.equals(Location.MARS_ORBIT)) {
                timeNeeded = 1;
            } else if (destination.equals(Location.EARTH)) {
                timeNeeded = earthFlight.getEarthMarsTravelTime() + freeMarsPlayer.getEarthFlightTimeModifier() + 1;
            }
        } else if (source.equals(Location.MARS_ORBIT)) {
            if (destination.equals(Location.MARS)) {
                timeNeeded = 1;
            } else if (destination.equals(Location.EARTH)) {
                timeNeeded = earthFlight.getEarthMarsTravelTime() + freeMarsPlayer.getEarthFlightTimeModifier();
            }
        } else if (source.equals(Location.EARTH)) {
            if (destination.equals(Location.MARS_ORBIT)) {
                timeNeeded = earthFlight.getEarthMarsTravelTime() + freeMarsPlayer.getEarthFlightTimeModifier();
            } else if (destination.equals(Location.MARS)) {
                timeNeeded = earthFlight.getEarthMarsTravelTime() + freeMarsPlayer.getEarthFlightTimeModifier() + 1;
            }
        }
        if (timeNeeded <= 0) {
            timeNeeded = 1;
        }
        return timeNeeded;
    }
}
