package org.freemars.mission;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.freemars.controller.FreeMarsController;
import org.freerealm.player.mission.ExplorationMission;

/**
 *
 * @author Deniz ARIKAN
 */
public class DisplayExplorationMissionAssignedAction extends AbstractAction {

    private final FreeMarsController controller;
    private final ExplorationMission explorationMission;

    public DisplayExplorationMissionAssignedAction(FreeMarsController controller, ExplorationMission explorationMission) {
        super("Exploration mission");
        this.controller = controller;
        this.explorationMission = explorationMission;
    }

    public void actionPerformed(ActionEvent e) {
        ExplorationMissionAssignedDialog dialog = new ExplorationMissionAssignedDialog(controller.getCurrentFrame(), controller.getFreeMarsModel(), explorationMission);
        dialog.display();
    }
}
