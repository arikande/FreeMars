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
public class TileImprovementCountMissionCompletedDialog extends MissionDialog {

    public TileImprovementCountMissionCompletedDialog(Frame owner, FreeMarsModel model, TileImprovementCountMission mission) {
        super(owner);
        setImage(FreeMarsImageManager.getImage("POPULATION_MISSION"));
        TileImprovementType tileImprovementType = model.getRealm().getTileImprovementTypeManager().getImprovement(mission.getTileImprovementId());
        appendMissionExplanation("<br>To colonial governor,<br><br>");
        if (tileImprovementType.getName().equals("Road")) {
            setTitle("Build roads mission completed");
            appendMissionExplanation("You have successfully <b>built " + mission.getTileImprovementCount() + " " + tileImprovementType.getName().toLowerCase() + "s</b> ");
            appendMissionExplanation("on Mars. By using these roads which will allow faster transportation, better profits will be made. ");
            appendMissionExplanation("You can use these roads to transport goods to colonies that have a starport so that they can be exported to Earth.<br><br>");
        } else if (tileImprovementType.getName().equals("Irrigation")) {
            setTitle("Build irrigation mission completed");
            appendMissionExplanation("You have successfully <b>built " + mission.getTileImprovementCount() + " irrigation</b> ");
            appendMissionExplanation("on Mars. Since irrigation will allow faster growth of crops, we will have better food output.<br><br>");
        }
        appendMissionExplanation("We recommend you to continue building improvements on Mars such as roads, irrigation and mines by using engineers. ");
        appendMissionExplanation("They will give you better food production, faster transportation and more resource output.<br><br>");
        Iterator<Reward> iterator = mission.getRewardsIterator();
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
