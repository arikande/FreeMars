package org.freerealm.vegetation;

import java.util.Iterator;

/**
 *
 * @author Deniz ARIKAN
 */
public interface VegetationTypeManager {

    public int getVegetationTypeCount();

    public VegetationType getVegetationType(int id);

    public void addVegetationType(VegetationType vegetationType);

    public Iterator<VegetationType> getVegetationTypesIterator();
}
