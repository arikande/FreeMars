package org.freemars.mission;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.freemars.controller.FreeMarsController;
import org.freerealm.player.mission.ClearTileVegetationCountMission;

/**
 *
 * @author Deniz ARIKAN
 */
public class DisplayClearTileVegetationMissionCompletedAction extends AbstractAction {

    private final FreeMarsController controller;
    private final ClearTileVegetationCountMission clearTileVegetationCountMission;

    public DisplayClearTileVegetationMissionCompletedAction(FreeMarsController controller, ClearTileVegetationCountMission clearTileVegetationCountMission) {
        super("Clear vegetation mission");
        this.controller = controller;
        this.clearTileVegetationCountMission = clearTileVegetationCountMission;
    }

    public void actionPerformed(ActionEvent e) {
        ClearTileVegetationMissionCompletedDialog dialog = new ClearTileVegetationMissionCompletedDialog(controller.getCurrentFrame(), controller.getFreeMarsModel(), clearTileVegetationCountMission);
        dialog.display();
    }
}
