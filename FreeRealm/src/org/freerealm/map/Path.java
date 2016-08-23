package org.freerealm.map;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Path {

    private final ArrayList<Coordinate> coordinates = new ArrayList<Coordinate>();

    public Path() {
    }

    @Override
    public String toString() {
        StringBuilder pathStringBuilder = new StringBuilder();
        for (int i = 0; i < getLength(); i++) {
            pathStringBuilder.append(getStep(i).toString());
            pathStringBuilder.append(" - ");
        }
        return pathStringBuilder.toString();
    }

    public int getLength() {
        return coordinates.size();
    }

    public Coordinate getStep(int index) {
        return (Coordinate) coordinates.get(index);
    }

    public int getX(int index) {
        return getStep(index).getAbscissa();
    }

    public int getY(int index) {
        return getStep(index).getOrdinate();
    }

    public void appendStep(int x, int y) {
        coordinates.add(new Coordinate(x, y));
    }

    public void prependStep(int x, int y) {
        coordinates.add(0, new Coordinate(x, y));
    }

    public boolean contains(Coordinate coordinate) {
        return coordinates.contains(coordinate);
    }

    public List<Coordinate> getCoordinates() {
        return coordinates;
    }

    public Iterator<Coordinate> getCoordinatesIterator() {
        return coordinates.iterator();
    }

}
