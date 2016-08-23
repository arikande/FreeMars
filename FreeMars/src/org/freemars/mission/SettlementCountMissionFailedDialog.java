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
public class SettlementCountMissionFailedDialog extends MissionDialog {

    public SettlementCountMissionFailedDialog(Frame owner, FreeMarsModel model, SettlementCountMission mission) {
        super(owner);
        setTitle("Settlement count mission failed");
        setImage(FreeMarsImageManager.getImage("POPULATION_MISSION"));
        appendMissionExplanation("<br>To colonial governor,<br><br>");
        appendMissionExplanation("You could not <b>build " + mission.getSettlementCount() + " ");
        appendMissionExplanation("colonies</b> on Mars in given time. News of this will dishearten future colonists from the ");
        appendMissionExplanation("overpopulated Earth. Produce colonizers to build more colonies.<br><br>");
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
