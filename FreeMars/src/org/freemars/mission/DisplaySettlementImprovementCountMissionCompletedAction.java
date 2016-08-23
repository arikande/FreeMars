package org.freemars.mission;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.freemars.controller.FreeMarsController;
import org.freerealm.player.mission.SettlementImprovementCountMission;

/**
 *
 * @author Deniz ARIKAN
 */
public class DisplaySettlementImprovementCountMissionCompletedAction extends AbstractAction {

    private final FreeMarsController controller;
    private final SettlementImprovementCountMission mission;

    public DisplaySettlementImprovementCountMissionCompletedAction(FreeMarsController controller, SettlementImprovementCountMission mission) {
        super("Settlement improvement count mission completed");
        this.controller = controller;
        this.mission = mission;
    }

    public void actionPerformed(ActionEvent e) {
        SettlementImprovementCountMissionCompletedDialog dialog = new SettlementImprovementCountMissionCompletedDialog(controller.getCurrentFrame(), controller.getFreeMarsModel(), mission);
        dialog.display();
    }
}
