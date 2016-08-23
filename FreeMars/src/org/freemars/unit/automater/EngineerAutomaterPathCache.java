package org.freemars.unit.automater;

import java.util.TreeMap;
import org.freerealm.map.Coordinate;
import org.freerealm.map.Path;

/**
 *
 * @author Deniz ARIKAN
 */
public class EngineerAutomaterPathCache extends TreeMap<EngineerAutomaterPathCacheKey, Path> {

    protected Path getPath(Coordinate coordinateA, Coordinate coordinateB) {
        return get(new EngineerAutomaterPathCacheKey(coordinateA, coordinateB));
    }

    protected void addPath(Coordinate coordinateA, Coordinate coordinateB, Path path) {
        put(new EngineerAutomaterPathCacheKey(coordinateA, coordinateB), path);
    }
}
