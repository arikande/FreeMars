package org.freerealm.map;

import org.freerealm.settlement.Settlement;
import org.freerealm.tile.Tile;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public interface Map {

    public String getName();

    public void setName(String name);

    public String getDescription();

    public void setDescription(String description);

    public int getHeight();

    public int getWidth();

    public int getSuggestedPlayers();

    public void setSuggestedPlayers(int suggestedPlayers);

    public Tile getTile(Coordinate coordinate);

    public Tile[][] getMapItems();

    public void setMapItems(Tile[][] mapItems);

    public void addSettlement(Settlement settlement);

    public void removeSettlement(Settlement settlement);

    public void addUnit(Unit unit);

    public void removeUnit(Unit unit);
}
