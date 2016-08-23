package org.freemars.mission;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.freemars.controller.FreeMarsController;
import org.freerealm.player.mission.PopulationMission;

/**
 *
 * @author Deniz ARIKAN
 */
public class DisplayPopulationMissionFailedAction extends AbstractAction {

    private final FreeMarsController controller;
    private final PopulationMission mission;

    public DisplayPopulationMissionFailedAction(FreeMarsController controller, PopulationMission mission) {
        super("Population mission failed");
        this.controller = controller;
        this.mission = mission;
    }

    public void actionPerformed(ActionEvent e) {
        PopulationMissionFailedDialog dialog = new PopulationMissionFailedDialog(controller.getCurrentFrame(), controller.getFreeMarsModel(), mission);
        dialog.display();
    }
}
