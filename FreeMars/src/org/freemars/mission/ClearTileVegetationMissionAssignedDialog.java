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
public class ClearTileVegetationMissionAssignedDialog extends MissionDialog {

    public ClearTileVegetationMissionAssignedDialog(Frame owner, FreeMarsModel model, ClearTileVegetationCountMission clearTileVegetationCountMission) {
        super(owner);
        setTitle("Clear vegetation mission assigned");
        setImage(FreeMarsImageManager.getImage("EXPLORATION_MISSION"));
        String currencyUnit = model.getRealm().getProperty("currency_unit");
        appendMissionExplanation("<br>To colonial governor,<br><br>");
        appendMissionExplanation("As you know, genetically engineered plant species were sent to Mars when terraforming began. ");
        appendMissionExplanation("These plants have adapted and survived the harsh conditions of the Red planet; more than we ");
        appendMissionExplanation("had expected actually.<br><br>");
        appendMissionExplanation("Clearing vegetation in areas around your colonies will make building irrigation easier and it will ");
        appendMissionExplanation("be good for transportation. Also, selling the lumber that is produced will provide a small but early ");
        appendMissionExplanation("income for your colony.<br><br>");
        appendMissionExplanation("To clear an area, bring one of your engineers over it and issue a \"Clear vegetation (V)\" order.");
        appendMissionExplanation("<br><br>");
        appendMissionExplanation("Your mission is to clear " + clearTileVegetationCountMission.getClearTileVegetationCount() + " areas ");
        appendMissionExplanation("of vegetation.<br><br>");
        appendMissionExplanation("Duration : ");
        if (clearTileVegetationCountMission.getDuration() == -1) {
            appendMissionExplanation("Indefinite<br><br>");
        } else {
            appendMissionExplanation(clearTileVegetationCountMission.getDuration() + " months<br><br>");
        }
        appendMissionExplanation("Reward :<br>");
        Iterator<Reward> iterator = clearTileVegetationCountMission.getRewardsIterator();
        while (iterator.hasNext()) {
            Reward reward = iterator.next();
            if (reward instanceof WealthReward) {
                WealthReward wealthReward = (WealthReward) reward;
                appendMissionExplanation(" * " + new DecimalFormat().format(wealthReward.getAmount()) + " " + currencyUnit + "<br><br>");
            }
        }
    }
}
