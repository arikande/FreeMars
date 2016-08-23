package org.freemars.ui.wizard.newgame;

import com.nexes.wizard.WizardPanelDescriptor;
import org.freemars.model.wizard.newgame.NewGameOptions;

public class ReviewSettingsPanelDescriptor extends WizardPanelDescriptor {

    public static final String IDENTIFIER = "REVIEW_SETTINGS_PANEL";

    public ReviewSettingsPanelDescriptor() {
        super(IDENTIFIER, new ReviewSettingsPanel());
    }

    @Override
    public Object getNextPanelDescriptor() {
        return FINISH;
    }

    @Override
    public Object getBackPanelDescriptor() {
        return SelectGameObjectivesPanelDescriptor.IDENTIFIER;
    }

    @Override
    public void aboutToDisplayPanel() {
        NewGameOptions newGameOptions = ((NewGameWizard) getWizard()).getNewGameOptions();
        ((ReviewSettingsPanel) getPanelComponent()).setNewGameOptions(newGameOptions);
    }
}