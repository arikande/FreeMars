package org.freemars.controller.action;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.freemars.controller.FreeMarsController;
import org.freemars.ui.player.StatisticsDialog;

/**
 *
 * @author Deniz ARIKAN
 */
public class DisplayEarthTaxRateStatisticsDialogAction extends AbstractAction {

    private final FreeMarsController controller;

    public DisplayEarthTaxRateStatisticsDialogAction(FreeMarsController controller) {
        super("Earth tax rate");
        this.controller = controller;
    }

    public void actionPerformed(ActionEvent e) {
        new StatisticsDialog(controller.getCurrentFrame(), controller.getFreeMarsModel()).display(StatisticsDialog.EARTH_TAX_RATE_STATISTICS_TAB);
    }
}
