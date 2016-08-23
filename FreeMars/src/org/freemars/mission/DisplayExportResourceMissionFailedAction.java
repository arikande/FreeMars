package org.freemars.mission;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.freemars.controller.FreeMarsController;

/**
 *
 * @author Deniz ARIKAN
 */
public class DisplayExportResourceMissionFailedAction extends AbstractAction {

    private final FreeMarsController controller;
    private final ExportResourceMission exportResourceMission;

    public DisplayExportResourceMissionFailedAction(FreeMarsController controller, ExportResourceMission exportResourceMission) {
        super("Export resource mission failed");
        this.controller = controller;
        this.exportResourceMission = exportResourceMission;
    }

    public void actionPerformed(ActionEvent e) {
        ExportResourceMissionFailedDialog dialog = new ExportResourceMissionFailedDialog(controller.getCurrentFrame(), controller.getFreeMarsModel(), exportResourceMission);
        dialog.display();
    }
}
