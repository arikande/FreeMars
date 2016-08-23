package org.freemars.ui.player;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import org.freechart.line.LineChartModel;
import org.freechart.line.LineChartPanel;
import org.freemars.model.FreeMarsModel;
import org.freemars.ui.util.FreeMarsDialog;
import org.freerealm.history.PlayerHistory;
import org.freerealm.history.PlayerTurnHistory;

/**
 *
 * @author Deniz ARIKAN
 */
public class StatisticsDialog extends FreeMarsDialog {

    public static final int POPULATION_STATISTICS_TAB = 0;
    public static final int WEALTH_STATISTICS_TAB = 1;
    public static final int EARTH_TAX_RATE_STATISTICS_TAB = 2;
    public static final int COLONY_COUNT_STATISTICS_TAB = 3;
    public static final int UNIT_COUNT_STATISTICS_TAB = 4;
    public static final int MAP_EXPLORATION_STATISTICS_TAB = 5;
    private static final int X_AXIS_DIVISOR = 20;
    private final int FRAME_WIDTH = 850;
    private final int FRAME_HEIGHT = 650;
    private final FreeMarsModel model;
    private JTabbedPane statisticsTabbedPane;
    private JPanel populationStatisticsPanel;
    private LineChartPanel populationStatisticsChartPanel;
    private LineChartModel populationStatisticsLineChartModel;
    private JPanel treasuryStatisticsPanel;
    private LineChartPanel treasuryStatisticsChartPanel;
    private LineChartModel treasuryStatisticsLineChartModel;
    private JPanel earthTaxStatisticsPanel;
    private LineChartPanel earthTaxStatisticsChartPanel;
    private LineChartModel earthTaxStatisticsLineChartModel;
    private JPanel settlementCountStatisticsPanel;
    private LineChartPanel settlementCountStatisticsChartPanel;
    private LineChartModel settlementCountStatisticsLineChartModel;
    private JPanel unitCountStatisticsPanel;
    private LineChartPanel unitCountStatisticsChartPanel;
    private LineChartModel unitCountStatisticsLineChartModel;
    private JPanel mapExplorationStatisticsPanel;
    private LineChartPanel mapExplorationStatisticsChartPanel;
    private LineChartModel mapExplorationStatisticsLineChartModel;

    public StatisticsDialog(JFrame parent, FreeMarsModel model) {
        super(parent);
        setModal(true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setTitle("Statistics");
        this.model = model;
    }

    public void display(int tabIndex) {
        getContentPane().setLayout(new BorderLayout(4, 4));
        initializeWidgets();
        pack();
        final java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width - FRAME_WIDTH) / 2, (screenSize.height - FRAME_HEIGHT) / 2, FRAME_WIDTH, FRAME_HEIGHT);
        getStatisticsTabbedPane().setSelectedIndex(tabIndex);
        setVisible(true);
        toFront();
    }

    private void initializeWidgets() {
        add(getStatisticsTabbedPane(), BorderLayout.CENTER);
    }

    private JTabbedPane getStatisticsTabbedPane() {
        if (statisticsTabbedPane == null) {
            statisticsTabbedPane = new JTabbedPane();
            statisticsTabbedPane.addTab("Population", getPopulationStatisticsPanel());
            statisticsTabbedPane.addTab("Treasury", getTreasuryStatisticsPanel());
            statisticsTabbedPane.addTab("Earth tax", getEarthTaxStatisticsPanel());
            statisticsTabbedPane.addTab("Colony", getSettlementCountStatisticsPanel());
            statisticsTabbedPane.addTab("Unit", getUnitCountStatisticsPanel());
            statisticsTabbedPane.addTab("Exploration percent", getMapExplorationStatisticsPanel());
        }
        return statisticsTabbedPane;
    }

    private JPanel getPopulationStatisticsPanel() {
        if (populationStatisticsPanel == null) {
            populationStatisticsPanel = new JPanel(new BorderLayout());
            populationStatisticsPanel.add(getPopulationStatisticsChartPanel(), BorderLayout.CENTER);
        }
        return populationStatisticsPanel;
    }

    private JPanel getTreasuryStatisticsPanel() {
        if (treasuryStatisticsPanel == null) {
            treasuryStatisticsPanel = new JPanel(new BorderLayout());
            treasuryStatisticsPanel.add(getTreasuryStatisticsChartPanel(), BorderLayout.CENTER);
        }
        return treasuryStatisticsPanel;
    }

    private JPanel getEarthTaxStatisticsPanel() {
        if (earthTaxStatisticsPanel == null) {
            earthTaxStatisticsPanel = new JPanel(new BorderLayout());
            earthTaxStatisticsPanel.add(getEarthTaxStatisticsChartPanel(), BorderLayout.CENTER);
        }
        return earthTaxStatisticsPanel;
    }

    private LineChartPanel getPopulationStatisticsChartPanel() {
        if (populationStatisticsChartPanel == null) {
            populationStatisticsChartPanel = new LineChartPanel(getPopulationStatisticsLineChartModel());
        }
        return populationStatisticsChartPanel;
    }

    private LineChartModel getPopulationStatisticsLineChartModel() {
        if (populationStatisticsLineChartModel == null) {
            populationStatisticsLineChartModel = new LineChartModel();
            populationStatisticsLineChartModel.setMinimumXValue(0);
            populationStatisticsLineChartModel.setMaximumXValue(model.getNumberOfTurns() - 1);
            int divisionCount = new Double((model.getNumberOfTurns() - 1) / Math.ceil((double) model.getNumberOfTurns() / X_AXIS_DIVISOR)).intValue();
            populationStatisticsLineChartModel.setXDivisionCount(divisionCount);
            populationStatisticsLineChartModel.setMinimumYValue(0);
            populationStatisticsLineChartModel.setMaximumYValue(getMaximumPopulation() + 50);
            populationStatisticsLineChartModel.setYDivisionCount(20);
            populationStatisticsLineChartModel.setYAxisName("Colonists");
            populationStatisticsLineChartModel.setXAxisName("Turns");
            populationStatisticsLineChartModel.setAxisNameFont(new Font("Arial", 1, 14));
            PlayerHistory playerHistory = model.getRealm().getHistory().getPlayerHistory(model.getActivePlayer());
            for (int i = 0; i < model.getNumberOfTurns(); i++) {
                PlayerTurnHistory playerTurnHistory = playerHistory.getTurnHistory(i);
                populationStatisticsLineChartModel.addValue(i, playerTurnHistory.getPopulation());
            }
        }
        return populationStatisticsLineChartModel;
    }

    private int getMaximumPopulation() {
        int maximumPopulation = 0;
        PlayerHistory playerHistory = model.getRealm().getHistory().getPlayerHistory(model.getActivePlayer());
        for (int i = 0; i < model.getNumberOfTurns(); i++) {
            PlayerTurnHistory playerTurnHistory = playerHistory.getTurnHistory(i);
            if (playerTurnHistory.getPopulation() > maximumPopulation) {
                maximumPopulation = playerTurnHistory.getPopulation();
            }
        }
        return maximumPopulation;
    }

    private LineChartPanel getTreasuryStatisticsChartPanel() {
        if (treasuryStatisticsChartPanel == null) {
            treasuryStatisticsChartPanel = new LineChartPanel(getTreasuryStatisticsLineChartModel());
        }
        return treasuryStatisticsChartPanel;
    }

    private LineChartModel getTreasuryStatisticsLineChartModel() {
        if (treasuryStatisticsLineChartModel == null) {
            treasuryStatisticsLineChartModel = new LineChartModel();
            treasuryStatisticsLineChartModel.setMinimumXValue(0);
            treasuryStatisticsLineChartModel.setMaximumXValue(model.getNumberOfTurns() - 1);
            int divisionCount = new Double((model.getNumberOfTurns() - 1) / Math.ceil((double) model.getNumberOfTurns() / X_AXIS_DIVISOR)).intValue();
            treasuryStatisticsLineChartModel.setXDivisionCount(divisionCount);
            treasuryStatisticsLineChartModel.setMinimumYValue(0);
            treasuryStatisticsLineChartModel.setMaximumYValue(getMaximumWealth() + 50);
            if (getMaximumWealth() + 50 >= 100000) {
                treasuryStatisticsLineChartModel.setXOffset(55);
            }
            treasuryStatisticsLineChartModel.setYDivisionCount(10);
            treasuryStatisticsLineChartModel.setYAxisName("Credits");
            treasuryStatisticsLineChartModel.setXAxisName("Turns");
            treasuryStatisticsLineChartModel.setAxisNameFont(new Font("Arial", 1, 14));
            PlayerHistory playerHistory = model.getRealm().getHistory().getPlayerHistory(model.getActivePlayer());
            for (int i = 0; i < model.getNumberOfTurns(); i++) {
                PlayerTurnHistory playerTurnHistory = playerHistory.getTurnHistory(i);
                treasuryStatisticsLineChartModel.addValue(i, playerTurnHistory.getWealth());
            }
        }
        return treasuryStatisticsLineChartModel;
    }

    private int getMaximumWealth() {
        int maximumWealth = 0;
        PlayerHistory playerHistory = model.getRealm().getHistory().getPlayerHistory(model.getActivePlayer());
        for (int i = 0; i < model.getNumberOfTurns(); i++) {
            PlayerTurnHistory playerTurnHistory = playerHistory.getTurnHistory(i);
            if (playerTurnHistory.getWealth() > maximumWealth) {
                maximumWealth = playerTurnHistory.getWealth();
            }
        }
        return maximumWealth;
    }

    private LineChartPanel getEarthTaxStatisticsChartPanel() {
        if (earthTaxStatisticsChartPanel == null) {
            earthTaxStatisticsChartPanel = new LineChartPanel(getEarthTaxStatisticsLineChartModel());
        }
        return earthTaxStatisticsChartPanel;
    }

    private LineChartModel getEarthTaxStatisticsLineChartModel() {
        if (earthTaxStatisticsLineChartModel == null) {
            earthTaxStatisticsLineChartModel = new LineChartModel();
            earthTaxStatisticsLineChartModel.setMinimumXValue(0);
            earthTaxStatisticsLineChartModel.setMaximumXValue(model.getNumberOfTurns() - 1);
            int divisionCount = new Double((model.getNumberOfTurns() - 1) / Math.ceil((double) model.getNumberOfTurns() / X_AXIS_DIVISOR)).intValue();
            earthTaxStatisticsLineChartModel.setXDivisionCount(divisionCount);
            earthTaxStatisticsLineChartModel.setMinimumYValue(0);
            earthTaxStatisticsLineChartModel.setMaximumYValue(getMaximumEarthTaxRate() + 2);
            earthTaxStatisticsLineChartModel.setYDivisionCount(1);
            earthTaxStatisticsLineChartModel.setYAxisName("Earth tax rate %");
            earthTaxStatisticsLineChartModel.setXAxisName("Turns");
            earthTaxStatisticsLineChartModel.setAxisNameFont(new Font("Arial", 1, 14));
            PlayerHistory playerHistory = model.getRealm().getHistory().getPlayerHistory(model.getActivePlayer());
            for (int i = 0; i < model.getNumberOfTurns(); i++) {
                PlayerTurnHistory playerTurnHistory = playerHistory.getTurnHistory(i);
                String earthTaxRateValue = playerTurnHistory.getCustomData("earthTaxRate");
                int earthTaxRateIntValue = Integer.parseInt(earthTaxRateValue);
                earthTaxStatisticsLineChartModel.addValue(i, earthTaxRateIntValue);
            }
        }
        return earthTaxStatisticsLineChartModel;
    }

    private int getMaximumEarthTaxRate() {
        int maximumEarthTaxRate = 0;
        PlayerHistory playerHistory = model.getRealm().getHistory().getPlayerHistory(model.getActivePlayer());
        for (int i = 0; i < model.getNumberOfTurns(); i++) {
            PlayerTurnHistory playerTurnHistory = playerHistory.getTurnHistory(i);
            String earthTaxRateValue = playerTurnHistory.getCustomData("earthTaxRate");
            int earthTaxRateIntValue = Integer.parseInt(earthTaxRateValue);
            if (earthTaxRateIntValue > maximumEarthTaxRate) {
                maximumEarthTaxRate = earthTaxRateIntValue;
            }
        }
        return maximumEarthTaxRate;
    }

    private JPanel getSettlementCountStatisticsPanel() {
        if (settlementCountStatisticsPanel == null) {
            settlementCountStatisticsPanel = new JPanel(new BorderLayout());
            settlementCountStatisticsPanel.add(getSettlementCountStatisticsChartPanel(), BorderLayout.CENTER);
        }
        return settlementCountStatisticsPanel;
    }

    private LineChartPanel getSettlementCountStatisticsChartPanel() {
        if (settlementCountStatisticsChartPanel == null) {
            settlementCountStatisticsChartPanel = new LineChartPanel(getSettlementCountStatisticsLineChartModel());
        }
        return settlementCountStatisticsChartPanel;
    }

    private LineChartModel getSettlementCountStatisticsLineChartModel() {
        if (settlementCountStatisticsLineChartModel == null) {
            settlementCountStatisticsLineChartModel = new LineChartModel();
            settlementCountStatisticsLineChartModel.setMinimumXValue(0);
            settlementCountStatisticsLineChartModel.setMaximumXValue(model.getNumberOfTurns() - 1);
            int divisionCount = new Double((model.getNumberOfTurns() - 1) / Math.ceil((double) model.getNumberOfTurns() / X_AXIS_DIVISOR)).intValue();
            settlementCountStatisticsLineChartModel.setXDivisionCount(divisionCount);
            settlementCountStatisticsLineChartModel.setMinimumYValue(0);
            settlementCountStatisticsLineChartModel.setMaximumYValue(getMaximumSettlementCount());
            settlementCountStatisticsLineChartModel.setYDivisionCount(getMaximumSettlementCount());
            settlementCountStatisticsLineChartModel.setYAxisName("Colony count");
            settlementCountStatisticsLineChartModel.setXAxisName("Turns");
            settlementCountStatisticsLineChartModel.setAxisNameFont(new Font("Arial", 1, 14));
            PlayerHistory playerHistory = model.getRealm().getHistory().getPlayerHistory(model.getActivePlayer());
            for (int i = 0; i < model.getNumberOfTurns(); i++) {
                PlayerTurnHistory playerTurnHistory = playerHistory.getTurnHistory(i);
                settlementCountStatisticsLineChartModel.addValue(i, playerTurnHistory.getSettlementCount());
            }
        }
        return settlementCountStatisticsLineChartModel;
    }

    private int getMaximumSettlementCount() {
        int maximumSettlementCount = 0;
        PlayerHistory playerHistory = model.getRealm().getHistory().getPlayerHistory(model.getActivePlayer());
        for (int i = 0; i < model.getNumberOfTurns(); i++) {
            PlayerTurnHistory playerTurnHistory = playerHistory.getTurnHistory(i);
            if (playerTurnHistory.getSettlementCount() > maximumSettlementCount) {
                maximumSettlementCount = playerTurnHistory.getSettlementCount();
            }
        }
        return maximumSettlementCount;
    }

    private JPanel getUnitCountStatisticsPanel() {
        if (unitCountStatisticsPanel == null) {
            unitCountStatisticsPanel = new JPanel(new BorderLayout());
            unitCountStatisticsPanel.add(getUnitCountStatisticsChartPanel(), BorderLayout.CENTER);
        }
        return unitCountStatisticsPanel;
    }

    private LineChartPanel getUnitCountStatisticsChartPanel() {
        if (unitCountStatisticsChartPanel == null) {
            unitCountStatisticsChartPanel = new LineChartPanel(getUnitCountStatisticsLineChartModel());
        }
        return unitCountStatisticsChartPanel;
    }

    private LineChartModel getUnitCountStatisticsLineChartModel() {
        if (unitCountStatisticsLineChartModel == null) {
            unitCountStatisticsLineChartModel = new LineChartModel();
            unitCountStatisticsLineChartModel.setMinimumXValue(0);
            unitCountStatisticsLineChartModel.setMaximumXValue(model.getNumberOfTurns() - 1);
            int divisionCount = new Double((model.getNumberOfTurns() - 1) / Math.ceil((double) model.getNumberOfTurns() / X_AXIS_DIVISOR)).intValue();
            unitCountStatisticsLineChartModel.setXDivisionCount(divisionCount);
            unitCountStatisticsLineChartModel.setMinimumYValue(0);
            unitCountStatisticsLineChartModel.setMaximumYValue(getMaximumUnitCount());
            unitCountStatisticsLineChartModel.setYDivisionCount(getUnitStatisticsYDivisionCount());
            unitCountStatisticsLineChartModel.setYAxisName("Unit count");
            unitCountStatisticsLineChartModel.setXAxisName("Turns");
            unitCountStatisticsLineChartModel.setAxisNameFont(new Font("Arial", 1, 14));
            PlayerHistory playerHistory = model.getRealm().getHistory().getPlayerHistory(model.getActivePlayer());
            for (int i = 0; i < model.getNumberOfTurns(); i++) {
                PlayerTurnHistory playerTurnHistory = playerHistory.getTurnHistory(i);
                unitCountStatisticsLineChartModel.addValue(i, playerTurnHistory.getUnitCount());
            }
        }
        return unitCountStatisticsLineChartModel;
    }

    private int getMaximumUnitCount() {
        int maximumUnitCount = 0;
        PlayerHistory playerHistory = model.getRealm().getHistory().getPlayerHistory(model.getActivePlayer());
        for (int i = 0; i < model.getNumberOfTurns(); i++) {
            PlayerTurnHistory playerTurnHistory = playerHistory.getTurnHistory(i);
            if (playerTurnHistory.getUnitCount() > maximumUnitCount) {
                maximumUnitCount = playerTurnHistory.getUnitCount();
            }
        }
        return maximumUnitCount;
    }

    private int getUnitStatisticsYDivisionCount() {
        if (getMaximumUnitCount() > 20) {
            return 20;
        } else {
            return getMaximumUnitCount();
        }
    }

    private JPanel getMapExplorationStatisticsPanel() {
        if (mapExplorationStatisticsPanel == null) {
            mapExplorationStatisticsPanel = new JPanel(new BorderLayout());
            mapExplorationStatisticsPanel.add(getMapExplorationStatisticsChartPanel(), BorderLayout.CENTER);
        }
        return mapExplorationStatisticsPanel;
    }

    private LineChartPanel getMapExplorationStatisticsChartPanel() {
        if (mapExplorationStatisticsChartPanel == null) {
            mapExplorationStatisticsChartPanel = new LineChartPanel(getMapExplorationStatisticsLineChartModel());
        }
        return mapExplorationStatisticsChartPanel;
    }

    private LineChartModel getMapExplorationStatisticsLineChartModel() {
        if (mapExplorationStatisticsLineChartModel == null) {
            mapExplorationStatisticsLineChartModel = new LineChartModel();
            mapExplorationStatisticsLineChartModel.setMinimumXValue(0);
            mapExplorationStatisticsLineChartModel.setMaximumXValue(model.getNumberOfTurns() - 1);
            int divisionCount = new Double((model.getNumberOfTurns() - 1) / Math.ceil((double) model.getNumberOfTurns() / X_AXIS_DIVISOR)).intValue();
            mapExplorationStatisticsLineChartModel.setXDivisionCount(divisionCount);
            mapExplorationStatisticsLineChartModel.setMinimumYValue(0);
            mapExplorationStatisticsLineChartModel.setMaximumYValue(getMaximumExplorationPercent());
            mapExplorationStatisticsLineChartModel.setYDivisionCount(10);
            mapExplorationStatisticsLineChartModel.setYAxisName("Map exploration");
            mapExplorationStatisticsLineChartModel.setXAxisName("Turns");
            mapExplorationStatisticsLineChartModel.setAxisNameFont(new Font("Arial", 1, 14));
            PlayerHistory playerHistory = model.getRealm().getHistory().getPlayerHistory(model.getActivePlayer());
            for (int i = 0; i < model.getNumberOfTurns(); i++) {
                PlayerTurnHistory playerTurnHistory = playerHistory.getTurnHistory(i);
                mapExplorationStatisticsLineChartModel.addValue(i, playerTurnHistory.getMapExplorationPercent());
            }
        }
        return mapExplorationStatisticsLineChartModel;
    }

    private int getMaximumExplorationPercent() {
        int maximumExplorationPercent = 0;
        PlayerHistory playerHistory = model.getRealm().getHistory().getPlayerHistory(model.getActivePlayer());
        for (int i = 0; i < model.getNumberOfTurns(); i++) {
            PlayerTurnHistory playerTurnHistory = playerHistory.getTurnHistory(i);
            if (playerTurnHistory.getMapExplorationPercent() > maximumExplorationPercent) {
                maximumExplorationPercent = playerTurnHistory.getMapExplorationPercent();
            }
        }
        return maximumExplorationPercent;
    }
}
