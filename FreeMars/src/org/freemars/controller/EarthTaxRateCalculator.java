package org.freemars.controller;

import java.util.Iterator;
import java.util.Random;
import org.freemars.earth.ModifyEarthTaxRate;
import org.freemars.model.FreeMarsModel;
import org.freemars.player.FreeMarsPlayer;
import org.freemars.player.ResourceTradeData;
import org.freerealm.resource.Resource;

/**
 *
 * @author Deniz ARIKAN
 */
public class EarthTaxRateCalculator {

    private static final byte MINIMUM_EARTH_TAX_RATE = 0;
    private static final byte MAXIMUM_EARTH_TAX_RATE = 30;
    private final FreeMarsModel freeMarsModel;
    private final FreeMarsPlayer freeMarsPlayer;
    private final Random random = new Random(System.currentTimeMillis());

    public EarthTaxRateCalculator(FreeMarsModel freeMarsModel, FreeMarsPlayer freeMarsPlayer) {
        this.freeMarsModel = freeMarsModel;
        this.freeMarsPlayer = freeMarsPlayer;
    }

    public byte getTaxRate() {
        byte earthTaxRate;
        int numberOfTurns = freeMarsModel.getNumberOfTurns();
        byte numberOfTurnsTaxRate = (byte) ((numberOfTurns - 36) / 72);
        byte netIncomeFromExportedResourcesTaxRate = (byte) (getNetIncomeFromExportedResources() / 40000);
        earthTaxRate = (byte) Math.max(numberOfTurnsTaxRate, netIncomeFromExportedResourcesTaxRate);
        byte randomByte = (byte) random.nextInt(4);
        earthTaxRate = (byte) (earthTaxRate + 4 - randomByte);

        ModifyEarthTaxRate modifyEarthTaxRate = (ModifyEarthTaxRate) freeMarsPlayer.getProperty("ModifyEarthTaxRate");
        if (modifyEarthTaxRate != null) {
            earthTaxRate = (byte) (earthTaxRate + modifyEarthTaxRate.getModifier());
        }

        if (earthTaxRate < MINIMUM_EARTH_TAX_RATE) {
            earthTaxRate = MINIMUM_EARTH_TAX_RATE;
        }
        if (earthTaxRate > MAXIMUM_EARTH_TAX_RATE) {
            earthTaxRate = MAXIMUM_EARTH_TAX_RATE;
        }
        return earthTaxRate;
    }

    private int getNetIncomeFromExportedResources() {
        int netIncomeFromExportedResources = 0;
        Iterator<Resource> iterator = freeMarsPlayer.getResourceTradeDataIterator();
        while (iterator.hasNext()) {
            Resource resource = iterator.next();
            ResourceTradeData resourceTradeData = freeMarsPlayer.getResourceTradeData(resource.getId());
            netIncomeFromExportedResources = netIncomeFromExportedResources + resourceTradeData.getNetIncome();
        }
        return netIncomeFromExportedResources;
    }
}
