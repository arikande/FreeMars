package org.freemars.earth.support;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.freemars.controller.FreeMarsController;
import org.freemars.player.FreeMarsPlayer;
import org.freemars.ui.util.FreeMarsOptionPane;
import org.freerealm.command.AddUnitCommand;
import org.freerealm.command.WealthAddCommand;
import org.freerealm.settlement.Settlement;
import org.freerealm.unit.FreeRealmUnitType;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class AddColonizerToColonyAction extends AbstractAction {

    private final FreeMarsController freeMarsController;
    private final FreeColonizerDialog freeColonizerDialog;
    private final Settlement colony;

    public AddColonizerToColonyAction(FreeMarsController freeMarsController, FreeColonizerDialog freeColonizerDialog, Settlement colony) {
        this.freeMarsController = freeMarsController;
        this.freeColonizerDialog = freeColonizerDialog;
        this.colony = colony;
    }

    public void actionPerformed(ActionEvent e) {
        FreeMarsPlayer activePlayer = (FreeMarsPlayer) freeMarsController.getFreeMarsModel().getActivePlayer();
        FreeRealmUnitType colonizerType = freeMarsController.getFreeMarsModel().getRealm().getUnitTypeManager().getUnitType("Colonizer");
        Unit unit = new Unit(freeMarsController.getFreeMarsModel().getRealm(), colonizerType, colony.getCoordinate(), activePlayer);
        freeMarsController.execute(new AddUnitCommand(freeMarsController.getFreeMarsModel().getRealm(), activePlayer, unit));
        freeMarsController.execute(new WealthAddCommand(activePlayer, 900));
        activePlayer.setReceivedFreeColonizer(true);
        FreeMarsOptionPane.showMessageDialog(freeColonizerDialog, "Your new colonizer has been transferred to " + colony.getName(), "Colonizer added");
        freeColonizerDialog.dispose();
    }
}
