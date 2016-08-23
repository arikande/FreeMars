package org.freerealm.settlement.improvement;

import org.freerealm.resource.ResourceProducer;
import org.freerealm.settlement.SettlementBuildable;

/**
 *
 * @author Deniz ARIKAN
 */
public interface SettlementImprovementType extends SettlementBuildable, ResourceProducer {

    public boolean isResourceProducer();

    public int getMaximumWorkers();

    public void setMaximumWorkers(int maximumWorkers);
}
