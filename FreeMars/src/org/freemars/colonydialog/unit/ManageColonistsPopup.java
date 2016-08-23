package org.freemars.colonydialog.unit;

import javax.swing.JPopupMenu;
import org.freemars.colonydialog.ColonyDialogModel;
import org.freemars.controller.FreeMarsController;

/**
 *
 * @author Deniz ARIKAN
 */
public class ManageColonistsPopup extends JPopupMenu {

    private FreeMarsController freeMarsController;
    private ColonyDialogModel colonyDialogModel;
    private static int[] colonizerGroupSizes = new int[]{10, 20, 50, 100};

    protected ManageColonistsPopup(FreeMarsController freeMarsController, ColonyDialogModel colonyDialogModel) {
        this.freeMarsController = freeMarsController;
        this.colonyDialogModel = colonyDialogModel;
    }

    protected FreeMarsController getFreeMarsController() {
        return freeMarsController;
    }

    protected void setFreeMarsController(FreeMarsController freeMarsController) {
        this.freeMarsController = freeMarsController;
    }

    protected ColonyDialogModel getColonyDialogModel() {
        return colonyDialogModel;
    }

    protected void setColonyDialogModel(ColonyDialogModel colonyDialogModel) {
        this.colonyDialogModel = colonyDialogModel;
    }

    protected static int[] getColonizerGroupSizes() {
        return colonizerGroupSizes;
    }

    protected static void setColonizerGroupSizes(int[] aColonizerGroupSizes) {
        colonizerGroupSizes = aColonizerGroupSizes;
    }
}
