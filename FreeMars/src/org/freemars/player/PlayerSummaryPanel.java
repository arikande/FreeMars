package org.freemars.player;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 *
 * @author Deniz ARIKAN
 */
public class PlayerSummaryPanel extends JPanel {

    private JTabbedPane playerSummaryTabbedPane;
    private JPanel societyPanel;
    private JPanel economyPanel;
    private JPanel militaryPanel;
    private JLabel populationLabel;
    private JLabel populationValueLabel;
    private JLabel mostPopulousColonyLabel;
    private JLabel mostPopulousColonyValueLabel;
    private JLabel numberOfColoniesLabel;
    private JLabel numberOfColoniesValueLabel;
    private JLabel numberOfUnitsLabel;
    private JLabel numberOfUnitsValueLabel;
    private JLabel mapExploredLabel;
    private JLabel mapExploredValueLabel;
    private JLabel timeOnMarsLabel;
    private JLabel timeOnMarsValueLabel;
    private JLabel waterProductionLabel;
    private JLabel waterProductionValueLabel;
    private JLabel waterConsumptionLabel;
    private JLabel waterConsumptionValueLabel;
    private JLabel foodProductionLabel;
    private JLabel foodProductionValueLabel;
    private JLabel foodConsumptionLabel;
    private JLabel foodConsumptionValueLabel;
    private JLabel wealthLabel;
    private JLabel wealthValueLabel;
    private JLabel wealthPerColonistLabel;
    private JLabel wealthPerColonistValueLabel;
    private JLabel earthTaxLabel;
    private JLabel earthTaxValueLabel;
    private JLabel colonialTaxLabel;
    private JLabel colonialTaxValueLabel;
    private JLabel colonialTaxIncomeLabel;
    private JLabel colonialTaxIncomeValueLabel;
    private JLabel totalUpkeepLabel;
    private JLabel totalUpkeepValueLabel;
    private JLabel incomeFromExportsLabel;
    private JLabel incomeFromExportsValueLabel;
    private JLabel totalTaxPaidToEarthLabel;
    private JLabel totalTaxPaidToEarthValueLabel;
    private JLabel profitFromExportsLabel;
    private JLabel profitFromExportsValueLabel;
    private JLabel favoriteExportLabel;
    private JLabel favoriteExportValueLabel;
    private JLabel mostProfitableExportLabel;
    private JLabel mostProfitableExportValueLabel;
    private JLabel farmersLabel;
    private JLabel farmersValueLabel;
    private JLabel ironMinersLabel;
    private JLabel ironMinersValueLabel;
    private JLabel silicaMinersLabel;
    private JLabel silicaMinersValueLabel;
    private JLabel mineralMinersLabel;
    private JLabel mineralMinersValueLabel;

    public PlayerSummaryPanel() {
        setLayout(new BorderLayout());
        add(getPlayerSummaryTabbedPane(), BorderLayout.CENTER);
    }

    public void setPopulationValueLabelText(String text) {
        getPopulationValueLabel().setText(text);
    }

    public void setMostPopulousColonyValueLabelText(String text) {
        getMostPopulousColonyValueLabel().setText(text);
    }

    public void setNumberOfColoniesValueLabelText(String text) {
        getNumberOfColoniesValueLabel().setText(text);
    }

    public void setNumberOfUnitsValueLabelText(String text) {
        getNumberOfUnitsValueLabel().setText(text);
    }

    public void setMapExploredValueLabelText(String text) {
        getMapExploredValueLabel().setText(text);
    }

    public void setTimeOnMarsValueLabelText(String text) {
        getTimeOnMarsValueLabel().setText(text);
    }

    public void setWaterProductionValueLabelText(String text) {
        getWaterProductionValueLabel().setText(text);
    }

    public void setWaterConsumptionValueLabelText(String text) {
        getWaterConsumptionValueLabel().setText(text);
    }

    public void setFoodProductionValueLabelText(String text) {
        getFoodProductionValueLabel().setText(text);
    }

    public void setFoodConsumptionValueLabelText(String text) {
        getFoodConsumptionValueLabel().setText(text);
    }

    public void setWealthValueValueLabelText(String text) {
        getWealthValueLabel().setText(text);
    }

    public void setWealthPerColonistValueLabelText(String text) {
        getWealthPerColonistValueLabel().setText(text);
    }

    public void setEarthTaxValueLabelText(String text) {
        getEarthTaxValueLabel().setText(text);
    }

    public void setColonialTaxValueLabelText(String text) {
        getColonialTaxValueLabel().setText(text);
    }

    public void setColonialTaxIncomeValueLabelText(String text) {
        getColonialTaxIncomeValueLabel().setText(text);
    }

    public void setTotalUpkeepValueLabelText(String text) {
        getTotalUpkeepValueLabel().setText(text);
    }

    public void setIncomeFromExportsValueLabelText(String text) {
        getIncomeFromExportsValueLabel().setText(text);
    }

    public void setProfitFromExportsValueLabelText(String text) {
        getProfitFromExportsValueLabel().setText(text);
    }

    public void setTotalTaxPaidToEarthValueLabelText(String text) {
        getTotalTaxPaidToEarthValueLabel().setText(text);
    }

    public void setFavoriteExportValueLabelText(String text) {
        getFavoriteExportValueLabel().setText(text);
    }

    public void setMostProfitableExportValueLabelText(String text) {
        getMostProfitableExportValueLabel().setText(text);
    }

    public void setFarmersValueLabelText(String text) {
        getFarmersValueLabel().setText(text);
    }

    public void setIronMinersValueLabelText(String text) {
        getIronMinersValueLabel().setText(text);
    }

    public void setSilicaMinersValueLabelText(String text) {
        getSilicaMinersValueLabel().setText(text);
    }

    public void setMineralMinersValueLabelText(String text) {
        getMineralMinersValueLabel().setText(text);
    }

    private JTabbedPane getPlayerSummaryTabbedPane() {
        if (playerSummaryTabbedPane == null) {
            playerSummaryTabbedPane = new JTabbedPane();
            playerSummaryTabbedPane.addTab("Society", getSocietyPanel());
            playerSummaryTabbedPane.addTab("Economy", getEconomyPanel());
//            playerSummaryTabbedPane.addTab("Military", getMilitaryPanel());
        }
        return playerSummaryTabbedPane;
    }

    private JPanel getSocietyPanel() {
        if (societyPanel == null) {
            societyPanel = new JPanel(new GridLayout(0, 4));
            societyPanel.add(getPopulationLabel());
            societyPanel.add(getPopulationValueLabel());
            societyPanel.add(getMostPopulousColonyLabel());
            societyPanel.add(getMostPopulousColonyValueLabel());
            societyPanel.add(getNumberOfColoniesLabel());
            societyPanel.add(getNumberOfColoniesValueLabel());
            societyPanel.add(getNumberOfUnitsLabel());
            societyPanel.add(getNumberOfUnitsValueLabel());
            societyPanel.add(getMapExploredLabel());
            societyPanel.add(getMapExploredValueLabel());
            societyPanel.add(getTimeOnMarsLabel());
            societyPanel.add(getTimeOnMarsValueLabel());
            societyPanel.add(getWaterProductionLabel());
            societyPanel.add(getWaterProductionValueLabel());
            societyPanel.add(getWaterConsumptionLabel());
            societyPanel.add(getWaterConsumptionValueLabel());
            societyPanel.add(getFoodProductionLabel());
            societyPanel.add(getFoodProductionValueLabel());
            societyPanel.add(getFoodConsumptionLabel());
            societyPanel.add(getFoodConsumptionValueLabel());
        }
        return societyPanel;
    }

    private JPanel getEconomyPanel() {
        if (economyPanel == null) {
            economyPanel = new JPanel(new GridLayout(0, 4));
            economyPanel.add(getWealthLabel());
            economyPanel.add(getWealthValueLabel());
            economyPanel.add(getWealthPerColonistLabel());
            economyPanel.add(getWealthPerColonistValueLabel());
            economyPanel.add(getEarthTaxLabel());
            economyPanel.add(getEarthTaxValueLabel());
            economyPanel.add(getColonialTaxLabel());
            economyPanel.add(getColonialTaxValueLabel());
            economyPanel.add(getColonialTaxIncomeLabel());
            economyPanel.add(getColonialTaxIncomeValueLabel());
            economyPanel.add(getTotalUpkeepLabel());
            economyPanel.add(getTotalUpkeepValueLabel());
            economyPanel.add(getIncomeFromExportsLabel());
            economyPanel.add(getIncomeFromExportsValueLabel());
            economyPanel.add(getTotalTaxPaidToEarthLabel());
            economyPanel.add(getTotalTaxPaidToEarthValueLabel());
            economyPanel.add(getProfitFromExportsLabel());
            economyPanel.add(getProfitFromExportsValueLabel());
            economyPanel.add(new JLabel());
            economyPanel.add(new JLabel());
            economyPanel.add(getFavoriteExportLabel());
            economyPanel.add(getFavoriteExportValueLabel());
            economyPanel.add(getMostProfitableExportLabel());
            economyPanel.add(getMostProfitableExportValueLabel());
            economyPanel.add(getFarmersLabel());
            economyPanel.add(getFarmersValueLabel());
            economyPanel.add(getIronMinersLabel());
            economyPanel.add(getIronMinersValueLabel());
            economyPanel.add(getSilicaMinersLabel());
            economyPanel.add(getSilicaMinersValueLabel());
            economyPanel.add(getMineralMinersLabel());
            economyPanel.add(getMineralMinersValueLabel());
        }
        return economyPanel;
    }

    private JPanel getMilitaryPanel() {
        if (militaryPanel == null) {
            militaryPanel = new JPanel();
        }
        return militaryPanel;
    }

    private JLabel getPopulationLabel() {
        if (populationLabel == null) {
            populationLabel = new JLabel("Population : ");
        }
        return populationLabel;
    }

    private JLabel getPopulationValueLabel() {
        if (populationValueLabel == null) {
            populationValueLabel = new JLabel();
        }
        return populationValueLabel;
    }

    private JLabel getMostPopulousColonyLabel() {
        if (mostPopulousColonyLabel == null) {
            mostPopulousColonyLabel = new JLabel("Most populous colony : ");
        }
        return mostPopulousColonyLabel;
    }

    private JLabel getMostPopulousColonyValueLabel() {
        if (mostPopulousColonyValueLabel == null) {
            mostPopulousColonyValueLabel = new JLabel();
        }
        return mostPopulousColonyValueLabel;
    }

    private JLabel getNumberOfColoniesLabel() {
        if (numberOfColoniesLabel == null) {
            numberOfColoniesLabel = new JLabel("Number of colonies : ");
        }
        return numberOfColoniesLabel;
    }

    private JLabel getNumberOfColoniesValueLabel() {
        if (numberOfColoniesValueLabel == null) {
            numberOfColoniesValueLabel = new JLabel();
        }
        return numberOfColoniesValueLabel;
    }

    private JLabel getNumberOfUnitsLabel() {
        if (numberOfUnitsLabel == null) {
            numberOfUnitsLabel = new JLabel("Number of units : ");
        }
        return numberOfUnitsLabel;
    }

    private JLabel getNumberOfUnitsValueLabel() {
        if (numberOfUnitsValueLabel == null) {
            numberOfUnitsValueLabel = new JLabel();
        }
        return numberOfUnitsValueLabel;
    }

    private JLabel getMapExploredLabel() {
        if (mapExploredLabel == null) {
            mapExploredLabel = new JLabel("Map explored : ");
        }
        return mapExploredLabel;
    }

    private JLabel getMapExploredValueLabel() {
        if (mapExploredValueLabel == null) {
            mapExploredValueLabel = new JLabel();
        }
        return mapExploredValueLabel;
    }

    private JLabel getTimeOnMarsLabel() {
        if (timeOnMarsLabel == null) {
            timeOnMarsLabel = new JLabel("Time on Mars : ");
        }
        return timeOnMarsLabel;
    }

    private JLabel getTimeOnMarsValueLabel() {
        if (timeOnMarsValueLabel == null) {
            timeOnMarsValueLabel = new JLabel();
        }
        return timeOnMarsValueLabel;
    }

    private JLabel getWaterProductionLabel() {
        if (waterProductionLabel == null) {
            waterProductionLabel = new JLabel("Water production : ");
        }
        return waterProductionLabel;
    }

    private JLabel getWaterProductionValueLabel() {
        if (waterProductionValueLabel == null) {
            waterProductionValueLabel = new JLabel();
        }
        return waterProductionValueLabel;
    }

    private JLabel getWaterConsumptionLabel() {
        if (waterConsumptionLabel == null) {
            waterConsumptionLabel = new JLabel("Water consumption : ");
        }
        return waterConsumptionLabel;
    }

    private JLabel getWaterConsumptionValueLabel() {
        if (waterConsumptionValueLabel == null) {
            waterConsumptionValueLabel = new JLabel();
        }
        return waterConsumptionValueLabel;
    }

    private JLabel getFoodProductionLabel() {
        if (foodProductionLabel == null) {
            foodProductionLabel = new JLabel("Food production : ");
        }
        return foodProductionLabel;
    }

    private JLabel getFoodProductionValueLabel() {
        if (foodProductionValueLabel == null) {
            foodProductionValueLabel = new JLabel();
        }
        return foodProductionValueLabel;
    }

    private JLabel getFoodConsumptionLabel() {
        if (foodConsumptionLabel == null) {
            foodConsumptionLabel = new JLabel("Food consumption : ");
        }
        return foodConsumptionLabel;
    }

    private JLabel getFoodConsumptionValueLabel() {
        if (foodConsumptionValueLabel == null) {
            foodConsumptionValueLabel = new JLabel();
        }
        return foodConsumptionValueLabel;
    }

    private JLabel getWealthLabel() {
        if (wealthLabel == null) {
            wealthLabel = new JLabel("Wealth : ");
        }
        return wealthLabel;
    }

    private JLabel getWealthValueLabel() {
        if (wealthValueLabel == null) {
            wealthValueLabel = new JLabel();
        }
        return wealthValueLabel;
    }

    private JLabel getWealthPerColonistLabel() {
        if (wealthPerColonistLabel == null) {
            wealthPerColonistLabel = new JLabel("Wealth per colonist : ");
        }
        return wealthPerColonistLabel;
    }

    private JLabel getWealthPerColonistValueLabel() {
        if (wealthPerColonistValueLabel == null) {
            wealthPerColonistValueLabel = new JLabel();
        }
        return wealthPerColonistValueLabel;
    }

    private JLabel getEarthTaxLabel() {
        if (earthTaxLabel == null) {
            earthTaxLabel = new JLabel("Earth tax : ");
        }
        return earthTaxLabel;
    }

    private JLabel getEarthTaxValueLabel() {
        if (earthTaxValueLabel == null) {
            earthTaxValueLabel = new JLabel();
        }
        return earthTaxValueLabel;
    }

    private JLabel getColonialTaxLabel() {
        if (colonialTaxLabel == null) {
            colonialTaxLabel = new JLabel("Colonial tax : ");
        }
        return colonialTaxLabel;
    }

    private JLabel getColonialTaxValueLabel() {
        if (colonialTaxValueLabel == null) {
            colonialTaxValueLabel = new JLabel();
        }
        return colonialTaxValueLabel;
    }

    private JLabel getColonialTaxIncomeLabel() {
        if (colonialTaxIncomeLabel == null) {
            colonialTaxIncomeLabel = new JLabel("Colonial tax income : ");
        }
        return colonialTaxIncomeLabel;
    }

    private JLabel getColonialTaxIncomeValueLabel() {
        if (colonialTaxIncomeValueLabel == null) {
            colonialTaxIncomeValueLabel = new JLabel();
        }
        return colonialTaxIncomeValueLabel;
    }

    private JLabel getTotalUpkeepLabel() {
        if (totalUpkeepLabel == null) {
            totalUpkeepLabel = new JLabel("Total upkeep : ");
        }
        return totalUpkeepLabel;
    }

    private JLabel getTotalUpkeepValueLabel() {
        if (totalUpkeepValueLabel == null) {
            totalUpkeepValueLabel = new JLabel();
        }
        return totalUpkeepValueLabel;
    }

    private JLabel getIncomeFromExportsLabel() {
        if (incomeFromExportsLabel == null) {
            incomeFromExportsLabel = new JLabel("Income from exports : ");
        }
        return incomeFromExportsLabel;
    }

    private JLabel getIncomeFromExportsValueLabel() {
        if (incomeFromExportsValueLabel == null) {
            incomeFromExportsValueLabel = new JLabel();
        }
        return incomeFromExportsValueLabel;
    }

    private JLabel getTotalTaxPaidToEarthLabel() {
        if (totalTaxPaidToEarthLabel == null) {
            totalTaxPaidToEarthLabel = new JLabel("Total tax paid to Earth : ");
        }
        return totalTaxPaidToEarthLabel;
    }

    private JLabel getTotalTaxPaidToEarthValueLabel() {
        if (totalTaxPaidToEarthValueLabel == null) {
            totalTaxPaidToEarthValueLabel = new JLabel();
        }
        return totalTaxPaidToEarthValueLabel;
    }

    private JLabel getProfitFromExportsLabel() {
        if (profitFromExportsLabel == null) {
            profitFromExportsLabel = new JLabel("Profit from exports : ");
        }
        return profitFromExportsLabel;
    }

    private JLabel getProfitFromExportsValueLabel() {
        if (profitFromExportsValueLabel == null) {
            profitFromExportsValueLabel = new JLabel();
        }
        return profitFromExportsValueLabel;
    }

    private JLabel getFavoriteExportLabel() {
        if (favoriteExportLabel == null) {
            favoriteExportLabel = new JLabel("Favorite export : ");
        }
        return favoriteExportLabel;
    }

    private JLabel getFavoriteExportValueLabel() {
        if (favoriteExportValueLabel == null) {
            favoriteExportValueLabel = new JLabel();
        }
        return favoriteExportValueLabel;
    }

    private JLabel getMostProfitableExportLabel() {
        if (mostProfitableExportLabel == null) {
            mostProfitableExportLabel = new JLabel("Most profitable export : ");
        }
        return mostProfitableExportLabel;
    }

    private JLabel getMostProfitableExportValueLabel() {
        if (mostProfitableExportValueLabel == null) {
            mostProfitableExportValueLabel = new JLabel();
        }
        return mostProfitableExportValueLabel;
    }

    private JLabel getFarmersLabel() {
        if (farmersLabel == null) {
            farmersLabel = new JLabel("Farmers : ");
        }
        return farmersLabel;
    }

    private JLabel getFarmersValueLabel() {
        if (farmersValueLabel == null) {
            farmersValueLabel = new JLabel();
        }
        return farmersValueLabel;
    }

    private JLabel getIronMinersLabel() {
        if (ironMinersLabel == null) {
            ironMinersLabel = new JLabel("Iron miners : ");
        }
        return ironMinersLabel;
    }

    private JLabel getIronMinersValueLabel() {
        if (ironMinersValueLabel == null) {
            ironMinersValueLabel = new JLabel();
        }
        return ironMinersValueLabel;
    }

    private JLabel getSilicaMinersLabel() {
        if (silicaMinersLabel == null) {
            silicaMinersLabel = new JLabel("Silica miners : ");
        }
        return silicaMinersLabel;
    }

    private JLabel getSilicaMinersValueLabel() {
        if (silicaMinersValueLabel == null) {
            silicaMinersValueLabel = new JLabel();
        }
        return silicaMinersValueLabel;
    }

    private JLabel getMineralMinersLabel() {
        if (mineralMinersLabel == null) {
            mineralMinersLabel = new JLabel("Mineral miners : ");
        }
        return mineralMinersLabel;
    }

    private JLabel getMineralMinersValueLabel() {
        if (mineralMinersValueLabel == null) {
            mineralMinersValueLabel = new JLabel();
        }
        return mineralMinersValueLabel;
    }
}
