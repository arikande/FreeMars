package org.freemars.earth.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.Iterator;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolTip;
import org.freemars.ui.image.FreeMarsImageManager;
import org.freerealm.resource.Resource;
import org.freerealm.unit.Unit;
import org.freerealm.unit.UnitType;

/**
 *
 * @author Deniz ARIKAN
 */
public class UnitSelectionToggleButtonTooltip extends JToolTip {

    private final Unit unit;

    private JPanel headerPanel;
    private JPanel mainPanel;
    private JPanel centerPanel;
    private JPanel footerPanel;
    private JPanel resourcesPanel;
    private JPanel populationPanel;
    private JLabel unitNameLabel;
    private JPanel containedUnitsPanel;

    public UnitSelectionToggleButtonTooltip(Unit unit) {
        super();
        this.unit = unit;
        setLayout(new BorderLayout());
        add(getMainPanel(), BorderLayout.CENTER);
    }

    @Override
    public Dimension getPreferredSize() {
        return getMainPanel().getPreferredSize();
    }

    private JPanel getMainPanel() {
        if (mainPanel == null) {
            mainPanel = new JPanel(new BorderLayout(10, 10));
            mainPanel.add(getHeaderPanel(), BorderLayout.PAGE_START);
            mainPanel.add(getCenterPanel(), BorderLayout.CENTER);
            mainPanel.add(getFooterPanel(), BorderLayout.PAGE_END);
        }
        return mainPanel;
    }

    private JPanel getHeaderPanel() {
        if (headerPanel == null) {
            headerPanel = new JPanel(new BorderLayout());
            headerPanel.setPreferredSize(new Dimension(180, 20));
            headerPanel.add(Box.createHorizontalStrut(20), BorderLayout.LINE_START);
            headerPanel.add(getUnitNameLabel(), BorderLayout.CENTER);
            headerPanel.add(Box.createHorizontalStrut(20), BorderLayout.LINE_END);
        }
        return headerPanel;
    }

    private JLabel getUnitNameLabel() {
        if (unitNameLabel == null) {
            unitNameLabel = new JLabel(unit.getName());
            unitNameLabel.setFont(unitNameLabel.getFont().deriveFont(14f));
        }
        return unitNameLabel;
    }

    private JPanel getCenterPanel() {
        if (centerPanel == null) {
            centerPanel = new JPanel(new BorderLayout(10, 10));
            int containedPopulation = unit.getContainedPopulation();
            if (containedPopulation > 0) {
                centerPanel.add(getPopulationPanel(), BorderLayout.PAGE_START);
            }
            if (unit.getTotalResourceWeight() > 0) {
                centerPanel.add(getResourcesPanel(), BorderLayout.CENTER);
            }
            if (unit.getContainedUnitCount() > 0) {
                centerPanel.add(getContainedUnitsPanel(), BorderLayout.PAGE_END);
            }
        }
        return centerPanel;
    }

    private JPanel getPopulationPanel() {
        if (populationPanel == null) {
            populationPanel = new JPanel(new GridLayout(0, 4, 5, 5));
            Image colonistImage = FreeMarsImageManager.getImage("COLONIST");
            colonistImage = FreeMarsImageManager.createResizedCopy(colonistImage, -1, 20, false, this);
            Icon colonistImageIcon = new ImageIcon(colonistImage);
            populationPanel.add(new JLabel(colonistImageIcon));
            populationPanel.add(new JLabel(String.valueOf(unit.getContainedPopulation())));
        }
        return populationPanel;
    }

    private JPanel getResourcesPanel() {
        if (resourcesPanel == null) {
            resourcesPanel = new JPanel(new GridLayout(0, 4, 5, 5));
            Iterator<Resource> containedResourcesIterator = unit.getContainedResourcesIterator();
            while (containedResourcesIterator.hasNext()) {
                Resource resource = containedResourcesIterator.next();
                int quantity = unit.getResourceQuantity(resource);
                if (quantity > 0) {
                    Image resourceImage = FreeMarsImageManager.getImage(resource);
                    resourceImage = FreeMarsImageManager.createResizedCopy(resourceImage, -1, 20, false, this);
                    Icon resourceImageIcon = new ImageIcon(resourceImage);
                    resourcesPanel.add(new JLabel(resourceImageIcon));
                    resourcesPanel.add(new JLabel(String.valueOf(quantity)));
                }
            }
        }
        return resourcesPanel;
    }

    private JPanel getContainedUnitsPanel() {
        if (containedUnitsPanel == null) {
            containedUnitsPanel = new JPanel(new GridLayout(0, 4, 5, 5));
            Iterator<Integer> containedUnitIds = unit.getContainedUnitsIterator();
            while (containedUnitIds.hasNext()) {
                Integer unitId = containedUnitIds.next();
                UnitType unitType = unit.getPlayer().getUnit(unitId).getType();
                Image unitTypeImage = FreeMarsImageManager.getImage(unitType);
                unitTypeImage = FreeMarsImageManager.createResizedCopy(unitTypeImage, -1, 20, false, this);
                Icon unitTypeImageIcon = new ImageIcon(unitTypeImage);
                containedUnitsPanel.add(new JLabel(unitTypeImageIcon));
                containedUnitsPanel.add(new JLabel(String.valueOf(unit.getContainedUnitsOfTypeCount(unitType.getId()))));
            }
        }
        return containedUnitsPanel;
    }

    private JPanel getFooterPanel() {
        if (footerPanel == null) {
            footerPanel = new JPanel();
        }
        return footerPanel;
    }
}
