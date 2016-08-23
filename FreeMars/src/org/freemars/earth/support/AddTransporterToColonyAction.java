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
public class AddTransporterToColonyAction extends AbstractAction {

    private final FreeMarsController freeMarsController;
    private final FreeTransporterDialog freeTransporterDialog;
    private final Settlement colony;

    public AddTransporterToColonyAction(FreeMarsController freeMarsController, FreeTransporterDialog freeTransporterDialog, Settlement colony) {
        this.freeMarsController = freeMarsController;
        this.freeTransporterDialog = freeTransporterDialog;
        this.colony = colony;
    }

    public void actionPerformed(ActionEvent e) {
        FreeMarsPlayer activePlayer = (FreeMarsPlayer) freeMarsController.getFreeMarsModel().getActivePlayer();
        FreeRealmUnitType unitType = freeMarsController.getFreeMarsModel().getRealm().getUnitTypeManager().getUnitType("Transporter");
        Unit unit = new Unit(freeMarsController.getFreeMarsModel().getRealm(), unitType, colony.getCoordinate(), activePlayer);
        freeMarsController.execute(new AddUnitCommand(freeMarsController.getFreeMarsModel().getRealm(), activePlayer, unit));
        freeMarsController.execute(new WealthAddCommand(activePlayer, 720));
        activePlayer.setReceivedFreeTransporter(true);
        FreeMarsOptionPane.showMessageDialog(freeTransporterDialog, "Your new transporter has been transferred to " + colony.getName(), "Transporter added");
        freeTransporterDialog.dispose();
    }
}
