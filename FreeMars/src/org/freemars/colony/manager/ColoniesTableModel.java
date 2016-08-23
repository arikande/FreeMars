package org.freemars.colony.manager;

import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.table.AbstractTableModel;
import org.freemars.colony.FreeMarsColony;
import org.freemars.ui.image.FreeMarsImageManager;
import org.freerealm.Realm;
import org.freerealm.modifier.Modifier;
import org.freerealm.player.Player;
import org.freerealm.resource.Resource;
import org.freerealm.settlement.RequiredPopulationResourceAmountCalculator;
import org.freerealm.settlement.Settlement;
import org.freerealm.settlement.SettlementBuildableCostCalculator;
import org.freerealm.settlement.improvement.SettlementImprovement;
import org.freerealm.settlement.improvement.SettlementImprovementResourceProductionModel;
import org.freerealm.settlement.improvement.SettlementImprovementType;
import org.freerealm.unit.FreeRealmUnitType;

/**
 *
 * @author Deniz ARIKAN
 */
public class ColoniesTableModel extends AbstractTableModel {

    public static final int ICON_COLUMN_INDEX = 0;
    public static final int NAME_COLUMN_INDEX = 1;
    public static final int POPULATION_COLUMN_INDEX = 2;
    public static final int WATER_COLUMN_INDEX = 3;
    public static final int FOOD_COLUMN_INDEX = 4;
    public static final int ENERGY_COLUMN_INDEX = 5;
    public static final int FERTILIZER_COLUMN_INDEX = 6;
    public static final int AUTO_MANAGED_RESOURCES_COLUMN_INDEX = 7;
    public static final int FARMERS_COLUMN_INDEX = 8;
    public static final int FARMER_PERCENT_COLUMN_INDEX = 9;
    public static final int WORKFORCE_COLUMN_INDEX = 10;
    public static final int CURRENT_PRODUCTION_COLUMN_INDEX = 11;
    public static final int STATUS_COLUMN_INDEX = 12;
    public static final int COMPLETED_IN_COLUMN_INDEX = 13;
    public static final int EFFICIENCY_COLUMN_INDEX = 14;
    public static final int MANUFACTURING_INFO_COLUMN_INDEX = 15;
    public static final int HYDROGEN_AMOUNT_COLUMN_INDEX = 16;
    public static final int LUMBER_AMOUNT_COLUMN_INDEX = 17;
    public static final int IRON_AMOUNT_COLUMN_INDEX = 18;
    public static final int STEEL_AMOUNT_COLUMN_INDEX = 19;
    public static final int SILICA_AMOUNT_COLUMN_INDEX = 20;
    public static final int GLASS_AMOUNT_COLUMN_INDEX = 21;
    public static final int MINERALS_AMOUNT_COLUMN_INDEX = 22;
    public static final int CHEMICALS_AMOUNT_COLUMN_INDEX = 23;
    public static final int MAGNESIUM_AMOUNT_COLUMN_INDEX = 24;
    public static final int GAUSS_RIFLE_AMOUNT_COLUMN_INDEX = 25;
    public static final int IMPROVEMENT_COUNT_COLUMN_INDEX = 26;
    public static final int IMPROVEMENT_UPKEEP_COLUMN_INDEX = 27;
    public static final int IMPROVEMENTS_COLUMN_INDEX = 28;
    public static final int DEFENSES_COLUMN_INDEX = 29;
    public static final int MILITARY_FACILITIES_COLUMN_INDEX = 30;
    public static final int UNITS_COLUMN_INDEX = 31;
    private final Realm realm;
    private final Player player;
    private final List<FreeMarsColony> colonies;
    private final String[] columnNames = {"", "Colony", "Population", "Water", "Food", "Energy", "Fertilizer", "Auto managed", "Farmers", "Farmer %", "Work force", "Current production", "Status", "Completed in", "Efficiency", "Manufacturing", "Hydrogen", "Lumber", "Iron", "Steel", "Silica", "Glass", "Minerals", "Chemicals", "Magnesium", "Gauss rifle", "Count", "Upkeep", "Improvements", "Defenses", "Facilities", "Units"};
    private final int waterResourceId;
    private final int foodResourceId;
    private final int energyResourceId;
    private final int hydrogenResourceId;
    private final int lumberResourceId;
    private final int fertilizerResourceId;
    private final int ironResourceId;
    private final int steelResourceId;
    private final int silicaResourceId;
    private final int glassResourceId;
    private final int mineralsResourceId;
    private final int chemicalsResourceId;
    private final int magnesiumResourceId;
    private final int gaussRifleResourceId;
    private final Resource waterResource;
    private final Resource foodResource;
    private final Resource energyResource;
    private final Resource hydrogenResource;
    private final Resource lumberResource;
    private final Resource fertilizerResource;
    private final Resource ironResource;
    private final Resource steelResource;
    private final Resource silicaResource;
    private final Resource glassResource;
    private final Resource mineralsResource;
    private final Resource chemicalsResource;
    private final Resource magnesiumResource;
    private final Resource gaussRifleResource;

    public ColoniesTableModel(Realm realm, Player player) {
        this.realm = realm;
        this.player = player;
        waterResourceId = 0;
        foodResourceId = 1;
        energyResourceId = 2;
        hydrogenResourceId = 3;
        lumberResourceId = 4;
        fertilizerResourceId = 5;
        ironResourceId = 6;
        steelResourceId = 7;
        silicaResourceId = 8;
        glassResourceId = 9;
        mineralsResourceId = 10;
        chemicalsResourceId = 11;
        magnesiumResourceId = 12;
        gaussRifleResourceId = 13;
        waterResource = realm.getResourceManager().getResource(waterResourceId);
        foodResource = realm.getResourceManager().getResource(foodResourceId);
        energyResource = realm.getResourceManager().getResource(energyResourceId);
        hydrogenResource = realm.getResourceManager().getResource(hydrogenResourceId);
        lumberResource = realm.getResourceManager().getResource(lumberResourceId);
        fertilizerResource = realm.getResourceManager().getResource(fertilizerResourceId);
        ironResource = realm.getResourceManager().getResource(ironResourceId);
        steelResource = realm.getResourceManager().getResource(steelResourceId);
        silicaResource = realm.getResourceManager().getResource(silicaResourceId);
        glassResource = realm.getResourceManager().getResource(glassResourceId);
        mineralsResource = realm.getResourceManager().getResource(mineralsResourceId);
        chemicalsResource = realm.getResourceManager().getResource(chemicalsResourceId);
        magnesiumResource = realm.getResourceManager().getResource(magnesiumResourceId);
        gaussRifleResource = realm.getResourceManager().getResource(gaussRifleResourceId);
        colonies = new ArrayList<FreeMarsColony>();
        Iterator<Settlement> iterator = player.getSettlementsIterator();
        while (iterator.hasNext()) {
            Settlement settlement = iterator.next();
            colonies.add((FreeMarsColony) settlement);
        }
    }

    @Override
    public Class getColumnClass(int column) {
        if (column == ICON_COLUMN_INDEX) {
            return Icon.class;
        }
        return Object.class;
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col].toString();
    }

    public int getRowCount() {
        return player.getSettlementCount();
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        FreeMarsColony settlement = colonies.get(rowIndex);
        Object value = null;
        switch (columnIndex) {
            case ICON_COLUMN_INDEX:
                Image image = FreeMarsImageManager.getImage(settlement);
                image = FreeMarsImageManager.createResizedCopy(image, -1, 38, false, null);
                value = new ImageIcon(image);
                break;
            case NAME_COLUMN_INDEX:
                value = settlement.getName();
                break;
            case POPULATION_COLUMN_INDEX:
                value = settlement.getPopulation();
                break;
            case WATER_COLUMN_INDEX:
                int[] waterValues = {0, 0};
                int waterProduction = settlement.getResourceProductionFromTerrain(waterResourceId) + settlement.getResourceProductionFromImprovements(waterResourceId);
                Modifier[] waterModifiers = new Modifier[]{player};
                RequiredPopulationResourceAmountCalculator requiredWaterAmountCalculator = new RequiredPopulationResourceAmountCalculator(realm, waterResourceId, waterModifiers);
                int waterNeededPerColonist = requiredWaterAmountCalculator.getRequiredPopulationResourceAmount();
                waterValues[0] = settlement.getResourceQuantity(waterResource);
                waterValues[1] = waterProduction - waterNeededPerColonist * settlement.getPopulation();
                value = waterValues;
                break;
            case FOOD_COLUMN_INDEX:
                int[] foodValues = {0, 0};
                int foodProduction = settlement.getResourceProductionFromTerrain(foodResourceId) + settlement.getResourceProductionFromImprovements(foodResourceId);
                Modifier[] foodModifiers = new Modifier[]{player};
                RequiredPopulationResourceAmountCalculator requiredFoodAmountCalculator = new RequiredPopulationResourceAmountCalculator(realm, foodResourceId, foodModifiers);
                int foodNeededPerColonist = requiredFoodAmountCalculator.getRequiredPopulationResourceAmount();
                foodValues[0] = settlement.getResourceQuantity(foodResource);
                foodValues[1] = foodProduction - foodNeededPerColonist * settlement.getPopulation();
                value = foodValues;
                break;
            case ENERGY_COLUMN_INDEX:
                int energyAmount = settlement.getResourceQuantity(energyResource);
                value = energyAmount;
                break;
            case FERTILIZER_COLUMN_INDEX:
                int fertilizerAmount = settlement.getResourceQuantity(fertilizerResource);
                int[] fertilizerValues = {0, 0};
                int fertilizerQuantityPerTile = Integer.parseInt(realm.getProperty("fertilizer_quantity_per_tile"));
                int fertilizerConsumption = settlement.getFertilizedCoordinatesSize() * fertilizerQuantityPerTile;
                fertilizerValues[0] = fertilizerAmount;
                fertilizerValues[1] = fertilizerConsumption;
                value = fertilizerValues;
                break;
            case AUTO_MANAGED_RESOURCES_COLUMN_INDEX:
                List<Resource> autoManagedResources = new ArrayList<Resource>();
                if (settlement.isAutomanagingResource(waterResource)) {
                    autoManagedResources.add(waterResource);
                }
                if (settlement.isAutomanagingResource(foodResource)) {
                    autoManagedResources.add(foodResource);
                }
                if (settlement.isAutoUsingFertilizer()) {
                    autoManagedResources.add(fertilizerResource);
                }
                value = autoManagedResources;
                break;
            case FARMERS_COLUMN_INDEX:
                value = settlement.getWorkForceManager().getTotalWorkersOnResource(foodResourceId);
                break;
            case FARMER_PERCENT_COLUMN_INDEX:
                if (settlement.getPopulation() > 0) {
                    value = (settlement.getWorkForceManager().getTotalWorkersOnResource(foodResourceId) * 100 / settlement.getPopulation()) + "%";
                } else {
                    value = "0%";
                }
                break;
            case WORKFORCE_COLUMN_INDEX:
                value = settlement.getProductionWorkforce();
                break;
            case CURRENT_PRODUCTION_COLUMN_INDEX:
                value = settlement.getCurrentProduction();
                break;
            case STATUS_COLUMN_INDEX:
                if (settlement.getCurrentProduction() != null) {
                    int productionCost = new SettlementBuildableCostCalculator(settlement.getCurrentProduction(), new Modifier[]{player}).getCost();
                    value = settlement.getProductionPoints() + "/" + productionCost;
                } else {
                    value = "";
                }
                break;
            case COMPLETED_IN_COLUMN_INDEX:
                if (settlement.getCurrentProduction() != null) {
                    int productionCost = new SettlementBuildableCostCalculator(settlement.getCurrentProduction(), new Modifier[]{player}).getCost();
                    if (settlement.getProductionWorkforce() == 0) {
                        value = "";
                    } else if (productionCost > settlement.getProductionPoints()) {
                        value = ((productionCost - settlement.getProductionPoints()) / settlement.getProductionWorkforce()) + 1;
                    } else {
                        value = 1;
                    }
                } else {
                    value = "";
                }
                break;
            case EFFICIENCY_COLUMN_INDEX:
                value = settlement.getEfficiency();
                break;
            case MANUFACTURING_INFO_COLUMN_INDEX:
                List<SettlementImprovementResourceProductionModel> manufacturingData = new ArrayList<SettlementImprovementResourceProductionModel>();
                Iterator<SettlementImprovementType> settlementImprovementTypeIterator = realm.getSettlementImprovementManager().getImprovementsIterator();
                while (settlementImprovementTypeIterator.hasNext()) {
                    SettlementImprovementType settlementImprovementType = settlementImprovementTypeIterator.next();
                    if (settlement.hasImprovementType(settlementImprovementType) && settlementImprovementType.getInputResourceCount() > 0 && settlementImprovementType.getOutputResourceCount() > 0) {
                        SettlementImprovement settlementImprovement = settlement.getImprovementOfType(settlementImprovementType);
                        SettlementImprovementResourceProductionModel settlementImprovementResourceProductionModel = new SettlementImprovementResourceProductionModel(settlement, settlementImprovement);
                        manufacturingData.add(settlementImprovementResourceProductionModel);
                    }
                }
                value = manufacturingData;
                break;
            case HYDROGEN_AMOUNT_COLUMN_INDEX:
                value = SettlementResourceAmountHelper.getSettlementResourceAmountValues(settlement, hydrogenResource);
                break;
            case LUMBER_AMOUNT_COLUMN_INDEX:
                value = SettlementResourceAmountHelper.getSettlementResourceAmountValues(settlement, lumberResource);
                break;
            case IRON_AMOUNT_COLUMN_INDEX:
                value = SettlementResourceAmountHelper.getSettlementResourceAmountValues(settlement, ironResource);
                break;
            case STEEL_AMOUNT_COLUMN_INDEX:
                value = SettlementResourceAmountHelper.getSettlementResourceAmountValues(settlement, steelResource);
                break;
            case SILICA_AMOUNT_COLUMN_INDEX:
                value = SettlementResourceAmountHelper.getSettlementResourceAmountValues(settlement, silicaResource);
                break;
            case GLASS_AMOUNT_COLUMN_INDEX:
                value = SettlementResourceAmountHelper.getSettlementResourceAmountValues(settlement, glassResource);
                break;
            case MINERALS_AMOUNT_COLUMN_INDEX:
                value = SettlementResourceAmountHelper.getSettlementResourceAmountValues(settlement, mineralsResource);
                break;
            case CHEMICALS_AMOUNT_COLUMN_INDEX:
                value = SettlementResourceAmountHelper.getSettlementResourceAmountValues(settlement, chemicalsResource);
                break;
            case MAGNESIUM_AMOUNT_COLUMN_INDEX:
                value = SettlementResourceAmountHelper.getSettlementResourceAmountValues(settlement, magnesiumResource);
                break;
            case GAUSS_RIFLE_AMOUNT_COLUMN_INDEX:
                value = SettlementResourceAmountHelper.getSettlementResourceAmountValues(settlement, gaussRifleResource);
                break;
            case IMPROVEMENT_COUNT_COLUMN_INDEX:
                value = settlement.getImprovementCount();
                break;
            case IMPROVEMENT_UPKEEP_COLUMN_INDEX:
                value = settlement.getImprovementUpkeep();
                break;
            case IMPROVEMENTS_COLUMN_INDEX:
                List<SettlementImprovement> improvements = new ArrayList<SettlementImprovement>();
                for (int i = 0; i < realm.getSettlementImprovementManager().getImprovementCount(); i++) {
                    SettlementImprovementType settlementImprovementType = realm.getSettlementImprovementManager().getImprovement(i);
                    if (settlement.hasImprovementType(settlementImprovementType)) {
                        improvements.add(settlement.getImprovementOfType(settlementImprovementType));
                    }
                }
                value = improvements;
                break;
            case DEFENSES_COLUMN_INDEX:
                List<SettlementImprovement> defensiveImprovements = new ArrayList<SettlementImprovement>();
                for (int i = 0; i < realm.getSettlementImprovementManager().getImprovementCount(); i++) {
                    SettlementImprovementType settlementImprovementType = realm.getSettlementImprovementManager().getImprovement(i);
                    if (settlement.hasImprovementType(settlementImprovementType) && settlementImprovementType.getProperty("ModifyDefence") != null) {
                        defensiveImprovements.add(settlement.getImprovementOfType(settlementImprovementType));
                    }
                }
                value = defensiveImprovements;
                break;
            case MILITARY_FACILITIES_COLUMN_INDEX:
                List<SettlementImprovement> militaryFacilityImprovements = new ArrayList<SettlementImprovement>();
                SettlementImprovementType starportImprovementType = realm.getSettlementImprovementManager().getImprovement("Starport");
                if (settlement.hasImprovementType(starportImprovementType)) {
                    militaryFacilityImprovements.add(settlement.getImprovementOfType(starportImprovementType));
                }

                SettlementImprovementType starshipyardImprovementType = realm.getSettlementImprovementManager().getImprovement("Starshipyard");
                if (settlement.hasImprovementType(starshipyardImprovementType)) {
                    militaryFacilityImprovements.add(settlement.getImprovementOfType(starshipyardImprovementType));
                }

                SettlementImprovementType mechBarracksImprovementType = realm.getSettlementImprovementManager().getImprovement("Mech barracks");
                if (settlement.hasImprovementType(mechBarracksImprovementType)) {
                    militaryFacilityImprovements.add(settlement.getImprovementOfType(mechBarracksImprovementType));
                }
                value = militaryFacilityImprovements;
                break;
            case UNITS_COLUMN_INDEX:
                HashMap<FreeRealmUnitType, Integer> unitTypeQuantities = new HashMap<FreeRealmUnitType, Integer>();
                for (int i = 0; i < realm.getUnitTypeManager().getUnitTypeCount(); i++) {
                    FreeRealmUnitType unitType = realm.getUnitTypeManager().getUnitType(i);
                    int unitCount = realm.getTile(settlement.getCoordinate()).getNumberOfUnitsOfType(unitType);
                    if (unitCount > 0) {
                        unitTypeQuantities.put(unitType, unitCount);
                    }
                }
                value = unitTypeQuantities;
                break;
        }
        return value;
    }

    public FreeMarsColony getColonyAtRow(int rowIndex) {
        return colonies.get(rowIndex);
    }
}
