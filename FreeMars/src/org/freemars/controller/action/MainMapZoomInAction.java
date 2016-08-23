package org.freemars.controller.action;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.freemars.controller.FreeMarsController;
import org.freemars.controller.viewcommand.SetMainMapZoomLevelCommand;
import org.freemars.model.FreeMarsViewModel;

/**
 *
 * @author Deniz ARIKAN
 */
public class MainMapZoomInAction extends AbstractAction {

    private final FreeMarsController freeMarsController;

    public MainMapZoomInAction(FreeMarsController freeMarsController) {
        super("Zoom in", null);
        this.freeMarsController = freeMarsController;
    }

    public void actionPerformed(ActionEvent e) {
        int zoomLevel = freeMarsController.getFreeMarsModel().getFreeMarsViewModel().getMapPanelZoomLevel();
        if (zoomLevel < FreeMarsViewModel.MAIN_MAP_MAXIMUM_ZOOM) {
            freeMarsController.executeViewCommand(new SetMainMapZoomLevelCommand(freeMarsController, zoomLevel + 1));
        }
    }

    public boolean checkEnabled() {
        return freeMarsController.getFreeMarsModel().getFreeMarsViewModel().getMapPanelZoomLevel() < FreeMarsViewModel.MAIN_MAP_MAXIMUM_ZOOM;
    }
}
