package org.freemars.mission;

import java.awt.Frame;
import java.text.DecimalFormat;
import java.util.Iterator;
import org.freemars.model.FreeMarsModel;
import org.freemars.ui.image.FreeMarsImageManager;
import org.freerealm.player.mission.Reward;
import org.freerealm.player.mission.SettlementCountMission;

/**
 *
 * @author Deniz ARIKAN
 */
public class SettlementCountMissionCompletedDialog extends MissionDialog {

    public SettlementCountMissionCompletedDialog(Frame owner, FreeMarsModel model, SettlementCountMission mission) {
        super(owner);
        setTitle("Settlement count mission completed");
        setImage(FreeMarsImageManager.getImage("POPULATION_MISSION"));
        appendMissionExplanation("<br>To colonial governor,<br><br>");
        appendMissionExplanation("You have <b>built " + mission.getSettlementCount() + " ");
        appendMissionExplanation("colonies</b> on Mars. News of this will surely attract more colonists from the ");
        appendMissionExplanation("overpopulated Earth.<br><br>");
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
