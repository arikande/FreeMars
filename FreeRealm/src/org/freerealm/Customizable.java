package org.freerealm;

import java.util.Properties;

/**
 *
 * @author Deniz ARIKAN
 */
public interface Customizable {

    public Object getCustomProperty(Object key);

    public void addCustomProperty(Object key, Object value);

    public Properties getCustomProperties();

    public void setCustomProperties(Properties properties);
}
