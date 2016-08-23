package org.freemars.unit.automater;

import java.util.List;
import org.apache.log4j.Logger;
import org.freemars.controller.FreeMarsController;
import org.freerealm.map.Coordinate;
import org.freerealm.unit.AbstractUnitAutomater;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class ScoutAutomater extends AbstractUnitAutomater {

    private static final String NAME = "freeMarsScoutAutomater";
    private FreeMarsController freeMarsController;

    public String getName() {
        return NAME;
    }

    public void automate() {
        if (isScoutingNeeded(getUnit())) {
            boolean explorationResult = ExplorationHelper.manageUnitInExplorationMode(freeMarsController, getUnit());
            if (!explorationResult) {
                String log = "Scout automater for \"" + getUnit().getName() + "\" could not find any tiles to explore in turn " + freeMarsController.getFreeMarsModel().getNumberOfTurns() + ". Removing automater for \"" + getUnit().getName() + "\".";
                Logger.getLogger(ScoutAutomater.class).info(log);
                getUnit().setAutomater(null);
            }
        }
    }

    public void setFreeMarsController(FreeMarsController freeMarsController) {
        this.freeMarsController = freeMarsController;

    }

    private boolean isScoutingNeeded(Unit scout) {
        for (int i = 1; i < 10; i++) {
            List<Coordinate> circleCoordinates = freeMarsController.getFreeMarsModel().getRealm().getCircleCoordinates(scout.getCoordinate(), i);
            for (Coordinate coordinate : circleCoordinates) {
                if (coordinate != null && !getUnit().getPlayer().isCoordinateExplored(coordinate)) {
                    return true;
                }
            }
        }
        return false;
    }

}
