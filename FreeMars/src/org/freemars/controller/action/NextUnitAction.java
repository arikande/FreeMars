package org.freemars.controller.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.freemars.controller.FreeMarsController;
import org.freemars.util.Utility;
import org.freerealm.command.SetActiveUnitCommand;
import org.freerealm.player.Player;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class NextUnitAction extends AbstractAction {

    private final FreeMarsController freeMarsController;
    private final Unit unit;

    public NextUnitAction(FreeMarsController freeMarsController, Unit unit) {
        super("Next unit");
        this.freeMarsController = freeMarsController;
        this.unit = unit;
    }

    public void actionPerformed(ActionEvent e) {
        Player activePlayer = freeMarsController.getFreeMarsModel().getActivePlayer();
        Unit activeUnit = activePlayer.getActiveUnit();
        if (activeUnit != null) {
            Unit nextUnit = Utility.getNextPlayableUnit(activePlayer, activeUnit);
            freeMarsController.execute(new SetActiveUnitCommand(activePlayer, nextUnit));
        }
    }

    public boolean checkEnabled() {
        Player activePlayer = freeMarsController.getFreeMarsModel().getActivePlayer();
        if (activePlayer == null) {
            return false;
        }
        Unit unitToOrder = unit != null ? unit : freeMarsController.getFreeMarsModel().getActivePlayer().getActiveUnit();
        return unitToOrder != null;
    }
}
