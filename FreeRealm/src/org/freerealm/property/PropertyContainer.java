package org.freerealm.property;

import java.util.Iterator;

/**
 *
 * @author Deniz ARIKAN
 */
public interface PropertyContainer {

    public int getPropertyCount();

    public void addProperty(Property property);

    public void removeProperty(Property property);

    public boolean hasProperty(Property property);

    public boolean hasPropertyNamed(String name);

    public Iterator<Property> getPropertiesIterator();

    public Property getProperty(String propertyName);

}
