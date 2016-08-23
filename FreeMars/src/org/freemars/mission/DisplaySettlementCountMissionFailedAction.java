package org.freemars.mission;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.freemars.controller.FreeMarsController;
import org.freerealm.player.mission.SettlementCountMission;

/**
 *
 * @author Deniz ARIKAN
 */
public class DisplaySettlementCountMissionFailedAction extends AbstractAction {

    private final FreeMarsController controller;
    private final SettlementCountMission mission;

    public DisplaySettlementCountMissionFailedAction(FreeMarsController controller, SettlementCountMission mission) {
        super("Settlement count mission failed");
        this.controller = controller;
        this.mission = mission;
    }

    public void actionPerformed(ActionEvent e) {
        SettlementCountMissionFailedDialog dialog = new SettlementCountMissionFailedDialog(controller.getCurrentFrame(), controller.getFreeMarsModel(), mission);
        dialog.display();
    }
}
