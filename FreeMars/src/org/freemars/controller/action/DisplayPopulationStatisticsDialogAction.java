package org.freemars.controller.action;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.freemars.controller.FreeMarsController;
import org.freemars.ui.player.StatisticsDialog;

/**
 *
 * @author Deniz ARIKAN
 */
public class DisplayPopulationStatisticsDialogAction extends AbstractAction {

    private final FreeMarsController controller;

    public DisplayPopulationStatisticsDialogAction(FreeMarsController controller) {
        super("Population");
        this.controller = controller;
    }

    public void actionPerformed(ActionEvent e) {
        new StatisticsDialog(controller.getCurrentFrame(), controller.getFreeMarsModel()).display(StatisticsDialog.POPULATION_STATISTICS_TAB);
    }
}
