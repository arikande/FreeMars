package org.freemars.mission;

import java.awt.Frame;
import java.text.DecimalFormat;
import java.util.Iterator;
import org.freemars.model.FreeMarsModel;
import org.freemars.ui.image.FreeMarsImageManager;
import org.freerealm.player.mission.Reward;
import org.freerealm.player.mission.SettlementImprovementCountMission;

/**
 *
 * @author Deniz ARIKAN
 */
public class SettlementImprovementCountMissionAssignedDialog extends MissionDialog {

    public SettlementImprovementCountMissionAssignedDialog(Frame owner, FreeMarsModel model, SettlementImprovementCountMission mission) {
        super(owner);
        setTitle("Settlement count mission assigned");
        setImage(FreeMarsImageManager.getImage("SETTLEMENT_IMPROVEMENT_COUNT_MISSION"));
        appendMissionExplanation("<br>To colonial governor,<br><br>");
        appendMissionExplanation("Improvements such as granaries, solar panels and workshops will increase productiveness of a Martian colony. ");
        appendMissionExplanation("We must build these improvements in our colonies whenever possible to increase our chances of success.<br><br>");
        appendMissionExplanation("As the colonial governor, you have been assigned a mission to build the following ");
        appendMissionExplanation("improvements in your colonies.<br><br>");
        appendMissionExplanation("<b>");
        Iterator<Integer> targetImprovementTypesIterator = mission.getTargetImprovementTypesIterator();
        while (targetImprovementTypesIterator.hasNext()) {
            int targetImprovementType = targetImprovementTypesIterator.next();
            int count = mission.getTargetCountForImprovementType(targetImprovementType);
            String settlementImprovementTypeName = model.getRealm().getSettlementImprovementManager().getImprovement(targetImprovementType).getName();
            appendMissionExplanation("- " + count + " x " + settlementImprovementTypeName + "<br>");
        }
        appendMissionExplanation("</b>");
        appendMissionExplanation("<br>");
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
