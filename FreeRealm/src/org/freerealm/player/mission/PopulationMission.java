package org.freerealm.player.mission;

/**
 *
 * @author Deniz ARIKAN
 */
public class PopulationMission extends AbstractMission {

    private static final String NAME = "populationMission";
    private int population;

    @Override
    public Mission clone() {
        PopulationMission clone = new PopulationMission();
        copyTo(clone);
        clone.setPopulation(population);
        return clone;
    }

    public String getMissionName() {
        return NAME;
    }

    public void checkStatus() {
        if (getPlayer().getPopulation() >= getPopulation()) {
            setStatus(Mission.STATUS_COMPLETED);
        } else {
            checkIfExpired();
        }
    }

    public int getProgressPercent() {
        if (getStatus() == Mission.STATUS_COMPLETED) {
            return 100;
        } else if (getStatus() == Mission.STATUS_FAILED) {
            return 0;
        } else {
            return (getPlayer().getPopulation() * 100) / getPopulation();
        }
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }
}
