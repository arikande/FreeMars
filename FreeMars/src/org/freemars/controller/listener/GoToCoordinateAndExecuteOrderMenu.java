package org.freemars.controller.listener;

import java.awt.event.ActionEvent;
import java.util.Iterator;

import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import org.freemars.colony.FreeMarsColony;
import org.freemars.controller.FreeMarsController;
import org.freerealm.command.UnitOrderAddCommand;
import org.freerealm.executor.order.BuildSettlementOrder;
import org.freerealm.executor.order.ClearVegetationOrder;
import org.freerealm.executor.order.Fortify;
import org.freerealm.executor.order.GoToCoordinate;
import org.freerealm.executor.order.ImproveTile;
import org.freerealm.executor.order.Sentry;
import org.freerealm.executor.order.TransformTileOrder;
import org.freerealm.executor.order.UnloadAllCargoOrder;
import org.freerealm.executor.order.UnloadAllPopulationOrder;
import org.freerealm.map.Coordinate;
import org.freerealm.property.BuildSettlementProperty;
import org.freerealm.property.BuildTileImprovementProperty;
import org.freerealm.property.ChangeTileTypeProperty;
import org.freerealm.property.ClearVegetationProperty;
import org.freerealm.property.ContainerProperty;
import org.freerealm.tile.Tile;
import org.freerealm.tile.TileType;
import org.freerealm.tile.improvement.BuildTileImprovementChecker;
import org.freerealm.tile.improvement.TileImprovementType;
import org.freerealm.unit.Order;
import org.freerealm.unit.Unit;

/**
 *
 * @author Deniz ARIKAN
 */
public class GoToCoordinateAndExecuteOrderMenu extends JMenu {

    public GoToCoordinateAndExecuteOrderMenu(FreeMarsController controller, Coordinate coordinate) {
        super("Go to this tile " + coordinate + " and...");
        JMenuItem goToAndFortifyMenuItem = new JMenuItem(new GoToAndFortifyAction(controller, coordinate));
        JMenuItem goToAndSentryMenuItem = new JMenuItem(new GoToAndSentryAction(controller, coordinate));
        add(goToAndFortifyMenuItem);
        add(goToAndSentryMenuItem);
        Unit unit = controller.getFreeMarsModel().getActivePlayer().getActiveUnit();
        Tile tile = controller.getFreeMarsModel().getTile(coordinate);

        if (tile.getSettlement() != null && unit.getPlayer().equals(tile.getSettlement().getPlayer())) {
            ContainerProperty containerProperty = (ContainerProperty) unit.getType().getProperty("ContainerProperty");
            if (containerProperty != null && unit.getTotalResourceWeight() > 0) {
                UnloadAllCargoOrder unloadAllCargoOrder = new UnloadAllCargoOrder(controller.getFreeMarsModel().getRealm());
                unloadAllCargoOrder.setRealm(controller.getFreeMarsModel().getRealm());
                unloadAllCargoOrder.setUnit(unit);
                JMenuItem goToAndUnloadAllCargoMenuItem = new JMenuItem(new GoToAndExecuteOrderAction("Unload all cargo", controller, coordinate, new Order[]{unloadAllCargoOrder}));
                add(goToAndUnloadAllCargoMenuItem);
            }
        }
        if (tile.getSettlement() != null && unit.getPlayer().equals(tile.getSettlement().getPlayer())) {
            ContainerProperty containerProperty = (ContainerProperty) unit.getType().getProperty("ContainerProperty");
            if (containerProperty != null && unit.getContainerManager().getContainedPopulation() > 0) {
                UnloadAllPopulationOrder unloadAllPopulationOrder = new UnloadAllPopulationOrder(controller.getFreeMarsModel().getRealm());
                unloadAllPopulationOrder.setRealm(controller.getFreeMarsModel().getRealm());
                unloadAllPopulationOrder.setUnit(unit);
                JMenuItem goToAndUnloadColonistsMenuItem = new JMenuItem(new GoToAndExecuteOrderAction("Unload colonists", controller, coordinate, new Order[]{unloadAllPopulationOrder}));
                add(goToAndUnloadColonistsMenuItem);
            }
        }
        if (unit.getType().getProperty(ClearVegetationProperty.NAME) != null) {
            if (tile.getVegetation() != null) {
                ClearVegetationOrder clearVegetationOrder = new ClearVegetationOrder(controller.getFreeMarsModel().getRealm());
                clearVegetationOrder.setUnit(unit);
                JMenuItem goToAndClearVegetationMenuItem = new JMenuItem(new GoToAndExecuteOrderAction("Clear vegetation", controller, coordinate, new Order[]{clearVegetationOrder}));
                add(goToAndClearVegetationMenuItem);
            }
        }
        if (unit.getType().getProperty(BuildSettlementProperty.NAME) != null) {
            if (tile.getSettlement() == null) {
                BuildSettlementOrder buildSettlementOrder = new BuildSettlementOrder(controller.getFreeMarsModel().getRealm());
                buildSettlementOrder.setUnit(unit);
                buildSettlementOrder.setSettlement(new FreeMarsColony(controller.getFreeMarsModel().getRealm()));
                JMenuItem goToAndBuildSettlementOrderMenuItem = new JMenuItem(new GoToAndExecuteOrderAction("Build colony", controller, coordinate, new Order[]{buildSettlementOrder}));
                add(goToAndBuildSettlementOrderMenuItem);
            }
        }
        BuildTileImprovementProperty buildTileImprovement = (BuildTileImprovementProperty) unit.getType().getProperty(BuildTileImprovementProperty.NAME);
        if (buildTileImprovement != null) {
            TileImprovementType irrigationTileImprovement = (TileImprovementType) controller.getFreeMarsModel().getRealm().getTileImprovementTypeManager().getImprovement("Irrigation");
            TileImprovementType roadTileImprovement = (TileImprovementType) controller.getFreeMarsModel().getRealm().getTileImprovementTypeManager().getImprovement("Road");
            TileImprovementType mineTileImprovement = (TileImprovementType) controller.getFreeMarsModel().getRealm().getTileImprovementTypeManager().getImprovement("Mine");
            boolean canBuildIrrigation = new BuildTileImprovementChecker().canBuildTileImprovement(buildTileImprovement, irrigationTileImprovement, tile);
            boolean canBuildRoad = new BuildTileImprovementChecker().canBuildTileImprovement(buildTileImprovement, roadTileImprovement, tile);
            boolean canBuildMine = new BuildTileImprovementChecker().canBuildTileImprovement(buildTileImprovement, mineTileImprovement, tile);

            ImproveTile roadImproveTile = new ImproveTile(controller.getFreeMarsModel().getRealm());
            roadImproveTile.setSymbol("R");
            roadImproveTile.setUnit(unit);
            roadImproveTile.setTileImprovementType(roadTileImprovement);

            ImproveTile irrigationImproveTile = new ImproveTile(controller.getFreeMarsModel().getRealm());
            irrigationImproveTile.setSymbol("I");
            irrigationImproveTile.setUnit(unit);
            irrigationImproveTile.setTileImprovementType(irrigationTileImprovement);

            ImproveTile mineImproveTile = new ImproveTile(controller.getFreeMarsModel().getRealm());
            mineImproveTile.setSymbol("M");
            mineImproveTile.setUnit(unit);
            mineImproveTile.setTileImprovementType(mineTileImprovement);

            if (canBuildRoad) {
                JMenuItem goToAndBuildIrrigationMenuItem = new JMenuItem(new GoToAndExecuteOrderAction("Build roads", controller, coordinate, new Order[]{roadImproveTile}));
                add(goToAndBuildIrrigationMenuItem);
            }
            if (canBuildIrrigation) {
                JMenuItem goToAndBuildIrrigationMenuItem = new JMenuItem(new GoToAndExecuteOrderAction("Build irrigation", controller, coordinate, new Order[]{irrigationImproveTile}));
                add(goToAndBuildIrrigationMenuItem);
            }
            if (canBuildMine) {
                JMenuItem goToAndBuildMineMenuItem = new JMenuItem(new GoToAndExecuteOrderAction("Build mine", controller, coordinate, new Order[]{mineImproveTile}));
                add(goToAndBuildMineMenuItem);
            }
            if (canBuildRoad && canBuildIrrigation) {
                JMenuItem goToAndBuildRoadIrrigationMenuItem = new JMenuItem(new GoToAndExecuteOrderAction("Build roads & irrigation", controller, coordinate, new Order[]{roadImproveTile, irrigationImproveTile}));
                add(goToAndBuildRoadIrrigationMenuItem);
            }
            if (canBuildRoad && canBuildMine) {
                JMenuItem goToAndBuildRoadMineMenuItem = new JMenuItem(new GoToAndExecuteOrderAction("Build roads & mine", controller, coordinate, new Order[]{roadImproveTile, mineImproveTile}));
                add(goToAndBuildRoadMineMenuItem);
            }
        }
        ChangeTileTypeProperty changeTileTypeProperty = (ChangeTileTypeProperty) unit.getType().getProperty("ChangeTileTypeProperty");
        if (changeTileTypeProperty != null) {
            if (tile.getType().getTransformableTileTypecount() > 0) {
                JMenuItem goToAndTransformTerrainMenuItem = new JMenuItem(new GoToAndExecuteOrderAction("Transform terrain", controller, coordinate, new Order[]{prepareTransformTerrainOrder(controller, unit, tile)}));
                add(goToAndTransformTerrainMenuItem);
            }
        }
    }

    private Order prepareTransformTerrainOrder(FreeMarsController controller, Unit unit, Tile tile) {
        TransformTileOrder transformTileOrder = new TransformTileOrder(controller.getFreeMarsModel().getRealm());
        transformTileOrder.setSymbol("T");
        transformTileOrder.setUnit(unit);
        TileType tileType = tile.getType();
        TileType transformToTileType = null;
        if (tileType.getTransformableTileTypecount() > 0) {
            Iterator<Integer> iterator = tileType.getTransformableTileTypeIdsIterator();
            while (iterator.hasNext()) {
                Integer integer = iterator.next();
                transformToTileType = controller.getFreeMarsModel().getTileType(integer);
            }
        }
        transformTileOrder.setTileType(transformToTileType);
        return transformTileOrder;
    }

    class GoToAndExecuteOrderAction extends AbstractAction {

        private final FreeMarsController controller;
        private final Coordinate coordinate;
        private final Order[] orders;

        public GoToAndExecuteOrderAction(String text, FreeMarsController controller, Coordinate coordinate, Order[] orders) {
            super(text);
            this.controller = controller;
            this.coordinate = coordinate;
            this.orders = orders;
        }

        public void actionPerformed(ActionEvent e) {
            Unit unit = controller.getFreeMarsModel().getActivePlayer().getActiveUnit();
            GoToCoordinate goToCoordinate = new GoToCoordinate(controller.getFreeMarsModel().getRealm());
            goToCoordinate.setUnit(unit);
            goToCoordinate.setCoordinate(coordinate);
            controller.execute(new UnitOrderAddCommand(controller.getFreeMarsModel().getRealm(), unit, goToCoordinate));
            for (int i = 0; i < orders.length; i++) {
                controller.execute(new UnitOrderAddCommand(controller.getFreeMarsModel().getRealm(), unit, orders[i]));
            }
        }
    }

    class GoToAndFortifyAction extends AbstractAction {

        private final FreeMarsController controller;
        private final Coordinate coordinate;

        public GoToAndFortifyAction(FreeMarsController controller, Coordinate coordinate) {
            super("Fortify");
            this.controller = controller;
            this.coordinate = coordinate;
        }

        public void actionPerformed(ActionEvent e) {
            Unit unit = controller.getFreeMarsModel().getActivePlayer().getActiveUnit();
            GoToCoordinate goToCoordinate = new GoToCoordinate(controller.getFreeMarsModel().getRealm());
            goToCoordinate.setUnit(unit);
            goToCoordinate.setCoordinate(coordinate);
            Fortify fortify = new Fortify(controller.getFreeMarsModel().getRealm());
            fortify.setUnit(unit);
            controller.execute(new UnitOrderAddCommand(controller.getFreeMarsModel().getRealm(), unit, goToCoordinate));
            controller.execute(new UnitOrderAddCommand(controller.getFreeMarsModel().getRealm(), unit, fortify));
        }
    }

    class GoToAndSentryAction extends AbstractAction {

        private final FreeMarsController controller;
        private final Coordinate coordinate;

        public GoToAndSentryAction(FreeMarsController controller, Coordinate coordinate) {
            super("Sentry");
            this.controller = controller;
            this.coordinate = coordinate;
        }

        public void actionPerformed(ActionEvent e) {
            Unit unit = controller.getFreeMarsModel().getActivePlayer().getActiveUnit();
            GoToCoordinate goToCoordinate = new GoToCoordinate(controller.getFreeMarsModel().getRealm());
            goToCoordinate.setUnit(unit);
            goToCoordinate.setCoordinate(coordinate);
            Sentry sentry = new Sentry(controller.getFreeMarsModel().getRealm());
            sentry.setUnit(unit);
            controller.execute(new UnitOrderAddCommand(controller.getFreeMarsModel().getRealm(), unit, goToCoordinate));
            controller.execute(new UnitOrderAddCommand(controller.getFreeMarsModel().getRealm(), unit, sentry));
        }
    }
}
