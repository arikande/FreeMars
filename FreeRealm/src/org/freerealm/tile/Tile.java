package org.freerealm.tile;

import java.util.Iterator;
import java.util.Map;
import org.freerealm.Customizable;
import org.freerealm.player.Player;
import org.freerealm.resource.Resource;
import org.freerealm.resource.bonus.BonusResource;
import org.freerealm.settlement.Settlement;
import org.freerealm.tile.improvement.TileImprovementType;
import org.freerealm.unit.FreeRealmUnitType;
import org.freerealm.unit.Unit;
import org.freerealm.vegetation.Vegetation;

/**
 *
 * @author Deniz ARIKAN
 */
public interface Tile extends Customizable {

    public TileType getType();

    public void setType(TileType type);

    public int getProduction(Resource resource);

    public void putUnit(Unit unit);

    public void removeUnit(Unit unit);

    public int getNumberOfUnits();

    public Iterator<Unit> getUnitsIterator();

    public Unit getFirstUnit();

    public Map<Integer, Unit> getUnits();

    public int getNumberOfUnitsOfType(FreeRealmUnitType unitType);

    public Player getOccupiedByPlayer();

    public Settlement getSettlement();

    public void setSettlement(Settlement settlement);

    public float getMovementCost();

    public boolean hasImprovement(TileImprovementType improvement);

    public Vegetation getVegetation();

    public void setVegetation(Vegetation vegetation);

    public Iterator<TileImprovementType> getImprovementsIterator();

    public void addImprovement(TileImprovementType improvement);

    public void removeImprovement(TileImprovementType improvement);

    public int getImprovementCount();

    public void clearImprovements();

    public BonusResource getBonusResource();

    public void setBonusResource(BonusResource bonusResource);

    public void clearCustomModifiers();

    public int getCustomModifierCount();

    public Iterator<String> getCustomModifierNamesIterator();

    public TileModifier getCustomModifier(String modifierName);

    public void addCustomModifier(String modifierName, TileModifier tileModifier);

    public void removeCustomModifier(String modifierName);

    public Collectable getCollectable();

    public void setCollectable(Collectable collectable);
}
