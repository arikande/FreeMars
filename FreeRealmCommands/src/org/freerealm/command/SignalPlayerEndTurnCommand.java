package org.freerealm.command;

import org.freerealm.player.Player;

/**
 * 
 * @author Deniz ARIKAN
 */
public class SignalPlayerEndTurnCommand extends FreeRealmAbstractCommand {

	private final Player player;

	public SignalPlayerEndTurnCommand(Player player) {
		this.player = player;
	}

	public void execute() {
		if (player != null) {
			player.setTurnEnded(true);
			setState(SUCCEEDED);
		} else {
			setText("Player can not be null");
			setState(FAILED);
		}
	}
}
