package org.freemars.mission;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.freemars.controller.FreeMarsController;
import org.freerealm.player.mission.SettlementImprovementCountMission;

/**
 *
 * @author Deniz ARIKAN
 */
public class DisplaySettlementImprovementCountMissionAssignedAction extends AbstractAction {

    private final FreeMarsController controller;
    private final SettlementImprovementCountMission mission;

    public DisplaySettlementImprovementCountMissionAssignedAction(FreeMarsController controller, SettlementImprovementCountMission mission) {
        super("Settlement improvement count mission");
        this.controller = controller;
        this.mission = mission;
    }

    public void actionPerformed(ActionEvent e) {
        SettlementImprovementCountMissionAssignedDialog dialog = new SettlementImprovementCountMissionAssignedDialog(controller.getCurrentFrame(), controller.getFreeMarsModel(), mission);
        dialog.display();
    }
}
