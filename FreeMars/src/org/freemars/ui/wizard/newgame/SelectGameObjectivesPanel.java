package org.freemars.ui.wizard.newgame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import org.freemars.ui.image.FreeMarsImageManager;

public class SelectGameObjectivesPanel extends JPanel {

    private JPanel contentPanel;
    private JPanel innerPanel;
    private JPanel headerPanel;
    private JPanel objectivesPanel;
    private JPanel declareIndependenceObjectivePanel;
    private JCheckBox declareIndependenceCheckBox;
    private JPanel defeatOtherPlayersObjectivePanel;
    private JCheckBox defeatOtherPlayersCheckBox;
    private JPanel reachPopulationObjectivePanel;
    private JCheckBox reachPopulationCheckBox;
    private JSpinner reachPopulationSpinner;
    private JPanel reachTreasuryObjectivePanel;
    private JCheckBox reachTreasuryCheckBox;
    private JSpinner reachTreasurySpinner;
    private final JLabel iconLabel;
    private final ImageIcon icon;

    public SelectGameObjectivesPanel() {
        iconLabel = new JLabel();
        contentPanel = getContentPanel();
        contentPanel.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));
        setLayout(new BorderLayout());
        icon = new ImageIcon(FreeMarsImageManager.getImage("NEW_GAME_WIZARD_6"));
        if (icon != null) {
            iconLabel.setIcon(icon);
        }
        iconLabel.setBorder(new EtchedBorder(0));
        add(iconLabel, BorderLayout.LINE_START);
        add(contentPanel, BorderLayout.CENTER);
    }

    public boolean isDefeatOtherPlayersObjectiveSelected() {
        return getDefeatOtherPlayersCheckBox().isSelected();
    }

    public boolean isReachPopulationObjectiveSelected() {
        return getReachPopulationCheckBox().isSelected();
    }

    public int getObjectivePopulation() {
        return Integer.parseInt(getReachPopulationSpinner().getValue().toString());
    }

    public boolean isReachTreasuryObjectiveSelected() {
        return getReachTreasuryCheckBox().isSelected();
    }

    public int getObjectiveTreasury() {
        return Integer.parseInt(getReachTreasurySpinner().getValue().toString());
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
            innerPanel = new JPanel(new BorderLayout());
            innerPanel.add(getHeaderPanel(), BorderLayout.PAGE_START);
            innerPanel.add(getObjectivesPanel(), BorderLayout.CENTER);
        }
        return innerPanel;
    }

    private JPanel getHeaderPanel() {
        if (headerPanel == null) {
            headerPanel = new JPanel();
            headerPanel.add(new JLabel("Select game objectives"));
        }
        return headerPanel;
    }

    private JPanel getObjectivesPanel() {
        if (objectivesPanel == null) {
            objectivesPanel = new JPanel(new GridLayout(0, 1));
            objectivesPanel.add(getDeclareIndependenceObjectivePanel());
            objectivesPanel.add(getDefeatOtherPlayersObjectivePanel());
            objectivesPanel.add(getReachPopulationObjectivePanel());
            objectivesPanel.add(getReachTreasuryObjectivePanel());
        }
        return objectivesPanel;
    }

    private JPanel getDeclareIndependenceObjectivePanel() {
        if (declareIndependenceObjectivePanel == null) {
            declareIndependenceObjectivePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            declareIndependenceObjectivePanel.add(getDeclareIndependenceCheckBox());
        }
        return declareIndependenceObjectivePanel;
    }

    private JCheckBox getDeclareIndependenceCheckBox() {
        if (declareIndependenceCheckBox == null) {
            declareIndependenceCheckBox = new JCheckBox("Declare & Defend independence");
            declareIndependenceCheckBox.setSelected(true);
            declareIndependenceCheckBox.setEnabled(false);
        }
        return declareIndependenceCheckBox;
    }

    private JPanel getDefeatOtherPlayersObjectivePanel() {
        if (defeatOtherPlayersObjectivePanel == null) {
            defeatOtherPlayersObjectivePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            defeatOtherPlayersObjectivePanel.add(getDefeatOtherPlayersCheckBox());
        }
        return defeatOtherPlayersObjectivePanel;
    }

    private JCheckBox getDefeatOtherPlayersCheckBox() {
        if (defeatOtherPlayersCheckBox == null) {
            defeatOtherPlayersCheckBox = new JCheckBox("Defeat other players");
        }
        return defeatOtherPlayersCheckBox;
    }

    private JPanel getReachPopulationObjectivePanel() {
        if (reachPopulationObjectivePanel == null) {
            reachPopulationObjectivePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            reachPopulationObjectivePanel.add(getReachPopulationCheckBox());
            reachPopulationObjectivePanel.add(getReachPopulationSpinner());
        }
        return reachPopulationObjectivePanel;
    }

    private JCheckBox getReachPopulationCheckBox() {
        if (reachPopulationCheckBox == null) {
            reachPopulationCheckBox = new JCheckBox("Reach population");
            reachPopulationCheckBox.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    if (getReachPopulationCheckBox().isSelected()) {
                        getReachPopulationSpinner().setEnabled(true);
                    } else {
                        getReachPopulationSpinner().setEnabled(false);
                    }
                }
            });
        }
        return reachPopulationCheckBox;
    }

    private JSpinner getReachPopulationSpinner() {
        if (reachPopulationSpinner == null) {
            reachPopulationSpinner = new JSpinner(new SpinnerNumberModel(40000, 10000, 60000, 2000));
            reachPopulationSpinner.setMinimumSize(new Dimension(120, 20));
            reachPopulationSpinner.setEnabled(false);
        }
        return reachPopulationSpinner;
    }

    private JPanel getReachTreasuryObjectivePanel() {
        if (reachTreasuryObjectivePanel == null) {
            reachTreasuryObjectivePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            reachTreasuryObjectivePanel.add(getReachTreasuryCheckBox());
            reachTreasuryObjectivePanel.add(getReachTreasurySpinner());
        }
        return reachTreasuryObjectivePanel;
    }

    private JCheckBox getReachTreasuryCheckBox() {
        if (reachTreasuryCheckBox == null) {
            reachTreasuryCheckBox = new JCheckBox("Reach treasury");
            reachTreasuryCheckBox.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    if (getReachTreasuryCheckBox().isSelected()) {
                        getReachTreasurySpinner().setEnabled(true);
                    } else {
                        getReachTreasurySpinner().setEnabled(false);
                    }
                }
            });
        }
        return reachTreasuryCheckBox;
    }

    private JSpinner getReachTreasurySpinner() {
        if (reachTreasurySpinner == null) {
            reachTreasurySpinner = new JSpinner(new SpinnerNumberModel(100000, 25000, 200000, 5000));
            reachTreasurySpinner.setMinimumSize(new Dimension(120, 20));
            reachTreasurySpinner.setEnabled(false);
        }
        return reachTreasurySpinner;
    }

    public void setActionListener(ActionListener actionListener) {
    }
}
