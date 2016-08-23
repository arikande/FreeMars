package org.freemars.mission;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.freemars.controller.FreeMarsController;
import org.freerealm.player.mission.ClearTileVegetationCountMission;

/**
 *
 * @author Deniz ARIKAN
 */
public class DisplayClearTileVegetationMissionFailedAction extends AbstractAction {

    private final FreeMarsController controller;
    private final ClearTileVegetationCountMission clearTileVegetationCountMission;

    public DisplayClearTileVegetationMissionFailedAction(FreeMarsController controller, ClearTileVegetationCountMission clearTileVegetationCountMission) {
        super("Clear vegetation mission");
        this.controller = controller;
        this.clearTileVegetationCountMission = clearTileVegetationCountMission;
    }

    public void actionPerformed(ActionEvent e) {
        ClearTileVegetationMissionFailedDialog dialog = new ClearTileVegetationMissionFailedDialog(controller.getCurrentFrame(), controller.getFreeMarsModel(), clearTileVegetationCountMission);
        dialog.display();
    }
}
