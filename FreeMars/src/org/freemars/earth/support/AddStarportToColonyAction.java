package org.freemars.earth.support;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.freemars.controller.FreeMarsController;
import org.freemars.player.FreeMarsPlayer;
import org.freemars.ui.util.FreeMarsOptionPane;
import org.freerealm.command.AddSettlementImprovementCommand;
import org.freerealm.command.WealthAddCommand;
import org.freerealm.settlement.Settlement;
import org.freerealm.settlement.improvement.SettlementImprovementType;

/**
 *
 * @author arikande
 */
public class AddStarportToColonyAction extends AbstractAction {

    private final FreeMarsController freeMarsController;
    private final FreeStarportDialog freeStarportDialog;
    private final Settlement colony;

    public AddStarportToColonyAction(FreeMarsController freeMarsController, FreeStarportDialog freeStarportDialog, Settlement colony) {
        this.freeMarsController = freeMarsController;
        this.freeStarportDialog = freeStarportDialog;
        this.colony = colony;
    }

    public void actionPerformed(ActionEvent e) {
        SettlementImprovementType starportType = freeMarsController.getFreeMarsModel().getRealm().getSettlementImprovementManager().getImprovement("Starport");
        freeMarsController.execute(new AddSettlementImprovementCommand(colony, starportType));
        freeMarsController.execute(new WealthAddCommand(freeMarsController.getFreeMarsModel().getActivePlayer(), 1920));
        ((FreeMarsPlayer) freeMarsController.getFreeMarsModel().getActivePlayer()).setReceivedFreeStarport(true);
        FreeMarsOptionPane.showMessageDialog(freeStarportDialog, "New starport has been built in " + colony.getName(), "Starport built");
        freeStarportDialog.dispose();
    }

}
