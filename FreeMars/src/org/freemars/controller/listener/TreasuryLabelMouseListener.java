package org.freemars.controller.listener;

import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import javax.swing.event.MouseInputAdapter;
import org.freemars.controller.FreeMarsController;
import org.freemars.controller.action.DisplayTreasuryStatisticsDialogAction;

/**
 *
 * @author Deniz ARIKAN
 */
public class TreasuryLabelMouseListener extends MouseInputAdapter {

    private final FreeMarsController controller;

    public TreasuryLabelMouseListener(FreeMarsController controller) {
        this.controller = controller;
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        if ((mouseEvent.getModifiers() & InputEvent.BUTTON1_MASK) == InputEvent.BUTTON1_MASK) {
            new DisplayTreasuryStatisticsDialogAction(controller).actionPerformed(null);
        }
    }
}
