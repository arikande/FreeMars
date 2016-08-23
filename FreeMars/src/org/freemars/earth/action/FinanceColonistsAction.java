package org.freemars.earth.action;

import java.awt.event.ActionEvent;
import java.text.DecimalFormat;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import org.freemars.controller.FreeMarsController;
import org.freemars.earth.Earth;
import org.freemars.earth.command.FinanceColonistsCommand;
import org.freemars.earth.ui.EarthDialog;
import org.freemars.player.FreeMarsPlayer;
import org.freemars.ui.util.FreeMarsOptionPane;
import org.freerealm.Utility;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class FinanceColonistsAction extends AbstractAction {

    private final FreeMarsController freeMarsController;
    private final EarthDialog earthDialog;
    private final int colonistCount;

    public FinanceColonistsAction(FreeMarsController freeMarsController, EarthDialog earthDialog, int colonistCount) {
        super(String.valueOf(colonistCount));
        this.freeMarsController = freeMarsController;
        this.earthDialog = earthDialog;
        this.colonistCount = colonistCount;
    }

    public void actionPerformed(ActionEvent e) {

        FreeMarsPlayer player = (FreeMarsPlayer) freeMarsController.getFreeMarsModel().getRealm().getPlayerManager().getActivePlayer();
        if (player.hasDeclaredIndependence()) {
            FreeMarsOptionPane.showMessageDialog(earthDialog, "Rebels can not finance colonists!", "Rejected");
        } else {

            int financeCostPerColonist = Integer.parseInt(freeMarsController.getFreeMarsModel().getRealm().getProperty("finance_cost_per_colonist"));
            int totalCost = colonistCount * financeCostPerColonist;
            totalCost = (int) Utility.modifyByPercent(totalCost, ((FreeMarsPlayer) freeMarsController.getFreeMarsModel().getActivePlayer()).getFinanceColonizerCostModifier());
            String totalCostString = new DecimalFormat().format(totalCost);
            Object[] options = {"Yes", "No, thanks"};
            int value = JOptionPane.showOptionDialog(earthDialog,
                    "Finance " + colonistCount + " colonists for " + totalCostString + " credits?",
                    "Finance colonists",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[1]);
            if (value == JOptionPane.YES_OPTION) {
                Unit unit = freeMarsController.getFreeMarsModel().getEarth().getEarthDialogSelectedUnit();
                freeMarsController.execute(new FinanceColonistsCommand(freeMarsController.getFreeMarsModel(), unit, colonistCount));
                earthDialog.addUnitInfoText(colonistCount + " colonists has boarded " + unit + "\n\n");
                earthDialog.update(Earth.BUY_SELL_UPDATE);
            }
        }
    }
}
