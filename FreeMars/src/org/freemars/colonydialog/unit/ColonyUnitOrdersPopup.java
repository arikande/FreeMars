package org.freemars.colonydialog.unit;

import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import org.freemars.colonydialog.ColonyDialogModel;
import org.freemars.controller.ActionManager;
import org.freemars.controller.FreeMarsController;
import org.freemars.controller.action.DisplayHelpContentsAction;

/**
 *
 * @author Deniz ARIKAN
 */
public class ColonyUnitOrdersPopup extends JPopupMenu {

    private final FreeMarsController controller;
    private final ColonyDialogModel model;

    public ColonyUnitOrdersPopup(ColonyDialogModel model, FreeMarsController controller) {
        this.model = model;
        this.controller = controller;
    }

    public void build() {
        if (model.getSelectedUnit() != null) {
            removeAll();
            Action fortifyAction = controller.getAction(ActionManager.ORDER_FORTIFY_ACTION);
            if (fortifyAction.isEnabled()) {
                add(new PopupMenuItem(new FortifyAction(model)));
            }
            Action sentryAction = controller.getAction(ActionManager.ORDER_SENTRY_ACTION);
            if (sentryAction.isEnabled()) {
                add(new PopupMenuItem(new SentryAction(model)));
            }
            Action buildRoadAction = controller.getAction(ActionManager.ORDER_BUILD_ROAD_ACTION);
            if (buildRoadAction.isEnabled()) {
                add(new PopupMenuItem(new BuildRoadAction(model)));
            }
            Action buildIrrigationAction = controller.getAction(ActionManager.ORDER_BUILD_IRRIGATION_ACTION);
            if (buildIrrigationAction.isEnabled()) {
                add(new PopupMenuItem(new BuildIrrigationAction(model)));
            }
            Action clearVegetationAction = controller.getAction(ActionManager.ORDER_CLEAR_VEGETATION_ACTION);
            if (clearVegetationAction.isEnabled()) {
                add(new PopupMenuItem(new ClearVegetationAction(model)));
            }
            Action unloadAllCargoAction = controller.getAction(ActionManager.UNLOAD_ALL_CARGO_ACTION);
            if (unloadAllCargoAction.isEnabled()) {
                PopupMenuItem unloadAllCargoMenuItem = new PopupMenuItem(new UnloadAllCargoAction(model));
                add(unloadAllCargoMenuItem);
            }
            Action unloadAllColonistsAction = controller.getAction(ActionManager.UNLOAD_ALL_COLONISTS_ACTION);
            if (unloadAllColonistsAction.isEnabled()) {
                PopupMenuItem unloadAllColonistsMenuItem = new PopupMenuItem(new UnloadAllColonistsAction(model));
                add(unloadAllColonistsMenuItem);
            }
            Action goToOrbitAction = controller.getAction(ActionManager.ORDER_SEND_UNIT_TO_ORBIT_ACTION);
            if (goToOrbitAction.isEnabled()) {
                PopupMenuItem goToEarthMenuItem = new PopupMenuItem(new GoToEarthAction(model));
                add(goToEarthMenuItem);
            }
            Action goToEarthAction = controller.getAction(ActionManager.ORDER_SEND_UNIT_TO_EARTH_ACTION);
            if (goToEarthAction.isEnabled()) {
                PopupMenuItem goToOrbitMenuItem = new PopupMenuItem(new GoToOrbitAction(model));
                add(goToOrbitMenuItem);
            }
            Action clearOrdersAction = controller.getAction(ActionManager.CLEAR_ORDERS_ACTION);
            if (clearOrdersAction.isEnabled()) {
                PopupMenuItem clearOrdersMenuItem = new PopupMenuItem(new ClearOrdersAction(model));
                add(clearOrdersMenuItem);
            }
            add(new JSeparator());
            Action displayRenameUnitDialogAction = controller.getAction(ActionManager.DISPLAY_RENAME_UNIT_DIALOG_ACTION);
            if (displayRenameUnitDialogAction.isEnabled()) {
                add(new PopupMenuItem(new DisplayRenameUnitDialogAction(model)));
            }
            Action disbandAction = controller.getAction(ActionManager.DISBAND_UNIT_ACTION);
            if (disbandAction.isEnabled()) {
                PopupMenuItem disbandMenuItem = new PopupMenuItem(new DisbandAction(model));
                add(disbandMenuItem);
            }
            add(new JSeparator());
            PopupMenuItem displayUnitHelpMenuItem = new PopupMenuItem(new DisplayHelpContentsAction(controller, "Unit." + model.getSelectedUnit().getType().getName()));
            displayUnitHelpMenuItem.setText(model.getSelectedUnit().getType().getName() + " help");
            add(displayUnitHelpMenuItem);
        }
    }

    private class PopupMenuItem extends JMenuItem {

        public PopupMenuItem(Action action) {
            super(action);
            setFont(new Font("Arial", 0, 10));
        }
    }

    private class SentryAction extends AbstractAction {

        private final ColonyDialogModel colonyDialogModel;

        private SentryAction(ColonyDialogModel colonyDialogModel) {
            super((String) controller.getAction(ActionManager.ORDER_SENTRY_ACTION).getValue(Action.NAME));
            this.colonyDialogModel = colonyDialogModel;
        }

        public void actionPerformed(ActionEvent e) {
            controller.getAction(ActionManager.ORDER_SENTRY_ACTION).actionPerformed(e);
            colonyDialogModel.refresh(ColonyDialogModel.COLONY_UNITS_UPDATE);
        }
    }

    private class FortifyAction extends AbstractAction {

        private final ColonyDialogModel colonyDialogModel;

        private FortifyAction(ColonyDialogModel colonyDialogModel) {
            super((String) controller.getAction(ActionManager.ORDER_FORTIFY_ACTION).getValue(Action.NAME));
            this.colonyDialogModel = colonyDialogModel;
        }

        public void actionPerformed(ActionEvent e) {
            controller.getAction(ActionManager.ORDER_FORTIFY_ACTION).actionPerformed(e);
            colonyDialogModel.refresh(ColonyDialogModel.COLONY_UNITS_UPDATE);
        }
    }

    private class BuildRoadAction extends AbstractAction {

        private final ColonyDialogModel colonyDialogModel;

        private BuildRoadAction(ColonyDialogModel colonyDialogModel) {
            super("Build road");
            this.colonyDialogModel = colonyDialogModel;
        }

        public void actionPerformed(ActionEvent e) {
            controller.getAction(ActionManager.ORDER_BUILD_ROAD_ACTION).actionPerformed(e);
            colonyDialogModel.refresh(ColonyDialogModel.COLONY_UNITS_UPDATE);
        }
    }

    private class BuildIrrigationAction extends AbstractAction {

        private final ColonyDialogModel colonyDialogModel;

        private BuildIrrigationAction(ColonyDialogModel colonyDialogModel) {
            super("Build irrigation");
            this.colonyDialogModel = colonyDialogModel;
        }

        public void actionPerformed(ActionEvent e) {
            controller.getAction(ActionManager.ORDER_BUILD_IRRIGATION_ACTION).actionPerformed(e);
            colonyDialogModel.refresh(ColonyDialogModel.COLONY_UNITS_UPDATE);
        }
    }

    private class ClearVegetationAction extends AbstractAction {

        private final ColonyDialogModel colonyDialogModel;

        private ClearVegetationAction(ColonyDialogModel colonyDialogModel) {
            super((String) controller.getAction(ActionManager.ORDER_CLEAR_VEGETATION_ACTION).getValue(Action.NAME));
            this.colonyDialogModel = colonyDialogModel;
        }

        public void actionPerformed(ActionEvent e) {
            controller.getAction(ActionManager.ORDER_CLEAR_VEGETATION_ACTION).actionPerformed(e);
            colonyDialogModel.refresh(ColonyDialogModel.COLONY_UNITS_UPDATE);
        }
    }

    private class UnloadAllCargoAction extends AbstractAction {

        private final ColonyDialogModel colonyDialogModel;

        private UnloadAllCargoAction(ColonyDialogModel colonyDialogModel) {
            super("Unload all cargo");
            this.colonyDialogModel = colonyDialogModel;
        }

        public void actionPerformed(ActionEvent e) {
            controller.getAction(ActionManager.UNLOAD_ALL_CARGO_ACTION).actionPerformed(e);
            colonyDialogModel.refresh(ColonyDialogModel.COLONY_RESOURCES_UPDATE);
            colonyDialogModel.refresh(ColonyDialogModel.COLONY_UNITS_UPDATE);
        }
    }

    private class UnloadAllColonistsAction extends AbstractAction {

        private final ColonyDialogModel colonyDialogModel;

        private UnloadAllColonistsAction(ColonyDialogModel colonyDialogModel) {
            super("Unload all colonists");
            this.colonyDialogModel = colonyDialogModel;
        }

        public void actionPerformed(ActionEvent e) {
            controller.getAction(ActionManager.UNLOAD_ALL_COLONISTS_ACTION).actionPerformed(e);
            colonyDialogModel.refresh(ColonyDialogModel.WORKFORCE_UPDATE);
            colonyDialogModel.refresh(ColonyDialogModel.COLONY_RESOURCES_UPDATE);
            colonyDialogModel.refresh(ColonyDialogModel.COLONY_IMPROVEMENTS_UPDATE);
            colonyDialogModel.refresh(ColonyDialogModel.UNIT_CARGO_CHANGE_UPDATE);
        }
    }

    private class GoToEarthAction extends AbstractAction {

        private final ColonyDialogModel colonyDialogModel;

        private GoToEarthAction(ColonyDialogModel colonyDialogModel) {
            super("Send to Earth");
            this.colonyDialogModel = colonyDialogModel;
        }

        public void actionPerformed(ActionEvent e) {
            controller.getAction(ActionManager.ORDER_SEND_UNIT_TO_EARTH_ACTION).actionPerformed(e);
            colonyDialogModel.setSelectedUnit(null);
            colonyDialogModel.refresh(ColonyDialogModel.COLONY_UNITS_UPDATE);
        }
    }

    private class GoToOrbitAction extends AbstractAction {

        private final ColonyDialogModel colonyDialogModel;

        private GoToOrbitAction(ColonyDialogModel colonyDialogModel) {
            super("Send to orbit");
            this.colonyDialogModel = colonyDialogModel;
        }

        public void actionPerformed(ActionEvent e) {
            controller.getAction(ActionManager.ORDER_SEND_UNIT_TO_ORBIT_ACTION).actionPerformed(e);
            colonyDialogModel.setSelectedUnit(null);
            colonyDialogModel.refresh(ColonyDialogModel.COLONY_UNITS_UPDATE);
        }
    }

    private class ClearOrdersAction extends AbstractAction {

        private final ColonyDialogModel colonyDialogModel;

        private ClearOrdersAction(ColonyDialogModel colonyDialogModel) {
            super((String) controller.getAction(ActionManager.CLEAR_ORDERS_ACTION).getValue(Action.NAME));
            this.colonyDialogModel = colonyDialogModel;
        }

        public void actionPerformed(ActionEvent e) {
            controller.getAction(ActionManager.CLEAR_ORDERS_ACTION).actionPerformed(e);
            colonyDialogModel.refresh(ColonyDialogModel.COLONY_UNITS_UPDATE);
        }
    }

    private class DisplayRenameUnitDialogAction extends AbstractAction {

        private final ColonyDialogModel colonyDialogModel;

        private DisplayRenameUnitDialogAction(ColonyDialogModel colonyDialogModel) {
            super("Rename");
            this.colonyDialogModel = colonyDialogModel;
        }

        public void actionPerformed(ActionEvent e) {
            controller.getAction(ActionManager.DISPLAY_RENAME_UNIT_DIALOG_ACTION).actionPerformed(e);
            colonyDialogModel.refresh(ColonyDialogModel.COLONY_UNITS_UPDATE);
        }
    }

    private class DisbandAction extends AbstractAction {

        private final ColonyDialogModel colonyDialogModel;

        private DisbandAction(ColonyDialogModel colonyDialogModel) {
            super("Disband");
            this.colonyDialogModel = colonyDialogModel;
        }

        public void actionPerformed(ActionEvent e) {
            controller.getAction(ActionManager.DISBAND_UNIT_ACTION).actionPerformed(e);
            colonyDialogModel.setSelectedUnit(null);
            colonyDialogModel.refresh(ColonyDialogModel.COLONY_UNITS_UPDATE);
        }
    }
}
