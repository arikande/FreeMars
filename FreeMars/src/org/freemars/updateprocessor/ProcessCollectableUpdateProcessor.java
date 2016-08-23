package org.freemars.updateprocessor;

import org.commandexecutor.Command;
import org.freemars.controller.FreeMarsController;
import org.freemars.controller.viewcommand.DisplayMessageCommand;
import org.freemars.tile.SpaceshipDebrisCollectable;
import org.freemars.ui.map.TilePaintModel;
import org.freerealm.map.Coordinate;
import org.freerealm.settlement.Settlement;
import org.freerealm.tile.Collectable;
import org.freerealm.unit.Unit;

public class ProcessCollectableUpdateProcessor extends FreeMarsUpdateProcessor {
	public ProcessCollectableUpdateProcessor(FreeMarsController freeMarsController) {
		super(freeMarsController);
	}

	@Override
	public void processUpdate(Command command) {
		Collectable collectable = (Collectable) command.getParameter("collectable");
		if (collectable instanceof SpaceshipDebrisCollectable) {
			SpaceshipDebrisCollectable spaceshipDebrisCollectable = (SpaceshipDebrisCollectable) collectable;
			Settlement settlement = spaceshipDebrisCollectable.getDeliveredSettlement();
			String resourceName = getFreeMarsController().getFreeMarsModel().getRealm().getResourceManager().getResource(spaceshipDebrisCollectable.getResourceId()).getName();
			if (settlement != null) {
				Coordinate coordinate = (Coordinate) command.getParameter("collected_coordinate");
				TilePaintModel tilePaintModel = getFreeMarsController().getFreeMarsModel().getFreeMarsViewModel().getTilePaintModel(coordinate);
				if (tilePaintModel != null) {
					tilePaintModel.setCollectableImage(null);
				}
				Unit collectingUnit = spaceshipDebrisCollectable.getCollectingUnit();
				if (collectingUnit.getPlayer().equals(getFreeMarsController().getFreeMarsModel().getHumanPlayer())) {
					String message = spaceshipDebrisCollectable.getAmount() + " " + resourceName + " found in debris. Resources delivered to " + settlement + ".";
					getFreeMarsController().executeViewCommand(new DisplayMessageCommand(getFreeMarsController(), message));
				}
			}
		}

	}

}
