package org.freemars.mission;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.freemars.controller.FreeMarsController;
import org.freerealm.player.mission.TileImprovementCountMission;

/**
 *
 * @author Deniz ARIKAN
 */
public class DisplayTileImprovementCountMissionCompletedAction extends AbstractAction {

    private final FreeMarsController controller;
    private final TileImprovementCountMission mission;

    public DisplayTileImprovementCountMissionCompletedAction(FreeMarsController controller, TileImprovementCountMission mission) {
        super("Tile improvement count mission");
        this.controller = controller;
        this.mission = mission;
    }

    public void actionPerformed(ActionEvent e) {
        TileImprovementCountMissionCompletedDialog dialog = new TileImprovementCountMissionCompletedDialog(controller.getCurrentFrame(), controller.getFreeMarsModel(), mission);
        dialog.display();
    }
}
