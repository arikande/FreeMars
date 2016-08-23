package org.freerealm.tile.improvement;

import java.util.Iterator;
import org.freerealm.property.BuildTileImprovementProperty;
import org.freerealm.tile.Tile;
import org.freerealm.tile.TileBuildablePrerequisite;

/**
 *
 * @author Deniz ARIKAN
 */
public class BuildTileImprovementChecker {

    public boolean canBuildTileImprovement(BuildTileImprovementProperty buildTileImprovement, TileImprovementType tileImprovement, Tile tile) {
        if (tile.hasImprovement(tileImprovement)) {
            return false;
        }
        if (!buildTileImprovement.containsTileImprovement(tileImprovement)) {
            return false;
        }
        if (!tileImprovement.canBeBuiltOnTileType(tile.getType())) {
            return false;
        }
        Iterator<TileBuildablePrerequisite> tileBuildablePrerequisiteIterator = tileImprovement.getPrerequisitesIterator();
        while (tileBuildablePrerequisiteIterator.hasNext()) {
            TileBuildablePrerequisite tileBuildablePrerequisite = tileBuildablePrerequisiteIterator.next();
            tileBuildablePrerequisite.setTile(tile);
            if (!tileBuildablePrerequisite.isSatisfied()) {
                return false;
            }
        }
        return true;
    }
}
