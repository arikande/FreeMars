package org.freerealm;

/**
 *
 * @author Deniz ARIKAN
 */
public interface PopulationContainer {

    public int getTotalCapacity();

    public int getTotalContainedWeight();

    public int getRemainingCapacity();

    public int getContainedPopulation();

    public void setContainedPopulation(int population);

    public boolean canContainPopulation();
}
