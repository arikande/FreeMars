package org.freemars.controller.action.file;

import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import org.freemars.controller.FreeMarsController;
import org.freemars.ui.util.FreeMarsOptionPane;
import org.freemars.ui.util.FreeMarsSaveFileChooser;
import org.freemars.util.SaveLoadUtility;

/**
 *
 * @author Deniz ARIKAN
 */
public class SaveGameAction extends AbstractAction {

    private final FreeMarsController controller;

    public SaveGameAction(FreeMarsController controller) {
        super("Save");
        this.controller = controller;
    }

    public void actionPerformed(ActionEvent e) {
        FreeMarsSaveFileChooser tbsFileChooser = new FreeMarsSaveFileChooser();
        if (tbsFileChooser.showSaveDialog(controller.getCurrentFrame()) == JFileChooser.APPROVE_OPTION) {
            try {
                File file = tbsFileChooser.getSelectedFile();
                if (!file.getName().endsWith(".fms")) {
                    file = new File(file.getAbsoluteFile() + ".fms");
                }
                SaveLoadUtility.saveGameToFile(file, controller.getFreeMarsModel());
                FreeMarsOptionPane.showMessageDialog(controller.getCurrentFrame(), "Save complete");
            } catch (Exception ex) {
                ex.printStackTrace();
                FreeMarsOptionPane.showMessageDialog(controller.getCurrentFrame(), "Error while saving");
            }
        }
    }
}
