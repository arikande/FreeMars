package org.freemars.controller.listener;

import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import javax.swing.event.MouseInputAdapter;
import org.freemars.controller.FreeMarsController;
import org.freemars.controller.viewcommand.SetCenteredCoordinateCommand;
import org.freemars.ui.map.MiniMapPanel;
import org.freerealm.map.Coordinate;

/**
 *
 * @author Deniz ARIKAN
 */
public class MiniMapPanelMouseListener extends MouseInputAdapter {

    private final FreeMarsController freeMarsController;
    private final MiniMapPanel miniMapPanel;

    public MiniMapPanelMouseListener(FreeMarsController freeMarsController, MiniMapPanel miniMapPanel) {
        this.freeMarsController = freeMarsController;
        this.miniMapPanel = miniMapPanel;
    }

    private void handleLeftClick(MouseEvent mouseEvent) {
        Coordinate coordinate = miniMapPanel.getCoordinateAt(mouseEvent.getX(), mouseEvent.getY());
        if (coordinate.getOrdinate() >= freeMarsController.getFreeMarsModel().getRealm().getMapHeight()) {
            return;
        }
        freeMarsController.executeViewCommand(new SetCenteredCoordinateCommand(freeMarsController, coordinate));
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        miniMapPanel.requestFocus();
        if ((mouseEvent.getModifiers() & InputEvent.BUTTON1_MASK) == InputEvent.BUTTON1_MASK) {
            handleLeftClick(mouseEvent);
        }
    }
}
