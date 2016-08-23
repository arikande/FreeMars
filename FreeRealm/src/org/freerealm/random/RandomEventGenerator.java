package org.freerealm.random;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * @author Deniz ARIKAN
 */
public class RandomEventGenerator {

    private final HashMap<Integer, RandomEvent> randomEvents;

    public RandomEventGenerator() {
        randomEvents = new HashMap<Integer, RandomEvent>();
    }

    public RandomEvent getRandomEvent(int id) {
        return randomEvents.get(id);
    }

    public void registerRandomEvent(RandomEvent randomEvent) {
        randomEvents.put(randomEvent.getId(), randomEvent);
    }

    public Iterator<Integer> getRandomEventIdsIterator() {
        return randomEvents.keySet().iterator();
    }

    public ArrayList<RandomEvent> generateRandomEvents() {
        ArrayList<RandomEvent> generatedRandomEvents = new ArrayList<RandomEvent>();
        Iterator<Integer> randomEventCodes = randomEvents.keySet().iterator();
        while (randomEventCodes.hasNext()) {
            Integer randomEventCode = randomEventCodes.next();
            RandomEvent randomEvent = randomEvents.get(randomEventCode);
            double randomNumber = Math.random();
            if (randomEvent.getProbability() > randomNumber) {
                generatedRandomEvents.add(randomEvent);
            }
        }
        return generatedRandomEvents;
    }
}
