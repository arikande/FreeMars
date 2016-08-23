package org.freemars.mission;

import java.awt.Frame;
import java.text.DecimalFormat;
import java.util.Iterator;
import org.freemars.model.FreeMarsModel;
import org.freemars.ui.image.FreeMarsImageManager;
import org.freerealm.player.mission.PopulationMission;
import org.freerealm.player.mission.Reward;

/**
 *
 * @author Deniz ARIKAN
 */
public class PopulationMissionCompletedDialog extends MissionDialog {

    public PopulationMissionCompletedDialog(Frame owner, FreeMarsModel model, PopulationMission populationMission) {
        super(owner);
        setTitle("Population mission completed");
        setImage(FreeMarsImageManager.getImage("POPULATION_MISSION"));
        appendMissionExplanation("<br>To colonial governor,<br><br>");
        appendMissionExplanation("You have reached target population of <b>" + new DecimalFormat().format(populationMission.getPopulation()) + " ");
        appendMissionExplanation("colonists</b> on Mars. News of this will surely attract more colonists from the ");
        appendMissionExplanation("overpopulated Earth.<br><br>");
        Iterator<Reward> iterator = populationMission.getRewardsIterator();
        while (iterator.hasNext()) {
            Reward reward = iterator.next();
            if (reward instanceof WealthReward) {
                appendMissionExplanation("Your mission reward of ");
                WealthReward wealthReward = (WealthReward) reward;
                appendMissionExplanation(new DecimalFormat().format(wealthReward.getAmount()) + " " + model.getRealm().getProperty("currency_unit") + " ");
                appendMissionExplanation("has been added to your treasury.");
            }
        }
    }
}
