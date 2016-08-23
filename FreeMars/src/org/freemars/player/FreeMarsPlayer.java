package org.freemars.player;

import java.util.HashMap;
import java.util.Iterator;
import org.freemars.earth.ModifyEarthFlightTime;
import org.freemars.earth.ModifyFinanceColonizerCost;
import org.freerealm.Realm;
import org.freerealm.player.FreeRealmPlayer;
import org.freerealm.property.Property;
import org.freerealm.resource.Resource;

/**
 *
 * @author Deniz ARIKAN
 */
public class FreeMarsPlayer extends FreeRealmPlayer {

    public static final int FREE_STARPORT_MIN_TURN = 24;
    public static final int FREE_COLONIZER_MIN_TURN = 36;
    public static final int FREE_TRANSPORTER_MIN_TURN = 48;
    public static final int FREE_FINANCIAL_AID_MIN_TURN = 60;
    private byte earthTaxRate;
    private final HashMap<Resource, ResourceTradeData> tradeData;
    private boolean declaredIndependence;
    private int independenceTurn;
    private boolean receivedFreeStarport;
    private boolean receivedFreeColonizer;
    private boolean receivedFreeTransporter;
    private boolean receivedFreeFinancialAid;
    private int totalTaxPaid;
    private boolean continuingGameAfterVictory;
    private boolean autoEndTurnPossible;
    private int financedColonistTotal;

    public FreeMarsPlayer(Realm realm) {
        super(realm);
        declaredIndependence = false;
        independenceTurn = -1;
        tradeData = new HashMap<Resource, ResourceTradeData>();
    }

    public byte getEarthTaxRate() {
        return earthTaxRate;
    }

    public void setEarthTaxRate(byte earthTaxRate) {
        this.earthTaxRate = earthTaxRate;
    }

    public boolean hasDeclaredIndependence() {
        return declaredIndependence;
    }

    public void setDeclaredIndependence(boolean declaredIndependence) {
        this.declaredIndependence = declaredIndependence;
    }

    public int getIndependenceTurn() {
        return independenceTurn;
    }

    public void setIndependenceTurn(int independenceTurn) {
        this.independenceTurn = independenceTurn;
    }

    public void clearTradeData() {
        tradeData.clear();
    }

    public Iterator<Resource> getResourceTradeDataIterator() {
        return tradeData.keySet().iterator();
    }

    public ResourceTradeData getResourceTradeData(int resourceId) {
        Resource resource = getRealm().getResourceManager().getResource(resourceId);
        if (!tradeData.containsKey(resource)) {
            tradeData.put(resource, new ResourceTradeData());
        }
        return tradeData.get(resource);
    }

    public void addResourceTradeData(Resource resource, ResourceTradeData resourceTradeData) {
        tradeData.put(resource, resourceTradeData);
    }

    public boolean canReceiveFreeStarport() {
        if (hasReceivedFreeStarport()) {
            return false;
        }
        if (getSettlementCount() == 0) {
            return false;
        }
        return getRealm().getNumberOfTurns() >= FREE_STARPORT_MIN_TURN;
    }

    public boolean hasReceivedFreeStarport() {
        return receivedFreeStarport;
    }

    public void setReceivedFreeStarport(boolean receivedFreeStarport) {
        this.receivedFreeStarport = receivedFreeStarport;
    }

    public boolean canReceiveFreeColonizer() {
        if (hasReceivedFreeColonizer()) {
            return false;
        }
        if (getSettlementCount() == 0) {
            return false;
        }
        return getRealm().getNumberOfTurns() >= FREE_COLONIZER_MIN_TURN;
    }

    public boolean hasReceivedFreeColonizer() {
        return receivedFreeColonizer;
    }

    public void setReceivedFreeColonizer(boolean receivedFreeColonizer) {
        this.receivedFreeColonizer = receivedFreeColonizer;
    }

    public boolean canReceiveFreeTransporter() {
        if (hasReceivedFreeTransporter()) {
            return false;
        }
        if (getSettlementCount() == 0) {
            return false;
        }
        return getRealm().getNumberOfTurns() >= FREE_TRANSPORTER_MIN_TURN;
    }

    public boolean hasReceivedFreeTransporter() {
        return receivedFreeTransporter;
    }

    public void setReceivedFreeTransporter(boolean receivedFreeTransporter) {
        this.receivedFreeTransporter = receivedFreeTransporter;
    }

    public boolean canReceiveFreeFinancialAid() {
        if (hasReceivedFreeFinancialAid()) {
            return false;
        }
        if (getSettlementCount() == 0) {
            return false;
        }
        return getRealm().getNumberOfTurns() >= FREE_FINANCIAL_AID_MIN_TURN;
    }

    public boolean hasReceivedFreeFinancialAid() {
        return receivedFreeFinancialAid;
    }

    public void setReceivedFreeFinancialAid(boolean receivedFreeFinancialAid) {
        this.receivedFreeFinancialAid = receivedFreeFinancialAid;
    }

    public int getFinanceColonizerCostModifier() {
        Property property = getProperty("ModifyFinanceColonizerCost");
        if (property != null) {
            ModifyFinanceColonizerCost modifyFinanceColonizerCost = (ModifyFinanceColonizerCost) property;
            return modifyFinanceColonizerCost.getModifier();
        }
        return 0;
    }

    public int getEarthFlightTimeModifier() {
        Property property = getProperty("ModifyEarthFlightTime");
        if (property != null) {
            ModifyEarthFlightTime modifyEarthFlightTime = (ModifyEarthFlightTime) property;
            return modifyEarthFlightTime.getModifier();
        }
        return 0;
    }

    public int getTotalIncomeFromExports() {
        int totalIncomeFromExports = 0;
        Iterator<Resource> iterator = getRealm().getResourceManager().getResourcesIterator();
        while (iterator.hasNext()) {
            Resource resource = iterator.next();
            ResourceTradeData resourceTradeData = getResourceTradeData(resource.getId());
            totalIncomeFromExports = totalIncomeFromExports + resourceTradeData.getIncomeBeforeTaxes();
        }
        return totalIncomeFromExports;
    }

    public int getTotalProfitFromExports() {
        int totalProfitFromExports = 0;
        Iterator<Resource> iterator = getRealm().getResourceManager().getResourcesIterator();
        while (iterator.hasNext()) {
            Resource resource = iterator.next();
            ResourceTradeData resourceTradeData = getResourceTradeData(resource.getId());
            totalProfitFromExports = totalProfitFromExports + resourceTradeData.getNetIncome();
        }
        return totalProfitFromExports;
    }

    public Resource getMostExportedResource() {
        Resource mostExportedResource = null;
        Iterator<Resource> iterator = getRealm().getResourceManager().getResourcesIterator();
        while (iterator.hasNext()) {
            Resource resource = iterator.next();
            if (mostExportedResource == null || getResourceTradeData(resource.getId()).getQuantityExported() > getResourceTradeData(mostExportedResource.getId()).getQuantityExported()) {
                mostExportedResource = resource;
            }
        }
        return mostExportedResource;
    }

    public Resource getMostProfitableExportResource() {
        Resource mostProfitableExportResource = null;
        Iterator<Resource> iterator = getRealm().getResourceManager().getResourcesIterator();
        while (iterator.hasNext()) {
            Resource resource = iterator.next();
            if (mostProfitableExportResource == null || getResourceTradeData(resource.getId()).getNetIncome() > getResourceTradeData(mostProfitableExportResource.getId()).getNetIncome()) {
                mostProfitableExportResource = resource;
            }
        }
        return mostProfitableExportResource;
    }

    public int getTotalTaxPaid() {
        return totalTaxPaid;
    }

    public void setTotalTaxPaid(int totalTaxPaid) {
        this.totalTaxPaid = totalTaxPaid;
    }

    public boolean isContinuingGameAfterVictory() {
        return continuingGameAfterVictory;
    }

    public void setContinuingGameAfterVictory(boolean continuingGameAfterVictory) {
        this.continuingGameAfterVictory = continuingGameAfterVictory;
    }

    public boolean isAutoEndTurnPossible() {
        return autoEndTurnPossible;
    }

    public void setAutoEndTurnPossible(boolean autoEndTurnPossible) {
        this.autoEndTurnPossible = autoEndTurnPossible;
    }

    public int getTradeIncome() {
        int tradeIncome = 0;
        Iterator<Resource> iterator = getResourceTradeDataIterator();
        while (iterator.hasNext()) {
            Resource resource = iterator.next();
            ResourceTradeData resourceTradeData = getResourceTradeData(resource.getId());
            tradeIncome = tradeIncome + resourceTradeData.getIncomeBeforeTaxes();
        }
        return tradeIncome;
    }

    public int getTradeProfit() {
        int tradeProfit = 0;
        Iterator<Resource> iterator = getResourceTradeDataIterator();
        while (iterator.hasNext()) {
            Resource resource = iterator.next();
            ResourceTradeData resourceTradeData = getResourceTradeData(resource.getId());
            tradeProfit = tradeProfit + resourceTradeData.getNetIncome();
        }
        return tradeProfit;
    }

    public int getFinancedColonistTotal() {
        return financedColonistTotal;
    }

    public void setFinancedColonistTotal(int financedColonistTotal) {
        this.financedColonistTotal = financedColonistTotal;
    }

}
