package org.freemars.controller.listener;

import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.Iterator;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.event.MouseInputAdapter;

import org.freemars.colony.FreeMarsColony;
import org.freemars.controller.ActionManager;
import org.freemars.controller.FreeMarsController;
import org.freemars.controller.action.DisplayColonyDialogAction;
import org.freemars.controller.action.DisplayHelpContentsAction;
import org.freemars.controller.action.order.OrderGoToCoordinateAction;
import org.freemars.controller.viewcommand.ScrollMapCommand;
import org.freemars.controller.viewcommand.SetCenteredCoordinateCommand;
import org.freemars.controller.viewcommand.SetMainMapUnitPathCommand;
import org.freemars.ui.map.MapPanel;
import org.freerealm.command.SetActiveUnitCommand;
import org.freerealm.map.Coordinate;
import org.freerealm.map.Path;
import org.freerealm.tile.Tile;
import org.freerealm.tile.improvement.TileImprovementType;
import org.freerealm.unit.Unit;

/**
 * 
 * @author Deniz ARIKAN
 */
public class MapPanelMouseListener extends MouseInputAdapter {

	private final FreeMarsController freeMarsController;
	private final MapPanel mapPanel;
	private Coordinate mousePointerTileCoordinate;

	public MapPanelMouseListener(FreeMarsController freeMarsController, MapPanel mapPanel) {
		this.freeMarsController = freeMarsController;
		this.mapPanel = mapPanel;
	}

	@Override
	public void mouseClicked(MouseEvent mouseEvent) {
		if ((mouseEvent.getModifiers() & InputEvent.BUTTON1_MASK) == InputEvent.BUTTON1_MASK) {
			handleLeftClick(mouseEvent);
		} else if ((mouseEvent.getModifiers() & InputEvent.BUTTON3_MASK) == InputEvent.BUTTON3_MASK) {
			handleRightClick(mouseEvent);
		}
	}

	@Override
	public void mouseMoved(MouseEvent mouseEvent) {
		if (freeMarsController.getFreeMarsModel().getFreeMarsViewModel().isMapPanelDisplayingUnitPath()) {
			showUnitPath(mouseEvent);
		}
		boolean mapEdgeScrolling = Boolean.valueOf(freeMarsController.getFreeMarsModel().getFreeMarsPreferences().getProperty("map_edge_scrolling"));
		if (mapEdgeScrolling && !ScrollMapCommand.running && ScrollMapCommand.getSlideDirection(freeMarsController) != null) {
			freeMarsController.executeViewCommand(new ScrollMapCommand(freeMarsController));
		}
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent mouseWheelEvent) {
		int notches = mouseWheelEvent.getWheelRotation();
		if (notches < 0) {
			freeMarsController.getAction(ActionManager.MAIN_MAP_ZOOM_IN_ACTION).actionPerformed(null);
		} else {
			freeMarsController.getAction(ActionManager.MAIN_MAP_ZOOM_OUT_ACTION).actionPerformed(null);
		}
	}

	private void handleLeftClick(MouseEvent mouseEvent) {
		Coordinate coordinate = mapPanel.getCoordinateAt(mouseEvent.getX(), mouseEvent.getY());
		if (coordinate.getOrdinate() >= freeMarsController.getFreeMarsModel().getRealm().getMapHeight()) {
			return;
		}
		Tile tile = freeMarsController.getFreeMarsModel().getTile(coordinate);
		if (tile == null) {
			return;
		}
		freeMarsController.executeViewCommand(new SetCenteredCoordinateCommand(freeMarsController, coordinate));
		if (tile.getSettlement() != null) {
			if (tile.getSettlement().getPlayer().equals(freeMarsController.getFreeMarsModel().getHumanPlayer())) {
				(new DisplayColonyDialogAction(freeMarsController, (FreeMarsColony) tile.getSettlement())).actionPerformed(null);
			}
		} else if (tile.getNumberOfUnits() > 0) {
			Iterator<Unit> unitIterator = tile.getUnitsIterator();
			Unit unit = unitIterator.next();
			freeMarsController.addCommandToQueue(new SetActiveUnitCommand(freeMarsController.getFreeMarsModel().getActivePlayer(), unit));
		}
	}

	private void handleRightClick(MouseEvent mouseEvent) {
		JPopupMenu popup = new JPopupMenu();
		Coordinate coordinate = mapPanel.getCoordinateAt(mouseEvent.getX(), mouseEvent.getY());

		Tile tile = freeMarsController.getFreeMarsModel().getTile(coordinate);
		if (tile != null) {
			OrderGoToCoordinateAction orderGoToCoordinateAction = new OrderGoToCoordinateAction(freeMarsController, coordinate);
			if (orderGoToCoordinateAction.isEnabled()) {
				JMenuItem goToCoordinateMenuItem = new JMenuItem();
				goToCoordinateMenuItem.setAction(orderGoToCoordinateAction);
				goToCoordinateMenuItem.setText("Go to this tile " + coordinate);
				popup.add(goToCoordinateMenuItem);
				popup.add(new GoToCoordinateAndExecuteOrderMenu(freeMarsController, coordinate));
				popup.addSeparator();
			}
			boolean unitOrdersPopupMenuAdded = false;
			Iterator<Unit> iterator = tile.getUnitsIterator();
			while (iterator.hasNext()) {
				Unit unit = iterator.next();
				if (unit.getPlayer().equals(freeMarsController.getFreeMarsModel().getHumanPlayer())) {
					popup.add(new UnitOrdersPopupMenu(freeMarsController, unit));
					unitOrdersPopupMenuAdded = true;
				}
			}
			if (unitOrdersPopupMenuAdded) {
				popup.addSeparator();
			}
		}
		popup.add(new JMenuItem(freeMarsController.getAction(ActionManager.MAIN_MAP_ZOOM_IN_ACTION)));
		popup.add(new JMenuItem(freeMarsController.getAction(ActionManager.MAIN_MAP_ZOOM_OUT_ACTION)));
		if (tile != null) {
			popup.addSeparator();
			String tileTypeName = tile.getType().getName();
			JMenuItem tileDescriptionMenuItem = new JMenuItem();
			tileDescriptionMenuItem.setAction(new DisplayHelpContentsAction(freeMarsController, "TileType." + tile.getType().getName().replaceAll(" ", "_")));
			tileDescriptionMenuItem.setText(tileTypeName);
			popup.add(tileDescriptionMenuItem);
			if (tile.getVegetation() != null) {
				JMenuItem vegetationMenuItem = new JMenuItem();
				vegetationMenuItem.setAction(new DisplayHelpContentsAction(freeMarsController, "Vegetation." + tile.getVegetation().getType().getName().replaceAll(" ", "_")));
				vegetationMenuItem.setText(tile.getVegetation().getType().getName());
				popup.add(vegetationMenuItem);
			}
			if (tile.getBonusResource() != null) {
				JMenuItem bonusResourceMenuItem = new JMenuItem();
				bonusResourceMenuItem.setAction(new DisplayHelpContentsAction(freeMarsController, "Resource.Bonus." + tile.getBonusResource().getId()));
				bonusResourceMenuItem.setText(tile.getBonusResource().getName());
				popup.add(bonusResourceMenuItem);
			}
			if (tile.getImprovementCount() > 0) {
				Iterator<TileImprovementType> iterator = tile.getImprovementsIterator();
				while (iterator.hasNext()) {
					TileImprovementType tileImprovement = iterator.next();
					JMenuItem tileImprovementMenuItem = new JMenuItem();
					tileImprovementMenuItem.setAction(new DisplayHelpContentsAction(freeMarsController, "TileImprovement." + tileImprovement.getName()));
					tileImprovementMenuItem.setText(tileImprovement.getName());
					popup.add(tileImprovementMenuItem);
				}
			}
		}
		popup.show(mapPanel, mouseEvent.getX(), mouseEvent.getY());
	}

	private void showUnitPath(MouseEvent e) {
		Coordinate checkCoordinate = mapPanel.getCoordinateAt(e.getX(), e.getY());
		if ((mousePointerTileCoordinate == null) || (checkCoordinate.compareTo(mousePointerTileCoordinate) != 0)) {
			mousePointerTileCoordinate = checkCoordinate;
			Unit unit = freeMarsController.getFreeMarsModel().getActivePlayer().getActiveUnit();
			if (unit != null) {
				Path path = freeMarsController.getFreeMarsModel().getRealm().getPathFinder().findPath(unit, mousePointerTileCoordinate);
				freeMarsController.executeViewCommand(new SetMainMapUnitPathCommand(freeMarsController, path));
			}
		}
	}

}
