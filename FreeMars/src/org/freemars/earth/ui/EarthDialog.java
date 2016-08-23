package org.freemars.earth.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.Map.Entry;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import org.freemars.controller.FreeMarsController;
import org.freemars.earth.Earth;
import org.freemars.earth.EarthFlightProperty;
import org.freemars.earth.Location;
import org.freemars.earth.action.BuyUnitFromEarthAction;
import org.freemars.earth.action.EarthDialogUnitSelectionAction;
import org.freemars.earth.action.FinanceColonistsAction;
import org.freemars.ui.image.FreeMarsImageManager;
import org.freemars.ui.image.ImagePanel;
import org.freemars.ui.util.FreeMarsDialog;
import org.freerealm.player.Player;
import org.freerealm.property.ContainerProperty;
import org.freerealm.unit.FreeRealmUnitType;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class EarthDialog extends FreeMarsDialog {

    private final int FRAME_WIDTH = 900;
    private final int FRAME_HEIGHT = 720;
    private JPanel headerPanel;
    private JPanel westPanel;
    private JPanel eastPanel;
    private JPanel westTopPanel;
    private EarthPricesPanel pageEndPanel;
    private ImagePanel unitInfoPanel;
    private JScrollPane toMarsPanelScrollPane;
    private JPanel toMarsPanel;
    private JLabel toMarsLabel;
    private JLabel toEarthLabel;
    private JLabel earthLabel;
    private JPanel earthPanel;
    private JScrollPane toEarthPanelScrollPane;
    private JPanel toEarthPanel;
    private JScrollPane earthPanelScrollPane;
    private JPanel earthInfoAndBuySellPanel;
    private JPanel earthBuySpecialCargoPanel;
    private JPanel financeColonistsPanel;
    private JLabel financeColonistsLabel;
    private JButton financeColonistsButton1;
    private JButton financeColonistsButton2;
    private JButton financeColonistsButton3;
    private JButton financeColonistsButton4;
    private JPanel financeColonistsButtonsPanel;
    private JPanel emptyPanel;
    private JPanel earthBuyUnitsPanel;
    private JLabel earthBuyUnitsLabel;
    private JPanel earthBuyUnitsCenterPanel;
    private JButton buyShuttleButton;
    private JButton buyMechButton;
    private JButton buyFreighterButton;
    private JButton buyBulkFreighterButton;
    private JButton buyColonizerButton;
    private JButton buyTransporterButton;
    private JScrollPane unitInformationScrollPane;
    private JTextArea unitInformationTextArea;
    private UnitCargoPanel unitCargoPanel;
    private final FreeMarsController freeMarsController;
    private ButtonGroup bg = new ButtonGroup();

    public EarthDialog(FreeMarsController freeMarsController) {
        super(freeMarsController.getCurrentFrame());
        this.freeMarsController = freeMarsController;
        setModal(true);
        setResizable(false);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setTitle("Earth");
        addWindowListener(new EarthDialogWindowAdapter(freeMarsController, this));
    }

    public void update(int updateType) {
        getUnitInformationScrollPane().requestFocus();

        switch (updateType) {
            case Earth.UNIT_RELOCATION_UPDATE:
                getUnitCargoPanel().setVisible(false);
                updateUnitLocations();
                updateFinanceColonistsButtons();
                break;
            case Earth.BUY_SELL_UPDATE:
                updateUnitCargo();
                updateUnitPurchasePanel();
                updateFinanceColonistsButtons();
                getEarthPricesPanel().update();
                break;
            case Earth.UNIT_SELECTION_UPDATE:
                Unit selectedUnit = freeMarsController.getFreeMarsModel().getEarth().getEarthDialogSelectedUnit();
                ContainerProperty containerProperty = (ContainerProperty) selectedUnit.getType().getProperty("ContainerProperty");
                EarthFlightProperty earthFlightProperty = (EarthFlightProperty) selectedUnit.getType().getProperty(EarthFlightProperty.NAME);
                if (containerProperty != null && earthFlightProperty != null) {
                    getEarthPricesPanel().setTransferHandler(new EarthTransferHandler(freeMarsController, this, selectedUnit));
                    getUnitCargoPanel().setUnit(selectedUnit);
                    getUnitCargoPanel().setMaxCapacity(selectedUnit.getTotalCapacity());
                    getUnitCargoPanel().setTransferHandler(new SpaceshipResourcesTransferHandler(freeMarsController, this, selectedUnit));
                    updateUnitCargo();
                    getUnitCargoPanel().setVisible(true);
                } else {
                    getUnitCargoPanel().setVisible(false);
                }
                updateFinanceColonistsButtons();
                break;
            case Earth.PURCHASE_EARTH_UNIT_UPDATE:
            case Earth.SELL_UNIT_TO_EARTH_UPDATE:
                getUnitCargoPanel().setVisible(false);
                updateUnitLocations();
                updateUnitPurchasePanel();
                break;
            default:
                getUnitCargoPanel().setVisible(false);
                updateUnitPurchasePanel();
                updateUnitLocations();
                updateFinanceColonistsButtons();
                break;
        }

    }

    public void display() {
        getContentPane().setLayout(new BorderLayout(0, 5));
        getContentPane().setBackground(Color.black);
        initializeWidgets();
        pack();
        final java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width - FRAME_WIDTH) / 2, (screenSize.height - FRAME_HEIGHT) / 2, FRAME_WIDTH, FRAME_HEIGHT);
        setVisible(true);
        toFront();
    }

    private void initializeWidgets() {
        add(getHeaderPanel(), BorderLayout.PAGE_START);
        add(getUnitInfoPanel(), BorderLayout.CENTER);
        add(getEarthPricesPanel(), BorderLayout.PAGE_END);
        update(-1);
    }

    public void addUnitInfoText(String text) {
        getUnitInformationTextArea().append(text);
    }

    public int getSelectedQuantity() {
        return getUnitCargoPanel().getSelectedQuantity();
    }

    private void updateUnitCargo() {
        getUnitCargoPanel().refresh();
    }

    private void updateUnitLocations() {
        getToEarthPanel().removeAll();
        getEarthPanel().removeAll();
        getToMarsPanel().removeAll();
        bg = new ButtonGroup();
        Iterator<Entry<Unit, Location>> iterator = freeMarsController.getFreeMarsModel().getEarth().getUnitLocationsIterator();
        while (iterator.hasNext()) {
            Entry<Unit, Location> entry = iterator.next();
            Unit unit = entry.getKey();
            Location location = entry.getValue();
            if (unit.getPlayer().equals(freeMarsController.getFreeMarsModel().getRealm().getPlayerManager().getActivePlayer())) {
                UnitSelectionToggleButton toggleButton = new UnitSelectionToggleButton(unit);
                toggleButton.setOpaque(false);
                toggleButton.setBorderPainted(false);
                toggleButton.setContentAreaFilled(false);
                if (location.equals(Location.EARTH)) {
                    toggleButton.setAction(new EarthDialogUnitSelectionAction(freeMarsController, this));
                    toggleButton.setText(unit.getName());
                    getEarthPanel().add(toggleButton);
                    bg.add(toggleButton);
                    toggleButton.requestFocus();
                } else if (location.equals(Location.TRAVELING_TO_EARTH)) {
                    toggleButton.setFocusable(false);
                    toggleButton.setText("<html><table><tr><td bgcolor='#FFEEEE'>E.T.A " + freeMarsController.getFreeMarsModel().getEarth().findETA(unit) + "</td></tr></table></html>");
                    toggleButton.setToolTipText("");
                    getToEarthPanel().add(toggleButton);
                } else if (location.equals(Location.TRAVELING_TO_MARS)) {
                    toggleButton.setFocusable(false);
                    String buttonText = "<html><table><tr><td bgcolor='#FFEEEE'>E.T.A " + freeMarsController.getFreeMarsModel().getEarth().findETA(unit);
                    buttonText = buttonText + "<br>" + freeMarsController.getFreeMarsModel().getEarth().findDestination(unit) + "</td></tr></table></html>";
                    toggleButton.setText(buttonText);
                    toggleButton.setToolTipText("");
                    getToMarsPanel().add(toggleButton);
                }
                Image unitImage = FreeMarsImageManager.getImage(unit);
                unitImage = FreeMarsImageManager.createResizedCopy(unitImage, 120, -1, false, null);
                toggleButton.setIcon(new ImageIcon(unitImage));
            }
        }
        for (int i = 0; i < 3 - getToMarsPanel().getComponentCount(); i++) {
            getToMarsPanel().add(new JLabel());
        }
        for (int i = 0; i < 3 - getToEarthPanel().getComponentCount(); i++) {
            getToEarthPanel().add(new JLabel());
        }
        for (int i = 0; i < 3 - getEarthPanel().getComponentCount(); i++) {
            getEarthPanel().add(new JLabel());
        }
        getToEarthPanel().revalidate();
        getEarthPanel().revalidate();
        getToMarsPanel().revalidate();
        getToEarthPanel().repaint();
    }

    private void updateFinanceColonistsButtons() {
        getFinanceColonistsButton1().setEnabled(false);
        getFinanceColonistsButton2().setEnabled(false);
        getFinanceColonistsButton3().setEnabled(false);
        getFinanceColonistsButton4().setEnabled(false);
        Unit selectedUnit = freeMarsController.getFreeMarsModel().getEarth().getEarthDialogSelectedUnit();
        if (selectedUnit != null) {
            ContainerProperty containerProperty = (ContainerProperty) selectedUnit.getType().getProperty("ContainerProperty");
            EarthFlightProperty earthFlightProperty = (EarthFlightProperty) selectedUnit.getType().getProperty(EarthFlightProperty.NAME);
            Location location = freeMarsController.getFreeMarsModel().getEarth().getUnitLocation(selectedUnit);
            if (location != null && location.equals(Location.EARTH) && containerProperty != null && earthFlightProperty != null) {
                int financeCostPerColonist = Integer.parseInt(freeMarsController.getFreeMarsModel().getRealm().getProperty("finance_cost_per_colonist"));
                Player player = freeMarsController.getFreeMarsModel().getEarth().getEarthDialogSelectedUnit().getPlayer();
                int remainingCapacity = selectedUnit.getRemainingCapacity();
                int weightPerCitizen = Integer.parseInt(freeMarsController.getFreeMarsModel().getRealm().getProperty("weight_per_citizen"));
                if ((remainingCapacity >= 10 * weightPerCitizen) && player.getWealth() >= financeCostPerColonist * 10) {
                    getFinanceColonistsButton1().setEnabled(true);
                }
                if ((remainingCapacity >= 20 * weightPerCitizen) && player.getWealth() >= financeCostPerColonist * 20) {
                    getFinanceColonistsButton2().setEnabled(true);
                }
                if ((remainingCapacity >= 50 * weightPerCitizen) && player.getWealth() >= financeCostPerColonist * 50) {
                    getFinanceColonistsButton3().setEnabled(true);
                }
                if ((remainingCapacity >= 100 * weightPerCitizen) && player.getWealth() >= financeCostPerColonist * 100) {
                    getFinanceColonistsButton4().setEnabled(true);
                }
            }
        }
    }

    private JPanel getHeaderPanel() {
        if (headerPanel == null) {
            headerPanel = new JPanel(new GridLayout(1, 4));
            headerPanel.setBackground(Color.BLACK);
            headerPanel.add(getToEarthLabel());
            headerPanel.add(getEarthLabel());
            headerPanel.add(getToMarsLabel());
            headerPanel.add(new JLabel());
        }
        return headerPanel;
    }

    private JLabel getEarthLabel() {
        if (earthLabel == null) {
            earthLabel = new JLabel("  Earth");
            earthLabel.setForeground(Color.WHITE);
        }
        return earthLabel;
    }

    private JLabel getToMarsLabel() {
        if (toMarsLabel == null) {
            toMarsLabel = new JLabel("  Traveling To Mars");
            toMarsLabel.setForeground(Color.WHITE);
        }
        return toMarsLabel;
    }

    private JLabel getToEarthLabel() {
        if (toEarthLabel == null) {
            toEarthLabel = new JLabel("  Traveling To Earth");
            toEarthLabel.setForeground(Color.WHITE);
        }
        return toEarthLabel;
    }

    private UnitCargoPanel getUnitCargoPanel() {
        if (unitCargoPanel == null) {
            unitCargoPanel = new UnitCargoPanel(freeMarsController, this);
        }
        return unitCargoPanel;
    }

    private ImagePanel getUnitInfoPanel() {
        if (unitInfoPanel == null) {
            unitInfoPanel = new ImagePanel(FreeMarsImageManager.getImage("EARTH_BACKGROUND"));
            unitInfoPanel.setLayout(new GridLayout(1, 2));
            unitInfoPanel.add(getWestPanel());
            unitInfoPanel.add(getEastPanel());
            unitInfoPanel.setBackground(Color.BLACK);
        }
        return unitInfoPanel;
    }

    private JPanel getWestPanel() {
        if (westPanel == null) {
            westPanel = new JPanel(new BorderLayout());
            westPanel.setOpaque(false);
            westPanel.add(getWestTopPanel(), BorderLayout.CENTER);
            westPanel.add(getUnitCargoPanel(), BorderLayout.PAGE_END);
        }
        return westPanel;
    }

    private JPanel getWestTopPanel() {
        if (westTopPanel == null) {
            westTopPanel = new JPanel(new GridLayout(0, 2));
            westTopPanel.setOpaque(false);
            westTopPanel.add(getToEarthPanelScrollPane());
            westTopPanel.add(getEarthPanelScrollPane());
        }
        return westTopPanel;
    }

    private JScrollPane getEarthPanelScrollPane() {
        if (earthPanelScrollPane == null) {
            earthPanelScrollPane = new JScrollPane(getEarthPanel(), ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            earthPanelScrollPane.setOpaque(false);
            earthPanelScrollPane.getViewport().setOpaque(false);
        }
        return earthPanelScrollPane;
    }

    private JPanel getEastPanel() {
        if (eastPanel == null) {
            eastPanel = new JPanel(new GridLayout(0, 2));
            eastPanel.setOpaque(false);
            eastPanel.add(getToMarsPanelScrollPane());
            eastPanel.add(getEarthInfoAndBuySellPanel());
        }
        return eastPanel;
    }

    private JScrollPane getUnitInformationScrollPane() {
        if (unitInformationScrollPane == null) {
            unitInformationScrollPane = new JScrollPane(getUnitInformationTextArea());
            unitInformationScrollPane.getViewport().setOpaque(false);
            unitInformationScrollPane.setOpaque(false);
        }
        return unitInformationScrollPane;
    }

    private JTextArea getUnitInformationTextArea() {
        if (unitInformationTextArea == null) {
            unitInformationTextArea = new JTextArea();
            unitInformationTextArea.setForeground(Color.WHITE);
            unitInformationTextArea.setOpaque(false);
            unitInformationTextArea.setLineWrap(true);
            unitInformationTextArea.setWrapStyleWord(true);
            unitInformationTextArea.setEditable(false);
        }
        return unitInformationTextArea;
    }

    private JScrollPane getToMarsPanelScrollPane() {
        if (toMarsPanelScrollPane == null) {
            toMarsPanelScrollPane = new JScrollPane(getToMarsPanel(), ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            toMarsPanelScrollPane.setOpaque(false);
            toMarsPanelScrollPane.getViewport().setOpaque(false);
        }
        return toMarsPanelScrollPane;
    }

    private JPanel getToMarsPanel() {
        if (toMarsPanel == null) {
            toMarsPanel = new JPanel(new GridLayout(0, 1));
            toMarsPanel.setOpaque(false);
            toMarsPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        }
        return toMarsPanel;
    }

    private JPanel getEarthPanel() {
        if (earthPanel == null) {
            earthPanel = new JPanel(new GridLayout(0, 1));
            earthPanel.setOpaque(false);
            earthPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        }
        return earthPanel;
    }

    private JScrollPane getToEarthPanelScrollPane() {
        if (toEarthPanelScrollPane == null) {
            toEarthPanelScrollPane = new JScrollPane(getToEarthPanel(), ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            toEarthPanelScrollPane.setOpaque(false);
            toEarthPanelScrollPane.getViewport().setOpaque(false);
        }
        return toEarthPanelScrollPane;
    }

    private JPanel getToEarthPanel() {
        if (toEarthPanel == null) {
            toEarthPanel = new JPanel(new GridLayout(0, 1));
            toEarthPanel.setOpaque(false);
            toEarthPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        }
        return toEarthPanel;
    }

    private JPanel getEarthInfoAndBuySellPanel() {
        if (earthInfoAndBuySellPanel == null) {
            earthInfoAndBuySellPanel = new JPanel(new BorderLayout());
            earthInfoAndBuySellPanel.add(getUnitInformationScrollPane(), BorderLayout.CENTER);
            earthInfoAndBuySellPanel.add(getEarthBuySpecialCargoPanel(), BorderLayout.PAGE_END);
            earthInfoAndBuySellPanel.setOpaque(false);
        }
        return earthInfoAndBuySellPanel;
    }

    private JPanel getEarthBuySpecialCargoPanel() {
        if (earthBuySpecialCargoPanel == null) {
            earthBuySpecialCargoPanel = new JPanel(new BorderLayout());
            earthBuySpecialCargoPanel.add(getFinanceColonistsPanel(), BorderLayout.PAGE_START);
            earthBuySpecialCargoPanel.add(getEarthBuyUnitsPanel(), BorderLayout.CENTER);
        }
        return earthBuySpecialCargoPanel;
    }

    private JPanel getFinanceColonistsPanel() {
        if (financeColonistsPanel == null) {
            financeColonistsPanel = new JPanel(new GridLayout(3, 1));
            financeColonistsPanel.add(getFinanceColonistsLabel());
            financeColonistsPanel.add(getFinanceColonistsButtonsPanel());
            financeColonistsPanel.add(getEmptyPanel());
        }
        return financeColonistsPanel;
    }

    private JPanel getFinanceColonistsButtonsPanel() {
        if (financeColonistsButtonsPanel == null) {
            financeColonistsButtonsPanel = new JPanel(new GridLayout(1, 4));
            financeColonistsButtonsPanel.add(getFinanceColonistsButton1());
            financeColonistsButtonsPanel.add(getFinanceColonistsButton2());
            financeColonistsButtonsPanel.add(getFinanceColonistsButton3());
            financeColonistsButtonsPanel.add(getFinanceColonistsButton4());
        }
        return financeColonistsButtonsPanel;
    }

    private JLabel getFinanceColonistsLabel() {
        if (financeColonistsLabel == null) {
            financeColonistsLabel = new JLabel("Finance colonists");
        }
        return financeColonistsLabel;
    }

    private JPanel getEmptyPanel() {
        if (emptyPanel == null) {
            emptyPanel = new JPanel();
            emptyPanel.setBackground(Color.black);
        }
        return emptyPanel;
    }

    private JButton getFinanceColonistsButton1() {
        if (financeColonistsButton1 == null) {
            financeColonistsButton1 = new JButton(new FinanceColonistsAction(freeMarsController, this, 10));
            financeColonistsButton1.setFont(financeColonistsButton1.getFont().deriveFont(10f));
        }
        return financeColonistsButton1;
    }

    private JButton getFinanceColonistsButton2() {
        if (financeColonistsButton2 == null) {
            financeColonistsButton2 = new JButton(new FinanceColonistsAction(freeMarsController, this, 20));
            financeColonistsButton2.setFont(financeColonistsButton2.getFont().deriveFont(10f));
        }
        return financeColonistsButton2;
    }

    private JButton getFinanceColonistsButton3() {
        if (financeColonistsButton3 == null) {
            financeColonistsButton3 = new JButton(new FinanceColonistsAction(freeMarsController, this, 50));
            financeColonistsButton3.setFont(financeColonistsButton3.getFont().deriveFont(10f));
        }
        return financeColonistsButton3;
    }

    private JButton getFinanceColonistsButton4() {
        if (financeColonistsButton4 == null) {
            financeColonistsButton4 = new JButton(new FinanceColonistsAction(freeMarsController, this, 100));
            financeColonistsButton4.setFont(financeColonistsButton4.getFont().deriveFont(10f));
        }
        return financeColonistsButton4;
    }

    private JPanel getEarthBuyUnitsPanel() {
        if (earthBuyUnitsPanel == null) {
            earthBuyUnitsPanel = new JPanel(new BorderLayout());
            earthBuyUnitsPanel.add(getEarthBuyUnitsLabel(), BorderLayout.PAGE_START);
            earthBuyUnitsPanel.add(getEarthBuyUnitsCenterPanel(), BorderLayout.CENTER);
        }
        return earthBuyUnitsPanel;
    }

    private JLabel getEarthBuyUnitsLabel() {
        if (earthBuyUnitsLabel == null) {
            earthBuyUnitsLabel = new JLabel("Buy unit");
        }
        return earthBuyUnitsLabel;
    }

    private JPanel getEarthBuyUnitsCenterPanel() {
        if (earthBuyUnitsCenterPanel == null) {
            earthBuyUnitsCenterPanel = new JPanel(new GridLayout(0, 2));
            earthBuyUnitsCenterPanel.add(getBuyShuttleButton());
            earthBuyUnitsCenterPanel.add(getBuyFreighterButton());
            earthBuyUnitsCenterPanel.add(getBuyBulkFreighterButton());
            earthBuyUnitsCenterPanel.add(getBuyMechButton());
            earthBuyUnitsCenterPanel.add(getBuyColonizerButton());
            earthBuyUnitsCenterPanel.add(getBuyTransporterButton());
        }
        return earthBuyUnitsCenterPanel;
    }

    private JButton getBuyShuttleButton() {
        if (buyShuttleButton == null) {
            buyShuttleButton = new JButton();
            FreeRealmUnitType unitType = freeMarsController.getFreeMarsModel().getRealm().getUnitTypeManager().getUnitType(6);
            buyShuttleButton.setAction(new BuyUnitFromEarthAction(freeMarsController, this, unitType));
            String unitPrice = new DecimalFormat().format(freeMarsController.getFreeMarsModel().getEarth().getEarthSellsAtPrice(unitType));
            buyShuttleButton.setText("<html>Shuttle<br>" + unitPrice + "</html>");
            Image shuttleImage = FreeMarsImageManager.getImage("UNIT_6_SW");
            shuttleImage = FreeMarsImageManager.createResizedCopy(shuttleImage, -1, 50, false, this);
            buyShuttleButton.setIcon(new ImageIcon(shuttleImage));
            buyShuttleButton.setVerticalTextPosition(AbstractButton.BOTTOM);
            buyShuttleButton.setHorizontalTextPosition(AbstractButton.CENTER);

        }
        return buyShuttleButton;
    }

    private JButton getBuyMechButton() {
        if (buyMechButton == null) {
            buyMechButton = new JButton();
            FreeRealmUnitType unitType = freeMarsController.getFreeMarsModel().getRealm().getUnitTypeManager().getUnitType(5);
            buyMechButton.setAction(new BuyUnitFromEarthAction(freeMarsController, this, unitType));
            String unitPrice = new DecimalFormat().format(freeMarsController.getFreeMarsModel().getEarth().getEarthSellsAtPrice(unitType));
            buyMechButton.setText("<html>Mech<br>" + unitPrice + "</html>");
            Image mechImage = FreeMarsImageManager.getImage("UNIT_5_SW");
            mechImage = FreeMarsImageManager.createResizedCopy(mechImage, -1, 50, false, this);
            buyMechButton.setIcon(new ImageIcon(mechImage));
            buyMechButton.setVerticalTextPosition(AbstractButton.BOTTOM);
            buyMechButton.setHorizontalTextPosition(AbstractButton.CENTER);
        }
        return buyMechButton;
    }

    private JButton getBuyFreighterButton() {
        if (buyFreighterButton == null) {
            buyFreighterButton = new JButton();
            FreeRealmUnitType unitType = freeMarsController.getFreeMarsModel().getRealm().getUnitTypeManager().getUnitType(7);
            buyFreighterButton.setAction(new BuyUnitFromEarthAction(freeMarsController, this, unitType));
            String unitPrice = new DecimalFormat().format(freeMarsController.getFreeMarsModel().getEarth().getEarthSellsAtPrice(unitType));
            buyFreighterButton.setText("<html>Freighter<br>" + unitPrice + "</html>");
            Image scoutImage = FreeMarsImageManager.getImage("UNIT_7_SW");
            scoutImage = FreeMarsImageManager.createResizedCopy(scoutImage, -1, 50, false, this);
            buyFreighterButton.setIcon(new ImageIcon(scoutImage));
            buyFreighterButton.setVerticalTextPosition(AbstractButton.BOTTOM);
            buyFreighterButton.setHorizontalTextPosition(AbstractButton.CENTER);
        }
        return buyFreighterButton;
    }

    private JButton getBuyBulkFreighterButton() {
        if (buyBulkFreighterButton == null) {
            buyBulkFreighterButton = new JButton();
            FreeRealmUnitType unitType = freeMarsController.getFreeMarsModel().getRealm().getUnitTypeManager().getUnitType(8);
            buyBulkFreighterButton.setAction(new BuyUnitFromEarthAction(freeMarsController, this, unitType));
            String unitPrice = new DecimalFormat().format(freeMarsController.getFreeMarsModel().getEarth().getEarthSellsAtPrice(unitType));
            buyBulkFreighterButton.setText("<html>Bulk freighter<br>" + unitPrice + "</html>");
            Image bulkFreighterImage = FreeMarsImageManager.getImage("UNIT_8_SW");
            bulkFreighterImage = FreeMarsImageManager.createResizedCopy(bulkFreighterImage, -1, 50, false, this);
            buyBulkFreighterButton.setIcon(new ImageIcon(bulkFreighterImage));
            buyBulkFreighterButton.setVerticalTextPosition(AbstractButton.BOTTOM);
            buyBulkFreighterButton.setHorizontalTextPosition(AbstractButton.CENTER);
        }
        return buyBulkFreighterButton;
    }

    private JButton getBuyColonizerButton() {
        if (buyColonizerButton == null) {
            buyColonizerButton = new JButton();
            FreeRealmUnitType unitType = freeMarsController.getFreeMarsModel().getRealm().getUnitTypeManager().getUnitType(0);
            buyColonizerButton.setAction(new BuyUnitFromEarthAction(freeMarsController, this, unitType));
            String unitPrice = new DecimalFormat().format(freeMarsController.getFreeMarsModel().getEarth().getEarthSellsAtPrice(unitType));
            buyColonizerButton.setText("<html>Colonizer<br>" + unitPrice + "</html>");
            Image colonizerImage = FreeMarsImageManager.getImage("UNIT_0_SW");
            colonizerImage = FreeMarsImageManager.createResizedCopy(colonizerImage, -1, 50, false, this);
            buyColonizerButton.setIcon(new ImageIcon(colonizerImage));
            buyColonizerButton.setVerticalTextPosition(AbstractButton.BOTTOM);
            buyColonizerButton.setHorizontalTextPosition(AbstractButton.CENTER);
        }
        return buyColonizerButton;
    }

    private JButton getBuyTransporterButton() {
        if (buyTransporterButton == null) {
            buyTransporterButton = new JButton();
            FreeRealmUnitType unitType = freeMarsController.getFreeMarsModel().getRealm().getUnitTypeManager().getUnitType(2);
            buyTransporterButton.setAction(new BuyUnitFromEarthAction(freeMarsController, this, unitType));
            String unitPrice = new DecimalFormat().format(freeMarsController.getFreeMarsModel().getEarth().getEarthSellsAtPrice(unitType));
            buyTransporterButton.setText("<html>Transporter<br>" + unitPrice + "</html>");
            Image colonizerImage = FreeMarsImageManager.getImage("UNIT_2_SW");
            colonizerImage = FreeMarsImageManager.createResizedCopy(colonizerImage, -1, 50, false, this);
            buyTransporterButton.setIcon(new ImageIcon(colonizerImage));
            buyTransporterButton.setVerticalTextPosition(AbstractButton.BOTTOM);
            buyTransporterButton.setHorizontalTextPosition(AbstractButton.CENTER);
        }
        return buyTransporterButton;
    }

    private EarthPricesPanel getEarthPricesPanel() {
        if (pageEndPanel == null) {
            pageEndPanel = new EarthPricesPanel(freeMarsController.getFreeMarsModel());
            pageEndPanel.update();
        }
        return pageEndPanel;
    }

    private void updateUnitPurchasePanel() {
        Player player = freeMarsController.getFreeMarsModel().getRealm().getPlayerManager().getActivePlayer();
        FreeRealmUnitType shuttleUnitType = freeMarsController.getFreeMarsModel().getRealm().getUnitTypeManager().getUnitType("Shuttle");
        int shuttlePrice = freeMarsController.getFreeMarsModel().getEarth().getEarthSellsAtPrice(shuttleUnitType);
        if (player.getWealth() >= shuttlePrice) {
            getBuyShuttleButton().setEnabled(true);
        } else {
            getBuyShuttleButton().setEnabled(false);
        }
        FreeRealmUnitType freighterUnitType = freeMarsController.getFreeMarsModel().getRealm().getUnitTypeManager().getUnitType("Freighter");
        int freighterPrice = freeMarsController.getFreeMarsModel().getEarth().getEarthSellsAtPrice(freighterUnitType);
        if (player.getWealth() >= freighterPrice) {
            getBuyFreighterButton().setEnabled(true);
        } else {
            getBuyFreighterButton().setEnabled(false);
        }

        FreeRealmUnitType bulkFreighterUnitType = freeMarsController.getFreeMarsModel().getRealm().getUnitTypeManager().getUnitType("Bulk freighter");
        int bulkFreighterPrice = freeMarsController.getFreeMarsModel().getEarth().getEarthSellsAtPrice(bulkFreighterUnitType);
        if (player.getWealth() >= bulkFreighterPrice) {
            getBuyBulkFreighterButton().setEnabled(true);
        } else {
            getBuyBulkFreighterButton().setEnabled(false);
        }
        FreeRealmUnitType mechUnitType = freeMarsController.getFreeMarsModel().getRealm().getUnitTypeManager().getUnitType("Mech");
        int mechPrice = freeMarsController.getFreeMarsModel().getEarth().getEarthSellsAtPrice(mechUnitType);
        if (player.getWealth() >= mechPrice) {
            getBuyMechButton().setEnabled(true);
        } else {
            getBuyMechButton().setEnabled(false);
        }
        FreeRealmUnitType colonizerUnitType = freeMarsController.getFreeMarsModel().getRealm().getUnitTypeManager().getUnitType("Colonizer");
        int colonizerPrice = freeMarsController.getFreeMarsModel().getEarth().getEarthSellsAtPrice(colonizerUnitType);
        if (player.getWealth() >= colonizerPrice) {
            getBuyColonizerButton().setEnabled(true);
        } else {
            getBuyColonizerButton().setEnabled(false);
        }
        FreeRealmUnitType transporterUnitType = freeMarsController.getFreeMarsModel().getRealm().getUnitTypeManager().getUnitType("Transporter");
        int transporterPrice = freeMarsController.getFreeMarsModel().getEarth().getEarthSellsAtPrice(transporterUnitType);
        if (player.getWealth() >= transporterPrice) {
            getBuyTransporterButton().setEnabled(true);
        } else {
            getBuyTransporterButton().setEnabled(false);
        }
    }

}
