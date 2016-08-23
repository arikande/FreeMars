package org.freemars.colonydialog.workforce;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JToggleButton;
import javax.swing.event.MouseInputAdapter;

import org.freemars.colony.RemoveFertilizerFromTileCommand;
import org.freemars.colonydialog.ColonyDialogModel;
import org.freemars.colonydialog.unit.SelectorPanel;
import org.freemars.controller.FreeMarsController;
import org.freemars.ui.image.FreeMarsImageManager;
import org.freerealm.Utility;
import org.freerealm.command.WorkforceRemoveCommand;
import org.freerealm.map.Coordinate;
import org.freerealm.map.Direction;
import org.freerealm.resource.Resource;
import org.freerealm.settlement.Settlement;
import org.freerealm.settlement.workforce.WorkForce;
import org.freerealm.tile.Tile;

/**
 *
 * @author Deniz ARIKAN
 */
public class WorkForceManagementMouseAdapter extends MouseInputAdapter {

    private final FreeMarsController freeMarsController;
    private final ColonyDialogModel model;

    public WorkForceManagementMouseAdapter(FreeMarsController freeMarsController, ColonyDialogModel model) {
        this.freeMarsController = freeMarsController;
        this.model = model;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        ColonyWorkForceManagementPanel cityWorkForceManagementPanel = (ColonyWorkForceManagementPanel) e.getSource();
        Coordinate movedCoordinate = cityWorkForceManagementPanel.getCoordinateAt(e.getX(), e.getY());
        Direction direction = getModel().getRealm().getDirection(new Coordinate(1, 2), movedCoordinate);

        cityWorkForceManagementPanel.clearAvailableResourcesForTile();

        if (direction != null) {
            cityWorkForceManagementPanel.setHighlightedCoordinate(movedCoordinate);
            Coordinate worldCoordinate = getModel().getRealm().getRelativeCoordinate(getModel().getColony().getCoordinate(), direction);
            cityWorkForceManagementPanel.setHighlightedWorldCoordinate(worldCoordinate);
            TreeMap<Resource, Integer> tileResources = getCoordinateResources(worldCoordinate);
            Iterator<Entry<Resource, Integer>> entryIterator = tileResources.entrySet().iterator();
            while (entryIterator.hasNext()) {
                Entry<Resource, Integer> entry = entryIterator.next();
                Image resourceImage = FreeMarsImageManager.getImage(entry.getKey());
                resourceImage = FreeMarsImageManager.createResizedCopy(resourceImage, 22, 22, false, cityWorkForceManagementPanel);
                cityWorkForceManagementPanel.addAvailableResourcesForTile(new TileAvailableResourceImage(resourceImage, entry.getValue()));
            }
        } else {
            cityWorkForceManagementPanel.setHighlightedCoordinate(null);
        }
        cityWorkForceManagementPanel.repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        ColonyWorkForceManagementPanel cityWorkForceManagementPanel = (ColonyWorkForceManagementPanel) e.getSource();
        Coordinate clickedCoordinate = cityWorkForceManagementPanel.getCoordinateAt(e.getX(), e.getY());
        Direction direction = getModel().getRealm().getDirection(new Coordinate(1, 2), clickedCoordinate);
        if (direction != null) {
            Coordinate worldCoordinate = getModel().getRealm().getRelativeCoordinate(getModel().getColony().getCoordinate(), direction);
            if (worldCoordinate != null && Utility.isTileAvailableForSettlement(getModel().getRealm(), getModel().getColony(), worldCoordinate)) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    WorkForceManagementPopupModel workForceManagementPopupModel = prepareModel(worldCoordinate);
                    if (workForceManagementPopupModel != null) {
                        WorkForceManagementPopup popup = new WorkForceManagementPopup();
                        SelectorPanel<Resource> resourceSelectorPanel = new SelectorPanel<Resource>(new GridLayout(1, 0));
                        Iterator<Resource> iterator = workForceManagementPopupModel.getSelectableResources().keySet().iterator();
                        while (iterator.hasNext()) {
                            Resource resource = iterator.next();
                            Image resourceImage = FreeMarsImageManager.getImage(resource);
                            resourceImage = FreeMarsImageManager.createResizedCopy(resourceImage, 25, -1, false, popup);
                            JToggleButton toggleButton = new JToggleButton(new ImageIcon(resourceImage));
                            toggleButton.setPreferredSize(new Dimension(30, 30));
                            toggleButton.setBorderPainted(false);
                            toggleButton.setFocusPainted(false);
                            toggleButton.setToolTipText(resource.getName());
                            resourceSelectorPanel.addSelectable(resource, toggleButton);
                        }
                        resourceSelectorPanel.setSelectedObject(workForceManagementPopupModel.getSelectedResource());
                        resourceSelectorPanel.addActionListener(new ResourceSelectorPanelActionListener(resourceSelectorPanel, workForceManagementPopupModel));
                        popup.setResourceSelectorPanelContent(resourceSelectorPanel);
                        int maximumValue = workForceManagementPopupModel.getMaxWorkers();
                        popup.initializeNumberSelectorPanel(getNumberSelectorPanelInterval(workForceManagementPopupModel), maximumValue);
                        popup.setNumberSelectorPanelValue(workForceManagementPopupModel.getSelectedNumberOfWorkers());
                        popup.addNumberSelectorPanelActionListener(new NumberSelectorPanelActionListener(popup, workForceManagementPopupModel));
                        popup.initializeWorkforceColonistsSlider(0, maximumValue);
                        popup.setWorkforceColonistsSliderValue(workForceManagementPopupModel.getSelectedNumberOfWorkers());
                        popup.addWorkforceColonistsSliderChangeListener(new WorkforceColonistsSliderChangeListener(popup, workForceManagementPopupModel));
                        popup.addPopupMenuListener(new WorkForceManagementPopupListener(freeMarsController, model, workForceManagementPopupModel));
                        popup.setNumberOfColonistsLabelText(String.valueOf(workForceManagementPopupModel.getSelectedNumberOfWorkers()));
                        popup.show(e.getComponent(), e.getX(), e.getY());
                    }
                } else if (e.getButton() == MouseEvent.BUTTON3) {
                    if (model.getColony().isCoordinateFertilized(worldCoordinate)) {
                        freeMarsController.execute(new RemoveFertilizerFromTileCommand(freeMarsController.getFreeMarsModel().getRealm(), model.getColony(), worldCoordinate, true));
                    }
                    WorkForce workForce = getModel().getColony().getWorkForceManager().getAssignedWorkforceForTile(worldCoordinate);
                    if (workForce != null) {
                        WorkforceRemoveCommand workforceRemoveCommand = new WorkforceRemoveCommand(model.getColony(), worldCoordinate);
                        freeMarsController.execute(workforceRemoveCommand);
                        model.refresh(ColonyDialogModel.WORKFORCE_UPDATE);
                        model.refresh(ColonyDialogModel.COLONY_RESOURCES_UPDATE);
                    }
                }
            } else {
                Settlement settlement = Utility.findSettlementUsingCoordinate(getModel().getRealm(), worldCoordinate);
                if (getModel().getColony().getPlayer().equals(settlement.getPlayer())) {
                    Object[] options = {"Yes", "No"};
                    StringBuilder message = new StringBuilder();
                    message.append("This tile is currently used by ");
                    message.append(settlement.getName());
                    message.append(". Do you want to remove the current workforce?");
                    message.append(System.getProperty("line.separator"));
                    message.append("If ");
                    message.append(settlement.getName());
                    message.append(" uses this tile for a vital resource, please reassign their colonists.");
                    int value = JOptionPane.showOptionDialog(freeMarsController.getCurrentFrame(),
                            message.toString(),
                            "Used by " + settlement.getName(),
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            new ImageIcon(FreeMarsImageManager.getImage(settlement)),
                            options,
                            options[1]);
                    if (value == JOptionPane.YES_OPTION) {
                        WorkforceRemoveCommand workforceRemoveCommand = new WorkforceRemoveCommand(settlement, worldCoordinate);
                        freeMarsController.execute(workforceRemoveCommand);
                        model.refresh(ColonyDialogModel.WORKFORCE_UPDATE);
                    }
                }
            }
        }
    }

    private int getNumberSelectorPanelInterval(WorkForceManagementPopupModel workForceManagementPopupModel) {
        if (workForceManagementPopupModel.getMaxWorkers() >= 200) {
            return 25;
        } else if (workForceManagementPopupModel.getMaxWorkers() >= 100) {
            return 20;
        } else if (workForceManagementPopupModel.getMaxWorkers() >= 50) {
            return 10;
        }
        return 5;
    }

    private WorkForceManagementPopupModel prepareModel(Coordinate worldCoordinate) {
        WorkForceManagementPopupModel workForceManagementPopupModel = new WorkForceManagementPopupModel();
        workForceManagementPopupModel.setColony(getModel().getColony());
        TreeMap<Resource, Integer> tileResources = getCoordinateResources(worldCoordinate);
        if (tileResources.size() == 0) {
            return null;
        }
        WorkForce workForce = getModel().getColony().getWorkForceManager().getAssignedWorkforceForTile(worldCoordinate);
        workForceManagementPopupModel.setSelectableResources(tileResources);
        if (workForce == null) {
            if (getModel().getColony().getProductionWorkforce() == 0) {
                return null;
            }
            Resource maximumOutputResource = null;
            Iterator<Resource> iterator = tileResources.keySet().iterator();
            while (iterator.hasNext()) {
                Resource resource = iterator.next();
                if (maximumOutputResource == null || tileResources.get(resource) > tileResources.get(maximumOutputResource)) {
                    maximumOutputResource = resource;
                }
            }
            if (maximumOutputResource != null) {
                workForceManagementPopupModel.setSelectedResource(maximumOutputResource);
            }
            workForceManagementPopupModel.setMaxWorkers(Math.min(getModel().getColony().getProductionWorkforce(), getModel().getColony().getMaxWorkersPerTile()));
            workForceManagementPopupModel.setSelectedNumberOfWorkers(0);
        } else {
            workForceManagementPopupModel.setSelectedResource(workForce.getResource());
            workForceManagementPopupModel.setMaxWorkers(Math.min(getModel().getColony().getProductionWorkforce() + workForce.getNumberOfWorkers(), getModel().getColony().getMaxWorkersPerTile()));
            workForceManagementPopupModel.setSelectedNumberOfWorkers(workForce.getNumberOfWorkers());
        }
        workForceManagementPopupModel.setCoordinate(worldCoordinate);
        return workForceManagementPopupModel;
    }

    private TreeMap<Resource, Integer> getCoordinateResources(Coordinate coordinate) {
        TreeMap<Resource, Integer> tileResources = new TreeMap<Resource, Integer>();
        if (coordinate != null) {
            Iterator<Resource> iterator = getModel().getRealm().getResourceManager().getResourcesIterator();
            while (iterator.hasNext()) {
                Resource resource = iterator.next();
                Tile tile = getModel().getRealm().getTile(coordinate);
                if (tile != null && tile.getProduction(resource) > 0) {
                    tileResources.put(resource, tile.getProduction(resource));
                }
            }
        }
        return tileResources;
    }

    private ColonyDialogModel getModel() {
        return model;
    }
}
