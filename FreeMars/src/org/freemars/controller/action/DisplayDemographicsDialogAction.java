package org.freemars.controller.action;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.freemars.controller.FreeMarsController;
import org.freemars.player.PlayerSummaryPanelHelper;
import org.freemars.ui.player.DemographicsDialog;

/**
 *
 * @author Deniz ARIKAN
 */
public class DisplayDemographicsDialogAction extends AbstractAction {

    private final FreeMarsController controller;
    private PlayerSummaryPanelHelper playerSummaryPanelHelper;

    public DisplayDemographicsDialogAction(FreeMarsController controller) {
        super("Demographics");
        this.controller = controller;
    }

    public void actionPerformed(ActionEvent e) {
        DemographicsDialog demographicsDialog = new DemographicsDialog(controller.getCurrentFrame());
        demographicsDialog.setPopulationValueLabelText(getPlayerSummaryPanelHelper().getPopulationValue());
        demographicsDialog.setMostPopulousColonyValueLabelText(getPlayerSummaryPanelHelper().getMostPopulousColonyValue());
        demographicsDialog.setNumberOfColoniesValueLabelText(getPlayerSummaryPanelHelper().getSettlementCountValue());
        demographicsDialog.setNumberOfUnitsValueLabelText(getPlayerSummaryPanelHelper().getUnitCountValue());
        demographicsDialog.setMapExploredValueLabelText(getPlayerSummaryPanelHelper().getMapExploredValue());
        demographicsDialog.setTimeOnMarsValueLabelText(getPlayerSummaryPanelHelper().getTimeOnMarsValue());
        demographicsDialog.setWaterProductionValueLabelText(getPlayerSummaryPanelHelper().getRequiredResourceProductionValue(0));
        demographicsDialog.setWaterConsumptionValueLabelText(getPlayerSummaryPanelHelper().getRequiredResourceValue(0));
        demographicsDialog.setFoodProductionValueLabelText(getPlayerSummaryPanelHelper().getRequiredResourceProductionValue(1));
        demographicsDialog.setFoodConsumptionValueLabelText(getPlayerSummaryPanelHelper().getRequiredResourceValue(1));
        demographicsDialog.setWealthValueValueLabelText(getPlayerSummaryPanelHelper().getWealthValue());
        demographicsDialog.setWealthPerColonistValueLabelText(getPlayerSummaryPanelHelper().getWealthPerColonistValue());
        demographicsDialog.setEarthTaxValueLabelText(getPlayerSummaryPanelHelper().getEarthTaxRateValue());
        demographicsDialog.setColonialTaxValueLabelText(getPlayerSummaryPanelHelper().getColonialTaxRateValue());
        demographicsDialog.setColonialTaxIncomeValueLabelText(getPlayerSummaryPanelHelper().getColonialTaxIncomeValue());
        demographicsDialog.setTotalUpkeepValueLabelText(getPlayerSummaryPanelHelper().getTotalUpkeepValue());
        demographicsDialog.setIncomeFromExportsValueLabelText(getPlayerSummaryPanelHelper().getIncomeFromExportsValue());
        demographicsDialog.setTotalTaxPaidToEarthValueLabelText(getPlayerSummaryPanelHelper().getTotalTaxPaidToEarthValue());
        demographicsDialog.setProfitFromExportsValueLabelText(getPlayerSummaryPanelHelper().getProfitFromExportsValue());
        demographicsDialog.setFavoriteExportValueLabelText(getPlayerSummaryPanelHelper().getFavoriteExportResourceValue());
        demographicsDialog.setMostProfitableExportValueLabelText(getPlayerSummaryPanelHelper().getMostProfitableExportResourceValue());
        demographicsDialog.setFarmersValueLabelText(getPlayerSummaryPanelHelper().getNumberOfColonistsOnResource(1));
        demographicsDialog.setIronMinersValueLabelText(getPlayerSummaryPanelHelper().getNumberOfColonistsOnResource(6));
        demographicsDialog.setSilicaMinersValueLabelText(getPlayerSummaryPanelHelper().getNumberOfColonistsOnResource(8));
        demographicsDialog.setMineralMinersValueLabelText(getPlayerSummaryPanelHelper().getNumberOfColonistsOnResource(10));
        demographicsDialog.display();
    }

    private PlayerSummaryPanelHelper getPlayerSummaryPanelHelper() {
        if (playerSummaryPanelHelper == null) {
            playerSummaryPanelHelper = new PlayerSummaryPanelHelper(controller.getFreeMarsModel().getRealm(), controller.getFreeMarsModel().getActivePlayer());
        }
        return playerSummaryPanelHelper;
    }
}
