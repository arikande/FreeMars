package org.freemars.mission;

import java.util.ArrayList;
import java.util.Iterator;
import org.freerealm.map.Coordinate;
import org.freerealm.player.mission.AbstractMission;
import org.freerealm.player.mission.Mission;
import org.freerealm.tile.Tile;

/**
 *
 * @author Deniz ARIKAN
 */
public class CollectDebrisMission extends AbstractMission {

    private static final String NAME = "collectDebrisMission";
    private final ArrayList<Coordinate> debrisCoordinates;

    public CollectDebrisMission() {
        debrisCoordinates = new ArrayList<Coordinate>();
    }

    @Override
    public Mission clone() {
        CollectDebrisMission clone = new CollectDebrisMission();
        copyTo(clone);
        clone.addDebrisCoordinates(debrisCoordinates);
        return clone;
    }

    public String getMissionName() {
        return NAME;
    }

    public void checkStatus() {
        if (getCollectedDebrisCount() == debrisCoordinates.size()) {
            setStatus(Mission.STATUS_COMPLETED);
        } else {
            checkIfExpired();
        }
    }

    public int getProgressPercent() {
        if (getStatus() == Mission.STATUS_COMPLETED) {
            return 100;
        } else if (getStatus() == Mission.STATUS_FAILED) {
            return 0;
        } else {
            if (debrisCoordinates.size() > 0) {
                return getCollectedDebrisCount() * 100 / debrisCoordinates.size();
            } else {
                return 100;
            }
        }
    }

    public void addDebrisCoordinate(Coordinate coordinate) {
        debrisCoordinates.add(coordinate);
    }

    public void addDebrisCoordinates(ArrayList<Coordinate> coordinates) {
        debrisCoordinates.addAll(coordinates);
    }

    public Iterator<Coordinate> getDebrisCoordinatesIterator() {
        return debrisCoordinates.iterator();
    }

    private int getCollectedDebrisCount() {
        int collectedDebrisCount = 0;
        for (Coordinate coordinate : debrisCoordinates) {
            Tile tile = getRealm().getTile(coordinate);
            if (tile.getCollectable() == null) {
                collectedDebrisCount = collectedDebrisCount + 1;
            }
        }
        return collectedDebrisCount;
    }
}
