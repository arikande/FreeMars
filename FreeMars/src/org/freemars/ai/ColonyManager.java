package org.freemars.ai;

import java.util.Iterator;
import org.freemars.colony.FreeMarsColony;
import org.freemars.controller.FreeMarsController;
import org.freerealm.settlement.Settlement;

/**
 *
 * @author Deniz ARIKAN
 */
public class ColonyManager {

    private final AIPlayer freeMarsAIPlayer;
    private final ColonyWorkForceManager colonyWorkForceManager;
    private final ColonyProductionManager colonyProductionManager;

    public ColonyManager(FreeMarsController freeMarsController, AIPlayer freeMarsAIPlayer) {
        this.freeMarsAIPlayer = freeMarsAIPlayer;
        colonyWorkForceManager = new ColonyWorkForceManager(freeMarsController, freeMarsAIPlayer);
        colonyProductionManager = new ColonyProductionManager(freeMarsController, freeMarsAIPlayer);
    }

    public void manage() throws Exception {
        Iterator<Settlement> iterator = freeMarsAIPlayer.getSettlementManager().getSettlementsIterator();
        while (iterator.hasNext()) {
            manageColony((FreeMarsColony) iterator.next());
        }
    }

    private void manageColony(FreeMarsColony freeMarsColony) throws Exception {
        colonyWorkForceManager.manage(freeMarsColony);
        colonyProductionManager.manage(freeMarsColony);
    }
}
