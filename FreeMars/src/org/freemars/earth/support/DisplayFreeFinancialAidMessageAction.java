package org.freemars.earth.support;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.freemars.controller.FreeMarsController;

/**
 *
 * @author Deniz ARIKAN
 */
public class DisplayFreeFinancialAidMessageAction extends AbstractAction {

    private final FreeMarsController controller;

    public DisplayFreeFinancialAidMessageAction(FreeMarsController controller) {
        super("Free financial aid");
        this.controller = controller;
    }

    public void actionPerformed(ActionEvent e) {
        FreeFinancialAidDialog dialog = new FreeFinancialAidDialog(controller.getCurrentFrame(), controller.getFreeMarsModel());
        dialog.addWindowListener(new FreeFinancialAidDialogWindowAdapter(controller));
        dialog.display();
    }
}
