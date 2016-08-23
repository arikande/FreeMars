package org.freemars.colonydialog.controller;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import org.freemars.colonydialog.ColonyDialogModel;
import org.freemars.colonydialog.unit.UnloadColonistsPopup;
import org.freemars.controller.FreeMarsController;

/**
 *
 * @author arikande
 */
public class DisplayUnloadColonistsPopupAction extends AbstractAction {

    private final FreeMarsController freeMarsController;
    private final ColonyDialogModel colonyDialogModel;

    public DisplayUnloadColonistsPopupAction(FreeMarsController freeMarsController, ColonyDialogModel colonyDialogModel) {
        this.freeMarsController = freeMarsController;
        this.colonyDialogModel = colonyDialogModel;
    }

    public void actionPerformed(ActionEvent e) {
        UnloadColonistsPopup unloadColonistsPopup = new UnloadColonistsPopup(freeMarsController, colonyDialogModel);
        JComponent source = (JComponent) e.getSource();
        unloadColonistsPopup.build();
        unloadColonistsPopup.show(source, source.getWidth(), 0);
    }
}
