package org.freemars.unit.automater;

import org.freerealm.map.Coordinate;

/**
 *
 * @author Deniz ARIKAN
 */
public class EngineerAutomaterPathCacheKey implements Comparable<EngineerAutomaterPathCacheKey> {

    private final Coordinate coordinateA;
    private final Coordinate coordinateB;

    protected EngineerAutomaterPathCacheKey(Coordinate coordinateA, Coordinate coordinateB) {
        this.coordinateA = coordinateA;
        this.coordinateB = coordinateB;
    }

    public int compareTo(EngineerAutomaterPathCacheKey otherKey) {
        if (getCoordinateA().compareTo(otherKey.getCoordinateA()) == 0 && getCoordinateB().compareTo(otherKey.getCoordinateB()) == 0) {
            return 0;
        }
        if (getCoordinateA().compareTo(otherKey.getCoordinateB()) == 0 && getCoordinateB().compareTo(otherKey.getCoordinateA()) == 0) {
            return 0;
        }
        if (getCoordinateA().compareTo(otherKey.getCoordinateA()) != 0) {
            return getCoordinateA().compareTo(otherKey.getCoordinateA());
        }
        if (getCoordinateB().compareTo(otherKey.getCoordinateB()) != 0) {
            return getCoordinateB().compareTo(otherKey.getCoordinateB());
        }
        return 1;
    }

    private Coordinate getCoordinateA() {
        return coordinateA;
    }

    private Coordinate getCoordinateB() {
        return coordinateB;
    }
}
