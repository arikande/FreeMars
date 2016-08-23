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
public class ClearTileVegetationMissionCompletedDialog extends MissionDialog {

    public ClearTileVegetationMissionCompletedDialog(Frame owner, FreeMarsModel model, ClearTileVegetationCountMission mission) {
        super(owner);
        setTitle("Clear vegetation mission completed");
        setImage(FreeMarsImageManager.getImage("POPULATION_MISSION"));
        appendMissionExplanation("<br>To colonial governor,<br><br>");
        appendMissionExplanation("Your engineers have <b>cleared " + mission.getClearTileVegetationCount() + " ");
        appendMissionExplanation("tiles</b> of vegetation on Mars. It is now easier to build irrigation ");
        appendMissionExplanation("on these tiles and increase the food output of your colonies.<br><br>");
        Iterator<Reward> iterator = mission.getRewardsIterator();
        while (iterator.hasNext()) {
            Reward reward = iterator.next();
            if (reward instanceof WealthReward) {
                appendMissionExplanation("Your mission reward of ");
                WealthReward wealthReward = (WealthReward) reward;
                appendMissionExplanation(new DecimalFormat().format(wealthReward.getAmount()) + " " + model.getRealm().getProperty("currency_unit") + " ");
                appendMissionExplanation("has been added to your treasury. ");
            }
        }
        appendMissionExplanation("Do not forget to export the lumber you have gained for extra income.");
    }
}
