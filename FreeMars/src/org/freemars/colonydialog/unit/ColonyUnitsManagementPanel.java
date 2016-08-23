package org.freemars.colonydialog.unit;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.Iterator;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.border.BevelBorder;
import javax.swing.event.MouseInputAdapter;

import org.freemars.colonydialog.ColonyDialogModel;
import org.freemars.colonydialog.controller.DisplayLoadColonistsPopupAction;
import org.freemars.colonydialog.controller.DisplayUnloadColonistsPopupAction;
import org.freemars.colonydialog.controller.ResourceTransferHandler;
import org.freemars.colonydialog.controller.UnloadUnitToColonyAction;
import org.freemars.controller.FreeMarsController;
import org.freemars.ui.image.FreeMarsImageManager;
import org.freerealm.Container;
import org.freerealm.command.SetActiveUnitCommand;
import org.freerealm.executor.order.Sentry;
import org.freerealm.unit.Order;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class ColonyUnitsManagementPanel extends JPanel {

    private ColonyDialogModel model;
    private JScrollPane cityUnitsScrollPane;
    private SelectorPanel<Unit> unitsPanel;
    private JPanel unitCargoPanel;
    private ResourceQuantitySelectorPanel resourceQuantitySelectorPanel;
    private JScrollPane unitResourcesScrollPane;
    private ResourceStorerPanel unitResourceQuantitiesPanel;
    private JPopupMenu unitOrdersPopupMenu;
    private JPanel unitSpecialCargoPanel;
    private JPanel numberOfColonistsPanel;
    private JLabel numberOfColonistsLabel;
    private JButton loadColonistsButton;
    private JButton unloadColonistsButton;
    private JPanel containedUnitsPanel;
    private final FreeMarsController freeMarsController;

    public ColonyUnitsManagementPanel(FreeMarsController freeMarsController) {
        super(new GridLayout(2, 1));
        this.freeMarsController = freeMarsController;
        setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        initializeWidgets();
    }

    public void refresh() {
        if (model != null) {
            getColonyUnitsPanel().removeAll();
            Iterator<Unit> unitsIterator = model.getRealm().getTile(model.getColony().getCoordinate()).getUnitsIterator();
            int addedUnitCount = 0;
            while (unitsIterator.hasNext()) {
                Unit unit = unitsIterator.next();
                getColonyUnitsPanel().addSelectable(unit);
                addedUnitCount = addedUnitCount + 1;
                JToggleButton toggleButton = getColonyUnitsPanel().getToggleButton(unit);
                Image unitImage;
                String unitText = unit.getName();
                Order order = unit.getCurrentOrder();
                if (order == null) {
                    order = unit.getNextOrder();
                }
                if (order != null) {
                    unitText = unitText + " (" + order.getSymbol();
                    if (order.getRemainingTurns() > 0) {
                        unitText = unitText + ":" + order.getRemainingTurns();
                    }
                    unitText = unitText + ")";
                    if (order instanceof Sentry) {
                        unitImage = FreeMarsImageManager.getImage(unit, true);
                    } else {
                        unitImage = FreeMarsImageManager.getImage(unit, false);
                    }
                } else {
                    unitImage = FreeMarsImageManager.getImage(unit, false);
                }
                unitImage = FreeMarsImageManager.createResizedCopy(unitImage, -1, 50, false, this);
                toggleButton.setIcon(new ImageIcon(unitImage));
                toggleButton.setBorderPainted(false);
                toggleButton.setVerticalTextPosition(AbstractButton.BOTTOM);
                toggleButton.setHorizontalTextPosition(AbstractButton.CENTER);
                toggleButton.setText(unitText);
                toggleButton.setMinimumSize(new Dimension(0, 120));
                toggleButton.setToolTipText(unitText);
                toggleButton.setPreferredSize(new Dimension(0, 120));
                int colonyDialogUnitSelectionType = Integer.parseInt(freeMarsController.getFreeMarsModel().getFreeMarsPreferences().getProperty("colony_dialog_unit_selection_type"));
                if (colonyDialogUnitSelectionType == 1) {
                    toggleButton.addMouseMotionListener(new MouseInputAdapter() {
                        @Override
                        public void mouseMoved(MouseEvent e) {
                            JToggleButton toggleButton = (JToggleButton) e.getSource();
                            Unit unit = getColonyUnitsPanel().getSelectable(toggleButton);
                            if (unit != null && !unit.equals(getColonyUnitsPanel().getSelectedItem()) && unit.getType().getProperty("ContainerProperty") != null) {
                                toggleButton.requestFocus();
                                getColonyUnitsPanel().setSelectedObject(unit);
                                model.setSelectedUnit(getColonyUnitsPanel().getSelectedItem());
                                freeMarsController.execute(new SetActiveUnitCommand(model.getColony().getPlayer(), getColonyUnitsPanel().getSelectedItem()));
                                unitOrdersPopupMenu.setVisible(false);
                            }
                        }
                    });
                }
                if (unit.getType().getProperty("ContainerProperty") != null) {
                    toggleButton.requestFocus();
                    getColonyUnitsPanel().setSelectedObject(unit);
                    model.setSelectedUnit(getColonyUnitsPanel().getSelectedItem());
                    freeMarsController.execute(new SetActiveUnitCommand(model.getColony().getPlayer(), getColonyUnitsPanel().getSelectedItem()));
                }

            }
            if (addedUnitCount < 3) {
                for (int i = addedUnitCount; i < 3; i++) {
                    getColonyUnitsPanel().add(new JLabel());
                }
            }
            getColonyUnitsPanel().addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    model.setSelectedUnit(getColonyUnitsPanel().getSelectedItem());
                    freeMarsController.execute(new SetActiveUnitCommand(model.getColony().getPlayer(), getColonyUnitsPanel().getSelectedItem()));
                    JToggleButton toggleButton = (JToggleButton) e.getSource();
                    unitOrdersPopupMenu.show(toggleButton, toggleButton.getWidth(), 10);
                }
            });

        }
    }

    public void refreshContainedUnits(Unit unit) {
        getContainedUnitsPanel().removeAll();
        Iterator<Integer> iterator = unit.getContainedUnitsIterator();
        while (iterator.hasNext()) {
            int containedUnitId = iterator.next();
            Unit containedUnit = unit.getPlayer().getUnit(containedUnitId);
            JButton containedUnitButton = new JButton();
            Image containedUnitImage = FreeMarsImageManager.getImage(containedUnit);
            containedUnitImage = FreeMarsImageManager.createResizedCopy(containedUnitImage, -1, 24, false, this);
            containedUnitButton.setAction(new UnloadUnitToColonyAction(freeMarsController, model, unit, containedUnit));
            containedUnitButton.setIcon(new ImageIcon(containedUnitImage));
            containedUnitButton.setToolTipText(containedUnit.toString());
            getContainedUnitsPanel().add(containedUnitButton);
        }
        getUnitSpecialCargoPanel().repaint();
        getUnitSpecialCargoPanel().revalidate();
    }

    public void refreshLoadedColonists(Unit selectedUnit) {
        getUnloadColonistsButton().setAction(new DisplayUnloadColonistsPopupAction(freeMarsController, model));
        Icon unloadColonistsIcon = new ImageIcon(FreeMarsImageManager.getImage("UNLOAD_COLONISTS"));
        getUnloadColonistsButton().setIcon(unloadColonistsIcon);
        getUnloadColonistsButton().setToolTipText("Unload colonists");
        if (selectedUnit.getContainedPopulation() > 0) {
            Icon colonistIcon = new ImageIcon(FreeMarsImageManager.getImage("COLONIST"));
            getNumberOfColonistsLabel().setIcon(colonistIcon);
            getNumberOfColonistsLabel().setText(String.valueOf(selectedUnit.getContainedPopulation()));
            getNumberOfColonistsLabel().setToolTipText(selectedUnit.getContainedPopulation() + " colonists onboard");
            getUnloadColonistsButton().setEnabled(true);
        } else {
            getNumberOfColonistsLabel().setIcon(null);
            getNumberOfColonistsLabel().setText("");
            getNumberOfColonistsLabel().setToolTipText("");
            getUnloadColonistsButton().setEnabled(false);
        }
        int weightPerColonizer = Integer.parseInt(freeMarsController.getFreeMarsModel().getRealm().getProperty("weight_per_citizen"));
        getLoadColonistsButton().setAction(new DisplayLoadColonistsPopupAction(freeMarsController, model));
        Icon loadColonistsIcon = new ImageIcon(FreeMarsImageManager.getImage("LOAD_COLONISTS"));
        getLoadColonistsButton().setIcon(loadColonistsIcon);
        getLoadColonistsButton().setToolTipText("Load colonists");
        if (selectedUnit.getRemainingCapacity() >= weightPerColonizer) {
            getLoadColonistsButton().setEnabled(true);
        } else {
            getLoadColonistsButton().setEnabled(false);
        }
    }

    public void setModel(ColonyDialogModel model) {
        this.model = model;
        getResourceQuantitySelectorPanel().setModel(model);
    }

    public void setUnitOrdersPopupMenu(JPopupMenu unitOrdersPopupMenu) {
        this.unitOrdersPopupMenu = unitOrdersPopupMenu;
    }

    public void setUnitResourcesPanelVisible(boolean visible) {
        getUnitCargoPanel().setVisible(visible);
    }

    public void setMaxResourceQuantity(int capacity) {
        getResourceQuantitySelectorPanel().setMaxCapacity(capacity);
    }

    public void setContainer(Container container) {
        getUnitResourceQuantitiesPanel().setContainer(container);
    }

    public void setResourceTransferHandler(ResourceTransferHandler resourceTransferHandler) {
        getUnitResourceQuantitiesPanel().setTransferHandler(resourceTransferHandler);
        getColonyUnitsPanel().setTransferHandler(resourceTransferHandler);
    }

    private void initializeWidgets() {
        add(getCityUnitsScrollPane());
        add(getUnitCargoPanel());
    }

    private JScrollPane getCityUnitsScrollPane() {
        if (cityUnitsScrollPane == null) {
            cityUnitsScrollPane = new JScrollPane(getColonyUnitsPanel());
            cityUnitsScrollPane.setBorder(BorderFactory.createTitledBorder("Units & cargo"));
        }
        return cityUnitsScrollPane;
    }

    private SelectorPanel<Unit> getColonyUnitsPanel() {
        if (unitsPanel == null) {
            unitsPanel = new SelectorPanel<Unit>(new GridLayout(0, 3));
        }
        return unitsPanel;
    }

    private JPanel getUnitCargoPanel() {
        if (unitCargoPanel == null) {
            unitCargoPanel = new JPanel(new BorderLayout(10, 0));
            unitCargoPanel.add(getResourceQuantitySelectorPanel(), BorderLayout.LINE_START);
            unitCargoPanel.add(getUnitResourcesScrollPane(), BorderLayout.CENTER);
            unitCargoPanel.add(getUnitSpecialCargoPanel(), BorderLayout.PAGE_END);
        }
        return unitCargoPanel;
    }

    private ResourceQuantitySelectorPanel getResourceQuantitySelectorPanel() {
        if (resourceQuantitySelectorPanel == null) {
            resourceQuantitySelectorPanel = new ResourceQuantitySelectorPanel();
        }
        return resourceQuantitySelectorPanel;
    }

    private JScrollPane getUnitResourcesScrollPane() {
        if (unitResourcesScrollPane == null) {
            unitResourcesScrollPane = new JScrollPane(getUnitResourceQuantitiesPanel());
        }
        return unitResourcesScrollPane;
    }

    private JPanel getUnitSpecialCargoPanel() {
        if (unitSpecialCargoPanel == null) {
            unitSpecialCargoPanel = new JPanel(new GridLayout(1, 0, 3, 0));
            unitSpecialCargoPanel.setPreferredSize(new Dimension(0, 32));
            unitSpecialCargoPanel.add(getNumberOfColonistsPanel());
            unitSpecialCargoPanel.add(getContainedUnitsPanel());
        }
        return unitSpecialCargoPanel;
    }

    private JPanel getNumberOfColonistsPanel() {
        if (numberOfColonistsPanel == null) {
            numberOfColonistsPanel = new JPanel(new GridLayout(1, 0, 3, 0));
            numberOfColonistsPanel.add(getNumberOfColonistsLabel());
            numberOfColonistsPanel.add(getLoadColonistsButton());
            numberOfColonistsPanel.add(getUnloadColonistsButton());
        }
        return numberOfColonistsPanel;
    }

    private JPanel getContainedUnitsPanel() {
        if (containedUnitsPanel == null) {
            containedUnitsPanel = new JPanel(new GridLayout(1, 0, 3, 0));
        }
        return containedUnitsPanel;
    }

    private JLabel getNumberOfColonistsLabel() {
        if (numberOfColonistsLabel == null) {
            numberOfColonistsLabel = new JLabel();
        }
        return numberOfColonistsLabel;
    }

    private JButton getLoadColonistsButton() {
        if (loadColonistsButton == null) {
            loadColonistsButton = new JButton();
        }
        return loadColonistsButton;
    }

    private JButton getUnloadColonistsButton() {
        if (unloadColonistsButton == null) {
            unloadColonistsButton = new JButton();
        }
        return unloadColonistsButton;
    }

    private ResourceStorerPanel getUnitResourceQuantitiesPanel() {
        if (unitResourceQuantitiesPanel == null) {
            unitResourceQuantitiesPanel = new ResourceStorerPanel();
        }
        return unitResourceQuantitiesPanel;
    }
}
