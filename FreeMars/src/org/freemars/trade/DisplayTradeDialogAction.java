package org.freemars.trade;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.freemars.controller.FreeMarsController;

/**
 *
 * @author Deniz ARIKAN
 */
public class DisplayTradeDialogAction extends AbstractAction {

    private final FreeMarsController controller;

    public DisplayTradeDialogAction(FreeMarsController controller) {
        super("Trade");
        this.controller = controller;
    }

    public void actionPerformed(ActionEvent e) {
        TradeDialog dialog = new TradeDialog(controller.getCurrentFrame(), controller.getFreeMarsModel());
        dialog.setCloseAction(new CloseTradeDialogAction(dialog));
        dialog.display();
    }
}
