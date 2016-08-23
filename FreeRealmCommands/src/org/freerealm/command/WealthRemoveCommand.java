package org.freerealm.command;

import org.freerealm.player.Player;

/**
 * Command class to remove given amount of wealth from a player.
 * 
 * @author Deniz ARIKAN
 */
public class WealthRemoveCommand extends FreeRealmAbstractCommand {

	private final Player player;
	private int amount = 0;

	/**
	 * Constructs a WealthRemoveCommand using player, amount
	 * 
	 * @param player
	 *            Player to remove wealth
	 * @param amount
	 *            Amount to remove
	 */
	public WealthRemoveCommand(Player player, int amount) {
		this.player = player;
		this.amount = amount;
	}

	/**
	 * Executes command to remove given amount of wealth from player
	 * 
	 * @return CommandResult
	 */
	public void execute() {
		if (player.getWealth() < amount) {
			setState(FAILED);
			return;
		}
		player.setWealth(player.getWealth() - amount);
		setState(SUCCEEDED);
	}
}
