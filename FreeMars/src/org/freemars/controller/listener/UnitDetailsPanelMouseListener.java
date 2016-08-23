package org.freemars.controller.listener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import org.freemars.controller.FreeMarsController;
import org.freemars.controller.viewcommand.SetCenteredCoordinateCommand;
import org.freerealm.player.Player;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class UnitDetailsPanelMouseListener extends MouseAdapter {

    private final FreeMarsController freeMarsController;

    public UnitDetailsPanelMouseListener(FreeMarsController freeMarsController) {
        this.freeMarsController = freeMarsController;
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        Player activePlayer = freeMarsController.getFreeMarsModel().getActivePlayer();
        Unit activeUnit = activePlayer.getActiveUnit();
        if (activeUnit != null) {
            freeMarsController.executeViewCommand(new SetCenteredCoordinateCommand(freeMarsController, activeUnit.getCoordinate()));
        }
    }
}
