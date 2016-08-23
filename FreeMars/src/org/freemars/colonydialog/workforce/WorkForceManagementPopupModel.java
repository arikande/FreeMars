package org.freemars.colonydialog.workforce;

import java.util.TreeMap;
import org.freemars.colony.FreeMarsColony;
import org.freerealm.map.Coordinate;
import org.freerealm.resource.Resource;

/**
 *
 * @author Deniz ARIKAN
 */
public class WorkForceManagementPopupModel {

    private FreeMarsColony freeMarsColony;
    private TreeMap<Resource, Integer> selectableResources;
    private Resource selectedResource;
    private int maxWorkers;
    private int selectedNumberOfWorkers;
    private Coordinate coordinate;

    public TreeMap<Resource, Integer> getSelectableResources() {
        return selectableResources;
    }

    public void setSelectableResources(TreeMap<Resource, Integer> selectableResources) {
        this.selectableResources = selectableResources;
    }

    public Resource getSelectedResource() {
        return selectedResource;
    }

    public void setSelectedResource(Resource selectedResource) {
        this.selectedResource = selectedResource;
    }

    public int getMaxWorkers() {
        return maxWorkers;
    }

    public void setMaxWorkers(int maxWorkers) {
        this.maxWorkers = maxWorkers;
    }

    public FreeMarsColony getColony() {
        return freeMarsColony;
    }

    public void setColony(FreeMarsColony freeMarsColony) {
        this.freeMarsColony = freeMarsColony;
    }

    public int getSelectedNumberOfWorkers() {
        return selectedNumberOfWorkers;
    }

    public void setSelectedNumberOfWorkers(int selectedNumberOfWorkers) {
        this.selectedNumberOfWorkers = selectedNumberOfWorkers;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }
}
