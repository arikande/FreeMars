package org.freerealm.vegetation;

import java.util.Iterator;
import java.util.TreeMap;

/**
 *
 * @author Deniz ARIKAN
 */
public class VegetationTypeManagerImpl implements VegetationTypeManager {

    private final TreeMap<Integer, VegetationType> vegetationTypes;

    public VegetationTypeManagerImpl() {
        vegetationTypes = new TreeMap<Integer, VegetationType>();
    }

    public int getVegetationTypeCount() {
        return vegetationTypes.size();
    }

    public VegetationType getVegetationType(int id) {
        return vegetationTypes.get(id);
    }

    public void addVegetationType(VegetationType vegetation) {
        vegetationTypes.put(vegetation.getId(), vegetation);
    }

    public Iterator<VegetationType> getVegetationTypesIterator() {
        return vegetationTypes.values().iterator();
    }
}
