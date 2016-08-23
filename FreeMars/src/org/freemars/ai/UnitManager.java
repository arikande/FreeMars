package org.freemars.ai;

import org.apache.log4j.Logger;
import org.freemars.controller.FreeMarsController;

/**
 *
 * @author Deniz ARIKAN
 */
public class UnitManager {

    private static final Logger logger = Logger.getLogger(UnitManager.class);
    
    private final ScoutManager scoutManager;
    private final ColonizerManager colonizerManager;
    private final EngineerManager engineerManager;
    private final TransporterManager transporterManager;
    private final SpaceshipManager spaceshipManager;
    private final MilitiaManager militiaManager;

    public UnitManager(FreeMarsController freeMarsController, AIPlayer aiPlayer, DecisionModel decisionModel) {
        scoutManager = new ScoutManager(freeMarsController, aiPlayer);
        colonizerManager = new ColonizerManager(freeMarsController, aiPlayer);
        engineerManager = new EngineerManager(freeMarsController, aiPlayer);
        transporterManager = new TransporterManager(freeMarsController, aiPlayer);
        spaceshipManager = new SpaceshipManager(freeMarsController, aiPlayer, decisionModel);
        militiaManager = new MilitiaManager(freeMarsController, aiPlayer, decisionModel);
    }

    public void manage() {
        scoutManager.manage();
        logger.info("Managed scouts.");
        colonizerManager.manage();
        logger.info("Managed colonizers.");
        engineerManager.manage();
        logger.info("Managed engineers.");
        transporterManager.manage();
        logger.info("Managed transporters.");
        spaceshipManager.manage();
        logger.info("Managed spaceships.");
        militiaManager.manage();
    }
}
