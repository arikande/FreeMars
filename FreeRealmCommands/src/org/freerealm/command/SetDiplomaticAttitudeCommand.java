package org.freerealm.command;

import org.freerealm.diplomacy.PlayerRelation;
import org.freerealm.player.Player;

/**
 * 
 * @author Deniz ARIKAN
 */
public class SetDiplomaticAttitudeCommand extends FreeRealmAbstractCommand {

	private final Player player1;
	private final Player player2;
	private int newAttitude;

	public SetDiplomaticAttitudeCommand(Player player1, Player player2, int newAttitude) {
		this.player1 = player1;
		this.player2 = player2;
		this.newAttitude = newAttitude;
	}

	public void execute() {
		if (newAttitude < PlayerRelation.MINIMUM_ATTITUDE) {
			newAttitude = PlayerRelation.MINIMUM_ATTITUDE;
		}
		if (newAttitude > PlayerRelation.MAXIMUM_ATTITUDE) {
			newAttitude = PlayerRelation.MAXIMUM_ATTITUDE;
		}
		player1.getDiplomacy().getPlayerRelation(player2).setAttitude(newAttitude);
		setState(SUCCEEDED);
	}

}
