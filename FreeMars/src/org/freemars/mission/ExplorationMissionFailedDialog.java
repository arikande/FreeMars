package org.freemars.mission;

import java.awt.Frame;
import java.text.DecimalFormat;
import java.util.Iterator;
import org.freemars.model.FreeMarsModel;
import org.freemars.ui.image.FreeMarsImageManager;
import org.freerealm.player.mission.ExplorationMission;
import org.freerealm.player.mission.Reward;

/**
 *
 * @author Deniz ARIKAN
 */
public class ExplorationMissionFailedDialog extends MissionDialog {

    public ExplorationMissionFailedDialog(Frame owner, FreeMarsModel model, ExplorationMission explorationMission) {
        super(owner);
        setTitle("Exploration mission failed");
        setImage(FreeMarsImageManager.getImage("EXPLORATION_MISSION"));
        int mapExplorationPercent = (explorationMission.getExplorationTileCount() * 100) / (model.getRealm().getMapHeight() * model.getRealm().getMapWidth());
        appendMissionExplanation("<br>To colonial governor,<br><br>");
        appendMissionExplanation("You have failed in <b>exploring " + mapExplorationPercent + " percent</b> of Mars. ");
        appendMissionExplanation("Because of your failure, we do not have any better information on changes that have happened ");
        appendMissionExplanation("on Martian surface after terraforming began.<br><br>");
        appendMissionExplanation("Please try not to disappoint us next time.<br><br>");
        Iterator<Reward> iterator = explorationMission.getRewardsIterator();
        while (iterator.hasNext()) {
            Reward reward = iterator.next();
            if (reward instanceof WealthReward) {
                appendMissionExplanation("Your mission reward of ");
                WealthReward wealthReward = (WealthReward) reward;
                appendMissionExplanation(new DecimalFormat().format(wealthReward.getAmount()) + " " + model.getRealm().getProperty("currency_unit") + " ");
                appendMissionExplanation("will be diverted to other needs.");
            }
        }
    }
}
