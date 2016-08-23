package org.freerealm.tile.improvement;

import org.freerealm.tile.Tile;
import org.freerealm.tile.TileBuildablePrerequisite;

/**
 *
 * @author Deniz ARIKAN
 */
public class NoVegetationPrerequisite implements TileBuildablePrerequisite {

    private Tile tile;

    public boolean isSatisfied() {
        return tile.getVegetation() == null;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }
}
