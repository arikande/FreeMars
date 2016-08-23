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
public class SettlementCountMissionAssignedDialog extends MissionDialog {

    public SettlementCountMissionAssignedDialog(Frame owner, FreeMarsModel model, SettlementCountMission mission) {
        super(owner);
        setTitle("Settlement count mission assigned");
        setImage(FreeMarsImageManager.getImage("POPULATION_MISSION"));
        appendMissionExplanation("<br>To colonial governor,<br><br>");
        appendMissionExplanation("We need to increase the number of colonies on Mars. As the number of available ");
        appendMissionExplanation("settlements increases, the Red planet will surely attract more population from the ");
        appendMissionExplanation("overcrowded Earth.<br><br>");
        appendMissionExplanation("As the colonial governor, you have been assigned a mission ");
        appendMissionExplanation("<b>to build at least " + mission.getSettlementCount());
        appendMissionExplanation(" colonies</b> on Mars.<br><br>");
        appendMissionExplanation("To reach the target number of " + mission.getSettlementCount() + " colonies ");
        appendMissionExplanation("you need to build or buy colonizers.<br><br>");
        appendMissionExplanation("Duration : ");
        if (mission.getDuration() == -1) {
            appendMissionExplanation("Indefinite<br><br>");
        } else {
            appendMissionExplanation(mission.getDuration() + " months<br><br>");
        }
        appendMissionExplanation("Reward :<br>");
        Iterator<Reward> iterator = mission.getRewardsIterator();
        while (iterator.hasNext()) {
            Reward reward = iterator.next();
            if (reward instanceof WealthReward) {
                WealthReward wealthReward = (WealthReward) reward;
                appendMissionExplanation(" * " + new DecimalFormat().format(wealthReward.getAmount()) + " " + model.getRealm().getProperty("currency_unit") + "<br><br>");
            }
        }
    }
}
