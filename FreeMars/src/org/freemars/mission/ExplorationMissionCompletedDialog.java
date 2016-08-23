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
public class ExplorationMissionCompletedDialog extends MissionDialog {

    public ExplorationMissionCompletedDialog(Frame owner, FreeMarsModel model, ExplorationMission explorationMission) {
        super(owner);
        setTitle("Exploration mission completed");
        setImage(FreeMarsImageManager.getImage("EXPLORATION_MISSION"));
        int mapExplorationPercent = (explorationMission.getExplorationTileCount() * 100) / (model.getRealm().getMapHeight() * model.getRealm().getMapWidth());
        appendMissionExplanation("<br>To colonial governor,<br><br>");
        appendMissionExplanation("You have completed <b>exploring " + mapExplorationPercent + " percent</b> of Mars. ");
        appendMissionExplanation("Thanks to your efforts, we now have better information on changes that have happened ");
        appendMissionExplanation("on Martian surface after terraforming began.<br><br>");
        appendMissionExplanation("Melting of polar ice has changed Martian geography much more than we had anticipated. ");
        appendMissionExplanation("Swamps now cover huge areas on the Red planet.<br><br>");
        appendMissionExplanation("Another surprise was the expansion of flora and fauna on this new world, which still ");
        appendMissionExplanation("has harsh conditions even after terraforming.<br><br>");
        Iterator<Reward> iterator = explorationMission.getRewardsIterator();
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
