package org.freemars.controller.action;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JDialog;
import org.freemars.command.ResetFreeMarsModelCommand;
import org.freemars.controller.FreeMarsController;
import org.freemars.controller.action.file.DisplayMainMenuAction;
import org.freemars.player.PlayerSummaryPanelHelper;
import org.freemars.ui.player.DefeatDialog;

/**
 *
 * @author Deniz ARIKAN
 */
public class DisplayDefeatDialogAction extends AbstractAction {

    private final FreeMarsController controller;
    private PlayerSummaryPanelHelper playerSummaryPanelHelper;

    public DisplayDefeatDialogAction(FreeMarsController controller) {
        this.controller = controller;
    }

    public void actionPerformed(ActionEvent e) {
        DefeatDialog defeatDialog = new DefeatDialog(controller.getCurrentFrame());
        defeatDialog.setPopulationValueLabelText(getPlayerSummaryPanelHelper().getPopulationValue());
        defeatDialog.setMostPopulousColonyValueLabelText(getPlayerSummaryPanelHelper().getMostPopulousColonyValue());
        defeatDialog.setNumberOfColoniesValueLabelText(getPlayerSummaryPanelHelper().getSettlementCountValue());
        defeatDialog.setNumberOfUnitsValueLabelText(getPlayerSummaryPanelHelper().getUnitCountValue());
        defeatDialog.setMapExploredValueLabelText(getPlayerSummaryPanelHelper().getMapExploredValue());
        defeatDialog.setTimeOnMarsValueLabelText(getPlayerSummaryPanelHelper().getTimeOnMarsValue());
        defeatDialog.setWaterProductionValueLabelText(getPlayerSummaryPanelHelper().getRequiredResourceProductionValue(0));
        defeatDialog.setWaterConsumptionValueLabelText(getPlayerSummaryPanelHelper().getRequiredResourceValue(0));
        defeatDialog.setFoodProductionValueLabelText(getPlayerSummaryPanelHelper().getRequiredResourceProductionValue(1));
        defeatDialog.setFoodConsumptionValueLabelText(getPlayerSummaryPanelHelper().getRequiredResourceValue(1));
        defeatDialog.setWealthValueValueLabelText(getPlayerSummaryPanelHelper().getWealthValue());
        defeatDialog.setWealthPerColonistValueLabelText(getPlayerSummaryPanelHelper().getWealthPerColonistValue());
        defeatDialog.setEarthTaxValueLabelText(getPlayerSummaryPanelHelper().getEarthTaxRateValue());
        defeatDialog.setColonialTaxValueLabelText(getPlayerSummaryPanelHelper().getColonialTaxRateValue());
        defeatDialog.setColonialTaxIncomeValueLabelText(getPlayerSummaryPanelHelper().getColonialTaxIncomeValue());
        defeatDialog.setTotalUpkeepValueLabelText(getPlayerSummaryPanelHelper().getTotalUpkeepValue());
        defeatDialog.setIncomeFromExportsValueLabelText(getPlayerSummaryPanelHelper().getIncomeFromExportsValue());
        defeatDialog.setTotalTaxPaidToEarthValueLabelText(getPlayerSummaryPanelHelper().getTotalTaxPaidToEarthValue());
        defeatDialog.setProfitFromExportsValueLabelText(getPlayerSummaryPanelHelper().getProfitFromExportsValue());
        defeatDialog.setFavoriteExportValueLabelText(getPlayerSummaryPanelHelper().getFavoriteExportResourceValue());
        defeatDialog.setMostProfitableExportValueLabelText(getPlayerSummaryPanelHelper().getMostProfitableExportResourceValue());
        defeatDialog.setFarmersValueLabelText(getPlayerSummaryPanelHelper().getNumberOfColonistsOnResource(1));
        defeatDialog.setIronMinersValueLabelText(getPlayerSummaryPanelHelper().getNumberOfColonistsOnResource(6));
        defeatDialog.setSilicaMinersValueLabelText(getPlayerSummaryPanelHelper().getNumberOfColonistsOnResource(8));
        defeatDialog.setMineralMinersValueLabelText(getPlayerSummaryPanelHelper().getNumberOfColonistsOnResource(10));
        defeatDialog.setCloseDialogAndOpenMainMenuButtonAction(new CloseDialogAndOpenMainMenuAction(defeatDialog));
        defeatDialog.display();
    }

    private PlayerSummaryPanelHelper getPlayerSummaryPanelHelper() {
        if (playerSummaryPanelHelper == null) {
            playerSummaryPanelHelper = new PlayerSummaryPanelHelper(controller.getFreeMarsModel().getRealm(), controller.getFreeMarsModel().getActivePlayer());
        }
        return playerSummaryPanelHelper;
    }

    class CloseDialogAndOpenMainMenuAction extends AbstractAction {

        private final JDialog dialog;

        public CloseDialogAndOpenMainMenuAction(JDialog dialog) {
            this.dialog = dialog;
        }

        public void actionPerformed(ActionEvent e) {
            dialog.dispose();
            controller.execute(new ResetFreeMarsModelCommand(controller.getFreeMarsModel()));
            new DisplayMainMenuAction(controller).actionPerformed(e);
        }
    }
}
