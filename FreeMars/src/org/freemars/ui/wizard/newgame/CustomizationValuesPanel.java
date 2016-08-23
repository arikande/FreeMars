package org.freemars.ui.wizard.newgame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.HashMap;
import java.util.Iterator;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import org.freemars.ui.image.FreeMarsImageManager;
import org.freerealm.tile.TileType;
import org.freerealm.tile.TileTypeManager;

public class CustomizationValuesPanel extends JPanel {

    private JPanel contentPanel;
    private JPanel innerPanel;
    private final JLabel iconLabel;
    private final ImageIcon icon;
    private JPanel pageStartPanel;
    private JPanel explanationsPanel;
    private JLabel explanationLabel1;
    private JLabel explanationLabel2;
    private JLabel explanationLabel3;
    private JLabel explanationLabel4;
    private JLabel explanationLabel5;
    private JPanel mapWidthHeightPanel;
    private JLabel mapWidthLabel;
    private JSpinner mapWidthSpinner;
    private JLabel mapHeightLabel;
    private JSpinner mapHeightSpinner;
    private JPanel tileTypeProbabilitiesPanel;
    private JPanel tileTypeNamesPanel;
    private JPanel tileTypeSlidersPanel;
    private final HashMap<JSlider, TileType> tileTypeSliders;
    private final TileTypeManager tileTypeManager;

    public CustomizationValuesPanel(TileTypeManager tileTypeManager) {
        this.tileTypeManager = tileTypeManager;
        tileTypeSliders = new HashMap<JSlider, TileType>();
        iconLabel = new JLabel();
        contentPanel = getContentPanel();
        contentPanel.setBorder(new EmptyBorder(new Insets(0, 10, 0, 10)));
        setLayout(new BorderLayout());
        icon = new ImageIcon(FreeMarsImageManager.getImage("NEW_GAME_WIZARD_2"));
        if (icon != null) {
            iconLabel.setIcon(icon);
        }
        iconLabel.setBorder(new EtchedBorder(0));
        add(iconLabel, "West");
        JPanel secondaryPanel = new JPanel();
        secondaryPanel.add(contentPanel, "North");
        add(secondaryPanel, "Center");
    }

    private JPanel getContentPanel() {
        if (contentPanel == null) {
            contentPanel = new JPanel();
            contentPanel.setLayout(new BorderLayout());
            contentPanel.add(getInnerPanel(), "Center");
        }
        return contentPanel;
    }

    private JPanel getInnerPanel() {
        if (innerPanel == null) {
            innerPanel = new JPanel();
            innerPanel.setLayout(new BorderLayout());
            innerPanel.add(getPageStartPanel(), BorderLayout.PAGE_START);
            innerPanel.add(getTileTypeProbabilitiesPanel(), BorderLayout.CENTER);
        }
        return innerPanel;
    }

    private JPanel getPageStartPanel() {
        if (pageStartPanel == null) {
            pageStartPanel = new JPanel(new BorderLayout());
            pageStartPanel.add(getExplanationsPanel(), BorderLayout.CENTER);
            pageStartPanel.add(getMapWidthHeightPanel(), BorderLayout.PAGE_END);
        }
        return pageStartPanel;
    }

    private JPanel getExplanationsPanel() {
        if (explanationsPanel == null) {
            explanationsPanel = new JPanel(new GridLayout(5, 1));
            explanationsPanel.add(getExplanationLabel1());
            explanationsPanel.add(getExplanationLabel2());
            explanationsPanel.add(getExplanationLabel3());
            explanationsPanel.add(getExplanationLabel4());
            explanationsPanel.add(getExplanationLabel5());
        }
        return explanationsPanel;
    }

    private JLabel getExplanationLabel1() {
        if (explanationLabel1 == null) {
            explanationLabel1 = new JLabel("Please enter the values to customize your map. Map width and ");
        }
        return explanationLabel1;
    }

    private JLabel getExplanationLabel2() {
        if (explanationLabel2 == null) {
            explanationLabel2 = new JLabel("height must be between 40 and 120 tiles. Tile type probabilities");
        }
        return explanationLabel2;
    }

    private JLabel getExplanationLabel3() {
        if (explanationLabel3 == null) {
            explanationLabel3 = new JLabel("will be relative to each other. Choosing same value for all of");
        }
        return explanationLabel3;
    }

    private JLabel getExplanationLabel4() {
        if (explanationLabel4 == null) {
            explanationLabel4 = new JLabel("tile types (rare or abundant) will give the same result.");
        }
        return explanationLabel4;
    }

    private JLabel getExplanationLabel5() {
        if (explanationLabel5 == null) {
            explanationLabel5 = new JLabel("");
        }
        return explanationLabel5;
    }

    private JPanel getMapWidthHeightPanel() {
        if (mapWidthHeightPanel == null) {
            mapWidthHeightPanel = new JPanel(new GridLayout(3, 2, 5, 5));
            mapWidthHeightPanel.add(getMapWidthLabel());
            mapWidthHeightPanel.add(getMapWidthSpinner());
            mapWidthHeightPanel.add(getMapHeightLabel());
            mapWidthHeightPanel.add(getMapHeightSpinner());
            mapWidthHeightPanel.add(new JLabel());
            mapWidthHeightPanel.add(new JLabel());
        }
        return mapWidthHeightPanel;
    }

    private JLabel getMapWidthLabel() {
        if (mapWidthLabel == null) {
            mapWidthLabel = new JLabel("Map width :");
        }
        return mapWidthLabel;
    }

    public JSpinner getMapWidthSpinner() {
        if (mapWidthSpinner == null) {
            mapWidthSpinner = new JSpinner(new SpinnerNumberModel(80, 40, 120, 5));
        }
        return mapWidthSpinner;
    }

    private JLabel getMapHeightLabel() {
        if (mapHeightLabel == null) {
            mapHeightLabel = new JLabel("Map height :");
        }
        return mapHeightLabel;
    }

    public JSpinner getMapHeightSpinner() {
        if (mapHeightSpinner == null) {
            mapHeightSpinner = new JSpinner(new SpinnerNumberModel(80, 40, 120, 5));
        }
        return mapHeightSpinner;
    }

    private JPanel getTileTypeProbabilitiesPanel() {
        if (tileTypeProbabilitiesPanel == null) {
            tileTypeProbabilitiesPanel = new JPanel(new BorderLayout(15, 15));
            tileTypeProbabilitiesPanel.add(getTileTypeNamesPanel(), BorderLayout.LINE_START);
            tileTypeProbabilitiesPanel.add(getTileTypeSlidersPanel(), BorderLayout.CENTER);
        }
        return tileTypeProbabilitiesPanel;
    }

    private JPanel getTileTypeNamesPanel() {
        if (tileTypeNamesPanel == null) {
            tileTypeNamesPanel = new JPanel(new GridLayout(0, 1));
            tileTypeNamesPanel.add(new JLabel("Tile type"));
            Iterator<TileType> tileTypes = tileTypeManager.getTileTypesIterator();
            while (tileTypes.hasNext()) {
                tileTypeNamesPanel.add(new JLabel(tileTypes.next().getName()));
            }
        }
        return tileTypeNamesPanel;
    }

    private JPanel getTileTypeSlidersPanel() {
        if (tileTypeSlidersPanel == null) {
            tileTypeSlidersPanel = new JPanel(new GridLayout(0, 1));
            tileTypeSlidersPanel.add(new JLabel("Rare               Normal               Abundant"));
            Iterator<TileType> tileTypes = tileTypeManager.getTileTypesIterator();
            while (tileTypes.hasNext()) {
                TileType tileType = tileTypes.next();
                JSlider jSlider = new JSlider(1, 200);
                if (tileType.getName().equals("Plains")) {
                    jSlider.setValue(100);
                } else {
                    jSlider.setValue(20);
                }
                getTileTypeSliders().put(jSlider, tileType);
                tileTypeSlidersPanel.add(jSlider);
            }
        }
        return tileTypeSlidersPanel;
    }

    public HashMap<JSlider, TileType> getTileTypeSliders() {
        return tileTypeSliders;
    }
}
