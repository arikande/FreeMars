package org.freemars.controller.action;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.freemars.controller.FreeMarsController;
import org.freemars.controller.viewcommand.SetMainMapZoomLevelCommand;

/**
 *
 * @author Deniz ARIKAN
 */
public class MapPanelZoomLevelSetAction extends AbstractAction {

    private final FreeMarsController freeMarsController;
    private final int zoomLevel;

    public MapPanelZoomLevelSetAction(FreeMarsController freeMarsController, int zoomLevel) {
        super(String.valueOf(zoomLevel), null);
        this.freeMarsController = freeMarsController;
        this.zoomLevel = zoomLevel;
    }

    public void actionPerformed(ActionEvent e) {
        freeMarsController.executeViewCommand(new SetMainMapZoomLevelCommand(freeMarsController, zoomLevel));
    }
}
