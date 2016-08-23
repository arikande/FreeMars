package org.freemars.mission;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.freemars.controller.FreeMarsController;
import org.freerealm.player.Player;

/**
 *
 * @author Deniz ARIKAN
 */
public class DisplayMissionsDialogAction extends AbstractAction {

    private final FreeMarsController controller;

    public DisplayMissionsDialogAction(FreeMarsController controller) {
        super("Missions");
        this.controller = controller;
    }

    public void actionPerformed(ActionEvent e) {
        Player player = controller.getFreeMarsModel().getActivePlayer();
        MissionsDialog missionsDialog = new MissionsDialog(controller.getCurrentFrame(), controller.getFreeMarsModel(), player);
        missionsDialog.display();
    }
}
