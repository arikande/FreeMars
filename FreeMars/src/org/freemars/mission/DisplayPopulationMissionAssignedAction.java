package org.freemars.mission;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.freemars.controller.FreeMarsController;
import org.freerealm.player.mission.PopulationMission;

/**
 *
 * @author Deniz ARIKAN
 */
public class DisplayPopulationMissionAssignedAction extends AbstractAction {

    private final FreeMarsController controller;
    private final PopulationMission populationMission;

    public DisplayPopulationMissionAssignedAction(FreeMarsController controller, PopulationMission populationMission) {
        super("Population mission");
        this.controller = controller;
        this.populationMission = populationMission;
    }

    public void actionPerformed(ActionEvent e) {
        PopulationMissionAssignedDialog dialog = new PopulationMissionAssignedDialog(controller.getCurrentFrame(), controller.getFreeMarsModel(), populationMission);
        dialog.display();
    }
}
