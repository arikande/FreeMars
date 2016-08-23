package org.freerealm.settlement.improvement;

/**
 *
 * @author Deniz ARIKAN
 */
public class SettlementImprovement {

    private SettlementImprovementType type;
    private boolean enabled;
    private int numberOfWorkers;

    public SettlementImprovement() {
        setEnabled(true);
    }

    @Override
    public String toString() {
        return getType().getName();
    }

    public SettlementImprovementType getType() {
        return type;
    }

    public void setType(SettlementImprovementType type) {
        this.type = type;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getNumberOfWorkers() {
        return numberOfWorkers;
    }

    public void setNumberOfWorkers(int numberOfWorkers) {
        this.numberOfWorkers = numberOfWorkers;
    }
}
