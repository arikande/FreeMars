package org.freerealm.settlement.workforce;

import org.freerealm.map.Coordinate;
import org.freerealm.resource.Resource;

/**
 *
 * @author Deniz ARIKAN
 */
public class WorkForce {

    private Coordinate coordinate;
    private Resource resource;
    private int numberOfWorkers;

    @Override
    public String toString() {
        return resource + String.valueOf(getNumberOfWorkers());
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public int getNumberOfWorkers() {
        return numberOfWorkers;
    }

    public void setNumberOfWorkers(int numberOfWorkers) {
        this.numberOfWorkers = numberOfWorkers;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }
}
