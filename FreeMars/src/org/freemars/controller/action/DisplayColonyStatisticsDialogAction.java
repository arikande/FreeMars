package org.freemars.controller.action;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.freemars.controller.FreeMarsController;
import org.freemars.ui.player.StatisticsDialog;

/**
 *
 * @author Deniz ARIKAN
 */
public class DisplayColonyStatisticsDialogAction extends AbstractAction {

    private final FreeMarsController controller;

    public DisplayColonyStatisticsDialogAction(FreeMarsController controller) {
        super("Colony");
        this.controller = controller;
    }

    public void actionPerformed(ActionEvent e) {
        new StatisticsDialog(controller.getCurrentFrame(), controller.getFreeMarsModel()).display(StatisticsDialog.COLONY_COUNT_STATISTICS_TAB);
    }
}
