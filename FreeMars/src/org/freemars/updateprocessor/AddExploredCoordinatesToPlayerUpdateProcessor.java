package org.freemars.updateprocessor;

import java.util.ArrayList;
import java.util.List;

import org.commandexecutor.Command;
import org.freemars.controller.FreeMarsController;
import org.freemars.controller.viewcommand.UpdateCoordinatePaintModelCommand;
import org.freerealm.map.Coordinate;
import org.freerealm.player.Player;
import org.freerealm.unit.Unit;

public class AddExploredCoordinatesToPlayerUpdateProcessor extends FreeMarsUpdateProcessor {

	public AddExploredCoordinatesToPlayerUpdateProcessor(FreeMarsController freeMarsController) {
		super(freeMarsController);
	}

	@Override
	public void processUpdate(Command command) {
		Player player = (Player) command.getParameter("player");
		if (player.equals(getFreeMarsController().getFreeMarsModel().getHumanPlayer())) {
			List<Coordinate> addedCoordinates = (List<Coordinate>) command.getParameter("added_coordinates");
			if (!addedCoordinates.isEmpty()) {
				List<Coordinate> modelUpdateCoordinates = new ArrayList<Coordinate>();
				modelUpdateCoordinates.addAll(addedCoordinates);
				for (Coordinate coordinate : addedCoordinates) {
					List<Coordinate> circleCoordinates = getFreeMarsController().getFreeMarsModel().getRealm().getCircleCoordinates(coordinate, 1);
					for (Coordinate circleCoordinate : circleCoordinates) {
						if (!modelUpdateCoordinates.contains(circleCoordinate)) {
							modelUpdateCoordinates.add(circleCoordinate);
						}
					}
				}
				Unit exploringUnit = (Unit) command.getParameter("exploring_unit");
				List<Unit> excludeUnits = new ArrayList<>();
				excludeUnits.add(exploringUnit);
				getFreeMarsController().executeViewCommand(new UpdateCoordinatePaintModelCommand(getFreeMarsController(), modelUpdateCoordinates, excludeUnits));
			}
		}
	}

}
