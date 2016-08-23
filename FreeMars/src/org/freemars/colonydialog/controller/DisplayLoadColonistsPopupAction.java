package org.freemars.colonydialog.controller;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import org.freemars.colonydialog.ColonyDialogModel;
import org.freemars.colonydialog.unit.LoadColonistsPopup;
import org.freemars.controller.FreeMarsController;

/**
 *
 * @author arikande
 */
public class DisplayLoadColonistsPopupAction extends AbstractAction {

    private final FreeMarsController freeMarsController;
    private final ColonyDialogModel colonyDialogModel;

    public DisplayLoadColonistsPopupAction(FreeMarsController freeMarsController, ColonyDialogModel colonyDialogModel) {
        this.freeMarsController = freeMarsController;
        this.colonyDialogModel = colonyDialogModel;
    }

    public void actionPerformed(ActionEvent e) {
        LoadColonistsPopup loadColonistsPopup = new LoadColonistsPopup(freeMarsController, colonyDialogModel);
        JComponent source = (JComponent) e.getSource();
        loadColonistsPopup.build();
        loadColonistsPopup.show(source, source.getWidth(), 0);
    }
}
