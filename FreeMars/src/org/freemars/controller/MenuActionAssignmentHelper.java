package org.freemars.controller;

import java.util.ArrayList;
import java.util.List;
import javax.swing.Action;
import org.freemars.colony.manager.DisplayColonyManagerDialogAction;
import org.freemars.controller.action.DisplayColonyStatisticsDialogAction;
import org.freemars.controller.action.DisplayDemographicsDialogAction;
import org.freemars.controller.action.DisplayEarthTaxRateStatisticsDialogAction;
import org.freemars.controller.action.DisplayHelpContentsAction;
import org.freemars.controller.action.DisplayMapExplorationStatisticsDialogAction;
import org.freemars.controller.action.DisplayMessageHistoryDialogAction;
import org.freemars.controller.action.DisplayObjectivesDialogAction;
import org.freemars.controller.action.DisplayPopulationStatisticsDialogAction;
import org.freemars.controller.action.DisplayTreasuryStatisticsDialogAction;
import org.freemars.controller.action.DisplayUnitStatisticsDialogAction;
import org.freemars.controller.action.EndTurnAction;
import org.freemars.controller.action.MapPanelZoomLevelSetAction;
import org.freemars.controller.action.ShowGridAction;
import org.freemars.controller.action.ShowTileCoordinatesAction;
import org.freemars.controller.action.ShowTileTypesAction;
import org.freemars.controller.action.ShowVersionAction;
import org.freemars.controller.action.file.DisplayPreferencesDialogAction;
import org.freemars.controller.action.file.ExitGameAction;
import org.freemars.controller.action.file.LoadGameAction;
import org.freemars.controller.action.file.NewGameAction;
import org.freemars.controller.action.file.QuickLoadGameAction;
import org.freemars.controller.action.file.QuickSaveGameAction;
import org.freemars.controller.action.file.QuitToMainMenuAction;
import org.freemars.controller.action.file.SaveGameAction;
import org.freemars.controller.listener.EarthTaxRateLabelMouseListener;
import org.freemars.controller.listener.NationLabelMouseListener;
import org.freemars.controller.listener.PopulationLabelMouseListener;
import org.freemars.controller.listener.TreasuryLabelMouseListener;
import org.freemars.diplomacy.DisplayDiplomacyDialogAction;
import org.freemars.mission.DisplayMissionsDialogAction;
import org.freemars.player.tax.DisplayTaxRateDialogAction;
import org.freemars.trade.DisplayTradeDialogAction;
import org.freemars.ui.GameFrame;
import org.freemars.unit.DisplayUnitManagerDialogAction;

/**
 *
 * @author Deniz ARIKAN
 */
public class MenuActionAssignmentHelper {

    public static void assignMenuActions(FreeMarsController freeMarsController, GameFrame gameFrame) {
        gameFrame.getMapMenu().setNewGameMenuItemAction(new NewGameAction(freeMarsController));
        gameFrame.getMapMenu().setLoadGameMenuItemAction(new LoadGameAction(freeMarsController));
        gameFrame.getMapMenu().setQuickLoadGameMenuItemAction(new QuickLoadGameAction(freeMarsController));
        gameFrame.getMapMenu().setSaveGameMenuItemAction(new SaveGameAction(freeMarsController));
        gameFrame.getMapMenu().setQuickSaveGameMenuItemAction(new QuickSaveGameAction(freeMarsController));
        gameFrame.getMapMenu().setPreferencesMenuItemAction(new DisplayPreferencesDialogAction(freeMarsController));
        gameFrame.getMapMenu().setQuitToMainMenuItemAction(new QuitToMainMenuAction(freeMarsController));
        gameFrame.getMapMenu().setExitGameMenuItemAction(new ExitGameAction(freeMarsController));
        gameFrame.getMapMenu().setDisplayDipomacyMenuItemAction(new DisplayDiplomacyDialogAction(freeMarsController));
        gameFrame.getMapMenu().setDisplayTradeMenuItemAction(new DisplayTradeDialogAction(freeMarsController));
        gameFrame.getMapMenu().setDisplayColonyManagerMenuItemAction(new DisplayColonyManagerDialogAction(freeMarsController));
        gameFrame.getMapMenu().setDisplayUnitManagerMenuItemAction(new DisplayUnitManagerDialogAction(freeMarsController));
        gameFrame.getMapMenu().setMissionsMenuItemAction(new DisplayMissionsDialogAction(freeMarsController));
        gameFrame.getMapMenu().setDisplayTaxRateMenuItemAction(new DisplayTaxRateDialogAction(freeMarsController));
        gameFrame.getMapMenu().setDisplayDemographicsMenuItemAction(new DisplayDemographicsDialogAction(freeMarsController));
        gameFrame.getMapMenu().setDisplayMessageHistoryMenuItemAction(new DisplayMessageHistoryDialogAction(freeMarsController));
        gameFrame.getMapMenu().setDisplayObjectivesMenuItemAction(new DisplayObjectivesDialogAction(freeMarsController));
        gameFrame.getMapMenu().setDeclareIndependenceMenuItemAction(freeMarsController.getAction(ActionManager.DECLARE_INDEPENDENCE_ACTION));

        gameFrame.getMapMenu().setPopulationStatisticsMenuItemAction(new DisplayPopulationStatisticsDialogAction(freeMarsController));
        gameFrame.getMapMenu().setTreasuryStatisticsMenuItemAction(new DisplayTreasuryStatisticsDialogAction(freeMarsController));
        gameFrame.getMapMenu().setEarthTaxRateStatisticsMenuItemAction(new DisplayEarthTaxRateStatisticsDialogAction(freeMarsController));
        gameFrame.getMapMenu().setColonyStatisticsMenuItemAction(new DisplayColonyStatisticsDialogAction(freeMarsController));
        gameFrame.getMapMenu().setUnitStatisticsMenuItemAction(new DisplayUnitStatisticsDialogAction(freeMarsController));
        gameFrame.getMapMenu().setMapExplorationStatisticsMenuItemAction(new DisplayMapExplorationStatisticsDialogAction(freeMarsController));

        gameFrame.getMapMenu().setToggleGridCheckBoxMenuItemAction(new ShowGridAction(freeMarsController));
        gameFrame.getMapMenu().setToggleTileCoordinatesCheckBoxMenuItemAction(new ShowTileCoordinatesAction(freeMarsController));
        gameFrame.getMapMenu().setToggleTileTypesCheckBoxMenuItemAction(new ShowTileTypesAction(freeMarsController));
        gameFrame.getMapMenu().setToggleUnitPathCheckBoxMenuItemAction(freeMarsController.getAction(ActionManager.SHOW_UNIT_PATH_ACTION));
        gameFrame.getMapMenu().setDisplayOrbitAction(freeMarsController.getAction(ActionManager.DISPLAY_ORBIT_ACTION));
        gameFrame.getMapMenu().setDisplayEarthAction(freeMarsController.getAction(ActionManager.DISPLAY_EARTH_ACTION));
        gameFrame.getMapMenu().setDisplayExpeditionaryForceAction(freeMarsController.getAction(ActionManager.DISPLAY_EXPEDITIONARY_FORCE_ACTION));
        gameFrame.getMapMenu().setMainMapZoomInMenuItemAction(freeMarsController.getAction(ActionManager.MAIN_MAP_ZOOM_IN_ACTION));
        gameFrame.getMapMenu().setMainMapZoomOutMenuItemAction(freeMarsController.getAction(ActionManager.MAIN_MAP_ZOOM_OUT_ACTION));
        gameFrame.getMapMenu().setMainMapDefaultZoomMenuItemAction(freeMarsController.getAction(ActionManager.MAIN_MAP_ZOOM_DEFAULT_ACTION));
        List<Action> actionsList = new ArrayList<Action>();
        for (int i = 0; i < 6; i++) {
            actionsList.add(new MapPanelZoomLevelSetAction(freeMarsController, i));
        }
        gameFrame.getMapMenu().setZoomLevelMenuActions(actionsList);
        gameFrame.getMapMenu().setBuildColonyMenuItemAction(freeMarsController.getAction(ActionManager.BUILD_COLONY_ACTION));
        gameFrame.getMapMenu().setDisbandUnitMenuItemAction(freeMarsController.getAction(ActionManager.DISBAND_UNIT_ACTION));
        gameFrame.getMapMenu().setSentryUnitMenuItemAction(freeMarsController.getAction(ActionManager.ORDER_SENTRY_ACTION));
        gameFrame.getMapMenu().setFortifyUnitMenuItemAction(freeMarsController.getAction(ActionManager.ORDER_FORTIFY_ACTION));
        gameFrame.getMapMenu().setClearOrdersMenuItemAction(freeMarsController.getAction(ActionManager.CLEAR_ORDERS_ACTION));
        gameFrame.getMapMenu().setBuildRoadMenuItemAction(freeMarsController.getAction(ActionManager.ORDER_BUILD_ROAD_ACTION));
        gameFrame.getMapMenu().setBuildIrrigationMenuItemAction(freeMarsController.getAction(ActionManager.ORDER_BUILD_IRRIGATION_ACTION));
        gameFrame.getMapMenu().setBuildMineMenuItemAction(freeMarsController.getAction(ActionManager.ORDER_BUILD_MINE_ACTION));

        gameFrame.getMapMenu().setDestroyAllTileImprovementsMenuItemAction(freeMarsController.getAction(ActionManager.ORDER_DESTROY_ALL_TILE_IMPROVEMENTS_ACTION));
        gameFrame.getMapMenu().setDestroyRoadsMenuItemAction(freeMarsController.getAction(ActionManager.ORDER_DESTROY_ROAD_ACTION));
        gameFrame.getMapMenu().setDestroyIrrigationMenuItemAction(freeMarsController.getAction(ActionManager.ORDER_DESTROY_IRRIGATION_ACTION));
        gameFrame.getMapMenu().setDestroyMineMenuItemAction(freeMarsController.getAction(ActionManager.ORDER_DESTROY_MINE_ACTION));

        gameFrame.getMapMenu().setClearVegetationMenuItemAction(freeMarsController.getAction(ActionManager.ORDER_CLEAR_VEGETATION_ACTION));
        gameFrame.getMapMenu().setTransformTerrainMenuItemAction(freeMarsController.getAction(ActionManager.ORDER_TRANSFORM_TERRAIN_ACTION));
        gameFrame.getMapMenu().setUnloadAllCargoMenuItemAction(freeMarsController.getAction(ActionManager.UNLOAD_ALL_CARGO_ACTION));
        gameFrame.getMapMenu().setSendUnitToOrbitMenuItemAction(freeMarsController.getAction(ActionManager.ORDER_SEND_UNIT_TO_ORBIT_ACTION));
        gameFrame.getMapMenu().setSendUnitToEarthMenuItemAction(freeMarsController.getAction(ActionManager.ORDER_SEND_UNIT_TO_EARTH_ACTION));
        gameFrame.getMapMenu().setSkipUnitMenuItemAction(freeMarsController.getAction(ActionManager.SKIP_UNIT_ACTION));
        gameFrame.getMapMenu().setAutomateUnitMenuItemAction(freeMarsController.getAction(ActionManager.ORDER_AUTOMATE_ACTION));
        gameFrame.getMapMenu().setNextUnitMenuItemAction(freeMarsController.getAction(ActionManager.NEXT_UNIT_ACTION));
        gameFrame.getMapMenu().setDisplayRenameUnitDialogMenuItemAction(freeMarsController.getAction(ActionManager.DISPLAY_RENAME_UNIT_DIALOG_ACTION));
        gameFrame.getMapMenu().setEndTurnMenuItemAction(new EndTurnAction(freeMarsController));
        gameFrame.getMapMenu().addNationLabelMouseListener(new NationLabelMouseListener(freeMarsController));
        gameFrame.getMapMenu().addPopulationLabelMouseListener(new PopulationLabelMouseListener(freeMarsController));
        gameFrame.getMapMenu().addEarthTaxRateLabelMouseListener(new EarthTaxRateLabelMouseListener(freeMarsController));
        gameFrame.getMapMenu().addTreasuryLabelMouseListener(new TreasuryLabelMouseListener(freeMarsController));
        gameFrame.getMapMenu().setDisplayHelpContentsMenuItemAction(new DisplayHelpContentsAction(freeMarsController, null));
        gameFrame.getMapMenu().setShowVersionMenuItemAction(new ShowVersionAction(freeMarsController));
    }
}
