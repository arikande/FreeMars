package org.freemars.mission;

import java.awt.Frame;
import java.text.DecimalFormat;
import java.util.Iterator;
import org.freemars.model.FreeMarsModel;
import org.freemars.ui.image.FreeMarsImageManager;
import org.freerealm.player.mission.Reward;
import org.freerealm.resource.Resource;

/**
 *
 * @author Deniz ARIKAN
 */
public class ExportResourceMissionCompletedDialog extends MissionDialog {

    public ExportResourceMissionCompletedDialog(Frame owner, FreeMarsModel model, ExportResourceMission exportResourceMission) {
        super(owner);
        setTitle("Export resource mission completed");
        setImage(FreeMarsImageManager.getImage("EXPORT_RESOURCE_MISSION"));
        Resource resource = model.getRealm().getResourceManager().getResource(exportResourceMission.getResourceId());
        appendMissionExplanation("<br>To colonial governor,<br><br>");
        appendMissionExplanation("We thank you for the <b>" + resource.getName() + " shipment</b>, you have ");
        appendMissionExplanation("proven that Martian colony is not a total waste of ");
        appendMissionExplanation("money. This will silence the opposers to colonization ");
        appendMissionExplanation("for a short time.<br><br> ");
        appendMissionExplanation("To increase mining output, try to assign your workers to ");
        appendMissionExplanation("areas with bonus resources and build mine improvements ");
        appendMissionExplanation("on them.<br><br>");
        Iterator<Reward> iterator = exportResourceMission.getRewardsIterator();
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
