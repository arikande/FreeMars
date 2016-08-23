package org.freerealm.property;

import java.util.ArrayList;
import java.util.Iterator;
import org.freerealm.tile.improvement.TileImprovementType;

/**
 *
 * @author Deniz ARIKAN
 */
public class BuildTileImprovementProperty implements Property {

    public static final String NAME = "build_tile_improvement_property";
    private int productionPoints;
    private final ArrayList<TileImprovementType> tileImprovements;

    public BuildTileImprovementProperty() {
        tileImprovements = new ArrayList<TileImprovementType>();
    }

    public String getName() {
        return NAME;
    }

    public Iterator<TileImprovementType> getTileImprovementsIterator() {
        return tileImprovements.iterator();
    }

    public boolean addTileImprovement(TileImprovementType tileImprovementType) {
        return tileImprovements.add(tileImprovementType);
    }

    public boolean containsTileImprovement(TileImprovementType tileImprovementType) {
        return tileImprovements.contains(tileImprovementType);
    }

    public int getProductionPoints() {
        return productionPoints;
    }

    public void setProductionPoints(int productionPoints) {
        this.productionPoints = productionPoints;
    }
}
