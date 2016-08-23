package org.freemars.player;

import java.text.DecimalFormat;
import java.util.Iterator;
import org.freerealm.Realm;
import org.freerealm.modifier.Modifier;
import org.freerealm.player.Player;
import org.freerealm.resource.Resource;
import org.freerealm.settlement.RequiredPopulationResourceAmountCalculator;
import org.freerealm.settlement.Settlement;

/**
 *
 * @author Deniz ARIKAN
 */
public class PlayerSummaryPanelHelper {

    private final Realm realm;
    private final FreeMarsPlayer freeMarsPlayer;
    private final String currencyUnit;

    public PlayerSummaryPanelHelper(Realm realm, FreeMarsPlayer freeMarsPlayer) {
        this.realm = realm;
        this.freeMarsPlayer = freeMarsPlayer;
        currencyUnit = realm.getProperty("currency_unit");
    }

    public String getPopulationValue() {
        return new DecimalFormat().format(freeMarsPlayer.getPopulation()) + " colonists";
    }

    public String getMostPopulousColonyValue() {
        Settlement mostPopulousColony = getMostPopulousColony(freeMarsPlayer);
        String returnValue = "-";
        if (mostPopulousColony != null) {
            String mostPopulousColonyPopulation = new DecimalFormat().format(mostPopulousColony.getPopulation());
            returnValue = mostPopulousColony.getName() + " (" + mostPopulousColonyPopulation + ")";
        }
        return returnValue;
    }

    public String getSettlementCountValue() {
        return String.valueOf(freeMarsPlayer.getSettlementCount());
    }

    public String getUnitCountValue() {
        return String.valueOf(freeMarsPlayer.getUnitCount());
    }

    public String getMapExploredValue() {
        int mapExplorationPercent = (freeMarsPlayer.getExploredCoordinateCount() * 100) / (realm.getMapHeight() * realm.getMapWidth());
        return mapExplorationPercent + "%";
    }

    public String getTimeOnMarsValue() {
        String timeSpent = "";
        int turnsSpent = realm.getNumberOfTurns();
        if (turnsSpent == 0) {
            timeSpent = "-";
        } else {
            if (turnsSpent >= 12) {
                int year = (turnsSpent / 12);
                if (year == 1) {
                    timeSpent = timeSpent + year + " year ";
                } else {
                    timeSpent = timeSpent + year + " years ";
                }
            }
            if (turnsSpent % 12 != 0) {
                if (turnsSpent % 12 > 1) {
                    timeSpent = timeSpent + (turnsSpent % 12) + " months";
                } else {
                    timeSpent = timeSpent + (turnsSpent % 12) + " month";
                }
            }
        }
        timeSpent = timeSpent.trim();
        return timeSpent;
    }

    public String getRequiredResourceProductionValue(int resourceId) {
        Iterator<Settlement> iterator = freeMarsPlayer.getSettlementsIterator();
        int totalProduction = 0;
        while (iterator.hasNext()) {
            Settlement settlement = iterator.next();
            totalProduction = totalProduction + settlement.getResourceProductionFromTerrain(resourceId) + settlement.getResourceProductionFromImprovements(resourceId);
        }
        return new DecimalFormat().format(totalProduction);
    }

    public String getRequiredResourceValue(int resourceId) {
        Modifier[] modifiers = new Modifier[]{freeMarsPlayer};
        RequiredPopulationResourceAmountCalculator requiredPopulationResourceAmountCalculator = new RequiredPopulationResourceAmountCalculator(realm, resourceId, modifiers);
        return new DecimalFormat().format(freeMarsPlayer.getPopulation() * requiredPopulationResourceAmountCalculator.getRequiredPopulationResourceAmount());
    }

    public String getWealthValue() {
        return new DecimalFormat().format(freeMarsPlayer.getWealth()) + " " + currencyUnit;
    }

    public String getWealthPerColonistValue() {
        if (freeMarsPlayer.getPopulation() > 0) {
            double wealthPerColonist = (double) freeMarsPlayer.getWealth() / freeMarsPlayer.getPopulation();
            return new DecimalFormat("#.##").format(wealthPerColonist);
        } else {
            return "-";
        }
    }

    public String getEarthTaxRateValue() {
        return String.valueOf(freeMarsPlayer.getEarthTaxRate()) + "%";
    }

    public String getColonialTaxRateValue() {
        return String.valueOf(freeMarsPlayer.getTaxRate()) + "%";
    }

    public String getColonialTaxIncomeValue() {
        return new DecimalFormat().format(freeMarsPlayer.getTotalIncome()) + " " + currencyUnit + "/month";
    }

    public String getTotalUpkeepValue() {
        return new DecimalFormat().format(freeMarsPlayer.getTotalExpenses()) + " " + currencyUnit;
    }

    public String getIncomeFromExportsValue() {
        return new DecimalFormat().format(freeMarsPlayer.getTotalIncomeFromExports()) + " " + currencyUnit;
    }

    public String getTotalTaxPaidToEarthValue() {
        return new DecimalFormat().format(freeMarsPlayer.getTotalTaxPaid()) + " " + currencyUnit;
    }

    public String getProfitFromExportsValue() {
        return new DecimalFormat().format(freeMarsPlayer.getTotalProfitFromExports()) + " " + currencyUnit;
    }

    public String getFavoriteExportResourceValue() {
        Resource mostExportedResource = freeMarsPlayer.getMostExportedResource();
        int mostExportedResourceQuantity = freeMarsPlayer.getResourceTradeData(mostExportedResource.getId()).getQuantityExported();
        if (mostExportedResourceQuantity > 0) {
            return mostExportedResource.getName() + " (" + new DecimalFormat().format(mostExportedResourceQuantity) + " tons)";
        } else {
            return "-";
        }
    }

    public String getMostProfitableExportResourceValue() {
        Resource mostProfitableExportResource = freeMarsPlayer.getMostProfitableExportResource();
        int mostProfitableExportResourceIncome = freeMarsPlayer.getResourceTradeData(mostProfitableExportResource.getId()).getNetIncome();
        if (mostProfitableExportResourceIncome > 0) {
            return mostProfitableExportResource.getName() + " (" + new DecimalFormat().format(mostProfitableExportResourceIncome) + " " + currencyUnit + ")";
        } else {
            return "-";
        }
    }

    public String getNumberOfColonistsOnResource(int resourceId) {
        int numberOfColonists = 0;
        Iterator<Settlement> iterator = freeMarsPlayer.getSettlementsIterator();
        while (iterator.hasNext()) {
            Settlement settlement = iterator.next();
            numberOfColonists = numberOfColonists + settlement.getWorkForceManager().getTotalWorkersOnResource(resourceId);
        }
        return new DecimalFormat().format(numberOfColonists);
    }

    private Settlement getMostPopulousColony(Player player) {
        Settlement mostPopulousColony = null;
        Iterator<Settlement> iterator = player.getSettlementsIterator();
        while (iterator.hasNext()) {
            Settlement settlement = iterator.next();
            if (mostPopulousColony == null || settlement.getPopulation() > mostPopulousColony.getPopulation()) {
                mostPopulousColony = settlement;
            }
        }
        return mostPopulousColony;
    }
}
