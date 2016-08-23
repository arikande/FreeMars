package org.freerealm.settlement;

import java.util.Iterator;
import java.util.TreeMap;
import org.freerealm.map.Coordinate;
import org.freerealm.settlement.workforce.WorkForce;

/**
 *
 * @author Deniz ARIKAN
 */
public class WorkForceManager {

    private TreeMap<Coordinate, WorkForce> assignedWorkforce = null;

    public WorkForceManager() {
        assignedWorkforce = new TreeMap<Coordinate, WorkForce>();
    }

    public TreeMap<Coordinate, WorkForce> getWorkforce() {
        return assignedWorkforce;
    }

    public Iterator<WorkForce> getWorkForceIterator() {
        return getWorkforce().values().iterator();
    }

    public void addWorkForce(Coordinate coordinate, WorkForce workforce) {
        getWorkforce().put(coordinate, workforce);
    }

    public void removeWorkForce(Coordinate coordinate) {
        getWorkforce().remove(coordinate);
    }

    public WorkForce getAssignedWorkforceForTile(Coordinate coordinate) {
        if (getWorkforce().get(coordinate) == null) {
            return null;
        }
        return getWorkforce().get(coordinate);
    }

    public int getTotalWorkers() {
        int totalWorkers = 0;
        Iterator<WorkForce> workforceIterator = getWorkForceIterator();
        while (workforceIterator.hasNext()) {
            WorkForce workForce = (WorkForce) workforceIterator.next();
            totalWorkers = totalWorkers + workForce.getNumberOfWorkers();
        }
        return totalWorkers;
    }

    public int getTotalWorkersOnResource(int resourceId) {
        int totalOnResourceWorkers = 0;
        Iterator<WorkForce> workforceIterator = getWorkForceIterator();
        while (workforceIterator.hasNext()) {
            WorkForce workForce = (WorkForce) workforceIterator.next();
            if (workForce.getResource().getId() == resourceId) {
                totalOnResourceWorkers = totalOnResourceWorkers + workForce.getNumberOfWorkers();
            }
        }
        return totalOnResourceWorkers;
    }

    public void clearAssignedWorkForce() {
        assignedWorkforce.clear();
    }
}
