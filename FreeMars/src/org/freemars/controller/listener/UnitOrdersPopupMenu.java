package org.freemars.controller.listener;

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import org.freemars.controller.FreeMarsController;
import org.freemars.controller.action.ActivateUnitAction;
import org.freemars.controller.action.AutomateUnitAction;
import org.freemars.controller.action.BuildColonyAction;
import org.freemars.controller.action.DisbandUnitAction;
import org.freemars.controller.action.DisplayRenameUnitDialogAction;
import org.freemars.controller.action.SendUnitToEarthAction;
import org.freemars.controller.action.SendUnitToOrbitAction;
import org.freemars.controller.action.UnloadAllCargoAction;
import org.freemars.controller.action.UnloadAllColonistsAction;
import org.freemars.controller.action.order.BuildIrrigationAction;
import org.freemars.controller.action.order.BuildMineAction;
import org.freemars.controller.action.order.BuildRoadAction;
import org.freemars.controller.action.order.BuildRoadAndIrrigationAction;
import org.freemars.controller.action.order.BuildRoadAndMineAction;
import org.freemars.controller.action.order.ClearOrdersAction;
import org.freemars.controller.action.order.ClearVegetationAction;
import org.freemars.controller.action.order.DestroyAllTileImprovementsAction;
import org.freemars.controller.action.order.DestroyIrrigationAction;
import org.freemars.controller.action.order.DestroyMineAction;
import org.freemars.controller.action.order.DestroyRoadAction;
import org.freemars.controller.action.order.OrderFortifyAction;
import org.freemars.controller.action.order.OrderSentryAction;
import org.freemars.controller.action.order.TransformTerrainAction;
import org.freemars.ui.image.FreeMarsImageManager;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class UnitOrdersPopupMenu extends JMenu {

    private final FreeMarsController freeMarsController;
    private final Unit unit;
    private JMenu destroyTileImprovementsMenu;

    public UnitOrdersPopupMenu(FreeMarsController freeMarsController, Unit unit) {
        Image unitImage = FreeMarsImageManager.getImage(unit);
        unitImage = FreeMarsImageManager.createResizedCopy(unitImage, -1, 18, false, this);
        setIcon(new ImageIcon(unitImage));
        String unitMenuItemString = unit.getName();
        if (unit.getCurrentOrder() != null) {
            unitMenuItemString = unitMenuItemString + " (" + unit.getCurrentOrder().getSymbol() + ")";
        } else {
            unitMenuItemString = unitMenuItemString + " (-)";
        }
        setText(unitMenuItemString);
        this.freeMarsController = freeMarsController;
        this.unit = unit;
        addActivateMenuItem();
        addClearOrdersMenuItem();
        addFortifyMenuItem();
        addSentryMenuItem();
        addBuildColonyMenuItem();
        addBuildRoadMenuItem();
        addDestroyTileImprovementsMenu();
        addBuildIrrigationMenuItem();
        addBuildMineMenuItem();
        addBuildRoadAndIrrigationMenuItem();
        addBuildRoadAndMineMenuItem();
        addClearVegetationMenuItem();
        addUnloadAllCargoMenuItem();
        addUnloadAllColonistsMenuItem();
        addSendToEarthMenuItem();
        addSendToOrbitMenuItem();
        addTransformTerrainMenuItem();
        addAutomateMenuItem();
        add(new JSeparator());
        addRenameMenuItem();
        add(new JSeparator());
        addDisbandMenuItem();
    }

    private void addActivateMenuItem() {
        if (freeMarsController.isActivateEnabledForUnit(unit)) {
            add(new JMenuItem(new ActivateUnitAction(freeMarsController, unit)));
        }
    }

    private void addClearOrdersMenuItem() {
        ClearOrdersAction clearOrdersAction = new ClearOrdersAction(freeMarsController, unit);
        if (clearOrdersAction.checkEnabled()) {
            add(new JMenuItem(clearOrdersAction));
        }
    }

    private void addFortifyMenuItem() {
        OrderFortifyAction orderFortifyAction = new OrderFortifyAction(freeMarsController, unit);
        if (orderFortifyAction.checkEnabled()) {
            add(new JMenuItem(orderFortifyAction));
        }
    }

    private void addSentryMenuItem() {
        OrderSentryAction orderSentryAction = new OrderSentryAction(freeMarsController, unit);
        if (orderSentryAction.checkEnabled()) {
            add(new JMenuItem(orderSentryAction));
        }
    }

    private void addBuildColonyMenuItem() {
        BuildColonyAction buildColonyAction = new BuildColonyAction(freeMarsController, unit);
        if (buildColonyAction.checkEnabled()) {
            add(new JMenuItem(buildColonyAction));
        }
    }

    private void addBuildRoadMenuItem() {
        BuildRoadAction buildRoadAction = new BuildRoadAction(freeMarsController, unit);
        if (buildRoadAction.checkEnabled()) {
            add(new JMenuItem(buildRoadAction));
        }
    }

    private void addDestroyTileImprovementsMenu() {
        DestroyAllTileImprovementsAction destroyAllTileImprovementsAction = new DestroyAllTileImprovementsAction(freeMarsController, unit);
        DestroyRoadAction destroyRoadAction = new DestroyRoadAction(freeMarsController, unit);
        DestroyIrrigationAction destroyIrrigationAction = new DestroyIrrigationAction(freeMarsController, unit);
        DestroyMineAction destroyMineAction = new DestroyMineAction(freeMarsController, unit);
        if (destroyAllTileImprovementsAction.checkEnabled()) {
            getDestroyTileImprovementsMenu().add(new JMenuItem(destroyAllTileImprovementsAction));
        }
        if (destroyRoadAction.checkEnabled()) {
            getDestroyTileImprovementsMenu().add(new JMenuItem(destroyRoadAction));
        }
        if (destroyIrrigationAction.checkEnabled()) {
            getDestroyTileImprovementsMenu().add(new JMenuItem(destroyIrrigationAction));
        }
        if (destroyMineAction.checkEnabled()) {
            getDestroyTileImprovementsMenu().add(new JMenuItem(destroyMineAction));
        }
        if (getDestroyTileImprovementsMenu().getMenuComponentCount() > 0) {
            add(getDestroyTileImprovementsMenu());
        }
    }

    private JMenu getDestroyTileImprovementsMenu() {
        if (destroyTileImprovementsMenu == null) {
            destroyTileImprovementsMenu = new JMenu("Destroy");
        }
        return destroyTileImprovementsMenu;
    }

    private void addBuildIrrigationMenuItem() {
        BuildIrrigationAction buildIrrigationAction = new BuildIrrigationAction(freeMarsController, unit);
        if (buildIrrigationAction.checkEnabled()) {
            add(new JMenuItem(buildIrrigationAction));
        }
    }

    private void addBuildMineMenuItem() {
        BuildMineAction buildMineAction = new BuildMineAction(freeMarsController, unit);
        if (buildMineAction.checkEnabled()) {
            add(new JMenuItem(buildMineAction));
        }
    }

    private void addBuildRoadAndIrrigationMenuItem() {
        BuildRoadAndIrrigationAction buildRoadAndIrrigationAction = new BuildRoadAndIrrigationAction(freeMarsController, unit);
        if (buildRoadAndIrrigationAction.isEnabled()) {
            add(new JMenuItem(buildRoadAndIrrigationAction));
        }
    }

    private void addBuildRoadAndMineMenuItem() {
        BuildRoadAndMineAction buildRoadAndMineAction = new BuildRoadAndMineAction(freeMarsController, unit);
        if (buildRoadAndMineAction.isEnabled()) {
            add(new JMenuItem(buildRoadAndMineAction));
        }
    }

    private void addClearVegetationMenuItem() {
        ClearVegetationAction clearVegetationAction = new ClearVegetationAction(freeMarsController, unit);
        if (clearVegetationAction.checkEnabled()) {
            add(new JMenuItem(clearVegetationAction));
        }
    }

    private void addUnloadAllCargoMenuItem() {
        UnloadAllCargoAction unloadAllCargoAction = new UnloadAllCargoAction(freeMarsController, unit);
        if (unloadAllCargoAction.checkEnabled()) {
            add(new JMenuItem(unloadAllCargoAction));
        }
    }

    private void addUnloadAllColonistsMenuItem() {
        UnloadAllColonistsAction unloadAllColonistsAction = new UnloadAllColonistsAction(freeMarsController, unit);
        if (unloadAllColonistsAction.checkEnabled()) {
            add(new JMenuItem(unloadAllColonistsAction));
        }
    }

    private void addSendToEarthMenuItem() {
        SendUnitToEarthAction sendUnitToEarthAction = new SendUnitToEarthAction(freeMarsController, unit);
        if (sendUnitToEarthAction.checkEnabled()) {
            add(new JMenuItem(sendUnitToEarthAction));
        }
    }

    private void addSendToOrbitMenuItem() {
        SendUnitToOrbitAction sendUnitToOrbitAction = new SendUnitToOrbitAction(freeMarsController, unit);
        if (sendUnitToOrbitAction.checkEnabled()) {
            add(new JMenuItem(sendUnitToOrbitAction));
        }
    }

    private void addTransformTerrainMenuItem() {
        TransformTerrainAction transformTerrainAction = new TransformTerrainAction(freeMarsController, unit);
        if (transformTerrainAction.checkEnabled()) {
            add(new JMenuItem(transformTerrainAction));
        }
    }

    private void addAutomateMenuItem() {
        AutomateUnitAction automateUnitAction = new AutomateUnitAction(freeMarsController, unit);
        if (automateUnitAction.checkEnabled()) {
            JCheckBoxMenuItem automateUnitCheckBoxMenuItem = new JCheckBoxMenuItem(automateUnitAction);
            if (unit.getAutomater() != null) {
                automateUnitCheckBoxMenuItem.setSelected(true);
            } else {
                automateUnitCheckBoxMenuItem.setSelected(false);
            }
            add(automateUnitCheckBoxMenuItem);
        }
    }

    private void addRenameMenuItem() {
        JMenuItem renameUnitMenuItem = new JMenuItem(new DisplayRenameUnitDialogAction(freeMarsController, unit));
        renameUnitMenuItem.setText("Rename");
        add(renameUnitMenuItem);
    }

    private void addDisbandMenuItem() {
        JMenuItem disbandMenuItem = new JMenuItem(new DisbandUnitAction(freeMarsController, unit));
        disbandMenuItem.setText("Disband");
        add(disbandMenuItem);
    }
}
