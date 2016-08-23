package org.freemars.controller.action.file;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.freemars.controller.FreeMarsController;
import org.freemars.ui.player.preferences.ConfirmPreferencesAction;
import org.freemars.ui.player.preferences.PreferencesDialog;

/**
 *
 * @author Deniz ARIKAN
 */
public class DisplayPreferencesDialogAction extends AbstractAction {

    private final FreeMarsController controller;

    public DisplayPreferencesDialogAction(FreeMarsController controller) {
        super("Preferences", null);
        this.controller = controller;
    }

    public void actionPerformed(ActionEvent e) {
        PreferencesDialog preferencesDialog = new PreferencesDialog(controller.getCurrentFrame(), controller.getFreeMarsModel());
        int autosavePeriodInTurns = Integer.parseInt(controller.getFreeMarsModel().getFreeMarsPreferences().getProperty("auto_save_period_in_turns"));
        preferencesDialog.setAutosaveComboBoxSelectedIndex(getRelatedAutosaveComboBoxIndex(autosavePeriodInTurns));
        preferencesDialog.setConfirmButtonAction(new ConfirmPreferencesAction(controller, preferencesDialog));
        preferencesDialog.display();
    }

    private int getRelatedAutosaveComboBoxIndex(int autosavePeriodInTurns) {
        if (autosavePeriodInTurns == 10) {
            return 6;
        } else {
            return autosavePeriodInTurns;
        }
    }
}
