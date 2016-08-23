package org.freemars.colony.manager;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import org.freemars.colony.FreeMarsColony;
import org.freemars.controller.FreeMarsController;
import org.freemars.controller.action.DisplayColonyDialogAction;
import org.freemars.controller.viewcommand.SetCenteredCoordinateCommand;

/**
 *
 * @author Deniz ARIKAN
 */
public class ColoniesTableMouseAdapter extends MouseAdapter {

    private final FreeMarsController freeMarsController;
    private final ColonyManagerDialog colonyManagerDialog;

    public ColoniesTableMouseAdapter(FreeMarsController freeMarsController, ColonyManagerDialog colonyManagerDialog) {
        this.freeMarsController = freeMarsController;
        this.colonyManagerDialog = colonyManagerDialog;

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        FreeMarsColony selectedColony = colonyManagerDialog.getSelectedColony();
        freeMarsController.executeViewCommand(new SetCenteredCoordinateCommand(freeMarsController, selectedColony.getCoordinate()));
        (new DisplayColonyDialogAction(freeMarsController, selectedColony)).actionPerformed(null);
    }
}
