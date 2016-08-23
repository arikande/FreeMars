package org.freemars.colonydialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Iterator;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import org.freemars.ui.image.FreeMarsImageManager;
import org.freemars.ui.image.ImagePanel;
import org.freemars.ui.util.FreeMarsDialog;
import org.freemars.ui.util.FreeMarsTheme;
import org.freerealm.modifier.Modifier;
import org.freerealm.property.RemoveSettlementImprovementProperty;
import org.freerealm.resource.Resource;
import org.freerealm.settlement.SettlementBuildable;
import org.freerealm.settlement.SettlementBuildableCostCalculator;
import org.freerealm.settlement.improvement.SettlementImprovementType;
import org.freerealm.unit.FreeRealmUnitType;

/**
 *
 * @author Deniz ARIKAN
 */
public class ProductionQueueManagementDialog extends FreeMarsDialog {

    private ColonyDialogModel model;
    private static final int FRAME_WIDTH = 950;
    private static final int FRAME_HEIGHT = 580;
    private JPanel mainPanel;
    private JPanel footerPanel;
    private JPanel buildableUnitsPanel;
    private JScrollPane buildableUnitsScrollPane;
    private JList buildableUnitsList;
    private DefaultListModel buildableUnitsListModel;
    private JPanel currentQueuePanel;
    private JPanel currentProductionPanel;
    private ImagePanel currentProductionImagePanel;
    private JPanel currentProductionInfoPanel;
    private JLabel currentProductionLabel;
    private JProgressBar currentProductionProgressBar;
    private JScrollPane currentQueueScrollPane;
    private JList currentQueueList;
    private DefaultListModel currentQueueListModel;
    private JPanel buildableImprovementsPanel;
    private DefaultListModel buildableImprovementsListModel;
    private JScrollPane buildableImprovementsScrollPane;
    private JList buildableImprovementsList;
    private JCheckBox displayAllImprovementsCheckBox;
    private JCheckBox displayAllUnitsCheckBox;
    private JButton confirmButton;
    private final HashMap<SettlementBuildable, Image> colonyImprovementImages;

    public ProductionQueueManagementDialog(Dialog owner) {
        super(owner);
        colonyImprovementImages = new HashMap<SettlementBuildable, Image>();
        setModal(true);
        setTitle("Production queue");
        initializeWidgets();
    }

    public void display() {
        super.display(FRAME_WIDTH, FRAME_HEIGHT);
    }

    public void setModel(ColonyDialogModel model) {
        this.model = model;
    }

    public void update() {
        updateCurrentProduction();
        updateCurrentQueueList();
        updateBuildableImprovementsList();
        updateBuildableUnitsList();
        repaint();
    }

    public void addItemToCurrentQueue(Object item) {
        getCurrentQueueListModel().addElement(item);
    }

    public void removeItemFromCurrentQueue(Object item) {
        getCurrentQueueListModel().removeElement(item);
    }

    public void removeItemFromBuildableImprovements(Object item) {
        getBuildableImprovementsListModel().removeElement(item);
    }

    public void addBuildableUnitsListMouseListener(MouseAdapter mouseAdapter) {
        getBuildableUnitsList().addMouseListener(mouseAdapter);
    }

    public void addBuildableImprovementsListMouseListener(MouseAdapter mouseAdapter) {
        getBuildableImprovementsList().addMouseListener(mouseAdapter);
    }

    public void addCurrentQueueListMouseListener(MouseAdapter mouseAdapter) {
        getCurrentQueueList().addMouseListener(mouseAdapter);
    }

    private void updateCurrentProduction() {
        SettlementBuildable currentProduction = model.getColony().getCurrentProduction();
        if (currentProduction != null) {
            int currentProductionCost = new SettlementBuildableCostCalculator(currentProduction, new Modifier[]{model.getColony().getPlayer()}).getCost();
            Image currentProductionImage = FreeMarsImageManager.getImage(currentProduction);
            BufferedImage resizedImprovementImage = FreeMarsImageManager.createResizedCopy(currentProductionImage, -1, 70, false, this);
            getCurrentProductionImagePanel().setImage(resizedImprovementImage);
            getCurrentProductionLabel().setText(currentProduction.toString());
            getCurrentProductionProgressBar().setMaximum(currentProductionCost);
            getCurrentProductionProgressBar().setValue(model.getColony().getProductionPoints());
            getCurrentProductionProgressBar().setString(String.valueOf(model.getColony().getProductionPoints()) + " / " + String.valueOf(currentProductionCost));
        } else {
            getCurrentProductionLabel().setText("Nothing");
            getCurrentProductionImagePanel().setImage(null);
            getCurrentProductionProgressBar().setMaximum(0);
            getCurrentProductionProgressBar().setValue(0);
            getCurrentProductionProgressBar().setString("");
        }
    }

    private void updateCurrentQueueList() {
        getCurrentQueueListModel().removeAllElements();
        Iterator<SettlementBuildable> settlementBuildableIterator = model.getColony().getProductionQueueIterator();
        while (settlementBuildableIterator.hasNext()) {
            SettlementBuildable settlementBuildable = settlementBuildableIterator.next();
            getCurrentQueueListModel().addElement(settlementBuildable);
        }
    }

    private void updateBuildableImprovementsList() {
        getBuildableImprovementsListModel().removeAllElements();
        Iterator<SettlementImprovementType> settlementImprovementTypeIterator = model.getFreeMarsModel().getRealm().getSettlementImprovementManager().getImprovementsIterator();
        while (settlementImprovementTypeIterator.hasNext()) {
            SettlementImprovementType settlementImprovementType = settlementImprovementTypeIterator.next();
            if (!getCurrentQueueListModel().contains(settlementImprovementType)) {
                if (model.getColony().canStartBuild(settlementImprovementType)) {
                    getBuildableImprovementsListModel().addElement(settlementImprovementType);
                } else {
                    if (getDisplayAllImprovementsCheckBox().isSelected()) {
                        if (!model.getColony().hasImprovementType(settlementImprovementType)) {
                            getBuildableImprovementsListModel().addElement(settlementImprovementType);
                        }
                    }
                }
            }
        }
    }

    private void updateBuildableUnitsList() {
        getBuildableUnitsListModel().removeAllElements();
        Iterator<FreeRealmUnitType> unitTypeIterator = model.getRealm().getUnitTypeManager().getUnitTypesIterator();
        while (unitTypeIterator.hasNext()) {
            FreeRealmUnitType unitType = unitTypeIterator.next();
            if (unitType.getProperty("buildable_property") != null) {
                if (model.getColony().canStartBuild(unitType) || getDisplayAllUnitsCheckBox().isSelected()) {
                    getBuildableUnitsListModel().addElement(unitType);
                }
            }
        }
    }

    private void initializeWidgets() {
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(getMainPanel(), BorderLayout.CENTER);
        getContentPane().add(getFooterPanel(), BorderLayout.PAGE_END);
    }

    private JPanel getMainPanel() {
        if (mainPanel == null) {
            mainPanel = new JPanel(new GridLayout(0, 3));
            mainPanel.add(getBuildableUnitsPanel());
            mainPanel.add(getCurrentQueuePanel());
            mainPanel.add(getBuildableImprovementsPanel());
        }
        return mainPanel;
    }

    private JPanel getFooterPanel() {
        if (footerPanel == null) {
            footerPanel = new JPanel();
            footerPanel.add(getConfirmButton());
        }
        return footerPanel;
    }

    private JButton getConfirmButton() {
        if (confirmButton == null) {
            confirmButton = new JButton("OK");
            confirmButton.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    dispose();
                }
            });
        }
        return confirmButton;
    }

    private JPanel getBuildableUnitsPanel() {
        if (buildableUnitsPanel == null) {
            buildableUnitsPanel = new JPanel(new BorderLayout());
            buildableUnitsPanel.setBorder(BorderFactory.createTitledBorder("Available units"));
            buildableUnitsPanel.add(getBuildableUnitsScrollPane(), BorderLayout.CENTER);
            buildableUnitsPanel.add(getDisplayAllUnitsCheckBox(), BorderLayout.PAGE_END);
        }
        return buildableUnitsPanel;
    }

    private JScrollPane getBuildableUnitsScrollPane() {
        if (buildableUnitsScrollPane == null) {
            buildableUnitsScrollPane = new JScrollPane(getBuildableUnitsList());
        }
        return buildableUnitsScrollPane;
    }

    private JList getBuildableUnitsList() {
        if (buildableUnitsList == null) {
            buildableUnitsList = new JList();
            buildableUnitsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            buildableUnitsList.setCellRenderer(new SettlementBuildableCellRenderer());
            buildableUnitsList.setModel(getBuildableUnitsListModel());
        }
        return buildableUnitsList;
    }

    private DefaultListModel getBuildableUnitsListModel() {
        if (buildableUnitsListModel == null) {
            buildableUnitsListModel = new DefaultListModel();
        }
        return buildableUnitsListModel;
    }

    private JPanel getCurrentQueuePanel() {
        if (currentQueuePanel == null) {
            currentQueuePanel = new JPanel(new BorderLayout(0, 20));
            currentQueuePanel.setBorder(BorderFactory.createTitledBorder("Queue"));
            currentQueuePanel.add(getCurrentProductionPanel(), BorderLayout.PAGE_START);
            currentQueuePanel.add(getCurrentQueueScrollPane(), BorderLayout.CENTER);
        }
        return currentQueuePanel;
    }

    private JPanel getCurrentProductionPanel() {
        if (currentProductionPanel == null) {
            currentProductionPanel = new JPanel(new BorderLayout(0, 5));
            currentProductionPanel.add(getCurrentProductionImagePanel(), BorderLayout.CENTER);
            currentProductionPanel.add(getCurrentProductionInfoPanel(), BorderLayout.PAGE_END);
        }
        return currentProductionPanel;
    }

    private ImagePanel getCurrentProductionImagePanel() {
        if (currentProductionImagePanel == null) {
            currentProductionImagePanel = new ImagePanel();
            currentProductionImagePanel.setPreferredSize(new Dimension(70, 70));
        }
        return currentProductionImagePanel;
    }

    private JPanel getCurrentProductionInfoPanel() {
        if (currentProductionInfoPanel == null) {
            currentProductionInfoPanel = new JPanel(new GridLayout(2, 1, 0, 0));
            currentProductionInfoPanel.add(getCurrentProductionLabel());
            currentProductionInfoPanel.add(getCurrentProductionProgressBar());
        }
        return currentProductionInfoPanel;
    }

    private JLabel getCurrentProductionLabel() {
        if (currentProductionLabel == null) {
            currentProductionLabel = new JLabel();
            currentProductionLabel.setHorizontalTextPosition(SwingConstants.CENTER);
            currentProductionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        }
        return currentProductionLabel;
    }

    private JProgressBar getCurrentProductionProgressBar() {
        if (currentProductionProgressBar == null) {
            currentProductionProgressBar = new JProgressBar();
            currentProductionProgressBar.setStringPainted(true);
        }
        return currentProductionProgressBar;
    }

    private JScrollPane getCurrentQueueScrollPane() {
        if (currentQueueScrollPane == null) {
            currentQueueScrollPane = new JScrollPane(getCurrentQueueList());
        }
        return currentQueueScrollPane;
    }

    private JList getCurrentQueueList() {
        if (currentQueueList == null) {
            currentQueueList = new JList();
            currentQueueList.setCellRenderer(new SettlementBuildableCellRenderer());
            currentQueueList.setModel(getCurrentQueueListModel());
        }
        return currentQueueList;
    }

    private DefaultListModel getCurrentQueueListModel() {
        if (currentQueueListModel == null) {
            currentQueueListModel = new DefaultListModel();
        }
        return currentQueueListModel;
    }

    private JPanel getBuildableImprovementsPanel() {
        if (buildableImprovementsPanel == null) {
            buildableImprovementsPanel = new JPanel(new BorderLayout());
            buildableImprovementsPanel.setBorder(BorderFactory.createTitledBorder("Available improvements"));
            buildableImprovementsPanel.add(getBuildableImprovementsScrollPane(), BorderLayout.CENTER);
            buildableImprovementsPanel.add(getDisplayAllImprovementsCheckBox(), BorderLayout.PAGE_END);
        }
        return buildableImprovementsPanel;
    }

    private JScrollPane getBuildableImprovementsScrollPane() {
        if (buildableImprovementsScrollPane == null) {
            buildableImprovementsScrollPane = new JScrollPane(getBuildableImprovementsList());
        }
        return buildableImprovementsScrollPane;
    }

    private JList getBuildableImprovementsList() {
        if (buildableImprovementsList == null) {
            buildableImprovementsList = new JList();
            buildableImprovementsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            buildableImprovementsList.setCellRenderer(new SettlementBuildableCellRenderer());
            buildableImprovementsList.setModel(getBuildableImprovementsListModel());
        }
        return buildableImprovementsList;
    }

    private JCheckBox getDisplayAllImprovementsCheckBox() {
        if (displayAllImprovementsCheckBox == null) {
            displayAllImprovementsCheckBox = new JCheckBox("Display all");
            displayAllImprovementsCheckBox.addItemListener(new ItemListener() {

                public void itemStateChanged(ItemEvent e) {
                    updateBuildableImprovementsList();
                }
            });
        }
        return displayAllImprovementsCheckBox;
    }

    private JCheckBox getDisplayAllUnitsCheckBox() {
        if (displayAllUnitsCheckBox == null) {
            displayAllUnitsCheckBox = new JCheckBox("Display all");
            displayAllUnitsCheckBox.addItemListener(new ItemListener() {

                public void itemStateChanged(ItemEvent e) {
                    updateBuildableUnitsList();
                }
            });
        }
        return displayAllUnitsCheckBox;
    }

    private DefaultListModel getBuildableImprovementsListModel() {
        if (buildableImprovementsListModel == null) {
            buildableImprovementsListModel = new DefaultListModel();
        }
        return buildableImprovementsListModel;
    }

    class SettlementBuildableCellRenderer implements ListCellRenderer {

        private Image prepareSettlementImage(SettlementBuildable settlementBuildable, Component component) {
            Image image;
            if (colonyImprovementImages.containsKey(settlementBuildable)) {
                image = colonyImprovementImages.get(settlementBuildable);
            } else {
                image = FreeMarsImageManager.getImage(settlementBuildable, !model.getColony().canStartBuild(settlementBuildable));
                image = FreeMarsImageManager.createResizedCopy(image, -1, 60, false, component);
                if (settlementBuildable instanceof SettlementImprovementType) {
                    SettlementImprovementType settlementImprovementType = (SettlementImprovementType) settlementBuildable;
                    RemoveSettlementImprovementProperty removeSettlementImprovement = (RemoveSettlementImprovementProperty) settlementImprovementType.getProperty(RemoveSettlementImprovementProperty.NAME);
                    if (removeSettlementImprovement != null) {
                        Image greenUpArrow = FreeMarsImageManager.getImage("GREEN_UP_ARROW", 15, 15);
                        image = FreeMarsImageManager.combineImages(new Image[]{image, greenUpArrow}, mainPanel);
                    }
                }
                colonyImprovementImages.put(settlementBuildable, image);
            }
            return image;
        }

        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            SettlementBuildable settlementBuildable = (SettlementBuildable) value;
            JPanel component = new JPanel(new BorderLayout(10, 0));
            JLabel nameLabel = new JLabel(settlementBuildable.getName());
            NumberFormat formatter = new DecimalFormat();
            int productionCost = new SettlementBuildableCostCalculator(settlementBuildable, new Modifier[]{model.getColony().getPlayer()}).getCost();
            JPanel productionCostPanel = new JPanel(new GridLayout(0, 1));
            productionCostPanel.setBackground(Color.white);
            JLabel productionPointCostLabel = new JLabel(formatter.format(productionCost) + "  ", SwingConstants.RIGHT);
            productionCostPanel.add(productionPointCostLabel);
            JLabel populationCostLabel = new JLabel(settlementBuildable.getPopulationCost() + " colonists  ", SwingConstants.RIGHT);
            if (settlementBuildable.getPopulationCost() > 0) {
                productionCostPanel.add(populationCostLabel);
            }
            if (settlementBuildable.getBuildCostResourceCount() > 0) {
                JPanel buildCostResourcesPanel = new JPanel(new FlowLayout(SwingConstants.RIGHT));
                buildCostResourcesPanel.setBackground(Color.white);
                Iterator<Integer> buildCostResourceIdsIterator = settlementBuildable.getBuildCostResourceIdsIterator();
                while (buildCostResourceIdsIterator.hasNext()) {
                    Integer resourceId = buildCostResourceIdsIterator.next();
                    Resource resource = model.getRealm().getResourceManager().getResource(resourceId);
                    Image image = FreeMarsImageManager.getImage(resource, !model.getColony().canStartBuild(settlementBuildable));
                    image = FreeMarsImageManager.createResizedCopy(image, -1, 15, false, null);
                    JLabel buildCostResourceQuantityLabel = new JLabel(String.valueOf(settlementBuildable.getBuildCostResourceQuantity(resourceId)));
                    if (!model.getColony().canStartBuild(settlementBuildable)) {
                        buildCostResourceQuantityLabel.setForeground(Color.lightGray);
                    }
                    buildCostResourcesPanel.add(buildCostResourceQuantityLabel);
                    buildCostResourcesPanel.add(new JLabel(new ImageIcon(image)));
                }
                buildCostResourcesPanel.setBackground(isSelected ? new FreeMarsTheme().getPrimaryControl() : Color.white);
                productionCostPanel.add(buildCostResourcesPanel);
            }
            if (!model.getColony().canStartBuild(settlementBuildable)) {
                nameLabel.setForeground(Color.lightGray);
                productionPointCostLabel.setForeground(Color.lightGray);
                populationCostLabel.setForeground(Color.lightGray);
                if (settlementBuildable instanceof SettlementImprovementType) {
                    String toolTip = new ColonyImprovementToolTipBuilder(model.getRealm(), (SettlementImprovementType) settlementBuildable).getFullToolTip();
                    if (!toolTip.equals("")) {
                        component.setToolTipText(toolTip);
                    }
                } else if (settlementBuildable instanceof FreeRealmUnitType) {
                    String toolTip = new UnitToolTipBuilder(model.getRealm(), (FreeRealmUnitType) settlementBuildable).getPrerequisiteToolTip();
                    if (!toolTip.equals("")) {
                        component.setToolTipText(toolTip);
                    }
                }
            } else {
                if (settlementBuildable instanceof SettlementImprovementType) {
                    String toolTip = new ColonyImprovementToolTipBuilder(model.getRealm(), (SettlementImprovementType) settlementBuildable).getProductionToolTip();
                    if (!toolTip.equals("")) {
                        component.setToolTipText(toolTip);
                    }
                }
            }
            JLabel imageLabel = new JLabel(new ImageIcon(prepareSettlementImage(settlementBuildable, component)));
            JPanel imagePanel = new JPanel(new BorderLayout());
            imagePanel.setBackground(isSelected ? new FreeMarsTheme().getPrimaryControl() : Color.white);
            imagePanel.add(Box.createHorizontalStrut(10), BorderLayout.LINE_START);
            imagePanel.add(imageLabel);
            component.add(imagePanel, BorderLayout.LINE_START);
            component.add(nameLabel, BorderLayout.CENTER);
            productionCostPanel.setBackground(isSelected ? new FreeMarsTheme().getPrimaryControl() : Color.white);
            component.add(productionCostPanel, BorderLayout.LINE_END);
            component.setBackground(isSelected ? new FreeMarsTheme().getPrimaryControl() : Color.white);
            component.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 220, 220)));
            return component;
        }
    }
}
