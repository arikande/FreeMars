package org.freemars.mission;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.freemars.controller.FreeMarsController;

/**
 *
 * @author Deniz ARIKAN
 */
public class DisplayExportResourceMissionAssignedAction extends AbstractAction {

    private final FreeMarsController controller;
    private final ExportResourceMission exportResourceMission;

    public DisplayExportResourceMissionAssignedAction(FreeMarsController controller, ExportResourceMission exportResourceMission) {
        super("Export resource mission");
        this.controller = controller;
        this.exportResourceMission = exportResourceMission;
    }

    public void actionPerformed(ActionEvent e) {
        ExportResourceMissionAssignedDialog dialog = new ExportResourceMissionAssignedDialog(controller.getCurrentFrame(), controller.getFreeMarsModel(), exportResourceMission);
        dialog.display();
    }
}
