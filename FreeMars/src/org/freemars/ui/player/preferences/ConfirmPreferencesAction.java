package org.freemars.ui.player.preferences;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.freemars.controller.FreeMarsController;

/**
 *
 * @author Deniz ARIKAN
 */
public class ConfirmPreferencesAction extends AbstractAction {

    private final FreeMarsController freeMarsController;
    private final PreferencesDialog preferencesDialog;

    public ConfirmPreferencesAction(FreeMarsController freeMarsController, PreferencesDialog preferencesDialog) {
        this.freeMarsController = freeMarsController;
        this.preferencesDialog = preferencesDialog;
    }

    public void actionPerformed(ActionEvent e) {
        if (preferencesDialog.getAutosaveComboBoxSelectedIndex() == 6) {
            freeMarsController.getFreeMarsModel().getFreeMarsPreferences().setProperty("auto_save_period_in_turns", "10");
        } else {
            freeMarsController.getFreeMarsModel().getFreeMarsPreferences().setProperty("auto_save_period_in_turns", String.valueOf(preferencesDialog.getAutosaveComboBoxSelectedIndex()));
        }
        boolean oldFullScreenValue = Boolean.valueOf(freeMarsController.getFreeMarsModel().getFreeMarsPreferences().getProperty("full_screen"));
        freeMarsController.getFreeMarsModel().getFreeMarsPreferences().setProperty("auto_save_files", String.valueOf(preferencesDialog.getAutosaveFilesToKeepComboBoxSelectedIndex() + 1));
        freeMarsController.getFreeMarsModel().getFreeMarsPreferences().setProperty("display_tips_on_startup", String.valueOf(preferencesDialog.isDisplayTipsOnStartupCheckBoxSelected()));
        freeMarsController.getFreeMarsModel().getFreeMarsPreferences().setProperty("auto_end_turn", String.valueOf(preferencesDialog.isAutoEndTurnCheckBoxSelected()));
        freeMarsController.getFreeMarsModel().getFreeMarsPreferences().setProperty("colony_dialog_unit_selection_type", String.valueOf(preferencesDialog.getColonyDialogUnitSelectionType()));
        freeMarsController.getFreeMarsModel().getFreeMarsPreferences().setProperty("full_screen", String.valueOf(preferencesDialog.isFullScreenCheckBoxSelected()));
        freeMarsController.getFreeMarsModel().getFreeMarsPreferences().setProperty("display_unit_move_animation", String.valueOf(preferencesDialog.isDisplayUnitMoveAnimationCheckBoxSelected()));
        freeMarsController.getFreeMarsModel().getFreeMarsPreferences().setProperty("display_player_nation_flags_on_units", String.valueOf(preferencesDialog.isDisplayPlayerNationFlagsOnUnitsCheckBoxSelected()));
        freeMarsController.getFreeMarsModel().getFreeMarsPreferences().setProperty("always_center_on_active_unit", String.valueOf(preferencesDialog.isAlwaysCenterOnActiveUnitCheckBoxSelected()));
        freeMarsController.getFreeMarsModel().getFreeMarsPreferences().setProperty("map_edge_scrolling", String.valueOf(preferencesDialog.isMapEdgeScrollingCheckBoxSelected()));
        freeMarsController.getFreeMarsModel().getFreeMarsPreferences().setProperty("display_territory_borders", String.valueOf(preferencesDialog.isDisplayTerritoryBordersCheckBoxSelected()));
        freeMarsController.getFreeMarsModel().getFreeMarsPreferences().setProperty("display_ai_player_progress_window", String.valueOf(preferencesDialog.isDisplayAIPlayerProgressWindowCheckBoxSelected()));
        freeMarsController.getFreeMarsModel().getFreeMarsPreferences().setProperty("mini_map_tile_display_type", String.valueOf(preferencesDialog.getMiniMapTileDisplayType()));
        freeMarsController.getFreeMarsModel().getFreeMarsPreferences().setProperty("unit_movement_sound", String.valueOf(preferencesDialog.isUnitMovementSoundCheckBoxSelected()));
        freeMarsController.getFreeMarsModel().getFreeMarsPreferences().setProperty("resource_transfer_sound", String.valueOf(preferencesDialog.isResourceTransferSoundCheckBoxSelected()));
        freeMarsController.getFreeMarsModel().getFreeMarsPreferences().setProperty("spaceship_launch_sound", String.valueOf(preferencesDialog.isSpaceshipLaunchSoundCheckBoxSelected()));
        freeMarsController.getFreeMarsModel().getFreeMarsPreferences().setProperty("wealth_transfer_sound", String.valueOf(preferencesDialog.isWealthTransferSoundCheckBoxSelected()));
        freeMarsController.getFreeMarsModel().getFreeMarsPreferences().setProperty("colony_improvement_enable_sound", String.valueOf(preferencesDialog.isColonyImprovementEnableSoundCheckBoxSelected()));
        freeMarsController.getFreeMarsModel().getFreeMarsPreferences().setProperty("colony_improvement_disable_sound", String.valueOf(preferencesDialog.isColonyImprovementDisableSoundCheckBoxSelected()));
        freeMarsController.getFreeMarsModel().getFreeMarsPreferences().savePreferences();
        boolean newFullScreenValue = Boolean.valueOf(freeMarsController.getFreeMarsModel().getFreeMarsPreferences().getProperty("full_screen"));
        preferencesDialog.dispose();
        if (oldFullScreenValue != newFullScreenValue) {
            freeMarsController.reDisplayCurrentFrame();
        }
        if (freeMarsController.getGameFrame().isVisible()) {
            freeMarsController.updateGameFrame();
        }
    }
}
