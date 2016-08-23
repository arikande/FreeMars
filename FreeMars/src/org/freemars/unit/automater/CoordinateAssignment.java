package org.freemars.unit.automater;

import org.freerealm.map.Coordinate;

/**
 *
 * @author Deniz ARIKAN
 */
public class CoordinateAssignment implements Comparable<CoordinateAssignment> {

    private Coordinate coordinate;
    private int points;

    @Override
    public String toString() {
        return coordinate.toString() + " - " + points;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int compareTo(CoordinateAssignment coordinateAssignment) {
        if (getPoints() > coordinateAssignment.getPoints()) {
            return 1;
        } else if (getPoints() == coordinateAssignment.getPoints()) {
            return getCoordinate().compareTo(coordinateAssignment.getCoordinate());
        }
        return -1;
    }
}
