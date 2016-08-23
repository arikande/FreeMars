package org.freemars.colonydialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeListener;
import javax.swing.event.MouseInputListener;
import org.freemars.colony.FreeMarsColony;
import org.freemars.colonydialog.controller.ResourceTransferHandler;
import org.freemars.colonydialog.controller.SetSelectedColonyAction;
import org.freemars.colonydialog.unit.ColonyUnitsManagementPanel;
import org.freemars.colonydialog.workforce.ColonyWorkForceManagementPanel;
import org.freemars.controller.FreeMarsController;
import org.freemars.ui.util.FreeMarsDialog;
import org.freerealm.resource.Resource;
import org.freerealm.settlement.Settlement;

/**
 *
 * @author Deniz ARIKAN
 */
public class ColonyDialog extends FreeMarsDialog implements Observer {

    private static final int FRAME_WIDTH = 1200;
    private static final int FRAME_HEIGHT = 706;
    private ColonyDialogModel model;
    private ColonyDialogHeaderPanel headerPanel;
    private JPanel mainPanel;
    private JPanel westPanel;
    private ColonyResourcesPanel colonyResourcesPanel;
    private ColonyProductionAndImprovementsPanel colonyProductionAndImprovementsPanel;
    private JPanel colonyWorkforcePanel;
    private ColonyWorkForceManagementPanel workForceManagementPanel;
    private JLabel freeWorkForceLabel;
    private ColonyUnitsManagementPanel colonyUnitsManagementPanel;
    private final FreeMarsController freeMarsController;

    public ColonyDialog(Frame owner, FreeMarsController freeMarsController) {
        super(owner);
        this.freeMarsController = freeMarsController;
        setModal(true);
        initializeWidgets();
    }

    public void display() {
        display(FRAME_WIDTH, FRAME_HEIGHT);
    }

    public void update(Observable o, Object arg) {
        Integer updateType = (Integer) arg;
        if (updateType != null) {
            switch (updateType) {
                case ColonyDialogModel.FULL_UPDATE:
                    updateSelectableColonies();
                    updateHeader();
                    updateWorkForce();
                    updateAutomanageButtons();
                    updateUnits();
                    updateUnitResources();
                    updateColonyResources();
                    updateColonyProductionAndImprovements();
                    break;
                case ColonyDialogModel.COLONY_CHANGE_UPDATE:
                    updateHeader();
                    updateWorkForce();
                    updateAutomanageButtons();
                    updateUnits();
                    updateUnitResources();
                    updateColonyResources();
                    updateColonyProductionAndImprovements();
                    break;
                case ColonyDialogModel.COLONY_RENAME_UPDATE:
                    getHeaderPanel().repaintColonySelectorComboBox();
                    break;
                case ColonyDialogModel.COLONY_UNITS_UPDATE:
                    updateUnits();
                    updateUnitResources();
                    updateColonyResources();
                    break;
                case ColonyDialogModel.SELECTED_UNIT_UPDATE:
                    updateUnitResources();
                    break;
                case ColonyDialogModel.WORKFORCE_UPDATE:
                    updateWorkForce();
                    getColonyProductionAndImprovementsPanel().repaint();
                    break;
                case ColonyDialogModel.UNIT_CARGO_CHANGE_UPDATE:
                    getHeaderPanel().setPopulation(model.getColony().getPopulation());
                    updateWorkForce();
                    updateColonyResources();
                    updateUnitResources();
                    getColonyProductionAndImprovementsPanel().repaint();
                    break;
                case ColonyDialogModel.CURRENT_PRODUCTION_UPDATE:
                    getColonyProductionAndImprovementsPanel().updateCurrentProduction();
                    getColonyProductionAndImprovementsPanel().repaint();
                    break;
                case ColonyDialogModel.COLONY_IMPROVEMENTS_UPDATE:
                    getColonyProductionAndImprovementsPanel().updateColonyImprovements();
                    updateColonyResources();
                    break;
                case ColonyDialogModel.COLONY_RESOURCES_UPDATE:
                    updateColonyResources();
                    break;
            }
        }
    }

    private void updateSelectableColonies() {
        getHeaderPanel().clearColonySelectorComboBox();
        Iterator<Settlement> iterator = model.getSelectableColoniesIterator();
        while (iterator.hasNext()) {
            Settlement settlement = iterator.next();
            getHeaderPanel().addSelectableColony(settlement);
        }
    }

    public void setPreviousColonyButtonAction(Action action) {
        getHeaderPanel().setPreviousColonyButtonAction(action);
    }

    public void setNextColonyButtonAction(Action action) {
        getHeaderPanel().setNextColonyButtonAction(action);
    }

    private void updateHeader() {
        getHeaderPanel().setSelectedColony(model.getColony());
        getHeaderPanel().setPopulation(model.getColony().getPopulation());
        getHeaderPanel().setEfficiency(model.getColony().getEfficiency());
        if (model.getSelectableColonyCount() == 1) {
            getHeaderPanel().setPreviousColonyButtonEnabled(false);
            getHeaderPanel().setNextColonyButtonEnabled(false);
        } else {
            FreeMarsColony previousColony = (FreeMarsColony) model.getPreviousColony(model.getColony());
            setPreviousColonyButtonAction(new SetSelectedColonyAction(freeMarsController, model, previousColony));
            FreeMarsColony nextColony = (FreeMarsColony) model.getNextColony(model.getColony());
            setNextColonyButtonAction(new SetSelectedColonyAction(freeMarsController, model, nextColony));
        }
    }

    private void updateWorkForce() {
        getFreeWorkForceLabel().setText(String.valueOf(model.getColony().getProductionWorkforce()));
        getWorkForceManagementPanel().setFreeWorkForce(String.valueOf(model.getColony().getProductionWorkforce()));
        getWorkForceManagementPanel().repaint();
    }

    private void updateUnits() {
        getColonyUnitsManagementPanel().refresh();
    }

    private void updateUnitResources() {
        getColonyUnitsManagementPanel().setUnitResourcesPanelVisible(false);
        if (model.getSelectedUnit() != null) {
            if (model.getSelectedUnit().getType().getProperty("ContainerProperty") != null) {
                int capacity = model.getSelectedUnit().getTotalCapacity(null);
                getColonyUnitsManagementPanel().setMaxResourceQuantity(capacity);
                getColonyUnitsManagementPanel().setContainer(model.getSelectedUnit());
                getColonyUnitsManagementPanel().refreshContainedUnits(model.getSelectedUnit());
                getColonyUnitsManagementPanel().refreshLoadedColonists(model.getSelectedUnit());
                getColonyUnitsManagementPanel().setUnitResourcesPanelVisible(true);
            }
        }
    }

    private void updateColonyProductionAndImprovements() {
        getColonyProductionAndImprovementsPanel().updateAll();
    }

    private void updateAutomanageButtons() {
        Resource foodResource = model.getFreeMarsModel().getRealm().getResourceManager().getResource(Resource.FOOD);
        getHeaderPanel().setAutomanageFoodToggleButtonSelected(model.getColony().isAutomanagingResource(foodResource));
        Resource waterResource = model.getFreeMarsModel().getRealm().getResourceManager().getResource("Water");
        getHeaderPanel().setAutomanageWaterToggleButtonSelected(model.getColony().isAutomanagingResource(waterResource));
        getHeaderPanel().setAutoUseFertilizerToggleButtonSelected(model.getColony().isAutoUsingFertilizer());
    }

    private void updateColonyResources() {
        getColonyResourcesPanel().refresh();
    }

    public void setModel(ColonyDialogModel model) {
        this.model = model;
        getWorkForceManagementPanel().setModel(model);
        getColonyUnitsManagementPanel().setModel(model);
        getColonyResourcesPanel().setModel(model);
        getColonyProductionAndImprovementsPanel().setModel(model);
        update(model, ColonyDialogModel.FULL_UPDATE);
    }

    public void addColonySelectorComboBoxListener(ItemListener itemListener) {
        getHeaderPanel().addColonySelectorComboBoxListener(itemListener);
    }

    public void addColonyWorkForceManagemenMouseAdapter(MouseInputListener mouseInputListener) {
        getWorkForceManagementPanel().addMouseAdapter(mouseInputListener);
    }

    public void setUnitOrdersPopupMenu(JPopupMenu unitOrdersPopupMenu) {
        getColonyUnitsManagementPanel().setUnitOrdersPopupMenu(unitOrdersPopupMenu);
    }

    public void setColonyImprovementsPopupMenu(JPopupMenu colonyImprovementsPopupMenu) {
        getColonyProductionAndImprovementsPanel().setColonyImprovementsPopupMenu(colonyImprovementsPopupMenu);
    }

    public void setColonyResourcesTransferHandler(ResourceTransferHandler transferHandler) {
        getColonyResourcesPanel().setResourceTransferHandler(transferHandler);
    }

    public void setUnitResourcesTransferHandler(ResourceTransferHandler transferHandler) {
        getColonyUnitsManagementPanel().setResourceTransferHandler(transferHandler);
    }

    public void setRenameButtonAction(Action action) {
        getHeaderPanel().setRenameButtonAction(action);
    }

    public void setHelpButtonAction(Action action) {
        getHeaderPanel().setHelpButtonAction(action);
    }

    public void addAutomanageWaterButtonChangeListener(ChangeListener changeListener) {
        getHeaderPanel().addAutomanageWaterButtonChangeListener(changeListener);
    }

    public void addAutomanageFoodButtonChangeListener(ChangeListener changeListener) {
        getHeaderPanel().addAutomanageFoodButtonChangeListener(changeListener);
    }

    public void addAutoUseFertilizerButtonChangeListener(ChangeListener changeListener) {
        getHeaderPanel().addAutoUseFertilizerButtonChangeListener(changeListener);
    }

    public void addColonyContinuousProductionActionListener(ActionListener actionListener) {
        getColonyProductionAndImprovementsPanel().addColonyContinuousProductionActionListener(actionListener);
    }

    public void setBuyProductionAction(Action action) {
        getColonyProductionAndImprovementsPanel().setBuyProductionAction(action);
    }

    public void setDisplayQueueManagementDialogButtonAction(Action action) {
        getColonyProductionAndImprovementsPanel().setDisplayQueueManagementDialogButtonAction(action);
    }

    private void initializeWidgets() {
        getContentPane().setLayout(new BorderLayout(4, 4));
        getContentPane().add(getHeaderPanel(), BorderLayout.PAGE_START);
        getContentPane().add(getMainPanel(), BorderLayout.CENTER);
    }

    private ColonyDialogHeaderPanel getHeaderPanel() {
        if (headerPanel == null) {
            headerPanel = new ColonyDialogHeaderPanel();
        }
        return headerPanel;
    }

    private JPanel getMainPanel() {
        if (mainPanel == null) {
            mainPanel = new JPanel(new GridLayout(0, 3));
            mainPanel.add(getWestPanel());
            mainPanel.add(getColonyResourcesPanel());
            mainPanel.add(getColonyProductionAndImprovementsPanel());
        }
        return mainPanel;
    }

    private JPanel getWestPanel() {
        if (westPanel == null) {
            westPanel = new JPanel(new BorderLayout());
            westPanel.add(getColonyWorkforcePanel(), BorderLayout.PAGE_START);
            westPanel.add(getColonyUnitsManagementPanel(), BorderLayout.CENTER);
        }
        return westPanel;
    }

    private ColonyResourcesPanel getColonyResourcesPanel() {
        if (colonyResourcesPanel == null) {
            colonyResourcesPanel = new ColonyResourcesPanel();
        }
        return colonyResourcesPanel;
    }

    private ColonyProductionAndImprovementsPanel getColonyProductionAndImprovementsPanel() {
        if (colonyProductionAndImprovementsPanel == null) {
            colonyProductionAndImprovementsPanel = new ColonyProductionAndImprovementsPanel();
        }
        return colonyProductionAndImprovementsPanel;
    }

    private JPanel getColonyWorkforcePanel() {
        if (colonyWorkforcePanel == null) {
            colonyWorkforcePanel = new JPanel();
            colonyWorkforcePanel.setPreferredSize(new Dimension(0, 215));
            colonyWorkforcePanel.setLayout(new BorderLayout(0, 4));
            colonyWorkforcePanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
            colonyWorkforcePanel.add(getWorkForceManagementPanel(), BorderLayout.CENTER);
        }
        return colonyWorkforcePanel;
    }

    private ColonyWorkForceManagementPanel getWorkForceManagementPanel() {
        if (workForceManagementPanel == null) {
            workForceManagementPanel = new ColonyWorkForceManagementPanel();
        }
        return workForceManagementPanel;
    }

    private JLabel getFreeWorkForceLabel() {
        if (freeWorkForceLabel == null) {
            freeWorkForceLabel = new JLabel();
        }
        return freeWorkForceLabel;
    }

    private ColonyUnitsManagementPanel getColonyUnitsManagementPanel() {
        if (colonyUnitsManagementPanel == null) {
            colonyUnitsManagementPanel = new ColonyUnitsManagementPanel(freeMarsController);
        }
        return colonyUnitsManagementPanel;
    }
}
