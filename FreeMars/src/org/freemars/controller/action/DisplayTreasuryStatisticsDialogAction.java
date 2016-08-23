package org.freemars.controller.action;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.freemars.controller.FreeMarsController;
import org.freemars.ui.player.StatisticsDialog;

/**
 *
 * @author Deniz ARIKAN
 */
public class DisplayTreasuryStatisticsDialogAction extends AbstractAction {

    private final FreeMarsController controller;

    public DisplayTreasuryStatisticsDialogAction(FreeMarsController controller) {
        super("Treasury");
        this.controller = controller;
    }

    public void actionPerformed(ActionEvent e) {
        new StatisticsDialog(controller.getCurrentFrame(), controller.getFreeMarsModel()).display(StatisticsDialog.WEALTH_STATISTICS_TAB);
    }
}
