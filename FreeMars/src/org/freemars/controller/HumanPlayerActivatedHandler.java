package org.freemars.controller;

import java.util.Iterator;

import org.freemars.colonydialog.controller.CloseDialogAction;
import org.freemars.controller.action.DisplayDefeatDialogAction;
import org.freemars.controller.viewcommand.DisplayDialogCommand;
import org.freemars.earth.command.SetEarthTaxRateCommand;
import org.freemars.earth.support.DisplayFreeColonizerMessageAction;
import org.freemars.earth.support.DisplayFreeFinancialAidMessageAction;
import org.freemars.earth.support.DisplayFreeStarportMessageAction;
import org.freemars.earth.support.DisplayFreeTransporterMessageAction;
import org.freemars.mission.DisplayClearTileVegetationMissionCompletedAction;
import org.freemars.mission.DisplayClearTileVegetationMissionFailedAction;
import org.freemars.mission.DisplayExplorationMissionCompletedAction;
import org.freemars.mission.DisplayExplorationMissionFailedAction;
import org.freemars.mission.DisplayExportResourceMissionCompletedAction;
import org.freemars.mission.DisplayExportResourceMissionFailedAction;
import org.freemars.mission.DisplayPopulationMissionCompletedAction;
import org.freemars.mission.DisplayPopulationMissionFailedAction;
import org.freemars.mission.DisplaySettlementCountMissionCompletedAction;
import org.freemars.mission.DisplaySettlementCountMissionFailedAction;
import org.freemars.mission.DisplaySettlementImprovementCountMissionCompletedAction;
import org.freemars.mission.DisplaySettlementImprovementCountMissionFailedAction;
import org.freemars.mission.DisplayTileImprovementCountMissionCompletedAction;
import org.freemars.mission.DisplayTileImprovementCountMissionFailedAction;
import org.freemars.mission.ExportResourceMission;
import org.freemars.mission.WealthReward;
import org.freemars.player.FreeMarsPlayer;
import org.freemars.ui.player.MessagesDialog;
import org.freemars.ui.player.MessagesTableMouseAdapter;
import org.freemars.util.Utility;
import org.freerealm.command.SetActiveUnitCommand;
import org.freerealm.command.WealthAddCommand;
import org.freerealm.executor.CommandResult;
import org.freerealm.player.Message;
import org.freerealm.player.mission.ClearTileVegetationCountMission;
import org.freerealm.player.mission.ExplorationMission;
import org.freerealm.player.mission.Mission;
import org.freerealm.player.mission.PopulationMission;
import org.freerealm.player.mission.Reward;
import org.freerealm.player.mission.SettlementCountMission;
import org.freerealm.player.mission.SettlementImprovementCountMission;
import org.freerealm.player.mission.TileImprovementCountMission;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class HumanPlayerActivatedHandler extends ActivePlayerHandler {

    private static final int MAX_MESSAGES_DIALOG_HEIGHT = 500;

    private final FreeMarsPlayer freeMarsPlayer;

    public HumanPlayerActivatedHandler(FreeMarsPlayer freeMarsPlayer) {
        this.freeMarsPlayer = freeMarsPlayer;
    }

    @Override
    public void handleUpdate(FreeMarsController freeMarsController, CommandResult commandResult) {
        manageAutomatedUnits(freeMarsPlayer);
        if (!freeMarsPlayer.hasDeclaredIndependence()) {
            freeMarsController.assignMissions(freeMarsPlayer);
            if (freeMarsController.getFreeMarsModel().getNumberOfTurns() > 24 && freeMarsController.getFreeMarsModel().getNumberOfTurns() % 24 == 0) {
                byte currentEarthTaxRate = freeMarsPlayer.getEarthTaxRate();
                byte newEarthTaxRate = new EarthTaxRateCalculator(freeMarsController.getFreeMarsModel(), freeMarsPlayer).getTaxRate();
                if (currentEarthTaxRate != newEarthTaxRate) {
                    freeMarsController.execute(new SetEarthTaxRateCommand(freeMarsPlayer, newEarthTaxRate));
                }
            }
        }
        if (freeMarsController.getFreeMarsModel().isHumanPlayerDefeated()) {
            new DisplayDefeatDialogAction(freeMarsController).actionPerformed(null);
        } else {
            Iterator<Unit> unitsIterator = freeMarsPlayer.getUnitsIterator();
            while (unitsIterator.hasNext()) {
                Unit playerUnit = unitsIterator.next();
                if (playerUnit.getStatus() == Unit.UNIT_ACTIVE && playerUnit.getMovementPoints() != 0 && playerUnit.getAutomater() == null) {
                    freeMarsPlayer.setAutoEndTurnPossible(true);
                }
            }
            Iterator<Mission> missionsIterator = freeMarsPlayer.getMissionsIterator();
            while (missionsIterator.hasNext()) {
                Mission mission = missionsIterator.next();
                if (mission.getStatus() == Mission.STATUS_ACTIVE) {
                    mission.checkStatus();
                    if (mission.getStatus() == Mission.STATUS_FAILED) {
                        if (mission instanceof ExplorationMission) {
                            new DisplayExplorationMissionFailedAction(freeMarsController, (ExplorationMission) mission).actionPerformed(null);
                        }
                        if (mission instanceof ClearTileVegetationCountMission) {
                            new DisplayClearTileVegetationMissionFailedAction(freeMarsController, (ClearTileVegetationCountMission) mission).actionPerformed(null);
                        }
                        if (mission instanceof PopulationMission) {
                            new DisplayPopulationMissionFailedAction(freeMarsController, (PopulationMission) mission).actionPerformed(null);
                        }
                        if (mission instanceof ExportResourceMission) {
                            new DisplayExportResourceMissionFailedAction(freeMarsController, (ExportResourceMission) mission).actionPerformed(null);
                        }
                        if (mission instanceof SettlementCountMission) {
                            new DisplaySettlementCountMissionFailedAction(freeMarsController, (SettlementCountMission) mission).actionPerformed(null);
                        }
                        if (mission instanceof TileImprovementCountMission) {
                            new DisplayTileImprovementCountMissionFailedAction(freeMarsController, (TileImprovementCountMission) mission).actionPerformed(null);
                        }
                        if (mission instanceof SettlementImprovementCountMission) {
                            new DisplaySettlementImprovementCountMissionFailedAction(freeMarsController, (SettlementImprovementCountMission) mission).actionPerformed(null);
                        }
                    } else if (mission.getStatus() == Mission.STATUS_COMPLETED) {
                        Iterator<Reward> rewardIterator = mission.getRewardsIterator();
                        while (rewardIterator.hasNext()) {
                            Reward reward = rewardIterator.next();
                            if (reward instanceof WealthReward) {
                                WealthReward wealthReward = (WealthReward) reward;
                                freeMarsController.execute(new WealthAddCommand(freeMarsPlayer, wealthReward.getAmount()));
                            }
                        }
                        if (mission instanceof ExplorationMission) {
                            new DisplayExplorationMissionCompletedAction(freeMarsController, (ExplorationMission) mission).actionPerformed(null);
                        }
                        if (mission instanceof ClearTileVegetationCountMission) {
                            new DisplayClearTileVegetationMissionCompletedAction(freeMarsController, (ClearTileVegetationCountMission) mission).actionPerformed(null);
                        }
                        if (mission instanceof PopulationMission) {
                            new DisplayPopulationMissionCompletedAction(freeMarsController, (PopulationMission) mission).actionPerformed(null);
                        }
                        if (mission instanceof ExportResourceMission) {
                            new DisplayExportResourceMissionCompletedAction(freeMarsController, (ExportResourceMission) mission).actionPerformed(null);
                        }
                        if (mission instanceof SettlementCountMission) {
                            new DisplaySettlementCountMissionCompletedAction(freeMarsController, (SettlementCountMission) mission).actionPerformed(null);
                        }
                        if (mission instanceof TileImprovementCountMission) {
                            new DisplayTileImprovementCountMissionCompletedAction(freeMarsController, (TileImprovementCountMission) mission).actionPerformed(null);
                        }
                        if (mission instanceof SettlementImprovementCountMission) {
                            new DisplaySettlementImprovementCountMissionCompletedAction(freeMarsController, (SettlementImprovementCountMission) mission).actionPerformed(null);
                        }
                    }
                }
            }
            int unreadMessageCount = freeMarsPlayer.getUnreadMessageCount();
            if (unreadMessageCount > 0) {
                MessagesDialog messagesDialog = new MessagesDialog(freeMarsController.getCurrentFrame());
                messagesDialog.addMessagesTableMouseListener(new MessagesTableMouseAdapter(freeMarsController, messagesDialog));
                Iterator<Message> iterator = freeMarsPlayer.getUnreadMessagesIterator();
                while (iterator.hasNext()) {
                    Message message = iterator.next();
                    message.setRead(true);
                    messagesDialog.addMessage(message);
                }
                messagesDialog.setOKButtonAction(new CloseDialogAction(messagesDialog));
                int height = unreadMessageCount * 60 + 86;
                if (height > MAX_MESSAGES_DIALOG_HEIGHT) {
                    height = MAX_MESSAGES_DIALOG_HEIGHT;
                }
                freeMarsController.executeViewCommand(new DisplayDialogCommand(freeMarsController, messagesDialog, 670, height));
            }
            Unit playableUnit = Utility.getNextPlayableUnit(freeMarsPlayer, null);
            freeMarsController.execute(new SetActiveUnitCommand(freeMarsPlayer, playableUnit));
            if (freeMarsPlayer.canReceiveFreeStarport()) {
                new DisplayFreeStarportMessageAction(freeMarsController).actionPerformed(null);
            }
            if (freeMarsPlayer.canReceiveFreeColonizer()) {
                new DisplayFreeColonizerMessageAction(freeMarsController).actionPerformed(null);
            }
            if (freeMarsPlayer.canReceiveFreeTransporter()) {
                new DisplayFreeTransporterMessageAction(freeMarsController).actionPerformed(null);
            }
            if (freeMarsPlayer.canReceiveFreeFinancialAid()) {
                new DisplayFreeFinancialAidMessageAction(freeMarsController).actionPerformed(null);
            }
        }
    }

}
