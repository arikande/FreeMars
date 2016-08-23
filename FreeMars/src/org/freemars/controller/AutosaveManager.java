package org.freemars.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.freemars.model.FreeMarsModel;
import org.freemars.util.SaveLoadUtility;

/**
 *
 * @author arikande
 */
public class AutosaveManager {

    private final FreeMarsModel model;
    private int lastAutosaveTurn;
    private final List<String> autosaveFileNames;

    public AutosaveManager(FreeMarsModel model) {
        this.model = model;
        autosaveFileNames = new ArrayList<String>();
    }

    public void manageAutosave() {
        int autosavePeriod = Integer.parseInt(model.getFreeMarsPreferences().getProperty("auto_save_period_in_turns"));
        int autosaveFilesToKeep = Integer.parseInt(model.getFreeMarsPreferences().getProperty("auto_save_files"));
        if (autosavePeriod > 0) {
            int currentTurn = model.getNumberOfTurns();
            if ((currentTurn - lastAutosaveTurn) >= autosavePeriod) {
                try {
                    String fileName = getAutosaveFileName();
                    File file = new File(fileName);
                    SaveLoadUtility.saveGameToFile(file, model);
                    autosaveFileNames.add(fileName);
                    int autosaveFileCount = autosaveFileNames.size();
                    if (autosaveFileCount > autosaveFilesToKeep) {
                        for (int i = 0; i < autosaveFileCount - autosaveFilesToKeep; i++) {
                            try {
                                new File(autosaveFileNames.get(0)).delete();
                            } catch (Exception e) {
                            }
                            autosaveFileNames.remove(0);
                        }
                    }
                } catch (Exception e) {
                }
                lastAutosaveTurn = currentTurn;
            }
        }
    }

    private String getAutosaveFileName() {
        String userHomeDirectory = System.getProperty("user.home");
        String fileName = userHomeDirectory;
        fileName = fileName + "\\FreeMars\\Autosave-";
        fileName = fileName + model.getHumanPlayer().getName();
        fileName = fileName + "-";
        fileName = fileName + String.valueOf(model.getNumberOfTurns());
        fileName = fileName + ".fms";
        return fileName;
    }
}
