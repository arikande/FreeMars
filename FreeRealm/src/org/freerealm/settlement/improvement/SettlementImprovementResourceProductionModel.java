package org.freerealm.settlement.improvement;

import java.util.HashMap;
import java.util.Iterator;
import org.freerealm.resource.Resource;
import org.freerealm.settlement.Settlement;

/**
 *
 * @author Deniz ARIKAN
 */
public class SettlementImprovementResourceProductionModel {

    private final SettlementImprovement settlementImprovement;
    private double productionRatio = 1;
    private double estimatedProductionRatio = 1;
    private final HashMap<Resource, Double> estimatedInputRatios;
    private final double workerRatio;

    public SettlementImprovementResourceProductionModel(Settlement settlement, SettlementImprovement settlementImprovement) {
        this.settlementImprovement = settlementImprovement;
        workerRatio = (double) settlementImprovement.getNumberOfWorkers() / settlementImprovement.getType().getMaximumWorkers();
        estimatedInputRatios = new HashMap<Resource, Double>();
        if (settlementImprovement.getType().getInputResourceCount() > 0) {
            Iterator<Resource> inputResourcesIterator = settlementImprovement.getType().getInputResourcesIterator();
            while (inputResourcesIterator.hasNext()) {
                Resource resource = inputResourcesIterator.next();
                int unitInputQuantity = settlementImprovement.getType().getInputQuantity(resource);
                double idealInputAmount = unitInputQuantity * settlementImprovement.getType().getMaximumMultiplier() * workerRatio;
                int actualInputAmount = settlement.getResourceQuantity(resource);
                double productionRatioForResource = actualInputAmount / idealInputAmount;
                if (productionRatioForResource < productionRatio) {
                    productionRatio = productionRatioForResource;
                }

                int estimatedInputResourceProduction = settlement.getResourceProductionFromImprovements(resource) + settlement.getResourceProductionFromTerrain(resource);
                int estimatedInputAmount = settlement.getResourceQuantity(resource) + estimatedInputResourceProduction;
                double estimatedInputRatioForResource = estimatedInputAmount / idealInputAmount;
                if (estimatedInputRatioForResource < estimatedProductionRatio) {
                    estimatedProductionRatio = estimatedInputRatioForResource;
                }
                if (estimatedInputRatioForResource < 1) {
                    estimatedInputRatios.put(resource, estimatedInputRatioForResource);
                } else {
                    estimatedInputRatios.put(resource, 1d);
                }
            }
        }
    }

    public SettlementImprovement getSettlementImprovement() {
        return settlementImprovement;
    }

    public double getProductionRatio() {
        return productionRatio;
    }

    public double getEstimatedProductionRatio() {
        return estimatedProductionRatio;
    }

    public int getIdealInputAmount(Resource resource) {
        return settlementImprovement.getType().getInputQuantity(resource) * settlementImprovement.getType().getMaximumMultiplier();
    }

    public int getEstimatedInputAmount(Resource resource) {
        return (int) (settlementImprovement.getType().getInputQuantity(resource) * settlementImprovement.getType().getMaximumMultiplier() * workerRatio * getEstimatedInputRatio(resource));
    }

    public int getInputAmount(Resource resource) {
        return (int) (settlementImprovement.getType().getInputQuantity(resource) * settlementImprovement.getType().getMaximumMultiplier() * workerRatio * productionRatio);
    }

    public int getIdealOutputAmount(Resource resource) {
        return settlementImprovement.getType().getOutputQuantity(resource) * settlementImprovement.getType().getMaximumMultiplier();
    }

    public int getEstimatedOutputAmount(Resource resource) {
        return (int) (settlementImprovement.getType().getOutputQuantity(resource) * settlementImprovement.getType().getMaximumMultiplier() * workerRatio * getEstimatedProductionRatio());
    }

    public int getOutputAmount(Resource resource) {
        return (int) (settlementImprovement.getType().getOutputQuantity(resource) * settlementImprovement.getType().getMaximumMultiplier() * workerRatio * productionRatio);
    }

    public double getEstimatedInputRatio(Resource resource) {
        if (estimatedInputRatios.containsKey(resource)) {
            return estimatedInputRatios.get(resource);
        } else {
            return 0;
        }
    }
}
