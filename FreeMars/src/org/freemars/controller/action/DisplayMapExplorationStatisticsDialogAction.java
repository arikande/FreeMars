package org.freemars.controller.action;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.freemars.controller.FreeMarsController;
import org.freemars.ui.player.StatisticsDialog;

/**
 *
 * @author Deniz ARIKAN
 */
public class DisplayMapExplorationStatisticsDialogAction extends AbstractAction {

    private final FreeMarsController controller;

    public DisplayMapExplorationStatisticsDialogAction(FreeMarsController controller) {
        super("Map exploration");
        this.controller = controller;
    }

    public void actionPerformed(ActionEvent e) {
        new StatisticsDialog(controller.getCurrentFrame(), controller.getFreeMarsModel()).display(StatisticsDialog.MAP_EXPLORATION_STATISTICS_TAB);
    }
}
