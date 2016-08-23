package org.freemars.ui.unit;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.Iterator;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.freemars.colonydialog.unit.ResourceQuantityPanel;
import org.freemars.model.FreeMarsModel;
import org.freemars.ui.image.FreeMarsImageManager;
import org.freemars.ui.image.ImagePanel;
import org.freerealm.resource.Resource;
import org.freerealm.unit.FreeRealmUnitType;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class UnitResourcesPanel extends JPanel {

    private static final int MAX_PANELS = 6;
    private final FreeMarsModel freeMarsModel;
    private int addedPanels = 0;

    public UnitResourcesPanel(FreeMarsModel freeMarsModel) {
        super(new GridLayout(1, 0, 10, 0));
        this.freeMarsModel = freeMarsModel;
    }

    public void setUnit(Unit unit) {
        removeAll();
        addedPanels = 0;
        if (unit != null) {
            if (unit.getContainedPopulation() > 0) {
                JPanel colonistsPanel = new JPanel(new BorderLayout());
                Image image = FreeMarsImageManager.getImage("COLONIST");
                ImagePanel imagePanel = new ImagePanel(image);
                colonistsPanel.add(Box.createVerticalStrut(2), BorderLayout.PAGE_START);
                colonistsPanel.add(imagePanel, BorderLayout.CENTER);
                JPanel numberOfColonistsPanel = new JPanel();
                numberOfColonistsPanel.add(new JLabel(String.valueOf(unit.getContainedPopulation())));
                colonistsPanel.add(numberOfColonistsPanel, BorderLayout.PAGE_END);
                add(colonistsPanel);
                addedPanels++;
            }
            Iterator<FreeRealmUnitType> iterator = freeMarsModel.getRealm().getUnitTypeManager().getUnitTypesIterator();
            while (iterator.hasNext()) {
                FreeRealmUnitType unitType = iterator.next();
                int count = unit.getContainedUnitsOfTypeCount(unitType.getId());
                if (count > 0) {
                    JPanel containedUnitPanel = new JPanel(new BorderLayout());
                    Image image = FreeMarsImageManager.getImage(unitType);
                    image = FreeMarsImageManager.createResizedCopy(image, 30, -1, false, this);
                    ImagePanel imagePanel = new ImagePanel(image);
                    containedUnitPanel.add(Box.createVerticalStrut(2), BorderLayout.PAGE_START);
                    containedUnitPanel.add(imagePanel, BorderLayout.CENTER);
                    JPanel unitCountPanel = new JPanel();
                    unitCountPanel.add(new JLabel(String.valueOf(count)));
                    containedUnitPanel.add(unitCountPanel, BorderLayout.PAGE_END);
                    add(containedUnitPanel);
                    addedPanels++;
                }
            }
            Iterator<Resource> itertor = unit.getStorableResourcesIterator();
            int i = 0;
            while (itertor.hasNext()) {
                Resource resource = itertor.next();
                if (unit.getResourceQuantity(resource) > 0) {
                    ResourceQuantityPanel resourceQuantityPanel = new ResourceQuantityPanel(resource, unit.getResourceQuantity(resource));
                    if (addedPanels < MAX_PANELS - 1) {
                        add(resourceQuantityPanel);
                        addedPanels++;
                    } else {
                        JLabel plusLabel = new JLabel("...");
                        plusLabel.setFont(plusLabel.getFont().deriveFont(14f).deriveFont(Font.BOLD));
                        add(plusLabel);
                        break;
                    }
                }
            }
        }
    }
}
