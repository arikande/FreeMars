package org.freemars.mission;

import java.awt.Frame;
import java.text.DecimalFormat;
import java.util.Iterator;
import org.freemars.model.FreeMarsModel;
import org.freemars.ui.image.FreeMarsImageManager;
import org.freerealm.player.mission.ClearTileVegetationCountMission;
import org.freerealm.player.mission.Reward;

/**
 *
 * @author Deniz ARIKAN
 */
public class ClearTileVegetationMissionFailedDialog extends MissionDialog {

    public ClearTileVegetationMissionFailedDialog(Frame owner, FreeMarsModel model, ClearTileVegetationCountMission mission) {
        super(owner);
        setTitle("Clear vegetation mission completed");
        setImage(FreeMarsImageManager.getImage("POPULATION_MISSION"));
        appendMissionExplanation("<br>To colonial governor,<br><br>");
        appendMissionExplanation("Unfortunately your engineers could not <b>clear " + mission.getClearTileVegetationCount() + " ");
        appendMissionExplanation("tiles</b> of vegetation on Mars in given time. We need to clear vegetation ");
        appendMissionExplanation("from tiles to build irrigation on them. ");
        appendMissionExplanation("As you very well know, irrigation is essential for food production.<br><br>");
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
