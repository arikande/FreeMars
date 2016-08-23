package org.freemars.controller.action;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.freemars.controller.FreeMarsController;
import org.freemars.controller.viewcommand.SetMiniMapZoomLevelCommand;
import org.freemars.model.FreeMarsViewModel;

/**
 *
 * @author Deniz ARIKAN
 */
public class MiniMapZoomInAction extends AbstractAction {

    private final FreeMarsController freeMarsController;

    public MiniMapZoomInAction(FreeMarsController controller) {
        super("+");
        this.freeMarsController = controller;
    }

    public void actionPerformed(ActionEvent e) {
        int zoomLevel = freeMarsController.getFreeMarsModel().getFreeMarsViewModel().getMiniMapPanelZoomLevel();
        if (zoomLevel < FreeMarsViewModel.MAIN_MAP_MAXIMUM_ZOOM) {
            freeMarsController.executeViewCommand(new SetMiniMapZoomLevelCommand(freeMarsController, zoomLevel + 1));
        }
    }

    public boolean checkEnabled() {
        return freeMarsController.getFreeMarsModel().getFreeMarsViewModel().getMiniMapPanelZoomLevel() < FreeMarsViewModel.MINI_MAP_MAXIMUM_ZOOM;
    }
}
