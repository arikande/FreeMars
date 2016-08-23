package org.freerealm;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import org.freerealm.map.Coordinate;
import org.freerealm.map.Direction;
import org.freerealm.player.Player;
import org.freerealm.resource.bonus.BonusResource;
import org.freerealm.settlement.Settlement;
import org.freerealm.tile.Tile;
import org.freerealm.tile.TileType;
import org.freerealm.vegetation.VegetationType;

/**
 *
 * @author Deniz ARIKAN
 */
public class Utility {

    public static ArrayList<BonusResource> getBonusResourceTypesAvailableForTileType(Realm realm, TileType tileType) {
        ArrayList<BonusResource> bonusResources = new ArrayList<BonusResource>();
        Iterator<BonusResource> iterator = realm.getBonusResourceManager().getBonusResourcesIterator();
        while (iterator.hasNext()) {
            BonusResource bonusResource = iterator.next();
            if (bonusResource.hasTileType(tileType)) {
                bonusResources.add(bonusResource);
            }
        }
        return bonusResources;
    }

    public static ArrayList<VegetationType> getVegetationTypesAvailableForTileType(Realm realm, TileType tileType) {
        ArrayList<VegetationType> vegetationTypes = new ArrayList<VegetationType>();
        Iterator<VegetationType> iterator = realm.getVegetationManager().getVegetationTypesIterator();
        while (iterator.hasNext()) {
            VegetationType vegetationType = iterator.next();
            if (vegetationType.canGrowOnTileType(tileType)) {
                vegetationTypes.add(vegetationType);
            }
        }
        return vegetationTypes;
    }

    private static boolean isValidCoordinate(Realm realm, Coordinate coordinate) {
        return !((coordinate.getOrdinate() < 0) || (coordinate.getOrdinate() >= realm.getMapHeight()));
    }

    private static Coordinate getRelativeCoordinate(Realm realm, Coordinate coordinate, Direction direction) {
        int newAbscissa = coordinate.getAbscissa();
        int newOrdinate = coordinate.getOrdinate();
        switch (direction.getId()) {
            case 7:
                newAbscissa = coordinate.getAbscissa() - 1 + (coordinate.getOrdinate() % 2);
                newOrdinate = coordinate.getOrdinate() - 1;
                break;
            case 8:
                newOrdinate = coordinate.getOrdinate() - 2;
                break;
            case 9:
                newAbscissa = coordinate.getAbscissa() + (coordinate.getOrdinate() % 2);
                newOrdinate = coordinate.getOrdinate() - 1;
                break;
            case 4:
                newAbscissa = coordinate.getAbscissa() - 1;
                break;
            case 6:
                newAbscissa = coordinate.getAbscissa() + 1;
                break;
            case 1:
                newAbscissa = coordinate.getAbscissa() - 1 + (coordinate.getOrdinate() % 2);
                newOrdinate = coordinate.getOrdinate() + 1;
                break;
            case 2:
                newOrdinate = coordinate.getOrdinate() + 2;
                break;
            case 3:
                newAbscissa = coordinate.getAbscissa() + (coordinate.getOrdinate() % 2);
                newOrdinate = coordinate.getOrdinate() + 1;
                break;
        }
        newAbscissa = (newAbscissa + realm.getMapWidth()) % realm.getMapWidth();
        Coordinate relativeCoordinate = new Coordinate(newAbscissa, newOrdinate);
        if (isValidCoordinate(realm, relativeCoordinate)) {
            return relativeCoordinate;
        } else {
            return null;
        }
    }

    public static boolean isTileAvailableForSettlement(Realm realm, Settlement settlement, Coordinate coordinate) {
        Tile tile = realm.getMap().getTile(coordinate);
        if (tile == null) {
            return false;
        }
        if (tile.getSettlement() != null && !tile.getSettlement().equals(settlement)) {
            return false;
        }
        Settlement settlementUsingCoordinate = findSettlementUsingCoordinate(realm, coordinate);
        return !(settlementUsingCoordinate != null && !settlement.equals(settlementUsingCoordinate));
    }

    public static Settlement findSettlementUsingCoordinate(Realm realm, Coordinate coordinate) {
        if (coordinate != null) {
            Iterator<Player> playersIterator = realm.getPlayerManager().getPlayersIterator();
            while (playersIterator.hasNext()) {
                Player player = playersIterator.next();
                Iterator<Settlement> settlementsIterator = player.getSettlementsIterator();
                while (settlementsIterator.hasNext()) {
                    Settlement settlement = settlementsIterator.next();
                    if (settlement.getWorkForceManager().getAssignedWorkforceForTile(coordinate) != null) {
                        return settlement;
                    }
                }
            }
        }
        return null;
    }

    public static Properties getProperties(String filePath) {
        Properties properties = null;
        properties = new Properties();
        try {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(ClassLoader.getSystemResourceAsStream(filePath));
            properties.load(bufferedInputStream);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return properties;
    }

    public static int calculatePercent(int value, int percent) {
        float result = value;
        result = (percent * value) / 100;
        return (int) result;
    }

    public static float modifyByPercent(float value, int percent) {
        float result = value;
        result = ((100 + percent) * value) / 100f;
        return result;
    }

    public static int getCoordinateDefenceBonus(Realm realm, Coordinate coordinate) {
        int coordinateDefenceBonus = 0;
        Tile tile = realm.getTile(coordinate);
        coordinateDefenceBonus = tile.getType().getDefencePercentage();
        if (tile.getSettlement() != null) {
            coordinateDefenceBonus = coordinateDefenceBonus + tile.getSettlement().getDefenceModifier();
        }
        return coordinateDefenceBonus;
    }

    public static List<Coordinate> getUnexploredCoordinatesOfPlayer(Player exploredCoordinatesPlayer, Player queryingPlayer) {
        List<Coordinate> coordinates = new ArrayList<Coordinate>();
        Iterator<Coordinate> iterator = exploredCoordinatesPlayer.getExploredCoordinatesIterator();
        while (iterator.hasNext()) {
            Coordinate coordinate = iterator.next();
            if (!queryingPlayer.isCoordinateExplored(coordinate)) {
                coordinates.add(coordinate);
            }
        }
        return coordinates;
    }

}
