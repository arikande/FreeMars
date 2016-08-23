package org.freemars.unit;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import org.freemars.controller.FreeMarsController;
import org.freemars.earth.Location;
import org.freemars.earth.order.RelocateUnitOrder;
import org.freemars.ui.image.FreeMarsImageManager;
import org.freemars.unit.manager.UnitManagerDialog;
import org.freemars.unit.manager.UnitsTableRow;
import org.freerealm.Realm;
import org.freerealm.executor.order.BuildSettlementOrder;
import org.freerealm.executor.order.ClearVegetationOrder;
import org.freerealm.executor.order.Fortify;
import org.freerealm.executor.order.GoToCoordinate;
import org.freerealm.executor.order.ImproveTile;
import org.freerealm.executor.order.Sentry;
import org.freerealm.executor.order.TransformTileOrder;
import org.freerealm.player.Player;
import org.freerealm.settlement.Settlement;
import org.freerealm.tile.Tile;
import org.freerealm.unit.Order;
import org.freerealm.unit.Unit;
import org.freerealm.unit.UnitType;

/**
 *
 * @author Deniz ARIKAN
 */
public class UnitsTableHelper {

    private final FreeMarsController freeMarsController;
    private final UnitManagerDialog unitManagerDialog;

    public UnitsTableHelper(FreeMarsController freeMarsController, UnitManagerDialog unitManagerDialog) {
        this.freeMarsController = freeMarsController;
        this.unitManagerDialog = unitManagerDialog;
    }

    public void updateUnitsTable() {
        Player player = freeMarsController.getFreeMarsModel().getActivePlayer();
        if (player != null) {
            unitManagerDialog.clearRows();
            Iterator<Unit> unitsIterator = player.getUnitsIterator();
            while (unitsIterator.hasNext()) {
                Unit unit = unitsIterator.next();
                UnitsTableRow unitsTableRow = new UnitsTableRow();
                unitsTableRow.setUnitId(unit.getId());
                unitsTableRow.setUnitIcon(getUnitIcon(unit));
                unitsTableRow.setUnitName(unit.getName());
                unitsTableRow.setUnitType(unit.getType().getName());
                unitsTableRow.setUnitLocation(getUnitLocation(player, unit));
                unitsTableRow.setUnitOrder(getUnitOrder(unit));
                unitsTableRow.setUnitCoordinate(getUnitCoordinate(unit));
                unitManagerDialog.addRow(unitsTableRow);
            }
        }
    }

    public void updateUnitTypeCounts() {
        Player player = freeMarsController.getFreeMarsModel().getActivePlayer();
        if (player != null) {
            unitManagerDialog.clearUnitTypeCount();
            TreeMap<UnitType, Integer> unitTypeCount = new TreeMap<UnitType, Integer>();
            Iterator<Unit> unitsIterator = player.getUnitsIterator();
            while (unitsIterator.hasNext()) {
                Unit unit = unitsIterator.next();
                int currentCount = 0;
                if (unitTypeCount.containsKey(unit.getType())) {
                    currentCount = unitTypeCount.get(unit.getType());
                }
                unitTypeCount.put(unit.getType(), currentCount + 1);
            }
            Iterator<UnitType> unitTypeIterator = unitTypeCount.keySet().iterator();
            while (unitTypeIterator.hasNext()) {
                UnitType unitType = unitTypeIterator.next();
                Image unitImage = FreeMarsImageManager.getImage(unitType);
                unitImage = FreeMarsImageManager.createResizedCopy(unitImage, 40, -1, false, unitManagerDialog);
                JLabel unitTypeCountLabel = new JLabel();
                unitTypeCountLabel.setIcon(new ImageIcon(unitImage));
                unitTypeCountLabel.setText(" : " + unitTypeCount.get(unitType) + "   ");
                unitTypeCountLabel.setToolTipText(unitType.getName());
                unitManagerDialog.addUnitTypeCount(unitTypeCountLabel);
            }
            unitManagerDialog.repaintUnitTypeCount();
        }
    }

    private ImageIcon getUnitIcon(Unit unit) {
        Image image = FreeMarsImageManager.getImage(unit);
        image = FreeMarsImageManager.createResizedCopy(image, -1, 45, false, null);
        return new ImageIcon(image);
    }

    private String getUnitOrder(Unit unit) {
        Order order = unit.getCurrentOrder();
        if (order != null) {
            if (order instanceof Fortify) {
                return "Fortified";
            }
            if (order instanceof Sentry) {
                return "Sentry";
            }
            if (order instanceof ClearVegetationOrder) {
                return "Clearing vegetation";
            }
            if (order instanceof TransformTileOrder) {
                return "Transforming terrain";
            }
            if (order instanceof GoToCoordinate) {
                GoToCoordinate goToCoordinate = (GoToCoordinate) order;
                return "Going to coordinate " + goToCoordinate.getCoordinate();
            }
            if (order instanceof BuildSettlementOrder) {
                return "Building colony";
            }
            if (order instanceof ImproveTile) {
                ImproveTile improveTile = (ImproveTile) order;
                return "Building " + improveTile.getTileImprovementType().getName().toLowerCase();
            }
        }
        return "None";
    }

    private String getUnitLocation(Player player, Unit unit) {
        Realm realm = freeMarsController.getFreeMarsModel().getRealm();
        if (unit.getCoordinate() != null) {
            Tile unitTile = realm.getTile(unit.getCoordinate());
            if (unitTile.getSettlement() != null) {
                return unitTile.getSettlement().getName();
            }
            ArrayList<Settlement> settlementsNearCoordinate = freeMarsController.getFreeMarsModel().getRealm().getSettlementsNearCoordinate(unit.getCoordinate(), 1, 5, unit.getPlayer());
            if (!settlementsNearCoordinate.isEmpty()) {
                return "Near " + settlementsNearCoordinate.get(0).getName();
            }
        } else {
            Location unitLocation = freeMarsController.getFreeMarsModel().getEarth().getUnitLocation(unit);
            if (unitLocation != null) {
                if (unitLocation.equals(Location.MARS_ORBIT)) {
                    return "In Mars orbit";
                } else if (unitLocation.equals(Location.EARTH)) {
                    return "In Earth orbit";
                } else if (unitLocation.equals(Location.TRAVELING_TO_EARTH)) {
                    return "Traveling to Earth (ETA " + freeMarsController.getFreeMarsModel().getEarth().findETA(unit) + " months)";
                } else if (unitLocation.equals(Location.TRAVELING_TO_MARS)) {
                    Order unitOrder = unit.getCurrentOrder();
                    if (unitOrder != null && unitOrder instanceof RelocateUnitOrder) {
                        RelocateUnitOrder relocateUnitOrder = (RelocateUnitOrder) unitOrder;
                        if (relocateUnitOrder.getLandOnColony() != null) {
                            return "Traveling to " + relocateUnitOrder.getLandOnColony().getName() + " (ETA " + freeMarsController.getFreeMarsModel().getEarth().findETA(unit) + " months)";
                        }
                    }
                    return "Traveling to Mars orbit (ETA " + freeMarsController.getFreeMarsModel().getEarth().findETA(unit) + " months)";
                } else if (unitLocation.equals(Location.MARS)) {
                    Order unitOrder = unit.getCurrentOrder();
                    if (unitOrder != null && unitOrder instanceof RelocateUnitOrder) {
                        RelocateUnitOrder relocateUnitOrder = (RelocateUnitOrder) unitOrder;
                        if (relocateUnitOrder.getLandOnColony() != null) {
                            return "Landing to " + relocateUnitOrder.getLandOnColony().getName();
                        }
                    }
                    return "Landing to Mars";
                }
            } else {
                Iterator<Unit> unitsIterator = player.getUnitsIterator();
                while (unitsIterator.hasNext()) {
                    Unit carryingUnit = unitsIterator.next();
                    if (carryingUnit.containsUnit(unit)) {
                        return "On board \"" + carryingUnit.getName() + "\"";
                    }
                }
            }
        }
        return "Unclaimed territory";
    }

    private String getUnitCoordinate(Unit unit) {
        if (unit.getCoordinate() != null) {
            return unit.getCoordinate().toString();
        } else {
            return "NA";
        }
    }
}
