package org.freerealm.map;

import org.freerealm.settlement.Settlement;
import org.freerealm.tile.Tile;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class FreeRealmMap implements Map {

    private String name;
    private String description;
    private int suggestedPlayers = -1;
    private int width;
    private int height;
    private Tile[][] mapItems = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addUnit(Unit unit) {
        Coordinate coordinate = unit.getCoordinate();
        getMapItems()[coordinate.getAbscissa()][coordinate.getOrdinate()].putUnit(unit);
    }

    public void removeUnit(Unit unit) {
        Coordinate coordinate = unit.getCoordinate();
        if (coordinate != null) {
            getMapItems()[coordinate.getAbscissa()][coordinate.getOrdinate()].removeUnit(unit);
        }
    }

    public void addSettlement(Settlement settlement) {
        Coordinate coordinate = settlement.getCoordinate();
        getMapItems()[coordinate.getAbscissa()][coordinate.getOrdinate()].setSettlement(settlement);
    }

    public void removeSettlement(Settlement settlement) {
        Coordinate coordinate = settlement.getCoordinate();
        getMapItems()[coordinate.getAbscissa()][coordinate.getOrdinate()].setSettlement(null);
    }

    public Tile getTile(Coordinate coordinate) {
        if ((coordinate.getOrdinate() < 0) || (coordinate.getOrdinate() >= getHeight())) {
            return null;
        }
        return getMapItems()[coordinate.getAbscissa()][coordinate.getOrdinate()];
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Tile[][] getMapItems() {
        return mapItems;
    }

    public void setMapItems(Tile[][] mapItems) {
        setWidth(mapItems.length);
        setHeight(mapItems[0].length);
        this.mapItems = mapItems;
    }

    private void setWidth(int width) {
        this.width = width;
    }

    private void setHeight(int height) {
        this.height = height;
    }

    public int getSuggestedPlayers() {
        return suggestedPlayers;
    }

    public void setSuggestedPlayers(int suggestedPlayers) {
        this.suggestedPlayers = suggestedPlayers;
    }
}
