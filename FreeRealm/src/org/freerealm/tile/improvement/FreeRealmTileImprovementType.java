package org.freerealm.tile.improvement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.freerealm.tile.FreeRealmTileModifier;
import org.freerealm.tile.TileBuildablePrerequisite;
import org.freerealm.tile.TileType;

/**
 *
 * @author Deniz ARIKAN
 */
public class FreeRealmTileImprovementType extends FreeRealmTileModifier implements TileImprovementType {

    private int productionCost;
    private int upkeepCost;
    private final List<TileBuildablePrerequisite> prerequisites;

    public FreeRealmTileImprovementType() {
        prerequisites = new ArrayList<TileBuildablePrerequisite>();
    }

    public boolean canBeBuiltOnTileType(TileType tileType) {
        return hasTileType(tileType);
    }

    public void addBuildableTileType(TileType tileType) {
        addTileType(tileType);
    }

    public void removeBuildableTileType(TileType tileType) {
        removeTileType(tileType);
    }

    public int getBuildableTileTypeCount() {
        return getTileTypeCount();
    }

    public Iterator<TileType> getBuildableTileTypesIterator() {
        return getTileTypesIterator();
    }

    public int getProductionCost() {
        return productionCost;
    }

    public void setProductionCost(int productionCost) {
        this.productionCost = productionCost;
    }

    public int getUpkeepCost() {
        return upkeepCost;
    }

    public void setUpkeepCost(int upkeepCost) {
        this.upkeepCost = upkeepCost;
    }

    public Iterator<TileBuildablePrerequisite> getPrerequisitesIterator() {
        return prerequisites.iterator();
    }

    public void addPrerequisite(TileBuildablePrerequisite prerequisite) {
        prerequisites.add(prerequisite);
    }

    public int getPrerequisiteCount() {
        return prerequisites.size();
    }

    public char getSymbol() {
        return getName().charAt(0);
    }
}
