package org.freemars.controller;

import java.util.HashMap;
import java.util.Random;

import org.freemars.ai.ExpeditionaryForcePlayer;
import org.freemars.controller.handler.PostCommandHandler;
import org.freemars.earth.command.SetExpeditionaryForceUnitsCommand;
import org.freerealm.command.SignalPlayerEndTurnCommand;
import org.freerealm.executor.CommandResult;
import org.freerealm.player.Player;
import org.freerealm.unit.FreeRealmUnitType;

/**
 *
 * @author Deniz ARIKAN
 */
public class ExpeditionaryForcePlayerActivatedHandler implements PostCommandHandler {

    private static final int EXPEDITIONARY_FORCE_FIRST_UPDATE_TURN = 40;
    private static final int EXPEDITIONARY_FORCE_UPDATE_INTERVAL = 20;

    private final ExpeditionaryForcePlayer expeditionaryForcePlayer;
    private Random randomGenerator;

    public ExpeditionaryForcePlayerActivatedHandler(ExpeditionaryForcePlayer expeditionaryForcePlayer) {
        this.expeditionaryForcePlayer = expeditionaryForcePlayer;
    }

    public void handleUpdate(FreeMarsController freeMarsController, CommandResult commandResult) {
        if (expeditionaryForcePlayer.getStatus() == Player.STATUS_ACTIVE) {
            expeditionaryForcePlayer.play();
        } else if (expeditionaryForcePlayer.getStatus() == Player.STATUS_PASSIVE) {
            if (freeMarsController.getFreeMarsModel().getNumberOfTurns() >= EXPEDITIONARY_FORCE_FIRST_UPDATE_TURN && freeMarsController.getFreeMarsModel().getNumberOfTurns() % EXPEDITIONARY_FORCE_UPDATE_INTERVAL == 0) {
                HashMap<FreeRealmUnitType, Integer> expeditionaryForceUnits = new HashMap<FreeRealmUnitType, Integer>();
                expeditionaryForceUnits.put(freeMarsController.getFreeMarsModel().getRealm().getUnitTypeManager().getUnitType("Infantry"), getInfantryCount(freeMarsController));
                expeditionaryForceUnits.put(freeMarsController.getFreeMarsModel().getRealm().getUnitTypeManager().getUnitType("LGT"), getLGTCount(freeMarsController));
                expeditionaryForceUnits.put(freeMarsController.getFreeMarsModel().getRealm().getUnitTypeManager().getUnitType("Mech"), getMechCount(freeMarsController));
                freeMarsController.execute(new SetExpeditionaryForceUnitsCommand(freeMarsController, expeditionaryForcePlayer, expeditionaryForceUnits));
            }
        }
        freeMarsController.execute(new SignalPlayerEndTurnCommand(expeditionaryForcePlayer));
    }

    private int getInfantryCount(FreeMarsController freeMarsController) {
        int minimum = (freeMarsController.getFreeMarsModel().getNumberOfTurns() / 20) + ExpeditionaryForcePlayer.BASE_INFANTRY_COUNT;
        randomGenerator = new Random();
        int returnValue = minimum + randomGenerator.nextInt(3);
        return returnValue <= ExpeditionaryForcePlayer.MAX_INFANTRY_COUNT ? returnValue : ExpeditionaryForcePlayer.MAX_INFANTRY_COUNT;
    }

    private int getMechCount(FreeMarsController freeMarsController) {
        int minimum = (freeMarsController.getFreeMarsModel().getNumberOfTurns() / 20) + ExpeditionaryForcePlayer.BASE_MECH_COUNT;
        randomGenerator = new Random();
        int returnValue = minimum + randomGenerator.nextInt(3);
        return returnValue <= ExpeditionaryForcePlayer.MAX_MECH_COUNT ? returnValue : ExpeditionaryForcePlayer.MAX_MECH_COUNT;
    }

    private int getLGTCount(FreeMarsController freeMarsController) {
        int minimum = (freeMarsController.getFreeMarsModel().getNumberOfTurns() / 30) + ExpeditionaryForcePlayer.BASE_LGT_COUNT;
        randomGenerator = new Random();
        int returnValue = minimum + randomGenerator.nextInt(2);
        return returnValue <= ExpeditionaryForcePlayer.MAX_LGT_COUNT ? returnValue : ExpeditionaryForcePlayer.MAX_LGT_COUNT;
    }
}
