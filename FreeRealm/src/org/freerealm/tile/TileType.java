package org.freerealm.tile;

import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;
import org.freerealm.resource.Resource;

/**
 *
 * @author Deniz ARIKAN
 */
public class TileType implements Comparable<TileType> {

    private int id;
    private String name;
    private int movementCost;
    private int defencePercentage;
    private int probability;
    private final TreeMap<Resource, Integer> production;
    private final HashMap<Integer, Integer> transformableTileTypes;

    public TileType() {
        production = new TreeMap<Resource, Integer>();
        transformableTileTypes = new HashMap<Integer, Integer>();
    }

    @Override
    public String toString() {
        return getName();
    }

    public int compareTo(TileType tileType) {
        if (getId() < tileType.getId()) {
            return -1;
        } else if (getId() > tileType.getId()) {
            return 1;
        } else {
            return 0;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMovementCost() {
        return movementCost;
    }

    public void setMovementCost(int movementCost) {
        this.movementCost = movementCost;
    }

    public int getProbability() {
        return probability;
    }

    public void setProbability(int probability) {
        this.probability = probability;
    }

    public void addProduction(Resource resource, int quantity) {
        getProduction().put(resource, quantity);
    }

    public int getProduction(Resource resource) {
        int productionValue = 0;
        if (getProduction().get(resource) != null) {
            productionValue = getProduction().get(resource);
        }
        return productionValue;
    }

    public int getDefencePercentage() {
        return defencePercentage;
    }

    public void setDefencePercentage(int defencePercentage) {
        this.defencePercentage = defencePercentage;
    }

    public TreeMap<Resource, Integer> getProduction() {
        return production;
    }

    public void addTransformableTileType(int tileTypeId, int transformationCost) {
        transformableTileTypes.put(tileTypeId, transformationCost);
    }

    public boolean canBeTransformedToTileType(TileType tileType) {
        return transformableTileTypes.keySet().contains(tileType.getId());
    }

    public Iterator<Integer> getTransformableTileTypeIdsIterator() {
        return transformableTileTypes.keySet().iterator();
    }

    public int getTransformableTileTypecount() {
        return transformableTileTypes.size();
    }

    public int getTransformationCostToTileType(int tileTypeId) {
        if (transformableTileTypes.containsKey(tileTypeId)) {
            return transformableTileTypes.get(tileTypeId);
        } else {
            return 0;
        }
    }
}
