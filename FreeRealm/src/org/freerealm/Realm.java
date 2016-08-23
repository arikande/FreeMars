package org.freerealm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import org.freerealm.history.History;
import org.freerealm.map.Coordinate;
import org.freerealm.map.Direction;
import org.freerealm.map.Map;
import org.freerealm.map.PathFinder;
import org.freerealm.nation.NationManager;
import org.freerealm.player.Player;
import org.freerealm.player.PlayerManager;
import org.freerealm.random.RandomEventGenerator;
import org.freerealm.resource.ResourceManager;
import org.freerealm.resource.bonus.BonusResourceManager;
import org.freerealm.settlement.Settlement;
import org.freerealm.settlement.improvement.SettlementImprovementManager;
import org.freerealm.tile.Tile;
import org.freerealm.tile.TileTypeManager;
import org.freerealm.tile.improvement.TileImprovementTypeManager;
import org.freerealm.unit.UnitTypeManager;
import org.freerealm.vegetation.VegetationTypeManager;

/**
 *
 * @author Deniz ARIKAN
 */
public class Realm {

    private Properties properties;
    private ResourceManager resourceManager;
    private BonusResourceManager bonusResourceManager;
    private TileTypeManager tileTypeManager;
    private TileImprovementTypeManager tileImprovementTypeManager;
    private VegetationTypeManager vegetationManager;
    private UnitTypeManager unitTypeManager;
    private SettlementImprovementManager settlementImprovementManager;
    private final HashMap<Integer, Integer> requiredPopulationResources;
    private NationManager nationManager;
    private PlayerManager playerManager;
    private int numberOfTurns;
    private int maximumNumberOfTurns;
    private int mapItemCount = 0;
    private Map map;
    private PathFinder pathFinder;
    private History history;
    private PopulationChangeManager populationChangeManager;
    private RandomEventGenerator randomEventGenerator;

    public Realm() {
        numberOfTurns = 0;
        maximumNumberOfTurns = Integer.MAX_VALUE;
        properties = new Properties();
        requiredPopulationResources = new HashMap<Integer, Integer>();
        randomEventGenerator = new RandomEventGenerator();
    }

    public int getMapItemCount() {
        return mapItemCount++;
    }

    public void setMapItemCount(int mapItemCount) {
        this.mapItemCount = mapItemCount;
    }

    public Iterator<Integer> getRequiredPopulationResourcesIterator() {
        return requiredPopulationResources.keySet().iterator();
    }

    public void addRequiredPopulationResourceAmount(int resourceId, int amount) {
        requiredPopulationResources.put(resourceId, amount);
    }

    public int getRequiredPopulationResourceAmount(int resourceId) {
        if (requiredPopulationResources.get(resourceId) != null) {
            return requiredPopulationResources.get(resourceId);
        } else {
            return 0;
        }
    }

    public Settlement getSettlement(int settlementId) {
        Iterator<Player> playerIterator = getPlayerManager().getPlayersIterator();
        while (playerIterator.hasNext()) {
            Player player = playerIterator.next();
            Iterator<Settlement> settlementsIterator = player.getSettlementsIterator();
            while (settlementsIterator.hasNext()) {
                Settlement settlement = settlementsIterator.next();
                if (settlement.getId() == settlementId) {
                    return settlement;
                }
            }
        }
        return null;
    }

    public Player getTileOwner(Coordinate coordinate) {
        if (getTile(coordinate) != null) {
            if (getTile(coordinate).getSettlement() != null) {
                return getTile(coordinate).getSettlement().getPlayer();
            }
            List<Coordinate> circleCoordinates = getCircleCoordinates(coordinate, 1);
            for (Coordinate circleCoordinate : circleCoordinates) {
                if (getTile(circleCoordinate).getSettlement() != null) {
                    return getTile(circleCoordinate).getSettlement().getPlayer();
                }
            }
        }
        return null;
    }

    public boolean isValidCoordinate(Coordinate coordinate) {
        return !((coordinate.getOrdinate() < 0) || (coordinate.getOrdinate() >= getMapHeight()));
    }

    private Coordinate normalizeCoordinate(Coordinate coordinate) {
        if (isValidCoordinate(coordinate)) {
            int abscissa = (coordinate.getAbscissa() + getMapWidth()) % getMapWidth();
            return new Coordinate(abscissa, coordinate.getOrdinate());
        } else {
            return null;
        }
    }

    private List<Coordinate> normalizeCoordinates(List<Coordinate> coordinates) {
        List<Coordinate> normalizedCoordinates = new ArrayList<Coordinate>();
        for (Coordinate coordinate : coordinates) {
            Coordinate normalizedCoordinate = normalizeCoordinate(coordinate);
            if (normalizedCoordinate != null) {
                normalizedCoordinates.add(normalizedCoordinate);
            }
        }
        return normalizedCoordinates;
    }

    public Direction getDirection(Coordinate coordinate, Coordinate directionCoordinate) {
        for (Direction direction : RealmConstants.directions) {
            Coordinate tryCoordinate = getRelativeCoordinate(coordinate, direction);
            if (directionCoordinate.equals(tryCoordinate)) {
                return direction;
            }
        }
        return null;
    }

    public Coordinate getRelativeCoordinate(Coordinate coordinate, Direction direction, boolean validCoordinate) {
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
        newAbscissa = (newAbscissa + getMapWidth()) % getMapWidth();
        Coordinate relativeCoordinate = new Coordinate(newAbscissa, newOrdinate);
        if (isValidCoordinate(relativeCoordinate)) {
            return relativeCoordinate;
        } else {
            return null;
        }
    }

    public Coordinate getRelativeCoordinate(Coordinate coordinate, Direction direction) {
        return getRelativeCoordinate(coordinate, direction, true);
    }

    public List<Coordinate> getCircleCoordinates(Coordinate coordinate, int distance) {
        return getCircleCoordinates(coordinate, distance, true);
    }

    public List<Coordinate> getCircleCoordinates(Coordinate coordinate, int distance, boolean normalizeCoordinates) {
        List<Coordinate> coordinates = new ArrayList<Coordinate>();
        if (distance == 0) {
            coordinates.add(coordinate);
        } else {
            Coordinate relativeCoordinate = new Coordinate(coordinate.getAbscissa(), coordinate.getOrdinate() - 2 * distance);
            coordinates.add(relativeCoordinate);
            for (int i = 0; i < distance * 2; i++) {
                relativeCoordinate = relativeCoordinate.getRelativeCoordinate(RealmConstants.SOUTHEAST);
                relativeCoordinate.setAbscissa((relativeCoordinate.getAbscissa() + getMapWidth()) % getMapWidth());
                if (!normalizeCoordinates || isValidCoordinate(relativeCoordinate)) {
                    coordinates.add(relativeCoordinate);
                }
            }
            for (int i = 0; i < distance * 2; i++) {
                relativeCoordinate = relativeCoordinate.getRelativeCoordinate(RealmConstants.SOUTHWEST);
                relativeCoordinate.setAbscissa((relativeCoordinate.getAbscissa() + getMapWidth()) % getMapWidth());
                if (!normalizeCoordinates || isValidCoordinate(relativeCoordinate)) {
                    coordinates.add(relativeCoordinate);
                }
            }
            for (int i = 0; i < distance * 2; i++) {
                relativeCoordinate = relativeCoordinate.getRelativeCoordinate(RealmConstants.NORTHWEST);
                relativeCoordinate.setAbscissa((relativeCoordinate.getAbscissa() + getMapWidth()) % getMapWidth());
                if (!normalizeCoordinates || isValidCoordinate(relativeCoordinate)) {
                    coordinates.add(relativeCoordinate);
                }
            }
            for (int i = 0; i < distance * 2 - 1; i++) {
                relativeCoordinate = relativeCoordinate.getRelativeCoordinate(RealmConstants.NORTHEAST);
                relativeCoordinate.setAbscissa((relativeCoordinate.getAbscissa() + getMapWidth()) % getMapWidth());
                if (!normalizeCoordinates || isValidCoordinate(relativeCoordinate)) {
                    coordinates.add(relativeCoordinate);
                }
            }
        }
        if (normalizeCoordinates) {
            coordinates = normalizeCoordinates(coordinates);
        }
        return coordinates;
    }

    public ArrayList<Settlement> getSettlementsNearCoordinate(Coordinate coordinate, int start, int end, Player belongsToPlayer) {
        ArrayList<Settlement> settlementsNearCoordinate = new ArrayList<Settlement>();
        if (coordinate != null) {
            for (int i = start; i <= end; i++) {
                List<Coordinate> coordinates = getCircleCoordinates(coordinate, i);
                for (Coordinate checkCoordinate : coordinates) {
                    Tile tile = getTile(checkCoordinate);
                    if (tile != null && tile.getSettlement() != null) {
                        if (belongsToPlayer == null) {
                            settlementsNearCoordinate.add(tile.getSettlement());
                        } else if (tile.getSettlement().getPlayer().equals(belongsToPlayer)) {
                            settlementsNearCoordinate.add(tile.getSettlement());
                        }
                    }
                }
            }
        }
        return settlementsNearCoordinate;
    }

    public void setPathFinder(PathFinder pathFinder) {
        this.pathFinder = pathFinder;
    }

    public PathFinder getPathFinder() {
        return pathFinder;
    }

    public int getNumberOfTurns() {
        return numberOfTurns;
    }

    public void setNumberOfTurns(int numberOfTurns) {
        this.numberOfTurns = numberOfTurns;
    }

    public int getMaximumNumberOfTurns() {
        return maximumNumberOfTurns;
    }

    public void setMaximumNumberOfTurns(int maximumNumberOfTurns) {
        this.maximumNumberOfTurns = maximumNumberOfTurns;
    }

    public int getMapWidth() {
        if (getMap() == null) {
            return 0;
        } else {
            return getMap().getWidth();
        }
    }

    public int getMapHeight() {
        if (getMap() == null) {
            return 0;
        } else {
            return getMap().getHeight();
        }
    }

    public TileTypeManager getTileTypeManager() {
        return tileTypeManager;
    }

    public void setTileTypeManager(TileTypeManager tileTypeManager) {
        this.tileTypeManager = tileTypeManager;
    }

    public UnitTypeManager getUnitTypeManager() {
        return unitTypeManager;
    }

    public void setUnitTypeManager(UnitTypeManager unitTypeManager) {
        this.unitTypeManager = unitTypeManager;
    }

    public SettlementImprovementManager getSettlementImprovementManager() {
        return settlementImprovementManager;
    }

    public void setSettlementImprovementManager(SettlementImprovementManager settlementImprovementManager) {
        this.settlementImprovementManager = settlementImprovementManager;
    }

    public ResourceManager getResourceManager() {
        return resourceManager;
    }

    public void setResourceManager(ResourceManager resourceManager) {
        this.resourceManager = resourceManager;
    }

    public BonusResourceManager getBonusResourceManager() {
        return bonusResourceManager;
    }

    public void setBonusResourceManager(BonusResourceManager bonusResourceManager) {
        this.bonusResourceManager = bonusResourceManager;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public void setPlayerManager(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public Tile getTile(Coordinate coordinate) {
        return getMap().getTile(coordinate);
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public NationManager getNationManager() {
        return nationManager;
    }

    public void setNationManager(NationManager nationManager) {
        this.nationManager = nationManager;
    }

    public TileImprovementTypeManager getTileImprovementTypeManager() {
        return tileImprovementTypeManager;
    }

    public void setTileImprovementTypeManager(TileImprovementTypeManager tileImprovementTypeManager) {
        this.tileImprovementTypeManager = tileImprovementTypeManager;
    }

    public VegetationTypeManager getVegetationManager() {
        return vegetationManager;
    }

    public void setVegetationManager(VegetationTypeManager vegetationManager) {
        this.vegetationManager = vegetationManager;
    }

    public History getHistory() {
        return history;
    }

    public void setHistory(History history) {
        this.history = history;
    }

    public PopulationChangeManager getPopulationChangeManager() {
        return populationChangeManager;
    }

    public void setPopulationChangeManager(PopulationChangeManager populationChangeManager) {
        this.populationChangeManager = populationChangeManager;
    }

    public RandomEventGenerator getRandomEventGenerator() {
        return randomEventGenerator;
    }

    public void setRandomEventGenerator(RandomEventGenerator randomEventGenerator) {
        this.randomEventGenerator = randomEventGenerator;
    }

}
