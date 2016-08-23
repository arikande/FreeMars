package org.freerealm;

import java.util.HashMap;
import java.util.Iterator;
import org.freerealm.settlement.PopulationInterval;

/**
 *
 * @author Deniz ARIKAN
 */
public class PopulationChangeManager {

    private final HashMap<PopulationInterval, Float> populationIncreaseBasePercentData;
    private final HashMap<PopulationInterval, Float> populationDecreaseBasePercentData;

    public PopulationChangeManager() {
        populationIncreaseBasePercentData = new HashMap<PopulationInterval, Float>();
        populationDecreaseBasePercentData = new HashMap<PopulationInterval, Float>();
    }

    public void addPopulationIncreaseBasePercentData(PopulationInterval populationInterval, float baseChange) {
        populationIncreaseBasePercentData.put(populationInterval, baseChange);
    }

    public Iterator<PopulationInterval> getPopulationIncreaseBasePercentDataIterator() {
        return populationIncreaseBasePercentData.keySet().iterator();
    }

    public float getPopulationIncreaseBasePercent(int population) {
        Iterator<PopulationInterval> iterator = populationIncreaseBasePercentData.keySet().iterator();
        while (iterator.hasNext()) {
            PopulationInterval populationInterval = iterator.next();
            if (population > populationInterval.getStart() && population < populationInterval.getEnd()) {
                return populationIncreaseBasePercentData.get(populationInterval);
            }
        }
        return 1;
    }

    public float getPopulationIncreaseBasePercent(PopulationInterval populationInterval) {
        if (populationIncreaseBasePercentData.containsKey(populationInterval)) {
            return populationIncreaseBasePercentData.get(populationInterval);
        } else {
            return 1;
        }
    }

    public void addPopulationDecreaseBasePercentData(PopulationInterval populationInterval, float baseChange) {
        populationDecreaseBasePercentData.put(populationInterval, baseChange);
    }

    public Iterator<PopulationInterval> getPopulationDecreaseBasePercentDataIterator() {
        return populationDecreaseBasePercentData.keySet().iterator();
    }

    public float getPopulationDecreaseBasePercent(int population) {
        Iterator<PopulationInterval> iterator = populationDecreaseBasePercentData.keySet().iterator();
        while (iterator.hasNext()) {
            PopulationInterval populationInterval = iterator.next();
            if (population > populationInterval.getStart() && population < populationInterval.getEnd()) {
                return populationDecreaseBasePercentData.get(populationInterval);
            }
        }
        return 1;
    }

    public float getPopulationDecreaseBasePercent(PopulationInterval populationInterval) {
        if (populationDecreaseBasePercentData.containsKey(populationInterval)) {
            return populationDecreaseBasePercentData.get(populationInterval);
        } else {
            return 1;
        }
    }
}
