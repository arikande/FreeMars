package org.freemars.controller.action;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.freemars.controller.FreeMarsController;
import org.freerealm.executor.order.UnloadAllPopulationOrder;
import org.freerealm.player.Player;
import org.freerealm.property.ContainerProperty;
import org.freerealm.settlement.Settlement;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class UnloadAllColonistsAction extends AbstractAction {

    private final FreeMarsController freeMarsController;
    private final Unit unit;

    public UnloadAllColonistsAction(FreeMarsController freeMarsController, Unit unit) {
        super("Unload all colonists");
        this.freeMarsController = freeMarsController;
        this.unit = unit;
    }

    public void actionPerformed(ActionEvent e) {
        Unit unitToOrder = unit != null ? unit : freeMarsController.getFreeMarsModel().getActivePlayer().getActiveUnit();
        if (unitToOrder != null) {
            UnloadAllPopulationOrder unloadAllPopulationOrder = new UnloadAllPopulationOrder(freeMarsController.getFreeMarsModel().getRealm());
            unloadAllPopulationOrder.setExecutor(freeMarsController);
            unloadAllPopulationOrder.setRealm(freeMarsController.getFreeMarsModel().getRealm());
            unloadAllPopulationOrder.setUnit(unitToOrder);
            unloadAllPopulationOrder.execute();
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
                if (containerProperty != null && unitToOrder.getContainerManager().getContainedPopulation() > 0) {
                    return true;
                }
            }
        }
        return false;
    }
}
