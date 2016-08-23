package org.freemars.ui.wizard.newgame;

import com.nexes.wizard.WizardPanelDescriptor;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.ImageIcon;
import org.freemars.model.wizard.newgame.NewGameOptions;
import org.freemars.ui.image.FreeMarsImageManager;
import org.freerealm.map.Map;
import org.freerealm.nation.Nation;

public class ChooseNationsPanelDescriptor extends WizardPanelDescriptor implements ItemListener, ActionListener {

    public static final String IDENTIFIER = "CHOOSE_NATIONS_PANEL";
    private final List<Color> selectedColors;
    private boolean sameColorSelected;

    public ChooseNationsPanelDescriptor() {
        ChooseNationsPanel chooseNationsPanel = new ChooseNationsPanel();
        setPanelDescriptorIdentifier(IDENTIFIER);
        setPanelComponent(chooseNationsPanel);
        selectedColors = new ArrayList<Color>();
    }

    @Override
    public Object getNextPanelDescriptor() {
        return SelectGameObjectivesPanelDescriptor.IDENTIFIER;
    }

    @Override
    public Object getBackPanelDescriptor() {
        NewGameOptions newGameOptions = ((NewGameWizard) getWizard()).getNewGameOptions();
        if (newGameOptions.getMapType() == NewGameOptions.CUSTOMIZED_MAP) {
            return CustomizationValuesPanelDescriptor.IDENTIFIER;
        } else {
            return SelectPremadeMapPanelDescriptor.IDENTIFIER;
        }
    }

    @Override
    public void aboutToDisplayPanel() {
        NewGameOptions newGameOptions = ((NewGameWizard) getWizard()).getNewGameOptions();
        List<Nation> selectedNations = newGameOptions.getNations();
        ((ChooseNationsPanel) getPanelComponent()).populatePlayerOptionsPanel(newGameOptions.getFreeMarsController().getFreeMarsModel().getRealm().getNationManager(), selectedNations);
        ((ChooseNationsPanel) getPanelComponent()).addNationOptionsCheckBoxListener(this);
        ((ChooseNationsPanel) getPanelComponent()).addNationOptionsColorChooserListener(this);
        setNextButtonAccordingToCheckBox();
    }

    @Override
    public void aboutToHidePanel() {
        ((NewGameWizard) getWizard()).getNewGameOptions().clearNations();
        ChooseNationsPanel chooseNationsPanel = (ChooseNationsPanel) getPanelComponent();
        ((NewGameWizard) getWizard()).getNewGameOptions().setHumanPlayerNation(chooseNationsPanel.getHumanPlayerNation());
        Iterator<NationCheckBoxPanel> nationOptions = chooseNationsPanel.getNationOptions().iterator();
        while (nationOptions.hasNext()) {
            NationCheckBoxPanel nationCheckBoxPanel = nationOptions.next();
            if (nationCheckBoxPanel.isSelected()) {
                Nation nation = nationCheckBoxPanel.getNation();
                Color color1 = nationCheckBoxPanel.getSelectedColor1();
                Color color2 = nationCheckBoxPanel.getSelectedColor2();
                ((NewGameWizard) getWizard()).getNewGameOptions().addNation(nation);
                ((NewGameWizard) getWizard()).getNewGameOptions().setNationPrimaryColor(nation, color1);
                ((NewGameWizard) getWizard()).getNewGameOptions().setNationSecondaryColor(nation, color2);
            }
        }
    }

    public void itemStateChanged(ItemEvent e) {
        setNextButtonAccordingToCheckBox();
    }

    public void actionPerformed(ActionEvent e) {
        setNextButtonAccordingToCheckBox();
    }

    private void setNextButtonAccordingToCheckBox() {
        int playerCount = 0;
        sameColorSelected = false;
        selectedColors.clear();
        ChooseNationsPanel chooseNationsPanel = (ChooseNationsPanel) getPanelComponent();
        chooseNationsPanel.getHumanPlayerComboBox().removeAllItems();
        Iterator<NationCheckBoxPanel> nationOptionsIterator = chooseNationsPanel.getNationOptions().iterator();
        while (nationOptionsIterator.hasNext()) {
            NationCheckBoxPanel nationCheckBoxPanel = nationOptionsIterator.next();
            if (nationCheckBoxPanel.isSelected()) {
                playerCount = playerCount + 1;
                chooseNationsPanel.getHumanPlayerComboBox().addItem(nationCheckBoxPanel.getNation());
                if (selectedColors.contains(nationCheckBoxPanel.getSelectedColor1())) {
//                    sameColorSelected = true;
                } else {
                    selectedColors.add(nationCheckBoxPanel.getSelectedColor1());
                }
            }
        }
        if (((NewGameWizard) getWizard()).getNewGameOptions().getHumanPlayerNation() != null) {
            chooseNationsPanel.getHumanPlayerComboBox().setSelectedItem(((NewGameWizard) getWizard()).getNewGameOptions().getHumanPlayerNation());
        }
        if (playerCount < 2 || sameColorSelected) {
            getWizard().setNextFinishButtonEnabled(false);
            chooseNationsPanel.getHumanPlayerComboBox().setEnabled(false);
        } else {
            getWizard().setNextFinishButtonEnabled(true);
            chooseNationsPanel.getHumanPlayerComboBox().setEnabled(true);
        }
        Map map = ((NewGameWizard) getWizard()).getNewGameOptions().getFreeMarsController().getFreeMarsModel().getRealm().getMap();
        if (playerCount > map.getSuggestedPlayers() && map.getSuggestedPlayers() > 0) {
            Image warningImage = FreeMarsImageManager.getImage("WARNING");
            chooseNationsPanel.setPlayerCountSuggestionLabelText("Suggested number of players for the selected map is " + map.getSuggestedPlayers());
            chooseNationsPanel.setPlayerCountSuggestionLabelIcon(new ImageIcon(warningImage));
        } else {
            chooseNationsPanel.setPlayerCountSuggestionLabelText("");
            chooseNationsPanel.setPlayerCountSuggestionLabelIcon(null);
        }
    }
}
