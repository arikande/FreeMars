package org.freemars.util;

import java.util.List;
import java.util.Random;
import org.apache.log4j.Logger;
import org.freemars.controller.FreeMarsController;
import org.freerealm.map.Coordinate;
import org.freerealm.tile.Tile;

/**
 *
 * @author Deniz ARIKAN
 */
public class PlayerStartCoordinateGenerator {
    
    private static final Logger logger = Logger.getLogger(PlayerStartCoordinateGenerator.class);
    private static final Random rand = new Random(System.currentTimeMillis());
    
    public static Coordinate generate(FreeMarsController freeMarsController) {
        int TRY_COUNT = 50;
        Coordinate coordinate = new Coordinate();
        for (int i = 0; i < TRY_COUNT; i++) {
            int randomAbscissa = rand.nextInt(freeMarsController.getFreeMarsModel().getRealm().getMapWidth());
            int randomOrdinate = rand.nextInt(freeMarsController.getFreeMarsModel().getRealm().getMapHeight());
            coordinate.setAbscissa(randomAbscissa);
            coordinate.setOrdinate(randomOrdinate);
            if (isCoordinateOk(freeMarsController, coordinate)) {
                logger.info("Player start coordinate " + coordinate + " found on iteration " + i + ".");
                break;
            }
        }
        return coordinate;
    }
    
    private static boolean isCoordinateOk(FreeMarsController freeMarsController, Coordinate coordinate) {
        Tile tile = freeMarsController.getFreeMarsModel().getTile(coordinate);
        if ((!tile.getType().getName().equals("Wasteland")) && !(tile.getType().getName().equals("Plains"))) {
            return false;
        }
        int maximumProximityToPoles = 15;
        if (coordinate.getOrdinate() < maximumProximityToPoles || coordinate.getOrdinate() > freeMarsController.getFreeMarsModel().getRealm().getMapHeight() - maximumProximityToPoles) {
            return false;
        }
        if (tile.getCollectable() != null) {
            return false;
        }
        int swampCount = 0;
        for (int i = 0; i < 7; i++) {
            List<Coordinate> circleCoordinates = freeMarsController.getFreeMarsModel().getRealm().getCircleCoordinates(coordinate, i);
            for (Coordinate tryCoordinate : circleCoordinates) {
                Tile tryTile = freeMarsController.getFreeMarsModel().getRealm().getTile(tryCoordinate);
                if (tryTile.getType().getName().equals("Swamp")) {
                    swampCount++;
                }
                if (tryTile.getSettlement() != null || !tryTile.getUnits().isEmpty()) {
                    return false;
                }
            }
        }
        if (swampCount > 20) {
            return false;
        }
        return true;
    }
    
}
