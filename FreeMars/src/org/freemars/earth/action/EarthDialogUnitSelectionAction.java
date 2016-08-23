package org.freemars.earth.action;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.Iterator;
import java.util.Map.Entry;
import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import org.freemars.controller.FreeMarsController;
import org.freemars.controller.action.DisplayRenameUnitFromEarthDialogAction;
import org.freemars.earth.Earth;
import org.freemars.earth.EarthFlightProperty;
import org.freemars.earth.Location;
import org.freemars.earth.ui.EarthDialog;
import org.freemars.earth.ui.UnitSelectionToggleButton;
import org.freerealm.resource.Resource;
import org.freerealm.settlement.Settlement;
import org.freerealm.settlement.improvement.SettlementImprovementType;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class EarthDialogUnitSelectionAction extends AbstractAction {

    private final FreeMarsController freeMarsController;
    private final EarthDialog earthDialog;

    public EarthDialogUnitSelectionAction(FreeMarsController freeMarsController, EarthDialog earthDialog) {
        this.freeMarsController = freeMarsController;
        this.earthDialog = earthDialog;
    }

    public void actionPerformed(ActionEvent e) {

        SettlementImprovementType starPortImprovementType = freeMarsController.getFreeMarsModel().getRealm().getSettlementImprovementManager().getImprovement("Starport");
        UnitSelectionToggleButton unitSelectionToggleButton = (UnitSelectionToggleButton) e.getSource();
        Unit unit = unitSelectionToggleButton.getUnit();
        freeMarsController.getFreeMarsModel().getEarth().setEarthDialogSelectedUnit(unit);
        earthDialog.update(Earth.UNIT_SELECTION_UPDATE);
        JPopupMenu popup = new JPopupMenu();
        if (unit.getType().getProperty(EarthFlightProperty.NAME) != null) {
            SendUnitToMarsAction sendUnitToMarsOrbitAction = new SendUnitToMarsAction(freeMarsController, earthDialog, unit);
            JMenuItem goToMarsOrbitMenuItem = new JMenuItem();
            goToMarsOrbitMenuItem.setAction(sendUnitToMarsOrbitAction);
            goToMarsOrbitMenuItem.setText("Go to Mars orbit");
            boolean colonyWithStarportFound = false;
            JMenu goToMarsColonyMenu = new JMenu("Go to Mars colony");
            Iterator<Settlement> iterator = unit.getPlayer().getSettlementsIterator();
            while (iterator.hasNext()) {
                Settlement settlement = iterator.next();
                if (settlement.hasImprovementType(starPortImprovementType)) {
                    colonyWithStarportFound = true;
                    JMenuItem marsColonyMenuItem = new JMenuItem();
                    marsColonyMenuItem.setAction(new SendUnitToMarsColonyAction(freeMarsController, earthDialog, settlement));
                    marsColonyMenuItem.setText(settlement.getName());
                    goToMarsColonyMenu.add(marsColonyMenuItem);
                }
            }
            popup.add(goToMarsOrbitMenuItem);
            if (colonyWithStarportFound) {
                popup.add(goToMarsColonyMenu);
            } else {
                popup.add(new JSeparator());
                JMenuItem noStarportMenuItem = new JMenuItem("No starport on Mars");
                noStarportMenuItem.setEnabled(false);
                noStarportMenuItem.setFont(noStarportMenuItem.getFont().deriveFont(Font.ITALIC));
                popup.add(noStarportMenuItem);
            }
            if ((unit.getType().getProperty("ContainerProperty") != null) && hasCargoToSell(unit)) {
                JMenuItem sellAllMenuItem = new JMenuItem();
                SellAllToEarthAction sellAllToEarthAction = new SellAllToEarthAction(freeMarsController, earthDialog, unit);
                sellAllMenuItem.setAction(sellAllToEarthAction);
                sellAllMenuItem.setText("Sell all cargo on board");
                popup.add(sellAllMenuItem);
            }
        } else {
            //   JPopupMenu popupMenu = new JPopupMenu();
            JMenuItem noStarportMenuItem = new JMenuItem("Board unit...");
            noStarportMenuItem.setEnabled(false);
            noStarportMenuItem.setFont(noStarportMenuItem.getFont().deriveFont(Font.ITALIC));
            popup.add(noStarportMenuItem);
            popup.add(new JSeparator());
            Iterator<Entry<Unit, Location>> iterator = freeMarsController.getFreeMarsModel().getEarth().getUnitLocationsIterator();
            while (iterator.hasNext()) {
                Entry<Unit, Location> entry = iterator.next();
                Unit locationUnit = entry.getKey();
                if (!unit.equals(locationUnit)) {
                    Location location = entry.getValue();
                    if (locationUnit.getPlayer().equals(freeMarsController.getFreeMarsModel().getRealm().getPlayerManager().getActivePlayer())) {
                        if (location.equals(Location.EARTH)) {
                            if (locationUnit.canContainUnit(unit.getType())) {
                                JMenuItem locationUnitMenuItem = new JMenuItem(new AddUnitToContainerAction(freeMarsController, earthDialog, locationUnit, unit));
                                locationUnitMenuItem.setText(locationUnit.getName());
                                popup.add(locationUnitMenuItem);
                            }
                        }
                    }
                }
            }
        }
        popup.add(new JSeparator());
        JMenuItem sellUnitToEarthMenuItem = new JMenuItem();
        sellUnitToEarthMenuItem.setAction(new SellUnitToEarthAction(freeMarsController, earthDialog, unit));
        sellUnitToEarthMenuItem.setText("Sell unit");
        popup.add(sellUnitToEarthMenuItem);
        popup.add(new JMenuItem(new DisplayRenameUnitFromEarthDialogAction(freeMarsController, earthDialog, unit)));
        popup.show(unitSelectionToggleButton, unitSelectionToggleButton.getWidth() / 2, unitSelectionToggleButton.getHeight() / 2);
    }

    private boolean hasCargoToSell(Unit unit) {
        Iterator<Resource> iterator = unit.getContainedResourcesIterator();
        while (iterator.hasNext()) {
            Resource resource = iterator.next();
            int quantity = unit.getResourceQuantity(resource);
            if (quantity > 0) {
                return true;
            }
        }
        return false;
    }
}
