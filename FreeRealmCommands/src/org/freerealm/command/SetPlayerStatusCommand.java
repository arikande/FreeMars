package org.freerealm.command;

import org.freerealm.player.Player;

/**
 * Command class to set a player's status.
 * 
 * @author Deniz ARIKAN
 */
public class SetPlayerStatusCommand extends FreeRealmAbstractCommand {

	private final Player player;
	private final int status;

	/**
	 * Constructs an SetPlayerStatusCommand using player, status.
	 * 
	 * @param player
	 *            Player to set status
	 * @param status
	 *            New status
	 */
	public SetPlayerStatusCommand(Player player, int status) {
		this.player = player;
		this.status = status;
	}

	/**
	 * Executes command and set the given status to player.
	 * 
	 * @return CommandResult
	 */
	public void execute() {
		player.setStatus(status);
		setState(SUCCEEDED);
	}
}
