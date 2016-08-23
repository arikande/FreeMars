package org.freemars.controller.action;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JDialog;
import org.freemars.command.ResetFreeMarsModelCommand;
import org.freemars.controller.FreeMarsController;
import org.freemars.controller.action.file.DisplayMainMenuAction;
import org.freemars.player.FreeMarsPlayer;
import org.freemars.player.PlayerSummaryPanelHelper;
import org.freemars.ui.player.VictoryDialog;

/**
 *
 * @author Deniz ARIKAN
 */
public class DisplayVictoryDialogAction extends AbstractAction {

    private final FreeMarsController controller;
    private PlayerSummaryPanelHelper playerSummaryPanelHelper;

    public DisplayVictoryDialogAction(FreeMarsController controller) {
        this.controller = controller;
    }

    public void actionPerformed(ActionEvent e) {
        VictoryDialog victoryDialog = new VictoryDialog(controller.getCurrentFrame());

        victoryDialog.setPopulationValueLabelText(getPlayerSummaryPanelHelper().getPopulationValue());
        victoryDialog.setMostPopulousColonyValueLabelText(getPlayerSummaryPanelHelper().getMostPopulousColonyValue());
        victoryDialog.setNumberOfColoniesValueLabelText(getPlayerSummaryPanelHelper().getSettlementCountValue());
        victoryDialog.setNumberOfUnitsValueLabelText(getPlayerSummaryPanelHelper().getUnitCountValue());
        victoryDialog.setMapExploredValueLabelText(getPlayerSummaryPanelHelper().getMapExploredValue());
        victoryDialog.setTimeOnMarsValueLabelText(getPlayerSummaryPanelHelper().getTimeOnMarsValue());
        victoryDialog.setWaterProductionValueLabelText(getPlayerSummaryPanelHelper().getRequiredResourceProductionValue(0));
        victoryDialog.setWaterConsumptionValueLabelText(getPlayerSummaryPanelHelper().getRequiredResourceValue(0));
        victoryDialog.setFoodProductionValueLabelText(getPlayerSummaryPanelHelper().getRequiredResourceProductionValue(1));
        victoryDialog.setFoodConsumptionValueLabelText(getPlayerSummaryPanelHelper().getRequiredResourceValue(1));
        victoryDialog.setWealthValueValueLabelText(getPlayerSummaryPanelHelper().getWealthValue());
        victoryDialog.setWealthPerColonistValueLabelText(getPlayerSummaryPanelHelper().getWealthPerColonistValue());
        victoryDialog.setEarthTaxValueLabelText(getPlayerSummaryPanelHelper().getEarthTaxRateValue());
        victoryDialog.setColonialTaxValueLabelText(getPlayerSummaryPanelHelper().getColonialTaxRateValue());
        victoryDialog.setColonialTaxIncomeValueLabelText(getPlayerSummaryPanelHelper().getColonialTaxIncomeValue());
        victoryDialog.setTotalUpkeepValueLabelText(getPlayerSummaryPanelHelper().getTotalUpkeepValue());
        victoryDialog.setIncomeFromExportsValueLabelText(getPlayerSummaryPanelHelper().getIncomeFromExportsValue());
        victoryDialog.setTotalTaxPaidToEarthValueLabelText(getPlayerSummaryPanelHelper().getTotalTaxPaidToEarthValue());
        victoryDialog.setProfitFromExportsValueLabelText(getPlayerSummaryPanelHelper().getProfitFromExportsValue());
        victoryDialog.setFavoriteExportValueLabelText(getPlayerSummaryPanelHelper().getFavoriteExportResourceValue());
        victoryDialog.setMostProfitableExportValueLabelText(getPlayerSummaryPanelHelper().getMostProfitableExportResourceValue());
        victoryDialog.setFarmersValueLabelText(getPlayerSummaryPanelHelper().getNumberOfColonistsOnResource(1));
        victoryDialog.setIronMinersValueLabelText(getPlayerSummaryPanelHelper().getNumberOfColonistsOnResource(6));
        victoryDialog.setSilicaMinersValueLabelText(getPlayerSummaryPanelHelper().getNumberOfColonistsOnResource(8));
        victoryDialog.setMineralMinersValueLabelText(getPlayerSummaryPanelHelper().getNumberOfColonistsOnResource(10));
        victoryDialog.setCloseDialogAndOpenMainMenuButtonAction(new CloseDialogAndOpenMainMenuAction(victoryDialog));
        victoryDialog.setContinuePlayingButtonAction(new CloseDialogAndContinuePlayingAction(victoryDialog));
        victoryDialog.display();
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

    class CloseDialogAndContinuePlayingAction extends AbstractAction {

        private final JDialog dialog;

        public CloseDialogAndContinuePlayingAction(JDialog dialog) {
            this.dialog = dialog;
        }

        public void actionPerformed(ActionEvent e) {
            ((FreeMarsPlayer) controller.getFreeMarsModel().getHumanPlayer()).setContinuingGameAfterVictory(true);
            dialog.dispose();
        }
    }
}
