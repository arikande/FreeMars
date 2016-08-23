package org.freemars.colony;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.freerealm.Realm;
import org.freerealm.map.Coordinate;
import org.freerealm.settlement.FreeRealmSettlement;

/**
 *
 * @author Deniz ARIKAN
 */
public class FreeMarsColony extends FreeRealmSettlement {

    private boolean autoUsingFertilizer;
    private final List<Coordinate> fertilizedCoordinates;

    public FreeMarsColony(Realm realm) {
        super(realm);
        fertilizedCoordinates = new ArrayList<Coordinate>();
    }

    public boolean isAutoUsingFertilizer() {
        return autoUsingFertilizer;
    }

    public void setAutoUsingFertilizer(boolean autoUsingFertilizer) {
        this.autoUsingFertilizer = autoUsingFertilizer;
    }

    public void addFertilizedCoordinate(Coordinate coordinate) {
        if (!fertilizedCoordinates.contains(coordinate)) {
            fertilizedCoordinates.add(coordinate);
        }
    }

    public void removeFertilizedCoordinate(Coordinate coordinate) {
        fertilizedCoordinates.remove(coordinate);
    }

    public boolean isCoordinateFertilized(Coordinate coordinate) {
        return fertilizedCoordinates.contains(coordinate);
    }

    public Iterator<Coordinate> getFertilizedCoordinatesIterator() {
        return fertilizedCoordinates.iterator();
    }

    public int getFertilizedCoordinatesSize() {
        return fertilizedCoordinates.size();
    }
}
