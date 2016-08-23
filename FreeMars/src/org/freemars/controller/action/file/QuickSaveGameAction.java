package org.freemars.controller.action.file;

import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.AbstractAction;
import org.apache.log4j.Logger;
import org.freemars.controller.FreeMarsController;
import org.freemars.ui.util.FreeMarsOptionPane;
import org.freemars.util.SaveLoadUtility;

/**
 *
 * @author Deniz ARIKAN
 */
public class QuickSaveGameAction extends AbstractAction {

    private final FreeMarsController controller;

    public QuickSaveGameAction(FreeMarsController controller) {
        super("Quick Save");
        this.controller = controller;
    }

    public void actionPerformed(ActionEvent e) {
        try {
            String userHomeDirectory = System.getProperty("user.home");
            File file = new File(userHomeDirectory + System.getProperty("file.separator") + "FreeMars" + System.getProperty("file.separator") + "FreeMarsQuickLoad.fms");
            SaveLoadUtility.saveGameToFile(file, controller.getFreeMarsModel());
            Logger.getLogger(QuickSaveGameAction.class).info("Quick Save complete.");
            FreeMarsOptionPane.showMessageDialog(controller.getCurrentFrame(), "Quick Save complete");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
