package org.freemars.colony.manager;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.apache.log4j.Logger;
import org.freemars.colonydialog.controller.CloseDialogAction;
import org.freemars.controller.FreeMarsController;
import org.freerealm.player.Player;

/**
 *
 * @author Deniz ARIKAN
 */
public class DisplayColonyManagerDialogAction extends AbstractAction {

    private final FreeMarsController controller;

    public DisplayColonyManagerDialogAction(FreeMarsController controller) {
        super("Colony manager");
        this.controller = controller;
    }

    public void actionPerformed(ActionEvent e) {
        Player player = controller.getFreeMarsModel().getActivePlayer();
        ColonyManagerDialog colonyManagerDialog = new ColonyManagerDialog(controller.getCurrentFrame(), controller.getFreeMarsModel(), player);
        colonyManagerDialog.setGrowthButtonAction(new SetColoniesTableModeAction(colonyManagerDialog, ColonyManagerDialog.GROWTH_MODE));
        colonyManagerDialog.setProductionButtonAction(new SetColoniesTableModeAction(colonyManagerDialog, ColonyManagerDialog.PRODUCTION_MODE));
        colonyManagerDialog.setManufacturingButtonAction(new SetColoniesTableModeAction(colonyManagerDialog, ColonyManagerDialog.MANUFACTURING_MODE));
        colonyManagerDialog.setResourcesButtonAction(new SetColoniesTableModeAction(colonyManagerDialog, ColonyManagerDialog.RESOURCES_MODE));
        colonyManagerDialog.setImprovementsButtonAction(new SetColoniesTableModeAction(colonyManagerDialog, ColonyManagerDialog.IMPROVEMENTS_MODE));
        colonyManagerDialog.setGarrisonButtonAction(new SetColoniesTableModeAction(colonyManagerDialog, ColonyManagerDialog.GARRISON_MODE));
        colonyManagerDialog.setCloseButtonAction(new CloseDialogAction(colonyManagerDialog));
        colonyManagerDialog.setColoniesTableMouseAdapter(new ColoniesTableMouseAdapter(controller, colonyManagerDialog));
        colonyManagerDialog.setMode(ColonyManagerDialog.GROWTH_MODE);
        Logger.getLogger(DisplayColonyManagerDialogAction.class).info("Colony manager will be displayed.");
        colonyManagerDialog.display();
    }
}
