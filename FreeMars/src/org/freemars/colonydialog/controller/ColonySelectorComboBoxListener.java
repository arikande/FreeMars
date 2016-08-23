package org.freemars.colonydialog.controller;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import org.freemars.colony.FreeMarsColony;
import org.freemars.colonydialog.ColonyDialogModel;
import org.freemars.colonydialog.ColonySelectorComboBox;
import org.freemars.controller.FreeMarsController;
import org.freemars.controller.viewcommand.SetCenteredCoordinateCommand;

/**
 *
 * @author Deniz ARIKAN
 */
public class ColonySelectorComboBoxListener implements ItemListener {

    private final FreeMarsController freeMarsController;
    private final ColonyDialogModel model;

    public ColonySelectorComboBoxListener(FreeMarsController freeMarsController, ColonyDialogModel model) {
        this.freeMarsController = freeMarsController;
        this.model = model;
    }

    public void itemStateChanged(ItemEvent e) {
        FreeMarsColony selectedFreeMarsColony = (FreeMarsColony) ((ColonySelectorComboBox) e.getSource()).getSelectedItem();
        model.setColony(selectedFreeMarsColony);
        freeMarsController.executeViewCommand(new SetCenteredCoordinateCommand(freeMarsController, selectedFreeMarsColony.getCoordinate()));
    }
}
