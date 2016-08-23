package org.freemars.mission;

import java.awt.Frame;
import java.text.DecimalFormat;
import java.util.Iterator;
import org.freemars.model.FreeMarsModel;
import org.freemars.ui.image.FreeMarsImageManager;
import org.freerealm.player.mission.Reward;
import org.freerealm.player.mission.TileImprovementCountMission;
import org.freerealm.tile.improvement.TileImprovementType;

/**
 *
 * @author Deniz ARIKAN
 */
public class TileImprovementCountMissionFailedDialog extends MissionDialog {

    public TileImprovementCountMissionFailedDialog(Frame owner, FreeMarsModel model, TileImprovementCountMission mission) {
        super(owner);
        setImage(FreeMarsImageManager.getImage("POPULATION_MISSION"));
        TileImprovementType tileImprovementType = model.getRealm().getTileImprovementTypeManager().getImprovement(mission.getTileImprovementId());
        appendMissionExplanation("<br>To colonial governor,<br><br>");
        if (tileImprovementType.getName().equals("Road")) {
            setTitle("Build roads mission failed");
            appendMissionExplanation("You could not <b>build " + mission.getTileImprovementCount() + " " + tileImprovementType.getName().toLowerCase() + "s</b> ");
            appendMissionExplanation("on Mars in given time. These roads would allow faster transportation and better profits. ");
        } else if (tileImprovementType.getName().equals("Irrigation")) {
            setTitle("Build irrigation mission failed");
            appendMissionExplanation("You could not <b>build " + mission.getTileImprovementCount() + " irrigation</b> ");
            appendMissionExplanation("on Mars in given time. Irrigation would have allowed better food production. ");
        }
        appendMissionExplanation("Try not to disappoint us next time.<br><br>");
        appendMissionExplanation("We strongly recommend you to build improvements on Mars such as roads, irrigation and mines by using engineers. ");
        appendMissionExplanation("They will give you better food production, faster transportation and more resource output.<br><br>");
        Iterator<Reward> iterator = mission.getRewardsIterator();
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
