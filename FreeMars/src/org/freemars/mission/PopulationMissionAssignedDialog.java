package org.freemars.mission;

import java.awt.Frame;
import java.text.DecimalFormat;
import java.util.Iterator;
import org.freemars.model.FreeMarsModel;
import org.freemars.ui.image.FreeMarsImageManager;
import org.freerealm.player.mission.PopulationMission;
import org.freerealm.player.mission.Reward;

/**
 *
 * @author Deniz ARIKAN
 */
public class PopulationMissionAssignedDialog extends MissionDialog {

    public PopulationMissionAssignedDialog(Frame owner, FreeMarsModel model, PopulationMission mission) {
        super(owner);
        setTitle("Population mission assigned");
        setImage(FreeMarsImageManager.getImage("POPULATION_MISSION"));
        appendMissionExplanation("<br>To colonial governor,<br><br>");
        appendMissionExplanation("We need to increase the colonist population on Mars. As the number of colonists ");
        appendMissionExplanation("increases, the Red planet will surely attract more population from the ");
        appendMissionExplanation("overcrowded Earth.<br><br>");
        appendMissionExplanation("As the colonial governor, you have been assigned a mission ");
        appendMissionExplanation("to reach a population of at least <b>" + new DecimalFormat().format(mission.getPopulation()));
        appendMissionExplanation(" colonists</b> on Mars.<br><br>");
        appendMissionExplanation("To reach the target population of " + new DecimalFormat().format(mission.getPopulation()) + " colonists ");
        appendMissionExplanation("you can support normal population growth by supplying enough food, or you can finance ");
        appendMissionExplanation(" colonists from Earth  to settle on Mars.<br><br>");
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
