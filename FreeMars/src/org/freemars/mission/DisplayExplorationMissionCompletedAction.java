package org.freemars.mission;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.freemars.controller.FreeMarsController;
import org.freerealm.player.mission.ExplorationMission;

/**
 *
 * @author Deniz ARIKAN
 */
public class DisplayExplorationMissionCompletedAction extends AbstractAction {

    private final FreeMarsController controller;
    private final ExplorationMission explorationMission;

    public DisplayExplorationMissionCompletedAction(FreeMarsController controller, ExplorationMission explorationMission) {
        super("Exploration mission");
        this.controller = controller;
        this.explorationMission = explorationMission;
    }

    public void actionPerformed(ActionEvent e) {
        ExplorationMissionCompletedDialog dialog = new ExplorationMissionCompletedDialog(controller.getCurrentFrame(), controller.getFreeMarsModel(), explorationMission);
        dialog.display();
    }
}
