package org.freerealm.command;

import org.freerealm.player.Player;

/**
 * Command class to add given amount of wealth to a player.
 * 
 * @author Deniz ARIKAN
 */
public class WealthAddCommand extends FreeRealmAbstractCommand {

	private final Player player;
	private int amount = 0;

	/**
	 * Constructs a WealthAddCommand using player, amount
	 * 
	 * @param player
	 *            Player to add wealth
	 * @param amount
	 *            Amount of wealth to add
	 */
	public WealthAddCommand(Player player, int amount) {
		this.player = player;
		this.amount = amount;
	}

	/**
	 * Executes command to add given amount of wealth to player
	 * 
	 * @return CommandResult
	 */
	public void execute() {
		if (player != null) {
			player.setWealth(player.getWealth() + amount);
			setState(SUCCEEDED);
		} else {
			setText("Player can not be null.");
			setState(FAILED);
		}
	}
}
