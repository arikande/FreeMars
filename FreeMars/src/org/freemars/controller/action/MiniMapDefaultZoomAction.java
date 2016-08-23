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
public class MiniMapDefaultZoomAction extends AbstractAction {

    private final FreeMarsController freeMarsController;

    public MiniMapDefaultZoomAction(FreeMarsController freeMarsController) {
        super("*");
        this.freeMarsController = freeMarsController;
    }

    public void actionPerformed(ActionEvent e) {
        freeMarsController.executeViewCommand(new SetMiniMapZoomLevelCommand(freeMarsController, FreeMarsViewModel.MINI_MAP_DEFAULT_ZOOM_LEVEL));
    }
}
