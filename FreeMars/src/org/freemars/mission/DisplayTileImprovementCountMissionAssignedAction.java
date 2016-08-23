package org.freemars.mission;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.freemars.controller.FreeMarsController;
import org.freerealm.player.mission.TileImprovementCountMission;

/**
 *
 * @author Deniz ARIKAN
 */
public class DisplayTileImprovementCountMissionAssignedAction extends AbstractAction {

    private final FreeMarsController controller;
    private final TileImprovementCountMission mission;

    public DisplayTileImprovementCountMissionAssignedAction(FreeMarsController controller, TileImprovementCountMission mission) {
        super("Tile improvement count mission");
        this.controller = controller;
        this.mission = mission;
    }

    public void actionPerformed(ActionEvent e) {
        TileImprovementCountMissionAssignedDialog dialog = new TileImprovementCountMissionAssignedDialog(controller.getCurrentFrame(), controller.getFreeMarsModel(), mission);
        dialog.display();
    }
}
