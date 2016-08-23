package org.freemars.controller.action;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.freemars.controller.FreeMarsController;
import org.freemars.controller.viewcommand.PlaySoundCommand;
import org.freerealm.executor.order.UnloadAllCargoOrder;
import org.freerealm.player.Player;
import org.freerealm.property.ContainerProperty;
import org.freerealm.settlement.Settlement;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class UnloadAllCargoAction extends AbstractAction {

    private final FreeMarsController freeMarsController;
    private final Unit unit;

    public UnloadAllCargoAction(FreeMarsController freeMarsController, Unit unit) {
        super("Unload all cargo");
        this.freeMarsController = freeMarsController;
        this.unit = unit;
    }

    public void actionPerformed(ActionEvent e) {
        Unit unitToOrder = unit != null ? unit : freeMarsController.getFreeMarsModel().getActivePlayer().getActiveUnit();
        if (unitToOrder != null) {
            boolean resourceTransferSound = Boolean.valueOf(freeMarsController.getFreeMarsModel().getFreeMarsPreferences().getProperty("resource_transfer_sound"));
            if (resourceTransferSound) {
                freeMarsController.executeViewCommandImmediately(new PlaySoundCommand("sound/resource_transfer.wav"));
            }
            UnloadAllCargoOrder unloadAllCargoOrder = new UnloadAllCargoOrder(freeMarsController.getFreeMarsModel().getRealm());
            unloadAllCargoOrder.setExecutor(freeMarsController);
            unloadAllCargoOrder.setRealm(freeMarsController.getFreeMarsModel().getRealm());
            unloadAllCargoOrder.setUnit(unitToOrder);
            unloadAllCargoOrder.execute();
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
        if (unitToOrder.getCurrentOrder() != null) {
            return false;
        }
        if (unitToOrder.getCoordinate() != null) {
            Settlement settlement = freeMarsController.getFreeMarsModel().getTile(unitToOrder.getCoordinate()).getSettlement();
            if (settlement != null) {
                ContainerProperty containerProperty = (ContainerProperty) unitToOrder.getType().getProperty("ContainerProperty");
                if (containerProperty != null && unitToOrder.getTotalResourceWeight() > 0) {
                    return true;
                }
            }
        }
        return false;
    }
}
