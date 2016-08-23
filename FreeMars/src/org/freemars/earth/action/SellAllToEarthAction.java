package org.freemars.earth.action;

import java.awt.event.ActionEvent;
import java.text.DecimalFormat;
import java.util.Iterator;
import javax.swing.AbstractAction;
import org.freemars.controller.FreeMarsController;
import org.freemars.controller.viewcommand.PlaySoundCommand;
import org.freemars.earth.Earth;
import org.freemars.earth.command.SellResourceToEarthCommand;
import org.freemars.earth.ui.EarthDialog;
import org.freemars.player.FreeMarsPlayer;
import org.freemars.ui.util.FreeMarsOptionPane;
import org.freerealm.executor.CommandResult;
import org.freerealm.resource.Resource;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class SellAllToEarthAction extends AbstractAction {

    private final FreeMarsController freeMarsController;
    private final EarthDialog earthDialog;
    private final Unit unit;

    public SellAllToEarthAction(FreeMarsController freeMarsController, EarthDialog earthDialog, Unit unit) {
        this.freeMarsController = freeMarsController;
        this.earthDialog = earthDialog;
        this.unit = unit;
    }

    public void actionPerformed(ActionEvent e) {
        FreeMarsPlayer player = (FreeMarsPlayer) unit.getPlayer();
        if (player.hasDeclaredIndependence()) {
            FreeMarsOptionPane.showMessageDialog(earthDialog, "We do not buy from rebels!", "Rejected");
        } else {
            Iterator<Resource> iterator = unit.getContainedResourcesIterator();
            boolean salesDone = false;
            while (iterator.hasNext()) {
                Resource resource = iterator.next();
                int quantity = unit.getResourceQuantity(resource);
                if (quantity > 0) {
                    CommandResult result = freeMarsController.execute(new SellResourceToEarthCommand(freeMarsController, unit, resource, quantity));
                    if (result.getCode() == CommandResult.RESULT_OK) {
                        salesDone = true;
                        int totalPrice = ((Integer) result.getParameter("total_price"));
                        int taxRate = (Integer) result.getParameter("tax_rate");
                        int taxAmount = (Integer) result.getParameter("tax_amount");
                        int netIncome = (Integer) result.getParameter("net_income");
                        String formattedQuantity = new DecimalFormat().format(quantity);
                        String formattedTotalPrice = new DecimalFormat().format(totalPrice);
                        earthDialog.addUnitInfoText(formattedQuantity + " " + resource.getName() + " sold for " + formattedTotalPrice + "\n");
                        if (taxAmount > 0) {
                            String formattedTaxAmount = new DecimalFormat().format(taxAmount);
                            earthDialog.addUnitInfoText("Colonial tax : -" + formattedTaxAmount + " (" + taxRate + "%)\n");
                            String formattedNetIncome = new DecimalFormat().format(netIncome);
                            earthDialog.addUnitInfoText("Net income : " + formattedNetIncome + "\n");
                        }
                        String formattedWealth = new DecimalFormat().format(unit.getPlayer().getWealth());
                        earthDialog.addUnitInfoText("New treasury : " + formattedWealth + " " + freeMarsController.getFreeMarsModel().getRealm().getProperty("currency_unit") + "\n\n");
                    }
                }
            }
            if (salesDone) {
                earthDialog.update(Earth.BUY_SELL_UPDATE);
                boolean wealthTransferSound = Boolean.valueOf(freeMarsController.getFreeMarsModel().getFreeMarsPreferences().getProperty("wealth_transfer_sound"));
                if (wealthTransferSound) {
                    freeMarsController.executeViewCommandImmediately(new PlaySoundCommand("sound/wealth_transfer.wav"));
                }
            }
        }
    }
}
