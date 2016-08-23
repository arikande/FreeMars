package org.freemars.player.tax;

import java.awt.Color;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.freerealm.player.Player;

/**
 *
 * @author Deniz ARIKAN
 */
public class TaxRateSliderChangeListener implements ChangeListener {

    private final TaxRateDialog dialog;
    private final Player player;

    public TaxRateSliderChangeListener(Player player, TaxRateDialog dialog) {
        this.dialog = dialog;
        this.player = player;
    }

    public void stateChanged(ChangeEvent e) {
        dialog.setTaxRateValue(String.valueOf(dialog.getTaxRateSliderValue()));
        dialog.setIncomeForSelectedTaxValue(String.valueOf(player.getTotalIncomeIf(dialog.getTaxRateSliderValue())));
        int netIncome = player.getTotalIncomeIf(dialog.getTaxRateSliderValue()) - player.getTotalExpenses();
        dialog.setNetIncomeValue(String.valueOf(netIncome));
        if (netIncome < 0) {
            dialog.setNetIncomeColor(Color.red);
        } else {
            dialog.setNetIncomeColor(new Color(40, 150, 40));
        }
    }
}
