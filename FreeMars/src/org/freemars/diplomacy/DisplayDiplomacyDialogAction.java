package org.freemars.diplomacy;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.freemars.colonydialog.controller.CloseDialogAction;
import org.freemars.controller.FreeMarsController;

/**
 *
 * @author Deniz ARIKAN
 */
public class DisplayDiplomacyDialogAction extends AbstractAction {

    private final FreeMarsController freeMarsController;

    public DisplayDiplomacyDialogAction(FreeMarsController controller) {
        super("Diplomacy");
        this.freeMarsController = controller;
    }

    public void actionPerformed(ActionEvent e) {
        DiplomacyDialogModel diplomacyDialogModel = new DiplomacyDialogModel();
        diplomacyDialogModel.setFreeMarsModel(freeMarsController.getFreeMarsModel());
        diplomacyDialogModel.setPlayer(freeMarsController.getFreeMarsModel().getActivePlayer());
        DiplomacyDialog diplomacyDialog = new DiplomacyDialog(freeMarsController.getCurrentFrame());
        DiplomacyTableModel diplomacyTableModel = new DiplomacyTableModel(freeMarsController.getFreeMarsModel(), freeMarsController.getFreeMarsModel().getActivePlayer());
        diplomacyDialog.setDiplomacyTableModel(diplomacyTableModel);
        DiplomacyTableMouseAdapter diplomacyTableMouseAdapter
                = new DiplomacyTableMouseAdapter(freeMarsController, diplomacyTableModel, freeMarsController.getFreeMarsModel().getActivePlayer());
        diplomacyDialog.setDiplomacyTableMouseAdapter(diplomacyTableMouseAdapter);
        diplomacyDialog.setCloseAction(new CloseDialogAction(diplomacyDialog));
        diplomacyDialog.display();
    }

}
