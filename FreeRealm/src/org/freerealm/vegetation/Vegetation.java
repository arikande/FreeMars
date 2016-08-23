package org.freerealm.vegetation;

import org.freerealm.Customizable;

/**
 *
 * @author Deniz ARIKAN
 */
public interface Vegetation extends Customizable {

    public VegetationType getType();

    public void setType(VegetationType type);

}
