package org.freerealm.player;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.freerealm.Realm;
import org.freerealm.diplomacy.Diplomacy;
import org.freerealm.map.Coordinate;
import org.freerealm.modifier.FreeRealmModifier;
import org.freerealm.nation.Nation;
import org.freerealm.player.mission.Mission;
import org.freerealm.property.ModifyProduction;
import org.freerealm.property.Property;
import org.freerealm.settlement.Settlement;
import org.freerealm.unit.DefaultUnitGroupDefinition;
import org.freerealm.unit.FreeRealmUnitType;
import org.freerealm.unit.Unit;
import org.freerealm.unit.UnitGroupDefinition;

/**
 *
 * @author Deniz ARIKAN
 */
public class FreeRealmPlayer extends FreeRealmModifier implements Player, Comparable<FreeRealmPlayer> {

    private final Realm realm;
    private int status;
    private Color primaryColor;
    private Color secondaryColor;
    private boolean turnEnded;
    private int wealth;
    private int taxRate;
    private Nation nation;
    private Diplomacy diplomacy;
    private SettlementManager settlementManager;
    private final UnitManager unitManager;
    private final ArrayList<Coordinate> exploredCoordinates;
    private final ArrayList<Mission> missions;
    private final MessageManager messageManager;
    private final HashMap<Integer, Integer> builtImprovementsCount;
    private int clearedVegetationCount;

    public FreeRealmPlayer(Realm realm) {
        this.realm = realm;
        setTaxRate(0);
        settlementManager = new SettlementManager(this);
        unitManager = new UnitManager(this);
        if (realm.getProperty("starting_wealth") != null) {
            setWealth(Integer.parseInt(realm.getProperty("starting_wealth")));
        }
        exploredCoordinates = new ArrayList<Coordinate>();
        missions = new ArrayList<Mission>();
        messageManager = new MessageManager();
        builtImprovementsCount = new HashMap<Integer, Integer>();
    }

    @Override
    public String toString() {
        return getName();
    }

    public int compareTo(FreeRealmPlayer player) {
        if (getId() < player.getId()) {
            return -1;
        } else if (getId() > player.getId()) {
            return 1;
        } else {
            return 0;
        }
    }

    public Realm getRealm() {
        return realm;
    }

    public UnitGroupDefinition getUnitGroupDefinition() {
        DefaultUnitGroupDefinition unitGroupDefinition = new DefaultUnitGroupDefinition();
        Iterator<Unit> iterator = getUnitsIterator();
        while (iterator.hasNext()) {
            Unit unit = iterator.next();
            unitGroupDefinition.setQuantityForUnitType(unit.getType(), unitGroupDefinition.getQuantityForUnitType(unit.getType()) + 1);
        }
        return unitGroupDefinition;
    }

    public int getTotalIncome() {
        int totalIncome = 0;
        Iterator<Settlement> settlementIterator = getSettlementManager().getSettlementsIterator();
        while (settlementIterator.hasNext()) {
            totalIncome = totalIncome + settlementIterator.next().getWealthCollectedByTax();
        }
        /*
         Iterator<Unit> unitIterator = getUnitsIterator();
         while (unitIterator.hasNext()) {
         unitIterator.next();
         }
         */
        return totalIncome;
    }

    public int getTotalIncomeIf(int tax) {
        int totalIncome = 0;
        Iterator<Settlement> settlementIterator = getSettlementManager().getSettlementsIterator();
        while (settlementIterator.hasNext()) {
            totalIncome = totalIncome + settlementIterator.next().getWealthCollectedByTaxIf(tax);
        }
        /*
         Iterator<Unit> unitIterator = getUnitsIterator();
         while (unitIterator.hasNext()) {
         unitIterator.next();
         }
         */
        return totalIncome;
    }

    public int getTotalExpenses() {
        int totalExpenses = 0;
        totalExpenses = totalExpenses + getSettlementManager().getSettlementUpkeep();
        totalExpenses = totalExpenses + getUnitManager().getUnitUpkeep();
        return totalExpenses;
    }

    public boolean isMissionAssigned(int missionId) {
        Iterator<Mission> iterator = getMissionsIterator();
        while (iterator.hasNext()) {
            Mission mission = iterator.next();
            if (mission.getId() == missionId) {
                return true;
            }
        }
        return false;
    }

    public boolean isActive() {
        return getStatus() == Player.STATUS_ACTIVE;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPopulation() {
        int population = 0;
        Iterator<Settlement> settlementIterator = getSettlementManager().getSettlementsIterator();
        while (settlementIterator.hasNext()) {
            population = population + settlementIterator.next().getPopulation();
        }
        return population;
    }

    public boolean isTurnEnded() {
        return turnEnded;
    }

    public void setTurnEnded(boolean turnEnded) {
        this.turnEnded = turnEnded;
    }

    public Color getPrimaryColor() {
        return primaryColor;
    }

    public void setPrimaryColor(Color primaryColor) {
        this.primaryColor = primaryColor;
    }

    public Color getSecondaryColor() {
        return secondaryColor;
    }

    public void setSecondaryColor(Color secondaryColor) {
        this.secondaryColor = secondaryColor;
    }

    public int getWealth() {
        return wealth;
    }

    public void setWealth(int wealth) {
        this.wealth = wealth;
    }

    public int getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(int taxRate) {
        this.taxRate = taxRate;
    }

    public Nation getNation() {
        return nation;
    }

    public void setNation(Nation nation) {
        this.nation = nation;
    }

    public Diplomacy getDiplomacy() {
        return diplomacy;
    }

    public void setDiplomacy(Diplomacy diplomacy) {
        this.diplomacy = diplomacy;
    }

    public String getNextSettlementName() {
        String settlementName = "";
        Iterator<String> iterator = getNation().getSettlementNamesIterator();
        while (iterator.hasNext()) {
            boolean settlementNameExists = false;
            String trysettlementName = iterator.next();
            Iterator<Settlement> settlementsIterator = getSettlementManager().getSettlementsIterator();
            while (settlementsIterator.hasNext()) {
                Settlement settlement = settlementsIterator.next();
                if (settlement.getName().equals(trysettlementName)) {
                    settlementNameExists = true;
                }
            }
            if (!settlementNameExists) {
                return trysettlementName;
            }
        }
        return settlementName;
    }

    public UnitManager getUnitManager() {
        return unitManager;
    }

    public int getUnitCount() {
        return unitManager.getUnitCount();
    }

    public Iterator<Unit> getUnitsIterator() {
        return getUnitManager().getUnitsIterator();
    }

    public Unit getUnit(int unitId) {
        return getUnitManager().getUnit(unitId);
    }

    public Unit getActiveUnit() {
        return getUnitManager().getActiveUnit();
    }

    public void setActiveUnit(Unit unit) {
        getUnitManager().setActiveUnit(unit);
    }

    public Unit getNextUnit(Unit unit) {
        return getUnitManager().getNextUnit(unit);
    }

    public boolean hasUnit(Unit unit) {
        return getUnitManager().getUnits().containsValue(unit);
    }

    public void addUnit(Unit unit) {
        getUnitManager().addUnit(unit);
    }

    public void removeUnit(Unit unit) {
        getUnitManager().removeUnit(unit);
    }

    public List<Unit> getUnitsOfType(FreeRealmUnitType unitType) {
        return getUnitManager().getUnitsOfType(unitType);
    }

    public SettlementManager getSettlementManager() {
        return settlementManager;
    }

    public void setSettlementManager(SettlementManager settlementManager) {
        this.settlementManager = settlementManager;
    }

    public Iterator<Settlement> getSettlementsIterator() {
        return getSettlementManager().getSettlementsIterator();
    }

    public int getSettlementCount() {
        return settlementManager.getSettlementCount();
    }

    public void addSettlement(Settlement settlement) {
        getSettlementManager().addSettlement(settlement);
    }

    public void removeSettlement(Settlement settlement) {
        getSettlementManager().removeSettlement(settlement);
    }

    public boolean addExploredCoordinate(Coordinate coordinate) {
        if (coordinate != null && (!exploredCoordinates.contains(coordinate))) {
            exploredCoordinates.add(coordinate);
            return true;
        } else {
            return false;
        }
    }

    public List<Coordinate> addExploredCoordinates(List<Coordinate> coordinates) {
        List<Coordinate> addedCoordinates = new ArrayList<Coordinate>();
        Iterator<Coordinate> iterator = coordinates.iterator();
        while (iterator.hasNext()) {
            Coordinate coordinate = iterator.next();
            boolean added = addExploredCoordinate(coordinate);
            if (added) {
                addedCoordinates.add(coordinate);
            }
        }
        return addedCoordinates;
    }

    public boolean isCoordinateExplored(Coordinate coordinate) {
        return exploredCoordinates.contains(coordinate);
    }

    public Iterator<Coordinate> getExploredCoordinatesIterator() {
        return exploredCoordinates.iterator();
    }

    public List<Coordinate> getExploredCoordinates() {
        return exploredCoordinates;
    }

    public int getExploredCoordinateCount() {
        return exploredCoordinates.size();
    }

    public void clearExploredCoordinates() {
        exploredCoordinates.clear();
    }

    public void clearMessages() {
        messageManager.clear();
    }

    public void addMessage(Message message) {
        messageManager.addMessage(message);
    }

    public int getUnreadMessageCount() {
        return messageManager.getUnreadMessageCount();
    }

    public Iterator<Message> getUnreadMessagesIterator() {
        return messageManager.getUnreadMessagesIterator();
    }

    public Iterator<Message> getMessagesIterator() {
        return messageManager.getMessagesIterator();
    }

    public void addMission(Mission mission) {
        missions.add(mission);
    }

    public void removeMission(Mission mission) {
        missions.remove(mission);
    }

    public void clearMissions() {
        missions.clear();
    }

    public Iterator<Mission> getMissionsIterator() {
        return missions.iterator();
    }

    public int getMissionCount() {
        return missions.size();
    }

    public int getCompletedMissionCount() {
        int completedMissionCount = 0;
        Iterator<Mission> missionsIterator = getMissionsIterator();
        while (missionsIterator.hasNext()) {
            Mission mission = missionsIterator.next();
            if (mission.getStatus() == Mission.STATUS_COMPLETED) {
                completedMissionCount++;
            }
        }
        return completedMissionCount;
    }

    public int getCompletedMissionPercent() {
        if (getMissionCount() > 0) {
            return getCompletedMissionCount() * 100 / getMissionCount();
        } else {
            return 0;
        }
    }

    public int getFailedMissionCount() {
        int failedMissionCount = 0;
        Iterator<Mission> missionsIterator = getMissionsIterator();
        while (missionsIterator.hasNext()) {
            Mission mission = missionsIterator.next();
            if (mission.getStatus() == Mission.STATUS_FAILED) {
                failedMissionCount++;
            }
        }
        return failedMissionCount;
    }

    public int getFailedMissionPercent() {
        if (getMissionCount() > 0) {
            return getFailedMissionCount() * 100 / getMissionCount();
        } else {
            return 0;
        }
    }

    public void clearBuiltTileImprovementCount() {
        builtImprovementsCount.clear();
    }

    public Iterator<Integer> getBuiltTileImprovementCountIterator() {
        return builtImprovementsCount.keySet().iterator();
    }

    public int getBuiltTileImprovementCount(int typeId) {
        if (builtImprovementsCount.get(typeId) != null) {
            return builtImprovementsCount.get(typeId);
        } else {
            return 0;
        }
    }

    public void setBuiltTileImprovementCount(int typeId, int count) {
        builtImprovementsCount.put(typeId, count);
    }

    public int getBuiltSettlementImprovementCount(int typeId) {
        int builtSettlementImprovementCount = 0;
        Iterator<Settlement> settlementsIterator = getSettlementsIterator();
        while (settlementsIterator.hasNext()) {
            Settlement settlement = settlementsIterator.next();
            if (settlement.hasImprovementType(realm.getSettlementImprovementManager().getImprovement(typeId))) {
                builtSettlementImprovementCount = builtSettlementImprovementCount + 1;
            }
        }
        return builtSettlementImprovementCount;
    }

    public int getProductionModifier() {
        int productionModifier = 0;
        Iterator<Property> propertyEditor = getPropertiesIterator();
        while (propertyEditor.hasNext()) {
            Property property = propertyEditor.next();
            if (property instanceof ModifyProduction) {
                ModifyProduction modifyProduction = (ModifyProduction) property;
                productionModifier = productionModifier + modifyProduction.getModifier();
            }
        }
        return productionModifier;
    }

    public int getSettlementCountHavingImprovementType(int settlementImprovementTypeId) {
        int settlementCountHavingImprovementType = 0;
        Iterator<Settlement> iterator = getSettlementsIterator();
        while (iterator.hasNext()) {
            Settlement settlement = iterator.next();
            if (settlement.hasImprovementType(settlementImprovementTypeId)) {
                settlementCountHavingImprovementType++;
            }
        }
        return settlementCountHavingImprovementType;
    }

    public int getClearedVegetationCount() {
        return clearedVegetationCount;
    }

    public void setClearedVegetationCount(int clearedVegetationCount) {
        this.clearedVegetationCount = clearedVegetationCount;
    }
}
