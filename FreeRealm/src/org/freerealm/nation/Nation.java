package org.freerealm.nation;

import java.awt.Color;
import java.util.Iterator;
import java.util.Vector;
import org.freerealm.property.Property;

/**
 *
 * @author Deniz ARIKAN
 */
public class Nation implements Comparable<Nation> {

    private int id;
    private String name;
    private String adjective;
    private String countryName;
    private Color defaultColor1;
    private Color defaultColor2;
    private Vector<String> settlementNames;
    private final Vector<Property> properties;

    public Nation() {
        settlementNames = new Vector<String>();
        properties = new Vector<Property>();
    }

    @Override
    public String toString() {
        return getName();
    }

    public int compareTo(Nation nation) {
        if (getId() < nation.getId()) {
            return -1;
        } else if (getId() > nation.getId()) {
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

    public String getAdjective() {
        return adjective;
    }

    public void setAdjective(String adjective) {
        this.adjective = adjective;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Color getDefaultColor1() {
        return defaultColor1;
    }

    public void setDefaultColor1(Color defaultColor1) {
        this.defaultColor1 = defaultColor1;
    }

    public Color getDefaultColor2() {
        return defaultColor2;
    }

    public void setDefaultColor2(Color defaultColor2) {
        this.defaultColor2 = defaultColor2;
    }

    public Iterator<String> getSettlementNamesIterator() {
        return settlementNames.iterator();
    }

    public void setSettlementNames(Vector<String> settlementNames) {
        this.settlementNames = settlementNames;
    }

    public void clearProperties() {
        properties.clear();
    }

    public void addProperty(Property property) {
        properties.add(property);
    }

    public Iterator<Property> getPropertiesIterator() {
        return properties.iterator();
    }

    public int getPropertyCount() {
        return properties.size();
    }
}
