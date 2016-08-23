package org.freemars.mission;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.freemars.controller.FreeMarsController;

/**
 *
 * @author Deniz ARIKAN
 */
public class DisplayExportResourceMissionCompletedAction extends AbstractAction {

    private final FreeMarsController controller;
    private final ExportResourceMission exportResourceMission;

    public DisplayExportResourceMissionCompletedAction(FreeMarsController controller, ExportResourceMission exportResourceMission) {
        super("Export resource mission completed");
        this.controller = controller;
        this.exportResourceMission = exportResourceMission;
    }

    public void actionPerformed(ActionEvent e) {
        ExportResourceMissionCompletedDialog dialog = new ExportResourceMissionCompletedDialog(controller.getCurrentFrame(), controller.getFreeMarsModel(), exportResourceMission);
        dialog.display();
    }
}
