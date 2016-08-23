package org.freemars.ai;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.freemars.player.FreeMarsPlayer;
import org.freerealm.Realm;
import org.freerealm.player.Player;
import org.freerealm.settlement.Settlement;
import org.freerealm.settlement.improvement.SettlementImprovementType;

/**
 *
 * @author Deniz ARIKAN
 */
public class AIPlayer extends FreeMarsPlayer {

    private static final Logger logger = Logger.getLogger(AIPlayer.class);
    private static final int DEFAULT_RESERVE_WEALTH = 20000;
    private static final double DEFAULT_BUY_PRODUCTION_FOR_100_POPULATION_RATIO = 0.001;
    private static final double DEFAULT_SPACESHIP_FINANCE_COLONISTS_RATIO = 0.001;
    private static final int DEFAULT_MAXIMUM_TOTAL_FERTILIZER_IN_COLONIES = 5000;
    private static final double DEFAULT_GAUSS_RIFLE_BUY_RATIO = 0.001;
    private static final int DEFAULT_COLONY_POPULATION_FOR_MILITIA_GARRISON = 200;

    private DecisionModel decisionModel;
    private Properties properties;

    public AIPlayer(Realm realm) {
        super(realm);
    }

    public void play() {
        try {
            if (getStatus() == Player.STATUS_ACTIVE) {
                StringBuilder log = new StringBuilder();
                log.append("AI Player with id ").append(getId()).append(" and name \"").append(getName()).append("\" will play turn ");
                log.append(getRealm().getNumberOfTurns()).append(".");
                logger.info(log);
                long playStart = System.currentTimeMillis();
                getDecisionModel().play();
                long playTime = System.currentTimeMillis() - playStart;
                log.setLength(0);
                log.append("AI Player with id ").append(getId()).append(" and name \"").append(getName()).append("\" has played turn ");
                log.append(getRealm().getNumberOfTurns()).append(" in ").append(playTime).append(" miliseconds.");
                logger.info(log);
            }
        } catch (Exception exception) {
            String logInfo = "Exception while AI Player with id " + getId() + " and name \"" + getName() + "\" is playing turn " + getRealm().getNumberOfTurns() + ".";
            logger.error(logInfo);
            logger.error(exception);
            logger.error("Exception message :");
            logger.error(exception.getMessage());
            System.out.println("**************************************************");
            System.out.println("EXCEPTION EXCEPTION EXCEPTION EXCEPTION EXCEPTION ");
            System.out.println("**************************************************");
            System.exit(0);
        }
    }

    public DecisionModel getDecisionModel() {
        return decisionModel;
    }

    public void setDecisionModel(DecisionModel decisionModel) {
        this.decisionModel = decisionModel;
    }

    List<Settlement> getSettlementsBuildingImprovementOfType(Integer typeId) {
        List<Settlement> settlementsBuildingImprovementOfType = new ArrayList<Settlement>();
        Iterator<Settlement> iterator = getSettlementsIterator();
        while (iterator.hasNext()) {
            Settlement settlement = iterator.next();
            if (settlement.getCurrentProduction() != null && settlement.getCurrentProduction() instanceof SettlementImprovementType) {
                SettlementImprovementType settlementImprovementType = (SettlementImprovementType) settlement.getCurrentProduction();
                if (settlementImprovementType.getId() == typeId) {
                    settlementsBuildingImprovementOfType.add(settlement);
                }
            }
        }
        return settlementsBuildingImprovementOfType;
    }

    public Properties getAIProperties() {
        return properties;
    }

    public void setAIProperties(Properties aiProperties) {
        this.properties = aiProperties;
    }

    public int getReserveWealth() {
        if (properties.containsKey("reserve_wealth")) {
            return Integer.parseInt(properties.getProperty("reserve_wealth"));
        }
        return DEFAULT_RESERVE_WEALTH;
    }

    public double getBuyProductionFor100PopulationRatio() {
        if (properties.containsKey("buy_production_for_100_population_ratio")) {
            return Double.parseDouble(properties.getProperty("buy_production_for_100_population_ratio"));
        }
        return DEFAULT_BUY_PRODUCTION_FOR_100_POPULATION_RATIO;
    }

    public double getSpaceshipFinanceColonistsRatio() {
        if (properties.containsKey("spaceship_finance_colonists_ratio")) {
            return Double.parseDouble(properties.getProperty("spaceship_finance_colonists_ratio"));
        }
        return DEFAULT_SPACESHIP_FINANCE_COLONISTS_RATIO;
    }

    public int getMaximumTotalFertilizerInColonies() {
        if (properties.containsKey("maximum_total_fertilizer_in_colonies")) {
            return Integer.parseInt(properties.getProperty("maximum_total_fertilizer_in_colonies"));
        }
        return DEFAULT_MAXIMUM_TOTAL_FERTILIZER_IN_COLONIES;
    }

    public double getGaussRifleBuyRatio() {
        if (properties.containsKey("gauss_rifle_buy_ratio")) {
            return Double.parseDouble(properties.getProperty("gauss_rifle_buy_ratio"));
        }
        return DEFAULT_GAUSS_RIFLE_BUY_RATIO;
    }

    public int getColonyPopulationForMilitiaGarrison() {
        if (properties.containsKey("colony_population_for_militia_garrison")) {
            return Integer.parseInt(properties.getProperty("colony_population_for_militia_garrison"));
        }
        return DEFAULT_COLONY_POPULATION_FOR_MILITIA_GARRISON;
    }

}
