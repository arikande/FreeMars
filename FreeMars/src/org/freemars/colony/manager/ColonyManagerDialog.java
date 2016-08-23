package org.freemars.colony.manager;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.event.MouseInputAdapter;
import org.freemars.colony.FreeMarsColony;
import org.freemars.model.FreeMarsModel;
import org.freemars.ui.image.FreeMarsImageManager;
import org.freemars.ui.util.FreeMarsDialog;
import org.freerealm.player.Player;

/**
 *
 * @author Deniz ARIKAN
 */
public class ColonyManagerDialog extends FreeMarsDialog {

    public static final int GROWTH_MODE = 0;
    public static final int PRODUCTION_MODE = 1;
    public static final int MANUFACTURING_MODE = 2;
    public static final int RESOURCES_MODE = 3;
    public static final int IMPROVEMENTS_MODE = 4;
    public static final int GARRISON_MODE = 5;
    private static final int FRAME_WIDTH = 1200;
    private static final int FRAME_HEIGHT = 700;
    private static final int DISPLAYED_COLUMN_MINIMUM_WIDTH = 40;
    private static final int DISPLAYED_COLUMN_PREFERRED_WIDTH = 80;
    private static final int DISPLAYED_COLUMN_MAXIMUM_WIDTH = 200;
    private JPanel pageStartPanel;
    private JPanel modeSelectionPanel;
    private ButtonGroup modeButtonGroup;
    private JToggleButton growthButton;
    private JToggleButton productionButton;
    private JToggleButton resourcesButton;
    private JToggleButton manufacturingButton;
    private JToggleButton improvementsButton;
    private JToggleButton garrisonButton;
    private JPanel mainPanel;
    private JScrollPane coloniesScrollPane;
    private ColoniesTable coloniesTable;
    private JPanel footerPanel;
    private JButton closeButton;
    private final FreeMarsModel freeMarsModel;
    private final Player player;

    public ColonyManagerDialog(Frame owner, FreeMarsModel freeMarsModel, Player player) {
        super(owner);
        this.freeMarsModel = freeMarsModel;
        this.player = player;
        setModal(true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setTitle("Colony manager");
        initializeWidgets();
    }

    public void display() {
        display(FRAME_WIDTH, FRAME_HEIGHT);
    }

    public void setMode(int mode) {
        for (int i = 0; i < getColoniesTable().getColumnCount(); i++) {
            getColoniesTable().getColumnModel().getColumn(i).setMinWidth(0);
            getColoniesTable().getColumnModel().getColumn(i).setPreferredWidth(0);
            getColoniesTable().getColumnModel().getColumn(i).setMaxWidth(0);
        }
        int[] displayColumns = {};
        switch (mode) {
            case GROWTH_MODE:
                getGrowthButton().setSelected(true);
                int[] growthModeColumns = {ColoniesTableModel.ICON_COLUMN_INDEX, ColoniesTableModel.NAME_COLUMN_INDEX, ColoniesTableModel.POPULATION_COLUMN_INDEX, ColoniesTableModel.WATER_COLUMN_INDEX, ColoniesTableModel.FOOD_COLUMN_INDEX, ColoniesTableModel.ENERGY_COLUMN_INDEX, ColoniesTableModel.FERTILIZER_COLUMN_INDEX, ColoniesTableModel.AUTO_MANAGED_RESOURCES_COLUMN_INDEX, ColoniesTableModel.FARMERS_COLUMN_INDEX, ColoniesTableModel.FARMER_PERCENT_COLUMN_INDEX};
                displayColumns = growthModeColumns;
                break;
            case PRODUCTION_MODE:
                int[] productionModeColumns = {ColoniesTableModel.ICON_COLUMN_INDEX, ColoniesTableModel.NAME_COLUMN_INDEX, ColoniesTableModel.POPULATION_COLUMN_INDEX, ColoniesTableModel.WORKFORCE_COLUMN_INDEX, ColoniesTableModel.CURRENT_PRODUCTION_COLUMN_INDEX, ColoniesTableModel.STATUS_COLUMN_INDEX, ColoniesTableModel.COMPLETED_IN_COLUMN_INDEX, ColoniesTableModel.EFFICIENCY_COLUMN_INDEX};
                displayColumns = productionModeColumns;
                break;
            case MANUFACTURING_MODE:
                int[] manufacturingModeColumns = {ColoniesTableModel.ICON_COLUMN_INDEX, ColoniesTableModel.NAME_COLUMN_INDEX, ColoniesTableModel.POPULATION_COLUMN_INDEX, ColoniesTableModel.MANUFACTURING_INFO_COLUMN_INDEX};
                displayColumns = manufacturingModeColumns;
                break;
            case RESOURCES_MODE:
                int[] resourcesModeColumns = {ColoniesTableModel.ICON_COLUMN_INDEX, ColoniesTableModel.NAME_COLUMN_INDEX, ColoniesTableModel.POPULATION_COLUMN_INDEX, ColoniesTableModel.HYDROGEN_AMOUNT_COLUMN_INDEX, ColoniesTableModel.LUMBER_AMOUNT_COLUMN_INDEX, ColoniesTableModel.IRON_AMOUNT_COLUMN_INDEX, ColoniesTableModel.STEEL_AMOUNT_COLUMN_INDEX, ColoniesTableModel.SILICA_AMOUNT_COLUMN_INDEX, ColoniesTableModel.GLASS_AMOUNT_COLUMN_INDEX, ColoniesTableModel.MINERALS_AMOUNT_COLUMN_INDEX, ColoniesTableModel.CHEMICALS_AMOUNT_COLUMN_INDEX, ColoniesTableModel.MAGNESIUM_AMOUNT_COLUMN_INDEX, ColoniesTableModel.GAUSS_RIFLE_AMOUNT_COLUMN_INDEX};
                displayColumns = resourcesModeColumns;
                break;
            case IMPROVEMENTS_MODE:
                int[] improvementsModeColumns = {ColoniesTableModel.ICON_COLUMN_INDEX, ColoniesTableModel.NAME_COLUMN_INDEX, ColoniesTableModel.POPULATION_COLUMN_INDEX, ColoniesTableModel.IMPROVEMENT_COUNT_COLUMN_INDEX, ColoniesTableModel.IMPROVEMENT_UPKEEP_COLUMN_INDEX, ColoniesTableModel.IMPROVEMENTS_COLUMN_INDEX};
                displayColumns = improvementsModeColumns;
                break;
            case GARRISON_MODE:
                int[] garrisonModeColumns = {ColoniesTableModel.ICON_COLUMN_INDEX, ColoniesTableModel.NAME_COLUMN_INDEX, ColoniesTableModel.POPULATION_COLUMN_INDEX, ColoniesTableModel.DEFENSES_COLUMN_INDEX, ColoniesTableModel.MILITARY_FACILITIES_COLUMN_INDEX, ColoniesTableModel.UNITS_COLUMN_INDEX};
                displayColumns = garrisonModeColumns;
                break;
        }
        for (int i = 0; i < displayColumns.length; i++) {
            getColoniesTable().getColumnModel().getColumn(displayColumns[i]).setMinWidth(DISPLAYED_COLUMN_MINIMUM_WIDTH);
            getColoniesTable().getColumnModel().getColumn(displayColumns[i]).setPreferredWidth(DISPLAYED_COLUMN_PREFERRED_WIDTH);
            getColoniesTable().getColumnModel().getColumn(displayColumns[i]).setMaxWidth(DISPLAYED_COLUMN_MAXIMUM_WIDTH);
        }
        getColoniesTable().getColumnModel().getColumn(ColoniesTableModel.ICON_COLUMN_INDEX).setMinWidth(75);
        getColoniesTable().getColumnModel().getColumn(ColoniesTableModel.ICON_COLUMN_INDEX).setPreferredWidth(75);
        getColoniesTable().getColumnModel().getColumn(ColoniesTableModel.ICON_COLUMN_INDEX).setMaxWidth(75);
        getColoniesTable().getColumnModel().getColumn(ColoniesTableModel.NAME_COLUMN_INDEX).setMinWidth(100);
        getColoniesTable().getColumnModel().getColumn(ColoniesTableModel.NAME_COLUMN_INDEX).setPreferredWidth(100);
        getColoniesTable().getColumnModel().getColumn(ColoniesTableModel.NAME_COLUMN_INDEX).setMaxWidth(100);
        getColoniesTable().getColumnModel().getColumn(ColoniesTableModel.POPULATION_COLUMN_INDEX).setMinWidth(75);
        getColoniesTable().getColumnModel().getColumn(ColoniesTableModel.POPULATION_COLUMN_INDEX).setPreferredWidth(75);
        getColoniesTable().getColumnModel().getColumn(ColoniesTableModel.POPULATION_COLUMN_INDEX).setMaxWidth(75);
        if (mode == GROWTH_MODE) {
            getColoniesTable().getColumnModel().getColumn(ColoniesTableModel.AUTO_MANAGED_RESOURCES_COLUMN_INDEX).setMinWidth(100);
            getColoniesTable().getColumnModel().getColumn(ColoniesTableModel.AUTO_MANAGED_RESOURCES_COLUMN_INDEX).setPreferredWidth(100);
            getColoniesTable().getColumnModel().getColumn(ColoniesTableModel.AUTO_MANAGED_RESOURCES_COLUMN_INDEX).setMaxWidth(100);
        } else if (mode == MANUFACTURING_MODE) {
            getColoniesTable().getColumnModel().getColumn(ColoniesTableModel.MANUFACTURING_INFO_COLUMN_INDEX).setMinWidth(950);
            getColoniesTable().getColumnModel().getColumn(ColoniesTableModel.MANUFACTURING_INFO_COLUMN_INDEX).setPreferredWidth(950);
            getColoniesTable().getColumnModel().getColumn(ColoniesTableModel.MANUFACTURING_INFO_COLUMN_INDEX).setMaxWidth(950);
        } else if (mode == IMPROVEMENTS_MODE) {
            getColoniesTable().getColumnModel().getColumn(ColoniesTableModel.IMPROVEMENT_COUNT_COLUMN_INDEX).setMinWidth(50);
            getColoniesTable().getColumnModel().getColumn(ColoniesTableModel.IMPROVEMENT_COUNT_COLUMN_INDEX).setPreferredWidth(50);
            getColoniesTable().getColumnModel().getColumn(ColoniesTableModel.IMPROVEMENT_COUNT_COLUMN_INDEX).setMaxWidth(50);
            getColoniesTable().getColumnModel().getColumn(ColoniesTableModel.IMPROVEMENT_UPKEEP_COLUMN_INDEX).setMinWidth(50);
            getColoniesTable().getColumnModel().getColumn(ColoniesTableModel.IMPROVEMENT_UPKEEP_COLUMN_INDEX).setPreferredWidth(50);
            getColoniesTable().getColumnModel().getColumn(ColoniesTableModel.IMPROVEMENT_UPKEEP_COLUMN_INDEX).setMaxWidth(50);
            getColoniesTable().getColumnModel().getColumn(ColoniesTableModel.IMPROVEMENTS_COLUMN_INDEX).setMinWidth(850);
            getColoniesTable().getColumnModel().getColumn(ColoniesTableModel.IMPROVEMENTS_COLUMN_INDEX).setPreferredWidth(850);
            getColoniesTable().getColumnModel().getColumn(ColoniesTableModel.IMPROVEMENTS_COLUMN_INDEX).setMaxWidth(850);
        } else if (mode == GARRISON_MODE) {
            getColoniesTable().getColumnModel().getColumn(ColoniesTableModel.DEFENSES_COLUMN_INDEX).setMinWidth(80);
            getColoniesTable().getColumnModel().getColumn(ColoniesTableModel.DEFENSES_COLUMN_INDEX).setPreferredWidth(80);
            getColoniesTable().getColumnModel().getColumn(ColoniesTableModel.DEFENSES_COLUMN_INDEX).setMaxWidth(80);
            getColoniesTable().getColumnModel().getColumn(ColoniesTableModel.MILITARY_FACILITIES_COLUMN_INDEX).setMinWidth(120);
            getColoniesTable().getColumnModel().getColumn(ColoniesTableModel.MILITARY_FACILITIES_COLUMN_INDEX).setPreferredWidth(120);
            getColoniesTable().getColumnModel().getColumn(ColoniesTableModel.MILITARY_FACILITIES_COLUMN_INDEX).setMaxWidth(120);
            getColoniesTable().getColumnModel().getColumn(ColoniesTableModel.UNITS_COLUMN_INDEX).setMinWidth(750);
            getColoniesTable().getColumnModel().getColumn(ColoniesTableModel.UNITS_COLUMN_INDEX).setPreferredWidth(750);
            getColoniesTable().getColumnModel().getColumn(ColoniesTableModel.UNITS_COLUMN_INDEX).setMaxWidth(750);
        }
        getColoniesTable().revalidate();
        getColoniesTable().repaint();
    }

    public FreeMarsColony getSelectedColony() {
        return getColoniesTable().getSelectedColony();
    }

    public void setColoniesTableMouseAdapter(MouseAdapter mouseAdapter) {
        getColoniesTable().addMouseListener(mouseAdapter);
    }

    public void setCloseButtonAction(Action action) {
        getCloseButton().setAction(action);
        getCloseButton().setText("Close");
    }

    public void setGrowthButtonAction(Action action) {
        getGrowthButton().setAction(action);
        getGrowthButton().setText("Growth");
        getGrowthButton().setIcon(new ImageIcon(FreeMarsImageManager.getImage("COLONY_MANAGER_GROWTH")));
    }

    public void setProductionButtonAction(Action action) {
        getProductionButton().setAction(action);
        getProductionButton().setText("Production");
        getProductionButton().setIcon(new ImageIcon(FreeMarsImageManager.getImage("COLONY_MANAGER_PRODUCTION")));
    }

    public void setManufacturingButtonAction(Action action) {
        getManufacturingButtonButton().setAction(action);
        getManufacturingButtonButton().setText("Manufacturing");
        getManufacturingButtonButton().setIcon(new ImageIcon(FreeMarsImageManager.getImage("COLONY_MANAGER_MANUFACTURING")));
    }

    public void setResourcesButtonAction(Action action) {
        getResourcesButton().setAction(action);
        getResourcesButton().setText("Resources");
        getResourcesButton().setIcon(new ImageIcon(FreeMarsImageManager.getImage("COLONY_MANAGER_RESOURCES")));
    }

    public void setImprovementsButtonAction(Action action) {
        getImprovementsButton().setAction(action);
        getImprovementsButton().setText("Improvements");
        getImprovementsButton().setIcon(new ImageIcon(FreeMarsImageManager.getImage("COLONY_MANAGER_IMPROVEMENTS")));
    }

    public void setGarrisonButtonAction(Action action) {
        getGarrisonButton().setAction(action);
        getGarrisonButton().setText("Garrison");
        getGarrisonButton().setIcon(new ImageIcon(FreeMarsImageManager.getImage("COLONY_MANAGER_GARRISON")));
    }

    private void initializeWidgets() {
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(getPageStartPanel(), BorderLayout.PAGE_START);
        getContentPane().add(getMainPanel(), BorderLayout.CENTER);
        getContentPane().add(getFooterPanel(), BorderLayout.PAGE_END);
    }

    private JPanel getPageStartPanel() {
        if (pageStartPanel == null) {
            pageStartPanel = new JPanel();
            pageStartPanel.add(getModeSelectionPanel());
        }
        return pageStartPanel;
    }

    private JPanel getModeSelectionPanel() {
        if (modeSelectionPanel == null) {
            modeSelectionPanel = new JPanel(new GridLayout(1, 0, 5, 0));
            modeSelectionPanel.add(getGrowthButton());
            modeSelectionPanel.add(getProductionButton());
            modeSelectionPanel.add(getManufacturingButtonButton());
            modeSelectionPanel.add(getResourcesButton());
            modeSelectionPanel.add(getImprovementsButton());
            modeSelectionPanel.add(getGarrisonButton());
            getModeButtonGroup().add(getGrowthButton());
            getModeButtonGroup().add(getProductionButton());
            getModeButtonGroup().add(getManufacturingButtonButton());
            getModeButtonGroup().add(getResourcesButton());
            getModeButtonGroup().add(getImprovementsButton());
            getModeButtonGroup().add(getGarrisonButton());
        }
        return modeSelectionPanel;
    }

    private ButtonGroup getModeButtonGroup() {
        if (modeButtonGroup == null) {
            modeButtonGroup = new ButtonGroup();
        }
        return modeButtonGroup;
    }

    private JToggleButton getGrowthButton() {
        if (growthButton == null) {
            growthButton = new JToggleButton();
            growthButton.setFocusPainted(false);
            growthButton.setBorderPainted(false);
            growthButton.setFont(growthButton.getFont().deriveFont(15f));
        }
        return growthButton;
    }

    private JToggleButton getProductionButton() {
        if (productionButton == null) {
            productionButton = new JToggleButton();
            productionButton.setFocusPainted(false);
            productionButton.setBorderPainted(false);
            productionButton.setFont(productionButton.getFont().deriveFont(15f));
        }
        return productionButton;
    }

    private JToggleButton getManufacturingButtonButton() {
        if (manufacturingButton == null) {
            manufacturingButton = new JToggleButton();
            manufacturingButton.setFocusPainted(false);
            manufacturingButton.setBorderPainted(false);
            manufacturingButton.setFont(manufacturingButton.getFont().deriveFont(15f));
        }
        return manufacturingButton;
    }

    private JToggleButton getResourcesButton() {
        if (resourcesButton == null) {
            resourcesButton = new JToggleButton();
            resourcesButton.setFocusPainted(false);
            resourcesButton.setBorderPainted(false);
            resourcesButton.setFont(resourcesButton.getFont().deriveFont(15f));
        }
        return resourcesButton;
    }

    private JToggleButton getImprovementsButton() {
        if (improvementsButton == null) {
            improvementsButton = new JToggleButton();
            improvementsButton.setFocusPainted(false);
            improvementsButton.setBorderPainted(false);
            improvementsButton.setFont(growthButton.getFont().deriveFont(15f));
        }
        return improvementsButton;
    }

    private JToggleButton getGarrisonButton() {
        if (garrisonButton == null) {
            garrisonButton = new JToggleButton();
            garrisonButton.setFocusPainted(false);
            garrisonButton.setBorderPainted(false);
            garrisonButton.setFont(growthButton.getFont().deriveFont(15f));
        }
        return garrisonButton;
    }

    private JPanel getMainPanel() {
        if (mainPanel == null) {
            mainPanel = new JPanel(new BorderLayout());
            mainPanel.add(getColoniesScrollPane());
        }
        return mainPanel;
    }

    private JScrollPane getColoniesScrollPane() {
        if (coloniesScrollPane == null) {
            coloniesScrollPane = new JScrollPane(getColoniesTable());
        }
        return coloniesScrollPane;
    }

    private ColoniesTable getColoniesTable() {
        if (coloniesTable == null) {
            coloniesTable = new ColoniesTable(freeMarsModel.getRealm(), player);

            coloniesTable.addMouseMotionListener(new MouseInputAdapter() {

                @Override
                public void mouseMoved(MouseEvent e) {
                    int row = coloniesTable.rowAtPoint(e.getPoint());
                    coloniesTable.getSelectionModel().setSelectionInterval(row, row);
                }
            });

        }
        return coloniesTable;
    }

    private JPanel getFooterPanel() {
        if (footerPanel == null) {
            footerPanel = new JPanel();
            footerPanel.add(getCloseButton());
        }
        return footerPanel;
    }

    private JButton getCloseButton() {
        if (closeButton == null) {
            closeButton = new JButton("Close");
        }
        return closeButton;
    }
}
