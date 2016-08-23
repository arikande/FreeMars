package org.freemars.mission;

import java.awt.Frame;
import java.text.DecimalFormat;
import java.util.Iterator;
import org.freemars.model.FreeMarsModel;
import org.freemars.ui.image.FreeMarsImageManager;
import org.freerealm.player.mission.ExplorationMission;
import org.freerealm.player.mission.Reward;

/**
 *
 * @author Deniz ARIKAN
 */
public class ExplorationMissionAssignedDialog extends MissionDialog {

    public ExplorationMissionAssignedDialog(Frame owner, FreeMarsModel model, ExplorationMission explorationMission) {
        super(owner);
        setTitle("Exploration mission assigned");
        setImage(FreeMarsImageManager.getImage("EXPLORATION_MISSION"));
        String currencyUnit = model.getRealm().getProperty("currency_unit");
        int mapExplorationPercent = (explorationMission.getExplorationTileCount() * 100) / (model.getRealm().getMapHeight() * model.getRealm().getMapWidth());
        appendMissionExplanation("<br>To colonial governor,<br><br>");
        appendMissionExplanation("Swamps, ice and vegetation that have been formed after the terraforming has quite changed ");
        appendMissionExplanation("the geography of Red planet and heavy mist on the surface prevents our orbital ");
        appendMissionExplanation("satellites from mapping the new Martian planetary surface in detail.<br><br>");
        appendMissionExplanation("Currently the only way to map Mars is by ground vehicles, especially scouts. As the colonial governor, ");
        appendMissionExplanation("you have been assigned a mission to <b>explore at least " + mapExplorationPercent + " ");
        appendMissionExplanation("percent</b> of Mars.<br><br>");
        appendMissionExplanation("Duration : ");
        if (explorationMission.getDuration() == -1) {
            appendMissionExplanation("Indefinite<br><br>");
        } else {
            appendMissionExplanation(explorationMission.getDuration() + " months<br><br>");
        }
        appendMissionExplanation("Reward :<br>");
        Iterator<Reward> iterator = explorationMission.getRewardsIterator();
        while (iterator.hasNext()) {
            Reward reward = iterator.next();
            if (reward instanceof WealthReward) {
                WealthReward wealthReward = (WealthReward) reward;
                appendMissionExplanation(" * " + new DecimalFormat().format(wealthReward.getAmount()) + " " + currencyUnit + "<br><br>");
            }
        }
        appendMissionExplanation("Also, next year's maintainance cost for the scout which is 120 " + currencyUnit + " ");
        appendMissionExplanation("(=10 x 12) will be added to your treasury.");
    }
}
