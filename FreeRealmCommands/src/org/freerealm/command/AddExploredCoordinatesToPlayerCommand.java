package org.freerealm.command;

import java.util.List;

import org.commandexecutor.Command;
import org.freerealm.map.Coordinate;
import org.freerealm.player.Player;
import org.freerealm.unit.Unit;

/**
 * @author Deniz ARIKAN
 */
public class AddExploredCoordinatesToPlayerCommand extends FreeRealmAbstractCommand {

	private final Player player;
	private Unit exploringUnit;
	private final List<Coordinate> coordinates;

	public AddExploredCoordinatesToPlayerCommand(Player player, List<Coordinate> coordinates) {
		this.player = player;
		this.coordinates = coordinates;
	}

	public void execute() {
		if (player != null) {
			List<Coordinate> addedCoordinates = player.addExploredCoordinates(coordinates);
			setState(Command.SUCCEEDED);
			putParameter("player", player);
			putParameter("exploring_unit", exploringUnit);
			putParameter("added_coordinates", addedCoordinates);
		} else {
			setText("Player cannot be null");
			setState(Command.FAILED);

		}
	}

	public Unit getExploringUnit() {
		return exploringUnit;
	}

	public void setExploringUnit(Unit exploringUnit) {
		this.exploringUnit = exploringUnit;
	}

}
