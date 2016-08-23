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
public class ExportResourceMissionAssignedDialog extends MissionDialog {

    public ExportResourceMissionAssignedDialog(Frame owner, FreeMarsModel model, ExportResourceMission mission) {
        super(owner);
        setTitle("Export resource mission assigned");
        setImage(FreeMarsImageManager.getImage("EXPORT_RESOURCE_MISSION"));
        String currencyUnit = model.getRealm().getProperty("currency_unit");
        Resource resource = model.getRealm().getResourceManager().getResource(mission.getResourceId());
        appendMissionExplanation("<br>To colonial governor,<br><br>");
        appendMissionExplanation("Your Earth government has requested a shipment of <b>");
        appendMissionExplanation(new DecimalFormat().format(mission.getTargetQuantity()) + " tons of ");
        appendMissionExplanation(resource.getName() + "</b> due to ");
        if (resource.getName().equals("Iron")) {
            appendMissionExplanation("increased demands in steel industry. ");
        } else if (resource.getName().equals("Silica")) {
            appendMissionExplanation("increased demands in glass industry. ");
        }
        appendMissionExplanation("We need you to complete ");
        appendMissionExplanation("this export resource mission as soon as possible.<br><br>");
        appendMissionExplanation("Please do not forget that Mars is ONLY a mining colony and ");
        appendMissionExplanation("your colonists are there to serve the raw material needs ");
        appendMissionExplanation("of your government. Opposers to Martian colonization ");
        appendMissionExplanation("have began to raise their voices, saying that it is a ");
        appendMissionExplanation("waste of funds.<br><br>");
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
                appendMissionExplanation(" * " + new DecimalFormat().format(wealthReward.getAmount()) + " " + currencyUnit + " + normal income from exports");
            }
        }
    }
}
