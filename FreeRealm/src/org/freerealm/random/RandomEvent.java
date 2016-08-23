package org.freerealm.random;

import java.util.Properties;

/**
 * @author Deniz ARIKAN
 */
public class RandomEvent {

    private int id;
    private String name;
    private double probability;
    private String command;
    private Properties properties;

    @Override
    public String toString() {
        return name;
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
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

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public Object getProperty(String key) {
        return properties.get(key);
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}
