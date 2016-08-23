package org.freemars.controller;

import javax.swing.AbstractAction;
import org.freemars.controller.action.AutomateUnitAction;
import org.freemars.controller.action.BuildColonyAction;
import org.freemars.controller.action.DeclareIndependenceAction;
import org.freemars.controller.action.DisbandUnitAction;
import org.freemars.controller.action.DisplayEarthAction;
import org.freemars.controller.action.DisplayExpeditionaryForceAction;
import org.freemars.controller.action.DisplayOrbitAction;
import org.freemars.controller.action.DisplayRenameUnitDialogAction;
import org.freemars.controller.action.MainMapZoomDefaultAction;
import org.freemars.controller.action.MainMapZoomInAction;
import org.freemars.controller.action.MainMapZoomOutAction;
import org.freemars.controller.action.MiniMapDefaultZoomAction;
import org.freemars.controller.action.MiniMapZoomInAction;
import org.freemars.controller.action.MiniMapZoomOutAction;
import org.freemars.controller.action.NextUnitAction;
import org.freemars.controller.action.SendUnitToEarthAction;
import org.freemars.controller.action.SendUnitToOrbitAction;
import org.freemars.controller.action.ShowUnitPathAction;
import org.freemars.controller.action.SkipUnitAction;
import org.freemars.controller.action.UnloadAllCargoAction;
import org.freemars.controller.action.UnloadAllColonistsAction;
import org.freemars.controller.action.order.BuildIrrigationAction;
import org.freemars.controller.action.order.BuildMineAction;
import org.freemars.controller.action.order.BuildRoadAction;
import org.freemars.controller.action.order.ClearOrdersAction;
import org.freemars.controller.action.order.ClearVegetationAction;
import org.freemars.controller.action.order.DestroyAllTileImprovementsAction;
import org.freemars.controller.action.order.DestroyIrrigationAction;
import org.freemars.controller.action.order.DestroyMineAction;
import org.freemars.controller.action.order.DestroyRoadAction;
import org.freemars.controller.action.order.OrderFortifyAction;
import org.freemars.controller.action.order.OrderSentryAction;
import org.freemars.controller.action.order.TransformTerrainAction;
import org.freemars.model.FreeMarsModel;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class ActionManager {

    private final FreeMarsController controller;
    public static final int MAIN_MAP_ZOOM_IN_ACTION = 0;
    public static final int MAIN_MAP_ZOOM_OUT_ACTION = 1;
    public static final int MAIN_MAP_ZOOM_DEFAULT_ACTION = 2;
    public static final int MINI_MAP_ZOOM_IN_ACTION = 3;
    public static final int MINI_MAP_ZOOM_OUT_ACTION = 4;
    public static final int MINI_MAP_ZOOM_DEFAULT_ACTION = 5;
    public static final int SKIP_UNIT_ACTION = 6;
    public static final int NEXT_UNIT_ACTION = 7;
    public static final int DISBAND_UNIT_ACTION = 8;
    public static final int BUILD_COLONY_ACTION = 9;
    public static final int SHOW_UNIT_PATH_ACTION = 10;
    public static final int CLEAR_ORDERS_ACTION = 11;
    public static final int ORDER_FORTIFY_ACTION = 12;
    public static final int ORDER_SENTRY_ACTION = 13;
    public static final int ORDER_BUILD_ROAD_ACTION = 14;
    public static final int ORDER_BUILD_IRRIGATION_ACTION = 15;
    public static final int ORDER_BUILD_MINE_ACTION = 16;
    public static final int ORDER_DESTROY_ALL_TILE_IMPROVEMENTS_ACTION = 17;
    public static final int ORDER_DESTROY_ROAD_ACTION = 18;
    public static final int ORDER_DESTROY_IRRIGATION_ACTION = 19;
    public static final int ORDER_DESTROY_MINE_ACTION = 20;
    public static final int DISPLAY_RENAME_UNIT_DIALOG_ACTION = 21;
    public static final int DISPLAY_EARTH_ACTION = 22;
    public static final int DISPLAY_EXPEDITIONARY_FORCE_ACTION = 23;
    public static final int DISPLAY_ORBIT_ACTION = 24;
    public static final int ORDER_SEND_UNIT_TO_ORBIT_ACTION = 25;
    public static final int ORDER_SEND_UNIT_TO_EARTH_ACTION = 26;
    public static final int ORDER_CLEAR_VEGETATION_ACTION = 27;
    public static final int ORDER_TRANSFORM_TERRAIN_ACTION = 28;
    public static final int DECLARE_INDEPENDENCE_ACTION = 29;
    public static final int UNLOAD_ALL_CARGO_ACTION = 30;
    public static final int UNLOAD_ALL_COLONISTS_ACTION = 31;
    public static final int ORDER_AUTOMATE_ACTION = 32;
    private MainMapZoomInAction mainMapZoomInAction;
    private MainMapZoomOutAction mainMapZoomOutAction;
    private AbstractAction mainMapZoomDefaultAction;
    private MiniMapZoomInAction miniMapZoomInAction;
    private MiniMapZoomOutAction miniMapZoomOutAction;
    private AbstractAction miniMapZoomDefaultAction;
    private SkipUnitAction skipUnitAction;
    private AutomateUnitAction automateUnitAction;
    private NextUnitAction nextUnitAction;
    private DisplayRenameUnitDialogAction displayRenameUnitDialogAction;
    private DisbandUnitAction disbandUnitAction;
    private BuildColonyAction buildCityAction;
    private AbstractAction showUnitPathAction;
    private ClearOrdersAction clearOrdersAction;
    private OrderFortifyAction orderFortifyAction;
    private OrderSentryAction orderSentryAction;
    private BuildRoadAction buildRoadAction;
    private BuildIrrigationAction buildIrrigationAction;
    private BuildMineAction buildMineAction;
    private DestroyAllTileImprovementsAction destroyAllTileImprovementsAction;
    private DestroyRoadAction destroyRoadAction;
    private DestroyIrrigationAction destroyIrrigationAction;
    private DestroyMineAction destroyMineAction;
    private AbstractAction displayOrbitAction;
    private AbstractAction displayEarthAction;
    private AbstractAction displayExpeditionaryForceAction;
    private SendUnitToOrbitAction sendUnitToOrbitAction;
    private SendUnitToEarthAction sendUnitToEarthAction;
    private ClearVegetationAction clearVegetationAction;
    private TransformTerrainAction transformTerrainAction;
    private DeclareIndependenceAction declareIndependenceAction;
    private UnloadAllCargoAction unloadAllCargoAction;
    private UnloadAllColonistsAction unloadAllColonistsAction;

    protected ActionManager(FreeMarsController controller) {
        this.controller = controller;
    }

    protected AbstractAction getAction(int actionId) {
        switch (actionId) {
            case MAIN_MAP_ZOOM_IN_ACTION:
                return getMainMapZoomInAction();
            case MAIN_MAP_ZOOM_OUT_ACTION:
                return getMainMapZoomOutAction();
            case MAIN_MAP_ZOOM_DEFAULT_ACTION:
                return getMainMapZoomDefaultAction();
            case MINI_MAP_ZOOM_IN_ACTION:
                return getMiniMapZoomInAction();
            case MINI_MAP_ZOOM_OUT_ACTION:
                return getMiniMapZoomOutAction();
            case MINI_MAP_ZOOM_DEFAULT_ACTION:
                return getMiniMapZoomDefaultAction();
            case SKIP_UNIT_ACTION:
                return getSkipUnitAction();
            case NEXT_UNIT_ACTION:
                return getNextUnitAction();
            case DISPLAY_RENAME_UNIT_DIALOG_ACTION:
                return getDisplayRenameUnitDialogAction();
            case DISBAND_UNIT_ACTION:
                return getDisbandUnitAction();
            case BUILD_COLONY_ACTION:
                return getBuildColonyAction();
            case SHOW_UNIT_PATH_ACTION:
                return getShowUnitPathAction();
            case CLEAR_ORDERS_ACTION:
                return getClearOrdersAction();
            case ORDER_FORTIFY_ACTION:
                return getOrderFortifyAction();
            case ORDER_SENTRY_ACTION:
                return getOrderSentryAction();
            case ORDER_BUILD_ROAD_ACTION:
                return getBuildRoadAction();
            case ORDER_BUILD_IRRIGATION_ACTION:
                return getBuildIrrigationAction();
            case ORDER_BUILD_MINE_ACTION:
                return getBuildMineAction();
            case ORDER_DESTROY_ALL_TILE_IMPROVEMENTS_ACTION:
                return getDestroyAllTileImprovementsAction();
            case ORDER_DESTROY_ROAD_ACTION:
                return getDestroyRoadAction();
            case ORDER_DESTROY_IRRIGATION_ACTION:
                return getDestroyIrrigationAction();
            case ORDER_DESTROY_MINE_ACTION:
                return getDestroyMineAction();
            case DISPLAY_ORBIT_ACTION:
                return getDisplayOrbitAction();
            case DISPLAY_EARTH_ACTION:
                return getDisplayEarthAction();
            case DISPLAY_EXPEDITIONARY_FORCE_ACTION:
                return getDisplayExpeditionaryForceAction();
            case ORDER_SEND_UNIT_TO_ORBIT_ACTION:
                return getSendUnitToOrbitAction();
            case ORDER_SEND_UNIT_TO_EARTH_ACTION:
                return getSendUnitToEarthAction();
            case ORDER_CLEAR_VEGETATION_ACTION:
                return getClearVegetationAction();
            case ORDER_TRANSFORM_TERRAIN_ACTION:
                return getTransformTerrainAction();
            case DECLARE_INDEPENDENCE_ACTION:
                return getDeclareIndependenceAction();
            case UNLOAD_ALL_CARGO_ACTION:
                return getUnloadAllCargoAction();
            case UNLOAD_ALL_COLONISTS_ACTION:
                return getUnloadAllColonistsAction();
            case ORDER_AUTOMATE_ACTION:
                return getAutomateUnitAction();
        }
        return null;
    }

    public void refresh() {
        getMainMapZoomInAction().setEnabled(getMainMapZoomInAction().checkEnabled());
        getMainMapZoomOutAction().setEnabled(getMainMapZoomOutAction().checkEnabled());
        getMiniMapZoomInAction().setEnabled(getMiniMapZoomInAction().checkEnabled());
        getMiniMapZoomOutAction().setEnabled(getMiniMapZoomOutAction().checkEnabled());
        getSendUnitToEarthAction().setEnabled(getSendUnitToEarthAction().checkEnabled());
        getSendUnitToOrbitAction().setEnabled(getSendUnitToOrbitAction().checkEnabled());
        getClearVegetationAction().setEnabled(getClearVegetationAction().checkEnabled());
        getAutomateUnitAction().setEnabled(getAutomateUnitAction().checkEnabled());
        getClearOrdersAction().setEnabled(getClearOrdersAction().checkEnabled());
        getBuildRoadAction().setEnabled(getBuildRoadAction().checkEnabled());
        getBuildIrrigationAction().setEnabled(getBuildIrrigationAction().checkEnabled());
        getBuildMineAction().setEnabled(getBuildMineAction().checkEnabled());
        getDisbandUnitAction().setEnabled(getDisbandUnitAction().checkEnabled());
        getSkipUnitAction().setEnabled(getSkipUnitAction().checkEnabled());
        getNextUnitAction().setEnabled(getNextUnitAction().checkEnabled());
        getOrderFortifyAction().setEnabled(getOrderFortifyAction().checkEnabled());
        getOrderSentryAction().setEnabled(getOrderSentryAction().checkEnabled());
        getDisplayRenameUnitDialogAction().setEnabled(getDisplayRenameUnitDialogAction().checkEnabled());
        getTransformTerrainAction().setEnabled(getTransformTerrainAction().checkEnabled());
        getBuildColonyAction().setEnabled(getBuildColonyAction().checkEnabled());
        getUnloadAllCargoAction().setEnabled(getUnloadAllCargoAction().checkEnabled());
        getUnloadAllColonistsAction().setEnabled(getUnloadAllColonistsAction().checkEnabled());
        getDeclareIndependenceAction().setEnabled(getDeclareIndependenceAction().checkEnabled());
        getDestroyAllTileImprovementsAction().setEnabled(getDestroyAllTileImprovementsAction().checkEnabled());
        getDestroyRoadAction().setEnabled(getDestroyRoadAction().checkEnabled());
        getDestroyIrrigationAction().setEnabled(getDestroyIrrigationAction().checkEnabled());
        getDestroyMineAction().setEnabled(getDestroyMineAction().checkEnabled());
    }

    protected boolean isActivateEnabledForUnit(FreeMarsModel model, Unit unit) {
        if (unit == null) {
            return false;
        }
        if (unit.getCurrentOrder() != null) {
            return false;
        }
        if (unit.equals(model.getActivePlayer().getActiveUnit())) {
            return false;
        }
        return true;
    }

    private MainMapZoomInAction getMainMapZoomInAction() {
        if (mainMapZoomInAction == null) {
            mainMapZoomInAction = new MainMapZoomInAction(controller);
        }
        return mainMapZoomInAction;
    }

    private MainMapZoomOutAction getMainMapZoomOutAction() {
        if (mainMapZoomOutAction == null) {
            mainMapZoomOutAction = new MainMapZoomOutAction(controller);
        }
        return mainMapZoomOutAction;
    }

    private AbstractAction getMainMapZoomDefaultAction() {
        if (mainMapZoomDefaultAction == null) {
            mainMapZoomDefaultAction = new MainMapZoomDefaultAction(controller);
        }
        return mainMapZoomDefaultAction;
    }

    private MiniMapZoomInAction getMiniMapZoomInAction() {
        if (miniMapZoomInAction == null) {
            miniMapZoomInAction = new MiniMapZoomInAction(controller);
        }
        return miniMapZoomInAction;
    }

    private MiniMapZoomOutAction getMiniMapZoomOutAction() {
        if (miniMapZoomOutAction == null) {
            miniMapZoomOutAction = new MiniMapZoomOutAction(controller);
        }
        return miniMapZoomOutAction;
    }

    private AbstractAction getMiniMapZoomDefaultAction() {
        if (miniMapZoomDefaultAction == null) {
            miniMapZoomDefaultAction = new MiniMapDefaultZoomAction(controller);
        }
        return miniMapZoomDefaultAction;
    }

    private SkipUnitAction getSkipUnitAction() {
        if (skipUnitAction == null) {
            skipUnitAction = new SkipUnitAction(controller, null);
        }
        return skipUnitAction;
    }

    private NextUnitAction getNextUnitAction() {
        if (nextUnitAction == null) {
            nextUnitAction = new NextUnitAction(controller, null);
        }
        return nextUnitAction;
    }

    private DisplayRenameUnitDialogAction getDisplayRenameUnitDialogAction() {
        if (displayRenameUnitDialogAction == null) {
            displayRenameUnitDialogAction = new DisplayRenameUnitDialogAction(controller, null);
        }
        return displayRenameUnitDialogAction;
    }

    private DisbandUnitAction getDisbandUnitAction() {
        if (disbandUnitAction == null) {
            disbandUnitAction = new DisbandUnitAction(controller, null);
        }
        return disbandUnitAction;
    }

    private BuildColonyAction getBuildColonyAction() {
        if (buildCityAction == null) {
            buildCityAction = new BuildColonyAction(controller, null);
        }
        return buildCityAction;
    }

    private AbstractAction getShowUnitPathAction() {
        if (showUnitPathAction == null) {
            showUnitPathAction = new ShowUnitPathAction(controller);
        }
        return showUnitPathAction;
    }

    private ClearOrdersAction getClearOrdersAction() {
        if (clearOrdersAction == null) {
            clearOrdersAction = new ClearOrdersAction(controller, null);
        }
        return clearOrdersAction;
    }

    private OrderFortifyAction getOrderFortifyAction() {
        if (orderFortifyAction == null) {
            orderFortifyAction = new OrderFortifyAction(controller, null);
        }
        return orderFortifyAction;
    }

    private OrderSentryAction getOrderSentryAction() {
        if (orderSentryAction == null) {
            orderSentryAction = new OrderSentryAction(controller, null);
        }
        return orderSentryAction;
    }

    private BuildRoadAction getBuildRoadAction() {
        if (buildRoadAction == null) {
            buildRoadAction = new BuildRoadAction(controller, null);
        }
        return buildRoadAction;
    }

    private BuildIrrigationAction getBuildIrrigationAction() {
        if (buildIrrigationAction == null) {
            buildIrrigationAction = new BuildIrrigationAction(controller, null);
        }
        return buildIrrigationAction;
    }

    private BuildMineAction getBuildMineAction() {
        if (buildMineAction == null) {
            buildMineAction = new BuildMineAction(controller, null);
        }
        return buildMineAction;
    }

    private DestroyAllTileImprovementsAction getDestroyAllTileImprovementsAction() {
        if (destroyAllTileImprovementsAction == null) {
            destroyAllTileImprovementsAction = new DestroyAllTileImprovementsAction(controller, null);
        }
        return destroyAllTileImprovementsAction;
    }

    private DestroyRoadAction getDestroyRoadAction() {
        if (destroyRoadAction == null) {
            destroyRoadAction = new DestroyRoadAction(controller, null);
        }
        return destroyRoadAction;
    }

    private DestroyIrrigationAction getDestroyIrrigationAction() {
        if (destroyIrrigationAction == null) {
            destroyIrrigationAction = new DestroyIrrigationAction(controller, null);
        }
        return destroyIrrigationAction;
    }

    private DestroyMineAction getDestroyMineAction() {
        if (destroyMineAction == null) {
            destroyMineAction = new DestroyMineAction(controller, null);
        }
        return destroyMineAction;
    }

    private AutomateUnitAction getAutomateUnitAction() {
        if (automateUnitAction == null) {
            automateUnitAction = new AutomateUnitAction(controller, null);
        }
        return automateUnitAction;
    }

    private AbstractAction getDisplayOrbitAction() {
        if (displayOrbitAction == null) {
            displayOrbitAction = new DisplayOrbitAction(controller);
        }
        return displayOrbitAction;
    }

    private AbstractAction getDisplayEarthAction() {
        if (displayEarthAction == null) {
            displayEarthAction = new DisplayEarthAction(controller);
        }
        return displayEarthAction;
    }

    private AbstractAction getDisplayExpeditionaryForceAction() {
        if (displayExpeditionaryForceAction == null) {
            displayExpeditionaryForceAction = new DisplayExpeditionaryForceAction(controller);
        }
        return displayExpeditionaryForceAction;
    }

    private SendUnitToOrbitAction getSendUnitToOrbitAction() {
        if (sendUnitToOrbitAction == null) {
            sendUnitToOrbitAction = new SendUnitToOrbitAction(controller, null);
        }
        return sendUnitToOrbitAction;
    }

    private SendUnitToEarthAction getSendUnitToEarthAction() {
        if (sendUnitToEarthAction == null) {
            sendUnitToEarthAction = new SendUnitToEarthAction(controller, null);
        }
        return sendUnitToEarthAction;
    }

    private ClearVegetationAction getClearVegetationAction() {
        if (clearVegetationAction == null) {
            clearVegetationAction = new ClearVegetationAction(controller, null);
        }
        return clearVegetationAction;
    }

    private TransformTerrainAction getTransformTerrainAction() {
        if (transformTerrainAction == null) {
            transformTerrainAction = new TransformTerrainAction(controller, null);
        }
        return transformTerrainAction;
    }

    private DeclareIndependenceAction getDeclareIndependenceAction() {
        if (declareIndependenceAction == null) {
            declareIndependenceAction = new DeclareIndependenceAction(controller);
        }
        return declareIndependenceAction;
    }

    private UnloadAllCargoAction getUnloadAllCargoAction() {
        if (unloadAllCargoAction == null) {
            unloadAllCargoAction = new UnloadAllCargoAction(controller, null);
        }
        return unloadAllCargoAction;
    }

    private UnloadAllColonistsAction getUnloadAllColonistsAction() {
        if (unloadAllColonistsAction == null) {
            unloadAllColonistsAction = new UnloadAllColonistsAction(controller, null);
        }
        return unloadAllColonistsAction;
    }

}
