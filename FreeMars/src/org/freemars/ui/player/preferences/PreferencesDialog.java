package org.freemars.ui.player.preferences;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import org.freemars.model.FreeMarsModel;
import org.freemars.ui.util.FreeMarsDialog;

/**
 *
 * @author Deniz ARIKAN
 */
public class PreferencesDialog extends FreeMarsDialog {

    private final int FRAME_WIDTH = 520;
    private final int FRAME_HEIGHT = 400;
    private JTabbedPane preferencesTabbedPane;
    private JPanel gameplayPreferencesPanel;
    private JLabel autosaveLabel;
    private JComboBox autosaveComboBox;
    private JLabel autosaveFilesToKeepLabel;
    private JComboBox autosaveFilesToKeepComboBox;
    private JLabel displayTipsOnStartupLabel;
    private JCheckBox displayTipsOnStartupCheckBox;
    private JLabel autoEndTurnLabel;
    private JCheckBox autoEndTurnCheckBox;
    private JLabel unitSelectionInColonyDialogLabel;
    private JPanel unitSelectionInColonyDialogPanel;
    private ButtonGroup unitSelectionInColonyDialogButtonGroup;
    private JRadioButton clickUnitSelectionRadioButton;
    private JRadioButton hoverUnitSelectionRadioButton;
    private JPanel screenPreferencesPanel;
    private JLabel fullScreenLabel;
    private JCheckBox fullScreenCheckBox;
    private JLabel displayUnitMoveAnimationLabel;
    private JCheckBox displayUnitMoveAnimationCheckBox;
    private JLabel displayPlayerNationFlagsOnUnitsLabel;
    private JCheckBox displayPlayerNationFlagsOnUnitsCheckBox;
    private JLabel alwaysCenterOnActiveUnitLabel;
    private JCheckBox alwaysCenterOnActiveUnitCheckBox;
    private JLabel mapEdgeScrollingLabel;
    private JCheckBox mapEdgeScrollingCheckBox;
    private JLabel miniMapDisplayTypeLabel;
    private JPanel miniMapDisplayTypePanel;
    private JLabel displayTerritoryBordersLabel;
    private JCheckBox displayTerritoryBordersCheckBox;
    private JLabel displayAIPlayerProgressWindowLabel;
    private JCheckBox displayAIPlayerProgressWindowCheckBox;
    private ButtonGroup miniMapDisplayTileButtonGroup;
    private JRadioButton miniMapDisplayTileColorsRadioButton;
    private JRadioButton miniMapDisplayTileImagesRadioButton;
    private JPanel soundPreferencesPanel;
    private JLabel unitMovementSoundLabel;
    private JCheckBox unitMovementSoundCheckBox;
    private JLabel resourceTransferSoundLabel;
    private JCheckBox resourceTransferSoundCheckBox;
    private JLabel spaceshipLaunchSoundLabel;
    private JCheckBox spaceshipLaunchSoundCheckBox;
    private JLabel wealthTransferSoundLabel;
    private JCheckBox wealthTransferSoundCheckBox;
    private JLabel colonyImprovementEnableSoundLabel;
    private JCheckBox colonyImprovementEnableSoundCheckBox;
    private JLabel colonyImprovementDisableSoundLabel;
    private JCheckBox colonyImprovementDisableSoundCheckBox;
    private JPanel pageEndPanel;
    private JButton confirmButton;
    private JButton cancelButton;
    private final FreeMarsModel model;

    public PreferencesDialog(JFrame parent, FreeMarsModel model) {
        super(parent);
        setModal(true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setTitle("Preferences");
        this.model = model;
    }

    public void display() {
        getContentPane().setLayout(new BorderLayout(4, 4));
        initializeWidgets();
        setWidgetValues();
        super.display(FRAME_WIDTH, FRAME_HEIGHT);
    }

    public void setConfirmButtonAction(Action action) {
        getConfirmButton().setAction(action);
        getConfirmButton().setText("Confirm");
    }

    public int getAutosaveComboBoxSelectedIndex() {
        return getAutosaveComboBox().getSelectedIndex();
    }

    public void setAutosaveComboBoxSelectedIndex(int index) {
        getAutosaveComboBox().setSelectedIndex(index);
    }

    public int getAutosaveFilesToKeepComboBoxSelectedIndex() {
        return getAutosaveFilesToKeepComboBox().getSelectedIndex();
    }

    public boolean isDisplayTipsOnStartupCheckBoxSelected() {
        return getDisplayTipsOnStartupCheckBox().isSelected();
    }

    public boolean isAutoEndTurnCheckBoxSelected() {
        return getAutoEndTurnCheckBox().isSelected();
    }

    public boolean isFullScreenCheckBoxSelected() {
        return getFullScreenCheckBox().isSelected();
    }

    public boolean isDisplayUnitMoveAnimationCheckBoxSelected() {
        return getDisplayUnitMoveAnimationCheckBox().isSelected();
    }

    public boolean isDisplayPlayerNationFlagsOnUnitsCheckBoxSelected() {
        return getDisplayPlayerNationFlagsOnUnitsCheckBox().isSelected();
    }

    public boolean isAlwaysCenterOnActiveUnitCheckBoxSelected() {
        return getAlwaysCenterOnActiveUnitCheckBox().isSelected();
    }

    public boolean isMapEdgeScrollingCheckBoxSelected() {
        return getMapEdgeScrollingCheckBox().isSelected();
    }

    public boolean isDisplayTerritoryBordersCheckBoxSelected() {
        return getDisplayTerritoryBordersCheckBox().isSelected();
    }

    public boolean isDisplayAIPlayerProgressWindowCheckBoxSelected() {
        return getDisplayAIPlayerProgressWindowCheckBox().isSelected();
    }

    public int getMiniMapTileDisplayType() {
        if (getMiniMapDisplayTileColorsRadioButton().isSelected()) {
            return 0;
        } else {
            return 1;
        }
    }

    public int getColonyDialogUnitSelectionType() {
        if (getClickUnitSelectionRadioButton().isSelected()) {
            return 0;
        } else {
            return 1;
        }
    }

    public boolean isUnitMovementSoundCheckBoxSelected() {
        return getUnitMovementSoundCheckBox().isSelected();
    }

    public boolean isResourceTransferSoundCheckBoxSelected() {
        return getResourceTransferSoundCheckBox().isSelected();
    }

    public boolean isSpaceshipLaunchSoundCheckBoxSelected() {
        return getSpaceshipLaunchSoundCheckBox().isSelected();
    }

    public boolean isWealthTransferSoundCheckBoxSelected() {
        return getWealthTransferSoundCheckBox().isSelected();
    }

    public boolean isColonyImprovementEnableSoundCheckBoxSelected() {
        return getColonyImprovementEnableSoundCheckBox().isSelected();
    }

    public boolean isColonyImprovementDisableSoundCheckBoxSelected() {
        return getColonyImprovementDisableSoundCheckBox().isSelected();
    }

    private void initializeWidgets() {
        getContentPane().add(getPreferencesTabbedPane(), BorderLayout.CENTER);
        getContentPane().add(getPageEndPanel(), BorderLayout.PAGE_END);
    }

    private void setWidgetValues() {
        try {
            getAutosaveFilesToKeepComboBox().setSelectedIndex(Integer.parseInt(model.getFreeMarsPreferences().getProperty("auto_save_files")) - 1);
            getDisplayTipsOnStartupCheckBox().setSelected(Boolean.valueOf(model.getFreeMarsPreferences().getProperty("display_tips_on_startup")));
            getAutoEndTurnCheckBox().setSelected(Boolean.valueOf(model.getFreeMarsPreferences().getProperty("auto_end_turn")));
            int colonyDialogUnitSelectionType = Integer.parseInt(model.getFreeMarsPreferences().getProperty("colony_dialog_unit_selection_type"));
            if (colonyDialogUnitSelectionType == 0) {
                getClickUnitSelectionRadioButton().setSelected(true);
            } else {
                getHoverUnitSelectionRadioButton().setSelected(true);
            }
            getFullScreenCheckBox().setSelected(Boolean.valueOf(model.getFreeMarsPreferences().getProperty("full_screen")));
            getDisplayUnitMoveAnimationCheckBox().setSelected(Boolean.valueOf(model.getFreeMarsPreferences().getProperty("display_unit_move_animation")));
            getDisplayPlayerNationFlagsOnUnitsCheckBox().setSelected(Boolean.valueOf(model.getFreeMarsPreferences().getProperty("display_player_nation_flags_on_units")));
            getAlwaysCenterOnActiveUnitCheckBox().setSelected(Boolean.valueOf(model.getFreeMarsPreferences().getProperty("always_center_on_active_unit")));
            getMapEdgeScrollingCheckBox().setSelected(Boolean.valueOf(model.getFreeMarsPreferences().getProperty("map_edge_scrolling")));
            getDisplayTerritoryBordersCheckBox().setSelected(Boolean.valueOf(model.getFreeMarsPreferences().getProperty("display_territory_borders")));
            getDisplayAIPlayerProgressWindowCheckBox().setSelected(Boolean.valueOf(model.getFreeMarsPreferences().getProperty("display_ai_player_progress_window")));
            int miniMapTileDisplayType = Integer.parseInt(model.getFreeMarsPreferences().getProperty("mini_map_tile_display_type"));
            if (miniMapTileDisplayType == 0) {
                getMiniMapDisplayTileColorsRadioButton().setSelected(true);
            } else {
                getMiniMapDisplayTileImagesRadioButton().setSelected(true);
            }
            getUnitMovementSoundCheckBox().setSelected(Boolean.valueOf(model.getFreeMarsPreferences().getProperty("unit_movement_sound")));
            getResourceTransferSoundCheckBox().setSelected(Boolean.valueOf(model.getFreeMarsPreferences().getProperty("resource_transfer_sound")));
            getSpaceshipLaunchSoundCheckBox().setSelected(Boolean.valueOf(model.getFreeMarsPreferences().getProperty("spaceship_launch_sound")));
            getWealthTransferSoundCheckBox().setSelected(Boolean.valueOf(model.getFreeMarsPreferences().getProperty("wealth_transfer_sound")));
            getColonyImprovementEnableSoundCheckBox().setSelected(Boolean.valueOf(model.getFreeMarsPreferences().getProperty("colony_improvement_enable_sound")));
            getColonyImprovementDisableSoundCheckBox().setSelected(Boolean.valueOf(model.getFreeMarsPreferences().getProperty("colony_improvement_disable_sound")));
        } catch (Exception exception) {
        }
    }

    private JTabbedPane getPreferencesTabbedPane() {
        if (preferencesTabbedPane == null) {
            preferencesTabbedPane = new JTabbedPane();
            preferencesTabbedPane.addTab("Gameplay", getGameplayPreferencesPanel());
            preferencesTabbedPane.addTab("Screen", getScreenPreferencesPanel());
            preferencesTabbedPane.addTab("Sound", getSoundPreferencesPanel());
        }
        return preferencesTabbedPane;
    }

    private JPanel getGameplayPreferencesPanel() {
        if (gameplayPreferencesPanel == null) {
            gameplayPreferencesPanel = new JPanel(new GridLayout(0, 2, 10, 10));
            gameplayPreferencesPanel.add(getAutosaveLabel());
            gameplayPreferencesPanel.add(getAutosaveComboBox());
            gameplayPreferencesPanel.add(getAutosaveFilesToKeepLabel());
            gameplayPreferencesPanel.add(getAutosaveFilesToKeepComboBox());
            gameplayPreferencesPanel.add(getDisplayTipsOnStartupLabel());
            gameplayPreferencesPanel.add(getDisplayTipsOnStartupCheckBox());
            gameplayPreferencesPanel.add(getAutoEndTurnLabel());
            gameplayPreferencesPanel.add(getAutoEndTurnCheckBox());
            gameplayPreferencesPanel.add(getSelectUnitsInColonyDialogLabel());
            gameplayPreferencesPanel.add(getUnitSelectionInColonyDialogPanel());
            gameplayPreferencesPanel.add(new JLabel());
            gameplayPreferencesPanel.add(new JLabel());
            gameplayPreferencesPanel.add(new JLabel());
            gameplayPreferencesPanel.add(new JLabel());
        }
        return gameplayPreferencesPanel;
    }

    private JLabel getAutosaveLabel() {
        if (autosaveLabel == null) {
            autosaveLabel = new JLabel("Autosave");
        }
        return autosaveLabel;
    }

    private JComboBox getAutosaveComboBox() {
        if (autosaveComboBox == null) {
            autosaveComboBox = new JComboBox();
            autosaveComboBox.addItem("Never");
            autosaveComboBox.addItem("Each turn");
            autosaveComboBox.addItem("Every 2 turns");
            autosaveComboBox.addItem("Every 3 turns");
            autosaveComboBox.addItem("Every 4 turns");
            autosaveComboBox.addItem("Every 5 turns");
            autosaveComboBox.addItem("Every 10 turns");
        }
        return autosaveComboBox;
    }

    private JLabel getAutosaveFilesToKeepLabel() {
        if (autosaveFilesToKeepLabel == null) {
            autosaveFilesToKeepLabel = new JLabel("Autosave files to keep");
        }
        return autosaveFilesToKeepLabel;
    }

    private JComboBox getAutosaveFilesToKeepComboBox() {
        if (autosaveFilesToKeepComboBox == null) {
            autosaveFilesToKeepComboBox = new JComboBox();
            autosaveFilesToKeepComboBox.addItem("1");
            autosaveFilesToKeepComboBox.addItem("2");
            autosaveFilesToKeepComboBox.addItem("3");
            autosaveFilesToKeepComboBox.addItem("4");
            autosaveFilesToKeepComboBox.addItem("5");
        }
        return autosaveFilesToKeepComboBox;
    }

    private JLabel getDisplayTipsOnStartupLabel() {
        if (displayTipsOnStartupLabel == null) {
            displayTipsOnStartupLabel = new JLabel("Display tips on startup");
        }
        return displayTipsOnStartupLabel;
    }

    private JCheckBox getDisplayTipsOnStartupCheckBox() {
        if (displayTipsOnStartupCheckBox == null) {
            displayTipsOnStartupCheckBox = new JCheckBox();
        }
        return displayTipsOnStartupCheckBox;
    }

    private JLabel getAutoEndTurnLabel() {
        if (autoEndTurnLabel == null) {
            autoEndTurnLabel = new JLabel("Auto end turn");
        }
        return autoEndTurnLabel;
    }

    private JCheckBox getAutoEndTurnCheckBox() {
        if (autoEndTurnCheckBox == null) {
            autoEndTurnCheckBox = new JCheckBox();
        }
        return autoEndTurnCheckBox;
    }

    private JLabel getSelectUnitsInColonyDialogLabel() {
        if (unitSelectionInColonyDialogLabel == null) {
            unitSelectionInColonyDialogLabel = new JLabel("Unit selection in colony dialog");
        }
        return unitSelectionInColonyDialogLabel;
    }

    private JPanel getUnitSelectionInColonyDialogPanel() {
        if (unitSelectionInColonyDialogPanel == null) {
            unitSelectionInColonyDialogPanel = new JPanel(new GridLayout(1, 0));
            unitSelectionInColonyDialogPanel.add(getClickUnitSelectionRadioButton());
            unitSelectionInColonyDialogPanel.add(getHoverUnitSelectionRadioButton());
        }
        return unitSelectionInColonyDialogPanel;
    }

    private ButtonGroup getUnitSelectionInColonyDialogButtonGroup() {
        if (unitSelectionInColonyDialogButtonGroup == null) {
            unitSelectionInColonyDialogButtonGroup = new ButtonGroup();
        }
        return unitSelectionInColonyDialogButtonGroup;
    }

    private JRadioButton getClickUnitSelectionRadioButton() {
        if (clickUnitSelectionRadioButton == null) {
            clickUnitSelectionRadioButton = new JRadioButton("Click");
            getUnitSelectionInColonyDialogButtonGroup().add(clickUnitSelectionRadioButton);
        }
        return clickUnitSelectionRadioButton;
    }

    private JRadioButton getHoverUnitSelectionRadioButton() {
        if (hoverUnitSelectionRadioButton == null) {
            hoverUnitSelectionRadioButton = new JRadioButton("Hover");
            getUnitSelectionInColonyDialogButtonGroup().add(hoverUnitSelectionRadioButton);
        }
        return hoverUnitSelectionRadioButton;
    }

    private JPanel getScreenPreferencesPanel() {
        if (screenPreferencesPanel == null) {
            screenPreferencesPanel = new JPanel(new GridLayout(0, 2));
            screenPreferencesPanel.add(getFullScreenLabel());
            screenPreferencesPanel.add(getFullScreenCheckBox());
            screenPreferencesPanel.add(getDisplayUnitMoveAnimationLabel());
            screenPreferencesPanel.add(getDisplayUnitMoveAnimationCheckBox());
            screenPreferencesPanel.add(getDisplayPlayerNationFlagsOnUnitsLabel());
            screenPreferencesPanel.add(getDisplayPlayerNationFlagsOnUnitsCheckBox());
            screenPreferencesPanel.add(getAlwaysCenterOnActiveUnitLabel());
            screenPreferencesPanel.add(getAlwaysCenterOnActiveUnitCheckBox());
            screenPreferencesPanel.add(getMapEdgeScrollingLabel());
            screenPreferencesPanel.add(getMapEdgeScrollingCheckBox());
            screenPreferencesPanel.add(getDisplayTerritoryBordersLabel());
            screenPreferencesPanel.add(getDisplayTerritoryBordersCheckBox());
            screenPreferencesPanel.add(getDisplayAIPlayerProgressWindowLabel());
            screenPreferencesPanel.add(getDisplayAIPlayerProgressWindowCheckBox());
            screenPreferencesPanel.add(getMiniMapDisplayTypeLabel());
            screenPreferencesPanel.add(getMiniMapDisplayTypePanel());
        }
        return screenPreferencesPanel;
    }

    private JLabel getFullScreenLabel() {
        if (fullScreenLabel == null) {
            fullScreenLabel = new JLabel("Full screen");
        }
        return fullScreenLabel;
    }

    private JCheckBox getFullScreenCheckBox() {
        if (fullScreenCheckBox == null) {
            fullScreenCheckBox = new JCheckBox();
        }
        return fullScreenCheckBox;
    }

    private JLabel getDisplayUnitMoveAnimationLabel() {
        if (displayUnitMoveAnimationLabel == null) {
            displayUnitMoveAnimationLabel = new JLabel("Display unit move animation");
        }
        return displayUnitMoveAnimationLabel;
    }

    private JCheckBox getDisplayUnitMoveAnimationCheckBox() {
        if (displayUnitMoveAnimationCheckBox == null) {
            displayUnitMoveAnimationCheckBox = new JCheckBox();
        }
        return displayUnitMoveAnimationCheckBox;
    }

    private JLabel getDisplayPlayerNationFlagsOnUnitsLabel() {
        if (displayPlayerNationFlagsOnUnitsLabel == null) {
            displayPlayerNationFlagsOnUnitsLabel = new JLabel("Display player flags on units");
        }
        return displayPlayerNationFlagsOnUnitsLabel;
    }

    private JCheckBox getDisplayPlayerNationFlagsOnUnitsCheckBox() {
        if (displayPlayerNationFlagsOnUnitsCheckBox == null) {
            displayPlayerNationFlagsOnUnitsCheckBox = new JCheckBox();
        }
        return displayPlayerNationFlagsOnUnitsCheckBox;
    }

    private JLabel getAlwaysCenterOnActiveUnitLabel() {
        if (alwaysCenterOnActiveUnitLabel == null) {
            alwaysCenterOnActiveUnitLabel = new JLabel("Always center on active unit");
        }
        return alwaysCenterOnActiveUnitLabel;
    }

    private JCheckBox getAlwaysCenterOnActiveUnitCheckBox() {
        if (alwaysCenterOnActiveUnitCheckBox == null) {
            alwaysCenterOnActiveUnitCheckBox = new JCheckBox();
        }
        return alwaysCenterOnActiveUnitCheckBox;
    }

    private JLabel getMapEdgeScrollingLabel() {
        if (mapEdgeScrollingLabel == null) {
            mapEdgeScrollingLabel = new JLabel("Map edge scrolling");
        }
        return mapEdgeScrollingLabel;
    }

    private JCheckBox getMapEdgeScrollingCheckBox() {
        if (mapEdgeScrollingCheckBox == null) {
            mapEdgeScrollingCheckBox = new JCheckBox();
        }
        return mapEdgeScrollingCheckBox;
    }

    private JLabel getMiniMapDisplayTypeLabel() {
        if (miniMapDisplayTypeLabel == null) {
            miniMapDisplayTypeLabel = new JLabel("Mini map display");
        }
        return miniMapDisplayTypeLabel;
    }

    private JPanel getMiniMapDisplayTypePanel() {
        if (miniMapDisplayTypePanel == null) {
            miniMapDisplayTypePanel = new JPanel(new GridLayout(1, 2));
            miniMapDisplayTypePanel.add(getMiniMapDisplayTileColorsRadioButton());
            miniMapDisplayTypePanel.add(getMiniMapDisplayTileImagesRadioButton());
        }
        return miniMapDisplayTypePanel;
    }

    private ButtonGroup getMiniMapDisplayTileButtonGroup() {
        if (miniMapDisplayTileButtonGroup == null) {
            miniMapDisplayTileButtonGroup = new ButtonGroup();
        }
        return miniMapDisplayTileButtonGroup;
    }

    private JRadioButton getMiniMapDisplayTileColorsRadioButton() {
        if (miniMapDisplayTileColorsRadioButton == null) {
            miniMapDisplayTileColorsRadioButton = new JRadioButton("Color");
            getMiniMapDisplayTileButtonGroup().add(miniMapDisplayTileColorsRadioButton);
        }
        return miniMapDisplayTileColorsRadioButton;
    }

    private JRadioButton getMiniMapDisplayTileImagesRadioButton() {
        if (miniMapDisplayTileImagesRadioButton == null) {
            miniMapDisplayTileImagesRadioButton = new JRadioButton("Image");
            getMiniMapDisplayTileButtonGroup().add(miniMapDisplayTileImagesRadioButton);
        }
        return miniMapDisplayTileImagesRadioButton;
    }

    private JLabel getDisplayTerritoryBordersLabel() {
        if (displayTerritoryBordersLabel == null) {
            displayTerritoryBordersLabel = new JLabel("Display territory borders");
        }
        return displayTerritoryBordersLabel;
    }

    private JCheckBox getDisplayTerritoryBordersCheckBox() {
        if (displayTerritoryBordersCheckBox == null) {
            displayTerritoryBordersCheckBox = new JCheckBox();
        }
        return displayTerritoryBordersCheckBox;
    }

    private JLabel getDisplayAIPlayerProgressWindowLabel() {
        if (displayAIPlayerProgressWindowLabel == null) {
            displayAIPlayerProgressWindowLabel = new JLabel("Display AI player progress window");
        }
        return displayAIPlayerProgressWindowLabel;
    }

    private JCheckBox getDisplayAIPlayerProgressWindowCheckBox() {
        if (displayAIPlayerProgressWindowCheckBox == null) {
            displayAIPlayerProgressWindowCheckBox = new JCheckBox();
        }
        return displayAIPlayerProgressWindowCheckBox;
    }

    private JPanel getSoundPreferencesPanel() {
        if (soundPreferencesPanel == null) {
            soundPreferencesPanel = new JPanel(new GridLayout(0, 2));
            soundPreferencesPanel.add(getUnitMovementSoundLabel());
            soundPreferencesPanel.add(getUnitMovementSoundCheckBox());
            soundPreferencesPanel.add(getResourceTransferSoundLabel());
            soundPreferencesPanel.add(getResourceTransferSoundCheckBox());
            soundPreferencesPanel.add(getSpaceshipLaunchSoundLabel());
            soundPreferencesPanel.add(getSpaceshipLaunchSoundCheckBox());
            soundPreferencesPanel.add(getWealthTransferSoundLabel());
            soundPreferencesPanel.add(getWealthTransferSoundCheckBox());
            soundPreferencesPanel.add(getColonyImprovementEnableSoundLabel());
            soundPreferencesPanel.add(getColonyImprovementEnableSoundCheckBox());
            soundPreferencesPanel.add(getColonyImprovementDisableSoundLabel());
            soundPreferencesPanel.add(getColonyImprovementDisableSoundCheckBox());
        }
        return soundPreferencesPanel;
    }

    private JLabel getUnitMovementSoundLabel() {
        if (unitMovementSoundLabel == null) {
            unitMovementSoundLabel = new JLabel("Unit movement");
        }
        return unitMovementSoundLabel;
    }

    private JCheckBox getUnitMovementSoundCheckBox() {
        if (unitMovementSoundCheckBox == null) {
            unitMovementSoundCheckBox = new JCheckBox();
        }
        return unitMovementSoundCheckBox;
    }

    private JLabel getResourceTransferSoundLabel() {
        if (resourceTransferSoundLabel == null) {
            resourceTransferSoundLabel = new JLabel("Resource transfer");
        }
        return resourceTransferSoundLabel;
    }

    private JCheckBox getResourceTransferSoundCheckBox() {
        if (resourceTransferSoundCheckBox == null) {
            resourceTransferSoundCheckBox = new JCheckBox();
        }
        return resourceTransferSoundCheckBox;
    }

    private JLabel getSpaceshipLaunchSoundLabel() {
        if (spaceshipLaunchSoundLabel == null) {
            spaceshipLaunchSoundLabel = new JLabel("Spaceship launch");
        }
        return spaceshipLaunchSoundLabel;
    }

    private JCheckBox getSpaceshipLaunchSoundCheckBox() {
        if (spaceshipLaunchSoundCheckBox == null) {
            spaceshipLaunchSoundCheckBox = new JCheckBox();
        }
        return spaceshipLaunchSoundCheckBox;
    }

    private JLabel getWealthTransferSoundLabel() {
        if (wealthTransferSoundLabel == null) {
            wealthTransferSoundLabel = new JLabel("Wealth transfer");
        }
        return wealthTransferSoundLabel;
    }

    private JCheckBox getWealthTransferSoundCheckBox() {
        if (wealthTransferSoundCheckBox == null) {
            wealthTransferSoundCheckBox = new JCheckBox();
        }
        return wealthTransferSoundCheckBox;
    }

    private JLabel getColonyImprovementEnableSoundLabel() {
        if (colonyImprovementEnableSoundLabel == null) {
            colonyImprovementEnableSoundLabel = new JLabel("Colony improvement enable");
        }
        return colonyImprovementEnableSoundLabel;
    }

    private JCheckBox getColonyImprovementEnableSoundCheckBox() {
        if (colonyImprovementEnableSoundCheckBox == null) {
            colonyImprovementEnableSoundCheckBox = new JCheckBox();
        }
        return colonyImprovementEnableSoundCheckBox;
    }

    private JLabel getColonyImprovementDisableSoundLabel() {
        if (colonyImprovementDisableSoundLabel == null) {
            colonyImprovementDisableSoundLabel = new JLabel("Colony improvement disable");
        }
        return colonyImprovementDisableSoundLabel;
    }

    private JCheckBox getColonyImprovementDisableSoundCheckBox() {
        if (colonyImprovementDisableSoundCheckBox == null) {
            colonyImprovementDisableSoundCheckBox = new JCheckBox();
        }
        return colonyImprovementDisableSoundCheckBox;
    }

    private JPanel getPageEndPanel() {
        if (pageEndPanel == null) {
            pageEndPanel = new JPanel();
            pageEndPanel.add(getConfirmButton());
            pageEndPanel.add(getCancelButton());
        }
        return pageEndPanel;
    }

    private JButton getConfirmButton() {
        if (confirmButton == null) {
            confirmButton = new JButton("Confirm");
        }
        return confirmButton;
    }

    private JButton getCancelButton() {
        if (cancelButton == null) {
            cancelButton = new JButton("Cancel");
            cancelButton.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    dispose();
                }
            });
        }
        return cancelButton;
    }
}
