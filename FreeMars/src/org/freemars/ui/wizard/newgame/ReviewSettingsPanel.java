package org.freemars.ui.wizard.newgame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.Iterator;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import org.freemars.model.wizard.newgame.NewGameOptions;
import org.freemars.ui.image.FreeMarsImageManager;
import org.freerealm.nation.Nation;
import org.freerealm.tile.TileType;

public class ReviewSettingsPanel extends JPanel {

    private JPanel contentPanel;
    private JPanel innerPanel;
    private final JLabel iconLabel;
    private final ImageIcon icon;
    private JPanel explanationsPanel;
    private JLabel explanationLabel1;
    private JPanel newGameOptionsPanel;

    public ReviewSettingsPanel() {
        iconLabel = new JLabel();
        contentPanel = getContentPanel();
        contentPanel.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));
        setLayout(new BorderLayout());
        icon = new ImageIcon(FreeMarsImageManager.getImage("NEW_GAME_WIZARD_5"));
        if (icon != null) {
            iconLabel.setIcon(icon);
        }
        iconLabel.setBorder(new EtchedBorder(0));
        add(iconLabel, "West");
        JPanel secondaryPanel = new JPanel();
        secondaryPanel.add(contentPanel, "North");
        add(secondaryPanel, BorderLayout.CENTER);
    }

    private JPanel getContentPanel() {
        if (contentPanel == null) {
            contentPanel = new JPanel();
            contentPanel.setLayout(new BorderLayout());
            contentPanel.add(getInnerPanel(), BorderLayout.CENTER);
        }
        return contentPanel;
    }

    private JPanel getInnerPanel() {
        if (innerPanel == null) {
            innerPanel = new JPanel();
            innerPanel.setLayout(new BorderLayout());
            innerPanel.add(new JLabel());
            innerPanel.add(getExplanationsPanel(), BorderLayout.PAGE_START);
            innerPanel.add(getNewGameOptionsPanel(), BorderLayout.CENTER);
        }
        return innerPanel;
    }

    private JPanel getExplanationsPanel() {
        if (explanationsPanel == null) {
            explanationsPanel = new JPanel(new GridLayout(2, 1));
            explanationsPanel.add(getExplanationLabel1());
            explanationsPanel.add(new JLabel());
        }
        return explanationsPanel;
    }

    private JLabel getExplanationLabel1() {
        if (explanationLabel1 == null) {
            explanationLabel1 = new JLabel("Please check your settings. Click \"Finish\" to start a new game");
        }
        return explanationLabel1;
    }

    private JPanel getNewGameOptionsPanel() {
        if (newGameOptionsPanel == null) {
            newGameOptionsPanel = new JPanel(new BorderLayout(10, 10));
        }
        return newGameOptionsPanel;
    }

    public void setNewGameOptions(NewGameOptions newGameOptions) {
        String mapType;
        String mapWidth;
        String mapHeight;
        if (newGameOptions.getMapType() == NewGameOptions.CUSTOMIZED_MAP) {
            mapType = "Custom";
        } else {
            mapType = "Premade";
        }
        mapWidth = String.valueOf(newGameOptions.getMapWidth());
        mapHeight = String.valueOf(newGameOptions.getMapHeight());
        JPanel headerPanel = new JPanel(new BorderLayout(0, 3));
        JPanel infoGridPanel = new JPanel(new GridLayout(0, 2, 0, 3));
        infoGridPanel.add(new JLabel("Map type :"));
        infoGridPanel.add(new JLabel(mapType));
        infoGridPanel.add(new JLabel("Map width :"));
        infoGridPanel.add(new JLabel(mapWidth));
        infoGridPanel.add(new JLabel("Map height :"));
        infoGridPanel.add(new JLabel(mapHeight));
        infoGridPanel.add(new JLabel("Human player :"));
        infoGridPanel.add(new JLabel(newGameOptions.getHumanPlayerNation().getName()));
        headerPanel.add(infoGridPanel, BorderLayout.CENTER);
        if (newGameOptions.getMapType() == NewGameOptions.PREMADE_MAP) {
            JTextField mapPathTextField = new JTextField(newGameOptions.getPremadeMapPath());
            mapPathTextField.setEditable(false);
            mapPathTextField.setPreferredSize(new Dimension(getNewGameOptionsPanel().getWidth(), 20));
            headerPanel.add(mapPathTextField, BorderLayout.PAGE_END);
        }
        JPanel nationNamesPanel = new JPanel(new GridLayout(0, 1, 0, 3));
        JPanel nationColorsPanel = new JPanel(new GridLayout(0, 1, 0, 3));
        Iterator<Nation> nationsIterator = newGameOptions.getNationsIterator();
        while (nationsIterator.hasNext()) {
            Nation nation = nationsIterator.next();
            Color primaryColor = newGameOptions.getNationPrimaryColor(nation);
            Color secondaryColor = newGameOptions.getNationSecondaryColor(nation);
            nationNamesPanel.add(new JLabel(nation.getName()));
            JPanel nationColorPanel = new JPanel(new GridLayout(1, 2));
            JPanel color1Panel = new JPanel();
            JPanel color2Panel = new JPanel();
            color1Panel.setBackground(primaryColor);
            color2Panel.setBackground(secondaryColor);
            nationColorPanel.add(color1Panel);
            nationColorPanel.add(color2Panel);
            nationColorPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            nationColorsPanel.add(nationColorPanel);
        }
        JPanel nationsPanel = new JPanel(new BorderLayout(10, 10));
        nationsPanel.add(nationNamesPanel, BorderLayout.CENTER);
        nationsPanel.add(nationColorsPanel, BorderLayout.LINE_END);
        JPanel tileTypesPanel = new JPanel(new BorderLayout());
        if (newGameOptions.getMapType() == NewGameOptions.CUSTOMIZED_MAP) {
            tileTypesPanel.setLayout(new BorderLayout());
            JPanel tileNamesPanel = new JPanel(new GridLayout(0, 1));
            JPanel tileProbabilitiesPanel = new JPanel(new GridLayout(0, 1));
            Iterator<TileType> tileTypes = newGameOptions.getTileTypes().keySet().iterator();
            while (tileTypes.hasNext()) {
                TileType tileType = tileTypes.next();
                Integer probabilityValue = newGameOptions.getTileTypes().get(tileType);
                tileNamesPanel.add(new JLabel(tileType.getName()));
                tileProbabilitiesPanel.add(new JLabel(probabilityValue.toString()));
            }
            tileTypesPanel.add(tileNamesPanel, BorderLayout.CENTER);
            tileTypesPanel.add(tileProbabilitiesPanel, BorderLayout.LINE_END);
        }
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        centerPanel.add(nationsPanel);
        centerPanel.add(tileTypesPanel);
        getNewGameOptionsPanel().removeAll();
        getNewGameOptionsPanel().add(headerPanel, BorderLayout.PAGE_START);
        getNewGameOptionsPanel().add(centerPanel, BorderLayout.CENTER);
    }
}
