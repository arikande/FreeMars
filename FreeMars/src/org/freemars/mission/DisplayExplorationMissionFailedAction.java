package org.freemars.mission;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.freemars.controller.FreeMarsController;
import org.freerealm.player.mission.ExplorationMission;

/**
 *
 * @author Deniz ARIKAN
 */
public class DisplayExplorationMissionFailedAction extends AbstractAction {

    private final FreeMarsController controller;
    private final ExplorationMission explorationMission;

    public DisplayExplorationMissionFailedAction(FreeMarsController controller, ExplorationMission explorationMission) {
        super("Exploration mission failed");
        this.controller = controller;
        this.explorationMission = explorationMission;
    }

    public void actionPerformed(ActionEvent e) {
        ExplorationMissionFailedDialog dialog = new ExplorationMissionFailedDialog(controller.getCurrentFrame(), controller.getFreeMarsModel(), explorationMission);
        dialog.display();
    }
}
