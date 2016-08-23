package org.freemars.controller.listener;

import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import javax.swing.event.MouseInputAdapter;
import org.freemars.controller.FreeMarsController;
import org.freemars.controller.action.DisplayPopulationStatisticsDialogAction;

/**
 *
 * @author Deniz ARIKAN
 */
public class PopulationLabelMouseListener extends MouseInputAdapter {

    private final FreeMarsController controller;

    public PopulationLabelMouseListener(FreeMarsController controller) {
        this.controller = controller;
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        if ((mouseEvent.getModifiers() & InputEvent.BUTTON1_MASK) == InputEvent.BUTTON1_MASK) {
            new DisplayPopulationStatisticsDialogAction(controller).actionPerformed(null);
        }
    }
 

}
