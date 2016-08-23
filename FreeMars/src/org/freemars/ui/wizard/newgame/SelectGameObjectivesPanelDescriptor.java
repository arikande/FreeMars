package org.freemars.ui.wizard.newgame;

import com.nexes.wizard.WizardPanelDescriptor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.freemars.model.objective.DeclareAndDefendIndependenceObjective;
import org.freemars.model.objective.DefeatOtherPlayersObjective;
import org.freemars.model.objective.ReachPopulationObjective;
import org.freemars.model.objective.ReachTreasuryObjective;
import org.freemars.model.wizard.newgame.NewGameOptions;

public class SelectGameObjectivesPanelDescriptor extends WizardPanelDescriptor implements ActionListener {

    public static final String IDENTIFIER = "SELECT_GAME_OBJECTIVES_PANEL";

    public SelectGameObjectivesPanelDescriptor() {
        SelectGameObjectivesPanel selectGameObjectivesPanel = new SelectGameObjectivesPanel();
        selectGameObjectivesPanel.setActionListener(this);
        setPanelDescriptorIdentifier(IDENTIFIER);
        setPanelComponent(selectGameObjectivesPanel);
    }

    @Override
    public Object getNextPanelDescriptor() {
        return ReviewSettingsPanelDescriptor.IDENTIFIER;
    }

    @Override
    public Object getBackPanelDescriptor() {
        return ChooseNationsPanelDescriptor.IDENTIFIER;
    }

    @Override
    public void aboutToHidePanel() {
        NewGameOptions newGameOptions = ((NewGameWizard) getWizard()).getNewGameOptions();
        SelectGameObjectivesPanel selectGameObjectivesPanel = (SelectGameObjectivesPanel) getPanelComponent();
        newGameOptions.clearObjectives();
        newGameOptions.addObjective(new DeclareAndDefendIndependenceObjective(newGameOptions.getFreeMarsController().getFreeMarsModel()));
        if (selectGameObjectivesPanel.isDefeatOtherPlayersObjectiveSelected()) {
            newGameOptions.addObjective(new DefeatOtherPlayersObjective());
        }
        if (selectGameObjectivesPanel.isReachPopulationObjectiveSelected()) {
            newGameOptions.addObjective(new ReachPopulationObjective(selectGameObjectivesPanel.getObjectivePopulation()));
        }
        if (selectGameObjectivesPanel.isReachTreasuryObjectiveSelected()) {
            newGameOptions.addObjective(new ReachTreasuryObjective(selectGameObjectivesPanel.getObjectiveTreasury()));
        }
    }

    public void actionPerformed(ActionEvent e) {
    }
}
