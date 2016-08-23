package org.freemars.controller.action;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.freemars.controller.FreeMarsController;
import org.freemars.controller.viewcommand.SetCenteredCoordinateCommand;
import org.freerealm.player.Player;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class DisplayActiveUnitAction extends AbstractAction {

    private final FreeMarsController freeMarsController;

    public DisplayActiveUnitAction(FreeMarsController freeMarsController) {
        super("Display active unit", null);
        this.freeMarsController = freeMarsController;
    }

    public void actionPerformed(ActionEvent e) {
        Player player = freeMarsController.getFreeMarsModel().getActivePlayer();
        if (player != null) {
            Unit activeUnit = player.getActiveUnit();
            if (activeUnit != null && activeUnit.getCoordinate() != null) {
                freeMarsController.executeViewCommand(new SetCenteredCoordinateCommand(freeMarsController, activeUnit.getCoordinate()));
            }
        }
    }
}
