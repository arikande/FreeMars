package org.freemars.colonydialog.unit;

import javax.swing.JMenuItem;
import org.freemars.colonydialog.ColonyDialogModel;
import org.freemars.colonydialog.controller.LoadColonistsAction;
import org.freemars.controller.FreeMarsController;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class LoadColonistsPopup extends ManageColonistsPopup {

    public LoadColonistsPopup(FreeMarsController freeMarsController, ColonyDialogModel colonyDialogModel) {
        super(freeMarsController, colonyDialogModel);
    }

    public void build() {
        Unit selectedUnit = getColonyDialogModel().getSelectedUnit();
        int weightPerColonizer = Integer.parseInt(getFreeMarsController().getFreeMarsModel().getRealm().getProperty("weight_per_citizen"));
        int colonizerCapacity = selectedUnit.getRemainingCapacity() / weightPerColonizer;
        if (colonizerCapacity > 0) {
            for (int i = 0; i < getColonizerGroupSizes().length; i++) {
                int colonizerGroupSize = getColonizerGroupSizes()[i];
                if (colonizerGroupSize < colonizerCapacity) {
                    add(new JMenuItem(new LoadColonistsAction(getFreeMarsController(), getColonyDialogModel(), colonizerGroupSize)));
                }
            }
            add(new JMenuItem(new LoadColonistsAction(getFreeMarsController(), getColonyDialogModel(), colonizerCapacity)));
        }
    }
}
