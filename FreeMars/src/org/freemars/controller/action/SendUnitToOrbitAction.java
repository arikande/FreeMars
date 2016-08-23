package org.freemars.controller.action;

import java.awt.event.ActionEvent;
import java.util.Iterator;

import javax.swing.AbstractAction;

import org.freemars.controller.FreeMarsController;
import org.freemars.controller.viewcommand.PlaySoundCommand;
import org.freemars.earth.EarthFlightProperty;
import org.freemars.earth.Location;
import org.freemars.earth.ModifyHydrogenConsumption;
import org.freemars.earth.order.RelocateUnitOrder;
import org.freemars.player.FreeMarsPlayer;
import org.freemars.ui.util.FreeMarsOptionPane;
import org.freerealm.Utility;
import org.freerealm.command.SetActiveUnitCommand;
import org.freerealm.command.UnitOrderAddCommand;
import org.freerealm.player.Player;
import org.freerealm.resource.Resource;
import org.freerealm.settlement.Settlement;
import org.freerealm.tile.Tile;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class SendUnitToOrbitAction extends AbstractAction {

    private final FreeMarsController freeMarsController;
    private final Unit unit;

    public SendUnitToOrbitAction(FreeMarsController freeMarsController, Unit unit) {
        super("Go to Orbit", null);
        this.freeMarsController = freeMarsController;
        this.unit = unit;
    }

    public void actionPerformed(ActionEvent e) {
        Unit unitToOrder = unit != null ? unit : freeMarsController.getFreeMarsModel().getActivePlayer().getActiveUnit();
        if (unitToOrder.getCurrentOrder() == null) {
            FreeMarsPlayer player = (FreeMarsPlayer) unitToOrder.getPlayer();
            if (player.hasDeclaredIndependence()) {
                FreeMarsOptionPane.showMessageDialog(freeMarsController.getCurrentFrame(), "We can not send spaceships to Earth after declaration of independence", "Rejected");
            } else {
                EarthFlightProperty earthFlight = (EarthFlightProperty) unitToOrder.getType().getProperty(EarthFlightProperty.NAME);
                if (earthFlight != null) {
                    Tile tile = freeMarsController.getFreeMarsModel().getTile(unitToOrder.getCoordinate());
                    Settlement colony = tile.getSettlement();
                    ModifyHydrogenConsumption modifyHydrogenConsumption = (ModifyHydrogenConsumption) player.getProperty("ModifyHydrogenConsumption");
                    Iterator<Resource> consumedResourcesIterator = earthFlight.getConsumedResourcesIterator();
                    while (consumedResourcesIterator.hasNext()) {
                        Resource resource = consumedResourcesIterator.next();
                        int requiredResourceQuantity = earthFlight.getResourceConsumption(resource);
                        if (modifyHydrogenConsumption != null) {
                            requiredResourceQuantity = (int) Utility.modifyByPercent(requiredResourceQuantity, modifyHydrogenConsumption.getModifier());
                        }
                        int resourceQuantity = colony.getResourceQuantity(resource);
                        if (resourceQuantity < requiredResourceQuantity) {
                            StringBuilder message = new StringBuilder();
                            message.append("Not enough ");
                            message.append(resource.getName());
                            message.append(". Need ");
                            message.append(requiredResourceQuantity);
                            message.append(" ");
                            message.append(resource.getName());
                            message.append(" to launch.");
                            FreeMarsOptionPane.showMessageDialog(freeMarsController.getCurrentFrame(), message);
                            return;
                        }

                    }
                    RelocateUnitOrder relocateUnitOrder = new RelocateUnitOrder(freeMarsController.getFreeMarsModel().getRealm());
                    relocateUnitOrder.setFreeMarsController(freeMarsController);
                    relocateUnitOrder.setUnit(unitToOrder);
                    relocateUnitOrder.setSource(Location.MARS);
                    relocateUnitOrder.setDestination(Location.MARS_ORBIT);
                    relocateUnitOrder.setLaunchFromColony(colony);
                    freeMarsController.execute(new UnitOrderAddCommand(freeMarsController.getFreeMarsModel().getRealm(), unitToOrder, relocateUnitOrder));
                    Unit nextUnit = org.freemars.util.Utility.getNextPlayableUnit(player, unitToOrder);
                    freeMarsController.execute(new SetActiveUnitCommand(player, nextUnit));
                    boolean spaceshipLaunchSound = Boolean.valueOf(freeMarsController.getFreeMarsModel().getFreeMarsPreferences().getProperty("spaceship_launch_sound"));
                    if (spaceshipLaunchSound) {
                        freeMarsController.executeViewCommandImmediately(new PlaySoundCommand("sound/spaceship_launch.wav"));
                    }
                    StringBuilder launchMessage = new StringBuilder();
                    launchMessage.append(unitToOrder.getType().getName());
                    launchMessage.append(" is sent to Mars orbit.");
                    launchMessage.append(System.getProperty("line.separator"));
                    consumedResourcesIterator = earthFlight.getConsumedResourcesIterator();
                    while (consumedResourcesIterator.hasNext()) {
                        Resource resource = consumedResourcesIterator.next();
                        int requiredResourceQuantity = earthFlight.getResourceConsumption(resource);
                        if (modifyHydrogenConsumption != null) {
                            requiredResourceQuantity = (int) Utility.modifyByPercent(requiredResourceQuantity, modifyHydrogenConsumption.getModifier());
                        }
                        launchMessage.append(requiredResourceQuantity);
                        launchMessage.append(" ");
                        launchMessage.append(resource.getName());
                        launchMessage.append(" used.");
                        launchMessage.append(System.getProperty("line.separator"));
                    }
                    FreeMarsOptionPane.showMessageDialog(freeMarsController.getCurrentFrame(), launchMessage);
                }
            }
        }
    }

    public boolean checkEnabled() {
        Player activePlayer = freeMarsController.getFreeMarsModel().getActivePlayer();
        if (activePlayer == null) {
            return false;
        }
        Unit unitToOrder = unit != null ? unit : freeMarsController.getFreeMarsModel().getActivePlayer().getActiveUnit();
        if (unitToOrder == null) {
            return false;
        }
        EarthFlightProperty earthFlight = (EarthFlightProperty) unitToOrder.getType().getProperty(EarthFlightProperty.NAME);
        return earthFlight != null;
    }
}
