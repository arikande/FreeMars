package org.freerealm;

import org.freerealm.map.Direction;

/**
 *
 * @author Deniz ARIKAN
 */
public class RealmConstants {

    public static final Direction NORTHWEST = new Direction(7, "NW");
    public static final Direction NORTH = new Direction(8, "N");
    public static final Direction NORTHEAST = new Direction(9, "NE");
    public static final Direction WEST = new Direction(4, "W");
    public static final Direction CENTER = new Direction(5, "C");
    public static final Direction EAST = new Direction(6, "E");
    public static final Direction SOUTHWEST = new Direction(1, "SW");
    public static final Direction SOUTH = new Direction(2, "S");
    public static final Direction SOUTHEAST = new Direction(3, "SE");
    public static final Direction[] directions = {NORTHWEST, NORTH, NORTHEAST, WEST, CENTER, EAST, SOUTHWEST, SOUTH, SOUTHEAST};
}
