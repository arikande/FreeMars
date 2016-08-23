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
public class SettlementImprovementCountMissionCompletedDialog extends MissionDialog {

    public SettlementImprovementCountMissionCompletedDialog(Frame owner, FreeMarsModel model, SettlementImprovementCountMission mission) {
        super(owner);
        setTitle("Settlement improvement count mission completed");
        setImage(FreeMarsImageManager.getImage("SETTLEMENT_IMPROVEMENT_COUNT_MISSION"));
        appendMissionExplanation("<br>To colonial governor,<br><br>");
        appendMissionExplanation("You have successfully built the following improvements in your colonies :<br><br>");
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
        appendMissionExplanation("These improvements will surely increase the growth and productivity of our colonies.<br><br>");
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
