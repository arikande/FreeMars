package org.freemars.colonydialog.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;

import org.freemars.colonydialog.ColonyDialogModel;
import org.freemars.controller.FreeMarsController;
import org.freerealm.command.SetSettlementContinuousProductionCommand;

/**
 *
 * @author Deniz ARIKAN
 */
public class ContinuousProductionActionListener implements ActionListener {

    private final FreeMarsController freeMarsController;
    private final ColonyDialogModel model;

    public ContinuousProductionActionListener(FreeMarsController freeMarsController, ColonyDialogModel model) {
        this.freeMarsController = freeMarsController;
        this.model = model;
    }

    public void actionPerformed(ActionEvent e) {
        JCheckBox contiuousProductionCheckBox = (JCheckBox) e.getSource();
        freeMarsController.execute(new SetSettlementContinuousProductionCommand(model.getColony(), contiuousProductionCheckBox.isSelected()));
    }
}
