package org.freemars.ui.wizard.newgame;

import com.nexes.wizard.Wizard;
import com.nexes.wizard.WizardPanelDescriptor;
import java.awt.Frame;
import org.freemars.controller.FreeMarsController;
import org.freemars.model.wizard.newgame.NewGameOptions;

/**
 *
 * @author Deniz ARIKAN
 */
public class NewGameWizard extends Wizard {

    private static final int NEW_GAME_WIZARD_DIALOG_HEIGHT = 560;
    private static final int NEW_GAME_WIZARD_DIALOG_WIDTH = 680;
    public static final int NEW_GAME_FINISH_RETURN_CODE = Wizard.FINISH_RETURN_CODE;
    private NewGameOptions newGameOptions;

    public NewGameWizard(Frame owner, FreeMarsController freeMarsController) {
        super(owner);
        newGameOptions = new NewGameOptions();
        newGameOptions.setFreeMarsModel(freeMarsController);
        getDialog().setTitle("Start a new game");
        WizardPanelDescriptor descriptor1 = new ChooseMapCustomizationPanelDescriptor();
        registerWizardPanel(ChooseMapCustomizationPanelDescriptor.IDENTIFIER, descriptor1);
        WizardPanelDescriptor descriptor2a = new CustomizationValuesPanelDescriptor(freeMarsController.getFreeMarsModel().getRealm().getTileTypeManager());
        registerWizardPanel(CustomizationValuesPanelDescriptor.IDENTIFIER, descriptor2a);
        WizardPanelDescriptor descriptor2b = new SelectPremadeMapPanelDescriptor();
        registerWizardPanel(SelectPremadeMapPanelDescriptor.IDENTIFIER, descriptor2b);
        WizardPanelDescriptor descriptor3 = new ChooseNationsPanelDescriptor();
        registerWizardPanel(ChooseNationsPanelDescriptor.IDENTIFIER, descriptor3);
        WizardPanelDescriptor descriptor4 = new SelectGameObjectivesPanelDescriptor();
        registerWizardPanel(SelectGameObjectivesPanelDescriptor.IDENTIFIER, descriptor4);
        WizardPanelDescriptor descriptor5 = new ReviewSettingsPanelDescriptor();
        registerWizardPanel(ReviewSettingsPanelDescriptor.IDENTIFIER, descriptor5);
        setCurrentPanel(ChooseMapCustomizationPanelDescriptor.IDENTIFIER);
    }

    public NewGameOptions showNewGameWizardDialog() {
        getDialog().setModal(true);
        getDialog().pack();
        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        getDialog().setBounds((screenSize.width - NEW_GAME_WIZARD_DIALOG_WIDTH) / 2, (screenSize.height - NEW_GAME_WIZARD_DIALOG_HEIGHT) / 2, NEW_GAME_WIZARD_DIALOG_WIDTH, NEW_GAME_WIZARD_DIALOG_HEIGHT);
        getDialog().setVisible(true);
        getNewGameOptions().setReturnCode(getReturnCode());
        return getNewGameOptions();
    }

    public NewGameOptions getNewGameOptions() {
        return newGameOptions;
    }

    public void setNewGameOptions(NewGameOptions newGameOptions) {
        this.newGameOptions = newGameOptions;
    }
}
