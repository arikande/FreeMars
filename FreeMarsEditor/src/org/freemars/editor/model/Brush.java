package org.freemars.editor.model;

import org.freerealm.resource.bonus.BonusResource;
import org.freerealm.tile.TileType;
import org.freerealm.vegetation.VegetationType;

/**
 *
 * @author Deniz ARIKAN
 */
public class Brush {

    private TileType tileType;
    private VegetationType vegetationType;
    private BonusResource bonusResource;

    public TileType getTileType() {
        return tileType;
    }

    public void setTileType(TileType tileType) {
        this.tileType = tileType;
    }

    public VegetationType getVegetationType() {
        return vegetationType;
    }

    public void setVegetationType(VegetationType vegetationType) {
        this.vegetationType = vegetationType;
    }

    public BonusResource getBonusResource() {
        return bonusResource;
    }

    public void setBonusResource(BonusResource bonusResource) {
        this.bonusResource = bonusResource;
    }

}
