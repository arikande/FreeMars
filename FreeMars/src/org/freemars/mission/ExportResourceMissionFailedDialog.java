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
public class ExportResourceMissionFailedDialog extends MissionDialog {

    public ExportResourceMissionFailedDialog(Frame owner, FreeMarsModel model, ExportResourceMission exportResourceMission) {
        super(owner);
        setTitle("Export resource mission failed");
        setImage(FreeMarsImageManager.getImage("EXPORT_RESOURCE_MISSION"));
        Resource resource = model.getRealm().getResourceManager().getResource(exportResourceMission.getResourceId());
        appendMissionExplanation("<br>To colonial governor,<br><br>");
        appendMissionExplanation("You could not deliver the <b>" + resource.getName() + " shipment</b> we have ");
        appendMissionExplanation("requested. This will cause opposers to colonization raise their voices. Try not to disappoint us next time.<br><br>");
        appendMissionExplanation("To increase mining output, assign your workers to ");
        appendMissionExplanation("areas with bonus resources and build mine improvements ");
        appendMissionExplanation("on them.<br><br>");
        Iterator<Reward> iterator = exportResourceMission.getRewardsIterator();
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
