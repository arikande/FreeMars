package org.freerealm.vegetation;

import java.util.Properties;

/**
 *
 * @author Deniz ARIKAN
 */
public class FreeRealmVegetation implements Vegetation {

    private VegetationType type;
    private Properties customProperties;

    public FreeRealmVegetation() {
        customProperties = new Properties();
    }

    public VegetationType getType() {
        return type;
    }

    public void setType(VegetationType type) {
        this.type = type;
    }

    public Object getCustomProperty(Object key) {
        return customProperties.get(key);
    }

    public void addCustomProperty(Object key, Object value) {
        customProperties.put(key, value);
    }

    public Properties getCustomProperties() {
        return customProperties;
    }

    public void setCustomProperties(Properties properties) {
        this.customProperties = properties;
    }
}
