package org.freemars.ui.wizard.newgame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import org.freemars.ui.image.FreeMarsImageManager;

public class ChooseMapCustomizationPanel extends JPanel {

    private JPanel contentPanel;
    private JPanel innerPanel;
    private final JLabel iconLabel;
    private final ImageIcon icon;
    private JLabel explanationLabel1;
    private JLabel explanationLabel2;
    private JLabel explanationLabel3;
    private JLabel explanationLabel4;
    private ButtonGroup mapSelectionButtonGroup;
    private JRadioButton customizeMapRadioButton;
    private JRadioButton premadeMapRadioButton;

    public ChooseMapCustomizationPanel() {
        iconLabel = new JLabel();
        contentPanel = getContentPanel();
        contentPanel.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));
        setLayout(new BorderLayout());
        icon = new ImageIcon(FreeMarsImageManager.getImage("NEW_GAME_WIZARD_1"));
        if (icon != null) {
            iconLabel.setIcon(icon);
        }
        iconLabel.setBorder(new EtchedBorder(0));
        add(iconLabel, BorderLayout.LINE_START);
        JPanel secondaryPanel = new JPanel();
        secondaryPanel.add(contentPanel, BorderLayout.PAGE_START);
        add(secondaryPanel, BorderLayout.CENTER);
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
            innerPanel.setLayout(new GridLayout(0, 1));
            innerPanel.add(new JLabel());
            innerPanel.add(getExplanationLabel1());
            innerPanel.add(getExplanationLabel2());
            innerPanel.add(getExplanationLabel3());
            innerPanel.add(getExplanationLabel4());
            innerPanel.add(new JLabel());
            innerPanel.add(getPremadeMapRadioButton());
            innerPanel.add(getCustomizeMapRadioButton());
        }
        return innerPanel;
    }

    private JLabel getExplanationLabel1() {
        if (explanationLabel1 == null) {
            explanationLabel1 = new JLabel("Please choose your map type. If you want to customize your map's");
        }
        return explanationLabel1;
    }

    private JLabel getExplanationLabel2() {
        if (explanationLabel2 == null) {
            explanationLabel2 = new JLabel("width, height and probability of tile types choose \"Customize map\"");
        }
        return explanationLabel2;
    }

    private JLabel getExplanationLabel3() {
        if (explanationLabel3 == null) {
            explanationLabel3 = new JLabel("option, or if you want to start on a built-in premade map or open");
        }
        return explanationLabel3;
    }

    private JLabel getExplanationLabel4() {
        if (explanationLabel4 == null) {
            explanationLabel4 = new JLabel("a premade map file choose  \"Start on premade map\" option.");
        }
        return explanationLabel4;
    }

    private ButtonGroup getMapSelectionButtonGroup() {
        if (mapSelectionButtonGroup == null) {
            mapSelectionButtonGroup = new ButtonGroup();
        }
        return mapSelectionButtonGroup;
    }

    private JRadioButton getCustomizeMapRadioButton() {
        if (customizeMapRadioButton == null) {
            customizeMapRadioButton = new JRadioButton("Customize map");
            customizeMapRadioButton.setActionCommand("CUSTOMIZE_MAP_RADIO");
            getMapSelectionButtonGroup().add(customizeMapRadioButton);
        }
        return customizeMapRadioButton;
    }

    private JRadioButton getPremadeMapRadioButton() {
        if (premadeMapRadioButton == null) {
            premadeMapRadioButton = new JRadioButton("Start on premade map");
            premadeMapRadioButton.setActionCommand("PREMADE_MAP_RADIO");
            premadeMapRadioButton.setSelected(true);
            getMapSelectionButtonGroup().add(premadeMapRadioButton);
        }
        return premadeMapRadioButton;
    }

    public void addMapSelectionButtonGroupActionListener(ActionListener actionListener) {
        getCustomizeMapRadioButton().addActionListener(actionListener);
        getPremadeMapRadioButton().addActionListener(actionListener);
    }
}
