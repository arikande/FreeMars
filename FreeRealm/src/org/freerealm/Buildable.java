package org.freerealm;

import org.freerealm.modifier.Modifier;

/**
 *
 * @author Deniz ARIKAN
 */
public interface Buildable extends Modifier {

    public int getPrerequisiteCount();

    public int getProductionCost();

    public int getUpkeepCost();

}
