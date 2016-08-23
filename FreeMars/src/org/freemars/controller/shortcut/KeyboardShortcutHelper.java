package org.freemars.controller.shortcut;

import java.awt.event.KeyEvent;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import org.freemars.colony.manager.DisplayColonyManagerDialogAction;
import org.freemars.controller.ActionManager;
import org.freemars.controller.FreeMarsController;
import org.freemars.controller.action.DisplayActiveUnitAction;
import org.freemars.controller.action.DisplayHelpContentsAction;
import org.freemars.controller.action.EndTurnWithEnterAction;
import org.freemars.controller.action.MoveUnitAction;
import org.freemars.controller.action.ToggleDisplayUnitPathAction;
import org.freemars.controller.action.ToggleGridAction;
import org.freemars.controller.action.ToggleTileCoordinatesAction;
import org.freemars.controller.action.file.DisplayConsoleDialogAction;
import org.freemars.controller.action.file.ExitGameAction;
import org.freemars.controller.action.file.LoadGameAction;
import org.freemars.controller.action.file.NewGameAction;
import org.freemars.controller.action.file.QuickLoadGameAction;
import org.freemars.controller.action.file.QuickSaveGameAction;
import org.freemars.controller.action.file.SaveGameAction;
import org.freemars.diplomacy.DisplayDiplomacyDialogAction;
import org.freemars.mission.DisplayMissionsDialogAction;
import org.freemars.player.tax.DisplayTaxRateDialogAction;
import org.freemars.trade.DisplayTradeDialogAction;
import org.freemars.unit.DisplayUnitManagerDialogAction;
import org.freerealm.RealmConstants;

/**
 *
 * @author Deniz ARIKAN
 */
public class KeyboardShortcutHelper {

    public static void assignKeyboardShortcuts(JComponent component, FreeMarsController freeMarsController) {
        assignFileShortcuts(component, freeMarsController);
        assignGovernmentShortcuts(component, freeMarsController);
        assignMapDisplayShortcuts(component, freeMarsController);
        assignUnitMovementShortcuts(component, freeMarsController);
        assignUnitOrderShortcuts(component, freeMarsController);
        assignEarthFlightShortcuts(component, freeMarsController);
    }

    private static void assignFileShortcuts(JComponent component, FreeMarsController freeMarsController) {
        component.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK), "NewGame");
        component.getActionMap().put("NewGame", new NewGameAction(freeMarsController));

        component.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK), "Save");
        component.getActionMap().put("Save", new SaveGameAction(freeMarsController));

        component.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_F8, 0), "QuickSave");
        component.getActionMap().put("QuickSave", new QuickSaveGameAction(freeMarsController));

        component.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_L, KeyEvent.CTRL_DOWN_MASK), "Load");
        component.getActionMap().put("Load", new LoadGameAction(freeMarsController));

        component.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_F9, 0), "QuickLoad");
        component.getActionMap().put("QuickLoad", new QuickLoadGameAction(freeMarsController));

        component.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "EndTurn");
        component.getActionMap().put("EndTurn", new EndTurnWithEnterAction(freeMarsController));

        component.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0), "DisplayHelpContents");
        component.getActionMap().put("DisplayHelpContents", new DisplayHelpContentsAction(freeMarsController, null));

        component.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_DOWN_MASK), "QuitGame");
        component.getActionMap().put("QuitGame", new ExitGameAction(freeMarsController));

        component.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_QUOTEDBL, 0), "DisplayConsole");
        component.getActionMap().put("DisplayConsole", new DisplayConsoleDialogAction(freeMarsController));
    }

    private static void assignGovernmentShortcuts(JComponent component, FreeMarsController controller) {
        component.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0), "ColonyManager");
        component.getActionMap().put("ColonyManager", new DisplayColonyManagerDialogAction(controller));

        component.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0), "UnitManager");
        component.getActionMap().put("UnitManager", new DisplayUnitManagerDialogAction(controller));

        component.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_F4, 0), "Missions");
        component.getActionMap().put("Missions", new DisplayMissionsDialogAction(controller));

        component.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0), "TradeData");
        component.getActionMap().put("TradeData", new DisplayTradeDialogAction(controller));

        component.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_F6, 0), "Diplomacy");
        component.getActionMap().put("Diplomacy", new DisplayDiplomacyDialogAction(controller));

        component.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_T, KeyEvent.CTRL_DOWN_MASK), "ColonyTaxRate");
        component.getActionMap().put("ColonyTaxRate", new DisplayTaxRateDialogAction(controller));
    }

    private static void assignMapDisplayShortcuts(JComponent component, FreeMarsController controller) {
        component.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_PLUS, 0), "ZoomIn");
        component.getInputMap().put(KeyStroke.getKeyStroke(107, 0), "ZoomIn");
        component.getActionMap().put("ZoomIn", controller.getAction(ActionManager.MAIN_MAP_ZOOM_IN_ACTION));

        component.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, 0), "ZoomOut");
        component.getInputMap().put(KeyStroke.getKeyStroke(109, 0), "ZoomOut");
        component.getActionMap().put("ZoomOut", controller.getAction(ActionManager.MAIN_MAP_ZOOM_OUT_ACTION));

        component.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_MULTIPLY, 0), "ZoomDefault");
        component.getActionMap().put("ZoomDefault", controller.getAction(ActionManager.MAIN_MAP_ZOOM_DEFAULT_ACTION));

        component.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_PLUS, KeyEvent.CTRL_DOWN_MASK), "MiniMapZoomIn");
        component.getInputMap().put(KeyStroke.getKeyStroke(107, KeyEvent.CTRL_DOWN_MASK), "MiniMapZoomIn");
        component.getActionMap().put("MiniMapZoomIn", controller.getAction(ActionManager.MINI_MAP_ZOOM_IN_ACTION));

        component.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, KeyEvent.CTRL_DOWN_MASK), "MiniMapZoomOut");
        component.getInputMap().put(KeyStroke.getKeyStroke(109, KeyEvent.CTRL_DOWN_MASK), "MiniMapZoomOut");
        component.getActionMap().put("MiniMapZoomOut", controller.getAction(ActionManager.MINI_MAP_ZOOM_OUT_ACTION));

        component.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_MULTIPLY, KeyEvent.CTRL_DOWN_MASK), "MiniMapZoomDefault");
        component.getActionMap().put("MiniMapZoomDefault", controller.getAction(ActionManager.MINI_MAP_ZOOM_DEFAULT_ACTION));

        component.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_G, KeyEvent.CTRL_DOWN_MASK), "ToggleDisplayGrid");
        component.getActionMap().put("ToggleDisplayGrid", new ToggleGridAction(controller));

        component.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_D, KeyEvent.CTRL_DOWN_MASK), "ToggleDisplayCoordinates");
        component.getActionMap().put("ToggleDisplayCoordinates", new ToggleTileCoordinatesAction(controller));

        component.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_H, KeyEvent.CTRL_DOWN_MASK), "ToggleDisplayUnitPath");
        component.getActionMap().put("ToggleDisplayUnitPath", new ToggleDisplayUnitPathAction(controller));

        component.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_C, 0), "DisplayActiveUnit");
        component.getActionMap().put("DisplayActiveUnit", new DisplayActiveUnitAction(controller));
    }

    private static void assignUnitMovementShortcuts(JComponent component, FreeMarsController controller) {
        component.getInputMap().put(KeyStroke.getKeyStroke(103, 0), "MoveNORTHWEST");
        component.getActionMap().put("MoveNORTHWEST", new MoveUnitAction(controller, RealmConstants.NORTHWEST));

        component.getInputMap().put(KeyStroke.getKeyStroke(104, 0), "MoveNORTH");
        component.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "MoveNORTH");
        component.getActionMap().put("MoveNORTH", new MoveUnitAction(controller, RealmConstants.NORTH));

        component.getInputMap().put(KeyStroke.getKeyStroke(105, 0), "MoveNORTHEAST");
        component.getActionMap().put("MoveNORTHEAST", new MoveUnitAction(controller, RealmConstants.NORTHEAST));

        component.getInputMap().put(KeyStroke.getKeyStroke(100, 0), "MoveWEST");
        component.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "MoveWEST");
        component.getActionMap().put("MoveWEST", new MoveUnitAction(controller, RealmConstants.WEST));

        component.getInputMap().put(KeyStroke.getKeyStroke(102, 0), "MoveEAST");
        component.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "MoveEAST");
        component.getActionMap().put("MoveEAST", new MoveUnitAction(controller, RealmConstants.EAST));

        component.getInputMap().put(KeyStroke.getKeyStroke(97, 0), "MoveSOUTHWEST");
        component.getActionMap().put("MoveSOUTHWEST", new MoveUnitAction(controller, RealmConstants.SOUTHWEST));

        component.getInputMap().put(KeyStroke.getKeyStroke(98, 0), "MoveSOUTH");
        component.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "MoveSOUTH");
        component.getActionMap().put("MoveSOUTH", new MoveUnitAction(controller, RealmConstants.SOUTH));

        component.getInputMap().put(KeyStroke.getKeyStroke(99, 0), "MoveSOUTHEAST");
        component.getActionMap().put("MoveSOUTHEAST", new MoveUnitAction(controller, RealmConstants.SOUTHEAST));
    }

    private static void assignUnitOrderShortcuts(JComponent component, FreeMarsController controller) {
        component.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_N, 0), "NextUnit");
        component.getActionMap().put("NextUnit", controller.getAction(ActionManager.NEXT_UNIT_ACTION));

        component.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "SkipUnit");
        component.getActionMap().put("SkipUnit", controller.getAction(ActionManager.SKIP_UNIT_ACTION));

        component.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "DisbandUnit");
        component.getActionMap().put("DisbandUnit", controller.getAction(ActionManager.DISBAND_UNIT_ACTION));

        component.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_B, 0), "BuildColony");
        component.getActionMap().put("BuildColony", controller.getAction(ActionManager.BUILD_COLONY_ACTION));

        component.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_F, 0), "Fortify");
        component.getActionMap().put("Fortify", controller.getAction(ActionManager.ORDER_FORTIFY_ACTION));

        component.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0), "Sentry");
        component.getActionMap().put("Sentry", controller.getAction(ActionManager.ORDER_SENTRY_ACTION));

        component.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_R, 0), "BuildRoad");
        component.getActionMap().put("BuildRoad", controller.getAction(ActionManager.ORDER_BUILD_ROAD_ACTION));

        component.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_V, 0), "ClearVegetation");
        component.getActionMap().put("ClearVegetation", controller.getAction(ActionManager.ORDER_CLEAR_VEGETATION_ACTION));

        component.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_T, 0), "TransformTerrain");
        component.getActionMap().put("TransformTerrain", controller.getAction(ActionManager.ORDER_TRANSFORM_TERRAIN_ACTION));

        component.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_I, 0), "BuildIrrigation");
        component.getInputMap().put(KeyStroke.getKeyStroke("İ"), "BuildIrrigation");
        component.getActionMap().put("BuildIrrigation", controller.getAction(ActionManager.ORDER_BUILD_IRRIGATION_ACTION));

        component.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0), "Automate");
        component.getActionMap().put("Automate", controller.getAction(ActionManager.ORDER_AUTOMATE_ACTION));

        component.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_M, 0), "BuildMine");
        component.getActionMap().put("BuildMine", controller.getAction(ActionManager.ORDER_BUILD_MINE_ACTION));

        component.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0), "DestroyAllTileImprovements");
        component.getActionMap().put("DestroyAllTileImprovements", controller.getAction(ActionManager.ORDER_DESTROY_ALL_TILE_IMPROVEMENTS_ACTION));

        component.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_U, KeyEvent.CTRL_DOWN_MASK), "UnloadAllCargo");
        component.getActionMap().put("UnloadAllCargo", controller.getAction(ActionManager.UNLOAD_ALL_CARGO_ACTION));

        component.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.CTRL_DOWN_MASK), "DisplayRenameUnitDialog");
        component.getActionMap().put("DisplayRenameUnitDialog", controller.getAction(ActionManager.DISPLAY_RENAME_UNIT_DIALOG_ACTION));
    }

    private static void assignEarthFlightShortcuts(JComponent component, FreeMarsController controller) {
        component.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_O, 0), "DisplayOrbit");
        component.getActionMap().put("DisplayOrbit", controller.getAction(ActionManager.DISPLAY_ORBIT_ACTION));

        component.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_E, 0), "DisplayEarth");
        component.getActionMap().put("DisplayEarth", controller.getAction(ActionManager.DISPLAY_EARTH_ACTION));

        component.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK), "SendUnitToOrbit");
        component.getActionMap().put("SendUnitToOrbit", controller.getAction(ActionManager.ORDER_SEND_UNIT_TO_ORBIT_ACTION));

        component.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.CTRL_DOWN_MASK), "SendUnitToEarth");
        component.getActionMap().put("SendUnitToEarth", controller.getAction(ActionManager.ORDER_SEND_UNIT_TO_EARTH_ACTION));
    }
}
