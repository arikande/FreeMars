package org.freemars.earth;

/**
 *
 * @author Deniz ARIKAN
 */
public class Location {

    private static final int MARS_ID = 0;
    private static final int MARS_ORBIT_ID = 1;
    private static final int TRAVELING_TO_EARTH_ID = 2;
    private static final int EARTH_ID = 3;
    private static final int TRAVELING_TO_MARS_ID = 4;
    public static final Location MARS = new Location(MARS_ID);
    public static final Location MARS_ORBIT = new Location(MARS_ORBIT_ID);
    public static final Location TRAVELING_TO_EARTH = new Location(TRAVELING_TO_EARTH_ID);
    public static final Location EARTH = new Location(EARTH_ID);
    public static final Location TRAVELING_TO_MARS = new Location(TRAVELING_TO_MARS_ID);
    int id;

    private Location(int locationId) {
        this.id = locationId;
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Location) {
            if (object != null) {
                return getId() == ((Location) object).getId();
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public int getId() {
        return id;
    }

    public static Location getLocation(int id) {
        switch (id) {
            case 0:
                return MARS;
            case 1:
                return MARS_ORBIT;
            case 2:
                return TRAVELING_TO_EARTH;
            case 3:
                return EARTH;
            case 4:
                return TRAVELING_TO_MARS;
            default:
                return MARS;
        }
    }
}
