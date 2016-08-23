package org.freemars.ui.wizard.newgame;

import com.nexes.wizard.WizardPanelDescriptor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.freemars.model.wizard.newgame.NewGameOptions;

public class ChooseMapCustomizationPanelDescriptor extends WizardPanelDescriptor implements ActionListener {

    public static final String IDENTIFIER = "CHOOSE_MAP_TYPE_PANEL";

    public ChooseMapCustomizationPanelDescriptor() {
        ChooseMapCustomizationPanel chooseMapCustomizationPanel = new ChooseMapCustomizationPanel();
        chooseMapCustomizationPanel.addMapSelectionButtonGroupActionListener(this);
        setPanelDescriptorIdentifier(IDENTIFIER);
        setPanelComponent(chooseMapCustomizationPanel);
    }

    @Override
    public Object getNextPanelDescriptor() {
        NewGameOptions newGameOptions = ((NewGameWizard) getWizard()).getNewGameOptions();
        if (newGameOptions.getMapType() == NewGameOptions.CUSTOMIZED_MAP) {
            return CustomizationValuesPanelDescriptor.IDENTIFIER;
        } else {
            return SelectPremadeMapPanelDescriptor.IDENTIFIER;
        }
    }

    @Override
    public Object getBackPanelDescriptor() {
        return null;
    }

    public void actionPerformed(ActionEvent e) {
        NewGameOptions newGameOptions = ((NewGameWizard) getWizard()).getNewGameOptions();
        if (e.getActionCommand().equals("CUSTOMIZE_MAP_RADIO")) {
            newGameOptions.setMapType(NewGameOptions.CUSTOMIZED_MAP);
        } else if (e.getActionCommand().equals("PREMADE_MAP_RADIO")) {
            newGameOptions.setMapType(NewGameOptions.PREMADE_MAP);
        }
    }
}