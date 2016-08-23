package org.freemars.controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Iterator;

import javax.swing.SwingWorker;

import org.apache.log4j.Logger;
import org.freemars.ai.AIPlayer;
import org.freemars.earth.command.SetEarthTaxRateCommand;
import org.freemars.mission.WealthReward;
import org.freemars.model.FreeMarsModel;
import org.freerealm.command.SignalPlayerEndTurnCommand;
import org.freerealm.command.WealthAddCommand;
import org.freerealm.executor.CommandResult;
import org.freerealm.player.Player;
import org.freerealm.player.mission.Mission;
import org.freerealm.player.mission.Reward;

/**
 *
 * @author Deniz ARIKAN
 */
public class FreeMarsAIPlayerActivatedHandler extends ActivePlayerHandler {

    private static final Logger logger = Logger.getLogger(FreeMarsAIPlayerActivatedHandler.class);
    private final AIPlayer freeMarsAIPlayer;

    public FreeMarsAIPlayerActivatedHandler(AIPlayer freeMarsAIPlayer) {
        this.freeMarsAIPlayer = freeMarsAIPlayer;
    }

    @Override
    public void handleUpdate(final FreeMarsController freeMarsController, CommandResult commandResult) {
        final boolean displayAIPlayerProgressWindow = Boolean.valueOf(freeMarsController.getFreeMarsModel().getFreeMarsPreferences().getProperty("display_ai_player_progress_window"));
        if (freeMarsAIPlayer.getStatus() == Player.STATUS_ACTIVE) {
            if (freeMarsController.getFreeMarsModel().getMode() == FreeMarsModel.SIMULATION_MODE || !displayAIPlayerProgressWindow) {
                playAI(freeMarsController, displayAIPlayerProgressWindow);
            } else {
                SwingWorker<Void, Void> mySwingWorker = new SwingWorker<Void, Void>() {
                    @Override
                    protected Void doInBackground() throws Exception {
                        playAI(freeMarsController, displayAIPlayerProgressWindow);
                        return null;
                    }
                };
                mySwingWorker.addPropertyChangeListener(new PropertyChangeListener() {
                    @Override
                    public void propertyChange(PropertyChangeEvent evt) {
                        if (evt.getPropertyName().equals("state")) {
                            if (evt.getNewValue() == SwingWorker.StateValue.DONE && displayAIPlayerProgressWindow) {
                                AISplashDisplayer.hide();
                            }
                        }
                    }
                });
                mySwingWorker.execute();
                if (displayAIPlayerProgressWindow) {
                    AISplashDisplayer.display(freeMarsController, freeMarsAIPlayer);
                }
            }
        } else {
            String logInfo = "Status of AI player with id ";
            logInfo = logInfo + freeMarsAIPlayer.getId() + " and name \"" + freeMarsAIPlayer.getName() + "\" is ";
            if (freeMarsAIPlayer.getStatus() == Player.STATUS_PASSIVE) {
                logInfo = logInfo + "\"passive\". ";
            } else {
                logInfo = logInfo + "\"unknown(" + freeMarsAIPlayer.getStatus() + ")\". ";
            }
            logInfo = logInfo + "Auto skipping turn.";
            logger.info(logInfo);
        }
        freeMarsController.execute(new SignalPlayerEndTurnCommand(freeMarsAIPlayer));
    }

    private void playAI(FreeMarsController freeMarsController, boolean displayAIPlayerProgressWindow) {
        if (displayAIPlayerProgressWindow) {
            AISplashDisplayer.setCurrentProcessLabelText("Automated units...");
        }
        manageAutomatedUnits(freeMarsAIPlayer);
        if (displayAIPlayerProgressWindow) {
            AISplashDisplayer.setCurrentProcessLabelText("Checking mission assignments...");
        }
        if (!freeMarsAIPlayer.hasDeclaredIndependence()) {
            freeMarsController.assignMissions(freeMarsAIPlayer);
            if (freeMarsController.getFreeMarsModel().getNumberOfTurns() > 24 && freeMarsController.getFreeMarsModel().getNumberOfTurns() % 24 == 0) {
                byte currentEarthTaxRate = freeMarsAIPlayer.getEarthTaxRate();
                byte newEarthTaxRate = new EarthTaxRateCalculator(freeMarsController.getFreeMarsModel(), freeMarsAIPlayer).getTaxRate();
                if (currentEarthTaxRate != newEarthTaxRate) {
                    freeMarsController.execute(new SetEarthTaxRateCommand(freeMarsAIPlayer, newEarthTaxRate));
                }
            }
        }
        if (displayAIPlayerProgressWindow) {
            AISplashDisplayer.setCurrentProcessLabelText("Checking completed missions...");
        }
        Iterator<Mission> missionsIterator = freeMarsAIPlayer.getMissionsIterator();
        while (missionsIterator.hasNext()) {
            Mission mission = missionsIterator.next();
            if (mission.getStatus() == Mission.STATUS_ACTIVE) {
                mission.checkStatus();
                if (mission.getStatus() == Mission.STATUS_COMPLETED) {
                    Iterator<Reward> rewardIterator = mission.getRewardsIterator();
                    while (rewardIterator.hasNext()) {
                        Reward reward = rewardIterator.next();
                        if (reward instanceof WealthReward) {
                            WealthReward wealthReward = (WealthReward) reward;
                            freeMarsController.execute(new WealthAddCommand(freeMarsAIPlayer, wealthReward.getAmount()));
                        }
                    }
                }
            }
        }
        if (displayAIPlayerProgressWindow) {
            AISplashDisplayer.setCurrentProcessLabelText("Playing...");
        }
        freeMarsAIPlayer.play();
        if (displayAIPlayerProgressWindow) {
            AISplashDisplayer.setCurrentProcessLabelText("Completed");
        }
    }

}
