package org.freemars.mission;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.freemars.controller.FreeMarsController;
import org.freerealm.player.mission.SettlementCountMission;

/**
 *
 * @author Deniz ARIKAN
 */
public class DisplaySettlementCountMissionAssignedAction extends AbstractAction {

    private final FreeMarsController controller;
    private final SettlementCountMission mission;

    public DisplaySettlementCountMissionAssignedAction(FreeMarsController controller, SettlementCountMission mission) {
        super("Settlement count mission");
        this.controller = controller;
        this.mission = mission;
    }

    public void actionPerformed(ActionEvent e) {
        SettlementCountMissionAssignedDialog dialog = new SettlementCountMissionAssignedDialog(controller.getCurrentFrame(), controller.getFreeMarsModel(), mission);
        dialog.display();
    }
}
