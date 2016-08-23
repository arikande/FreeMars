package org.freerealm.executor.order;

import org.freerealm.Realm;
import org.freerealm.command.SetContainerPopulationCommand;
import org.freerealm.command.SetSettlementPopulationCommand;
import org.freerealm.property.ContainerProperty;
import org.freerealm.settlement.Settlement;

/**
 *
 * @author Deniz ARIKAN
 */
public class UnloadAllPopulationOrder extends AbstractOrder {

    private static final String NAME = "UnloadAllPopulationOrder";

    public UnloadAllPopulationOrder(Realm realm) {
        super(realm);
        setSymbol("U");
    }

    @Override
    public boolean isExecutable() {
        return true;
    }

    @Override
    public void execute() {
        Settlement settlement = getRealm().getTile(getUnit().getCoordinate()).getSettlement();
        if (settlement != null) {
            ContainerProperty containerProperty = (ContainerProperty) getUnit().getType().getProperty("ContainerProperty");
            if (containerProperty != null) {
                int population = getUnit().getContainedPopulation();
                if (population > 0) {
                    int weightPerCitizen = Integer.parseInt(getRealm().getProperty("weight_per_citizen"));
                    getExecutor().execute(new SetContainerPopulationCommand(getUnit(), 0, weightPerCitizen));
                    getExecutor().execute(new SetSettlementPopulationCommand(settlement, settlement.getPopulation() + population));
                }
            }
        }
        setComplete(true);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public int getRemainingTurns() {
        return -1;
    }
}
