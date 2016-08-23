package org.freemars.colonydialog.controller;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.freemars.colony.FreeMarsColony;
import org.freemars.colonydialog.ColonyDialogModel;
import org.freemars.controller.FreeMarsController;
import org.freemars.controller.viewcommand.SetCenteredCoordinateCommand;

/**
 *
 * @author Deniz ARIKAN
 */
public class SetSelectedColonyAction extends AbstractAction {

    private final FreeMarsController freeMarsController;
    private final ColonyDialogModel colonyDialogModel;
    private final FreeMarsColony freeMarsColony;

    public SetSelectedColonyAction(FreeMarsController freeMarsController, ColonyDialogModel colonyDialogModel, FreeMarsColony freeMarsColony) {
        this.freeMarsController = freeMarsController;
        this.colonyDialogModel = colonyDialogModel;
        this.freeMarsColony = freeMarsColony;
    }

    public void actionPerformed(ActionEvent e) {
        colonyDialogModel.setColony(freeMarsColony);
        freeMarsController.executeViewCommand(new SetCenteredCoordinateCommand(freeMarsController, freeMarsColony.getCoordinate()));
    }
}
