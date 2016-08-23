package org.freemars.earth.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.util.Iterator;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.TransferHandler;
import org.freemars.controller.FreeMarsController;
import org.freemars.earth.action.RemoveUnitFromContainerAction;
import org.freemars.ui.image.FreeMarsImageManager;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class UnitCargoPanel extends JPanel {

    private static final int PREFERRED_HEIGHT = 250;
    private JScrollPane unitResourcesScrollPane;
    private ResourceQuantitySelectorPanel resourceQuantitySelectorPanel;
    private ResourceStorerPanel unitResourceQuantitiesPanel;
    private JPanel unitResourcesPanel;
    private JPanel unitSpecialCargoPanel;
    private JPanel numberOfColonistsPanel;
    private JLabel numberOfColonistsLabel;
    private JLabel numberOfColonistsValueLabel;
    private JPanel containedUnitsPanel;
    private Unit unit;
    private final FreeMarsController freeMarsController;
    private final EarthDialog earthDialog;

    public UnitCargoPanel(FreeMarsController freeMarsController, EarthDialog earthDialog) {
        super(new BorderLayout(10, 0));
        this.freeMarsController = freeMarsController;
        this.earthDialog = earthDialog;
        setPreferredSize(new Dimension(0, PREFERRED_HEIGHT));
        add(getUnitResourcesPanel(), BorderLayout.CENTER);
        add(getUnitSpecialCargoPanel(), BorderLayout.PAGE_END);
    }

    @Override
    public void setTransferHandler(TransferHandler transferHandler) {
        getUnitResourceQuantitiesPanel().setTransferHandler(transferHandler);
    }

    public void refresh() {
        getNumberOfColonistsValueLabel().setText(String.valueOf(unit.getContainedPopulation()));
        getContainedUnitsPanel().removeAll();
        Iterator<Integer> iterator = unit.getContainedUnitsIterator();
        while (iterator.hasNext()) {
            int containedUnitId = iterator.next();
            Unit containedUnit = unit.getPlayer().getUnit(containedUnitId);
            JButton containedUnitButton = new JButton();
            Image containedUnitImage = FreeMarsImageManager.getImage(containedUnit);
            containedUnitImage = FreeMarsImageManager.createResizedCopy(containedUnitImage, -1, 24, false, this);
            containedUnitButton.setAction(new RemoveUnitFromContainerAction(freeMarsController, earthDialog, unit, containedUnit));
            containedUnitButton.setIcon(new ImageIcon(containedUnitImage));
            containedUnitButton.setToolTipText(containedUnit.toString());
            getContainedUnitsPanel().add(containedUnitButton);
        }
        getUnitSpecialCargoPanel().repaint();
        getUnitSpecialCargoPanel().revalidate();
        getUnitResourceQuantitiesPanel().refresh();
    }

    public int getSelectedQuantity() {
        return getResourceQuantitySelectorPanel().getSelectedQuantity();
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
        getUnitResourceQuantitiesPanel().setResourceStorer(unit);
    }

    public void setMaxCapacity(int maxCapacity) {
        getResourceQuantitySelectorPanel().setMaxCapacity(maxCapacity);
    }

    private JPanel getUnitResourcesPanel() {
        if (unitResourcesPanel == null) {
            unitResourcesPanel = new JPanel(new BorderLayout(10, 0));
            unitResourcesPanel.add(getResourceQuantitySelectorPanel(), BorderLayout.LINE_START);
            unitResourcesPanel.add(getUnitResourcesScrollPane(), BorderLayout.CENTER);
        }
        return unitResourcesPanel;
    }

    private JPanel getUnitSpecialCargoPanel() {
        if (unitSpecialCargoPanel == null) {
            unitSpecialCargoPanel = new JPanel(new BorderLayout());
            unitSpecialCargoPanel.add(getNumberOfColonistsPanel(), BorderLayout.LINE_START);
            unitSpecialCargoPanel.add(getContainedUnitsPanel(), BorderLayout.CENTER);
        }
        return unitSpecialCargoPanel;
    }

    private JPanel getNumberOfColonistsPanel() {
        if (numberOfColonistsPanel == null) {
            numberOfColonistsPanel = new JPanel(new BorderLayout());
            numberOfColonistsPanel.add(getNumberOfColonistsLabel(), BorderLayout.LINE_START);
            numberOfColonistsPanel.add(getNumberOfColonistsValueLabel(), BorderLayout.CENTER);
        }
        return numberOfColonistsPanel;
    }

    private JLabel getNumberOfColonistsLabel() {
        if (numberOfColonistsLabel == null) {
            numberOfColonistsLabel = new JLabel(" Colonists : ");
        }
        return numberOfColonistsLabel;
    }

    private JLabel getNumberOfColonistsValueLabel() {
        if (numberOfColonistsValueLabel == null) {
            numberOfColonistsValueLabel = new JLabel();
        }
        return numberOfColonistsValueLabel;
    }

    private JPanel getContainedUnitsPanel() {
        if (containedUnitsPanel == null) {
            containedUnitsPanel = new JPanel();
        }
        return containedUnitsPanel;
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

    private ResourceStorerPanel getUnitResourceQuantitiesPanel() {
        if (unitResourceQuantitiesPanel == null) {
            unitResourceQuantitiesPanel = new ResourceStorerPanel();
        }
        return unitResourceQuantitiesPanel;
    }
}
