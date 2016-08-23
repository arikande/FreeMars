package org.freerealm.command;

import java.util.List;

import org.freerealm.Utility;
import org.freerealm.map.Coordinate;
import org.freerealm.player.Player;

/**
 * @author Deniz ARIKAN
 */
public class ExchangeExploredCoordinatesCommand extends FreeRealmAbstractCommand {

	private final Player player1;
	private final Player player2;

	public ExchangeExploredCoordinatesCommand(Player player1, Player player2) {
		this.player1 = player1;
		this.player2 = player2;
	}

	public void execute() {
		if (player1 == null || player2 == null) {
			setText("Player cannot be null");
			setState(FAILED);
			return;
		}
		List<Coordinate> unexploredCoordinates1 = Utility.getUnexploredCoordinatesOfPlayer(player1, player2);
		List<Coordinate> unexploredCoordinates2 = Utility.getUnexploredCoordinatesOfPlayer(player2, player1);
		getExecutor().execute(new AddExploredCoordinatesToPlayerCommand(player1, unexploredCoordinates2));
		getExecutor().execute(new AddExploredCoordinatesToPlayerCommand(player2, unexploredCoordinates1));
		setState(SUCCEEDED);
	}

}
