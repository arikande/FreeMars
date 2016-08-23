package org.freemars.controller.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import org.freemars.controller.FreeMarsController;
import org.freerealm.Realm;
import org.freerealm.command.RemoveUnitCommand;
import org.freerealm.command.SetActiveUnitCommand;
import org.freerealm.player.Player;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class DisbandUnitAction extends AbstractAction {

    private final FreeMarsController freeMarsController;
    private final Unit unit;

    public DisbandUnitAction(FreeMarsController freeMarsController, Unit unit) {
        super("Disband unit");
        this.freeMarsController = freeMarsController;
        this.unit = unit;
    }

    public void actionPerformed(ActionEvent e) {
        Object[] options = {"Yes, disband", "No, thanks"};
        int value = JOptionPane.showOptionDialog(freeMarsController.getCurrentFrame(),
                "Really disband unit?",
                "Disband unit",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[1]);
        Realm realm = freeMarsController.getFreeMarsModel().getRealm();
        if (value == JOptionPane.YES_OPTION) {
            if (unit == null) {
                Player activePlayer = freeMarsController.getFreeMarsModel().getActivePlayer();
                Unit activeUnit = activePlayer.getActiveUnit();
                if (activeUnit != null) {
                    Unit nextUnit = org.freemars.util.Utility.getNextPlayableUnit(activePlayer, activeUnit);
                    freeMarsController.execute(new SetActiveUnitCommand(activePlayer, nextUnit));
                    freeMarsController.execute((new RemoveUnitCommand(realm, activePlayer, activeUnit)));
                }
            } else {
                freeMarsController.execute((new RemoveUnitCommand(realm, unit.getPlayer(), unit)));
            }
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
