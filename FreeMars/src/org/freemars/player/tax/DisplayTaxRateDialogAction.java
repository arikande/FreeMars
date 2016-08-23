package org.freemars.player.tax;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import org.freemars.controller.FreeMarsController;
import org.freemars.controller.action.DisplayHelpContentsAction;
import org.freerealm.player.Player;

/**
 *
 * @author Deniz ARIKAN
 */
public class DisplayTaxRateDialogAction extends AbstractAction {

    private final FreeMarsController controller;

    public DisplayTaxRateDialogAction(FreeMarsController controller) {
        super("Colony tax rate");
        this.controller = controller;
    }

    public void actionPerformed(ActionEvent e) {
        Player player = controller.getFreeMarsModel().getActivePlayer();
        TaxRateDialog taxRateDialog = new TaxRateDialog(controller.getCurrentFrame());
        taxRateDialog.setTaxRateValue(String.valueOf(player.getTaxRate()));
        taxRateDialog.setIncomeForSelectedTaxValue(String.valueOf(player.getTotalIncomeIf(player.getTaxRate())));
        taxRateDialog.setTotalExpensesValue(String.valueOf(player.getTotalExpenses()));
        int netIncome = player.getTotalIncome() - player.getTotalExpenses();
        taxRateDialog.setNetIncomeValue(String.valueOf(netIncome));
        if (netIncome < 0) {
            taxRateDialog.setNetIncomeColor(Color.red);
        } else {
            taxRateDialog.setNetIncomeColor(new Color(40, 150, 40));
        }
        taxRateDialog.setTaxRateSliderValue(player.getTaxRate());
        taxRateDialog.setAcceptButtonAction(new AcceptTaxRateAction(controller, player, taxRateDialog));
        taxRateDialog.addTaxRateSliderChangeListener(new TaxRateSliderChangeListener(player, taxRateDialog));
        assignHelpKeyStroke(taxRateDialog);
        taxRateDialog.display();
    }

    private void assignHelpKeyStroke(TaxRateDialog taxRateDialog) {
        ActionListener actionListener = new ActionListener() {

            public void actionPerformed(ActionEvent actionEvent) {
                new DisplayHelpContentsAction(controller, "Government.Colony_tax").actionPerformed(actionEvent);
            }
        };
        KeyStroke stroke = KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0);
        taxRateDialog.getRootPane().registerKeyboardAction(actionListener, stroke, JComponent.WHEN_IN_FOCUSED_WINDOW);
    }
}
