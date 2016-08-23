package org.freerealm.command;

import org.freerealm.player.Player;

/**
 * 
 * @author Deniz ARIKAN
 */
public class IncreaseDiplomaticAttitudeCommand extends FreeRealmAbstractCommand {

	private final Player player1;
	private final Player player2;
	private final int attitudeIncrease;

	public IncreaseDiplomaticAttitudeCommand(Player player1, Player player2, int attitudeIncrease) {
		this.player1 = player1;
		this.player2 = player2;
		this.attitudeIncrease = attitudeIncrease;
	}

	public void execute() {
		int newAttitude = player1.getDiplomacy().getPlayerRelation(player2).getAttitude() + attitudeIncrease;
		getExecutor().execute(new SetDiplomaticAttitudeCommand(player1, player2, newAttitude));
		setState(SUCCEEDED);
	}

}