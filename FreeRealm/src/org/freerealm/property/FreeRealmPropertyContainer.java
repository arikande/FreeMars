package org.freerealm.property;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Deniz ARIKAN
 */
public class FreeRealmPropertyContainer implements PropertyContainer, Cloneable {

    private final List<Property> properties;

    public FreeRealmPropertyContainer() {
        properties = new ArrayList<Property>();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        FreeRealmPropertyContainer clone = new FreeRealmPropertyContainer();
        return clone;
    }
    
    public int getPropertyCount() {
        return properties.size();
    }

    public Iterator<Property> getPropertiesIterator() {
        return properties.iterator();
    }

    public void addProperty(Property property) {
        properties.add(property);
    }

    public void removeProperty(Property property) {
        properties.remove(property);
    }

    public boolean hasProperty(Property property) {
        return properties.contains(property);
    }

    public boolean hasPropertyNamed(String name) {
        Iterator<Property> iterator = getPropertiesIterator();
        while (iterator.hasNext()) {
            Property property = iterator.next();
            if (property.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public Property getProperty(String propertyName) {
        Property returnValue = null;
        Iterator<Property> propertyIterator = properties.iterator();
        while (propertyIterator.hasNext()) {
            Property property = propertyIterator.next();
            if (property.getName().equals(propertyName)) {
                returnValue = property;
            }
        }
        return returnValue;
    }
}
