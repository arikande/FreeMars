package org.freerealm.map;

/**
 * 
 * @author Deniz ARIKAN
 */
public interface MapItem extends Comparable<MapItem> {

    public int getId();

    public void setId(int id);

    public Coordinate getCoordinate();

    public void setCoordinate(Coordinate coordinate);
}
