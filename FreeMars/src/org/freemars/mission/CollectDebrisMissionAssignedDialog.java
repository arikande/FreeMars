package org.freemars.mission;

import java.awt.Frame;
import java.util.Iterator;
import org.freemars.model.FreeMarsModel;
import org.freemars.ui.image.FreeMarsImageManager;
import org.freerealm.map.Coordinate;

/**
 *
 * @author Deniz ARIKAN
 */
public class CollectDebrisMissionAssignedDialog extends MissionDialog {

    public CollectDebrisMissionAssignedDialog(Frame owner, FreeMarsModel model, CollectDebrisMission collectDebrisMission) {
        super(owner);
        setTitle("Collect debris mission assigned");
        setImage(FreeMarsImageManager.getImage("EXPLORATION_MISSION"));
        appendMissionExplanation("<br>Welcome to Mars colonial governor,<br><br>");
        appendMissionExplanation("The landing module responsible for delivering your units to Mars surface had a rocket failure while ");
        appendMissionExplanation("returning to ISS/MC (International Space Station for Mars Colonization).<br><br>");
        appendMissionExplanation("While the loss of this small unmanned vessel is not very important, ");
        appendMissionExplanation("the debris from the spacecraft has scattered all around your LZ and must be reclaimed. <br><br>");
        appendMissionExplanation("You may collect some valuable resources from these spaceship parts located at the following coordinates.<br><br>");
        String coordinates = "";
        Iterator<Coordinate> iterator = collectDebrisMission.getDebrisCoordinatesIterator();
        while (iterator.hasNext()) {
            Coordinate coordinate = iterator.next();
            coordinates = coordinates + coordinate + "  ";
        }
        appendMissionExplanation("<b>" + coordinates + "</b><br><br>");
        appendMissionExplanation("Duration : ");
        if (collectDebrisMission.getDuration() == -1) {
            appendMissionExplanation("Indefinite<br><br>");
        } else {
            appendMissionExplanation(collectDebrisMission.getDuration() + " months<br><br>");
        }
    }
}
