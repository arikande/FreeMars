package org.freemars.mission;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.freemars.controller.FreeMarsController;
import org.freerealm.player.mission.SettlementCountMission;

/**
 *
 * @author Deniz ARIKAN
 */
public class DisplaySettlementCountMissionCompletedAction extends AbstractAction {

    private final FreeMarsController controller;
    private final SettlementCountMission mission;

    public DisplaySettlementCountMissionCompletedAction(FreeMarsController controller, SettlementCountMission mission) {
        super("Settlement count mission completed");
        this.controller = controller;
        this.mission = mission;
    }

    public void actionPerformed(ActionEvent e) {
        SettlementCountMissionCompletedDialog dialog = new SettlementCountMissionCompletedDialog(controller.getCurrentFrame(), controller.getFreeMarsModel(), mission);
        dialog.display();
    }
}
