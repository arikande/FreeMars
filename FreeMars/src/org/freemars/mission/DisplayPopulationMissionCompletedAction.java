package org.freemars.mission;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.freemars.controller.FreeMarsController;
import org.freerealm.player.mission.PopulationMission;

/**
 *
 * @author Deniz ARIKAN
 */
public class DisplayPopulationMissionCompletedAction extends AbstractAction {

    private final FreeMarsController controller;
    private final PopulationMission populationMission;

    public DisplayPopulationMissionCompletedAction(FreeMarsController controller, PopulationMission populationMission) {
        super("Population mission");
        this.controller = controller;
        this.populationMission = populationMission;
    }

    public void actionPerformed(ActionEvent e) {
        PopulationMissionCompletedDialog dialog = new PopulationMissionCompletedDialog(controller.getCurrentFrame(), controller.getFreeMarsModel(), populationMission);
        dialog.display();
    }
}
