package org.freerealm.map;

import org.freerealm.Realm;

/**
 *
 * @author Deniz ARIKAN
 */
public class FreeRealmMapItem implements MapItem {

    private int id;
    private Coordinate coordinate;

    protected FreeRealmMapItem(Realm realm, Coordinate coordinate) {
        id = realm.getMapItemCount();
        this.coordinate = coordinate;
    }

    public int compareTo(MapItem mapItem) {
        return (new Integer(getId())).compareTo(mapItem.getId());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }
}
