package org.freemars.colonydialog.unit;

import javax.swing.JMenuItem;
import org.freemars.colonydialog.ColonyDialogModel;
import org.freemars.colonydialog.controller.UnloadColonistsAction;
import org.freemars.controller.FreeMarsController;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class UnloadColonistsPopup extends ManageColonistsPopup {

    public UnloadColonistsPopup(FreeMarsController freeMarsController, ColonyDialogModel colonyDialogModel) {
        super(freeMarsController, colonyDialogModel);
    }

    public void build() {
        Unit selectedUnit = getColonyDialogModel().getSelectedUnit();
        int containedPopulation = selectedUnit.getContainedPopulation();
        if (containedPopulation > 0) {
            for (int i = 0; i < getColonizerGroupSizes().length; i++) {
                int colonizerGroupSize = getColonizerGroupSizes()[i];
                if (colonizerGroupSize < containedPopulation) {
                    add(new JMenuItem(new UnloadColonistsAction(getFreeMarsController(), getColonyDialogModel(), colonizerGroupSize)));
                }
            }
            add(new JMenuItem(new UnloadColonistsAction(getFreeMarsController(), getColonyDialogModel(), containedPopulation)));
        }
    }
}
