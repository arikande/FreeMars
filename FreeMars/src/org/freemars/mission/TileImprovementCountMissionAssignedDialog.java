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
public class TileImprovementCountMissionAssignedDialog extends MissionDialog {

    public TileImprovementCountMissionAssignedDialog(Frame owner, FreeMarsModel model, TileImprovementCountMission mission) {
        super(owner);
        TileImprovementType tileImprovementType = model.getRealm().getTileImprovementTypeManager().getImprovement(mission.getTileImprovementId());
        setImage(FreeMarsImageManager.getImage("POPULATION_MISSION"));
        if (tileImprovementType.getName().equals("Road")) {
            setTitle("Build roads mission assigned");
            appendMissionExplanation("<br>To colonial governor,<br><br>");
            appendMissionExplanation("To improve trade on Mars, we need to build roads to increase transportation between colonies. ");
            appendMissionExplanation("Since trade goods have to be carried to colonies that have a starport, ");
            appendMissionExplanation("roads are essential for Earth exports. More roads will surely mean more profits.<br><br>");
            appendMissionExplanation("You have been assigned a mission to <b>build " + mission.getTileImprovementCount() + " ");
            appendMissionExplanation(tileImprovementType.getName().toLowerCase() + "s</b> on Martian surface. ");
            appendMissionExplanation("After completion of these roads, trade goods will be transported between colonies more easily.<br><br>");
        } else if (tileImprovementType.getName().equals("Irrigation")) {
            setTitle("Build irrigation mission assigned");
            appendMissionExplanation("<br>To colonial governor,<br><br>");
            appendMissionExplanation("To improve food production on Mars, we need to build irrigation to increase food output of farms. ");
            appendMissionExplanation("Even after some terraforming, this planet still has harsh conditions. Food is scarce and land is ");
            appendMissionExplanation("not fertile enough to support a large population. The only way to improve this is to build irrigation ");
            appendMissionExplanation("on food production areas.<br><br>");
            appendMissionExplanation("You have been assigned a mission to <b>build " + mission.getTileImprovementCount() + " ");
            appendMissionExplanation("irrigation</b> on Martian surface. ");
            appendMissionExplanation("After completion of irrigation, we will be able to support a larger population. ");
        }
        appendMissionExplanation("Improvements such as roads, irrigation and mines can be built by engineers.<br><br>");
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
