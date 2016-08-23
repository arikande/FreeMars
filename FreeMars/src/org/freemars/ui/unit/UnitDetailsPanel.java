package org.freemars.ui.unit;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.Iterator;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import org.freemars.model.FreeMarsModel;
import org.freemars.ui.image.FreeMarsImageManager;
import org.freemars.ui.image.ImagePanel;
import org.freerealm.resource.bonus.BonusResource;
import org.freerealm.tile.Tile;
import org.freerealm.tile.improvement.TileImprovementType;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class UnitDetailsPanel extends JPanel {

    private ImagePanel unitImagePanel;
    private JPanel centerPanel;
    private JPanel footerPanel;
    private JPanel unitValuesPanel;
    private JLabel unitTypeValueLabel;
    private JPanel unitInfoPanel;
    private JLabel movementPointsLabel;
    private JLabel movementPointsValueLabel;
    private JPanel terrainInfoPanel;
    private JLabel coordinateValueLabel;
    private UnitResourcesPanel resourcesPanel;
    private final FreeMarsModel freeMarsModel;

    public UnitDetailsPanel(FreeMarsModel model) {
        this.freeMarsModel = model;
        setLayout(new BorderLayout(0, 15));
        setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        initializeWidgets();
    }

    public void setUnit(Unit unit) {
        if (unit != null) {
            Image unitImage = FreeMarsImageManager.getImage(unit);
            unitImage = FreeMarsImageManager.createResizedCopy(unitImage, 70, -1, false, this);
            getUnitImagePanel().setImage(unitImage);
            getUnitTypeValueLabel().setText(unit.getName());
            getMovementPointsValueLabel().setText(String.valueOf(unit.getMovementPoints()));
            getCoordinateValueLabel().setText(String.valueOf(unit.getCoordinate()));
            getTerrainInfoPanel().removeAll();
            if (unit.getCoordinate() != null) {
                Tile tile = freeMarsModel.getTile(unit.getCoordinate());
                getTerrainInfoPanel().add(new JLabel("- " + tile.getType().getName()));
                if (tile.getVegetation() != null) {
                    getTerrainInfoPanel().add(new JLabel("- " + tile.getVegetation().getType().getName()));
                }
                Iterator<TileImprovementType> iterator = tile.getImprovementsIterator();
                while (iterator.hasNext()) {
                    TileImprovementType tileImprovement = iterator.next();
                    getTerrainInfoPanel().add(new JLabel("- " + tileImprovement.getName()));
                }
                if (tile.getBonusResource() != null) {
                    BonusResource bonusResource = tile.getBonusResource();
                    getTerrainInfoPanel().add(new JLabel("- " + bonusResource.getName()));
                }
            }
            getResourcesPanel().setUnit(unit);
        } else {
            getUnitImagePanel().setImage(null);
            getUnitTypeValueLabel().setText("");
            getMovementPointsValueLabel().setText("");
            getCoordinateValueLabel().setText("");
        }
    }

    private void initializeWidgets() {
        add(getCenterPanel(), BorderLayout.CENTER);
        add(getFooterPanel(), BorderLayout.PAGE_END);
    }

    private JPanel getCenterPanel() {
        if (centerPanel == null) {
            centerPanel = new JPanel(new BorderLayout());
            centerPanel.add(getUnitImagePanel(), BorderLayout.LINE_START);
            centerPanel.add(getUnitValuesPanel(), BorderLayout.CENTER);
        }
        return centerPanel;
    }

    private JPanel getFooterPanel() {
        if (footerPanel == null) {
            footerPanel = new JPanel(new BorderLayout());
            footerPanel.add(getResourcesPanel(), BorderLayout.CENTER);
            footerPanel.setPreferredSize(new Dimension(0, 55));
        }
        return footerPanel;
    }

    private ImagePanel getUnitImagePanel() {
        if (unitImagePanel == null) {
            unitImagePanel = new ImagePanel();
            unitImagePanel.setImageVerticalAlignment(ImagePanel.CENTER_ALIGNMENT);
            unitImagePanel.setPreferredSize(new Dimension(85, 85));
        }
        return unitImagePanel;
    }

    private JPanel getUnitValuesPanel() {
        if (unitValuesPanel == null) {
            unitValuesPanel = new JPanel(new BorderLayout());
            unitValuesPanel.add(getUnitInfoPanel(), BorderLayout.CENTER);
            unitValuesPanel.add(getTerrainInfoPanel(), BorderLayout.PAGE_END);
        }
        return unitValuesPanel;
    }

    private JPanel getUnitInfoPanel() {
        if (unitInfoPanel == null) {
            unitInfoPanel = new JPanel(new GridLayout(2, 2));
            unitInfoPanel.add(getUnitTypeValueLabel());
            unitInfoPanel.add(getCoordinateValueLabel());
            unitInfoPanel.add(getMovementPointsLabel());
            unitInfoPanel.add(getMovementPointsValueLabel());
        }
        return unitInfoPanel;
    }

    private JPanel getTerrainInfoPanel() {
        if (terrainInfoPanel == null) {
            terrainInfoPanel = new JPanel(new GridLayout(0, 1));
        }
        return terrainInfoPanel;
    }

    private JLabel getUnitTypeValueLabel() {
        if (unitTypeValueLabel == null) {
            unitTypeValueLabel = new JLabel();
        }
        return unitTypeValueLabel;
    }

    private JLabel getMovementPointsLabel() {
        if (movementPointsLabel == null) {
            movementPointsLabel = new JLabel("Moves : ");
        }
        return movementPointsLabel;
    }

    private JLabel getMovementPointsValueLabel() {
        if (movementPointsValueLabel == null) {
            movementPointsValueLabel = new JLabel();
        }
        return movementPointsValueLabel;
    }

    private JLabel getCoordinateValueLabel() {
        if (coordinateValueLabel == null) {
            coordinateValueLabel = new JLabel();
        }
        return coordinateValueLabel;
    }

    private UnitResourcesPanel getResourcesPanel() {
        if (resourcesPanel == null) {
            resourcesPanel = new UnitResourcesPanel(freeMarsModel);
        }
        return resourcesPanel;
    }
}
