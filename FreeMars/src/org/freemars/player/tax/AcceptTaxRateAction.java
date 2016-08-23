package org.freemars.player.tax;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.freemars.controller.FreeMarsController;
import org.freerealm.command.SetTaxRateCommand;
import org.freerealm.player.Player;

/**
 *
 * @author Deniz ARIKAN
 */
public class AcceptTaxRateAction extends AbstractAction {

    private final FreeMarsController controller;
    private final Player player;
    private final TaxRateDialog taxRateDialog;

    public AcceptTaxRateAction(FreeMarsController controller, Player player, TaxRateDialog taxRateDialog) {
        super("Accept");
        this.controller = controller;
        this.player = player;
        this.taxRateDialog = taxRateDialog;
    }

    public void actionPerformed(ActionEvent e) {
        controller.execute(new SetTaxRateCommand(player, taxRateDialog.getTaxRateSliderValue()));
        taxRateDialog.dispose();
    }
}
