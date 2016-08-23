package org.freemars.ui.player.preferences;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 *
 * @author Deniz ARIKAN
 */
public class FreeMarsPreferences extends Properties {

    private static final Logger logger = Logger.getLogger(FreeMarsPreferences.class);

    public void readPreferences() {
        clear();
        setProperty("auto_save_period_in_turns", "5");
        setProperty("auto_save_files", "5");
        setProperty("display_tips_on_startup", "false");
        setProperty("auto_end_turn", "false");
        setProperty("colony_dialog_unit_selection_type", "0");
        setProperty("full_screen", "false");
        setProperty("display_unit_move_animation", "true");
        setProperty("display_player_nation_flags_on_units", "false");
        setProperty("always_center_on_active_unit", "false");
        setProperty("map_edge_scrolling", "false");
        setProperty("mini_map_tile_display_type", "0");
        setProperty("display_territory_borders", "true");
        setProperty("display_ai_player_progress_window", "true");
        setProperty("unit_movement_sound", "false");
        setProperty("resource_transfer_sound", "true");
        setProperty("spaceship_launch_sound", "true");
        setProperty("wealth_transfer_sound", "true");
        setProperty("colony_improvement_enable_sound", "true");
        setProperty("colony_improvement_disable_sound", "true");
        File preferenecesFile = new File(getPreferencesFilePath());
        if (!preferenecesFile.exists()) {
            logger.info("Preferences file not found, saving file with default values.");
            savePreferences();
        } else {
            readPreferencesFile(preferenecesFile);
            logger.info("Preferences file read.");
        }
    }

    public void savePreferences() {
        StringBuilder preferencesFileContent = getPreferencesFileContent();
        try {
            logger.info("Saving Preferences file...");
            File file = new File(getPreferencesFilePath());
            FileWriter fw = new FileWriter(file);
            fw.write(preferencesFileContent.toString());
            fw.close();
            logger.info("Preferences file saved.");
        } catch (IOException iOException) {
            logger.error("IO exception while saving preferences file.");
            logger.error("Exception message : " + iOException.getMessage());
        }
    }

    private StringBuilder getPreferencesFileContent() {
        StringBuilder preferencesFileContent = new StringBuilder();
        preferencesFileContent.append("auto_save_period_in_turns=");
        preferencesFileContent.append(getProperty("auto_save_period_in_turns"));
        preferencesFileContent.append("\n");
        preferencesFileContent.append("auto_save_files=");
        preferencesFileContent.append(getProperty("auto_save_files"));
        preferencesFileContent.append("\n");
        preferencesFileContent.append("display_tips_on_startup=");
        preferencesFileContent.append(getProperty("display_tips_on_startup"));
        preferencesFileContent.append("\n");
        preferencesFileContent.append("auto_end_turn=");
        preferencesFileContent.append(getProperty("auto_end_turn"));
        preferencesFileContent.append("\n");
        preferencesFileContent.append("colony_dialog_unit_selection_type=");
        preferencesFileContent.append(getProperty("colony_dialog_unit_selection_type"));
        preferencesFileContent.append("\n");
        preferencesFileContent.append("full_screen=");
        preferencesFileContent.append(getProperty("full_screen"));
        preferencesFileContent.append("\n");
        preferencesFileContent.append("display_unit_move_animation=");
        preferencesFileContent.append(getProperty("display_unit_move_animation"));
        preferencesFileContent.append("\n");
        preferencesFileContent.append("display_player_nation_flags_on_units=");
        preferencesFileContent.append(getProperty("display_player_nation_flags_on_units"));
        preferencesFileContent.append("\n");
        preferencesFileContent.append("always_center_on_active_unit=");
        preferencesFileContent.append(getProperty("always_center_on_active_unit"));
        preferencesFileContent.append("\n");
        preferencesFileContent.append("map_edge_scrolling=");
        preferencesFileContent.append(getProperty("map_edge_scrolling"));
        preferencesFileContent.append("\n");
        preferencesFileContent.append("mini_map_tile_display_type=");
        preferencesFileContent.append(getProperty("mini_map_tile_display_type"));
        preferencesFileContent.append("\n");
        preferencesFileContent.append("display_territory_borders=");
        preferencesFileContent.append(getProperty("display_territory_borders"));
        preferencesFileContent.append("\n");
        preferencesFileContent.append("display_ai_player_progress_window=");
        preferencesFileContent.append(getProperty("display_ai_player_progress_window"));
        preferencesFileContent.append("\n");
        preferencesFileContent.append("unit_movement_sound=");
        preferencesFileContent.append(getProperty("unit_movement_sound"));
        preferencesFileContent.append("\n");
        preferencesFileContent.append("resource_transfer_sound=");
        preferencesFileContent.append(getProperty("resource_transfer_sound"));
        preferencesFileContent.append("\n");
        preferencesFileContent.append("spaceship_launch_sound=");
        preferencesFileContent.append(getProperty("spaceship_launch_sound"));
        preferencesFileContent.append("\n");
        preferencesFileContent.append("wealth_transfer_sound=");
        preferencesFileContent.append(getProperty("wealth_transfer_sound"));
        preferencesFileContent.append("\n");
        preferencesFileContent.append("colony_improvement_enable_sound=");
        preferencesFileContent.append(getProperty("colony_improvement_enable_sound"));
        preferencesFileContent.append("\n");
        preferencesFileContent.append("colony_improvement_disable_sound=");
        preferencesFileContent.append(getProperty("colony_improvement_disable_sound"));
        preferencesFileContent.append("\n");
        return preferencesFileContent;
    }

    private String getPreferencesFilePath() {
        String fileSeparator = System.getProperty("file.separator");
        return System.getProperty("user.home") + fileSeparator + "FreeMars" + fileSeparator + "FreeMarsPreferences.fmp";
    }

    private void readPreferencesFile(File preferenecesFile) {
        try {
            load(new BufferedInputStream(new FileInputStream(preferenecesFile)));
        } catch (IOException iOException) {
            logger.error("IO exception while reading preferences file.");
            logger.error("Exception message : " + iOException.getMessage());
        }
    }
}
