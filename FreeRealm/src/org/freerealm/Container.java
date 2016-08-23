package org.freerealm;

import org.freerealm.resource.ResourceStorer;
import org.freerealm.unit.UnitContainer;

/**
 *
 * @author Deniz ARIKAN
 */
public interface Container extends ResourceStorer, UnitContainer, PopulationContainer {

    public int getTotalCapacity();

    public int getTotalContainedWeight();

    public int getRemainingCapacity();
}
