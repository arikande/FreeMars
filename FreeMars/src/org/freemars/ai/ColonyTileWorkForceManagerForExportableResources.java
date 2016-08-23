package org.freemars.ai;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.freemars.colony.FreeMarsColony;
import org.freemars.controller.FreeMarsController;
import org.freemars.mission.ExportResourceMission;
import org.freerealm.command.WorkforceAssignCommand;
import org.freerealm.map.Coordinate;
import org.freerealm.player.mission.Mission;
import org.freerealm.resource.Resource;
import org.freerealm.settlement.workforce.WorkForce;
import org.freerealm.tile.Tile;
import org.freerealm.tile.TileType;

/**
 *
 * @author Deniz ARIKAN
 */
public class ColonyTileWorkForceManagerForExportableResources {

    private static final String[] TILE_PRODUCABLE_EXPORT_RESOURCE_NAMES = new String[]{"Iron", "Silica", "Minerals", "Magnesium"};
    private static final Logger logger = Logger.getLogger(ColonyTileWorkForceManagerForExportableResources.class);
    private final FreeMarsController freeMarsController;
    private final AIPlayer aiPlayer;
    private List<Resource> tileProducableExportResources;
    private List<Coordinate> tileProducableExportResourceCoordinates;
    private TileType swampTileType;
    private int missionRequestedResourceId;

    public ColonyTileWorkForceManagerForExportableResources(FreeMarsController freeMarsController, AIPlayer freeMarsAIPlayer) {
        this.freeMarsController = freeMarsController;
        this.aiPlayer = freeMarsAIPlayer;
    }

    public void manage(FreeMarsColony freeMarsColony) {
        fillProducableExportResources(freeMarsColony);
        fillProducableExportResourceCoordinates(freeMarsColony);
        missionRequestedResourceId = findMissionRequestedResourceId();
        List<AssignmentOption> assignmentOptions = new ArrayList<AssignmentOption>();
        for (Coordinate coordinate : getTileProducableExportResourceCoordinates()) {
            Tile tile = freeMarsController.getFreeMarsModel().getTile(coordinate);
            for (Resource resource : getTileProducableExportResources()) {
                if (tile.getType().getProduction(resource) > 0) {
                    assignmentOptions.add(new AssignmentOption(coordinate, resource));
                }
            }
        }
        Collections.sort(assignmentOptions);
        if (!assignmentOptions.isEmpty()) {
            int availableWorkersForTileAssignment = freeMarsColony.getProductionWorkforce() * 3 / 4;
            if (availableWorkersForTileAssignment > freeMarsColony.getMaxWorkersPerTile()) {
                availableWorkersForTileAssignment = freeMarsColony.getMaxWorkersPerTile();
            }
            AssignmentOption assignmentOption = assignmentOptions.get(0);
            StringBuilder log = new StringBuilder();
            log.append("Assignment for ").append(freeMarsColony.getName()).append(" is \"");
            log.append(assignmentOption).append("\". ");
            log.append(availableWorkersForTileAssignment).append(" colonists will be assigned to tile.");
            logger.info(log.toString());
            WorkForce workForce = new WorkForce();
            workForce.setCoordinate(assignmentOption.getCoordinate());
            workForce.setNumberOfWorkers(availableWorkersForTileAssignment);
            workForce.setResource(assignmentOption.getResource());
            WorkforceAssignCommand workforceAssignCommand = new WorkforceAssignCommand(freeMarsController.getFreeMarsModel().getRealm(), freeMarsColony, workForce, assignmentOption.getCoordinate());
            freeMarsController.execute(workforceAssignCommand);
        }
    }

    private int findMissionRequestedResourceId() {
        Iterator<Mission> iterator = aiPlayer.getMissionsIterator();
        while (iterator.hasNext()) {
            Mission mission = iterator.next();
            if (mission.getStatus() == Mission.STATUS_ACTIVE && mission instanceof ExportResourceMission) {
                ExportResourceMission exportResourceMission = (ExportResourceMission) mission;
                return exportResourceMission.getResourceId();
            }
        }
        return -1;
    }

    private void fillProducableExportResources(FreeMarsColony freeMarsColony) {
        getTileProducableExportResources().clear();

        for (String tileProducableExportResourceName : TILE_PRODUCABLE_EXPORT_RESOURCE_NAMES) {
            Resource tileProducableExportResource = freeMarsController.getFreeMarsModel().getRealm().getResourceManager().getResource(tileProducableExportResourceName);
            if (freeMarsColony.getRemainingCapacity(tileProducableExportResource) > 1000) {
                getTileProducableExportResources().add(tileProducableExportResource);
            }
        }
    }

    private List<Resource> getTileProducableExportResources() {
        if (tileProducableExportResources == null) {
            tileProducableExportResources = new ArrayList<Resource>();
        }
        return tileProducableExportResources;
    }

    private void fillProducableExportResourceCoordinates(FreeMarsColony freeMarsColony) {
        getTileProducableExportResourceCoordinates().clear();
        List<Coordinate> validWorkForceCoordinates = freeMarsColony.getValidWorkForceCoordinates();
        for (Coordinate coordinate : validWorkForceCoordinates) {
            if (isValidCoordinateForExportResources(freeMarsColony, coordinate)) {
                getTileProducableExportResourceCoordinates().add(coordinate);
            }
        }
    }

    private boolean isValidCoordinateForExportResources(FreeMarsColony freeMarsColony, Coordinate coordinate) {
        if (coordinate == null) {
            return false;
        }
        Tile tile = freeMarsController.getFreeMarsModel().getTile(coordinate);
        if (tile == null) {
            return false;
        }
        if (tile.getType().equals(getSwampTileType())) {
            return false;
        }
        if (freeMarsColony.getWorkForceManager().getAssignedWorkforceForTile(coordinate) != null) {
            return false;
        }
        for (Resource tileProducableResource : getTileProducableExportResources()) {
            if (tile.getType().getProduction(tileProducableResource) > 0) {
                return true;
            }
        }
        return false;
    }

    private List<Coordinate> getTileProducableExportResourceCoordinates() {
        if (tileProducableExportResourceCoordinates == null) {
            tileProducableExportResourceCoordinates = new ArrayList<Coordinate>();
        }
        return tileProducableExportResourceCoordinates;
    }

    private TileType getSwampTileType() {
        if (swampTileType == null) {
            swampTileType = freeMarsController.getFreeMarsModel().getRealm().getTileTypeManager().getTileType("Swamp");
        }
        return swampTileType;
    }

    class AssignmentOption implements Comparable<AssignmentOption> {

        private final Coordinate coordinate;

        private final Resource resource;

        public AssignmentOption(Coordinate coordinate, Resource resource) {
            this.coordinate = coordinate;
            this.resource = resource;
        }

        @Override
        public String toString() {
            return coordinate.toString() + " - " + resource + " - " + getIncome();
        }

        public int compareTo(AssignmentOption otherOption) {
            if (getIncome() > otherOption.getIncome()) {
                return -1;
            } else if (getIncome() < otherOption.getIncome()) {
                return 1;
            } else {
                return 0;
            }
        }

        public Coordinate getCoordinate() {
            return coordinate;
        }

        public Resource getResource() {
            return resource;
        }

        public int getIncome() {
            Tile tile = freeMarsController.getFreeMarsModel().getTile(coordinate);
            int production = tile.getProduction(resource);
            int price = freeMarsController.getFreeMarsModel().getEarth().getEarthBuysAtPrice(resource);
            int income = production * price;
            if (missionRequestedResourceId == resource.getId()) {
                income = income * 4;
            }
            return income;
        }

    }

}
